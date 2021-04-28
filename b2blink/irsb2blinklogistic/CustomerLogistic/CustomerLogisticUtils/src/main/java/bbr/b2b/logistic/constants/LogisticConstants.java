package bbr.b2b.logistic.constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Constants;

public class LogisticConstants extends Constants {
	
	static Logger logger = Logger.getLogger(LogisticConstants.class);

	private static LogisticConstants _instance;

	public static final String AGENDADA = "Agendada";

	public static final String AGENDADA_CODE = "AGENDADA";

	public static final String LIBERADA = "Liberada";

	public static final String LIBERADA_CODE = "LIBERADA";

	public static final String ACEPTADA = "Aceptada";

	public static final String ACEPTADA_CODE = "ACEPTADA";

	public static final String MODIFICADA = "Modificada";

	public static final String MODIFICADA_CODE = "MODIFICADA";

	public static final String RECHAZADA = "Rechazada";

	public static final String RECHAZADA_CODE = "RECHAZADA";

	public static final String RE_AGENDAR = "Re-agendar";

	public static final String RE_AGENDAR_CODE = "RE_AGENDAR";

	public static final String CITA_SOLICITADA = "Cita solicitada";

	public static final String CITA_SOLICITADA_CODE = "CITA_SOLICITADA";

	public static final String CITA_ASIGNADA = "Cita asignada";

	public static final String CITA_ASIGNADA_CODE = "CITA_ASIGNADA";

	public static final String STOCK = "Stock";

	// Constructor
	public static synchronized LogisticConstants getInstance() {
		if (_instance == null) {
			
			_instance = new LogisticConstants();
//			String propertiesFilePath = _instance.getClass().getClassLoader().getResource("").getPath();
//			String propertiesFilePath2 = _instance.getClass().getResource("").getPath();
//			InputStream i = _instance.getClass().getResourceAsStream("");

//			String path = propertiesFilePath + "LogisticConstants.properties";
//			String confPath = "/Desarrollo/wildfly-10.1.0.Final/logistica/configuration/properties/";
			
			String path = System.getProperty("user.dir");
			
			logger.info("user.dir = "+ path);
			
			if(path.contains("bin"))
			{
				path = path.substring(0,path.indexOf("bin")-1);
				
				path = path + "//logistica//configuration//properties//";
			}
			else{
				path = "//opt//wildfly//logistica//configuration//properties//";
			}
			
			logger.info("Total path = "+path);
			
			String solicitudSOAxsdpath = path + "SolicitudOrdenes.xsd";
			String notificacionOcXSDpath = path + "NotificacionReciboOrden_1.0.xsd";
			String propsPath = path + "LogisticConstants.properties";
			String notificacionInv = path + "NotificacionInventario_1.0.xsd";
			String notificacionSales = path + "NotificacionVentas_1.0.xsd";
			String solicitudInv = path + "SolicitudInventario_1.1.xsd";
			String solicitudSales = path + "SolicitudVentas_1.1.xsd";
			String reporteInv = path + "ReporteInventario_1.0.xsd";
			String reporteSales = path + "ReporteVentas_1.0.xsd";
			
			_instance.setSolicitudSOAXSDPath(solicitudSOAxsdpath);
			_instance.setSOANotificacionOcXSDPath(notificacionOcXSDpath);		
			_instance.setPropertyfilepath(propsPath);
			_instance.setNotificacionInv(notificacionInv);
			_instance.setNotificacionSales(notificacionSales);
			_instance.setSolicitudSales(solicitudSales);
			_instance.setSolicitudInv(solicitudInv);
			_instance.setReporteInv(reporteInv);
			_instance.setReporteSales(reporteSales);
			
		}
		return _instance;
	}
	
//	public static synchronized LogisticConstants getInstance() {
//		if (_instance == null) {
//			
//			_instance = new LogisticConstants();
//			
//			String propertiesFilePath = _instance.getClass().getClassLoader().getResource("LogisticConstants.properties").getPath();
//			_instance.setPropertyfilepath(propertiesFilePath);
//
////			String orderxsdpath = propertiesFilePath + File.separator + "qorden.xsd";
////			_instance.setOrderXSDPath(orderxsdpath);
//			
//			String solicitudSOAxsdpath = _instance.getClass().getClassLoader().getResource("SolicitudOrdenes.xsd").getPath();
////			String solicitudProveedoresXSDpath = _instance.getClass().getClassLoader().getResource("SolicitudProveedoresOrdenesPendientes_1.1.xsd").getPath();
////			String receptionxsdpath = _instance.getClass().getClassLoader().getResource("ReceptionData.xsd").getPath();
//			String notificacionOcXSDpath = _instance.getClass().getClassLoader().getResource("NotificacionReciboOrden_1.0.xsd").getPath();
//			
//			_instance.setSolicitudSOAXSDPath(solicitudSOAxsdpath);
////			_instance.setSOASolicitudProveedoresXSDPath(solicitudProveedoresXSDpath);
////			_instance.setReceptionXSDPath(receptionxsdpath);
//			_instance.setSOANotificacionOcXSDPath(notificacionOcXSDpath);
//		}
//		return _instance;
//	}

	private String orderXSDpath;

	private String orderPredXSDpath;

	private String vendorXSDpath;
	
	private String solicitudSOAXSDpath;
	
	private String notificacionOcXSDpath;
	
	private String notificacionInv;
	
	private String notificacionSales;
	
	private String solicitudInv;
	
	private String solicitudSales;
	
	private String reporteInv;
	
	private String reporteSales;
	
	
	

	public File getNotificacionInv() {
		return new File(notificacionInv);
	}

	public void setNotificacionInv(String notificacionInv) {
		this.notificacionInv = notificacionInv;
	}

	public File getNotificacionSales() {
		return new File(notificacionSales);
	}

	public void setNotificacionSales(String notificacionSales) {
		this.notificacionSales = notificacionSales;
	}

	public File getSolicitudInvFile() {
		return new File(solicitudInv);
	}

	public void setSolicitudInv(String solicitudInv) {
		this.solicitudInv = solicitudInv;
	}

	public File getSolicitudSalesFile() {
		return new File(solicitudSales);
	}

	public void setSolicitudSales(String solicitudSales) {
		this.solicitudSales = solicitudSales;
	}

	public File getReporteInv() {
		return new File(reporteInv);
	}

	public void setReporteInv(String reporteInv) {
		this.reporteInv = reporteInv;
	}

	public File getReporteSales() {
		return new File(reporteSales);
	}

	public void setReporteSales(String reporteSales) {
		this.reporteSales = reporteSales;
	}

	// // ------ TEST ----
	public static final String MQ_HOST = "10.200.22.18";

	public static final String MQ_PORT = "2501";

	public static final String MQ_NAME = "QM_CENCOEASYAR";

	private static final boolean produccion = false;
	// // ------ TEST ----

	public static final String EN_RECEPCION_CODE = "EN_RECEPCION";

	public static final String PREDISTRIBUIDA = "Predistribuida";

	public static final String NO_ASISTE_CODE = "NO_ASISTE";

	public static final int USER_TYPE_PROVIDER = 1;

	// ----- PRODUCCION ----
	// public static final String MQ_HOST = "10.200.23.14";
	// public static final String MQ_PORT = "2501";
	// public static final String MQ_NAME = "QM_CENCOEASYAR";
	// private static final boolean produccion = true;
	// ----- PRODUCCION ----

	public String getB2BName() throws OperationFailedException {
		return System.getProperty("B2B_NAME");
	}

	public String getMailSender() throws OperationFailedException {
		if (getProduccion())
			return System.getProperty("MAIL_SENDER_PROD");
		else
			return System.getProperty("MAIL_SENDER_TEST");
	}

	public boolean getProduccion() {
		return produccion;
	}

	// ------------------------------
	public int getFREEHOURSTOASSIGN() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("FREEHOURSTOASSIGN"));
	}

	public static String getFILE_TRANSFER_PATH() throws OperationFailedException {
		return System.getProperty("FILE_TRANSFER_PATH");
	}

	public static String getFILESERVER_FOLDER() throws OperationFailedException {
		return System.getProperty("FILES_FOLDER");
	}

	public String getOS() throws OperationFailedException {
		return System.getProperty("OS");
	}

	public String getSOA_QMGR_NAME() throws OperationFailedException {
		return System.getProperty("SOA_QMGR_NAME");
	}

	public String getMAIL_SESSION() throws OperationFailedException {
		return System.getProperty("MAIL_SESSION");
	}

	public String[] getDEVELOPER_MAIL_ERROR() throws OperationFailedException {
		return System.getProperty("DEVELOPER_MAIL_ERROR").split(",");
	}

	public int getINT_TODOS() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("INT_TODOS"));
	}

	public int getUSERTYPE_PROVIDER() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("USERTYPE_PROVIDER"));
	}

	public int getUSERTYPE_RETAILER() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("USERTYPE_RETAILER"));
	}

	public int getUSERTYPE_BBR() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("USERTYPE_BBR"));
	}

	public int getSOA_MQ_PORT() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("SOA_MQ_PORT"));
	}

	public int getMAX_NUMBER_OF_ORDERS() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("MAX_NUMBER_OF_ORDERS"));
	}

	public static int getMAX_NUMBER_OF_ROWS() throws OperationFailedException {
		return Integer.parseInt(System.getProperty("MAX_NUMBER_OF_ROWS"));
	}

	public static boolean getSTOP_CHECK_STATE() throws OperationFailedException {
		return Boolean.getBoolean(System.getProperty("STOP_CHECK_STATE"));
	}

	public boolean getSENDNOTIF() throws OperationFailedException {
		return Boolean.parseBoolean(System.getProperty("SENDNOTIF"));
	}

	public String getCOUNTRYCODE() throws OperationFailedException {
		return System.getProperty("COUNTRYCODE");
	}

	public String getBUSINESSAREA() throws OperationFailedException {
		return System.getProperty("BUSINESSAREA");
	}

	public String getBACKUP_MSG_PATH() throws OperationFailedException {
		return System.getProperty("BACKUP_MSG_PATH");
	}

	public void setOrderXSDPath(String orderXSDpath) {
		this.orderXSDpath = orderXSDpath;
	}

	public void setOrderPredXSDPath(String orderPredXSDpath) {
		this.orderPredXSDpath = orderPredXSDpath;
	}

	public void setVendorXSDPath(String vendorXSDpath) {
		this.vendorXSDpath = vendorXSDpath;
	}

	public File getOrderPredXSDFile() {
		File file = new File(orderPredXSDpath);
		return file;
	}
	
	public File getSolicitudSOAXSDFile() {
		File file = new File(solicitudSOAXSDpath);
		return file;
	}
	
	public void setSolicitudSOAXSDPath(String solicitudSOAXSDpath) {
		this.solicitudSOAXSDpath = solicitudSOAXSDpath;
	}

	public File getOrderXSDFile() {
		File file = new File(orderXSDpath);
		return file;
	}

	public File getVendorXSDFile() {
		File file = new File(vendorXSDpath);
		return file;
	}
	
	public void setSOANotificacionOcXSDPath(String notificacionOcXSDpath) {
		this.notificacionOcXSDpath = notificacionOcXSDpath;
	}
	
	public File getSOA_NotificacionOcXSDFile() throws OperationFailedException {
		File file = new File(notificacionOcXSDpath);
		return file;
	}

	public static String getHourToNotificationsDelete() throws OperationFailedException {
		return System.getProperty("HOUR");
	}

	public static int getMAX_MAIL_TO_SEND() {
		return Integer.valueOf(System.getProperty("MAX_MAIL_TO_SEND"));
	}

	public static boolean getENABLE_SEND_MAIL_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_SEND_MAIL_CRON"));
	}

	public static boolean getENABLE_CHECKBUYORDER_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_CHECKBUYORDER_CRON"));
	}

	public static int getTIME_CHECKBUYORDER_CRON() {
		return Integer.valueOf(System.getProperty("TIME_CHECKBUYORDER_CRON"));
	}

	public static boolean getENABLE_KPIDATA_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_KPIDATA_CRON"));
	}

	public static String getTIME_KPIDATA_CRON() {
		return System.getProperty("TIME_CHECKBUYORDER_CRON");
	}

	public static boolean getENABLE_PENDING_MESSAGE_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_PENDING_MESSAGE_CRON"));
	}

	public static String getPENDINGMESSAGECOUNT() {
		return System.getProperty("PENDING_MESSAGE_COUNT");
	}

	public static boolean getENABLE_LOADMESSAGE_CRON() {
		return Boolean.parseBoolean(System.getProperty("ENABLE_LOADMESSAGE_CRON"));
	}

}