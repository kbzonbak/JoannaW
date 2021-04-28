package bbr.b2b.logistic.msgregionalb2b;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.TaxDocumentW;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.xml.qrm.QRM;

@Stateless(name = "handlers/XmlToTaxDocumentRM")
public class XmlToTaxDocumentRM implements XmlToTaxDocumentRMLocal {

	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	TaxDocumentServerLocal taxdocumentserver;

	CommonQueueUtils qutils = CommonQueueUtils.getInstance();

	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMdd");
	
	private void doValidateMessage(QRM qrm) throws LoadDataException {

		// N°mero de ASN
		if (qrm.getNasn() == null) {
			throw new LoadDataException("No se especifica el tag para el N°mero de ASN");
		}
		if (qrm.getNasn() <= 0) {
			throw new LoadDataException("N°mero de ASN debe ser mayor a cero");
		}
		qutils.validaLargo(qrm.getNasn(), 10, "Valor en campo para N°mero de ASN supera los 10 dígitos");


		// N°mero de OC
		if (qrm.getNorden() == null) {
			throw new LoadDataException("No se especifica el tag para el N°mero de OC");
		}
		if (qrm.getNorden() <= 0) {
			throw new LoadDataException("N°mero de OC debe ser mayor a cero");
		}
		qutils.validaLargo(qrm.getNorden(), 6, "Valor en campo para N°mero de OC supera los 6 dígitos");
		
		// N°mero de factura asociada a la OC
		if (qrm.getFactura() == null) {
			throw new LoadDataException("No se especifica tag para N°mero de factura");
		}
		if (qrm.getFactura() <= 0) {
			throw new LoadDataException("N°mero de factura debe ser mayor a cero");
		}
		qutils.validaLargo(qrm.getFactura(), 10, "Valor en campo para N°mero de factura supera los 10 dígitos");
		
		// N°mero de RM asociado a la OC y factura
		if (qrm.getRm() == null) {
			throw new LoadDataException("No se especifica tag para N°mero de RM");
		}
		if (qrm.getRm().trim().equals("")) {
			throw new LoadDataException("Campo para N°mero de RM est� vac�o");
		}
		qutils.validaLargo(qrm.getRm().trim(), 20, "Valor en campo para N°mero de RM supera los 20 caracteres");
		
		// Fecha de recepción
		if (qrm.getFrecepcion() == null) {
			throw new LoadDataException("No se especifica el tag para fecha de recepción");
		}
		if (qrm.getFrecepcion().trim().equals("")) {
			throw new LoadDataException("Campo para fecha de recepción est� vac�o");
		}
		qutils.validaLargo(qrm.getFrecepcion().trim(), 6, "Valor en campo para fecha de recepción supera los 6 caracteres");
		try {
			sdfDate.parse(qrm.getFrecepcion().trim());
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la fecha de recepción debe ser 'yyMMdd'");
		}
	}

	public void processMessage(QRM qrm) throws LoadDataException, NotFoundException, OperationFailedException, AccessDeniedException {
		
		// Validar el mensaje
		doValidateMessage(qrm);

		/** *************************************************************************************** */
		/** ******************************** CARGA DE RM *********************************** */
		/** *************************************************************************************** */
		// Validar que el N°mero de ASN (nacional) exista
		Long asn = qrm.getNasn();
		DatingW[] datings = datingserver.getByPropertyAsArray("number", asn);
		if (datings == null || datings.length == 0) {
			throw new LoadDataException("El N°mero de ASN " + asn + " no existe en el sistema");
		}
		DatingW dating = datings[0];
		
		// Obtener el despacho asociado al ASN
		DeliveryW delivery = deliveryserver.getById(dating.getDeliveryid());
		
		// Validar que la OC pertenezca al despacho asociado al N°mero de ASN
		Long orderNumber = qrm.getNorden();
		PropertyInfoDTO prop1 = new PropertyInfoDTO("delivery.id", "deliveryid", delivery.getId());
		PropertyInfoDTO prop2 = new PropertyInfoDTO("order.number", "ordernumber", orderNumber);
		OrderDeliveryW[] orderDeliveries = orderdeliveryserver.getByPropertiesAsArray(prop1, prop2);
		if (orderDeliveries == null || orderDeliveries.length == 0) {
			throw new LoadDataException("La OC " + orderNumber + " no existe para el ASN " + asn);
		}
		OrderDeliveryW orderDelivery = orderDeliveries[0];
		
		// Validar que el despacho tenga facturas asociadas
		prop1 = new PropertyInfoDTO("orderdeliverydetail.orderdelivery.delivery.id", "deliveryid", orderDelivery.getDeliveryid());
		prop2 = new PropertyInfoDTO("orderdeliverydetail.orderdelivery.order.id", "orderid", orderDelivery.getOrderid());
		BulkDetailW[] bulkDetails = bulkdetailserver.getByPropertiesAsArray(prop1, prop2);
		if (bulkDetails == null || bulkDetails.length == 0) {
			throw new LoadDataException("El despacho " + delivery.getNumber() + " para el ASN " + asn + " no tiene facturas cargadas");
		}
		
		// Validar que el N°mero de factura indicado pertenezca al despacho y est� asociado a la OC
		Long taxDocumentNumber = qrm.getFactura();
		TaxDocumentW taxDocument = taxdocumentserver.getByOrderDeliveryAndNumber(orderDelivery.getOrderid(), orderDelivery.getDeliveryid(), taxDocumentNumber);
		if (taxDocument == null) {
			throw new LoadDataException("No existe la factura " + taxDocumentNumber + " para la OC " + orderNumber + " en el despacho " + delivery.getNumber());
		}
		
		// Valida que la factura no posea un N°mero de RM previo
		if (taxDocument.getReceptionnumber() != null && !taxDocument.getReceptionnumber().equals("")) {
			throw new LoadDataException("La factura " + taxDocumentNumber + " posee un N°mero de RM cargado previamente (N° " + taxDocument.getReceptionnumber() + ")");
		}
		
		// Guardar el N°mero de RM y la fecha recepción en la factura correspondiente
		String receptionNumber = qrm.getRm().trim();
		Date receptionDate = null;
		try {
			receptionDate = sdfDate.parse(qrm.getFrecepcion().trim());
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la fecha de recepción debe ser 'yyMMdd'");
		}
		
		taxDocument.setReceptionnumber(receptionNumber);
		taxDocument.setReceptiondate(receptionDate);
		
		taxdocumentserver.updateTaxDocument(taxDocument);
	}
}