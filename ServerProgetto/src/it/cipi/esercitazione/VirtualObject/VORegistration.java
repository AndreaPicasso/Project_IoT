package it.cipi.esercitazione.VirtualObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletContext;

import com.google.gson.Gson;

public class VORegistration {
	public static String VO_REGISTER = "http://localhost:8080/VORegister/rest/VORegister/VORegister";
	public static String VO_UNREGISTER = "http://localhost:8080/VORegister/rest/VORegister/VOUNRegister";

	public static String name = "VO1";

	
	

	public void register() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		HashMap<String, String> actions = new HashMap<String, String>();
		HashMap<String, String> events = new HashMap<String, String>();
		events.put("1","NUM_MAX_ACCESS_EXCEDED");
		
		data.put("name", name);
		data.put("actions", actions);
		data.put("events", events);
		
		Gson json = new Gson();
		
		/* ....Registrazione
		try {
			URL url = new URL(VO_REGISTER);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(json.toJson(data).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void unregister() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);
				Gson json = new Gson();
		
		try {
			URL url = new URL(VO_UNREGISTER);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(json.toJson(data).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
