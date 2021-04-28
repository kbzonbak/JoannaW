package bbr.b2b.logistic.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/*
 * en las propiedades del m�dulo deben, existir las siguentes lineas, donde 
 * 
 * VALUEn, representa los CODIGOS o ID de la tabla ordertype, dependiendo del valor VALUETYPE
 * 
 * ORDERTYPEBBR_REG 	: VALUE1, VALUE2, VALUEn
 * ORDERTYPEBBR_VEV_DDC : VALUE1, VALUE2, VALUEn
 * ORDERTYPEBBR_VEV_DVR : VALUE1, VALUE2, VALUEn
 * ORDERTYPEBBR_VEV_DVO : VALUE1, VALUE2, VALUEn
 * ORDERTYPEBBR_VEV_RBP : VALUE1, VALUE2, VALUEn
 * ORDERTYPEBBR_NA		: VALUE1, VALUE2, VALUEn
 * */
public class OrderTypeBBRUtils {	
	
	private static Logger logger = Logger.getLogger(OrderTypeBBRUtils.class);

	private static OrderTypeBBRUtils _instance;

	/**********************/
	//valor que indica si los valores en el property es el ID o el CODE de ordertype de B2B. 
	//Se sugiere siempre usar CODE, pero algunos B2B, no manejan ese campo 
	
	private final String VALUETYPE = "CODE";
	//private final String VALUETYPE = "ID";
	/**********************/
	
	public static final Map<Long, String> otypeidbbr = new HashMap<Long, String>();
	public static final Map<String, String> otypecodebbr = new HashMap<String, String>();

	
	public static final String[] ordertypebbr = new String[] { "REG", "VEV_DDC", "VEV_DVR", "VEV_DVO", "VEV_RBP", "NA" };

	// For lazy initialization
	public static synchronized OrderTypeBBRUtils getInstance() {
		if (_instance == null) {
			_instance = new OrderTypeBBRUtils();
		}
		return _instance;
	}

	// Constructor
	private OrderTypeBBRUtils() {
		fillOrderTypesBBR();
	}

	public String getOrderTypeBBRByID(Long ordertypeid) {
		if("CODE".equals(this.VALUETYPE)){
			logger.error("Se tiene configurado los c�digos. Se debe usar el método getOrderTypeBBRByCode");
			return "";
		}
			
		if (!otypeidbbr.containsKey(ordertypeid))
			return "NA";
		else
			return otypeidbbr.get(ordertypeid);
	}

	public String getOrderTypeBBRByCode(String ordertypecode) {
		if("ID".equals(this.VALUETYPE)){
			logger.error("Se tiene configurado los ID. Se debe usar el método getOrderTypeBBRByID");
			return "";
		}
		if (!otypecodebbr.containsKey(ordertypecode))
			return "NA";
		else
			return otypecodebbr.get(ordertypecode);
	}
	
	private void fillOrderTypesBBR() {
		int count = 0;
		for (int i = 0; i < ordertypebbr.length; i++) {
			String otretail = B2BSystemPropertiesUtil.getProperty("ORDERTYPEBBR_" + ordertypebbr[i]);
			String[] otretailarray = otretail.split(",");
			for (int j = 0; j < otretailarray.length; j++) {
				try {
					if( otretailarray[j].length() > 0 ){
						if("ID".equals(this.VALUETYPE))
							otypeidbbr.put(Long.valueOf(otretailarray[j].trim()), ordertypebbr[i].replaceAll("_", " ")); // quita los _ del nombre
						if("CODE".equals(this.VALUETYPE))
							otypecodebbr.put(otretailarray[j].trim(), ordertypebbr[i].replaceAll("_", " ")); // quita los _ del nombre
						
						count++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		logger.info("Completando tipos de OC BBR (" + count + " encontrados)");

	}
}
