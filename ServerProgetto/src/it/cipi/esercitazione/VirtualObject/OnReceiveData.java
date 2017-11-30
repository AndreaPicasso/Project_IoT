package it.cipi.esercitazione.VirtualObject;

import java.util.HashMap;

import com.google.gson.Gson;

public class OnReceiveData implements Runnable {
	 private HashMap <String, Object> data;
	 
	

	public OnReceiveData(HashMap<String, Object> inputs) {
		super();
		this.data = inputs;
		}



	@Override
	public void run() {
		
	}

}
