package it.cipi.esercitazione.VirtualObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;

import it.cipi.esercitazione.CameraInfo;

public class OnReceiveData implements Runnable {
	 private CameraInfo[] data;
	 
	

	public OnReceiveData(CameraInfo[] inputs) {
		super();
		this.data = inputs;
		}

	public void processing() {
		/* Decode image:
		byte[] decoded = Base64.getDecoder().decode(encoded);
		// convert byte array back to BufferedImage
		InputStream in = new ByteArrayInputStream(decoded);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "jpg", new File("C:\\Users\\Giancarlo\\Documents\\news.jpg"));
		*/
		int r=(int)Math.random()*10;
		//faccio cosi in modo da non notificare sempre l'evento
		if(r>5) {
			this.notifyEvent();
		}
		
	}
	public void notifyEvent() {
		Gson json = new Gson();
		//QUI DEVO PRENDERE L'URL DELL'ORCHESTRATORE CHE � SALVATO NELLA PROPS DEL SERVLET CONTEXT
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
