package bbr.b2b.logistic.utils;

public class B2BSystemPropertiesUtil {

	public static String getProperty(String property){
		String endpoint = System.getProperty(property);
		if (endpoint == null){
			System.out.println("***** ERROR al cargar desde el SystemProperties el valor de "+ property +" *******");
		}
		return endpoint;
	}
	
}
