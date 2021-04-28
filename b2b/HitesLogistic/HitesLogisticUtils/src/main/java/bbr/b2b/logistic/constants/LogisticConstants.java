package bbr.b2b.logistic.constants;


import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Constants;

public class LogisticConstants extends Constants {

	private static LogisticConstants _instance;

	// Atributos de productos
	public static final String PROD_ATRIB_AREA			= "AREA";
	public static final String PROD_ATRIB_DEPARTAMENTO	= "DEPARTAMENTO";
	public static final String PROD_ATRIB_SECCION		= "SECCION";
	public static final String PROD_ATRIB_FAMILIA		= "FAMILIA";
	public static final String PROD_ATRIB_TALLA			= "TALLA";
	public static final String PROD_ATRIB_COLOR			= "COLOR";
	
	// Estados de órdenes DDC
	public static final String DDCORDER_STATE_RELEASED	= "LIBERADA";
	public static final String DDCORDER_STATE_ACCEPTED	= "ACEPTADA";
	public static final String DDCORDER_STATE_CANCELED	= "CANCELADA";
	public static final String DDCORDER_STATE_REJECTED	= "RECHAZADA";
	public static final String DDCORDER_STATE_EXPIRED	= "VENCIDA";
	public static final String DDCORDER_STATE_MODIFIED	= "MODIFICADA";
	public static final String DDCORDER_STATE_RECEIVED	= "RECEPCIONADA";
	
	// Estados de despachos DDC
	public static final String DDCDELIVERY_STATE_DELIVERED		= "ENTREGADO";
	public static final String DDCDELIVERY_STATE_UNDELIVERED	= "NO_ENTREGADO";
	public static final String DDCDELIVERY_STATE_REJECTED		= "RECHAZADO";
	public static final String DDCDELIVERY_STATE_ON_ROUTE		= "EN_RUTA";
			
	// Tipos de usuarios
	public static final String USER_TYPE_BBR			= "BBR";
	public static final String USER_TYPE_VENDOR			= "PROVEEDOR";
	public static final String USER_TYPE_RETAIL			= "RETAIL";
	
	public static final String[] VALID_FILE_TYPES		= {"jpg", "jpeg", "bmp", "png", "pdf"};
	
	// Constructor
	public static synchronized LogisticConstants getInstance() {
		if (_instance == null) {
			_instance = new LogisticConstants();
		}
		return _instance;
	}

	public static String getMailSender() throws OperationFailedException {
		return System.getProperty("MAIL_SENDER");
	}

	public static String[] getMailMQ() throws OperationFailedException {
		return System.getProperty("MAILMQ").split(",");
	}
	
	public static String getFILE_TRANSFER_PATH() throws OperationFailedException {
		return System.getProperty("download_file_path");
	}

	public static String getUPLOAD_FILE_PATH() throws OperationFailedException {
		return System.getProperty("upload_file_path");
	}
	
	public static String getFILESERVER_FOLDER() throws OperationFailedException {
		return System.getProperty("FILES_FOLDER");
	}

	public static String getMAIL_SESSION() throws OperationFailedException {
		return System.getProperty("MAIL_SESSION");
	}

	public static String[] getDEVELOPER_MAIL_ERROR() throws OperationFailedException {
		return System.getProperty("DEVELOPER_MAIL_ERROR").split(",");
	}
	
	public static Boolean getSENDMAILMQ() throws OperationFailedException {
		return Boolean.parseBoolean(System.getProperty("SENDMAILMQ"));
	}
	
	public static String[] getHITES_DATINGREQUEST_MAIL() throws OperationFailedException {
		return System.getProperty("HITES_DATINGREQUEST_MAIL").split(",");
	}
		
	public static int getINT_TODOS() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("INT_TODOS"));
	}

	public static int getEXCEL_MAX_NUMBER_OF_ROWS() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("EXCEL_MAX_NUMBER_OF_ROWS"));
	}

	public static int getPDF_MAX_NUMBER_OF_ROWS_OC() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("PDF_MAX_NUMBER_OF_ROWS_OC"));
	}
	
	public static int getMAX_LIST_ERROR_TO_SHOW() throws OperationFailedException{
		return Integer.parseInt(System.getProperty("MAX_LIST_ERROR_TO_SHOW"));
	}
	
	public static int getMAX_DAYS_INTERVAL_DATINGREPORT() throws OperationFailedException{
		return Integer.parseInt(System.getProperty("MAX_DAYS_INTERVAL_DATINGREPORT"));
	}
	
	public static int getMAX_DAYS_INTERVAL_LPNREPORT() throws OperationFailedException{
		return Integer.parseInt(System.getProperty("MAX_DAYS_INTERVAL_LPNREPORT"));
	}
	
	public static double getIVA() throws OperationFailedException{
		return Double.parseDouble(System.getProperty("IVA"));
	}
	
	public static String getCOUNTRYCODE() throws OperationFailedException {
		return System.getProperty("COUNTRYCODE");
	}

	public static String getBUSINESSAREA() throws OperationFailedException {
		return System.getProperty("BUSINESSAREA");
	}

	public static String getBACKUP_MSG_PATH() throws OperationFailedException {
		return System.getProperty("BACKUP_MSG_PATH");
	}

	// Mensajes
	public static boolean getENABLE_PENDING_MESSAGE_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_PENDING_MESSAGE_CRON"));
	}

	// Notificaciones
	public static String getHourToNotificationsDelete() throws OperationFailedException {
		return System.getProperty("NOTIFICATION_HOUR");
	}
		
	public static int getINTERVAL_MINUTE_NOTIFICATIONS_CRON() {
		return Integer.valueOf(System.getProperty("INTERVAL_MINUTE_NOTIFICATIONS_CRON"));
	}

	// Vencimiento OC
	public static Boolean getENABLE_CHECKBUYORDER() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_CHECKBUYORDER"));
	}	
	
	public static int getTIME_CHECKBUYORDER() {
		return Integer.valueOf(System.getProperty("TIME_CHECKBUYORDER"));
	}
	
	public static boolean getENABLE_LOADMESSAGE_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_LOADMESSAGE_CRON"));
	}

	// Pending Message
	public static int getDELETE_PREVIOUS_WEEKS(){
		return Integer.valueOf(System.getProperty("DELETE_PREVIOUS_WEEKS"));
	}
	
	public static String getPENDINGMESSAGECOUNT() {
		return System.getProperty("PENDING_MESSAGE_COUNT");
	}
	
	// Pending Mail
	public static int getMAX_MAIL_TO_SEND() {
		return Integer.valueOf(System.getProperty("MAX_MAIL_TO_SEND"));
	}

	public static boolean getENABLE_SEND_MAIL_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_SEND_MAIL_CRON"));
	}
	
	//WS VeV
	public static String getWSVeVURL() {
		return System.getProperty("VEV_WS_DES_URL");
	}
	
	public static String getWSVeV_UserKey() {
		return System.getProperty("VEV_WS_DES_USERKEY");
	}
	
	public static String getWSVeV_KeyValue() {
		return System.getProperty("VEV_WS_DES_KEYVALUE");
	}
	
	// PROXY
	public static boolean getHttpProxy() {
		return Boolean.parseBoolean(System.getProperty("HTTP_PROXY"));
	}
	
	public static String getHttpProxyIP() {
		return System.getProperty("HTTP_PROXY_IP");
	}
	
	public static int getHttpProxyPort() {
		return Integer.valueOf(System.getProperty("HTTP_PROXY_PORT"));
	}
	
	// Credenciales WS Envío de ASN
	public static String getWS_ASN_URL() {
		return String.valueOf(System.getProperty("WS_ASN_URL"));
	}
		
	public static String getWS_ASN_METHOD() {
		return String.valueOf(System.getProperty("WS_ASN_METHOD"));
	}

	public static String getWS_ASN_USER() {
		return String.valueOf(System.getProperty("WS_ASN_USER"));
	}
	
	public static String getWS_ASN_PASSWORD() {
		return String.valueOf(System.getProperty("WS_ASN_PASSWORD"));
	}
	
	
	public static String getACTAENTREGA_EXTENSION_IMAGE() throws OperationFailedException {
		return String.valueOf(System.getProperty("ACTAENTREGA_EXTENSION_IMAGE"));
	}

	public static String getACTAENTREGA_FOLDER_IMAGE() throws OperationFailedException {
		return String.valueOf(System.getProperty("ACTAENTREGA_FOLDER_IMAGE"));
	}	
	
}