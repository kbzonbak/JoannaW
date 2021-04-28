package bbr.b2b.regional.logistic.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;

public class SystemPropertiesProccesor {

	public static void main(String[] args) {
				
		try {
			
			String systemPropertyPath = "bbr.b2b.regional.logistic.properties.SystemProperties";
			
			if (args != null && args.length > 0) {
			
				String serverPath = args[0];				
				String serverOneFilePath = serverPath + args[1];
				String serverTwoFilePath = serverPath + args[2];
				String[] environment = new String[2];
				environment[0] = "default";
				
				// AMBIENTE
				if (args.length > 3 && args[3] != null) 
					environment[1] = args[3];
				else
					environment[1] = "none";			
				
				if (serverOneFilePath != null) {
					PropertyResourceBundle properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(systemPropertyPath);	
									
					// ELIMINA ARCHIVO PREVIO DE CREACIÓN Y CREA UNO NUEVO
					if (deletePreviousScript(serverOneFilePath))
						generateCLIPropertyScript(properties, serverPath, serverOneFilePath, environment, "1");			
				}	
				
				if (serverTwoFilePath != null) {
					PropertyResourceBundle properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(systemPropertyPath);	
									
					// ELIMINA ARCHIVO PREVIO DE CREACIÓN Y CREA UNO NUEVO
					if (deletePreviousScript(serverTwoFilePath))
						generateCLIPropertyScript(properties, serverPath, serverTwoFilePath, environment, "2");			
				}	
							
			}		
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			
		}		
	}	
	
	private static boolean deletePreviousScript(String filePath) {
		try {
			
			System.out.println("Eliminando archivo " + filePath);
			
			Path fileToDeletePath = Paths.get(filePath);
			Files.delete(fileToDeletePath);
			
			return true;
		} catch (NoSuchFileException e) {
			// El archivo no existe				
			return true;
		} catch (IOException e) {
			// Falló la eliminación			
			e.printStackTrace();
			return false;
		}			
	}
	
	private static void generateCLIPropertyScript(PropertyResourceBundle properties, String path, String filePath, String[] environment, String server) {
				
		System.out.println("Creando archivo " + filePath);
		
		Enumeration<String> keys = properties.getKeys(); 
		
		try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){					
			
			while (keys.hasMoreElements()) {
				
				String key = keys.nextElement();
				String keyTmp = key;
				
				// SI TIENE PREFIJO, VALIDAR A QUE SERVIDOR CORRESPONDE 
				if (key.startsWith("server")) {
					
					keyTmp = deletePrefix(key);
									
					// VALIDAR A QUE SERVER					
					if (keyTmp.startsWith(server)) {
						
						// ELIMINA PREFIJOS Y LUEGO PROCESA SEGÚN AMBIENTE
						keyTmp = deletePrefix(keyTmp);						
					}
					else if (hasPrefix(keyTmp)) {
						//System.out.println("Key: " + keyTmp + ", prefijo no válido");
						continue;				
					}
				}				
				
				// SI NO TIENE PREFIJO, ES PARA TODOS LOS AMBIENTES
				if (!hasPrefix(keyTmp)) {
					
					System.out.println("Key: " + keyTmp + " Value: " + properties.getString(key));
					
					String commandLine = getCreateCommandBlockByProperty(keyTmp, properties.getString(key));
					
					writer.append(commandLine);					
				}	
				
				// SI TIENE PREFIJO, VALIDAR QUE CORRESPONDA AL DEFINIDO EN LAS VARIABLES DE AMBIENTE
				else if (keyTmp.startsWith(environment[0]) || keyTmp.startsWith(environment[1])) {
					
					System.out.println("Key: " + deletePrefix(keyTmp) + " Value: " + properties.getString(key));		
					
					String commandLine = getCreateCommandBlockByProperty(deletePrefix(keyTmp), properties.getString(key));
								
					writer.append(commandLine);				
				}		
				
				// SI TIENE PREFIJO api, keycloak SON EXCEPCIONES
				else if (hasPrefix(keyTmp) && (keyTmp.startsWith("keycloak") || keyTmp.startsWith("api"))) {
					
					System.out.println("Key: " + keyTmp + " Value: " + properties.getString(key));		
					
					String commandLine = getCreateCommandBlockByProperty(keyTmp, properties.getString(key));
								
					writer.append(commandLine);				
				}	
			}				
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
	}	
	
	private static String getCreateCommandBlockByProperty(String propertyName, String propertyValue) {
			
		StringBuilder commandBuilder = new StringBuilder();
		
		commandBuilder.append("if (outcome != failed) of /system-property=" + propertyName + ":read-resource()\n");
		commandBuilder.append("\techo Se actualiza propiedad " + propertyName + " con valor '" + propertyValue + "'\n");
		commandBuilder.append("\t/system-property=" + propertyName + ":write-attribute(name=value,value=\"" + propertyValue + "\")\n");
		commandBuilder.append("else\n");
		commandBuilder.append("\techo Se agrega propiedad " + propertyName + " con valor '" + propertyValue + "'\n");
		commandBuilder.append("\t/system-property=" + propertyName + ":add(value=\"" + propertyValue + "\")\n");
		commandBuilder.append("end-if\n\n");
			
		
		return commandBuilder.toString();
	}	
	
	private static String deletePrefix(String property) {
		return property.substring(property.indexOf(".") + 1);		
	}
	
	private static Boolean hasPrefix(String property) {
		if (property.indexOf(".") > 0)
			return true;
		else
			return false;		
	}	
}
