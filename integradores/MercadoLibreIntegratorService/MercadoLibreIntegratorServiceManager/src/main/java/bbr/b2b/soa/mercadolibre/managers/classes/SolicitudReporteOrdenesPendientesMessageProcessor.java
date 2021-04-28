package bbr.b2b.soa.mercadolibre.managers.classes;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes.Proveedores.Proveedor;
import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.constants.SOAStates;
import bbr.b2b.soa.mercadolibre.constants.ServiceConstants;
import bbr.b2b.soa.mercadolibre.constants.Topic;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.entities.NotificationDetail;
import bbr.b2b.soa.mercadolibre.managers.interfaces.ISolicitudReporteOrdenesPendientesMessageProcessor;
import bbr.b2b.soa.mercadolibre.repository.classes.AuthorizationRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.CompanyRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationDetailRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationRepository;
import bbr.b2b.soa.mercadolibre.utis.JMSMessageService;
import bbr.b2b.soa.mercadolibre.utis.MessageMapperOCInternoImpl;
import lombok.Data;

@Service
@Transactional
public class SolicitudReporteOrdenesPendientesMessageProcessor implements ISolicitudReporteOrdenesPendientesMessageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudReporteOrdenesPendientesMessageProcessor.class);

	private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

	private static JAXBContext jcCtxListadoOrdenesPendientes = null;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotificationDetailRepository notificationDetailRepository;

	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Autowired
	private MessageMapperOCInternoImpl messageMapper;

	@Autowired
	private JMSMessageService messageservice;

	@Value("${mdp.jms.notifications.queue.name}")
	private String notificationQueueName;

	@Value("${mdp.jms.outbound.queue.name}")
	private String soaOutboundQueueName;

	@Autowired
	private ServiceConstants serviceConstants;

	@Override
	public void processMessage(SolicitudProveedoresOrdenesPendientes solicitudProveedoresOrdenesPendientes) {
		// TODO DVI TEST

		Map<Long, Company> companyIds = new HashMap<>();
		Map<Long, CompanyData> companyDatas = new HashMap<Long, CompanyData>();

		// Constantes
		int soaReceptionTimeDefault = serviceConstants.getSoaPendingReceptionTime() != null && serviceConstants.getSoaPendingReceptionTime() > 0 ? serviceConstants.getSoaPendingReceptionTime() : 0;
		int maxPendingOrders = serviceConstants.getMaxPendingMessages() != null && serviceConstants.getMaxPendingMessages() > 0 ? serviceConstants.getMaxPendingMessages() : 10;

		// PROVEEDORES
		List<Proveedor> provList = solicitudProveedoresOrdenesPendientes.getProveedores().getProveedor();

		for (Proveedor prov : provList) {

			// TIEMPO DE TOLERANCIA ANTES DE ALERTAR
			Integer soaPendingReceptionTime = prov.getTiempoMaximoFinFlujo() > 0 ? prov.getTiempoMaximoFinFlujo() : soaReceptionTimeDefault;

			Optional<Company> optCompany = companyRepository.findByRut(prov.getCodigo());
			if (optCompany.isEmpty()) {
				LOGGER.info("No se encontró una empresa con el código : {}", prov.getCodigo());
				continue;
			}

			Company company = optCompany.get();

			if (!companyIds.containsKey(company.getId())) {
				companyIds.put(company.getId(), company);

				LocalDate fechaActivacion = LocalDate.now();
				try {
					fechaActivacion = LocalDateTime.parse(prov.getFechaActivacion(), dtf).toLocalDate();
				} catch (DateTimeParseException e) {

				}
				CompanyData providerData = new CompanyData();
				providerData.setActivationDate(fechaActivacion);
				providerData.setPendingTime(soaPendingReceptionTime);
				providerData.setContractedStates(null);
				companyDatas.put(company.getId(), providerData);

			}

		}

		if (companyIds.isEmpty()) {
			return;
		}

		ObjectFactory objFactory = new ObjectFactory();
		ListaProveedoresOrdenesPendientes qlistaPendProv = objFactory.createListaProveedoresOrdenesPendientes();
		Proveedores proveedores = objFactory.createListaProveedoresOrdenesPendientesProveedores();

		for (Long vendorId : companyIds.keySet()) {

			Company company = companyIds.get(vendorId);
			CompanyData data = companyDatas.get(vendorId);

			Proveedores.Proveedor proveedor = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedor();
			proveedor.setCodigo(company.getRut());
			proveedor.setNombre(company.getName());

			// CONSULTA POR CANTIDAD DE OC PENDIENTES DE RECEPCIONAR DESDE SOA
			// List<Order> orders = orderRepository.getPendingOrdersByVendor(ok.getId(), soaPendingTime, vendorId,
			// companyDatas.get(vendorId).getActivationDate());

			// Buscar los detalles de notificacion de tipo orden con flag "recibido" en false para la empresa
			LocalDateTime soaPendingTime = LocalDateTime.now().minusMinutes(companyDatas.get(vendorId).getPendingTime());
			List<NotificationDetail> ordersList = notificationDetailRepository.findByCompanyUserIdAndTopicAndReceived(company.getUserId(), Topic.ORDERS, false);
			proveedor.setNumpendientes(ordersList.size());
			// Solo se informa hasta un máximo de ordenes
			Integer maxOrdersToInform = Math.min(ordersList.size(), maxPendingOrders);
			Detalles detalles = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetalles();
			List<Detalle> detallesList = detalles.getDetalle();

			// INFORMACION DE LAS ORDENES PENDIENTES
			for (int i = 0; i < maxOrdersToInform; i++) {

				NotificationDetail detail = ordersList.get(i);

				// obtener el cuerpo json y transformarlo a objeto
				String jsonBody = detail.getJsonBody();
				OrderData orderdata = JsonUtils.getObjectFromJson(jsonBody, OrderData.class);

				Detalle detalle = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetallesDetalle();

				SOAStateData estadoSOA = getSOAState(detail);
				String fecha = dtf.format(estadoSOA.getWhen());

				detalle.setEstadoSoaOC(estadoSOA.getState());
				detalle.setUltimoCambioEstadoSOA(fecha);
				detalle.setNumeroOC(orderdata.getId().toString());
				detalle.setEstadoOC("Liberada");
				detalle.setTipoOC("");

				detallesList.add(detalle);
			}

			if (detallesList != null && detallesList.size() > 0)
				proveedor.setDetalles(detalles);

			proveedores.getProveedor().add(proveedor);
		}

		qlistaPendProv.setNombreportal(serviceConstants.getNombrePortal());
		qlistaPendProv.setCodcomprador(solicitudProveedoresOrdenesPendientes.getCodcomprador());
		qlistaPendProv.setProveedores(proveedores);

		try {
			// OBTIENE EL XML FINAL
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			m.marshal(qlistaPendProv, sw);
			String result = sw.toString();
			LOGGER.info("Mensaje ListaProveedoresOrdenesPendientes: " + result);

			// ENVIA A LA COLA DE SOA
			messageservice.send(soaOutboundQueueName, result);

		} catch (JAXBException e) {
			LOGGER.error("Error al realizar operación", e);
		}

	}

	private static JAXBContext getJC() {
		if (jcCtxListadoOrdenesPendientes == null)
			try {
				jcCtxListadoOrdenesPendientes = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes");
			} catch (JAXBException e) {
				LOGGER.error("Error al obtener JAXBContext", e);
				throw new RuntimeException("Error al obtener JAXBContext", e);
			}
		return jcCtxListadoOrdenesPendientes;
	}

	private SOAStateData getSOAState(NotificationDetail detail) {
		SOAStateData result = new SOAStateData();
		if (!detail.isInformed()) {
			result.setState(SOAStates.POR_NOTIFICAR);
			result.setWhen(detail.getWhen());
		} else if (!detail.isSent()) {
			result.setState(SOAStates.NOTIFICADO);
			result.setWhen(detail.getDateInformed());
		} else if (!detail.isReceived()) {
			result.setState(SOAStates.ENVIADO);
			result.setWhen(detail.getDateSent());
		} else {
			result.setState(SOAStates.RECIBIDO_OK);
			result.setWhen(detail.getDateReceived());
		}
		return result;
	}

	@Data
	private class SOAStateData {

		private String state;

		private LocalDateTime when;

	}

	@Data
	private class CompanyData {

		private LocalDate activationDate;

		private Integer pendingTime;

		private String[] contractedStates;

	}

}
