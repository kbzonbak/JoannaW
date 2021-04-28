package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.TransactionTimeout;

import bbr.b2b.b2blink.logistic.xml.Rec_Interno.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Comprador;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Detalles;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.EdiData;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Locales;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Locales.Local;
import bbr.b2b.b2blink.logistic.xml.Rec_Interno.Recepcion.Vendedor;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionDetailServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionLocationServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaRecStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionDetailW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionLocationW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;

@Stateless(name = "handlers/RecOCSendToXml")
public class RecOCSendToXml implements RecOCSendToXmlLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.Rec_Interno");
		return jc;
	}

	@EJB
	BuyerServerLocal buyerserver = null;

	@EJB
	LocationServerLocal locationserver = null;
	
	@EJB
	ReceptionLocationServerLocal receptionlocationserver = null;

	@EJB
	OrderServerLocal orderserver = null;

	@EJB
	OrderTypeServerLocal ordertypeserver = null;

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	ReceptionServerLocal receptionserver = null;

	@EJB
	ReceptionDetailServerLocal receptiondetailserver = null;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	SoaRecStateServerLocal soarecstateserver;

	@EJB
	SchedulerManagerLocal schedulermanager;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(180000)
	public void processMessage(ReceptionW reception, VendorW vendor, BuyerW buyer, int count, int totalcount) throws ServiceException {
		String msgcontent = null;
		String nombreportal = System.getProperty("NombrePortal");

		SoaStateTypeW soaEnvSt = soastatetypeserver.getByPropertyAsSingleResult("code", "ENVIADO"); 

		// SE CONSTRUYE EL XML
		ObjectFactory objFactory = new ObjectFactory();

		try {
			// Crear estructura del mensaje
			Recepcion qRecepcion = objFactory.createRecepcion();

			// Nombre Portal
			qRecepcion.setNombreportal(nombreportal);

			// Número de Recepción
			qRecepcion.setNorecepcion(reception.getReceptionnumber());

			// N�mero de OC
			qRecepcion.setNorden(reception.getOrdernumber());

			// Tipo OC
			qRecepcion.setTipoOc(reception.getOrdertypename());

			// Fecha de Recepción
			Locale locale = new Locale("es", "CL");
			GregorianCalendar gcal = new GregorianCalendar(locale);
			gcal.setTime(reception.getReceptiondate());
			XMLGregorianCalendar xmlgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			
			qRecepcion.setFechaRecepcion(xmlgcal);

			// Total de la OC
			qRecepcion.setTotal(reception.getTotal());

			// Codigo Local de entrega
			String deliveryplaceCode = "";
			if(reception.getDeliveryplaceid() == null || reception.getDeliveryplaceid().equals(0L)){
				qRecepcion.setCodLocalEntrega("");
				
			}else{
				LocationW deliveryplace = locationserver.getById(reception.getDeliveryplaceid());
				qRecepcion.setCodLocalEntrega(deliveryplace.getCode());
				deliveryplaceCode = deliveryplace.getCode();
			}

			// Forma de pago
			qRecepcion.setFormaPago(reception.getPaymentcondition());

			// Comentarios
			qRecepcion.setComentarios(reception.getObservation());

			// Responsable
			qRecepcion.setResponsable(reception.getResponsible());

			// Comprador
			Comprador comprador = setComprador(objFactory, buyer);
			qRecepcion.setComprador(comprador);

			// Vendedor
			Vendedor vendedor = setVendedor(objFactory, vendor);
			qRecepcion.setVendedor(vendedor);
			
			//edi
			qRecepcion.setEdiData(setEdiData(objFactory, buyer, vendor, deliveryplaceCode));
			
			// Locales
			qRecepcion.setLocales(setLocales(objFactory, reception));
			
			// Detalle de Recepción
			ReceptionDetailW[] receptiondetail = receptiondetailserver.getByPropertyAsArrayOrdered("reception.id", reception.getId(),new OrderCriteriaDTO("position", true));
			Detalles detalles = setReceptionDetail(objFactory, receptiondetail);
			qRecepcion.setDetalles(detalles);

			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qRecepcion, sw);
			msgcontent = sw.toString();
			logger.info(msgcontent);

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al construir mensaje", e);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al construir mensaje", e);
		}

		// Enviar mensaje a la cola ESB
		try {
			schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl", "EnviadoRecSoa", String.valueOf(reception.getOrdernumber()), msgcontent, Charset.forName("UTF-8"));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OperationFailedException("Error al enviar mensaje", ex);
		}
		logger.info("BBR SOA (" + count + " de " + totalcount + "): Se ha enviado la Recepción de Orden Nro. " + String.valueOf(reception.getReceptionnumber()) + " a SOA para proveedor RUT: " + vendor.getRut());

		// Se marca la reception como enviada

		Date now = new Date();

		reception.setSoastatetypeid(soaEnvSt.getId());
		reception = receptionserver.updateReception(reception);

		SoaRecStateW soarecstate = new SoaRecStateW();
		soarecstate.setReceptionid(reception.getId());
		soarecstate.setSoastatetypeid(soaEnvSt.getId());
		soarecstate.setWhen(now);

		soarecstate = soarecstateserver.addSoaRecState(soarecstate);

	}

	private Comprador setComprador(ObjectFactory objFactory, BuyerW buyer){
		Comprador comprador = objFactory.createRecepcionComprador();
		comprador.setRut(buyer.getRut());
		comprador.setRazonSocial(buyer.getName());
		comprador.setUnidadNegocio(buyer.getBusinessarea());
		return comprador;
	}

	private Detalles setReceptionDetail(ObjectFactory objFactory, ReceptionDetailW[] receptiondetail) throws NotFoundException, OperationFailedException {
		Detalles detalles = objFactory.createRecepcionDetalles();
		List detallesList = detalles.getDetalle();

		for (ReceptionDetailW recdetail : receptiondetail) {
			
			Detalle detalle = objFactory.createRecepcionDetallesDetalle();

			detalle.setItem(recdetail.getPosition());

			// Cod. producto comprador
			detalle.setCodProdComp(recdetail.getSkubuyer());

			// Cod. producto vendedor
			detalle.setCodProdVendedor(recdetail.getSkuvendor());

			// EAN13
			detalle.setEan13(recdetail.getEan13());

			// Descripción producto
			detalle.setDescripcionProd(recdetail.getProductdescription());

			// C�digo de empaque
			detalle.setCodEmpaque(recdetail.getCodeumc());

			// Descripci�n de empaque
			detalle.setDescEmpaque(recdetail.getDescriptionumc());

			// Descripción UM
			detalle.setDescUmUnidad(recdetail.getDescriptionumb());

			// Prod. empaque
			detalle.setProdEmpaque(recdetail.getQuantityumc());

			// Cantidad recepcionada
			detalle.setCantidadRecepcionada(recdetail.getReceivedquantity().floatValue());

			// Costo Lista
			detalle.setCostoLista(recdetail.getListcost());

			// Costo final
			detalle.setCostoFinal(recdetail.getFinalcost());

			detallesList.add(detalle);
		}
		return detalles;
	}


	

	private Vendedor setVendedor(ObjectFactory objFactory, VendorW vendor) throws NotFoundException, OperationFailedException {
		Vendedor vendedor = objFactory.createRecepcionVendedor();
		vendedor.setRut(vendor.getRut());
		vendedor.setRazonSocial(vendor.getName());
		vendedor.setContacto(vendor.getEmail() != null ? vendor.getEmail() : "");
		vendedor.setDireccion(vendor.getAddress() != null ? vendor.getAddress() : "");
		vendedor.setTelefono(vendor.getPhone() != null ? vendor.getPhone() : "");
		return vendedor;
	}
	

	private Locales setLocales(ObjectFactory objFactory, ReceptionW reception) throws OperationFailedException, NotFoundException {
		
		PropertyInfoDTO[] props = new PropertyInfoDTO[1];
		props[0] = new PropertyInfoDTO("id.receptionid", "reception_id", reception.getId());
		
		ReceptionLocationW[] locationslist = receptionlocationserver.getByPropertiesAsArray(props);
		Locales locales = objFactory.createRecepcionLocales();
		List<Local> localesList = locales.getLocal();
		if (locationslist.length == 0 || locationslist == null){
			throw new OperationFailedException("no se encontraron locales para la recepcion: " +reception.getReceptionnumber());
		}else{
		
			for (ReceptionLocationW receptionLocationW : locationslist) {
				LocationW location = locationserver.getById(receptionLocationW.getLocationid());
				
				Local local = objFactory.createRecepcionLocalesLocal();
				local.setCod(location.getCode());
				local.setNombre(location.getName());
				local.setDireccion(location.getName());
				local.setEan(location.getCode());
				localesList.add(local);
			}	
		}
		return locales;
	}
	
	private EdiData setEdiData(ObjectFactory objFactory, BuyerW buyerW, VendorW vendorW, String deliveryPlaceCode) throws ServiceException {
		EdiData qedidata = objFactory.createRecepcionEdiData();
		qedidata.setSenderIdentification(buyerW.getGln());
		qedidata.setRecipientIdentification(vendorW.getGln());
		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber("");
		qedidata.setCountrycode("");
		qedidata.setBuyerCode(buyerW.getCode());
		qedidata.setBuyerLocationCode(deliveryPlaceCode);
		return qedidata;
	}
}
