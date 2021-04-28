package bbr.b2b.logistic.msgregionalb2b;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetailId;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetailId;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.BoxServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OutReceptionDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OutReceptionServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.PalletServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailPK;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryPK;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionW;
import bbr.b2b.regional.logistic.deliveries.entities.OutReceptionDetailId;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionErrorServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.evaluations.managers.interfaces.EvaluationReportManagerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.xml.qreception.Detalle;
import bbr.b2b.regional.logistic.xml.qreception.Detalles;
import bbr.b2b.regional.logistic.xml.qreception.QRECEPCION;

@Stateless(name = "handlers/XmlToReception")
public class XmlToReception implements XmlToReceptionLocal {

	@EJB
	ItemServerLocal itemserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DeliveryServerLocal deliveryserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	@EJB
	DeliveryStateServerLocal deliverystateserver;

	@EJB
	ReceptionErrorServerLocal receptionerrorserver;

	@EJB
	DatingServerLocal datingserver;

	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;

	@EJB
	BulkServerLocal bulkserver;

	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	TaxDocumentServerLocal taxdocumentserver;

	@EJB
	BoxServerLocal boxserver;

	@EJB
	PalletServerLocal palletserver;

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	@EJB
	OutReceptionServerLocal outreceptionserver;

	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	OutReceptionDetailServerLocal outreceptiondetailserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanager;

	@EJB
	DeliveryReportManagerLocal deliveryreportmanager;

	@EJB
	EvaluationReportManagerLocal evaluationmanager;

	CommonQueueUtils qutils = CommonQueueUtils.getInstance();

	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private void doValidateReceptionMessage(QRECEPCION qrecepcion) throws LoadDataException {

		// N°MERO DE SECUENCIA
		qutils.datoObligatorio(qrecepcion.getSecuencia(), "No se especifica N°mero de secuencia");
		qutils.validaLargo(qrecepcion.getSecuencia(), 12, "El N°mero de secuencia debe tener un largo de a los más 12 dígitos");

		// N°MERO DE RECEPción
		qutils.datoObligatorio(qrecepcion.getNrecepcion(), "No se especifica N°mero de recepción");
		qutils.validaLargo(qrecepcion.getNrecepcion(), 13, "El N°mero de recepción debe tener un largo de a los más 13 dígitos");

		// N°MERO DE ASN
		qutils.datoObligatorio(qrecepcion.getNasn(), "No se especifica N°mero de asn");
		qutils.validaLargo(qrecepcion.getNasn(), 9, "El N°mero de asn debe tener un largo de a los más 9 dígitos");

		// WAREHOUSE
		qutils.datoObligatorio(qrecepcion.getWarehouse(), "No se especifica warehouse");
		qutils.validaLargo(qrecepcion.getWarehouse(), 5, "El warehouse debe tener un largo de a los más 5 caracter(es)");

		// FECHA DE RECEPción
		qutils.datoObligatorio(qrecepcion.getFRecepcion(), "No se especifica fecha de recepción");
		qutils.validaLargo(qrecepcion.getFRecepcion(), 6, "La fecha de recepción debe tener un largo de a los más 6 caracter(es)");

		try {
			qutils.getDate(qrecepcion.getFRecepcion(), "yyMMdd");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha de recepción debe tener formato 'YYMMDD'");
		}

		// DETALLES
		if (qrecepcion.getDetalles() != null) {

			Detalles detparser = qrecepcion.getDetalles();

			List<Detalle> detalles = detparser.getDetalle();

			for (int j = 0; j < detalles.size(); j++) {

				// código LOCAL DESTINO
				qutils.datoObligatorio(detalles.get(j).getCodloc(), "No se especifica código local de destino");
				qutils.validaLargo(detalles.get(j).getCodloc(), 5, "El código de local de destino debe tener un largo de a los más 5 caracter(es)");

				// N°MERO DE ORDEN
				qutils.datoObligatorio(detalles.get(j).getNorden(), "No se especifica N°mero de orden");
				qutils.validaLargo(detalles.get(j).getNorden(), 6, "El N°mero de orden debe tener un largo de a los más 6 dígito(s)");

				// código SKU PRODUCTO
				qutils.datoObligatorio(detalles.get(j).getCodProducto(), "No se especifica código SKU producto");
				qutils.validaLargo(detalles.get(j).getCodProducto(), 9, "El código SKU producto debe tener un largo de a los más 9 caracter(es)");

				// CANTIDAD RECIBIDA
				qutils.datoObligatorio(detalles.get(j).getCantidad(), "No se especifica cantidad");
				qutils.validaLargo(detalles.get(j).getCantidad(), 9, "La cantidad debe tener un largo de a los más 9 dígito(s)");
			}
		}
	}

	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}

	private Long loadOutReception(QRECEPCION recepcionParser) throws ServiceException, LoadDataException {

		Long nreception = recepcionParser.getNrecepcion();

		// Se busca la recepción manual por N°mero de recepción
		OutReceptionW[] outRArr = outreceptionserver.getByPropertyAsArray("outdelivery", nreception);

		// Verificar que el N°mero de recepción no existe en el B2B
		if (outRArr != null && outRArr.length > 0)
			throw new LoadDataException("La recepción manual No " + recepcionParser.getNrecepcion() + ", ya existe.");

		// Busca si existe el warehouse
		LocationW[] locArr = locationserver.getByPropertyAsArray("code", recepcionParser.getWarehouse());

		if (locArr == null || locArr.length == 0)
			throw new LoadDataException("No existe local con código " + recepcionParser.getWarehouse());

		LocationW deliverylocationw = locArr[0];

		// Validar si el local encontrado es warehouse
		if (!deliverylocationw.getWarehouse())
			throw new LoadDataException("El local de entrega no es una bodega");

		// Validar fecha de recepcion
		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		// FECHA DE RECEPCION
		Date f_reception = null;

		try {
			String f_receptionSTR = recepcionParser.getFRecepcion().trim();
			f_reception = sdf.parse(f_receptionSTR);

			cal.setTime(f_reception);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_reception = cal.getTime();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de recepción");
		}

		List<Detalle> detalle = recepcionParser.getDetalles().getDetalle();

		// Verifica si el mensaje contiene el detalle
		if (detalle.size() == 0)
			throw new LoadDataException("No existe detalle de la recepción manual");

		Iterator it = detalle.iterator();
		Detalle detail = null;

		// Seteo de datos en nueva recepción manual
		OutReceptionW outreception = new OutReceptionW();
		outreception.setOutdelivery(nreception);
		Long nasn = recepcionParser.getNasn();
		outreception.setNumber(nasn);
		outreception.setReceptiondate(f_reception);
		outreception.setLocationid(deliverylocationw.getId());

		// Se agrega la recepción manual
		outreception = outreceptionserver.addOutReception(outreception);

		PreDeliveryDetailW predeliveryw = null;
		OrderW orderw = null;
		LocationW locationw = null;
		ItemW itemw = null;
		OutReceptionDetailW orecepdetail = null;
		OrderDetailW orderdetailw = null;
		Long oldvendorid = 0L;
		Long vendorid = 0L;
		Double outunits = 0D;
		boolean isFirstTime = true;

		Map<Long, OrderW> ordersMap = new HashMap<Long, OrderW>();
		Map<OrderDetailId, OrderDetailW> orderdetailMap = new TreeMap<OrderDetailId, OrderDetailW>();
		Map<PreDeliveryDetailId, PreDeliveryDetailW> predeliveryMap = new TreeMap<PreDeliveryDetailId, PreDeliveryDetailW>();

		while (it.hasNext()) {

			detail = (Detalle) it.next();

			Long ordernumber = detail.getNorden();
			String itemcode = detail.getCodProducto();
			String localcode = detail.getCodloc();
			Double outreceivedunits = detail.getCantidad();

			orderw = null;
			locationw = null;
			itemw = null;

			OrderW[] ocArr = orderserver.getByPropertyAsArray("number", ordernumber);
			if(ocArr != null && ocArr.length == 0)
				throw new LoadDataException("La order " + ordernumber + " no existe");
			
			orderw = ocArr[0];

			vendorid = orderw.getVendorid();

			VendorW vendor = vendorserver.getById(vendorid);

			// Se obtiene el proveedor asociado a la primera orden encontrada en el detalle
			if (isFirstTime) {
				oldvendorid = vendorid;
				isFirstTime = false;

				// Se valida que no existe la misma recepción manual para el mismo proveedor
				// para esto, el N°mero de ASN y el ID del Proveedor no se debe repetir en una nueva recepción manual

				OutReceptionW[] or = outreceptionserver.getByPropertyAsArray("number", nasn);
				if ((or != null && or.length > 0) && or[0].getVendorid().equals(vendorid))
					throw new LoadDataException("La recepcion manual ya existe para el proveedor " + vendor.getName());
			}

			// Verifica que las ordenes que vengan en el detalle del mensaje correspondan a un mismo proveedor
			if (!oldvendorid.equals(vendorid))
				error("Las Ordenes registradas corresponden a mas de un proveedor.");

			// Guardar las ordenes en un mapa para setear las cantidades recepcionadas por fuera
			// Si en el detalle del mensaje que viene el N°mero de orden es nuevo, se guarda en el mapa
			if (!ordersMap.containsKey(ordernumber)) {
				orderw.setOutreceivedunits(outreceivedunits);
				ordersMap.put(ordernumber, orderw);
			} else {
				// si el detalle del mensaje correponde a una orden existente en el mapa,
				// se suma la nueva cantidad recepcionada con la que ya ten�a seteada
				orderw = (OrderW) ordersMap.get(ordernumber);
				outunits = orderw.getOutreceivedunits() + outreceivedunits;
				orderw.setOutreceivedunits(outunits);
				ordersMap.put(ordernumber, orderw);
			}
			outunits = 0D;

			// Se obtiene el local asociado al código que viene en el detalle
			LocationW[] loArr = locationserver.getByPropertyAsArray("code", localcode);

			if (loArr == null || loArr.length == 0)
				throw new LoadDataException("No existe local con código " + localcode);

			locationw = loArr[0];

			// Se obtiene el producto asociado al código que viene en el detalle
			ItemW[] itArr = itemserver.getByPropertyAsArray("internalcode", itemcode);

			if (itArr == null || itArr.length == 0)
				throw new LoadDataException("No existe producto con sku " + itemcode);

			itemw = itArr[0];

			OrderDetailId odPk = new OrderDetailId();
			odPk.setOrderid(orderw.getId());
			odPk.setItemid(itemw.getId());

			// Se guarda el detalle de las ordenes en un mapa para setear las cantidades recepcionadas por fuera
			// Si el detalle de orden es nuevo, dada la PK, se guarda en el mapa
			if (!orderdetailMap.containsKey(odPk)) {
				try {
					orderdetailw = orderdetailserver.getById(odPk);
				} catch (NotFoundException e) {
					throw new LoadDataException("El detalle de orden para el producto " + detail.getCodProducto() + " y la O/C " + detail.getNorden() + " no se ha encontrado");
				}

				orderdetailw.setOutreceivedunits(outreceivedunits);
				orderdetailMap.put(odPk, orderdetailw);
			} else {
				// si el detalle de orden ya existe en el mapa,
				// se suma la nueva cantidad recepcionada con la que ya ten�a seteada
				orderdetailw = (OrderDetailW) orderdetailMap.get(odPk);
				outunits = orderdetailw.getOutreceivedunits() + outreceivedunits;
				orderdetailw.setOutreceivedunits(outunits);
				orderdetailMap.put(odPk, orderdetailw);
			}

			PreDeliveryDetailId predeliveryPK = new PreDeliveryDetailId();
			predeliveryPK.setOrderid(orderw.getId());
			predeliveryPK.setLocationid(locationw.getId());
			predeliveryPK.setItemid(itemw.getId());

			try {
				predeliveryw = predeliverydetailserver.getById(predeliveryPK);
			} catch (NotFoundException e) {
				throw new LoadDataException("No existe el Predespacho asociado al item " + detail.getCodProducto() + ", local " + detail.getCodloc() + "y O/C " + detail.getNorden());
			}

			// Se guardan en un mapa las predistribuciones asociadas al detalle del mensaje
			if (!predeliveryMap.containsKey(predeliveryPK)) {
				// Se setean las cantidades recepcionada por fuera
				predeliveryw.setOutreceivedunits(outreceivedunits);
				predeliveryMap.put(predeliveryPK, predeliveryw);
			}

			orecepdetail = new OutReceptionDetailW();

			// veo si el detalle ya existe, si es asi, se rechaza
			OutReceptionDetailId orpk = new OutReceptionDetailId();
			orpk.setItemid(itemw.getId());
			orpk.setLocationid(locationw.getId());
			orpk.setOrderid(orderw.getId());
			orpk.setOutreceptionid(outreception.getId());
			try {
				orecepdetail = outreceptiondetailserver.getById(orpk);
				if (orecepdetail != null)
					throw new LoadDataException("Ya existe detalle para recepción manual");
			} catch (NotFoundException e1) {
				// Si no existe esta todo bien
			}

			// Seteo de los datos asociados al detalle de recepciones manuales
			orecepdetail.setItemid(itemw.getId());
			orecepdetail.setLocationid(locationw.getId());
			orecepdetail.setOrderid(orderw.getId());
			orecepdetail.setOutreceptionid(outreception.getId());
			orecepdetail.setReceivedunits(detail.getCantidad());

			orecepdetail = outreceptiondetailserver.addOutReceptionDetail(orecepdetail);

		} // fin while

		// Set proveedor y flag "inprocess" en true
		outreception.setVendorid(oldvendorid);
		outreception.setInprocess(true);
		// Se actualiza la recepción manual con los nuevos valores
		outreception = outreceptionserver.updateOutReception(outreception);

		// Se registra la recepción por fuera a nivel de la OC.
		Collection orders = ordersMap.values();
		for (Iterator iter = orders.iterator(); iter.hasNext();) {
			OrderW order = (OrderW) iter.next();
			order = orderserver.updateOrder(order);
		}

		// Se registra la recepción por fuera a nivel de Detalle de orden.
		Collection orderdetails = orderdetailMap.values();
		for (Iterator iter = orderdetails.iterator(); iter.hasNext();) {
			OrderDetailW od = (OrderDetailW) iter.next();
			od = orderdetailserver.updateOrderDetail(od);
		}

		// Se registra la recepción por fuera a nivel de la Predistribución.
		Collection predeliveries = predeliveryMap.values();
		for (Iterator iter = predeliveries.iterator(); iter.hasNext();) {
			PreDeliveryDetailW pd = (PreDeliveryDetailW) iter.next();
			pd = predeliverydetailserver.updatePreDeliveryDetail(pd);
		}

		// Se reclaculan las OC
		orders = ordersMap.values();
		for (Iterator iter = orders.iterator(); iter.hasNext();) {
			OrderW order = (OrderW) iter.next();
			buyorderreportmanager.doCalculateTotalOfOrder(order.getId());
		}

		return nreception;
	}

	private Boolean loadImportReception(QRECEPCION recepcionParser, DeliveryW deliveryw) throws ServiceException, LoadDataException {

		Long n_reception = recepcionParser.getNrecepcion();
		List<Detalle> detalle = recepcionParser.getDetalles().getDetalle();

		Iterator it = detalle.iterator();
		Detalle detail = null;

		Map<Long, OrderW> ordenesMap = new HashMap<Long, OrderW>();
		Map<String, LocationW> localMap = new HashMap<String, LocationW>();
		Map<String, ItemW> itemMap = new HashMap<String, ItemW>();

		Map<OrderDeliveryDetailPK, OrderDeliveryDetailW> oddMap = new TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW>();
		Map<OrderDeliveryPK, OrderDeliveryW> odMap = new TreeMap<OrderDeliveryPK, OrderDeliveryW>();

		Map<Long, TreeMap<OrderDetailPK, OrderDetailW>> orderdetailMap = new HashMap<Long, TreeMap<OrderDetailPK, OrderDetailW>>();
		OrderDeliveryW orderdeliveryw = null;

		List<Long> orderIds = new ArrayList<Long>();

		Date now = new Date();

		// Llenar los mapas de Ordenes, Items, OrderDeliveries y OrderDeliveryDetails del lote
		OrderW[] orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where odl.order = oc and odl.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < orders.length; i++) {
			OrderW orderw = orders[i];
			Long ordernumber = orderw.getNumber();
			if (!ordenesMap.containsKey(ordernumber))
				ordenesMap.put(ordernumber, orderw);
			orderIds.add(orderw.getId());
		}

		ItemW[] items = itemserver.getByQueryAsArray("select it from Item as it, OrderDeliveryDetail as odd where odd.item = it and odd.orderdelivery.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < items.length; i++) {
			ItemW itemw = items[i];
			String itemcode = itemw.getInternalcode();
			if (!itemMap.containsKey(itemcode))
				itemMap.put(itemcode, itemw);
		}

		LocationW[] locations = locationserver.getByQueryAsArray("select lo from Location as lo, OrderDeliveryDetail as odd where odd.location = lo and odd.orderdelivery.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < locations.length; i++) {
			LocationW locationw = locations[i];
			String localcode = new String(locationw.getCode());
			if (!localMap.containsKey(localcode))
				localMap.put(localcode, locationw);
		}

		OrderDeliveryW[] ods = orderdeliveryserver.getByPropertyAsArray("id.deliveryid", deliveryw.getId());
		for (int i = 0; i < ods.length; i++) {
			OrderDeliveryPK odPk = new OrderDeliveryPK();
			odPk.setDeliveryid(ods[i].getDeliveryid());
			odPk.setOrderid(ods[i].getOrderid());
			odMap.put(odPk, ods[i]);
		}

		OrderDeliveryDetailW[] oddetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", deliveryw.getId());
		for (int i = 0; i < oddetails.length; i++) {
			OrderDeliveryDetailPK oddPk = new OrderDeliveryDetailPK();
			oddPk.setDeliveryid(oddetails[i].getDeliveryid());
			oddPk.setItemid(oddetails[i].getItemid());
			oddPk.setLocationid(oddetails[i].getLocationid());
			oddPk.setOrderid(oddetails[i].getOrderid());
			// Setear en cero lo recibido anteriormente por otra recepcion
			oddetails[i].setReceivedunits(0D);
			orderdeliverydetailserver.updateOrderDeliveryDetail(oddetails[i]);
			oddMap.put(oddPk, oddetails[i]);
		}

		OrderW orderw = null;
		ItemW itemw = null;
		LocationW locationw = null;

		// ///////////RAG: REVISO SI TODAS LAS UNIDADES VIENEN EN CERO///////////////
		float receivedunits = 0;
		while (it.hasNext()) {
			detail = (Detalle) it.next();

			// Busca orden
			Long ordernumber = detail.getNorden();

			if (!ordenesMap.containsKey(ordernumber))
				throw new LoadDataException("No existe la orden " + detail.getNorden() + " � no est� asociada al lote");

			orderw = (OrderW) ordenesMap.get(ordernumber);

			// Busca producto
			String itemcode = detail.getCodProducto();

			if (!itemMap.containsKey(itemcode)) {
				// Si el item esta asociado al lote, tambien esta asociado a la OC
				// ver si esta asociado a la OC
				TreeMap<OrderDetailPK, OrderDetailW> detallesOC = new TreeMap<OrderDetailPK, OrderDetailW>();
				if (orderdetailMap.containsKey(ordernumber))
					detallesOC = (TreeMap) orderdetailMap.get(ordernumber);
				else {
					// Busco el detalle de la Oc y la guardo en mapa
					OrderDetailW[] orderdetail = orderdetailserver.getByPropertyAsArray("id.orderid", orderw.getId());
					detallesOC = new TreeMap<OrderDetailPK, OrderDetailW>();
					for (int i = 0; i < orderdetail.length; i++) {
						OrderDetailPK orderdetailPk = new OrderDetailPK(orderdetail[i].getOrderid(), orderdetail[i].getItemid());
						detallesOC.put(orderdetailPk, orderdetail[i]);
					}
					orderdetailMap.put(ordernumber, detallesOC);
				}

				ItemW[] itArr = itemserver.getByPropertyAsArray("internalcode", detail.getCodProducto());

				if (itArr == null || itArr.length == 0)
					throw new LoadDataException("No existe producto con sku " + detail.getCodProducto());

				itemw = itArr[0];

				OrderDetailPK orderdetailPk = new OrderDetailPK(orderw.getId(), itemw.getId());
				if (!orderdetailMap.containsKey(orderdetailPk))
					throw new LoadDataException("El item " + detail.getCodProducto() + " no est� asociado a la Orden");

			} else
				itemw = (ItemW) itemMap.get(itemcode);

			// Busca local
			String localcode = new String(detail.getCodloc());

			if (!localMap.containsKey(localcode))
				throw new LoadDataException("No existe el local " + detail.getCodloc() + " � no est� asociado al lote");

			locationw = (LocationW) localMap.get(localcode);

			// Verificamos si existe detalle de despacho
			OrderDeliveryDetailW orderdeliverydetailw = null;
			OrderDeliveryDetailPK orderdeliverydetailpk = new OrderDeliveryDetailPK();

			orderdeliverydetailpk.setDeliveryid(deliveryw.getId());
			orderdeliverydetailpk.setOrderid(orderw.getId());
			orderdeliverydetailpk.setItemid(itemw.getId());
			orderdeliverydetailpk.setLocationid(locationw.getId());

			if (oddMap.containsKey(orderdeliverydetailpk))
				orderdeliverydetailw = (OrderDeliveryDetailW) oddMap.get(orderdeliverydetailpk);
			else {
				// Si no se encuentra el detalle de despacho se debe crear.
				// Si tampoco existe la predistribución, tambión se debe crear
				PreDeliveryDetailId predeliverypk = new PreDeliveryDetailId(orderw.getId(), itemw.getId(), locationw.getId());
				try {
					predeliverydetailserver.getById(predeliverypk);
				} catch (NotFoundException e2) {
					// No existe, asi que se crea la predistribución
					PreDeliveryDetailW predelivery = new PreDeliveryDetailW();
					predelivery.setOrderid(orderw.getId());
					predelivery.setItemid(itemw.getId());
					predelivery.setLocationid(locationw.getId());
					predelivery.setUnits(0D);
					predeliverydetailserver.addPreDeliveryDetail(predelivery);
				}

				// Ahora creo el detalle del despacho
				orderdeliverydetailw = new OrderDeliveryDetailW();
				orderdeliverydetailw.setDeliveryid(deliveryw.getId());
				orderdeliverydetailw.setItemid(itemw.getId());
				orderdeliverydetailw.setLocationid(locationw.getId());
				orderdeliverydetailw.setOrderid(orderw.getId());
				orderdeliverydetailw.setAvailableunits(0D);
				orderdeliverydetailw.setPendingunits(0D);
				orderdeliverydetailw = orderdeliverydetailserver.addOrderDeliveryDetail(orderdeliverydetailw);

				// Lo agregamos al mapa
				oddMap.put(orderdeliverydetailpk, orderdeliverydetailw);
			}

			// Seteamos cantidad recepcionada
			orderdeliverydetailw.setReceivedunits(detail.getCantidad());

			// Buscamos OrderDelivery
			OrderDeliveryPK orderdeliverypk = new OrderDeliveryPK(orderw.getId(), deliveryw.getId());
			if (!odMap.containsKey(orderdeliverypk)) {
				throw new LoadDataException("No se puede encontrar despacho de orden");
			}

			// Veo si todos son cero
			receivedunits += detail.getCantidad();

		} // ///// FIN ////////RAG: REVISO SI TODAS LAS UNIDADES VIENEN EN CERO///////////////

		//
		// 3A1 Todos las recepciones vienen en cero
		// 3A1.1 El sistema elimina el PL asociado al lote
		// 3A1.2 El lote cambia a estado rechazado
		if (receivedunits == 0) {
			// JPE 20190904
			// Obtener las facturas asociadas a los detalles de bultos
			Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(deliveryw.getId());

			// Borrar los detalles de bultos
			bulkdetailserver.deleteByProperty("id.deliveryid", deliveryw.getId());
			
			// JPE 20190904
			// Eliminar las facturas asociadas a los detalles de bultos
			if (taxdocumentids != null && taxdocumentids.length > 0) {
				taxdocumentserver.deleteByIds(taxdocumentids);
			}
			
			// Elimino PL
			BulkW[] bulks = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", deliveryw.getId());

			// ELIMINA LOS DETALLES DE BULTO
			for (int j = 0; j < bulks.length; j++) {
				BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bulks[j].getId());

				if (boxArr != null && boxArr.length > 0) {
					boxserver.deleteElement(bulks[j].getId());
				} else {
					palletserver.deleteElement(bulks[j].getId());
				}
			}

			// Cambio estado a rechazado
			DeliveryStateTypeW delstatetype = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");

			DeliveryStateW deliverystatew = new DeliveryStateW();

			// Genera nuevo estado del despacho
			deliverystatew.setDeliverystatetypeid(delstatetype.getId());
			deliverystatew.setWhen(now);
			deliverystatew.setDeliveryid(deliveryw.getId());

			deliverystatew = deliverystateserver.addDeliveryState(deliverystatew);

			// Actualizamos el lote
			deliveryw.setCurrentstatetypedate(now);
			deliveryw.setCurrentstatetypeid(delstatetype.getId());
			deliveryw.setReceptionnumber(n_reception);

			deliveryserver.updateDelivery(deliveryw);
						
			// IRA: Agrego evaluacion por sistema, RECHAZO COMPLETO DEL PROVEEDOR
			ReceptionErrorW recerror = receptionerrorserver.getByPropertyAsSingleResult("code", "RCP");

			// Definir cual va para este tipo
			EvaluationDetailW[] evaldetails = new EvaluationDetailW[1];
			evaldetails[0] = new EvaluationDetailW();
			evaldetails[0].setReceptionerrorid(new Long(recerror.getId()));
			doAddEvaluation(deliveryw, evaldetails);

			deliveryserver.flush();
			
			// Obtener las entregas de órdenes y cerrarlas
			for (Map.Entry<OrderDeliveryPK, OrderDeliveryW> e : odMap.entrySet()) {
				orderdeliveryw = e.getValue();
				orderdeliveryw.setClosed(true);
				orderdeliveryserver.updateOrderDelivery(orderdeliveryw);
			}
			orderdeliveryserver.flush();

			// RECALCULA UNIDADES
			for (Long id : orderIds) {
				buyorderreportmanager.doCalculateTotalOfOrder(id);
			}

			return false; // para que no envie la guia de despacho a Paris
		}

		// ////los recorro nuevamente, pero ahora se que si existe al menos un valor > 0
		// las validaciones se repiten
		it = detalle.iterator();
		while (it.hasNext()) {
			detail = (Detalle) it.next();
			Long ordernumber = detail.getNorden();

			if (!ordenesMap.containsKey(ordernumber))
				throw new LoadDataException("No existe la orden " + detail.getNorden() + " � no est� asociada al lote");

			orderw = (OrderW) ordenesMap.get(ordernumber);

			// Busca producto
			String itemcode = new String(detail.getCodProducto());

			if (!itemMap.containsKey(itemcode))
				throw new LoadDataException("No existe el item " + detail.getCodProducto() + " � no est� asociado al lote");

			itemw = (ItemW) itemMap.get(itemcode);

			// Busca local
			String localcode = new String(detail.getCodloc());

			if (!localMap.containsKey(localcode))
				throw new LoadDataException("No existe el local " + detail.getCodloc() + " � no est� asociado al lote");

			locationw = (LocationW) localMap.get(localcode);

			// Verificamos si existe detalle de despacho
			OrderDeliveryDetailW orderdeliverydetailw = null;
			OrderDeliveryDetailPK orderdeliverydetailpk = new OrderDeliveryDetailPK();

			orderdeliverydetailpk.setDeliveryid(deliveryw.getId());
			orderdeliverydetailpk.setOrderid(orderw.getId());
			orderdeliverydetailpk.setItemid(itemw.getId());
			orderdeliverydetailpk.setLocationid(locationw.getId());

			if (oddMap.containsKey(orderdeliverydetailpk)) {
				orderdeliverydetailw = (OrderDeliveryDetailW) oddMap.get(orderdeliverydetailpk);
			}
			else {
				throw new LoadDataException("No se encontr� detalle de despacho para la recepción");
			}

			// Seteamos cantidad recepcionada
			orderdeliverydetailw.setReceivedunits(detail.getCantidad());

			// Actualizamos OrderDeliveryDetail, Detalle de Despacho
			orderdeliverydetailserver.updateOrderDeliveryDetail(orderdeliverydetailw);

			// Buscamos OrderDelivery
			OrderDeliveryPK orderdeliverypk = new OrderDeliveryPK(orderw.getId(), deliveryw.getId());
			if (odMap.containsKey(orderdeliverypk)) {
				orderdeliveryw = (OrderDeliveryW) odMap.get(orderdeliverypk);
			}
			else {
				throw new LoadDataException("No se puede encontrar despacho de orden");
			}

			// Cerramos el Despacho de Orden
			orderdeliveryw.setClosed(true);

			// Actualizamos el Despacho de Orden
			orderdeliveryserver.updateOrderDelivery(orderdeliveryw);
		} // fin while

		// Cambiar estado del Delivery
		DeliveryStateTypeW delstatetype = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");

		DeliveryStateW deliverystatew = new DeliveryStateW();

		// Genera nuevo estado del despacho
		deliverystatew.setDeliverystatetypeid(delstatetype.getId());
		deliverystatew.setWhen(now);
		deliverystatew.setDeliveryid(deliveryw.getId());

		deliverystatew = deliverystateserver.addDeliveryState(deliverystatew);

		// Actualizamos el lote
		deliveryw.setCurrentstatetypedate(new Date());
		deliveryw.setCurrentstatetypeid(delstatetype.getId());
		deliveryw.setReceptionnumber(n_reception);

		deliveryserver.updateDelivery(deliveryw);

		// Actualizo unidades para OC
		Iterator it2 = ordenesMap.values().iterator();
		OrderW order;
		while (it2.hasNext()) {
			order = (OrderW) it2.next();
			buyorderreportmanager.doCalculateTotalOfOrder(order.getId());
		}

		// Agregar evaluación por el 100% si no la tiene
		// Crear el detalle de evaluación (largo cero -> sin errores)
		EvaluationDetailW[] evaldetails = new EvaluationDetailW[0];
		return doAddEvaluation(deliveryw, evaldetails);
	}

	private Boolean doAddEvaluation(DeliveryW deliveryw, EvaluationDetailW[] evaldetails) throws LoadDataException {

		// Agregar evaluación por el 100% si no la tiene
		// Buscar la cita
		try {
			DatingW[] dtArr = datingserver.getByPropertyAsArray("delivery.id", deliveryw.getId());
			DatingW datingw = dtArr[0];

			ReceptionEvaluationW evaluationw = null;

			// Buscar la evaluación si existe. Si existe, salir
			ReceptionEvaluationW[] rcpEvaArr = receptionevaluationserver.getByPropertyAsArray("dating.id", datingw.getId());

			if (rcpEvaArr == null || rcpEvaArr.length == 0) {

				// Crear la evaluación de sistema, sin errores
				evaluationw = new ReceptionEvaluationW();
				evaluationw.setDatingid(datingw.getId());
				evaluationw.setAutogenerated(true);
				evaluationw.setComments("Generada por el sistema");
				evaluationw.setWhen(new Date());
				evaluationw.setWho("Sistema");
			} else {
				evaluationw = rcpEvaArr[0];
				return true;
			}

			// Crear el detalle de evaluación (largo cero -> sin errores)
			// EvaluationDetailW[] evaldetails = new EvaluationDetailW[0];

			evaluationw = evaluationmanager.doAddOrUpdateReceptionEvaluationAndDetails(evaluationw, evaldetails);
			return true;

		} catch (ServiceException e) {
			e.printStackTrace();
			throw new LoadDataException("Error en la creación de evaluaciones");
		}
	}

	public Boolean processMessage(QRECEPCION recepcionParser) throws LoadDataException, ServiceException {
		
		// Validación del Mensaje
		doValidateReceptionMessage(recepcionParser);

		/** *************************************************************************************** */
		/** ******************************** CARGA DE RECEPción *********************************** */
		/** *************************************************************************************** */
		List<Long> orderIds = new ArrayList<Long>();

		Map<Long, OrderW> ordenesMap = new HashMap<Long, OrderW>();
		Map<String, LocationW> localMap = new HashMap<String, LocationW>();
		Map<String, ItemW> itemMap = new HashMap<String, ItemW>();

		Map<OrderDeliveryDetailPK, OrderDeliveryDetailW> oddMap = new TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW>();
		Map<OrderDeliveryPK, OrderDeliveryW> odMap = new TreeMap<OrderDeliveryPK, OrderDeliveryW>();

		Long n_reception = recepcionParser.getNrecepcion();
		Long asn = recepcionParser.getNasn();

		OrderDeliveryW orderdeliveryw = null;

		Date now = new Date();

		DeliveryStateTypeW dst_agendada = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
		DeliveryStateTypeW delstatetypeinreception = deliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RECEPCION");

		// JPE 20190508
		// Buscar el despacho (nacional o internacional) por ASN
		DeliveryW[] dlArr = deliveryserver.getByQueryAsArray("select dl from Delivery as dl, Dating as dt where dt.delivery = dl and dt.number = " + asn);
		if (dlArr == null || dlArr.length <= 0) {
			dlArr = deliveryserver.getByQueryAsArray("select dl from Delivery as dl, OrderDelivery as od where od.delivery = dl and od.asnimp = " + asn);
			if (dlArr == null || dlArr.length == 0) {
				// Si no existe el ASN entonces corresponde a una recepción manual
				loadOutReception(recepcionParser);
				return false;
			}
		}
		DeliveryW deliveryw = dlArr[0];
				
		// Validar que la recepción es para una OC nacional
		VendorW vendor = null;
		try {
			vendor = vendorserver.getById(deliveryw.getVendorid());
		} catch (NotFoundException e) {
			throw new LoadDataException("No existe proveedor");
		}
		
		boolean imported = vendor.getRut().equalsIgnoreCase("IMP");

		if (vendor.getDomestic()) {
			// Si llega una recepción y el lote no est� en estado "Agendada" se rechaza el mensaje
			if (!deliveryw.getCurrentstatetypeid().equals(dst_agendada.getId())) {
				throw new LoadDataException("El lote no se encuentra en estado 'Agendada'");
			}
		}
		else if (imported) {
			// Si llega una recepción y el lote no est� en estado "Agendada" o "En recepción", se rechaza el mensaje
			if (!deliveryw.getCurrentstatetypeid().equals(dst_agendada.getId()) &&
					!deliveryw.getCurrentstatetypeid().equals(delstatetypeinreception.getId())) {
				throw new LoadDataException("El lote no se encuentra en estado 'Agendada' o 'En Recepción'");
			}			
		}
		else {
			// Depende del proceso interno si envía o no gu�a de despacho como respuesta
			return loadImportReception(recepcionParser, deliveryw);
		}
		
		// LOCAL DE ENTREGA
		LocationW[] dlLocArr = locationserver.getByPropertyAsArray("code", recepcionParser.getWarehouse());
		if (dlLocArr == null || dlLocArr.length == 0) {
			throw new LoadDataException("No existe local con el código " + recepcionParser.getWarehouse());
		}
		LocationW deliverylocationw = dlLocArr[0];

		// Local encontrado es warehouse?
		if (!deliverylocationw.getWarehouse()) {
			throw new LoadDataException("El local de entrega no es una bodega");
		}

		// Validar que el local indicado corresponde al local de entrega del lote
		if (!deliverylocationw.getId().equals(deliveryw.getLocationid())) {
			throw new LoadDataException("El local de entrega informado no corresponde al local de entrega del lote");
		}

		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		// FECHA DE RECEPCION
		Date f_reception = null;

		try {
			String f_receptionSTR = recepcionParser.getFRecepcion().trim();
			f_reception = sdf.parse(f_receptionSTR);

			cal.setTime(f_reception);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_reception = cal.getTime();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de recepción");
		}

		List<Detalle> detalle = recepcionParser.getDetalles().getDetalle();
		if (detalle.size() == 0) {
			throw new LoadDataException("No existe detalle de la recepción");
		}

		Iterator it = detalle.iterator();
		Detalle detail = null;

		// Llenar los mapas de Ordenes, Items, OrderDeliveries y OrderDeliveryDetails del lote
		OrderW[] orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where odl.order = oc and odl.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < orders.length; i++) {
			OrderW orderw = orders[i];
			Long ordernumber = orderw.getNumber();
			if (!ordenesMap.containsKey(ordernumber)) {
				ordenesMap.put(ordernumber, orderw);
				orderIds.add(orderw.getId());
			}
		}

		ItemW[] items = itemserver.getByQueryAsArray("select it from Item as it, OrderDeliveryDetail as odd where odd.item = it and odd.orderdelivery.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < items.length; i++) {
			ItemW itemw = items[i];
			String itemcode = itemw.getInternalcode();
			if (!itemMap.containsKey(itemcode)) {
				itemMap.put(itemcode, itemw);
			}
		}

		LocationW[] locations = locationserver.getByQueryAsArray("select lo from Location as lo, OrderDeliveryDetail as odd where odd.location = lo and odd.orderdelivery.delivery.id = " + deliveryw.getId());
		for (int i = 0; i < locations.length; i++) {
			LocationW locationw = locations[i];
			String localcode = locationw.getCode();
			if (!localMap.containsKey(localcode)) {
				localMap.put(localcode, locationw);
			}
		}

		OrderDeliveryW[] ods = orderdeliveryserver.getByPropertyAsArray("id.deliveryid", deliveryw.getId());
		for (int i = 0; i < ods.length; i++) {
			OrderDeliveryPK odPk = new OrderDeliveryPK();
			odPk.setDeliveryid(ods[i].getDeliveryid());
			odPk.setOrderid(ods[i].getOrderid());
			odMap.put(odPk, ods[i]);
		}

		OrderDeliveryDetailW[] oddetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", deliveryw.getId());
		for (int i = 0; i < oddetails.length; i++) {
			OrderDeliveryDetailPK oddPk = new OrderDeliveryDetailPK();
			oddPk.setDeliveryid(oddetails[i].getDeliveryid());
			oddPk.setItemid(oddetails[i].getItemid());
			oddPk.setLocationid(oddetails[i].getLocationid());
			oddPk.setOrderid(oddetails[i].getOrderid());
			
			if (vendor.getDomestic()) {
				oddetails[i].setReceivedunits(0D); // Setear en cero lo recibido anteriormente por otra recepción
				orderdeliverydetailserver.updateOrderDeliveryDetail(oddetails[i]);
			}
			
			oddMap.put(oddPk, oddetails[i]);
		}

		OrderW orderw = null;
		ItemW itemw = null;
		LocationW locationw = null;

		// ///////////RAG: REVISO SI TODAS LAS UNIDADES VIENEN EN CERO///////////////
		double receivedunits = 0;
		while (it.hasNext()) {
			detail = (Detalle) it.next();

			// Busca orden
			Long ordernumber = detail.getNorden();
			if (!ordenesMap.containsKey(ordernumber)) {
				throw new LoadDataException("No existe la orden " + detail.getNorden() + " o no est� asociada al lote");
			}
			orderw = (OrderW) ordenesMap.get(ordernumber);

			// Busca producto
			String itemcode = detail.getCodProducto();
			if (!itemMap.containsKey(itemcode)) {
				throw new LoadDataException("No existe el item " + detail.getCodProducto() + " o no est� asociado al lote");
			}
			itemw = (ItemW) itemMap.get(itemcode);

			// Busca local
			String localcode = detail.getCodloc();
			if (!localMap.containsKey(localcode)) {
				throw new LoadDataException("No existe el local " + detail.getCodloc() + " o no est� asociado al lote");
			}
			locationw = (LocationW) localMap.get(localcode);

			// Verificamos si existe detalle de despacho
			OrderDeliveryDetailPK orderdeliverydetailpk = new OrderDeliveryDetailPK();
			orderdeliverydetailpk.setDeliveryid(deliveryw.getId());
			orderdeliverydetailpk.setOrderid(orderw.getId());
			orderdeliverydetailpk.setItemid(itemw.getId());
			orderdeliverydetailpk.setLocationid(locationw.getId());

			if (!oddMap.containsKey(orderdeliverydetailpk)) {
				throw new LoadDataException("No se encontr� detalle de despacho para la recepción");
			}
			OrderDeliveryDetailW orderdeliverydetailw = (OrderDeliveryDetailW) oddMap.get(orderdeliverydetailpk);

			// Seteamos cantidad recepcionada
			// 20150720: Podr�an no venir agrupadas por oc/sku/local
			orderdeliverydetailw.setReceivedunits(orderdeliverydetailw.getReceivedunits() + detail.getCantidad());
			oddMap.put(orderdeliverydetailpk, orderdeliverydetailw);

			// Buscamos OrderDelivery
			OrderDeliveryPK orderdeliverypk = new OrderDeliveryPK(orderw.getId(), deliveryw.getId());
			if (!odMap.containsKey(orderdeliverypk)) {
				throw new LoadDataException("No se puede encontrar despacho de orden");
			}

			// Veo si todos son cero
			receivedunits += detail.getCantidad();
		} // ///// FIN ////////RAG: REVISO SI TODAS LAS UNIDADES VIENEN EN CERO///////////////

		//
		// 3A1 Todos las recepciones vienen en cero
		// 3A1.1 El sistema elimina el PL asociado al lote
		// 3A1.2 El lote cambia a estado rechazado
		if (receivedunits == 0) {
			// JPE 20190904
			// Obtener las facturas asociadas a los detalles de bultos
			Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(deliveryw.getId());

			// Borrar los detalles de bultos
			bulkdetailserver.deleteByProperty("id.deliveryid", deliveryw.getId());
			
			// JPE 20190904
			// Eliminar las facturas asociadas a los detalles de bultos
			if (taxdocumentids != null && taxdocumentids.length > 0) {
				taxdocumentserver.deleteByIds(taxdocumentids);
			}
			
			// Elimino PL
			BulkW[] bulks = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", deliveryw.getId());

			// ELIMINA LOS DETALLES DE BULTO
			for (int j = 0; j < bulks.length; j++) {
				BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bulks[j].getId());
				if (boxArr != null && boxArr.length > 0) {
					boxserver.deleteElement(bulks[j].getId());
				} else {
					palletserver.deleteElement(bulks[j].getId());
				}
			}

			// Cambio estado a rechazado
			DeliveryStateTypeW delstatetype = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");

			// Genera nuevo estado del despacho
			DeliveryStateW deliverystatew = new DeliveryStateW();

			deliverystatew.setDeliverystatetypeid(delstatetype.getId());
			deliverystatew.setWhen(now);
			deliverystatew.setDeliveryid(deliveryw.getId());

			deliverystatew = deliverystateserver.addDeliveryState(deliverystatew);

			// Actualizamos el lote
			deliveryw.setCurrentstatetypedate(now);
			deliveryw.setCurrentstatetypeid(delstatetype.getId());
			deliveryw.setReceptionnumber(n_reception);

			deliveryserver.updateDelivery(deliveryw);

			// Agrego evaluacion por sistema, RECHAZO COMPLETO DEL PROVEEDOR
			ReceptionErrorW recerror = receptionerrorserver.getByPropertyAsSingleResult("code", "RCP");

			// Definir cual va para este tipo
			EvaluationDetailW[] evaldetails = new EvaluationDetailW[1];
			evaldetails[0] = new EvaluationDetailW();
			evaldetails[0].setReceptionerrorid(recerror.getId());

			doAddEvaluation(deliveryw, evaldetails);

			deliveryserver.flush();
			
			// Obtener las entregas de órdenes y cerrarlas
			for (Map.Entry<OrderDeliveryPK, OrderDeliveryW> e : odMap.entrySet()) {
				orderdeliveryw = e.getValue();
				orderdeliveryw.setClosed(true);
				orderdeliveryserver.updateOrderDelivery(orderdeliveryw);
			}
			orderdeliveryserver.flush();

			// RECALCULA UNIDADES
			for (Long id : orderIds) {
				buyorderreportmanager.doCalculateTotalOfOrder(id);
			}

			// CALCULA BULTOS
			deliveryreportmanager.doCalculateTotalBulkDetailOfDelivery(deliveryw.getId());

			return false; // para que no envie la guia de despacho a Paris
		}

		Map<Long, BulkW> bulkMap = new TreeMap<Long, BulkW>();
		Map<OrderDeliveryDetailPK, List<BulkDetailW>> bulkDetailMap = new TreeMap<OrderDeliveryDetailPK, List<BulkDetailW>>();
		List<BulkDetailW> bdList = null;

		List<Long> bulkDeleteArray = new ArrayList<Long>();
		boolean deleteBulks = false;

		// ////los recorro nuevamente, pero ahora se que s� existe al menos un valor > 0
		it = detalle.iterator();
		while (it.hasNext()) {
			detail = (Detalle) it.next();

			// Validamos n_orden
			Long ordernumber = detail.getNorden();
			orderw = (OrderW) ordenesMap.get(ordernumber);

			// Busca producto
			String itemcode = detail.getCodProducto();
			itemw = (ItemW) itemMap.get(itemcode);

			// Busca Local
			String localcode = detail.getCodloc();
			locationw = (LocationW) localMap.get(localcode);

			// Verificamos si existe detalle de despacho
			OrderDeliveryDetailPK orderDeliveryDetailPK = new OrderDeliveryDetailPK();
			orderDeliveryDetailPK.setDeliveryid(deliveryw.getId());
			orderDeliveryDetailPK.setOrderid(orderw.getId());
			orderDeliveryDetailPK.setItemid(itemw.getId());
			orderDeliveryDetailPK.setLocationid(locationw.getId());
			OrderDeliveryDetailW orderdeliverydetailw = (OrderDeliveryDetailW) oddMap.get(orderDeliveryDetailPK);

			// Buscamos OrderDelivery
			OrderDeliveryPK orderDeliveryPK = new OrderDeliveryPK(orderw.getId(), deliveryw.getId());
			orderdeliveryw = (OrderDeliveryW) odMap.get(orderDeliveryPK);

			// 5A1 La recepción de una combinación Lote-Orden-producto-Local viene en 0
			// 5A1.2 El sistema elimina todos los bultos asociados a dicho detalle de despacho.
			if (orderdeliverydetailw.getReceivedunits() == 0) {
				// se elimina los bultos asociados al detalle de despacho
				if (bulkMap.isEmpty()) { // para que los cargue una sola vez
					BulkW[] bulks = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", deliveryw.getId());
					for (BulkW bulk : bulks) {
						bulkMap.put(bulk.getId(), bulk);
					}
				}

				if (bulkDetailMap.isEmpty()) {
					// Para que los cargue una sola vez
					BulkDetailW[] bulkDetails = bulkdetailserver.getByPropertyAsArray("id.deliveryid", deliveryw.getId());
					for (BulkDetailW bulkDetail : bulkDetails) {
						OrderDeliveryDetailPK oddpk = new OrderDeliveryDetailPK();
						oddpk.setDeliveryid(bulkDetail.getDeliveryid());
						oddpk.setItemid(bulkDetail.getItemid());
						oddpk.setLocationid(bulkDetail.getLocationid());
						oddpk.setOrderid(bulkDetail.getOrderid());
						
						// Guardo como clave el OrderDeliveryDetailPK para poder obtener los detalles de bultos
						if (bulkDetailMap.containsKey(oddpk)) {
							bdList = bulkDetailMap.get(oddpk);
						}
						else {
							bdList = new ArrayList<BulkDetailW>();
						}
						bdList.add(bulkDetail);
						bulkDetailMap.put(oddpk, bdList);
					}
				}

				// Busco el detalle de bulto
				if (!bulkDetailMap.containsKey(orderDeliveryDetailPK)) {
					throw new LoadDataException("No se encuentra detalle de bulto para la recepción");
				}

				// Guardo en ArrayList el listado de bulto a eliminar, No los borro ahora pq se podrian repetir
				deleteBulks = true;
				BulkW bulk = null;
				for (BulkDetailW bulkDetail : bulkDetailMap.get(orderDeliveryDetailPK)) {
					bulk = (BulkW) bulkMap.get(bulkDetail.getBulkid());
					if (!bulkDeleteArray.contains(bulk.getId())) {
						bulkDeleteArray.add(bulk.getId());
					}
				}
			}
			else {
				// Actualizamos OrderDeliveryDetail, Detalle de Despacho
				orderdeliverydetailserver.updateOrderDeliveryDetail(orderdeliverydetailw);

				// Buscamos OrderDelivery
				orderDeliveryPK = new OrderDeliveryPK(orderw.getId(), deliveryw.getId());
				if (!odMap.containsKey(orderDeliveryPK)) {
					throw new LoadDataException("No se puede encontrar despacho de orden");
				}

				orderdeliveryw = (OrderDeliveryW) odMap.get(orderDeliveryPK);
			}

			// Cerramos el Despacho de Orden
			orderdeliveryw.setClosed(true);			
		}
		
		// Actualizamos los despachos de Orden
		for (OrderDeliveryW od : odMap.values()) {
			orderdeliveryserver.updateOrderDelivery(od);
		}

		// Veo si tengo bultos que borrar
		if (deleteBulks) {
			Long[] bulkids = (Long[]) bulkDeleteArray.toArray(new Long[bulkDeleteArray.size()]);
			
			// JPE 20190904
			// Obtener las facturas asociadas a los detalles de bultos
			Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByBulks(bulkids);
			
			for (int i = 0; i < bulkids.length; i++) {
				logger.info("Eliminado bulto para lote " + deliveryw.getId() + " bulkid:" + bulkids[i]);
				
				bulkdetailserver.deleteByProperty("id.bulkid", bulkids[i]);
				bulkdetailserver.flush();
				bulkserver.deleteElement(bulkids[i]);
			}
			logger.info("Eliminado(s) " + bulkids.length + " bultos para lote " + deliveryw.getId());
			
			// JPE 20200519
			// Eliminar las facturas que quedaron sin detalles de bultos
			if (taxdocumentids != null && taxdocumentids.length > 0) {
				int total = taxdocumentserver.deleteTaxDocumentsWithoutBulkDetailByDelivery(deliveryw.getId(), taxdocumentids);
				
				logger.info("Eliminado(s) " + total + " documentos para lote " + deliveryw.getId());
			}			
		}

		// Cambiar estado del Delivery
		DeliveryStateTypeW delstatetype = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
		
		// Validar si todos los orderdeliveries est�n con el campo "closed" en true.
		// en caso de que no sea as�, la entrega se dejar� en estado "EN RECEPCION"
		if (imported) {
			boolean isClosed = true;
			for (OrderDeliveryW od : odMap.values()) {
				if (!od.getClosed()) {
					isClosed = false;
					break;
				}
			}
			
			if (!isClosed) {
				delstatetype = delstatetypeinreception;
			}
		}
		
		// Genera nuevo estado del despacho
		DeliveryStateW deliverystatew = new DeliveryStateW();
		deliverystatew.setDeliverystatetypeid(delstatetype.getId());
		deliverystatew.setWhen(now);
		deliverystatew.setDeliveryid(deliveryw.getId());

		deliverystatew = deliverystateserver.addDeliveryState(deliverystatew);

		// Actualizamos el lote
		deliveryw.setCurrentstatetypedate(now);
		deliveryw.setCurrentstatetypeid(delstatetype.getId());
		deliveryw.setReceptionnumber(n_reception);

		deliveryserver.updateDelivery(deliveryw);

		// Agregar evaluación por el 100% si no la tiene
		// Crear el detalle de evaluación (largo cero -> sin errores)
		EvaluationDetailW[] evaldetails = new EvaluationDetailW[0];
		boolean result = doAddEvaluation(deliveryw, evaldetails);

		// CALCULA LOS BULTOS
		deliveryreportmanager.doCalculateTotalBulkDetailOfDelivery(deliveryw.getId());

		// RECALCULAR UNIDADES
		for (Long id : orderIds) {
			buyorderreportmanager.doCalculateTotalOfOrder(id);
		}

		logger.info("Recepción N° " + n_reception + " cargada");
		return result;
	}

	public void doChangeStateOrder(QRECEPCION recepcionParser) throws LoadDataException, ServiceException {

		// 2014-01-08: solo se cambia a Recepcionadas las oc que vienen en el mensaje
		List<Long> ocList = new ArrayList<Long>();
		List<Detalle> detalle = recepcionParser.getDetalles().getDetalle();
		if (detalle.size() == 0)
			throw new LoadDataException("No existe detalle de la recepción");

		Iterator it = detalle.iterator();
		Detalle detail = null;

		while (it.hasNext()) {
			detail = (Detalle) it.next();
			Long norden = detail.getNorden();
			if (!ocList.contains(norden))
				ocList.add(norden);
		}

		DeliveryW deliveryw = null;
		DeliveryW[] dlArr = null;
		OutReceptionW[] or = null;
		OrderW[] orders = null;

		OrderStateTypeW ostRecep = orderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");

		// buscamos el lote, por asn
		Long asn = 0L;

		asn = recepcionParser.getNasn();
		dlArr = deliveryserver.getByQueryAsArray("select dl from Delivery as dl, Dating as dt where dt.delivery = dl and dt.number = " + asn);

		if (dlArr == null || dlArr.length == 0) {
			or = outreceptionserver.getByPropertyAsArray("number", asn);

			if (or == null || or.length == 0) {
				return;
			}

			// busco para cada orden
			orders = orderserver.getByQueryAsArray("select distinct oc from Order as oc, OutReceptionDetail as ord where ord.id.outreceptionid = " + or[0].getId() + " and ord.predeliverydetail.orderdetail.order = oc");
		} else {
			deliveryw = dlArr[0];

			// busco para cada orden del delivery
			orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where odl.order = oc and odl.delivery.id = " + deliveryw.getId());
		}

		OrderW[] ordersdata = null;
		Date now = new Date();
		for (int i = 0; i < orders.length; i++) {

			// 2013-01-08: solo se cambia a "Recepcionada" las oc que vienen en el mensaje
			Long ordernumber = orders[i].getNumber();
			if (!ocList.contains(ordernumber)) {
				logger.debug("Ignorar cambio a Recepcionada de OC" + orders[i].getNumber() + " - recepcion de asn:" + asn);
				continue;
			}
			if (orders[i].getVevcd()) { // solo para oc VeV CD
				ordersdata = orderserver.getByQueryAsArray("select oc from Order as oc where oc.vendor.id = " + orders[i].getVendorid() + " and oc.number = " + orders[i].getNumber() + " and oc.vevcd = TRUE");

				if (ordersdata != null && ordersdata.length > 0) {
					if (ordersdata[0].getReceivedunits().doubleValue() >= ordersdata[0].getNeedunits().doubleValue()) {

						// Cambio estado a recepcionada
						orders[i].setCurrentstatetypeid(ostRecep.getId());
						orders[i].setCurrentstatetypedate(now);
						orderserver.updateOrder(orders[i]);

						// Guardo historial
						OrderStateW os = new OrderStateW();
						os.setOrderid(orders[i].getId());
						os.setOrderstatetypeid(ostRecep.getId());
						os.setWhen(now);
						orderstateserver.addOrderState(os);
					}
				}
			}
		}
	}
}
