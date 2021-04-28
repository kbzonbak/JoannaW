//package bbr.b2b.logistic.queuehandler;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.nio.charset.Charset;
//import java.util.zip.GZIPInputStream;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//
//import org.apache.log4j.Logger;
//import org.xml.sax.InputSource;
//
//import sun.misc.BASE64Decoder;
//import bbr.b2b.logistic.msgregionalb2b.XmlToVendorLocal;
//import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
//import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
//import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
//import bbr.b2b.common.util.Mailer;
//import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
//import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
//import bbr.b2b.regional.logistic.xml.qvendordwh.VENDORDWH;
//import bbr.b2b.regional.parser.RegionalVendorParserFactory;
//import bbr.b2b.regional.parser.dto.InitParamGetParserDTO;
//import bbr.b2b.regional.parser.dto.InitParamParseDTO;
//import bbr.b2b.regional.parser.dto.MessageDTO;
//import bbr.b2b.regional.parser.dto.ResultParseDTO;
//import bbr.b2b.regional.parser.interfaces.IParser;
//
//@Stateless(name = "handlers/VendorHandler")
//@TransactionManagement(TransactionManagementType.BEAN)
//public class VendorHandler implements VendorHandlerLocal {
//
//	private static Logger logger = Logger.getLogger(VendorHandler.class);
//	
//	@EJB
//	XmlToVendorLocal xmlToVendorLocal;
//
//	
//	private static JAXBContext jc = null;
//
//	private static JAXBContext getJC() throws JAXBException {
//		if (jc == null)
//			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.vendor");
//		return jc;
//	}	
//	
//	
//	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {
//
//		// Como el dato viene comprimido y en BASE 64, hay que transformarlo
//		BASE64Decoder decoder = new BASE64Decoder();
//		int created = 0;
//		int updated = 0;		
//		int state = 0;
//		
//		try {
//			// Decodificacion
//			byte[] bytes = decoder.decodeBuffer(content);
//			// Descomprimir
//			ByteArrayInputStream instream = new ByteArrayInputStream(bytes);
//			GZIPInputStream gzipstream = new GZIPInputStream(instream);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(gzipstream, Charset.forName("UTF-8")));
//			String line = null;
//			
//			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//			Schema schema = null;
//			File xsdfile = null;
//			xsdfile = RegionalLogisticConstants.getInstance().getVendorXSDFile();
//			JAXBContext jc = getJC();
//			Unmarshaller u = jc.createUnmarshaller();
//			schema = schemaFactory.newSchema(xsdfile);
//			u.setSchema(schema);
//			
//			while ((line = reader.readLine()) != null) {				
//				// Obtiene XML desde linea CSV
//				String xmlStr = getXmlFromCsvVendor(line);
//				
//				// Procesar la l�nea				
//				InputSource source = new InputSource(new StringReader(xmlStr));				
//				
//				VENDORDWH vendorParser = (VENDORDWH) u.unmarshal(source);
//				
//				logger.info(line);
//				state = xmlToVendorLocal.processMessageVendors(vendorParser);
//								
//				switch (state){					
//					case 0: created++; break;
//					case 1: updated++; break;
//					case 2: break;				
//				}			
//			}
//
//			// Se envia notificación de la carga
//			doSendNotification(created, updated);
//			logger.info("Vendedores cargados");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (LoadDataException e) {
//			 logger.error("Error en la carga");
//			 //Rollback de la transacion
//			 //rollback(usertransaction);
//
//		} catch (Exception ex) {
//			// ex.printStackTrace();
//			logger.error("Error ");
//			// Rollback de la transacion
//			// rollback(usertransaction);
//
//			// RECOVERY
//			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
//
//			String messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_VENDOR";
//			try {
//				msgRecoveryServices.saveMsgToFile(messagetype, content, ex, "csv");
//			} catch (Exception e) {
//				logger.error("Error en Recovery de Carga Vendor");
//				logger.error(e.getMessage());
//			}
//		}
//	}
//
//	private void doSendNotification(int created, int updated){
//		StringBuffer mensaje = new StringBuffer();
//		try {
//			mensaje.append("Estimados, \n\n");
//			mensaje.append("Se ha realizado la carga de Proveedores en el m�dulo logístico para el proyecto ");
//			mensaje.append(RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + ". \n\n");
//			mensaje.append("Se ha cargado un total de "+ (created + updated) + " proveedor(es): \n");
//			mensaje.append("* Nuevos: " + created + "\n");
//			mensaje.append("* Actualizados: " + updated + "\n\n");
//			mensaje.append("Atte. B2B logístico " + RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE());
//		
//			logger.info(mensaje.toString());		
//			
//			String subject = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + " Logistica: Carga de Proveedores";
//							
//			String[] mailreciever = RegionalLogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
//			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
//			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();			
//			Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, mensaje.toString(), false, null, mailSession);
//			
//		} catch (Exception e) {
//			logger.error("No se envio el mail de notificación de carga de proveedores");
//		}		
//	}	
//	
//	private String getXmlFromCsvVendor(String csv){
//		String str = "";
//		RegionalVendorParserFactory iXDBParserFactory = RegionalVendorParserFactory.getInstance();	
//		InitParamGetParserDTO iInitParamGetParserDTO = new InitParamGetParserDTO();
//		IParser iParser = null;
//		InitParamParseDTO iInitParaParseDTO = new InitParamParseDTO();
//		MessageDTO iMessageDTO = new MessageDTO();		
//		
//		try {				
//			iInitParamGetParserDTO.setParserType(RegionalVendorParserFactory.CSVVENDORCOMERCIAL_TO_XMLLOGISTICA);
//			iParser = iXDBParserFactory.getParser(iInitParamGetParserDTO);
//			iMessageDTO.setMessage(csv);
//			iInitParaParseDTO.setMessageDTO(iMessageDTO);
//			ResultParseDTO iResultParseDTO = (ResultParseDTO) iParser.parse(iInitParaParseDTO);
//			str = iResultParseDTO.getMessageDTO().getMessage();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}		
//		return str;
//	}
//}
