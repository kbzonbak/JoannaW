package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.storage.managers.interfaces.FileServiceManagerLocal;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.vev.managers.interfaces.VeVReportManagerLocal;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVInitParamFileDTO;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVResultDTO;
import bbr.common.dataset.util.CSVConverter;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataColumnStyle;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;


@Stateless(name = "handlers/XmlToInventProvSoa")
public class XmlToInventProvSoa implements XmlToInventProvSoaLocal {

	private static Logger logger = Logger.getLogger("SOALog");
	private static JAXBContext jc = null;

	@Resource
	ManagedExecutorService executor;
	
	@EJB
	VendorServerLocal vendorserver = null;
	
	@EJB
	VeVReportManagerLocal vevReportManagerLocal;
	
	@EJB
	FileServiceManagerLocal fileservicemanager;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	private ArrayList<BaseResultDTO> errorList = new ArrayList<BaseResultDTO>();
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processMessage(InventarioProveedor message, Long ticketNumber) throws LoadDataException, OperationFailedException{

		String senderIdentificacion = B2BSystemPropertiesUtil.getProperty("SenderIdentification");
		try {
			String msg = "";
			VendorW vendor = null;
			logger.info("Inicio proceso archivo de Inventario");
			// Verificar si existe vendedor correctamente si no viene generar una excepcion
			if (message.getVendedor() == null || message.getVendedor().getCodigo() == null
					|| message.getVendedor().getCodigo().trim().equals("")) {
				msg = "No hay datos para Codigo Vendedor";
				logger.error(msg);
				sendMailToBBR(
						"Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
				errorList.add(
						LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", msg, false));
			} else {
				String codProveedor = message.getVendedor().getCodigo().trim();

				try {
					VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", codProveedor);
					if (vendorArr == null || vendorArr.length == 0) {
						sendMailToBBR(
								"Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
						errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1",
								"No existe proveedor con código " + codProveedor, false));
					}
					vendor = vendorArr[0];
				} catch (NotFoundException nfe) {
					nfe.printStackTrace();
					sendMailToBBR(
							"Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1",
							"No existe Proveedor con código " + codProveedor, false));
				} catch (Exception ge) {
					ge.printStackTrace();
					sendMailToBBR(
							"Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1",
							"Problemas al obtener el Proveedor" + codProveedor, false));
				}
			}
			// construccion del excel
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
			String realfilename = "inv_prov_" + dateFormat.format(new Date()) + ".csv";
			DataRow row = null;
			// Escribir descripcion del filtro seleccionado
			DataTable dt0 = new DataTable("Inventario");
			DataColumn col01 = new DataColumn("Bodega", String.class);
			DataColumn col02 = new DataColumn("SKU", String.class);
			DataColumn col03 = new DataColumn("Descripción Art.", String.class);
			DataColumn col04 = new DataColumn("Stock Disponible", String.class);
			DataColumn col05 = new DataColumn("Reposición Diaria", String.class);
			dt0.addColumn(col01);
			dt0.addColumn(col02);
			dt0.addColumn(col03);
			dt0.addColumn(col04);
			dt0.addColumn(col05);
			dt0.setShowcolumnheaders(false);
			int rownumber = 0;
			String error = "";
			for (int i = 0; i < message.getDetalles().getDetalle().size(); i++) {
				Detalle detalle = message.getDetalles().getDetalle().get(i);
				row = dt0.newRow();
				row.setCellValue(rownumber,"rownumber");
				if (detalle.getCodLocal() != null && !detalle.getCodLocal().isEmpty()) {
					row.setCellValue("Bodega", detalle.getCodLocal());
				} else {
					error = "Fila " + (rownumber) + ": Código de local es obligatorio";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error,
							false));
				}

				if (detalle.getSku() != null && !detalle.getSku().isEmpty()) {
					row.setCellValue("SKU", detalle.getSku());
				} else {
					error = "Fila " + (rownumber) + ": El SKU es obligatorio";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error,
							false));
				}

				row.setCellValue("Descripción Art.", "");

				if (detalle.getCantidad() != null && !detalle.getCantidad().isEmpty()) {
					row.setCellValue("Stock Disponible", detalle.getCantidad());
				} else {
					error = "Fila " + (rownumber) + ": La cantidad es obligatoria";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error,
							false));
				}

				row.setCellValue("Reposición Diaria", "0");
				dt0.addRow(row);

			}
			String status = "0";
			String statusMessage = "Inventario se ha procesado correctamente";
			
			
			if (errorList.size() > 0) {
				status = "-1";
				statusMessage = "Error procesando archivo";
				BaseResultDTO[] result = (BaseResultDTO[]) errorList.toArray(new BaseResultDTO[errorList.size()]);
				sendTicketResponse("CL0601", ticketNumber, senderIdentificacion, status, statusMessage, "", result);

			} else {
				//Si no hay errores se genera el archivo csv 
				// ESTILOS
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(0);
				DataColumnStyle style1 = new DataColumnStyle("decimal", df, 20);

				DataColumnStyleInfo info = new DataColumnStyleInfo();
				//info.addDataColumnStyles(style1, col06);

				// Crear Archivo
				// CSV
				CSVConverter converter = new CSVConverter();
				converter.setSeparator(',');
				converter.setStringSeparator('\'');
				converter.setUseStringSeparator(true);
				String finalPath = "";
				try {
					finalPath = System.getProperty("FILE_INVENTORY_PATH") + realfilename;
					converter.ExportToCSV(dt0, finalPath, Charset.forName("ISO-8859-1"));
					logger.info("Archivo " + finalPath + " se ha generado correctamente.");
				} catch (IOException e) {
					e.printStackTrace();
					throw new LoadDataException("Error al generar data para carga");
				}
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
				DoUploadStockVeVInitParamFileDTO initParamDTO = new DoUploadStockVeVInitParamFileDTO();
				initParamDTO.setFecha(now.format(formatter));
				initParamDTO.setFilename(finalPath);
				initParamDTO.setFormatoFecha("DD-MM-YYYY");
				initParamDTO.setProveedor(message.getVendedor().getCodigo());
				initParamDTO.setUsuario("SOA");
				initParamDTO.setTipoUsuario("BBR");
				DoUploadStockVeVResultDTO doUploadStockVeVResultDTO = vevReportManagerLocal.doUploadStockVeV(initParamDTO);
				
				if (doUploadStockVeVResultDTO.getStatuscode() != null && doUploadStockVeVResultDTO.getStatuscode().equals("L6004")) {
					status = "-1";
				}
				
				sendTicketResponse(message.getVendedor().getCodigo(), ticketNumber, senderIdentificacion, status, doUploadStockVeVResultDTO.getStatusmessage(), "",
						doUploadStockVeVResultDTO.getBaseresults());
			} 
		} catch (Exception e) {
			sendTicketResponse(message.getVendedor().getCodigo(), ticketNumber, senderIdentificacion, "-1", "Ha ocurrido un error al procesar el inventario " + e.getMessage(), "", null);
		}
		
		
	}
	
	private void sendMailToBBR(String content){		
		String subject = "B2B Easy: Actualización Capacidad Disponible VeV";
		String body = "";
		try {
			String[] mailreciever = LogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
			String mailsender = LogisticConstants.getInstance().getMailSender();
			String mailSession = LogisticConstants.getInstance().getMAIL_SESSION();
			body = body + content;
//			Mailer.getInstance().sendMailBySession(mailreciever, null, mailsender, subject, body, true, null, mailSession);
			Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, body, true, null, mailSession);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se envio el mail del fracaso de envio de email");
		}		
	}
	
	private void sendTicketResponse(String vendorCode, Long ticketNumber, String codigoportal, String status, String statusMessage, String url, BaseResultDTO[] detalle) {
		// RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber != null ? ticketNumber : 0);
		qrespuestaticket.setCodproveedor(vendorCode);
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("AST");
		qrespuestaticket.setEstadoticket(status);
		qrespuestaticket.setDescripcion(statusMessage);
		qrespuestaticket.setUrl(url);

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
			LocalDateTime now = LocalDateTime.now();
			try {
				schedulermanager.doAddMessageQueue("qcf_soa", "q_esbgrl", "RespuestaTicket", String.valueOf(ticketNumber), "", result, now);
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

}
