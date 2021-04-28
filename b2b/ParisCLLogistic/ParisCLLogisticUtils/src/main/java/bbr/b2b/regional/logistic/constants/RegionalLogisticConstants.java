package bbr.b2b.regional.logistic.constants;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

public class RegionalLogisticConstants {//extends Constants {

	private static RegionalLogisticConstants _instance;
	
	public static final String TITLESKPIVEVCD = "\"Codigo_Tienda\",\"Nombre_Tienda\",\"Codigo_Proveedor\",\"Razon_Social\",\"Numero_Orden\",\"Numero_Solicitud\",\"Codigo_Depto\",\"Departamento\",\"Estado_de_Orden\",\"Fecha_Envio\",\"FPI\",\"MES_FPI\",\"Año_FPI\",\"Fecha_RM_Estado\",\"SKU_Paris\",\"Evaluacion\",\"Descripcion_SKU\",\"Unidades_Solicitadas\",\"Unidades_En_Despacho\",\"Unidades_Entregadas\",\"Unidades_Pendientes\"";
	public static final String TITLESKPIVEVPD = "\"Codigo_Tienda\",\"Nombre_Tienda\",\"Codigo_Proveedor\",\"Razon_Social\",\"Numero_Orden\",\"Numero_Solicitud\",\"Codigo_Depto.\",\"Departamento\",\"Estado_de_Orden\",\"Estado_de_ultimo_Despacho\",\"Fecha_Envio\",\"Fecha_Compromiso\",\"MES_Compromiso\",\"Año_Compromiso\",\"Fecha_Reprogramada\",\"Fecha_Cambio_Estado\",\"Evaluacion\",\"SKU_Paris\",\"Descripcion_SKU\",\"Unidades_Solicitadas\",\"Unidades_En_Despacho\",\"Unidades_Entregadas\",\"Unidades_Pendientes\"";
	
	// Constructor
	public static synchronized RegionalLogisticConstants getInstance() {
		if (_instance == null) {
			_instance = new RegionalLogisticConstants();
//			String propertiesFilePath = _instance.getClass().getResource("/properties/").getPath();

//			String path = propertiesFilePath + File.separator + "RegionalLogisticConstants.properties";
//			_instance.setPropertyfilepath(path);

//			String orderxsdpath = propertiesFilePath + File.separator + "qorden.xsd";
//			String orderPredxsdpath = propertiesFilePath + File.separator + "qorden_PRED.xsd";
//			String vendorxsdpath = propertiesFilePath + File.separator + "VENDOR_DWH.xsd";
//			String solicitudSOAxsdpath = propertiesFilePath + File.separator + "SolicitudOrdenes.xsd";
//			String solicitudProveedoresXSDpath = propertiesFilePath + File.separator + "SolicitudProveedoresOrdenesPendientes_1.1.xsd";
//			String receptionxsdpath = propertiesFilePath + File.separator + "ReceptionData.xsd";
//			String notificacionOcXSDpath = propertiesFilePath + File.separator + "NotificacionReciboOrden_1.0.xsd";			
//			String inventarioProvXSDpath = propertiesFilePath + File.separator + "Inventario_Prov_1.1.xsd"; //MHE CU 149
			
//			_instance.setOrderPredXSDPath(orderPredxsdpath);
//			_instance.setOrderXSDPath(orderxsdpath);
//			_instance.setVendorXSDPath(vendorxsdpath);
//			_instance.setSolicitudSOAXSDPath(solicitudSOAxsdpath);
//			_instance.setSOASolicitudProveedoresXSDPath(solicitudProveedoresXSDpath);
//			_instance.setReceptionXSDPath(receptionxsdpath);
//			_instance.setSOANotificacionOcXSDPath(notificacionOcXSDpath);
//			_instance.setInventarioProvXSDpath(inventarioProvXSDpath); //MHE CU 149
		}
		return _instance;
	}

//	private String orderXSDpath = null;
//	private String orderPredXSDpath = null;
//	private String vendorXSDpath = null;
//	private String solicitudSOAXSDpath = null;
//	private String solicitudProveedoresXSDpath = null;
//	private String receptionXSDpath = null;
//	private String notificacionOcXSDpath = null;
//	private String inventarioProvXSDpath = null; //MHE CU 149
	
	public static final String CHARSET_CSV	= "UTF-16LE";


	public String getMailSender() throws OperationFailedException {
		if (getProduccion())
			return getPropertyValueAsString("MAIL_SENDER_PROD");
		else
			return getPropertyValueAsString("MAIL_SENDER_TEST");
	}

	public boolean getProduccion() {
		return Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("Produccion"));
	}

	// ------------------------------

	public String getFILE_TRANSFER_PATH() throws OperationFailedException {
		return getPropertyValueAsString("FILE_TRANSFER_PATH");
	}

	public String getATTACHMENTS_PATH() throws OperationFailedException {
		return getPropertyValueAsString("ATTACHMENTS_PATH");
	}
	
	public String getOS() throws OperationFailedException {
		return getPropertyValueAsString("OS");
	}

	public String getMAIL_SESSION() throws OperationFailedException {
		return getPropertyValueAsString("MAIL_SESSION");
	}	
	
	public String[] getDEVELOPER_MAIL_ERROR() throws OperationFailedException {
		return getPropertyValueAsString("DEVELOPER_MAIL_ERROR").split(",");
	}

	public String[] getREASSIGNMENT_MAIL() throws OperationFailedException {
		if (getProduccion())
			return getPropertyValueAsString("REASSIGNMENT_MAIL_PROD").split(",");
		else
			return getPropertyValueAsString("REASSIGNMENT_MAIL_TEST").split(",");
	}
	
	public String[] getDELETEDATING_MAIL() throws OperationFailedException {
		if (getProduccion())
			return getPropertyValueAsString("DELETEDATING_MAIL_PROD").split(",");
		else
			return getPropertyValueAsString("DELETEDATING_MAIL_TEST").split(",");
	}
	
	public String[] getMDMMailCC() throws OperationFailedException {
		if (getProduccion())
			return getPropertyValueAsString("MDM_MAIL_CC_PROD").split(",");
		else
			return getPropertyValueAsString("MDM_MAIL_CC_TEST").split(",");
	}
	
	public String[] getPL_PENDING_TAXDOCUMENTS_MAIL_CC() throws OperationFailedException {
		if (getProduccion())
			return getPropertyValueAsString("PL_PENDING_TAXDOCUMENTS_MAIL_CC_PROD").split(",");
		else
			return getPropertyValueAsString("PL_PENDING_TAXDOCUMENTS_MAIL_CC_TEST").split(",");
	}
	
	public int getINT_TODOS() throws OperationFailedException {
		return getPropertyValueAsInt("INT_TODOS");
	}

	public int getUSERTYPE_PROVIDER() throws OperationFailedException {
		return getPropertyValueAsInt("USERTYPE_PROVIDER");
	}

	public int getUSERTYPE_RETAILER() throws OperationFailedException {
		return getPropertyValueAsInt("USERTYPE_RETAILER");
	}

	public int getUSERTYPE_BBR() throws OperationFailedException {
		return getPropertyValueAsInt("USERTYPE_BBR");
	}


	public int getMAX_NUMBER_OF_ORDERS() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_NUMBER_OF_ORDERS");
	}
	
	public int getMAX_RECEPTIONS() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_RECEPTIONS");
	}

	public int getMAX_NUMBER_OF_ROWS() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_NUMBER_OF_ROWS");
	}
	
	public int getMAX_NUMBER_OF_CSV_ROWS() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_NUMBER_OF_CSV_ROWS");
	}

	public int getMAX_NUMBER_OF_ROWS_PACKINGLIST() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_NUMBER_OF_ROWS_PACKINGLIST");
	}
	
	public int getMAX_DAYS_VEV_REPORT() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_DAYS_VEV_REPORT");
	}
	
	public int getMAX_ROWS_INVOICE_PENDING_REPORT() throws OperationFailedException {
		return getPropertyValueAsInt("MAX_ROWS_INVOICE_PENDING_REPORT");
	}
		
	public boolean getSENDMAILTESTCSUD() throws OperationFailedException {
		return getPropertyValueAsBoolean("SENDMAILTESTCSUD");
	}
	
	public boolean getSTOP_CHECK_STATE() throws OperationFailedException {
		return getPropertyValueAsBoolean("STOP_CHECK_STATE");
	}
	
	public boolean getLOADING_RECEPTIONS() throws OperationFailedException {
		return getPropertyValueAsBoolean("LOADING_RECEPTIONS");
	}

	public String getPENDINGMESSAGECOUNT() throws OperationFailedException {
		return getPropertyValueAsString("PENDINGMESSAGECOUNT");
	}
	
	public String getPENDINGMAILCOUNT() throws OperationFailedException {
		return getPropertyValueAsString("PENDINGMAILCOUNT");
	}
	
	public String getCOUNTRYCODE() throws OperationFailedException {
		return getPropertyValueAsString("COUNTRYCODE");
	}

	public String getBACKUP_MSG_PATH() throws OperationFailedException{
		return getPropertyValueAsString("BACKUP_MSG_PATH");
	}
	
	public String getBUSINESSAREA() throws OperationFailedException {
		return getPropertyValueAsString("BUSINESSAREA");
	}
	
	public String getRECEPTION_FILE_PATH() throws OperationFailedException {
		return getPropertyValueAsString("RECEPTION_FILE_PATH");
	}
	
	public String getRECEPTION_FILE_ERROR_PATH() throws OperationFailedException {
		return getPropertyValueAsString("RECEPTION_FILE_ERROR_PATH");
	}
	
	public String getRECEPTION_FILE_BACK_PATH() throws OperationFailedException {
		return getPropertyValueAsString("RECEPTION_FILE_BACK_PATH");
	}	
	
	public String getCOUNTRY_ID() throws OperationFailedException {
		return getPropertyValueAsString("COUNTRY_ID");
	}
	
	public String getINTERFACE_TYPE() throws OperationFailedException {
		return getPropertyValueAsString("INTERFACE_TYPE");
	}

	
	public int getFREEHOURSTOASSIGN() throws OperationFailedException {
		return getPropertyValueAsInt("FREEHOURSTOASSIGN");
	}
	public Long getVendorCodeBase() throws OperationFailedException{
		return getPropertyValueAsLong("VENDORCODEBASE");
	}
	public Long getVendorCodeLength() throws OperationFailedException{
		return getPropertyValueAsLong("VENDORCODELENGTH");
	}
	
	public String getASN_FILE_PATH() throws OperationFailedException{
		return getPropertyValueAsString("ASN_FILE_PATH");
	}
	
	public boolean getSTOP_MAKE_RANKING() throws OperationFailedException {
		return getPropertyValueAsBoolean("STOP_MAKE_RANKING");
	}
	
	public boolean getSTOP_MAKE_FILLRATE() throws OperationFailedException {
		return getPropertyValueAsBoolean("STOP_MAKE_FILLRATE");
	}
	
	public String getTMP_FILE_PATH() throws OperationFailedException {
		return getPropertyValueAsString("TMP_FILE_PATH");
	}	
	
	public boolean getSTOP_DATING_REPORT() throws OperationFailedException {
		return getPropertyValueAsBoolean("STOP_DATING_REPORT");
	}
	
	public Boolean get_PROXY() throws OperationFailedException{
		return getPropertyValueAsBoolean("PROXY");
	}
	public String getHTTP_PROXY_IP() throws OperationFailedException{
		return getPropertyValueAsString("HTTP_PROXY_IP");
	}
	public int getHTTP_PROXY_PORT() throws OperationFailedException {
		return getPropertyValueAsInt("HTTP_PROXY_PORT");
	}
	public String getUPLOADCHILEXPRESSSTATEBACKUP() throws OperationFailedException {
		return getPropertyValueAsString("UPLOADCHILEXPRESSSTATEBACKUP");
	}
	public String getUPLOADCHILEXPRESSSTATE() throws OperationFailedException {
		return getPropertyValueAsString("UPLOADCHILEXPRESSSTATE");
	}

	
	public String getACTAENTREGA_EXTENSION_IMAGE() throws OperationFailedException {
		return getPropertyValueAsString("ACTAENTREGA_EXTENSION_IMAGE");
	}

	public String getACTAENTREGA_FOLDER_IMAGE() throws OperationFailedException {
		return getPropertyValueAsString("ACTAENTREGA_FOLDER_IMAGE");
	}	
	
	public String getHourToNotificationsDetele() throws OperationFailedException {
		return getPropertyValueAsString("HOUR");
	}
	
	public String getMaxCourierPickUpTime()  throws OperationFailedException {
		return getPropertyValueAsString("MAX_COURIER_PICKUP_TIME");
	}
	
	public int getVEV_SUMMARY_NOTIFICATION_BEGINS()  throws OperationFailedException {
		return getPropertyValueAsInt("VEV_SUMMARY_NOTIFICATION_BEGINS");
	}
	
	public int getVEV_SUMMARY_NOTIFICATION_ENDS() throws OperationFailedException {
		return getPropertyValueAsInt("VEV_SUMMARY_NOTIFICATION_ENDS");
	}
	
	public String getERROR_MAIL_TO() throws OperationFailedException {
		return getPropertyValueAsString("ERROR_MAIL_TO");
	}

	public String getMOBILE_RETAILER_CODE() throws OperationFailedException {
		return getPropertyValueAsString("MOBILE_RETAILER_CODE");
	}
	
	public String getMOBILE_RETAILER_NAME() throws OperationFailedException {
		return getPropertyValueAsString("MOBILE_RETAILER_NAME");
	}
	
	private String getPropertyValueAsString(String param) {
		return B2BSystemPropertiesUtil.getProperty(param.trim());
	}
	
	private int getPropertyValueAsInt(String param) {
		return Integer.valueOf(B2BSystemPropertiesUtil.getProperty(param.trim()));
	}
	
	private boolean getPropertyValueAsBoolean(String param) {
		return Boolean.valueOf(B2BSystemPropertiesUtil.getProperty(param.trim()));
	}

	private long getPropertyValueAsLong(String param) {
		return Long.valueOf(B2BSystemPropertiesUtil.getProperty(param.trim()));
	}
	
}