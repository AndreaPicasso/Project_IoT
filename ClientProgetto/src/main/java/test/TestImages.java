package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

public class TestImages {
	public static void main(String args[]) {
		BufferedImage img;
		try {
			URL url = new URL("http://mobilitypoint.comune.genova.it/WebCams/Images/1.jpg");
			img = ImageIO.read(url);
			String encoded = Base64.getEncoder().encodeToString(imageToByteArray(img));
			System.out.println(encoded);
			byte[] decoded = Base64.getDecoder().decode(encoded);

			// convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(decoded);
			BufferedImage bImageFromConvert = ImageIO.read(in);

			ImageIO.write(bImageFromConvert, "jpg", new File("C:\\Users\\Giancarlo\\Documents\\news.jpg"));

		} catch (IOException e) {
			e.printStackTrace();
		}   
		System.out.println("Done");

	}

	public static byte[] imageToByteArray(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		return baos.toByteArray();
	}
}
