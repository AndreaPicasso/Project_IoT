package it.cipi.esercitazione;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/service")
public class Service {

	private static Logger log;
	private static ServletContext servletContext;
	
	static void init(ServletContext sc){
		log= Logger.getRootLogger();
		servletContext=sc;
		
	}
	@GET
	@Path("getinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String test () {
		String hello ="ciao!!!!!";
		return hello;
	}
	
	
	@POST
	@Path("newdata")
	public String newdata (String data) {
		OnRecieveNewData onRecieveNewData =new OnRecieveNewData(data);
		SSSConnection SSSConnection= new SSSConnection(data);
		Thread t_sCon=new Thread(SSSConnection);
		t_sCon.start();
		Thread t_rec=new Thread(onRecieveNewData);
		t_rec.start();
//		String hello ="Hai passato il messaggio: "+messaggio;
//		Gson inputValuesJson= new Gson();
//		//Prendiamo valori delle properties
//		Properties prop = (Properties) servletContext.getAttribute("Properties");
//		
//		
//		//Esempio di parsing json, mappa json in una hash
//		HashMap <String, Object> inputs= (HashMap <String, Object>)inputValuesJson.fromJson(messaggio, HashMap.class);
//		log.info(inputs.toString());
//		
//		// ------------ PROVARE A FARE TESTIG e scrivere su log risultati
//		return hello;
		return"";
	}
	
}
