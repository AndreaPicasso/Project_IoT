package test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.gson.Gson;


public class Gateway {
	
	public final static String REST_URL = "http://localhost:8080/WeatherData_V0/rest/weatherdata/newWeatherData";

	
	public static void main(String args[]) {
		
		
		BufferedImage img = null;
		try {
			while(true) {
				//Cerco telecamere disponibili
				URL getCameras = new URL("http://www.iononrischioclout.comune.genova.it/back_end/webcam.php?checkimage");
				HttpURLConnection connection = (HttpURLConnection) getCameras.openConnection();
				InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));  

				String message = org.apache.commons.io.IOUtils.toString(rd);
				Gson gson= new Gson();
				
				CameraInfo[] cameras = gson.fromJson(message, CameraInfo[].class);
				
				for(CameraInfo camera: cameras) {
					URL url = new URL(camera.getUrl());
					try {
					img = ImageIO.read(url);
					}catch(Exception e) {
						System.out.println("Unreachable img: "+camera.getUrl());
					}
					System.out.println(camera.getUrl());
					String encoded = Base64.getEncoder().encodeToString(imageToByteArray(img));
					camera.img = encoded;
					
					
					/*
					byte[] decoded = Base64.getDecoder().decode(encoded);
					// convert byte array back to BufferedImage
					InputStream in = new ByteArrayInputStream(decoded);
					BufferedImage bImageFromConvert = ImageIO.read(in);
					ImageIO.write(bImageFromConvert, "jpg", new File("C:\\Users\\Giancarlo\\Documents\\news.jpg"));
					*/
					
				}
				
				// Invio cameras info a REST Service
				URL url = new URL(REST_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				//HashMap<String, String> input_values = new HashMap<String, String>();


				String input = gson.toJson(cameras);

				OutputStream os = null;
				try{
					os =conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();
				}catch(Exception e) {
					System.out.println("/!\\ REST service unreachable");
				}
				
				Thread.sleep(3*60*1000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}   

	}
	
	public static byte[] imageToByteArray(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		return baos.toByteArray();
	}
}
