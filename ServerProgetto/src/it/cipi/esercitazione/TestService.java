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

@Path("/testclass")
public class TestService {

	private static Logger log;
	private static ServletContext servletContext;
	
	static void init(ServletContext sc){
		log= Logger.getRootLogger();
		servletContext=sc;
		
	}
	@GET
	@Path("testget")
	@Produces(MediaType.APPLICATION_JSON)
	public String test () {
		String hello ="ciao!!!!!";
		return hello;
	}
	
	@GET
	@Path("testgetqueryparam")
	@Produces(MediaType.APPLICATION_JSON)
	public String testQuery (@QueryParam("colore") String colore) {
		String hello ="Hai passato il colore: "+colore;
		return hello;
	}
	
	@GET
	@Path("testgetpathparam/{forma}")
	@Produces(MediaType.APPLICATION_JSON)
	public String testPath (@PathParam("forma") String forma) {
		String hello ="Hai passato la forma: "+forma;
		return hello;
	}
	
	@POST
	@Path("testpost")
	public String testpost (String messaggio) {
		String hello ="Hai passato il messaggio: "+messaggio;
		Gson inputValuesJson= new Gson();
		//Prendiamo valori delle properties
		Properties prop = (Properties) servletContext.getAttribute("Properties");
		
		
		//Esempio di parsing json, mappa json in una hash
		HashMap <String, Object> inputs= (HashMap <String, Object>)inputValuesJson.fromJson(messaggio, HashMap.class);
		log.info(inputs.toString());
		
		// ------------ PROVARE A FARE TESTIG e scrivere su log risultati
		return hello;
	}
	
}
