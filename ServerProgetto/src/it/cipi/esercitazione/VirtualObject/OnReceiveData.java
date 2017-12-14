package it.cipi.esercitazione.VirtualObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;

public class OnReceiveData implements Runnable {
	 private HashMap <String, Object> data;
	 
	

	public OnReceiveData(HashMap<String, Object> inputs) {
		super();
		this.data = inputs;
		}

	public void processing() {
		int r=(int)Math.random()*10;
		//faccio cosi in modo da non notificare sempre l'evento
		if(r>5) {
			this.notifyEvent();
		}
		
	}
	public void notifyEvent() {
Gson json = new Gson();
		//QUI DEVO PRENDERE L'URL DELL'ORCHESTRATORE CHE è SALVATO NELLA PROPS DEL SERVLET CONTEXT
		String urlOrchestrator= System.getProperty("urlOrchestrator");
		try {
			URL url = new URL(urlOrchestrator);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(json.toJson("NUM_MAX_ACCESS_EXCEDED").getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		this.processing();
	}

}
