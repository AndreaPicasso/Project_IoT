package it.cipi.esercitazione;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.google.gson.Gson;

public class VORegistration {
	
	Properties props;
		public VORegistration(ServletContext sc ) {
		super();
		 this.props= new Properties();
		props=(Properties)sc.getAttribute("properties");
		
				
	}

		public void register() {
			URL url;
			try {
				url = new URL(props.getProperty("urlVORegister"));
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			Gson gson = new Gson();
			//qui bisogna prendere tutte le cose necessarie da props e metterle dentro a hasmap epoi nel son da inviare
			String input = gson.toJson("");
			
			OutputStream os;
			
				os = conn.getOutputStream();
			
			
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} 
	}

	

		

