package bbr.b2b.logistic.msgregionalb2b;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.util.StringUtils;
import bbr.b2b.regional.logistic.buyorders.classes.AtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.ItemAtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.report.classes.ItemAtcDTO;
import bbr.b2b.regional.logistic.datings.classes.DatingRequestServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.deliveries.classes.BoxServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.PalletServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.PalletW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.classes.PropertyLocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.locations.data.wrappers.PropertyLocationW;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.xml.qplimport.BS;
import bbr.b2b.regional.logistic.xml.qplimport.BU;
import bbr.b2b.regional.logistic.xml.qplimport.PL;
import bbr.b2b.regional.logistic.xml.qplimport.PR;
import bbr.b2b.regional.logistic.xml.qplimport.PS;
import bbr.b2b.regional.logistic.xml.qplimport.QPLIMPORTADOREQ;
import bbr.b2b.regional.logistic.xml.qplimport.SC;

@Stateless(name = "handlers/XmlToPLImport")
public class XmlToPLImport implements XmlToPLImportLocal{
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	OrderDetailServerLocal orderdetailserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	PropertyLocationServerLocal propertylocationserver;
	
	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;
	
	@EJB
	DeliveryStateServerLocal deliverystateserver;
	
	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;
	
	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;
	
	@EJB
	DatingRequestServerLocal datingrequestserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	BoxServerLocal boxserver;
	
	@EJB
	PalletServerLocal palletserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	TaxDocumentServerLocal taxdocumentserver;
	
	@EJB
	AtcServerLocal atcserver;
	
	@EJB
	ItemAtcServerLocal itematcserver;
	
	@EJB
	BuyOrderReportManagerLocal ordermanager;
	
	@EJB
	DeliveryReportManagerLocal deliverymanager;
	
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();	
	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private List<String> doValidatePackingListMessage(QPLIMPORTADOREQ qplimportadoreq) throws LoadDataException {
		
		// SE: N°MERO DE SECUENCIA
		qutils.datoObligatorio(qplimportadoreq.getSE(), "No se especifica N°mero de secuencia");
		qutils.validaLargo(qplimportadoreq.getSE(), 12, "El N°mero de secuencia debe tener un largo de a los más 12 dígitos");		
		
		// IM: N°MERO GU�A IMPORTAción
		qutils.datoObligatorio(qplimportadoreq.getIM(), "No se especifica N°mero de gu�a de importación");
		qutils.validaLargo(qplimportadoreq.getIM(), 11, "El N°mero de gu�a de importación debe tener un largo de a los más 11 caracteres");
		
		// OC: N°MERO DE ORDEN
		qutils.datoObligatorio(qplimportadoreq.getOC(), "No se especifica N°mero de orden");
		qutils.validaLargo(qplimportadoreq.getOC(), 6, "El N°mero de orden debe tener un largo de a los más 6 dígitos");
		
		// GD: N°MERO DE GU�A
		qutils.datoObligatorio(qplimportadoreq.getGD(), "No se especifica N°mero de gu�a");
		qutils.validaLargo(qplimportadoreq.getGD(), 10, "El N°mero de gu�a debe tener un largo de a los más 10 dígitos");
		
		// NC: N°MERO DE CONTENEDOR
		qutils.datoObligatorio(qplimportadoreq.getNC(), "No se especifica N°mero de contenedor");
		qutils.validaLargo(qplimportadoreq.getNC(), 20, "El N°mero de contenedor debe tener un largo de a los más 20 caracteres");
		
/*		// AC: ACción
		qutils.datoObligatorio(qplimportadoreq.getAC(), "No se especifica acción");
		qutils.validaLargo(qplimportadoreq.getAC(), 1, "La acción debe tener un largo de a los más 1 caracter");
		
		String action = null;
		try {
			action = qplimportadoreq.getAC().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la acción");			
		}
		
		if (!action.equalsIgnoreCase("P") && !action.equalsIgnoreCase("E")){
			throw new LoadDataException("La acción solo puede tener valores 'P' o 'E'");
		}
*/			
		
		// SC: BLOQUE DE SOLICITUD DE CITA
		SC datingRequest = qplimportadoreq.getSC();	
		if (datingRequest == null) {
			throw new LoadDataException("No se especifica bloque de solicitud de cita");
		}
		
		// NO: NOMBRE
		qutils.datoObligatorio(datingRequest.getNO(), "No se especifica nombre en solicitud de cita");
		qutils.validaLargo(datingRequest.getNO(), 100, "El nombre en solicitud de cita debe tener un largo de a los más 100 caracteres");
		
		// EM: E-MAIL
		qutils.datoObligatorio(datingRequest.getEM(), "No se especifica e-mail en solicitud de cita");
		qutils.validaLargo(datingRequest.getEM(), 100, "El e-mail en solicitud de cita debe tener un largo de a los más 100 caracteres");

		String email = null;
		try {
			email = datingRequest.getEM().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el e-mail en solicitud de cita");			
		}
		
		if(!isEmail(email))
			throw new LoadDataException("El e-mail no es válido: " + email);

		// FE: FECHA
		qutils.datoObligatorio(datingRequest.getFE(), "No se especifica fecha en solicitud de cita");
		qutils.validaLargo(datingRequest.getFE(), 8, "La fecha en solicitud de cita debe tener un largo de a los más 8 caracteres");

		try {
			qutils.getDate(datingRequest.getFE(), "ddMMyyyy");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha en solicitud de cita debe tener formato 'DDMMYYYY'");
		}			
		
		// HH: HORA
		qutils.datoObligatorio(datingRequest.getHH(), "No se especifica hora en solicitud de cita");
		qutils.validaLargo(datingRequest.getHH(), 8, "La hora en solicitud de cita debe tener un largo de a los más 8 caracteres");

		try {
			qutils.getDate(datingRequest.getHH(), "hh:mm:ss");
		} catch (ParseException e) {
			throw new LoadDataException("La hora en solicitud de cita debe tener formato 'HH:MM:SS'");
		}			
		
/*		// CO: COMENTARIO
		qutils.datoObligatorio(datingRequest.getCO(), "No se especifica comentario en solicitud de cita");
		qutils.validaLargo(datingRequest.getCO(), 100, "El comentario en solicitud de cita debe tener un largo de a los más 100 caracteres");
*/		
		
		// UN: CANTIDAD DE BULTOS
		qutils.datoObligatorio(datingRequest.getUN().intValue(), "No se especifica cantidad de bultos en solicitud de cita");
		qutils.validaLargo(datingRequest.getUN(), 8, "La cantidad de bultos en solicitud de cita debe tener un largo de a los más 8 dígitos");
		
		// NP: CANTIDAD DE PALLETS
		qutils.datoObligatorio(datingRequest.getNP().intValue(), "No se especifica cantidad de pallets en solicitud de cita");
		qutils.validaLargo(datingRequest.getNP(), 8, "La cantidad de pallets en solicitud de cita debe tener un largo de a los más 8 dígitos");
		
		// CE: código LOCAL DE ENTREGA
		qutils.datoObligatorio(datingRequest.getCE(), "No se especifica código local de entrega en solicitud de cita");
		qutils.validaLargo(datingRequest.getCE(), 5, "El código local de entrega en solicitud de cita debe tener un largo de a los más 5 caracteres");
		
		// PL: BLOQUE DE PACKING LIST
		PL packingList = qplimportadoreq.getPL();
		
		if (packingList == null) {
			throw new LoadDataException("No se especifica packing list");		
		}
		
		// BS: BLOQUE DE BULTOS
		BS bulks = packingList.getBS();
		
		if (bulks == null || bulks.getBU() == null || bulks.getBU().size() <= 0){
			throw new LoadDataException("No se especifican bultos en packing list");		
		}
		
		// BU: LISTA DE BULTOS
		List<BU> bulkList = bulks.getBU();
		
		Long bulkId = null;
		String code = null;
		List<PR> itemList = null;
		List<String> codeList = new ArrayList<String>();
		for (int i = 0 ; i < bulkList.size(); i++) {
			// IB: ID BULTO			
			qutils.datoObligatorio(bulkList.get(i).getIB().longValue(), "No se especifica ID bulto en packing list");
			qutils.validaLargo(bulkList.get(i).getIB(), 8, "El ID bulto en packing list debe tener un largo de a los más 8 dígitos");
			bulkId = bulkList.get(i).getIB().longValue();
			
			// TB: TIPO BULTO
			qutils.datoObligatorio(bulkList.get(i).getTB(), "No se especifica tipo bulto en packing list, Bulto " + bulkId);
			qutils.validaLargo(bulkList.get(i).getTB(), 8, "El tipo bulto en packing list debe tener un largo de a los más 8 caracteres, Bulto " + bulkId);
			
			String bulkType = null;
			try {
				bulkType = bulkList.get(i).getTB().trim();	
			} catch (Exception e) {
				throw new LoadDataException("No se puede obtener el tipo bulto en packing list, Bulto " + bulkId);			
			}
			
			if (!bulkType.equalsIgnoreCase("CAJA") && !bulkType.equalsIgnoreCase("PALLET")) {
				throw new LoadDataException("El tipo bulto solo puede tener valores 'CAJA' o 'PALLET', Bulto " + bulkId);
			}
			
			// CD: código DESTINO
			qutils.datoObligatorio(bulkList.get(i).getCD(), "No se especifica código destino en packing list, Bulto " + bulkId);		
			qutils.validaLargo(bulkList.get(i).getCD(), 5, "El código destino en packing list debe tener un largo de a los más 5 caracteres, Bulto " + bulkId);
		
			// PS: BLOQUE DE PRODUCTOS
			PS items = bulkList.get(i).getPS();
			
			if (items == null || items.getPR() == null || items.getPR().size() <= 0) {
				throw new LoadDataException("No se especifican productos, Bulto " + bulkId);		
			}

			// PR: LISTA DE PRODUCTOS
			itemList = items.getPR();
			
			for (int j = 0 ; j < itemList.size(); j++) {
				// SK: código SKU			
				qutils.datoObligatorio(itemList.get(j).getSK(), "No se especifica código SKU en bulto de packing list, Bulto " + bulkId);
				qutils.validaLargo(itemList.get(j).getSK(), 22, "El código SKU en bulto de packing list debe tener un largo de a los más 22 caracteres, Bulto " + bulkId);
				code = itemList.get(j).getSK().trim();
				
				// CA: CANTIDAD
				qutils.datoObligatorio(itemList.get(j).getCA().intValue(), "No se especifica cantidad en bulto de packing list, Bulto " + bulkId + ", Producto " + code);		
				qutils.validaLargo(itemList.get(j).getCA(), 8, "La cantidad en bulto de packing list debe tener un largo de a los más 8 dígitos, Bulto " + bulkId + ", Producto " + code);
				if (itemList.get(j).getCA().intValue() <= 0) {
					throw new LoadDataException("La cantidad en bulto de packing list debe ser mayor que cero, Bulto " + bulkId + ", Producto " + code);
				}
				
				// JPE 20190409 LISTA DE TODOS LOS PRODUCTOS DEL PL
				if (!codeList.contains(code)) {
					codeList.add(code);
				}
			}		
		}
		
		return codeList;
	}
	
	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}
	
	public String processMessage(QPLIMPORTADOREQ qplimportadoreq) throws LoadDataException, ServiceException{
		
		//////////// Validación del mensaje //////////////
		
		List<String> codeList = doValidatePackingListMessage(qplimportadoreq);
		
		// IM: N°MERO GU�A IMPORTAción
		try {
			qplimportadoreq.getIM().trim();		
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la gu�a de importación");			
		}		
		
		//////////// Validación de la Orden /////////////
		
		// OC: N°MERO DE ORDEN
		Long orderNumber = null;
		try {
			orderNumber = qplimportadoreq.getOC();		
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N°mero de orden");			
		}
				
		// Obtener la Orden
		List<OrderW> orders =  orderserver.getByProperty("number", orderNumber);
		if (orders == null || orders.size() <= 0){
			throw new LoadDataException("La O/C con N°mero " + orderNumber + " no existe");		
		}	
		OrderW order = orders.get(0);
		
		// Obtener el proveedor de la Orden
		VendorW vendor = vendorserver.getById(order.getVendorid()); 
		
		// Validar que el proveedor es importado
		if (vendor.getDomestic()){
			throw new LoadDataException("El proveedor asociado a la orden " + orderNumber + " no es importado");	
		}
		
		// Obtener locales de la Orden
		LocationW[] locations = locationserver.getLocationsOfOrder(orderNumber);
		
		HashMap<String, LocationW> locationMap = new HashMap<String, LocationW>();
		for (LocationW location : locations){
			locationMap.put(new String(location.getCode()), location);
		}
		
		// Obtener productos de la orden
		ItemW[] items = itemserver.getItemsOfOrder(orderNumber);
		
		Map<String, ItemW> itemMap = new HashMap<String, ItemW>();
		for (ItemW item : items) {
			itemMap.put(item.getInternalcode(), item);
		}
		
		// Obtener los detalles de predistribución de la orden
		PreDeliveryDetailW[] predeliveryDetails = predeliverydetailserver.getPreDeliveryDetailsOfOrder(orderNumber);
		
		HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> predeliveryDetailMap = new HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
		PreDeliveryDetailPK pdDetailPK = null;
		for (PreDeliveryDetailW predeliveryDetail : predeliveryDetails){
			pdDetailPK = new PreDeliveryDetailPK();
			
			pdDetailPK.setItemid(predeliveryDetail.getItemid());
			pdDetailPK.setOrderid(predeliveryDetail.getOrderid());
			pdDetailPK.setLocationid(predeliveryDetail.getLocationid());
			
			predeliveryDetailMap.put(pdDetailPK, predeliveryDetail);
		}
		
		// GD: N°MERO DE GU�A
		Long guideNumber = null;
		try {
			guideNumber = qplimportadoreq.getGD();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N°mero de gu�a");			
		}
		
		// NC: N°MERO DE CONTENEDOR
		String containerNumber = null;
		try {
			containerNumber = qplimportadoreq.getNC().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el N°mero de contenedor");			
		}
		
		// JPE 20190606
		// Validar si el lugar de entrega de la OC est� configurado para trabajar con ATC
		PropertyInfoDTO prop1 = new PropertyInfoDTO("code", "code", "RECIBE_ATC");
		PropertyInfoDTO prop2 = new PropertyInfoDTO("location.id", "locationid", order.getDeliverylocationid());
		List<PropertyLocationW> propertylocations = propertylocationserver.getByProperties(prop1, prop2); 
		boolean atcLocation = propertylocations != null && propertylocations.size() > 0;
		
		// JPE 20190409
		// Se modifica el flujo creando despachos por el mismo N°mero de contenedor, tipo de flujo y si es con ATC o no
		HashMap<String, List<ItemAtcDTO>> iaMap = new HashMap<String, List<ItemAtcDTO>>();
		HashMap<String, Long> iaCurveMap = new HashMap<String, Long>();
		HashMap<String, String> itemATCCodeMap = new HashMap<String, String>();
		List<ItemAtcDTO> iaList = null;
		List<ItemAtcDTO> iaListTmp = null;
		List<String> itemCodes = null;
		List<String> atcCodes = null;
		if (codeList != null && !codeList.isEmpty()) {
			// Determinar cu�les productos vienen con código SKU o ATC
			itemCodes = itemserver.getBySKUs(codeList);
			atcCodes = atcserver.getByATCCodes(codeList);
			
			int sisize = itemCodes == null ? 0 : itemCodes.size();
			int atcsize = atcCodes == null ? 0 : atcCodes.size();;
			
			if (sisize + atcsize != codeList.size()) {
				throw new LoadDataException("El packing list tiene productos/ATCs que no existen en el sistema");
			}
	
			// JPE 20190606
			// Si el local est� habilitado para recibir ATC
			if (atcLocation) {
				// y el PL trae c�digos ATC
				if (atcsize > 0) {
					// Obtener el mapa de productos de los ATCs
					iaList = itematcserver.getItemDataByOrderAndATCCodes(order.getId(), atcCodes);
					for (ItemAtcDTO ia : iaList) {
						if (iaMap.containsKey(ia.getAtccode())) {
							iaListTmp = iaMap.get(ia.getAtccode());
						}
						else {
							iaListTmp = new ArrayList<ItemAtcDTO>();
						}
						iaListTmp.add(ia);
						iaMap.put(ia.getAtccode(), iaListTmp);
					}
					
					if (iaMap.size() != atcsize) {
						throw new LoadDataException("Se est�n informando ATCs que no est�n considerados en el detalle de la orden");
					}
				}
				
				// y el PL trae c�digos SKU
				if (sisize > 0) {
					// Obtener la información completa de los ATC integrados (obtenidos a partir de los SKUs)
					iaList = itematcserver.getItemDataByOrderAndSKUs(order.getId(), itemCodes);
					
					if (iaList != null && iaList.size() > 0) {
						for (ItemAtcDTO ia : iaList) {
							// Validar que se hayan indicado todos los productos que componen el ATC
							if (!itemCodes.contains(ia.getItemsku())) {
								throw new LoadDataException("No se informaron todos los productos del ATC " + ia.getAtccode() + " en la orden de compra");
							}
							
							// Agregar al mapa de productos de los ATCs
							if (iaMap.containsKey(ia.getAtccode())) {
								iaListTmp = iaMap.get(ia.getAtccode());
								
								// Validar que no se repita el ATC en el PL
								// Esto es para asegurar que no venga el mismo ATC con sus productos de manera independiente o como ATC completo
								if (iaListTmp.contains(ia)) {
									throw new LoadDataException("ATC " + ia.getAtccode() + " viene repetido en el packing list");
								}
							}
							else {
								iaListTmp = new ArrayList<ItemAtcDTO>();
							}
							iaListTmp.add(ia);
							iaMap.put(ia.getAtccode(), iaListTmp);
							
							// Agregar al mapa de curvas de productos asociadas a cada ATC
							iaCurveMap.put(ia.getAtccode() + "_" + ia.getItemid(), ia.getCurve());
							
							// Actualizar los listados de c�digos
							itemCodes.remove(ia.getItemsku()); // se elimina el producto del listado de productos individuales 
							itemATCCodeMap.put(ia.getItemsku(), ia.getAtccode()); // se agrega el producto que pertenece a un ATC
						}
					}		
				}
				
				// Se revisa el detalle de la orden, que es quien define si los productos llevan ATC o no
				if ((itemATCCodeMap.keySet() != null && itemATCCodeMap.keySet().size() > 0 && !orderdetailserver.doValidateOrderATCs(order.getId(), itemATCCodeMap.keySet())) ||
						(itemCodes != null && itemCodes.size() > 0 && !orderdetailserver.doValidateOrderSKUs(order.getId(), itemCodes))) {
					throw new LoadDataException("Inconsistencia entre los productos/ATCs y el detalle de la orden");
				}
			}
			// Rechazar si el PL trae c�digos ATC y el local no est� habilitado para recibirlos 
			else if (atcsize > 0) {
				throw new LoadDataException("El local de entrega de la orden de compra no est� habilitado para recibir ATCs");
			}
					
			// BULTOS
			List<BU> bulkList = qplimportadoreq.getPL().getBS().getBU();
			
			HashMap<String, List<ItemW>> bulkMap = new HashMap<String, List<ItemW>>();
			HashMap<String, HashMap<Long, Long>> bulkItemUnitMap = new HashMap<String, HashMap<Long, Long>>();
			HashMap<String, List<String>> flowTypeMap = new HashMap<String, List<String>>();
			HashMap<String, Integer> bulkUnitMap = new HashMap<String, Integer>();
			HashMap<String, Double> atcBulksMap = new HashMap<String, Double>();
			HashMap<Long, Long> unitMap = null;
			List<String> skuProcessed = new ArrayList<String>();
			List<String> atcProcessed = new ArrayList<String>();
			List<ItemW> itemList = null;
			List<String> ftCodes = null;
			boolean isItem = false;
			boolean isItemATC = false;
			Double multiplier = null;
			Long plbulkId = null;
			String code = null;
			Long units = null;
			String ftStr = "";
			String atcCode = "";
			for (BU bulk : bulkList) {
				// IB: ID BULTO
				try {
					plbulkId = bulk.getIB().longValue();
				} catch (Exception e) {
					throw new LoadDataException("No se puede obtener ID bulto en packing list");
				}
				
				List<PR> plitemList = bulk.getPS().getPR();
				for (PR plitem : plitemList) {
					// SK: SKU
					try {
						code = plitem.getSK().trim();
					} catch (Exception e) {
						throw new LoadDataException("No se puede obtener código SKU en bulto de packing list, Bulto " + plbulkId);			
					}
					
					isItem = itemCodes.contains(code);
					isItemATC = itemATCCodeMap.containsKey(code);
					
					// CA: CANTIDAD
					try {
						units = plitem.getCA().longValue();
					} catch (Exception e) {
						throw new LoadDataException("No se puede obtener cantidad en bulto de packing list, Bulto " + plbulkId + ", Producto " + code);			
					}
					
					// Producto
					if (isItem) {
						// Validar que no se repita el SKU en el PL
						if (skuProcessed.contains(code)) {
							throw new LoadDataException("SKU " + code + " viene repetido en el packing list");
						}
						else {
							skuProcessed.add(code);
						}
						
						// Validar que el producto pertenezca a la orden
						if (itemMap.containsKey(code)) {
							itemList = new ArrayList<ItemW>();
							itemList.add(itemMap.get(code));
							
							unitMap = new HashMap<Long, Long>();
							unitMap.put(itemMap.get(code).getId(), units);
							
							bulkMap.put(code, itemList);
							bulkItemUnitMap.put(code, unitMap);
							bulkUnitMap.put(code, 1);
							
							// Para los PL de 'Cencosud Importado' se debe agrupar por tipo de flujo-SKU
							ftStr = vendor.getRut().equalsIgnoreCase("IMP") ? itemMap.get(code).getFlowtypeid() + "_SKU" : "";
							if (flowTypeMap.containsKey(ftStr)) {
								ftCodes = flowTypeMap.get(ftStr);							
							}
							else {
								ftCodes = new ArrayList<String>();
							}
							
							ftCodes.add(code);
							flowTypeMap.put(ftStr, ftCodes);
						}
						else {
							throw new LoadDataException("No existe producto en la Orden con código SKU " + code + ", Bulto " + plbulkId);
						}
					}
					// ATC que aparece como producto independiente
					else if (isItemATC) {
						// Validar que no se repita el SKU en el PL
						if (skuProcessed.contains(code)) {
							throw new LoadDataException("SKU " + code + " viene repetido en el packing list");
						}
						else {
							skuProcessed.add(code);
						}
						
						// Validar que el producto pertenezca a la orden
						if (itemMap.containsKey(code)) {
							atcCode = itemATCCodeMap.get(code);
							if (atcBulksMap.containsKey(atcCode)) {
								multiplier = atcBulksMap.get(atcCode);
								
								// Validar que las unidades indicadas corresponden a curvas completas
								if ((double)units / iaCurveMap.get(atcCode + "_" + itemMap.get(code).getId()) != multiplier) {
									throw new LoadDataException("Las cantidades a entregar indicadas para todos los productos del ATC " + atcCode + " no conforman curvas completas");
								}
								
								unitMap = bulkItemUnitMap.get(atcCode);
							}
							else {
								// Validar que las unidades indicadas est�n en la proporción correcta de curvas
								if (units % iaCurveMap.get(atcCode + "_" + itemMap.get(code).getId()) != 0) {
									throw new LoadDataException("La cantidad a entregar del producto " + code + " no es proporcional a su valor de curva");
								}
								else {
									multiplier = (double)units / iaCurveMap.get(atcCode + "_" + itemMap.get(code).getId());
									atcBulksMap.put(atcCode, multiplier);
								}
								
								unitMap = new HashMap<Long, Long>();
							}
							
							// Obtener el listado de productos independientes agregados asociado al ATC
							if (bulkMap.containsKey(atcCode)) {
								itemList = bulkMap.get(atcCode);
							}
							else {
								itemList = new ArrayList<ItemW>();
							}
							itemList.add(itemMap.get(code));
							unitMap.put(itemMap.get(code).getId(), iaCurveMap.get(atcCode + "_" + itemMap.get(code).getId()));
							
							bulkMap.put(atcCode, itemList);				
							bulkItemUnitMap.put(atcCode, unitMap);
							
							if (!bulkUnitMap.containsKey(atcCode)) {
								bulkUnitMap.put(atcCode, multiplier.intValue());
							}
														
							// Para los PL de 'Cencosud Importado' se debe agrupar por tipo de flujo-ATC
							ftStr = vendor.getRut().equalsIgnoreCase("IMP") ? itemMap.get(code).getFlowtypeid() + "_ATC" : "";
							if (flowTypeMap.containsKey(ftStr)) {
								ftCodes = flowTypeMap.get(ftStr);							
							}
							else {
								ftCodes = new ArrayList<String>();
							}
							
							ftCodes.add(code);
							flowTypeMap.put(ftStr, ftCodes);
						}
						else {
							throw new LoadDataException("No existe producto en la orden de compra con código SKU " + code + ", Bulto " + plbulkId);
						}
					}
					// ATC
					else {
						// Validar que no se repita el ATC en el PL
						if (atcProcessed.contains(code)) {
							throw new LoadDataException("ATC " + code + " viene repetido en el packing list");
						}
						else {
							atcProcessed.add(code);
						}
						
						// Lista de productos del ATC
						iaList = iaMap.get(code);
						
						itemList = new ArrayList<ItemW>();
						for (ItemAtcDTO ia : iaList) {
							// Validar que el producto pertenezca a la orden
							if (itemMap.containsKey(ia.getItemsku())) {
								itemList.add(itemMap.get(ia.getItemsku()));																
								unitMap.put(itemMap.get(ia.getItemsku()).getId(), ia.getCurve());
							}
							else {
								throw new LoadDataException("El ATC " + code + " no fue informado para la orden de compra, Bulto " + plbulkId);
							}
						}
						bulkMap.put(code, itemList);
						bulkItemUnitMap.put(code, unitMap);
						
						// Para los PL de 'Cencosud Importado' se debe agrupar por tipo de flujo-ATC
						ftStr = vendor.getRut().equalsIgnoreCase("IMP") ? iaList.get(0).getFlowtypeid() + "_ATC" : "";
						if (flowTypeMap.containsKey(ftStr)) {
							ftCodes = flowTypeMap.get(ftStr);							
						}
						else {
							ftCodes = new ArrayList<String>();
						}
						
						ftCodes.add(code);
						flowTypeMap.put(ftStr, ftCodes);
						
						bulkUnitMap.put(code, units.intValue());
					}
				}
			}
			
			// Flujo GTM ('Cencosud Importado')
			if (vendor.getRut().equalsIgnoreCase("IMP")) {
				processGTM(qplimportadoreq, order, vendor, guideNumber, containerNumber, predeliveryDetailMap, bulkMap, bulkUnitMap,
							bulkItemUnitMap, itemATCCodeMap, flowTypeMap, locationMap);
			}
			// Flujo SAP COMEX
			else {
				processSAPCOMEX(qplimportadoreq, order, vendor, guideNumber, containerNumber, predeliveryDetailMap, bulkMap, bulkUnitMap,
									bulkItemUnitMap, itemATCCodeMap, flowTypeMap, locationMap);
			}
		}
		
		return containerNumber + "_" + orderNumber;		
	}
	
	private void processGTM(QPLIMPORTADOREQ qplimportadoreq, OrderW order, VendorW vendor, Long guideNumber, String containerNumber, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> predeliveryDetailMap, HashMap<String, List<ItemW>> bulkMap, HashMap<String, Integer> bulkUnitMap, HashMap<String, HashMap<Long, Long>> bulkItemUnitMap, HashMap<String, String> itemATCCodeMap, HashMap<String, List<String>> flowTypeMap, HashMap<String, LocationW> locationMap) throws LoadDataException, ServiceException {
		
		// Obtener tipo de estado de despacho 'Cita Solicitada'
		DeliveryStateTypeW dstRequested = deliverystatetypeserver.getByPropertyAsSingleResult("name", "Cita Solicitada");
		
		// Obtener la cantidad de detalles de predistribución de la orden
		int predeliveryDetailSequence = predeliveryDetailMap.size();
		
		List<String> validDeliveryCodes = null;
		DeliveryDTO[] deliveries = null;
		DeliveryW delivery = null;
		String[] str = null;
		for (Map.Entry<String, List<String>> ft : flowTypeMap.entrySet()) {
			delivery = null;
			
			// Validar si existe un despacho de 'Cencosud Importado' para el mismo N°mero de contenedor, local de despacho,
			// tipo de flujo y si es con ATC o no
			str = ft.getKey().split("_");
			deliveries = deliveryserver.getCencosudImportedDeliveriesByContainerDeliveryLocationFlowTypeAndCodeType(containerNumber, order.getDeliverylocationid(), Long.parseLong(str[0]), str[1]);
			
			validDeliveryCodes = ft.getValue();
			
			if (deliveries != null && deliveries.length > 0) {
				for (DeliveryDTO dl : deliveries) {
					// Validar si el despacho está en 'Cita Solicitada' (actualizar)
					if (dl.getCurrentstatetypeid().equals(dstRequested.getId())) {
						delivery = deliveryserver.getById(dl.getId());
						break;
					}
				}
			}
			// En las demás condiciones se crea un despacho nuevo
			
			// Crear/actualizar los despachos con los productos/atc correspondientes
			processPL(qplimportadoreq, order, delivery, vendor, guideNumber, predeliveryDetailMap, bulkMap, bulkUnitMap, bulkItemUnitMap,
						itemATCCodeMap, locationMap, validDeliveryCodes, predeliveryDetailSequence, str[1].equals("ATC"));
		}
	}
	
	private void processSAPCOMEX(QPLIMPORTADOREQ qplimportadoreq, OrderW order, VendorW vendor, Long guideNumber, String containerNumber, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> predeliveryDetailMap, HashMap<String, List<ItemW>> bulkMap, HashMap<String, Integer> bulkUnitMap, HashMap<String, HashMap<Long, Long>> bulkItemUnitMap, HashMap<String, String> itemATCCodeMap, HashMap<String, List<String>> flowTypeMap, HashMap<String, LocationW> locationMap) throws LoadDataException, ServiceException {
		
		// Obtener tipo de estado de despacho 'Cita Solicitada'
		DeliveryStateTypeW dstRequested = deliverystatetypeserver.getByPropertyAsSingleResult("name", "Cita Solicitada");
		
		// Obtener la cantidad de detalles de predistribución de la orden
		int predeliveryDetailSequence = predeliveryDetailMap.size();
		
		// Obtener los despachos asociados a la orden que cumplan con la combinación OC-GUIA-CONTENEDOR
		OrderDeliveryW[] orderDeliveries = orderdeliveryserver.getOrderDeliveriesByOrderRefDocumentAndContainer(order.getId(), guideNumber, containerNumber);
		
		Long deliveryId = null;
		if (orderDeliveries != null && orderDeliveries.length > 0) {
			for (OrderDeliveryW orderDelivery : orderDeliveries) {
				deliveryId = orderDelivery.getDeliveryid();
				
				// Obtener el despacho previo asociado y comprobar su estado.
				// Si su estado es 'Cita Solicitada' borrarlo, de lo contrario lanzar excepción
				DeliveryW previousDelivery = deliveryserver.getById(deliveryId, LockModeType.PESSIMISTIC_WRITE);
								
				if (previousDelivery.getCurrentstatetypeid().equals(dstRequested.getId())) {
					// JPE 20190904
					// Obtener las facturas asociadas a los detalles de bultos
					Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(deliveryId);

					// Borrar los detalles de bultos
					bulkdetailserver.deleteByProperty("id.deliveryid", deliveryId);	
					
					// JPE 20190904
					// Eliminar las facturas asociadas a los detalles de bultos
					if (taxdocumentids != null && taxdocumentids.length > 0) {
						taxdocumentserver.deleteByIds(taxdocumentids);
					}
					
					PropertyInfoDTO property1 = new PropertyInfoDTO("orderdelivery.delivery.id", "delivery", deliveryId);
					List<BulkW> bulkList = bulkserver.getByProperties(property1);
					for (int j = 0; j < bulkList.size(); j++) {
						long bulkId = bulkList.get(j).getId().longValue();
						
						// Borrar la caja o pallet
						PropertyInfoDTO property2 = new PropertyInfoDTO("id", "id", bulkId);
						if (boxserver.getByProperties(property2) != null && (boxserver.getByProperties(property2).size() > 0)) {
							boxserver.deleteElement(bulkId);
						}
						else if (palletserver.getByProperties(property2).get(0) != null && (palletserver.getByProperties(property2).size() > 0)) {
							palletserver.deleteElement(bulkId);
						}											
					}
					
					// Borrar los bultos
					bulkserver.deleteByProperties(property1);
					
					// Borrar los detalles del despacho previo de la Orden
					orderdeliverydetailserver.deleteByProperty("id.deliveryid", deliveryId);		
					
					// Borrar el despacho previo de la Orden
					orderdeliveryserver.deleteByProperty("id.deliveryid", deliveryId);		
					
					// Borrar el estado del despacho previo
					deliverystateserver.deleteByProperty("delivery.id", deliveryId);
					
					// Borrar la solicitud de cita del despacho previo
					datingrequestserver.deleteByProperty("delivery.id", deliveryId);			
					
					// Borrar el despacho previo
					deliveryserver.deleteDelivery(deliveryId);	
					
					// Recalcular los totales de la Orden
					ordermanager.doCalculateTotalOfOrder(order.getId());
				}
				else {
					throw new LoadDataException("Existe lote con el mismo OC-Guia-Contenedor no borrable, Lote No." + previousDelivery.getNumber());
				}
			}
		}
		
		List<String> validDeliveryCodes = flowTypeMap.get("");
		
		// Crear los despachos con los productos/atc correspondientes
		processPL(qplimportadoreq, order, null, vendor, guideNumber, predeliveryDetailMap, bulkMap, bulkUnitMap, bulkItemUnitMap, itemATCCodeMap,
					locationMap, validDeliveryCodes, predeliveryDetailSequence, false);		
	}
	
	private void processPL(QPLIMPORTADOREQ qplimportadoreq, OrderW order, DeliveryW delivery, VendorW vendor, Long guideNumber, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> predeliveryDetailMap, HashMap<String, List<ItemW>> bulkMap, HashMap<String, Integer> bulkUnitMap, HashMap<String, HashMap<Long, Long>> bulkItemUnitMap, HashMap<String, String> itemATCCodeMap, HashMap<String, LocationW> locationMap, List<String> validDeliveryCodes, int predeliveryDetailSequence, boolean hasatc) throws LoadDataException, ServiceException {
		
		//////////// Procesamiento del Packing List ////////////
						
		// Obtener el �ltimo N°mero de bulto del proveedor
		Long bulkNumber = new Long(vendor.getLastbulknumber() == null ? 0 : vendor.getLastbulknumber());
		bulkNumber++;
		
		// JPE 20190410
		// BULTOS
		List<BU> bulkList = qplimportadoreq.getPL().getBS().getBU();
		List<PR> plitemList = null;
		
		Long plBulkId = null;
		String plBulkDeliveryLocation = null;
		String plBulkType = null;

		HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> pddMap = new HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
		HashMap<Long, Long> bulkItemUnits = null;
		List<BoxW> boxList = new ArrayList<BoxW>();
		List<PalletW> palletList = new ArrayList<PalletW>();
		List<BulkDetailW> bulkDetailList = new ArrayList<BulkDetailW>();
		List<ItemW> itemList = null;
		LocationW location = null;
		BoxW box = null;
		PalletW pallet = null;
		PreDeliveryDetailPK predeliveryDetailPK = null;
		PreDeliveryDetailW predeliveryDetail = null;
		BulkDetailW bulkDetail = null;
		
		Long bulkId = -1L;
		String code = "";
		long itemUnits = 0;
		int bulkUnits = 0;
		int deliveryBulkUnits = 0;
		boolean added = false;
		for (BU plBulk : bulkList) {
			// IB: ID BULTO
			plBulkId = plBulk.getIB().longValue();
			
			// CD: LOCAL DE DESPACHO
			plBulkDeliveryLocation = plBulk.getCD().trim();
			
			// Validar si es un local de despacho válido de la orden
			if (locationMap.containsKey(plBulkDeliveryLocation)) {
				location = locationMap.get(plBulkDeliveryLocation);
			}
			else {
				throw new LoadDataException("No existe local de destino en la Orden con código " + plBulkDeliveryLocation + ", Bulto " + plBulkId);
			}
			
			// TB: TIPO BULTO
			plBulkType = plBulk.getTB().trim();
			
			// Validar si el tipo de bulto es CAJA o PALLET
			if (!plBulkType.equalsIgnoreCase("CAJA") && !plBulkType.equalsIgnoreCase("PALLET")) {
				throw new LoadDataException("Tipo de bulto no definido : " + plBulkType + ", Bulto " + plBulkId);
			}
			
			plitemList = plBulk.getPS().getPR();
			for (PR plitem : plitemList) {
				code = plitem.getSK().trim();
				
				// Validar si el item est� incluido en este despacho
				if (!validDeliveryCodes.contains(code)) {
					continue;
				}
				
				// Si es un ATC relacionado como SKU independiente ...
				if (itemATCCodeMap.containsKey(code)) {
					code = itemATCCodeMap.get(code);
				}
				
				itemList = bulkMap.get(code);
				bulkItemUnits = bulkItemUnitMap.get(code);
				bulkUnits = bulkUnitMap.get(code);
				
				// JPE 20190627
				// Validar que no se haya agregado el item (caso ATC relacionado como SKU independiente)
				predeliveryDetailPK = new PreDeliveryDetailPK();
				predeliveryDetailPK.setItemid(itemList.get(0).getId());
				predeliveryDetailPK.setLocationid(location.getId());
				predeliveryDetailPK.setOrderid(order.getId());
				
				if (!pddMap.containsKey(predeliveryDetailPK)) {
					// Se itera por la cantidad de bultos de este código (puede ser diferente de 1 para ATC o ATC relacionado como SKU independiente)
					for (int i = 0; i < bulkUnits; i++) {
						// Iterar por los productos del bulto
						added = false;
						for (ItemW item : itemList) {
							// Validar la información de las unidades del producto
							if (!bulkItemUnits.containsKey(item.getId())) {
								continue;
							}
							itemUnits = bulkItemUnits.get(item.getId());
							
							// Validar si existe en el sistema la combinación OC-PRODUCTO-LOCAL
							predeliveryDetailPK = new PreDeliveryDetailPK();
							predeliveryDetailPK.setItemid(item.getId());
							predeliveryDetailPK.setLocationid(location.getId());
							predeliveryDetailPK.setOrderid(order.getId());
							
							if (predeliveryDetailMap.containsKey(predeliveryDetailPK)) {
								predeliveryDetail = predeliveryDetailMap.get(predeliveryDetailPK);
							}
							else {
								// Si no existe, crear el nuevo detalle de predistribución de la orden con cantidad cero
								predeliveryDetailSequence++;
								
								predeliveryDetail = new PreDeliveryDetailW();						
								predeliveryDetail.setSequence(predeliveryDetailSequence);
								predeliveryDetail.setUnits(0.0);
								predeliveryDetail.setOutreceivedunits(0.0);
								predeliveryDetail.setLocationid(predeliveryDetailPK.getLocationid());
								predeliveryDetail.setOrderid(predeliveryDetailPK.getOrderid());
								predeliveryDetail.setItemid(predeliveryDetailPK.getItemid());
								// no se inicializan a�n el resto de las unidades y montos
								
								predeliveryDetail = predeliverydetailserver.addPreDeliveryDetail(predeliveryDetail);
							}
							
							// Agregar los detalles al mapa espec�fico de este despacho
							pddMap.put(predeliveryDetailPK, predeliveryDetail);
							
							// Crear el detalle del bulto
							bulkDetail = new BulkDetailW();					
							bulkDetail.setUnits((double)itemUnits);
							bulkDetail.setRefdocument(guideNumber.toString());
							bulkDetail.setOrderid(order.getId());
							bulkDetail.setDeliveryid(0L);
							bulkDetail.setItemid(item.getId());
							bulkDetail.setLocationid(location.getId());
							bulkDetail.setBulkid(bulkId);
							
							// Se agrega el nuevo detalle al arreglo del packing list
							bulkDetailList.add(bulkDetail);
							
							added = true;
						}
						
						if (added) {
							// Instanciar caja o pallet seg�n corresponda
							if (plBulkType.equalsIgnoreCase("CAJA")){
								box = new BoxW();				
								box.setId(bulkId);
								box.setCode(hasatc ? "ATC" + StringUtils.getInstance().addLeadingZeros(bulkNumber.toString(), 8) : bulkNumber.toString());
								box.setNumber(bulkNumber++);
								box.setOrderid(order.getId());
								box.setDeliveryid(0L); // Se asume en este momento que deliveryid = 0
								box.setDepartmentid(order.getDepartmentid());
								box.setLocationid(location.getId());
								// no se inicializa a�n flowtypeid
								
								// Se agrega la caja al arreglo del packing list
								boxList.add(box);				
							}
							else {
								pallet = new PalletW();				
								pallet.setId(bulkId);
								pallet.setCode(hasatc ? "ATC" + StringUtils.getInstance().addLeadingZeros(bulkNumber.toString(), 8) : bulkNumber.toString());
								pallet.setNumber(bulkNumber++);
								pallet.setOrderid(order.getId());
								pallet.setDeliveryid(0L); // Se asume en este momento que deliveryid = 0
								pallet.setDepartmentid(order.getDepartmentid());
								pallet.setLocationid(location.getId());
								// no se inicializan a�n boxcount ni flowtypeid
								
								// Se agrega el pallet al arreglo del packing list
								palletList.add(pallet);
							}
													
							// Acumula la cantidad de bultos del despacho
							deliveryBulkUnits++;
							
							bulkId--;
						}
					}
				}
			}
		}
				
		List<PreDeliveryDetailW> predeliveryList = new ArrayList<PreDeliveryDetailW>(pddMap.values());
		PreDeliveryDetailW[] preDeliveryDetailArray = (PreDeliveryDetailW[]) predeliveryList.toArray(new PreDeliveryDetailW[predeliveryList.size()]);
		
		// Crear/actualizar el despacho asociado
		try {
			if (delivery == null) {
				doAddDeliveries(qplimportadoreq, order, boxList, palletList, bulkDetailList, hasatc);
			}
			else {
				doUpdateDeliveries(qplimportadoreq, order, delivery, boxList, palletList, bulkDetailList, preDeliveryDetailArray);
			}
		} catch (ServiceException e) {
			throw new LoadDataException(e.getMessage());	
		}
		
		// Recalcular los totales de la Orden
		ordermanager.doCalculateTotalOfOrder(order.getId());
		
		logger.info("Recalculadas las unidades de la Orden No. " + order.getNumber());
	}
	
	private void doAddDeliveries(QPLIMPORTADOREQ qplimportadoreq, OrderW order, List<BoxW> boxList, List<PalletW> palletList, List<BulkDetailW> bulkDetailList, boolean hasatc) throws LoadDataException, ServiceException{
		
		// IM: N°MERO GU�A IMPORTAción
		String importationGuide = qplimportadoreq.getIM().trim();
				
		// GD: N°MERO DE GU�A
		Long guideNumber = qplimportadoreq.getGD();

		// NC: N°MERO DE CONTENEDOR
		String containerNumber = qplimportadoreq.getNC().trim();	
	
		// SC: BLOQUE DE SOLICITUD DE CITA
		SC pldatingRequest = qplimportadoreq.getSC();	
		
		// NO: NOMBRE
		String name = null;
		try {
			name = pldatingRequest.getNO().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el nombre en solicitud de cita");			
		}
		
		// EM: E-MAIL
		String email = pldatingRequest.getEM().trim();	
		
		// FE: FECHA
		Date date = null;
		try{
			date = qutils.getDate(pldatingRequest.getFE(), "ddMMyyyy");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha en solicitud de cita debe tener formato 'DDMMYYYY'");
		}			
		
		Calendar rdDate = Calendar.getInstance();
		rdDate.setTime(date);
		
		// HH: HORA
		Date time = null;
		try{
			time = qutils.getDate(pldatingRequest.getHH(), "hh:mm:ss");
		} catch (ParseException e) {
			throw new LoadDataException("La hora en solicitud de cita debe tener formato 'DDMMYYYY'");
		}
		
		Calendar rdTime = Calendar.getInstance();
		rdTime.setTime(time);
		
		// Agrupar la información de fecha y hora en una sola variable Date 
		rdDate.set(Calendar.HOUR_OF_DAY, rdTime.get(Calendar.HOUR_OF_DAY));
		rdDate.set(Calendar.MINUTE, rdTime.get(Calendar.MINUTE));
		rdDate.set(Calendar.SECOND, rdTime.get(Calendar.SECOND));
		Date requestedDatingDate = rdDate.getTime();				
		
		// CE: código LOCAL DE ENTREGA
		String deliveryLocationCode = null;
		try {
			deliveryLocationCode = pldatingRequest.getCE().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código local de entrega en solicitud de cita");			
		}
		
		List<LocationW> locations =  locationserver.getByProperty("code", deliveryLocationCode);		
		if (locations == null || locations.size() <= 0) {
			throw new LoadDataException("El local de entrega en solicitud de cita con código " + deliveryLocationCode + " no existe");		
		}	
		
		// Arreglos asociados a los bultos del packing list
		BulkDetailW[] bulkDetails = (BulkDetailW[]) bulkDetailList.toArray(new BulkDetailW[bulkDetailList.size()]);
		PalletW[] pallets = (PalletW[]) palletList.toArray(new PalletW[palletList.size()]);
		BoxW[] boxs = (BoxW[]) boxList.toArray(new BoxW[boxList.size()]);
		
		// Crear despacho base con par�metros comunes procedentes del packing list
		DeliveryW delivery = new DeliveryW();		
		delivery.setRefdocument(guideNumber);
		delivery.setContainer(containerNumber);
		delivery.setImp(importationGuide);
		
		// Crear solicitud de cita con par�metros procedentes del packing list	
		DatingRequestW datingRequest = new DatingRequestW();
		
		datingRequest.setRequesteddate(requestedDatingDate);
		datingRequest.setRequester(name);
		datingRequest.setEmail(email);
		// Se calculan despu�s
		datingRequest.setEstimatedboxes(0);
		datingRequest.setEstimatedpallets(0);
		
		// Crear nuevo despacho asociado a los productos del packing list
		OrderW[] orders = new OrderW[1];
		orders[0] = order;
		deliverymanager.doAddDeliveriesByPackingList(orders, delivery, datingRequest, pallets, boxs, bulkDetails, hasatc, guideNumber, importationGuide);	
		
		logger.info("Creados los nuevos despachos y solicitudes de cita tras la carga del Packing List Internacional (Contenedor " + containerNumber + " - OC " + order.getNumber() + ")");		
	}
	
	private void doUpdateDeliveries(QPLIMPORTADOREQ qplimportadoreq, OrderW order, DeliveryW delivery, List<BoxW> boxList, List<PalletW> palletList, List<BulkDetailW> bulkDetailList, PreDeliveryDetailW[] preDeliveryDetailArray) throws LoadDataException, ServiceException{
		
		// IM: N°MERO GU�A IMPORTAción
		String importationGuide = qplimportadoreq.getIM().trim();
				
		// GD: N°MERO DE GU�A
		Long guideNumber = qplimportadoreq.getGD();
		
		// SC: BLOQUE DE SOLICITUD DE CITA
		SC pldatingRequest = qplimportadoreq.getSC();	
		
		// NC: N°MERO DE CONTENEDOR
		String containerNumber = qplimportadoreq.getNC().trim();
		
		// NO: NOMBRE
		String name = null;
		try {
			name = pldatingRequest.getNO().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el nombre en solicitud de cita");			
		}
		
		// EM: E-MAIL
		String email = pldatingRequest.getEM().trim();	
		
		// FE: FECHA
		Date date = null;
		try {
			date = qutils.getDate(pldatingRequest.getFE(), "ddMMyyyy");	
		} catch (ParseException e) {
			throw new LoadDataException("La fecha en solicitud de cita debe tener formato 'DDMMYYYY'");
		}			
		
		Calendar rdDate = Calendar.getInstance();
		rdDate.setTime(date);
		
		// HH: HORA
		Date time = null;
		try{
			time = qutils.getDate(pldatingRequest.getHH(), "hh:mm:ss");	// Ya fue validado	
		} catch (ParseException e) {
			throw new LoadDataException("La hora en solicitud de cita debe tener formato 'DDMMYYYY'");
		}	
		Calendar rdTime = Calendar.getInstance();
		rdTime.setTime(time);
		
		// Agrupar la información de fecha y hora en una sola variable Date 
		rdDate.set(Calendar.HOUR_OF_DAY, rdTime.get(Calendar.HOUR_OF_DAY));
		rdDate.set(Calendar.MINUTE, rdTime.get(Calendar.MINUTE));
		rdDate.set(Calendar.SECOND, rdTime.get(Calendar.SECOND));
		
		// CE: código LOCAL DE ENTREGA
		String deliveryLocationCode = null;
		try {
			deliveryLocationCode = pldatingRequest.getCE().trim();	
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código local de entrega en solicitud de cita");			
		}
		
		List<LocationW> locations =  locationserver.getByProperty("code", deliveryLocationCode);		
		if (locations == null || locations.size() <= 0) {
			throw new LoadDataException("El local de entrega en solicitud de cita con código " + deliveryLocationCode + " no existe");		
		}	
		
		// Arreglos asociados a los bultos del packing list
		BulkDetailW[] bulkDetails = (BulkDetailW[]) bulkDetailList.toArray(new BulkDetailW[bulkDetailList.size()]);
		PalletW[] pallets = (PalletW[]) palletList.toArray(new PalletW[palletList.size()]);
		BoxW[] boxs = (BoxW[]) boxList.toArray(new BoxW[boxList.size()]);
		
		// Actualizar solicitud de cita con par�metros procedentes del packing list
		DatingRequestW datingRequest = datingrequestserver.getByPropertyAsSingleResult("delivery.id", delivery.getId());
		
		datingRequest.setRequester(name);
		datingRequest.setEmail(email);
		
		HashMap<Long, OrderW> orderMap = new HashMap<Long, OrderW>();
		orderMap.put(order.getId(), order);
		
		deliverymanager.doUpdateDeliveriesByPackingList(orderMap, delivery, datingRequest, pallets, boxs, bulkDetails, preDeliveryDetailArray, guideNumber, importationGuide);	
		
		logger.info("Actualizados los despachos y solicitudes de cita tras la carga del Packing List Internacional (Contenedor " + containerNumber + " - OC " + order.getNumber() + ")");
	}

	private boolean isEmail(String correo){
		
		Pattern pat = null;
		Matcher mat = null;
		pat = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
		mat = pat.matcher(correo);
		
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
}
