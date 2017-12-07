package it.cipi.esercitazione;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;

public class OnRecieveNewData implements Runnable {
	String data;
	Boolean flag;

	public OnRecieveNewData(String data) {
		super();
		this.data = data;
		this.flag=false;
	}

	@Override
	public void run() {
		this.processing();
		if(this.flag==true) {
			this.notifyEvent();
		}
		this.flag=false;

	}
	public void processing() {
		int rand= (int)Math.random()*10;
		if(rand>8) {
			flag=true;
		}
	}
	public void notifyEvent() {
		try {

			URL url = new URL("http://localhost:8080/Test3/rest/testclass/testpost");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			HashMap<String, String> input_values = new HashMap<String, String>();
			input_values.put("event", "NUM_MAX_ACCESS_EXCEDED");

			Gson gson = new Gson();

			String input = gson.toJson(input_values);
			System.out.println(gson.toJson(input_values));

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		

	}

}
