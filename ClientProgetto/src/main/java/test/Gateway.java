package test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
	
	public final static String REST_URL = "http://localhost:8080/WeatherStation_V0/rest/VORealObjectCommunication/newdata";

	
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
					//camera.img = encoded.substring(0,10);
					
					/*
					byte[] decoded = Base64.getDecoder().decode(encoded);
					// convert byte array back to BufferedImage
					InputStream in = new ByteArrayInputStream(decoded);
					BufferedImage bImageFromConvert = ImageIO.read(in);
					ImageIO.write(bImageFromConvert, "jpg", new File("C:\\Users\\Giancarlo\\Documents\\news.jpg"));
					*/
					
				}
				
				
				getCameras = new URL("http://localhost:8080/WeatherStation_V0/rest/VORealObjectCommunication/getinfo");
				connection = (HttpURLConnection) getCameras.openConnection();
				is = connection.getInputStream();
			    rd = new BufferedReader(new InputStreamReader(is));  
			    message = org.apache.commons.io.IOUtils.toString(rd);
				System.out.println(message);
			    
				// Invio cameras info a REST Service
				URL url = new URL(REST_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("User-Agent", "Mozilla/5.0");
				conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				conn.setDoOutput(true);
				
				String input = gson.toJson(cameras);
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				try{
					wr.writeBytes(input);
					wr.flush();
					wr.close();
					System.out.println("Cameras imgs sent");
				}catch(Exception e) {
					System.out.println("/!\\ REST service unreachable");
				}
				
				//Responce:
				int responseCode = conn.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
				
				
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
