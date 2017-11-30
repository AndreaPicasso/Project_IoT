package it.cipi.esercitazione.VirtualObject;

import java.util.HashMap;

public class UpdateSSSView implements Runnable {
	 private HashMap <String, Object> data;

	 

	public UpdateSSSView(HashMap<String, Object> data) {
		super();
		this.data = data;
	}



	@Override
	public void run() {
		
	}

}

/*
 * MAVEN
 * 
 * simone@simone:~/Scrivania/apache-maven-3.5.2/bin$ ./mvn install:install-file -Dfile=sdk-0.1.25-jar-with-dependencies.jar -DgroupId=it.sss -DartifactId=SDK 

 * 
 */