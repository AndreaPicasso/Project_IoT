package it.cipi.esercitazione;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import it.cipi.esercitazione.VirtualObject.OnReceiveData;
import it.cipi.esercitazione.VirtualObject.UpdateSSSView;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/VORealObjectCommunication")
public class VORealObjectCommunication {

	private static Logger log;
	private static ServletContext servletContext;
	
	static void init(ServletContext sc){
		log= Logger.getRootLogger();
		servletContext=sc;
		
	}
	@GET
	@Path("getinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getinfo () {
		String info ="HashMap<String, Object> inputs";
		return info ;
	}
	
	
	@POST
	@Path("newdata")
	public String newdata (String data) {
		Gson inputValuesJson= new Gson();
		HashMap <String, Object> inputs= (HashMap <String, Object>)inputValuesJson.fromJson(data, HashMap.class);
		OnReceiveData onRecieveNewData =new OnReceiveData(inputs);
		UpdateSSSView UpdateSSSView =new UpdateSSSView(inputs);
		Thread t_sCon=new Thread(UpdateSSSView);
		t_sCon.start();
		Thread t_rec=new Thread(onRecieveNewData);
		t_rec.start();
		
		return"";
	}
	
}
