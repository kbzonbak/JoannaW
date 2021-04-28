package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

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

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.report.classes.PackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "handlers/XmlToPLProveedor")
public class XmlToPLProveedor implements XmlToPLProveedorLocal {
	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext jc = null;

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	DeliveryServerLocal deliveryServerLocal = null;

	@EJB
	DeliveryReportManagerLocal deliveryReportManagerLocal;

	@EJB
	DatingServerLocal datingServerLocal;

	@EJB
	SchedulerManagerLocal schedulermanager;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processMessage(PLProveedor message, Long ticketNumber) throws ServiceException {
		logger.info("Ticket " + ticketNumber + " se est� procesando");
		DatingW datingW = null;
		UploadPackingListResultDTO uploadPackingListResultDTO = null;
		UploadPackingListInitParamDTO uploadPackingListInitParamDTO = new UploadPackingListInitParamDTO();
		List<PackingListDataDTO> list = new ArrayList<PackingListDataDTO>();
		List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
		String error = "";

		List<DatingW> listDatingW = null;
		try {
			listDatingW = datingServerLocal.getByProperty("number", Long.parseLong(message.getNroCita()));
		} catch (NumberFormatException e) {
			error = "Fila " + 0 + ": Cita " + message.getNroCita() + " no es num�rico";
			baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
		}
		if (baseresulList.size() == 0) {
			if (listDatingW == null || listDatingW.size() == 0) {
				error = "Fila " + 0 + ": Cita " + message.getNroCita() + " no existe";
				baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
			} else {
				datingW = listDatingW.get(0);

				VendorW vendor = vendorserver.getById(datingW.getVendorid());
				if (!vendor.getCode().equals(message.getVendedor().getCodigo())) {
					error = "Fila " + 0 + ": Cita " + message.getNroCita() + " proveedor no corresponde al informado";
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				// doCVSReport(message, delivery.getId(), vendor, ticketNumber);

				uploadPackingListInitParamDTO.setDeliveryid(datingW.getDeliveryid());
				uploadPackingListInitParamDTO.setVendorcode(vendor.getRut());
			}
			int rowNumber = 1;

			for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
				Bulto bulto = message.getBultos().getBulto().get(i);
				boolean isBulk = false;
				if ((bulto.getNroBulto() != null && !bulto.getNroBulto().isEmpty()) || (bulto.getNroPallet() != null && !bulto.getNroPallet().isEmpty())) {
					if (bulto.getNroBulto() != null && !bulto.getNroBulto().isEmpty()) {
						isBulk = true;
					}
				} else {
					error = "Fila " + (rowNumber) + ": Debe ingresar NROBULTO o NOPALLET";
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				if (bulto.getCodLocalDestino() == null || bulto.getCodLocalDestino().isEmpty()) {
					error = "Fila " + (rowNumber) + ": El código de local es obligatorio";
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				for (Detalle detalle : bulto.getDetalles().getDetalle()) {
					PackingListDataDTO pl = new PackingListDataDTO();
					pl.setLocationcode(bulto.getCodLocalDestino());
					pl.setBulknumber(bulto.getNroBulto());
					pl.setPalletnumber(bulto.getNroPallet());
					pl.setBulk(isBulk);
					pl.setTaxnumber(detalle.getNoDocTributario() != null && !detalle.getNoDocTributario().isEmpty() ? Long.valueOf(detalle.getNoDocTributario()) : null);

					if (detalle.getNoc() != null && !detalle.getNoc().isEmpty()) {
						pl.setOrdernumber(Long.valueOf(detalle.getNoc()));
					} else {
						error = "Fila " + (rowNumber) + ": El N°mero de orden es obligatorio";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}

					if (detalle.getCodProdCliente() != null && !detalle.getCodProdCliente().isEmpty()) {
						pl.setItemsku(detalle.getCodProdCliente());
					} else {
						error = "Fila " + (rowNumber) + ": El SKU (COD_PROD_CTE) es obligatorio";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}

					if (detalle.getCantidadUnidades() != null && !detalle.getCantidadUnidades().isEmpty()) {
						pl.setUnits(Double.valueOf(detalle.getCantidadUnidades()));
					} else {
						error = "Fila " + (rowNumber) + ": N°mero de unidades es obligatorio";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					pl.setRownumber(rowNumber);
					list.add(pl);
					rowNumber++;
				}
			}
		}
		if (baseresulList.size() > 0) {
			uploadPackingListResultDTO = new UploadPackingListResultDTO();
			uploadPackingListResultDTO.setStatuscode("L1");
			uploadPackingListResultDTO.setStatusmessage("Existe uno o mas registros con datos obligatorios nulos o vac�os");
			uploadPackingListResultDTO.setValidationerrors((BaseResultDTO[]) baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
			logger.info("Packing list ticket " + ticketNumber + " tiene errores");
		} else {
			uploadPackingListResultDTO = new UploadPackingListResultDTO();
			PackingListDataDTO[] packinglist = (PackingListDataDTO[]) list.toArray(new PackingListDataDTO[list.size()]);
			uploadPackingListInitParamDTO.setPackinglist(packinglist);
			uploadPackingListInitParamDTO.setComplementnumber(1);
			uploadPackingListResultDTO = deliveryReportManagerLocal.doUploadPackingList(uploadPackingListInitParamDTO);
			logger.info("Ticket " + ticketNumber + " se ha procesado correctamente");
		}
		sendTicketResponse(message.getVendedor().getCodigo(), ticketNumber, "CL0201", uploadPackingListResultDTO);
	}

	private void sendTicketResponse(String vendor, Long ticketNumber, String codigoportal, UploadPackingListResultDTO uploadPackingListResultDTO) {
		// RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber != null ? ticketNumber : 0);
		// qrespuestaticket.setTicketnumber(ticketNumber.longValue());
		qrespuestaticket.setCodproveedor(vendor);
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("CPL");
		qrespuestaticket.setEstadoticket(uploadPackingListResultDTO.getStatuscode().equals("0") ? "0" : "-1");
		qrespuestaticket.setDescripcion(uploadPackingListResultDTO.getStatusmessage());

		// se completan detalles
		bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles detallesticket = objFactory.createRespuestaTicketDetalles();
		List<bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle> detalleticketlist = detallesticket.getDetalle();
		BaseResultDTO[] resultserror = uploadPackingListResultDTO.getValidationerrors();

		// se agregan los resultados de WS
		if (resultserror != null) {
			for (int i = 0; i < resultserror.length; i++) {
				bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle detalleticket = objFactory.createRespuestaTicketDetallesDetalle();
				detalleticket.setTipo("linea");
				detalleticket.setCodigo(resultserror[i].getStatuscode());
				detalleticket.setEstado("");
				detalleticket.setDescripcion(resultserror[i].getStatusmessage());
				detalleticketlist.add(detalleticket);
			}
		}
		qrespuestaticket.setDetalles(detallesticket);

		try {
			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qrespuestaticket, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, String.valueOf(qrespuestaticket.getTicketnumber()), "RESPUESTATICKET");
			logger.info(result);
			try {
				schedulermanager.doAddMessageQueue_newtx("jboss/qcf_soa", "jboss/q_esbgrlcenco", "RespuestaTicket", String.valueOf(ticketNumber != null ? ticketNumber : 0L), result);
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

	@Resource
	private ManagedExecutorService executor;

	private void doBackUpMessage(String content, String number, String msgType){
		
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try{
			executor.submit(new BackUpUtils(content, number, msgType));			
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}			
	}
}
