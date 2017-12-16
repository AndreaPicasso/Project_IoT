package it.cipi.esercitazione;
import com.google.gson.annotations.SerializedName;


public class CameraInfo {



	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("x")
	private String x;

	@SerializedName("y")
	private String y;

	@SerializedName("url")
	private String url;
	
	@SerializedName("img")
	public String img;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public String getUrl() {
		return url;
	}
	
}
