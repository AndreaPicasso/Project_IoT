package it.cipi.esercitazione.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class ConfigurationFileUtil {
	private static Logger log = null;
	public static String basePath = null;
	public static String SERVER_ADDR=null;
	public static String THIS_SERVER_ADDR=null;
	
	/*
	 * Il file di properties deve essere letto e deve essere reso disponibile alla 
	 * servlet quando questa si attiva
	 */
	public static void readConfigFile(ServletContextEvent sce) {
		String fileName = null;
		Properties props = new Properties();
		log = Logger.getRootLogger();
		ServletContext sc = sce.getServletContext();
		String warName = sc.getContextPath().length() == 0 ? "ROOT" : sc.getContextPath().substring(1);
		try {
			/*
			 * Carichiamo file di properties, questo modo lo fa in default
			 * la classe Properties props = new.. serve proprio per fare questo in automatico
			 * il file di properties è fatto come <nomeVariabile> = <valore> \n
			 * dopodichè deve essere memorizzato come <nomefile>.conf
			 */
			fileName = System.getProperty("catalina.home") + "/conf/" + warName + ".conf"; 
			log.info("Opening conf file " + fileName);
			FileInputStream in = new FileInputStream(fileName);
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			log.error("Property file not found: " + fileName + "\n" + e.getMessage());
			log.error(e);
		} catch (IOException e) {
			log.error("Property file IOException: " + e.getMessage());
			log.error(e);
		}
		//Settiamo delle variabili globali definite all'interno del contesto della servlet
		// definite come <nomevariabile, valore>
		sc.setAttribute("properties", props);
		basePath=THIS_SERVER_ADDR +"/"+warName;
		log.info("Server up and running at: " + basePath);
		
	}
}