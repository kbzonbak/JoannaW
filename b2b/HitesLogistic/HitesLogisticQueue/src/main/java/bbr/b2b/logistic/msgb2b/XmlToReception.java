package bbr.b2b.logistic.msgb2b;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkDetailW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryDetailW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetailId;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryId;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.data.wrappers.ItemW;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.utils.CommonQueueUtils;
import bbr.b2b.logistic.xml.reception.Recepcion;
import bbr.b2b.logistic.xml.reception.Recepcion.Detalles.Detalle;

@Stateless(name = "handlers/XmlToReception")
public class XmlToReception implements XmlToReceptionLocal {

	// Estados de la recepción
	private static final Integer RX_COMPLETA 				= 0;
	private static final Integer RX_PARCIAL 				= 1;
	private static final Integer RX_RECHAZADA				= 2;

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanager;
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DocumentServerLocal documentserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	DvrDeliveryStateServerLocal dvrdeliverystateserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;
	
	@EJB
	DvrOrderStateServerLocal dvrorderstateserver;
	
	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver; 
	
	@EJB
	DvrOrderDeliveryDetailServerLocal dvrorderdeliverydetailserver;
	
	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	LocationServerLocal locationserver;
		
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();
	
	private String datePattern = "yyyy-MM-dd HH:mm";
	private DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(datePattern);
		
	// Valida campos obligatorios del mensaje
	private void doValidateOrderMessage(Recepcion receptionParser) throws LoadDataException {
		
		// Valida que no se repitan lineas del detalle
		// num_oc - cod_producto – posicion – cod_local_destino
		Set<String> lineDetailMsgSet = new HashSet<String>();
				
		// Tag num_asn
		qutils.datoObligatorio(receptionParser.getNumAsn(), "No se informa un valor para num_asn");
		
		// Tag fecha_recepcion
		qutils.datoObligatorio(receptionParser.getFechaRecepcion(), "No se informa un valor para fecha_recepcion");
		
		// Valida formato de fecha
		String receptionDateStr = receptionParser.getFechaRecepcion().trim();
		try {
			qutils.getDate(receptionDateStr, datePattern);
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la fecha_recepcion debe ser " + datePattern);
		}
		
		// Valida que sea una fecha real
		try {
			qutils.getLocalDate(receptionDateStr, datePattern);
		} catch (DateTimeParseException e) {
			throw new LoadDataException("La fecha_recepcion debe ser válida");
		}
		
		// Tag estado
		// Se valida que estado informado sea válido
		if (receptionParser.getEstado() != RX_COMPLETA && receptionParser.getEstado() != RX_PARCIAL && receptionParser.getEstado() != RX_RECHAZADA) {			
			throw new LoadDataException("Valor informado para estado de la recepción no es válido (" + receptionParser.getEstado() + ")");
		}

		// Detalles
		if (receptionParser.getDetalles() == null) {
			throw new LoadDataException("No se especifica tag para detalles");
		}
			
		// Valida que se informe al menos un detalle
		if (receptionParser.getDetalles().getDetalle().size() == 0) {
			throw new LoadDataException("No se informaron detalles para la recepción");
		}

		for (Detalle detalle : receptionParser.getDetalles().getDetalle()) {			
			// num_oc
			qutils.datoObligatorio(detalle.getNumOc(), "No se informa valor para num_oc en detalle de recepción");
			
			// posicion
			qutils.datoObligatorio(detalle.getPosicion(), "No se informa valor para posicion en detalle de recepción");
			
			// cod_producto
			qutils.datoObligatorio(detalle.getCodProducto(), "No se informa valor para cod_producto en detalle de recepción");
			
			// cod_local_destino	
			qutils.datoObligatorio(detalle.getCodLocalDestino(), "No se informa valor para cod_local_destino en detalle de recepción");
			
			// cantidades_umc	
			qutils.datoObligatorio(detalle.getCantidadesUmc(), "No se informa valor para cantidades_umc en detalle de recepción");
			
			// Valida que la combinación oc-sku-pos-local (num_oc - cod_producto – posicion - cod_local_destino) no se repita en la recepción.
			String uniqueDetail = detalle.getNumOc() + "_" + detalle.getCodProducto().trim() + "_" + detalle.getPosicion() + "_" + detalle.getCodLocalDestino().trim();
			if (!lineDetailMsgSet.contains(uniqueDetail)) {
				lineDetailMsgSet.add(uniqueDetail);
			}
			else {
				throw new LoadDataException("La combinación de OC " + detalle.getNumOc() + " – SKU " + detalle.getCodProducto().trim() + " – Posición " + detalle.getPosicion() + " – Local " + detalle.getCodLocalDestino().trim() + " no se puede repetir en la recepción");
			}
		}
	}

	public String processMessageOrder(Recepcion receptionParser) throws LoadDataException, ServiceException {
		
		// Validacion de datos
		doValidateOrderMessage(receptionParser);
		
		DvrDeliveryW dvrdeliveryW;
		DocumentW documentW;
		
		LocalDateTime now = LocalDateTime.now();
		
		// Estados de la Orden
		DvrOrderStateTypeW recOcSt		= dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
		
		// Estados de la entrega
		DvrDeliveryStateTypeW confDatSt	= dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_CONFIRMADA");
		DvrDeliveryStateTypeW onRecpSt 	= dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RECEPCION");
		DvrDeliveryStateTypeW recSt		= dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
		
		// Mapa de OC
		Map<Integer, DvrOrderW> dvrOrderMap = new HashMap<Integer, DvrOrderW>();
		// Mapa de Local
		Map<String, LocationW> locationMap = new HashMap<String, LocationW>();
		// Mapa de Item
		Map<String, ItemW> itemMap = new HashMap<String, ItemW>();
		
		// Map con Detalles de la entrega (para actualización de unidades recibidas)
		Map<DvrOrderDeliveryDetailId, Double> dvrorderdeliverydetailRecMap = new HashMap<DvrOrderDeliveryDetailId, Double>();
		
		// Set que almacena las distintas ordenes informadas en los detalles
		Set<DvrOrderW> dvrorderSet = new HashSet<DvrOrderW>();
		
		// Ids de las órdenes informadas
		List<Long> dvrOrderIds = new ArrayList<Long>();
		
		// Fecha de recepción informada en el mensaje
		LocalDateTime receptionDateTime = LocalDateTime.parse(receptionParser.getFechaRecepcion().trim(), datetimeFormatter);
		
		// Se obtiene el DvrDelivery
		// Obtiene documento a partir del asnnumber
		//El sistema valida que exista un documento con el número de asn informado.
		DocumentW[] documentArr = documentserver.getByPropertyAsArray("asnnumber", receptionParser.getNumAsn().trim());
		if(documentArr.length == 0) {
			throw new LoadDataException("El número de ASN " + receptionParser.getNumAsn().trim() + " no existe");
		}
		documentW = documentArr[0];
		
		// Valida que el documento no esté cerrado
		if (documentW.getClosed()) {
			throw new LoadDataException("El número de ASN " + receptionParser.getNumAsn().trim() + " ya ha sido recepcionado");
		}

		// Obtiene bulto asociado
		BulkW[] bulkArr = bulkserver.getByPropertyAsArray("document.id", documentW.getId());
		if(bulkArr.length == 0) {
			throw new LoadDataException("No existe bulto asociado a  ASN " + receptionParser.getNumAsn().trim());
		}
		BulkW bulkW = bulkArr[0];
		
		// Obtiene DvrDeliveryW asociado al bulto
		DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", bulkW.getDvrdeliveryid());
		if(dvrdeliveryArr.length == 0) {
			throw new LoadDataException("El número de cita para la recepción con asn número " + receptionParser.getNumAsn().trim() + " no existe");
		}
		dvrdeliveryW = dvrdeliveryArr[0];
				
		// 4.	El sistema valida que el despacho asociado se encuentre en estado ‘Cita confirmada’ o ‘En recepción’.
		if( ! dvrdeliveryW.getCurrentstatetypeid().equals(confDatSt.getId()) && 
			! dvrdeliveryW.getCurrentstatetypeid().equals(onRecpSt.getId())	) {
			throw new LoadDataException("La entrega " + dvrdeliveryW.getNumber() + " no se encuentra en un estado válido para la recepción");
		}		

		// Estado informado de la recepción sea recepción completa o parcial
		DvrOrderW dvrorderW;
		ItemW itemW;
		LocationW locationW;
		Double totalunits;
		if(receptionParser.getEstado() == RX_COMPLETA || receptionParser.getEstado() == RX_PARCIAL) {
			// Itera sobre los detalles
			for(Detalle detalle : receptionParser.getDetalles().getDetalle()) {
				// Obtiene orden				 
				if(! dvrOrderMap.containsKey(detalle.getNumOc())) {
					DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertyAsArray("number", Long.valueOf(detalle.getNumOc()));
					if(dvrorderArr.length == 0) {
						throw new LoadDataException("La orden número " + detalle.getNumOc() + " informada en los detalles no existe");
					}
					dvrorderW = dvrorderserver.getById(dvrorderArr[0].getId());
					dvrOrderMap.put(detalle.getNumOc(), dvrorderW);
				}
				else {
					dvrorderW = dvrOrderMap.get(detalle.getNumOc());
				}
				
				// Item
				if(! itemMap.containsKey(detalle.getCodProducto().trim())) {
					ItemW[] itemArr = itemserver.getByPropertyAsArray("sku", detalle.getCodProducto().trim());
					if(itemArr.length == 0) {
						throw new LoadDataException("El producto código " + detalle.getCodProducto().trim() + " informado en los detalles no existe");
					}
					itemW = itemserver.getById(itemArr[0].getId());
					itemMap.put(detalle.getCodProducto().trim(), itemArr[0]);
				}
				else {
					itemW = itemMap.get(detalle.getCodProducto().trim());
				}
				
				// Local
				if(! locationMap.containsKey(detalle.getCodLocalDestino().trim())) {
					LocationW[] locationArr = locationserver.getByPropertyAsArray("code", detalle.getCodLocalDestino().trim());
					if(locationArr.length == 0) {
						throw new LoadDataException("El local código " + detalle.getCodLocalDestino().trim() + " informado en los detalles no existe");
					}
					locationW = locationserver.getById(locationArr[0].getId());
					locationMap.put(detalle.getCodLocalDestino().trim(), locationW);
				}
				else {
					locationW = locationMap.get(detalle.getCodLocalDestino().trim());
				}
				
				// 7.	El sistema valida que la combinación de oc-sku-pos-local-entrega-documento (num_oc - cod_producto – posicion - cod_local_destino - num_asn) exista en el LPN asociado.
				PropertyInfoDTO[] props = new PropertyInfoDTO[6];
				props[0] = new PropertyInfoDTO("dvrorderdeliverydetail.dvrpredeliverydetail.dvrorderdetail.dvrorder.id", "dvrorderid", dvrOrderMap.get(detalle.getNumOc()).getId());
				props[1] = new PropertyInfoDTO("dvrorderdeliverydetail.dvrpredeliverydetail.dvrorderdetail.item.id", "itemid", itemMap.get(detalle.getCodProducto().trim()).getId());
				props[2] = new PropertyInfoDTO("dvrorderdeliverydetail.dvrpredeliverydetail.dvrorderdetail.id.position", "position", detalle.getPosicion());
				props[3] = new PropertyInfoDTO("dvrorderdeliverydetail.dvrpredeliverydetail.location.id", "locationid", locationMap.get(detalle.getCodLocalDestino().trim()).getId());
				props[4] = new PropertyInfoDTO("dvrorderdeliverydetail.dvrorderdelivery.dvrdelivery.id", "dvrdeliveryid", dvrdeliveryW.getId());
				props[5] = new PropertyInfoDTO("bulk.document.id", "documentid", documentW.getId());

				BulkDetailW[] bulkdetailArr = bulkdetailserver.getByPropertiesAsArray(props);
				if(bulkdetailArr.length == 0) {
					throw new LoadDataException("Para el número asn " + receptionParser.getNumAsn().trim() + " no existe la combinación de OC " + detalle.getNumOc() + //
												" – SKU " + detalle.getCodProducto().trim() + " – Posición " + detalle.getPosicion() + //
												" – Local " + detalle.getCodLocalDestino().trim());
				}

				// 9.	El sistema valida que las unidades totales de la combinación sean iguales o menores a lo comprometido en el despacho para ese ASN (bulto).
				totalunits = Arrays.stream(bulkdetailArr).mapToDouble(bd -> bd.getUnits()).sum();
				  
				if(detalle.getCantidadesUmc().doubleValue() > totalunits) {
					throw new LoadDataException("Para el número asn " + receptionParser.getNumAsn().trim() + " OC " + detalle.getNumOc() + //
												" – SKU " + detalle.getCodProducto() + " – Posición " + detalle.getPosicion() + //
												" – Local " + detalle.getCodLocalDestino() + " las unidades recepcionadas no pueden ser mayor a las comprometidas en el ASN");
				}
								
				// 10.	El sistema almacena las unidades recepcionadas (dvr_orderdeliverydetail), sumándolas a las unidades ya antes recepcionadas (de ser el caso).
				DvrOrderDeliveryDetailId oddpk = new DvrOrderDeliveryDetailId(dvrorderW.getId(), dvrdeliveryW.getId(), itemW.getId(), locationW.getId(), detalle.getPosicion()); 
				if(! dvrorderdeliverydetailRecMap.containsKey(oddpk)) {
					// Obtiene valor actual
					DvrOrderDeliveryDetailW oddW = dvrorderdeliverydetailserver.getById(oddpk);
					Double newRecValue = oddW.getReceivedunits() + detalle.getCantidadesUmc().doubleValue();
					dvrorderdeliverydetailRecMap.put(oddpk, newRecValue);
				}
				else {
					Double newRecValue = dvrorderdeliverydetailRecMap.get(oddpk) + detalle.getCantidadesUmc().doubleValue();
					dvrorderdeliverydetailRecMap.put(oddpk, newRecValue);
				}
				
				// Guarda orden informada
				dvrorderSet.add(dvrorderW);
				dvrOrderIds.add(dvrorderW.getId());
			}
		}
		
		// Validaciones OK, se actualizan datos
		// Actualiza unidades recibidas
		for (Map.Entry<DvrOrderDeliveryDetailId, Double> entry : dvrorderdeliverydetailRecMap.entrySet()) {
			DvrOrderDeliveryDetailW oddW = dvrorderdeliverydetailserver.getById(entry.getKey());
			oddW.setReceivedunits(entry.getValue());
			oddW = dvrorderdeliverydetailserver.updateDvrOrderDeliveryDetail(oddW);
		}

		// El sistema actualiza el campo ‘closed’ del documento a true.
		documentW.setClosed(true);
		
		// El sistema almacena la fecha y hora de recepción a nivel de documento (informada en el mensaje).		
		documentW.setReceptiondate(receptionDateTime);
		
		// Fecha actual
		documentW.setWhen(now);
		
		// Si se informa número de recepcion, lo almacena
		//Integer receptionNumber;
		boolean hasRecNbr = true;
		try {
			receptionParser.getNumRecepcion();
		} catch (Exception e) {
			hasRecNbr = false;
		}
		if(hasRecNbr) {
			documentW.setReceptionnumber(receptionParser.getNumRecepcion().longValue());
		}
		// Actualiza documento
		documentW = documentserver.updateDocument(documentW);
		
		// 15.	El sistema valida que todos los documentos asociados al order_delivery estén ‘closed’ = true.
		// Itera por cada orderdelivery informado
		for(DvrOrderW dvrOrderW : dvrorderSet) {
			// Obtiene DvrOrderDelivery actual
			DvrOrderDeliveryId odid = new DvrOrderDeliveryId(dvrOrderW.getId(), dvrdeliveryW.getId()); 
			DvrOrderDeliveryW dvrorderdeliveryW = dvrorderdeliveryserver.getById(odid);
			
			// 17.	El sistema almacena la fecha y hora de recepción, a nivel de order_delivery (fecha actual). Siempre se almacena la última fecha
			dvrorderdeliveryW.setReceptiondate(receptionDateTime);
			
			// Obtiene documentos asociados al dvrorderdelivery actual
			DocumentW[] documentrelatedArr = documentserver.getDocumentRelatedToOrderDelivery(dvrOrderW.getId(), dvrdeliveryW.getId());
			// dvrorderdelivery que están cerrados
			DocumentW[] documentClosedArr = Arrays.stream(documentrelatedArr).filter(doc->doc.getClosed().equals(true)).toArray(DocumentW[]::new);

			// Si todos los documentos realcionados a ese orderdelivery están en estado closed -> Actualiza estado closed de ese orderdelivery a 'true'			
			if(documentrelatedArr.length == documentClosedArr.length) {
				dvrorderdeliveryW.setClosed(true);
			}
			dvrorderdeliveryW = dvrorderdeliveryserver.updateDvrOrderDelivery(dvrorderdeliveryW);
		}
		
		// Obtiene todos los dvrorderdelivery asociados al despacho
		DvrOrderDeliveryW[] dvrorderdeliveryRelatedDeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
		// Del resultado anterios, obtiene aquellos que están cerrados
		DvrOrderDeliveryW[] dvrorderdeliveryClosedArr = Arrays.stream(dvrorderdeliveryRelatedDeliveryArr).filter(od->od.getClosed().equals(true)).toArray(DvrOrderDeliveryW[]::new);
		// 18.	El sistema valida que todos los order_delivery  asociados al despacho estén ‘closed’ = true.
		
		// - Todos los orderdelviery están cerrados
		if(dvrorderdeliveryRelatedDeliveryArr.length == dvrorderdeliveryClosedArr.length) {
			// El sistema actualiza el estado del despacho a ‘Cita recepcionada’.
			dvrdeliveryW.setCurrentstatetypeid(recSt.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			
			// Estado
			DvrDeliveryStateW  dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setComment("");
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(recSt.getId());
			dvrdeliverystateW.setUser("MQ");
			dvrdeliverystateW.setUsertype("MQ");
			dvrdeliverystateW.setWhen(now);
			dvrdeliverystateW = dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);			
		}		
		
		// - No todos los order-deliveries están cerrados.
		// El despacho se encuentra en estado 'Cita Confirmada' 
		else if(dvrdeliveryW.getCurrentstatetypeid().equals(confDatSt.getId())){			
			// El sistema actualiza el estado del despacho a 'En recepción'
			dvrdeliveryW.setCurrentstatetypeid(onRecpSt.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
					
			// Estado
			DvrDeliveryStateW  dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setComment("");
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(onRecpSt.getId());
			dvrdeliverystateW.setUser("MQ");
			dvrdeliverystateW.setUsertype("MQ");
			dvrdeliverystateW.setWhen(now);
			dvrdeliverystateW = dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);
		}
		
		// Cálculo de totales de todas las órdenes del despacho
		Long[] dvrDeliveryOrderIds = Arrays.stream(dvrorderdeliveryRelatedDeliveryArr).map(od -> od.getDvrorderid()).toArray(Long[]::new);
		buyorderreportmanager.doCalculateTotalOfDvrOrders(dvrDeliveryOrderIds);
		dvrorderserver.flush();

		// Valida si la cantidad de unidades recepcionadas para cada orden informada es igual a lo solicitado
		for (Long dvrOrderId : dvrOrderIds) {
			dvrorderW = dvrorderserver.getById(dvrOrderId);
			
			if (dvrorderW.getReceivedunits().equals(dvrorderW.getTotalunits())) {
				// Actualiza el estado de la orden a 'RECEPCIONADO'
				dvrorderW.setCurrentstatetypeid(recOcSt.getId());
				dvrorderW.setCurrentstatetypedate(now);
				dvrorderW = dvrorderserver.updateDvrOrder(dvrorderW);
				
				// Estado
				DvrOrderStateW state = new DvrOrderStateW();
				state.setDvrorderid(dvrorderW.getId());
				state.setDvrorderstatetypeid(recOcSt.getId());
				state.setWhen(now);
				state.setWho("MQ");
				state = dvrorderstateserver.addDvrOrderState(state);				
			}			
		}
		
		return receptionParser.getNumAsn().trim();			
	}	
}
