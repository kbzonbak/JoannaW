package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.dto.PLFileDTO;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.soa.PLProcess.PLProcessAndValidation;
import bbr.b2b.logistic.storage.classes.S3FileDataDTO;
import bbr.b2b.logistic.storage.managers.classes.FileServiceManager;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "handlers/XmlToPLProveedor")
public class XmlToPLProveedor extends PLProcessAndValidation implements XmlToPLProveedorLocal {
	private static Logger logger = Logger.getLogger("SOALog");
	private static JAXBContext jc = null;

	@EJB
	SchedulerManagerLocal schedulermanager;

	@EJB
	VendorServerLocal vendorServerLocal;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processMessage(PLProveedor message, Long ticketNumber) throws ServiceException {
		String statusCode = "0";
		String statusMessage = "";
		String nombrePortal =  message.getCodCliente();
		String url = "";
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		String bucketname = B2BSystemPropertiesUtil.getProperty("S3_BUCKET_NAME");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String folderPath = System.getProperty("PACKIN_LIST_PATH"); 
		PLFileDTO plfiledto = new PLFileDTO();
		try {
			
			List<VendorW> listVendorW = vendorServerLocal.getByProperty("code", message.getCodProveedor());
			
			if (listVendorW.size() == 0) {
				BaseResultDTO e = new BaseResultDTO();
				e.setStatuscode("-1");
				e.setStatusmessage("Proveedor indicado no existe");
				errores.add(e);
			}
			
			if(errores.size() > 0){
				throw new Exception("Proveedor indicado no existe");
			}
			
			switch (message.getCodCliente()) {
				case "CL0501": //hites = CL0501
					errores.addAll(doValidateHites(message));
					if(errores.size() > 0){
						throw new Exception("Error al validar información entregada");
					}
					plfiledto = doCreateFileHites(message, folderPath, ticketNumber);
					errores.addAll(plfiledto.getErrores());
					break;
					
				case "CL1101": //LaPolar = CL1101
					errores.addAll(doValidateLaPolar(message));
					if(errores.size() > 0){
						throw new Exception("Error al validar información entregada");
					}
					plfiledto = doCreateFileLaPolar(message, folderPath, ticketNumber);
					errores.addAll(plfiledto.getErrores());
					break;
				case "CL0201": //Paris = CL0201
					errores.addAll(doValidateParisCL(message));
					if(errores.size() > 0){
						throw new Exception("Error al validar información entregada");
					}
					plfiledto = doCreateFileParisCL(message, folderPath, ticketNumber);
					errores.addAll(plfiledto.getErrores());
					break;
				case "CL0101": //SuperCL = CL0101
					errores.addAll(doValidateSuperCL(message));
					if(errores.size() > 0){
						throw new Exception("Error al validar información entregada");
					}
					plfiledto = doCreateFileSuperCL(message, folderPath, ticketNumber);
					errores.addAll(plfiledto.getErrores());
					break;
	
				default:
					BaseResultDTO e = new BaseResultDTO();
					e.setStatuscode("-1");
					e.setStatusmessage("Codigo de Cliente no existe");
					errores.add(e);
				}
			
			if(errores.size() > 0){
				throw new Exception("Error al generar archivo");
			}
			
			List<String> files = plfiledto.getFiles();
			String filename = "";
			if(files.size() > 1) {
				filename = "PL_" + message.getNroCita() + "_" + sdf.format(new Date()) + "_" + ".zip"; 
				String[] arrayFiles = (String[]) files.toArray(new String[files.size()]);
				createZipFile(folderPath, filename, arrayFiles);
			}else{
				filename = plfiledto.getFilename();
			}
			String statuscode = plfiledto.getStatuscode();
			statusMessage = plfiledto.getStatusmessage();
			
			int retry = 3;
			
			if (!statuscode.equals("-1")) {
				File file = new File(folderPath + filename);
				// se setea la url publica, si falla se realizan 3 reintentos
				while((url.isEmpty() || url.equals("-1")) && retry > 0 ){
					if(url.isEmpty()){
						logger.info("Subiendo arhivo PL a S3");
						url = uploadS3PL(bucketname, filename, file);
					}else{
						retry--;
						logger.info("Error al subir errores al S3 reintentando.. quedan  "+ retry +" reintentos ");
						url = uploadS3PL(bucketname, filename, file);
					}
				}
					
				if(url.equals("-1")){
					url = "";
					logger.error("No fue posible subir archivo de Packing list al S3 " + message.getNroCita());
					BaseResultDTO er = new BaseResultDTO();
					er.setStatuscode("-1");
					er.setStatusmessage("No fue posible subir archivo de Packing list al S3 " + message.getNroCita());
					errores.add(er);
				}
			}//
			BaseResultDTO[] results3 = null;
			if(errores.size() > 0){
				results3 = (BaseResultDTO[]) errores.toArray(new BaseResultDTO[errores.size()]);
			}
			sendTicketResponse(message.getVendedor().getCodigo(), ticketNumber, nombrePortal, statusCode, statusMessage, url, results3);
		} catch (Exception e) {
			statusCode = "-1";
			statusMessage = e.getMessage();
			BaseResultDTO[] result = (BaseResultDTO[]) errores.toArray(new BaseResultDTO[errores.size()]);
			sendTicketResponse(message.getVendedor().getCodigo(), ticketNumber, nombrePortal, statusCode, statusMessage, url, result);
		}

	}
	
	private String uploadS3PL(String bucketname, String filename, File file){
		String result = "-1";
		try{
			S3FileDataDTO filedata = FileServiceManager.getInstance().createObject(bucketname, filename, file,filename);
			return String.valueOf(filedata.getSignedUrl());
		}catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		
	}

	private void sendTicketResponse(String vendorCode, Long ticketNumber, String codigoportal, String status, String statusMessage, String adjunto, BaseResultDTO[] detalle) {
		// RESPUESTA HACIA SOA
		//
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber != null ? ticketNumber : 0);
		qrespuestaticket.setCodproveedor(vendorCode);
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("CPL");
		qrespuestaticket.setEstadoticket("PLCS");
		qrespuestaticket.setDescripcion(statusMessage);
		qrespuestaticket.setUrl("");
		qrespuestaticket.setAdjunto(adjunto);

		// se completan detalles
		bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles detallesticket = objFactory
				.createRespuestaTicketDetalles();
		List<bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle> detalleticketlist = detallesticket
				.getDetalle();
		if (detalle != null) {
			BaseResultDTO[] resultserror = detalle;

			// se agregan los resultados de WS
			if (resultserror != null) {
				for (int i = 0; i < resultserror.length; i++) {
					bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle detalleticket = objFactory
							.createRespuestaTicketDetallesDetalle();
					detalleticket.setTipo("linea");
					detalleticket.setCodigo(resultserror[i].getStatuscode());
					detalleticket.setEstado("");
					detalleticket.setDescripcion(resultserror[i].getStatusmessage());
					detalleticketlist.add(detalleticket);
				}
			}
			qrespuestaticket.setDetalles(detallesticket);
		}

		try {
			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qrespuestaticket, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			// doBackUpMessage(result,
			// String.valueOf(qrespuestaticket.getTicketnumber()),
			// "RESPUESTATICKET");
			System.out.println(result);
			try {
				schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl", "RespuestaTicket",
						String.valueOf(ticketNumber), result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private File createZipFile(String path, String zipName, String[] files) throws IOException {

        File zipFile = new File(path + zipName);

        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));

        for (String file: files) {
        	File localFile = new File(file);
            BufferedInputStream bufferedInputStream = null;
            byte data[] = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(localFile.getAbsolutePath());
            bufferedInputStream = new BufferedInputStream(fileInputStream, 1024);

            ZipEntry entry = new ZipEntry(localFile.getName());
            zipOutputStream.putNextEntry(entry);
            int count;
            while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
                zipOutputStream.write(data, 0, count);
            }
            bufferedInputStream.close();
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();

        return zipFile;
    }

	// private void doBackUpMessage(String content, String number, String
	// msgType) {
	// // EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
	// // ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
	// try {
	// MBeanServer server = MBeanServerLocator.locateJBoss();
	// ObjectName objectName = new ObjectName("jboss.jca:service=WorkManager");
	// JBossWorkManagerMBean jwm = (JBossWorkManagerMBean)
	// MBeanServerInvocationHandler.newProxyInstance(server, objectName,
	// JBossWorkManagerMBean.class, false);
	// WorkManager wm = jwm.getInstance();
	//
	// wm.scheduleWork(new BackUpUtils(content, number, msgType));
	// } catch (MalformedObjectNameException e) {
	// e.printStackTrace();
	// } catch (WorkException e) {
	// e.printStackTrace();
	// }
	// }
}
