package bbr.esb.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import bbr.common.adtclasses.exceptions.OperationFailedException;

public class Constants {

	private static Constants _instance;

	static private Properties properties = null;

	// PARA OBTENER EL NOMBRE DEL EAR
	static private String propertyEARname = "";

	// PARA OBTENER LA RUTA DEL ARCHIVO DE PROPERTIES
	static private String propertyfilepath = "";

	// PARA OBTENER LA RUTA DE DESCARGA DE ARCHIVOS
	static private String downloadpath = "";

	public static String[] getContactsWeb() throws OperationFailedException {
		String[] mails = getProperties().getProperty("contactsWeb").split(",");
		return mails;
	}

	// Constructor
	public static synchronized Constants getInstance() {
		if (_instance == null) {
			_instance = new Constants();
		}
		return _instance;
	}

	public static String getMailSession() throws OperationFailedException {
		String mailsession = getProperties().getProperty("mailsession");
		return mailsession;
	}

	/**
	 * Lee las properties de Constants.properties. Si el archivo ha cambiado, vuelve a cargar las properties, de lo
	 * contrario, devuelve el objeto almacenado previamente
	 * 
	 * @return el objeto Properties que almacena la propiedades cargadas del archivo Constants.properties
	 * @throws OperationFailedException
	 */
	private static Properties getProperties() throws OperationFailedException {
		if (properties == null) {
			try {
				Properties auxproperties = new Properties();

				if (propertyfilepath == null)
					throw new OperationFailedException("Path de archivo de properties not set");
				File file = new File(propertyfilepath);
				FileInputStream fis = new FileInputStream(file);
				long lastmodified = file.lastModified();
				auxproperties.load(fis);
				// Agregar a las properties, la fecha de la última modificación del archivo de properties
				String lastModifiedStr = Long.toString(lastmodified);
				auxproperties.setProperty("lastModified", lastModifiedStr);
				properties = auxproperties;
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el archivo de properties: " + propertyfilepath);
				throw new OperationFailedException("No se encontró el archivo de properties: " + propertyfilepath, e);
			} catch (IOException e) {
				System.out.println("No se puede leer el archivo de properties: " + propertyfilepath);
				throw new OperationFailedException("No se puede leer el archivo de properties: " + propertyfilepath, e);
			}
		} else {
			// Chequear si hay cambios en el archivo de configuración, comparando
			// las fechas de modificación
			String lastmodifiedStr = properties.getProperty("lastModified");
			long lastmodified = Long.parseLong(lastmodifiedStr);
			File file = new File(propertyfilepath);
			if (file.exists() && file.lastModified() > lastmodified) {
				try {
					// Recargar el archivo de properties
					FileInputStream fis = new FileInputStream(file);
					long newlastmodified = file.lastModified();
					Properties auxproperties = new Properties();
					auxproperties.load(fis);
					// Agregar a las properties, la fecha de la última modificación del archivo de properties
					String newlastModifiedStr = Long.toString(newlastmodified);
					auxproperties.setProperty("lastModified", newlastModifiedStr);
					properties = auxproperties;
					System.out.println("-------------------------------------------------------------");
					System.out.println("Actualizando archivo de configuración constants.properties");
					System.out.println("claveencriptada : " + Constants.getPropertyValue("claveencriptada"));
					System.out.println("clavenormalizada : " + Constants.getPropertyValue("clavenormalizada"));
					System.out.println("intentos : " + Constants.getPropertyValue("intentos"));
					System.out.println("-------------------------------------------------------------");
				} catch (FileNotFoundException e) {
					System.out.println("No se encontró el archivo de properties: " + propertyfilepath);
					throw new OperationFailedException("No se encontró el archivo de properties: " + propertyfilepath, e);
				} catch (IOException e) {
					System.out.println("No se puede leer el archivo de properties: " + propertyfilepath);
					throw new OperationFailedException("No se puede leer el archivo de properties: " + propertyfilepath, e);
				}
			}
		}
		return properties;
	}

	public static String getPropertyEarName() {
		return propertyEARname;
	}

	public static String getPropertyfilepath() {
		return propertyfilepath;
	}

	public static String getPropertyValue(String propertyname) throws OperationFailedException {
		String result = getProperties().getProperty(propertyname);
		return result;
	}

	// ----------------------------------------------------------------------------------------------

	public static void setPropertyEarName(String earname) {
		propertyEARname = earname;
	}

	public static void setPropertyfilepath(String string) {
		propertyfilepath = string;
	}

	public static String getDownloadpath() {
		return downloadpath;
	}

	public static void setDownloadpath(String string) {
		downloadpath = string;
	}

	private Constants() {
		// getParams("bbr.common.util.constants");
	}
}
