package it.cipi.esercitazione;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import it.cipi.esercitazione.VORealObjectCommunication;
import it.cipi.esercitazione.VirtualObject.VORegistration;
import it.cipi.esercitazione.utils.ConfigurationFileUtil;

@WebListener
public class ServiceListener implements ServletContextListener{
	

	private static Logger log = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// Quando stoppiamo la servlet dobbiamo UNREGISTER VO: toglierci dai VO registrati
		
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		/*
		ServletContextEvent evt: qui si hanno un sacco di variabili, parametri ...
		Che contengono info sul contesto della servlet
		*/
		
		System.out.println("TEST SERVICE con runtime annotations STARTED!!!!");
		
		ServletContext sc = evt.getServletContext();	//Prendi il contesto della servlet
		
		
		//Prendi il nome dell'archivio
		String warName = sc.getContextPath().length() == 0 ? "ROOT" : sc.getContextPath().substring(1);
		
		
		//------ LOGGING ---------------
		/*
		 * In locale abbiamo la nostra bella console personale, su un server di amazon
		 * o qualcosa dove girano un sacco di applicazione no
		 * 
		 * Questa cosa va a scrivere dentro la mia console (a seconda di dove lo sto
		 * facendo partire) sulla mia console, chiaramente è utile solo in locale
		 * 
		 * Serve una libreria chiamata log4j usatissima
		 *  - Scrive i log in maniera ordinata
		 *  - permette i scrivere con pattern: date, definire classi di log ecc
		 */
		//Vogliamo mettere i log su una console:
		ConsoleAppender console = new ConsoleAppender(); //create appender
		// Layout di qualunque log: data - classe - messaggio
		String PATTERN = "%d{dd MMM yyyy HH:mm:ss} %5p %l %m%n";
		console.setLayout(new PatternLayout(PATTERN)); 
		//Possiamo scrivere i log su vari livelli Level.{debug, error, fatal,trace,...}
		//Seleziona il livello dei log da cui stampare:(fino a quale livello stampo?)
		//Magari voglio stampare solo errori fatali, o magari al momento del deploy Level.off
		//in modo da non stamparne nessuno
		console.setThreshold(Level.DEBUG);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
		
		
		
		
		//Logger che scrive invece dentro dei file
		//RollingFileAppender: i file di log non vengono scritti tutti dentro un file
		//ma viene fatta una rotazione sui file
		RollingFileAppender rfa = new RollingFileAppender();
		//Setta il nome dei file di log es. [nome_war]_log_1.txt
		rfa.setName(warName);
		rfa.setImmediateFlush(true);
		//Dove salvare i file
		rfa.setFile(System.getProperty("catalina.home") + "/logs/"+warName+".log");
		rfa.setLayout(new PatternLayout("%d{dd MMM yyyy HH:mm:ss} %5p %l %m%n"));
		rfa.setThreshold(Level.DEBUG);
		rfa.setAppend(true);
		//Tronca i file di log quando vengono raggiunti 10M
		rfa.setMaxFileSize("10MB");
		rfa.setMaxBackupIndex(50);
		rfa.activateOptions();
		Logger.getRootLogger().addAppender(rfa);
		
		log = Logger.getRootLogger();
		//Leggi i file di configurazione -> 
		//funzione statica di nostra classe in it.cipi.esercitazione.utils
		ConfigurationFileUtil.readConfigFile(evt);
		//Apri metodo di configurazione interno della mia servlet passando eventualemte
		//dei parametri
		it.cipi.esercitazione.VORealObjectCommunication.init(sc);
		

		VORegistration reg = new VORegistration();
		reg.register();
		
		
	}

}
