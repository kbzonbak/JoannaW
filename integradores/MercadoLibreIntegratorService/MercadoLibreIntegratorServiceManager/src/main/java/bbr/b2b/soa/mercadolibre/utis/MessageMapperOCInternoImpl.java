package bbr.b2b.soa.mercadolibre.utis;

import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.NotificacionOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.CargosGenerales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Comprador;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.DescGenerales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle.CargosProd;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle.DescProducto;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.EdiData;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Locales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Locales.Local;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Predistribuciones;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Seccion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Vendedor;
import bbr.b2b.soa.mercadolibre.constants.ServiceConstants;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderItem;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.repository.classes.CompanyRepository;

@Component
public class MessageMapperOCInternoImpl implements IMessageMapper {

	private static JAXBContext jcCtxOrden = null;

	private static JAXBContext jcCtxNotificacionOrden = null;

	private static JAXBContext getOrderContext() throws JAXBException {
		if (jcCtxOrden == null)
			jcCtxOrden = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_Interno");
		return jcCtxOrden;
	}

	private static JAXBContext getNotificacionOrderContext() throws JAXBException {
		if (jcCtxNotificacionOrden == null)
			jcCtxNotificacionOrden = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionOrden");
		return jcCtxNotificacionOrden;
	}

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceConstants serviceConstants;

	public Optional<String> getOrderNotificationMessage(OrderData data) {
		try {
			// Crear una instancia del objeto de salida (para generar xml)
			Optional<NotificacionOrden> optNotif = getNotificacionOrder(data);
			if (optNotif == null || optNotif.isEmpty()) {
				return Optional.empty();
			}
			// Transformar el objeto de salida en string
			NotificacionOrden notificacion = optNotif.get();
			JAXBContext jc = getNotificacionOrderContext();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			m.marshal(notificacion, writer);
			String messagecontent = writer.toString();
			return Optional.of(messagecontent);
		} catch (JAXBException e) {
			return Optional.empty();
		}
	}

	public Optional<String> getOrderMessage(OrderData data) {
		try {
			// Crear una instancia del objeto de salida (para generar xml)
			Optional<Orden> optOrder = getOrder(data);
			if (optOrder == null || optOrder.isEmpty()) {
				return Optional.empty();
			}
			// Transformar el objeto de salida en string
			Orden order = optOrder.get();
			JAXBContext jc = getOrderContext();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			m.marshal(order, writer);
			String messagecontent = writer.toString();
			return Optional.of(messagecontent);
		} catch (JAXBException e) {
			return Optional.empty();
		}
	}

	private Optional<NotificacionOrden> getNotificacionOrder(OrderData data) {
		try {
			// Buscar la empresa relacionada con la orden
			Long userId = Long.parseLong(data.getSeller().getId());
			Optional<Company> optCompany = companyRepository.findByUserId(userId);
			if (optCompany == null || optCompany.isEmpty()) {
				return Optional.empty();
			}
			Company company = optCompany.get();

			// Construye el objeto de salida
			ObjectFactory objFactory = new ObjectFactory();
			NotificacionOrden notificacion = objFactory.createNotificacionOrden();
			notificacion.setNumorden(Long.toString(data.getId()));
			notificacion.setNombreportal(serviceConstants.getNombrePortal());
			notificacion.setCodcomprador(serviceConstants.getRetailerRut());
			notificacion.setCodproveedor(company.getRut());
			notificacion.setEstado("Liberada");

			return Optional.of(notificacion);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	private Optional<Orden> getOrder(OrderData data) {
		try {
			// Buscar la empresa relacionada con la orden
			Long userId = Long.parseLong(data.getSeller().getId());
			Optional<Company> optCompany = companyRepository.findByUserId(userId);
			if (optCompany == null || optCompany.isEmpty()) {
				return Optional.empty();
			}
			Company company = optCompany.get();

			// Construye el objeto de salida
			bbr.b2b.b2blink.logistic.xml.OC_Interno.ObjectFactory objFactory = new bbr.b2b.b2blink.logistic.xml.OC_Interno.ObjectFactory();
			Orden order = objFactory.createOrden();

			// Cabecera
			order.setNo(data.getId());
			order.setComentarios(data.getComment());
			order.setTipoOc("");
			order.setTipoOcBbr("VEV DDC");
			order.setEstadoOc("Liberada");
			order.setEstadoOcBbr("LIBERADA");
			order.setNombreportal(serviceConstants.getNombrePortal());
			order.setMoneda(data.getCurrency_id());
			order.setTotal(data.getTotal_amount());
			order.setCodLocalEntrega("");
			order.setComentarios("");
			order.setResponsable("");
			// Fecha Emisión -> date_created
			Optional<XMLGregorianCalendar> optDateCreated = getXMLGregorianCalendar(data.getDate_created());
			if (optDateCreated != null && optDateCreated.isPresent()) {
				order.setFechaEmision(optDateCreated.get());
			}
			// Fecha Vigencia -> date_closed
			Optional<XMLGregorianCalendar> optValidDate = getXMLGregorianCalendar(data.getDate_closed());
			if (optValidDate != null && optValidDate.isPresent()) {
				order.setFechaVigencia(optValidDate.get());
			}
			// Fecha Vto -> expiration_date
			Optional<XMLGregorianCalendar> optExpirationDate = getXMLGregorianCalendar(data.getExpiration_date());
			if (optExpirationDate != null && optExpirationDate.isPresent()) {
				order.setFechaVto(optExpirationDate.get());
			}

			if (data.getBilling_info() != null) {
				order.setNroBoleta(data.getBilling_info().getDoc_number());
			}

			// SECCION
			Seccion seccion = objFactory.createOrdenSeccion();
			seccion.setCodSeccion("");
			seccion.setSeccion("");
			order.setSeccion(seccion);

			// EDI DATA
			EdiData edidata = objFactory.createOrdenEdiData();
			edidata.setBillToPartner("");
			edidata.setBuyerCode("");
			edidata.setBuyerLocationCode("");
			edidata.setCorrelativeVendor("");
			edidata.setCountrycode("");
			edidata.setCountrycode("");
			edidata.setMessageReferenceNumber("");
			edidata.setRecipientIdentification("");
			edidata.setSenderIdentification("");
			order.setEdiData(edidata);

			// Comprador
			Comprador comprador = objFactory.createOrdenComprador();
			comprador.setRut(serviceConstants.getRetailerRut());
			comprador.setDigitoVerificador(serviceConstants.getRetailerDv());
			comprador.setRazonSocial(serviceConstants.getRetailerName());
			comprador.setUnidadNegocio(serviceConstants.getRetailerUn());
			order.setComprador(comprador);

			// Vendedor
			Vendedor vendedor = objFactory.createOrdenVendedor();
			vendedor.setRut(company.getRut());
			vendedor.setDigitoVerificador(company.getDv());
			vendedor.setRazonSocial(company.getName());
			vendedor.setContacto(company.getContactinfo());
			vendedor.setDireccion(company.getAddress());
			vendedor.setTelefono(company.getPhone());
			order.setVendedor(vendedor);

			// Locales
			Locales locales = objFactory.createOrdenLocales();
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod("");
			local.setDireccion("");
			local.setEan("");
			local.setNombre("");
			locales.getLocal().add(local);
			order.setLocales(locales);

			// Descuentos generales
			DescGenerales descuentos = objFactory.createOrdenDescGenerales();
			order.setDescGenerales(descuentos);

			// Cargos generales
			CargosGenerales cargos = objFactory.createOrdenCargosGenerales();
			order.setCargosGenerales(cargos);

			// Detalles
			Detalles details = objFactory.createOrdenDetalles();
			if (data.getOrder_items() != null && data.getOrder_items().length > 0) {
				for (int i = 0; i < data.getOrder_items().length; i++) {
					OrderItem orderitem = data.getOrder_items()[i];
					Detalle detail = objFactory.createOrdenDetallesDetalle();
					detail.setItem(i + 1);
					detail.setCodProdComp(orderitem.getItem().getId());
					detail.setCodProdVendedor(orderitem.getItem().getSeller_sku());
					detail.setEan13("");
					detail.setDescripcionProd(orderitem.getItem().getTitle());
					detail.setCodEmpaque("UN");
					detail.setDescEmpaque("UN");
					detail.setProdEmpaque(1f);
					detail.setCantidadEmpaque(orderitem.getQuantity());
					detail.setCostoLista(orderitem.getFull_unit_price());
					detail.setCostoFinal(orderitem.getUnit_price());

					// descuentos
					DescProducto descProd = objFactory.createOrdenDetallesDetalleDescProducto();
					detail.setDescProducto(descProd);
					// cargos
					CargosProd cargosProd = objFactory.createOrdenDetallesDetalleCargosProd();
					detail.setCargosProd(cargosProd);

					details.getDetalle().add(detail);
				}
			}
			order.setDetalles(details);

			// Predistribuciones
			Predistribuciones pred = objFactory.createOrdenPredistribuciones();
			order.setPredistribuciones(pred);

			return Optional.of(order);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	private Optional<XMLGregorianCalendar> getXMLGregorianCalendar(String datetime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime, formatter);
			GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);
			XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
			return Optional.of(xmlGregorianCalendar);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
