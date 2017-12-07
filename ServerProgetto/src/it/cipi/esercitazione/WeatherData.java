package it.cipi.esercitazione;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;


@Path("/weatherdata")
public class WeatherData {
	
	private static Logger log;
	private static ServletContext servletContext;
	
	static void init(ServletContext sc){
		log= Logger.getRootLogger();
		servletContext=sc;
		
		
	}
	
	@POST
	@Path("newWeatherData")
	@Produces(MediaType.APPLICATION_JSON)
	public String testpost (String messaggio) {
		//Faccio partire i thread del virtual object
		Gson inputValuesJson= new Gson();
		
		HashMap <String, Object> inputs= (HashMap <String, Object>)inputValuesJson.fromJson(messaggio, HashMap.class);
		log.info(inputs.toString());
		it.cipi.esercitazione.VirtualObject.OnReceiveData data = new it.cipi.esercitazione.VirtualObject.OnReceiveData(inputs);
		Thread data_t = new Thread(data);
		it.cipi.esercitazione.VirtualObject.UpdateSSSView spreadSpace = new it.cipi.esercitazione.VirtualObject.UpdateSSSView(inputs);
		Thread spread_t = new Thread(spreadSpace);
		data_t.run();
		spread_t.run();
		
		
		return "OK"+messaggio;
	}
	
	
	
	@GET
	@Path("testget")
	@Produces(MediaType.APPLICATION_JSON)
	public String test () {
		String hello ="ciao!!!!!";
		return hello;
	}

}
