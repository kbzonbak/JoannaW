package bbr.b2b.regional.logistic.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

import sun.misc.BASE64Decoder;

public class Base64Utils {

	// LoggingUtil loggingUtil = LoggingUtil.getInstance();

	private static Base64Utils _instance;

	// For lazy initialization
	public static synchronized Base64Utils getInstance() {
		if (_instance == null) {
			_instance = new Base64Utils();
		}
		return _instance;
	}

	// Constructor
	private Base64Utils() {

	}

	public File saveToFile(String stringbase64, String folder, String filename) throws OperationFailedException {

		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i > 0) {
			extension = filename.substring(i + 1);
		}

		File outputfile = null;
		BufferedImage image = null;
		byte[] imageByte;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(stringbase64);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();

			outputfile = new File(folder + File.separatorChar + filename);
			ImageIO.write(image, extension, outputfile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al generar imagen");
		}
		return outputfile;
	}

}
