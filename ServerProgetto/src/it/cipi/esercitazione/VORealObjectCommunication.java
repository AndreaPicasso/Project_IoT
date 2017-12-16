package it.cipi.esercitazione;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import it.cipi.esercitazione.VirtualObject.OnReceiveData;
import it.cipi.esercitazione.VirtualObject.SSSConnection;
import it.cipi.esercitazione.VirtualObject.UpdateSSSView;
import it.cipi.esercitazione.CameraInfo;

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
		/* JSON example:
		 * [{"id":"1","name":"Piazza Savonarola","x":"8.95064","y":"44.4022","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/1.jpg","img":"/9j/4AAQSk"},{"id":"2","name":"Piazza Verdi","x":"8.94683","y":"44.4062","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/2.jpg","img":"/9j/4AAQSk"},{"id":"15","name":"Piazza Dante","x":"8.93586","y":"44.4053","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/15.jpg","img":"/9j/4AAQSk"},{"id":"19","name":"P.le Atleti Azzurri","x":"8.9514","y":"44.4157","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/19.jpg","img":"/9j/4AAQSk"},{"id":"20","name":"Piazza Acquaverde","x":"8.92231","y":"44.4166","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/20.jpg","img":"/9j/4AAQSk"},{"id":"21","name":"Canevari/Montegrappa","x":"8.95086","y":"44.4085","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/21.jpg","img":"/9j/4AAQSk"},{"id":"22","name":"Via Merano Poch","x":"8.84437","y":"44.426","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/22.jpg","img":"/9j/4AAQSk"},{"id":"23","name":"Iori Canepari Rossini","x":"8.89304","y":"44.4347","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/23.jpg","img":"/9j/4AAQSk"},{"id":"24","name":"DeStefanis/Sardegna","x":"8.95673","y":"44.415","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/24.jpg","img":"/9j/4AAQSk"},{"id":"30","name":"Cornigliano Pieragostini","x":"8.88087","y":"44.415","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/30.jpg","img":"/9j/4AAQSk"},{"id":"31","name":"Piazza Annunziata","x":"8.92865","y":"44.4137","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/31bis.jpg","img":"/9j/4AAQSk"},{"id":"33","name":"Cantore","x":"8.89956","y":"44.4105","url":"http://mobilitypoint.comune.genova.it/WebCams/Images/33.jpg","img":"/9j/4AAQSk"}]
		 * 
		 */
		System.out.println("Data Arrived");
		Gson inputValuesJson= new Gson();
		CameraInfo[] cameras = inputValuesJson.fromJson(data, CameraInfo[].class);
		HashMap <String, Object> inputs= new HashMap<String, Object>();
		for(CameraInfo camera: cameras) {
			inputs.put(camera.getId(), camera);
		}
		

		OnReceiveData onRecieveNewData =new OnReceiveData(inputs);
		SSSConnection SSSConnection =new SSSConnection(inputs);
		Thread t_sCon=new Thread(SSSConnection);
		t_sCon.start();
		Thread t_rec=new Thread(onRecieveNewData);
		t_rec.start();
		
		return"recived";
	}
	
}
