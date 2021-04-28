package bbr.b2b.regional.logistic.deliveries.managers.classes;

import java.io.File;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;
import org.jboss.annotation.IgnoreDependency;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.common.util.StringUtils;
import bbr.b2b.logistic.msgregionalb2b.AsnToXmlLocal;
import bbr.b2b.regional.logistic.buyorders.classes.AtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.BlockedOrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.ItemAtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.AtcW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.BlockedOrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ItemAtcW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.report.classes.ItemAtcDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadPreDeliveryDetailDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadPreDeliveryDetailDataResultDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.datings.classes.DatingRequestServerLocal;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ReserveDetailServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.datings.report.classes.OrderUnitsDTO;
import bbr.b2b.regional.logistic.deliveries.classes.BoxServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.PalletServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailPK;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryPK;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.PalletW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerRemote;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddImportedDeliveryOfOrdersInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailSummaryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryAvailabilityDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryAvailabilityInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryItemsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryStateTypeDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DoDeleteDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderDeliveryDetailDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderDeliveryDetailResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderUnitsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PendingItemDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListTotalDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateAllRefDocumentsOfDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadDeliveryAvailabilityInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPredistributedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPredistributedPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPredistributedPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ValidateDeliveryItemsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ValidateDeliveryItemsInitParamDTO;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.report.classes.FlowTypeDTO;
import bbr.b2b.regional.logistic.items.report.classes.FlowTypesInitParamDTO;
import bbr.b2b.regional.logistic.items.report.classes.FlowTypesResultDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.classes.PropertyLocationServerLocal;
import bbr.b2b.regional.logistic.locations.classes.VendorLocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.locations.data.wrappers.PropertyLocationW;
import bbr.b2b.regional.logistic.locations.data.wrappers.VendorLocationW;
import bbr.b2b.regional.logistic.locations.entities.VendorLocationId;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.report.classes.BaseResultsDTO;
import bbr.b2b.regional.logistic.report.classes.UploadErrorDTO;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.TaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.TaxDocumentReportManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentAddResultDTO;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

@Stateless(name = "managers/DeliveryReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryReportManager implements DeliveryReportManagerLocal, DeliveryReportManagerRemote {

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	private static Logger logger = Logger.getLogger(DeliveryReportManager.class);

	@EJB
	private VendorServerLocal vendorserverlocal;
	
	@EJB
	AsnToXmlLocal asntoxml;

	@EJB
	BoxServerLocal boxserver;

	@EJB
	BulkDetailServerLocal bulkdetailserver;

	@EJB
	BulkServerLocal bulkserver;

	@EJB
	DatingRequestServerLocal datingrequestserver;

	@EJB
	DatingServerLocal datingserver;

	@EJB
	DeliveryServerLocal deliveryserver;

	@EJB
	DeliveryStateServerLocal deliverystateserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

	@EJB
	DeliveryTypeServerLocal deliverytypeserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	AtcServerLocal atcserver;
	
	@EJB
	ItemAtcServerLocal itematcserver;

	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	PropertyLocationServerLocal propertylocationserver;

	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	BlockedOrderServerLocal blockedorderserver;

	@EJB
	PalletServerLocal palletserver;

	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	@EJB
	ReserveDetailServerLocal reservedetailserver;

	@EJB
	VendorLocationServerLocal vendorlocationserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	TaxDocumentServerLocal taxdocumentserver;
		
	@EJB
	@IgnoreDependency
	BuyOrderReportManagerLocal buyorderreportmanager;

	@EJB
	TaxDocumentReportManagerLocal taxdocumentreportmanager;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public DeliveryItemsArrayResultDTO getDeliveryItemsByOrders(ValidateDeliveryItemsInitParamDTO initParams) {

		DeliveryItemsArrayResultDTO resultDTO = new DeliveryItemsArrayResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Obtener el código del proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserver.getById(vendor.getId());
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}
			
			// Obtener las órdenes
			OrderW[] orders = orderserver.getOrdersByIdsAndVendor(initParams.getOrderids(), vendor.getId());
			if (orders == null || orders.length != initParams.getOrderids().length) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2308");
			}
			
			// JPE 20190625
			// Validar que todas las órdenes sean del mismo tipo y tengan el mismo local de entrega
			Long ordertypeid = orders[0].getOrdertypeid();
			Long deliverylocationid = orders[0].getDeliverylocationid();
			for (OrderW order : orders) {
				if (!order.getOrdertypeid().equals(ordertypeid)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2312");
				}
				
				if (!order.getDeliverylocationid().equals(deliverylocationid)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2313");
				}
			}
			
			// Validar que las órdenes sean de tipo Stock ('S') o Pre-distribuida (XDB) ('C') y
			// que su local de entrega corresponda al código '200' o '12'
			OrderTypeW ordertype = ordertypeserver.getById(ordertypeid);
			LocationW location = locationserver.getById(deliverylocationid);
			
			if ((ordertype.getCode().equals("S") || ordertype.getCode().equals("C")) &&
					(location.getCode().equals("200") || location.getCode().equals("12"))) {
				// Obtener los productos de las órdenes seleccionadas considerando:
				// - todos los correspondientes al local de entrega con código '200'
				// - los productos correspondientes al local de entrega con código '12' pero solo con destino de predistribución '200' o '12'
				List<String> iteminternalnames = itemserver.getDeliveryItemsByOrders(initParams.getOrderids());
				if (iteminternalnames != null && iteminternalnames.size() > 0) {
					resultDTO.setVendorcode(vendor.getCode());
					resultDTO.setIteminternalnames(iteminternalnames);
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public ValidateDeliveryItemsArrayResultDTO getPendingItemsData(Long[] orderids, List<String> pSKUList, boolean sendmailnotification, String username, String useremail, String usertype, String vendorcode) {

		ValidateDeliveryItemsArrayResultDTO resultDTO = new ValidateDeliveryItemsArrayResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Obtener el código del proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserver.getById(vendorid);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}
			
			// Obtener los datos de productos pendientes
			PendingItemDTO[] pendingitems = itemserver.getPendingItemsData(orderids, pSKUList);
			if (pendingitems == null || pendingitems.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L445");
			}
			
			// JPE 20190625
			// Registrar la información de las órdenes, productos y predistribuciones en la tabla de historial de agendamientos bloqueados
			Date now = new Date();
			
			BlockedOrderW blockedorder = null;
			for (PendingItemDTO pendingitem : pendingitems) {
				blockedorder = new BlockedOrderW();
				blockedorder.setWhen(now);
				blockedorder.setUsername(username);
				blockedorder.setUseremail(useremail);
				blockedorder.setUsertype(usertype);
				blockedorder.setOrdernumber(pendingitem.getOrdernumber());
				blockedorder.setVendorcode(vendor.getCode());
				blockedorder.setVendorname(vendor.getName());
				blockedorder.setDeliverylocationcode(pendingitem.getDeliverylocationcode());
				blockedorder.setDeliverylocationname(pendingitem.getDeliverylocationname());
				blockedorder.setDestinationlocationcode(pendingitem.getDestinationlocationcode());
				blockedorder.setDestinationlocationname(pendingitem.getDestinationlocationname());
				blockedorder.setIteminternalcode(pendingitem.getIteminternalcode());
				blockedorder.setItemname(pendingitem.getItemname());
				
				blockedorderserver.addBlockedOrder(blockedorder);
			}
			
			resultDTO.setPendingitems(pendingitems);
			
			if (sendmailnotification) {
				try {
					// Enviar correo de alerta MDM al usuario
					String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
					String from = RegionalLogisticConstants.getInstance().getMailSender();
					String[] to = {useremail};
					String[] cc = RegionalLogisticConstants.getInstance().getMDMMailCC();
					String bcc[] = null;
					
					String subject = "B2B Paris: SKUs con falta de Enriquecimiento (" + vendor.getTradename() + ")";
														
					String message =
						"<p>Estimado Proveedor,</p>" + //
						"<p>Le informamos que no se ha podido generar la solicitud de agendamiento de órdenes de Compra seleccionadas debido " + //
						   "a que se encuentra pendiente la carga de fichas y fotos de los SKUs correspondientes en la sección de Maestros " + //
						   "Información de productos.</p>" + //
						"<p>Para incorporar información faltante debe ingresar a m�dulo <b>Información de Productos</b>, opción <b>Maestros</b>.</p>" + //
						"<p>Si requiere asistencia contacte area <b>C�talogo & Publicación</b> al mail: catalogo.proveedores@cencosud.cl<br>" + //
						   "A continuación detalle de SKUs con falta de Enriquecimiento:</p>"; //
						
						for (PendingItemDTO pendingitem : pendingitems) {
							message += "&nbsp;&nbsp;&nbsp;&nbsp;* " + pendingitem.getIteminternalcode() + " - " + pendingitem.getItemname() + "<br>";
						}
					message +=
						"<br><br>" + //
						"<p><b><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></b></p>" + //
						"<p>Atte.<br>" + //
						"Equipo Cat�logo y Logística Proveedores<br>" + //
						"B2B Paris</p>"; //

					schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, "MDM_NOTIFICATION", vendor.getRut());
					
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("No fue posible enviar email para notificar a MDM los SKUs con falta de Enriquecimiento");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public void doUpdateDeliveriesByPackingList(HashMap<Long, OrderW> orderMap, DeliveryW basedelivery, DatingRequestW basedatingrequest, PalletW[] pallets, BoxW[] boxes, BulkDetailW[] bulkdetails, PreDeliveryDetailW[] predeliveryDetails, Long guideNumber, String importationGuide) throws AccessDeniedException, NotFoundException, OperationFailedException {

		// Obtener el proveedor de la Orden
		OrderW[] orders = (OrderW [])orderMap.values().toArray(new OrderW[orderMap.values().size()]);
		VendorW vendor = vendorserver.getById(orders[0].getVendorid());

		// //////// 3 - Crear los detalles y los despachos de la Orden asociados a cada producto y tipo de flujo
		// respectivamente //////////

		Map<PreDeliveryDetailPK, OrderDeliveryDetailW> orderDetailMap = new HashMap<PreDeliveryDetailPK, OrderDeliveryDetailW>();
		Map<PreDeliveryDetailPK, OrderDeliveryDetailW> orderDeliveryDetailMap = new HashMap<PreDeliveryDetailPK, OrderDeliveryDetailW>();
		Map<OrderDeliveryPK, OrderDeliveryW> orderDeliveryMap = new HashMap<OrderDeliveryPK, OrderDeliveryW>();
		OrderDeliveryW[] ods = null;
		OrderDeliveryPK orderDeliveryPK = null;
			
		// Explorar los detalles de predistribución de la Orden
		for (PreDeliveryDetailW predeliveryDetail : predeliveryDetails) {
			// Crear un detalle de despacho de la Orden por cada detalle de predistribución
			OrderDeliveryDetailW orderDeliveryDetail = new OrderDeliveryDetailW();

			orderDeliveryDetail.setPendingunits(predeliveryDetail.getPendingunits());
			orderDeliveryDetail.setAvailableunits(0.0); // Inicializar las unidades disponibles a cero
			orderDeliveryDetail.setOrderid(predeliveryDetail.getOrderid());
			orderDeliveryDetail.setDeliveryid(basedelivery.getId());
			orderDeliveryDetail.setItemid(predeliveryDetail.getItemid());
			orderDeliveryDetail.setLocationid(predeliveryDetail.getLocationid());

			// Agregar al mapa de detalles de la Orden utilizando �ndice orden-item-local
			PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

			predeliveryDetailPK.setOrderid(predeliveryDetail.getOrderid());
			predeliveryDetailPK.setItemid(predeliveryDetail.getItemid());
			predeliveryDetailPK.setLocationid(predeliveryDetail.getLocationid());

			orderDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);

			// Agregar al mapa de detalles de despachos de la Orden utilizando �ndice orden-item-local si el detalle
			// de predistribución tiene unidades pendientes
			if (predeliveryDetail.getPendingunits() > 0) {
				orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
			}
			
			orderDeliveryPK = new OrderDeliveryPK(predeliveryDetail.getOrderid(), basedelivery.getId());
			if (!orderDeliveryMap.containsKey(orderDeliveryPK)) {
				//Se agrega al mapa el orderdelivery en caso de existir para ser actualizado
				ods = orderdeliveryserver.getOrderDeliveriesByOrderAndDelivery(predeliveryDetail.getOrderid(), basedelivery.getId());
				if (ods != null && ods.length > 0) {
					orderDeliveryMap.put(orderDeliveryPK, ods[0]);
				}
			}
		}

		// //////// 4 - Actualizar las unidades disponibles por detalles de despachos de la Orden //////////

		// Explorar nuevamente los detalles de bultos
		for (BulkDetailW bulkDetail : bulkdetails) {
			// Obtener el detalle de despacho de la Orden asociado
			PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

			predeliveryDetailPK.setOrderid(bulkDetail.getOrderid());
			predeliveryDetailPK.setItemid(bulkDetail.getItemid());
			predeliveryDetailPK.setLocationid(bulkDetail.getLocationid());

			if (!orderDeliveryDetailMap.containsKey(predeliveryDetailPK)) {
				// Si la predistribución no tiene unidades pendientes se crea igual el detalle de despacho a partir de los detalles de la orden
				if (!orderDetailMap.containsKey(predeliveryDetailPK))
					throw new OperationFailedException("Error: detalle de despacho no encontrado");

				OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDetailMap.get(predeliveryDetailPK);

				orderDeliveryDetail.setAvailableunits(bulkDetail.getUnits());
				orderDeliveryDetail.setPendingunits(0.0);
				orderDeliveryDetail.setReceivedunits(0.0);

				orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
				// lo agrego como detalle de despacho
			} else {
				OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDeliveryDetailMap.get(predeliveryDetailPK);
				// Agregar lo disponible en el bulkdetail al orderdeliverydetail.
				orderDeliveryDetail.setAvailableunits(orderDeliveryDetail.getAvailableunits() + bulkDetail.getUnits());
				orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
			}
		}
		// Al final del paso 4 est�n listos todos los detalles de despachos de la Orden, con sus unidades disponibles

		// //////// 5 - Agregar los despachos con sus detalles y las solicitudes de cita //////////

		// Explorar los detalles de despachos de la Orden
		for (Iterator iterator = orderDeliveryDetailMap.values().iterator(); iterator.hasNext();) {
			OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) iterator.next();

			// EBO
			if (orderDeliveryDetail.getAvailableunits().doubleValue() <= 0) {
				continue;
			}

			// Actualizar el detalle de despacho de la Orden con el id del despacho creado
			orderDeliveryDetail.setDeliveryid(basedelivery.getId());

			// Agregar el despacho de la Orden si a�n no se encuentra en la BD
			orderDeliveryPK = new OrderDeliveryPK(orderDeliveryDetail.getOrderid(), orderDeliveryDetail.getDeliveryid());
			if (!orderDeliveryMap.containsKey(orderDeliveryPK)) {
				// Obtener los despachos previos de la Orden
				OrderDeliveryW[] orderDeliveries = orderdeliveryserver.getOrderDeliveriesOfOrder(orderDeliveryDetail.getOrderid());

				OrderDeliveryW orderDelivery = new OrderDeliveryW();

				orderDelivery.setClosed(false);
				orderDelivery.setRefdocument(guideNumber);
				orderDelivery.setImp(importationGuide);
				orderDelivery.setDeliveryindex(orderDeliveries.length + 1);
				orderDelivery.setDeliveryid(orderDeliveryDetail.getDeliveryid());
				orderDelivery.setOrderid(orderDeliveryDetail.getOrderid());
				orderDelivery.setDepartmentid(orderMap.get(orderDeliveryDetail.getOrderid()).getDepartmentid());
				orderDelivery.setFlowtypeid(basedelivery.getFlowtypeid());
				orderDelivery.setEstimatedunits(orderDeliveryDetail.getAvailableunits());
				
				orderDelivery = orderdeliveryserver.addOrderDelivery(orderDelivery);

				orderDeliveryMap.put(orderDeliveryPK, orderDelivery);
			} else {
				OrderDeliveryW orderDelivery = orderDeliveryMap.get(orderDeliveryPK);
				orderDelivery.setEstimatedunits(orderDelivery.getEstimatedunits() + orderDeliveryDetail.getAvailableunits());
				orderdeliveryserver.updateOrderDelivery(orderDelivery);
			}
			orderdeliveryserver.flush();

			// Agregar el detalle de despacho de la Orden
			orderdeliverydetailserver.addOrderDeliveryDetail(orderDeliveryDetail);
		}
		// Al final del paso 5 est�n creados todos los despachos con sus detalles y las solicitudes de cita

		// //////// 6 - Agregar el packing list //////////

		Map<Long, BoxW> boxMap = new HashMap<Long, BoxW>();
		Map<Long, PalletW> palletMap = new HashMap<Long, PalletW>();

		Long bulkGreaterNumber = 0L;

		// Obtener las cajas del packing list
		for (BoxW box : boxes) {
			boxMap.put(box.getId(), box);
		}

		// Obtener las cajas del packing list
		for (PalletW pallet : pallets) {
			palletMap.put(pallet.getId(), pallet);
		}

		// Explorar nuevamente los detalles de bultos
		int boxUnits = 0;
		int palletUnits = 0;
		for (BulkDetailW bulkDetail : bulkdetails) {
			// Obtener el detalle de despacho de Orden asociado por orden-item-local.
			PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

			predeliveryDetailPK.setOrderid(bulkDetail.getOrderid());
			predeliveryDetailPK.setItemid(bulkDetail.getItemid());
			predeliveryDetailPK.setLocationid(bulkDetail.getLocationid());

			if (!orderDeliveryDetailMap.containsKey(predeliveryDetailPK))
				throw new OperationFailedException("Error: detalle de despacho no encontrado");

			OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDeliveryDetailMap.get(predeliveryDetailPK);

			// Obtener el bulto (caja o pallet) asociado al detalle de bulto
			BulkW bulk = null;
			Long bulkId = bulkDetail.getBulkid();

			if (boxMap.containsKey(bulkId)) {
				bulk = (BoxW) boxMap.get(bulkId);
			}
			else if (palletMap.containsKey(bulkId)) {
				bulk = (PalletW) palletMap.get(bulkId);
			}
			else {
				throw new AccessDeniedException("Un detalle de bulto no corresponde a ning�n bulto");
			}

			// Validar el tipo de flujo en el bulto y su detalle
			ItemW item = itemserver.getById(bulkDetail.getItemid());

			if (bulk.getFlowtypeid() == null || bulk.getFlowtypeid().longValue() <= 0) {
				bulk.setFlowtypeid(item.getFlowtypeid());
			}

			if (item.getFlowtypeid().longValue() != bulk.getFlowtypeid().longValue())
				throw new AccessDeniedException("Los tipos de flujo de un bulto y su detalle son distintos");

			// Actualizar el bulto con el despacho
			bulk.setDeliveryid(orderDeliveryDetail.getDeliveryid());

			// Agregar el bulto si a�n no se encuentra en la BD
			if (bulk.getId().longValue() < 0) {
				if (bulk instanceof BoxW) {
					bulk = boxserver.addBox((BoxW) bulk);
					boxMap.put(bulkId, (BoxW) bulk);
					boxUnits++;
				} else if (bulk instanceof PalletW) {
					bulk = palletserver.addPallet((PalletW) bulk);
					palletMap.put(bulkId, (PalletW) bulk);
					palletUnits++;
				}
			}

			// Agregar el detalle de bulto a la BD
			bulkDetail.setDeliveryid(orderDeliveryDetail.getDeliveryid());
			bulkDetail.setBulkid(bulk.getId());

			bulkDetail = bulkdetailserver.addBulkDetail(bulkDetail);

			// Calcular el �ltimo N°mero de bulto
			if (bulkGreaterNumber < bulk.getNumber())
				bulkGreaterNumber = bulk.getNumber();
		}
		
		// Actualizar la cita
		basedatingrequest.setEstimatedboxes(basedatingrequest.getEstimatedboxes() + boxUnits);
		basedatingrequest.setEstimatedpallets(basedatingrequest.getEstimatedpallets() + palletUnits);
		basedatingrequest = datingrequestserver.updateDatingRequest(basedatingrequest);

		// Actualizar el �ltimo N°mero de bulto en el proveedor si es mayor que el actual
		if (vendor.getLastbulknumber() == null || (bulkGreaterNumber > vendor.getLastbulknumber())) {
			vendor.setLastbulknumber(bulkGreaterNumber.intValue());

			vendor = vendorserver.updateVendor(vendor);
		}

		doCalculateTotalBulkDetailOfDelivery(basedelivery.getId());
	}

	public DeliveryW[] doAddDeliveriesByPackingList(OrderW[] order, DeliveryW basedelivery, DatingRequestW basedatingrequest, PalletW[] pallets, BoxW[] boxes, BulkDetailW[] bulkdetails, boolean hasatc, Long guideNumber, String importationGuide) throws AccessDeniedException, NotFoundException, OperationFailedException {

		// Obtener tipo de estado de Despacho 'Cita Solicitada'
		DeliveryStateTypeW requestDatingDeliveryStateType = deliverystatetypeserver.getByPropertyAsSingleResult("name", "Cita Solicitada");
		Long rddst = requestDatingDeliveryStateType.getId();

		// //////// 1 - Obtener los datos de la Orden //////////

		if (order == null || order.length == 0) {
			throw new OperationFailedException("El Array de ordenes no puede estar vac�o");
		}

		Long deliveryLocationId = order[0].getDeliverylocationid();
		Long orderTypeId = order[0].getOrdertypeid();
		Long vendorId = order[0].getVendorid();

		Map<Long, OrderW> ordersMap = new HashMap<Long, OrderW>();
		Long[] ordersId = new Long[order.length];
		int i = 0;
		for (OrderW oc : order) {
			ordersMap.put(oc.getId(), oc);
			ordersId[i++] = oc.getId();
		}

		// Obtener el proveedor de la Orden
		VendorW vendor = vendorserver.getById(vendorId);

		// //////// 2 - Obtener los productos del packing list y sus tipos de flujo //////////

		Map<Long, ItemW> itemMap = new HashMap<Long, ItemW>();
		Map<Long, FlowTypeW> flowTypeMap = new HashMap<Long, FlowTypeW>();
		Map<Long, DatingRequestW> datingrequestMap = new HashMap<Long, DatingRequestW>();

		// Explorar los detalles de bultos
		for (BulkDetailW bulkDetail : bulkdetails) {
			// Obtener los productos del packing list
			Long itemid = bulkDetail.getItemid();

			if (!itemMap.containsKey(itemid)) {
				ItemW item = itemserver.getById(itemid);
				itemMap.put(itemid, item);
			}

			// Obtener los tipos de flujo en base a los productos del packing list
			ItemW item = itemMap.get(itemid);
			Long flowTypeId = item.getFlowtypeid();

			if (flowTypeId <= 0)
				throw new AccessDeniedException("El producto con código " + item.getInternalcode().trim() + " no tiene tipo de flujo");

			if (!flowTypeMap.containsKey(flowTypeId)) {
				FlowTypeW flowType = flowtypeserver.getById(flowTypeId);
				flowTypeMap.put(flowTypeId, flowType);
			}
		}

		// //////// 3 - Crear los detalles y los despachos de la Orden asociados a cada producto y tipo de flujo
		// respectivamente //////////

		Map<Long, DeliveryW> deliveryMap = new HashMap<Long, DeliveryW>();
		Map<PreDeliveryDetailPK, OrderDeliveryDetailW> orderDetailMap = new HashMap<PreDeliveryDetailPK, OrderDeliveryDetailW>();
		Map<PreDeliveryDetailPK, OrderDeliveryDetailW> orderDeliveryDetailMap = new HashMap<PreDeliveryDetailPK, OrderDeliveryDetailW>();

		// Explorar los tipos de flujo
		for (Iterator iterator = flowTypeMap.values().iterator(); iterator.hasNext();) {
			FlowTypeW flowType = (FlowTypeW) iterator.next();
			Long flowTypeId = flowType.getId();

			DeliveryTypeW deliveryType = getDeliveryTypeByOrderType(orderTypeId);
			Long deliveryTypeId = deliveryType.getId();

			// Crear un despacho de la Orden por cada tipo de flujo
			DeliveryW delivery = new DeliveryW();

			delivery.setNumber(deliveryserver.getNextSequenceDeliveryNumber());
			delivery.setCreated(new Date());
			delivery.setCurrentstatetypedate(new Date());
			delivery.setRefdocument(basedelivery.getRefdocument());
			delivery.setContainer(basedelivery.getContainer());
			delivery.setImp(basedelivery.getImp());
			delivery.setHasatc(hasatc);
			delivery.setDeliverytypeid(deliveryTypeId);
			delivery.setCurrentstatetypeid(rddst);
			delivery.setLocationid(deliveryLocationId);
			delivery.setOrdertypeid(orderTypeId);
			delivery.setFlowtypeid(flowTypeId);
			delivery.setVendorid(vendorId);

			// Agregar al mapa de despachos utilizando consecutivo
			Long deliveryIndex = new Long(deliveryMap.size());
			deliveryMap.put(deliveryIndex, delivery);

			// Obtener los detalles de predistribución de la Orden por cada combinación OC-Tipo de flujo
			PreDeliveryDetailW[] predeliveryDetails = predeliverydetailserver.getPreDeliveryDetailsOfOrderByFlowType(ordersId, flowTypeId);

			// Explorar los detalles de predistribución de la Orden
			for (PreDeliveryDetailW predeliveryDetail : predeliveryDetails) {
				// Crear un detalle de despacho de la Orden por cada detalle de predistribución
				OrderDeliveryDetailW orderDeliveryDetail = new OrderDeliveryDetailW();

				orderDeliveryDetail.setPendingunits(0.0);
				orderDeliveryDetail.setAvailableunits(0.0); // Inicializar las unidades disponibles a cero
				orderDeliveryDetail.setReceivedunits(0.0);
				orderDeliveryDetail.setOrderid(predeliveryDetail.getOrderid());
				orderDeliveryDetail.setDeliveryid(deliveryIndex); // Asociado al consecutivo de despachos creado
				orderDeliveryDetail.setItemid(predeliveryDetail.getItemid());
				orderDeliveryDetail.setLocationid(predeliveryDetail.getLocationid());

				// Agregar al mapa de detalles de la Orden utilizando �ndice orden-item-local
				PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

				predeliveryDetailPK.setOrderid(predeliveryDetail.getOrderid());
				predeliveryDetailPK.setItemid(predeliveryDetail.getItemid());
				predeliveryDetailPK.setLocationid(predeliveryDetail.getLocationid());

				orderDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);

				// Agregar al mapa de detalles de despachos de la Orden utilizando �ndice orden-item-local si el detalle
				// de predistribución tiene unidades pendientes
				if (predeliveryDetail.getPendingunits() > 0) {
					orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
				}
			}
		}

		// //////// 4 - Actualizar las unidades disponibles por detalles de despachos de la Orden //////////

		// Explorar nuevamente los detalles de bultos
		for (BulkDetailW bulkDetail : bulkdetails) {
			// Obtener el detalle de despacho de la Orden asociado
			PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

			predeliveryDetailPK.setOrderid(bulkDetail.getOrderid());
			predeliveryDetailPK.setItemid(bulkDetail.getItemid());
			predeliveryDetailPK.setLocationid(bulkDetail.getLocationid());
			if (!orderDeliveryDetailMap.containsKey(predeliveryDetailPK)) {
				// Si la predistribución no tiene unidades pendientes se crea igual el detalle de despacho a partir de
				// los detalles de la Orden
				if (!orderDetailMap.containsKey(predeliveryDetailPK)) {
					throw new OperationFailedException("Error: detalle de despacho no encontrado");
				}

				OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDetailMap.get(predeliveryDetailPK);

				orderDeliveryDetail.setAvailableunits(bulkDetail.getUnits());
				orderDeliveryDetail.setPendingunits(0.0);
				orderDeliveryDetail.setReceivedunits(0.0);

				orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
				// lo agrego como detalle de despacho
			} else {
				OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDeliveryDetailMap.get(predeliveryDetailPK);
				// Agregar lo disponible en el bulkdetail al orderdeliverydetail.
				orderDeliveryDetail.setAvailableunits(orderDeliveryDetail.getAvailableunits() + bulkDetail.getUnits());
				orderDeliveryDetail.setPendingunits(orderDeliveryDetail.getAvailableunits());
				orderDeliveryDetailMap.put(predeliveryDetailPK, orderDeliveryDetail);
			}
		}
		// Al final del paso 4 est�n listos todos los detalles de despachos de la Orden, con sus unidades disponibles

		// //////// 5 - Agregar los despachos con sus detalles y las solicitudes de cita //////////

		Map<OrderDeliveryPK, OrderDeliveryW> orderDeliveryMap = new HashMap<OrderDeliveryPK, OrderDeliveryW>();

		// Explorar los detalles de despachos de la Orden
		for (Iterator iterator = orderDeliveryDetailMap.values().iterator(); iterator.hasNext();) {
			OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) iterator.next();

			// EBO
			if (orderDeliveryDetail.getAvailableunits().doubleValue() <= 0)
				continue;

			// Obtener el despacho asociado
			Long deliveryIndex = orderDeliveryDetail.getDeliveryid();

			if (!deliveryMap.containsKey(deliveryIndex))
				throw new OperationFailedException("Lote de despacho no encontrado");

			DeliveryW delivery = (DeliveryW) deliveryMap.get(deliveryIndex);

			// Agregar el despacho si a�n no se encuentra en la BD
			if (delivery.getId() == null || delivery.getId().longValue() <= 0) {
				DeliveryW newDelivery = deliveryserver.addDelivery(delivery);

				// Agregar la solicitud de cita del despacho
				basedatingrequest.setNumber(datingrequestserver.getNextSequenceDatingRequestNumber());
				basedatingrequest.setDeliveryid(newDelivery.getId());
				basedatingrequest.setWhen(new Date());

				basedatingrequest = datingrequestserver.addDatingRequest(basedatingrequest);

				// Agregar el estado 'Cita Solicitada' del despacho
				DeliveryStateW deliveryState = new DeliveryStateW();

				deliveryState.setDeliveryid(newDelivery.getId());
				deliveryState.setDeliverystatetypeid(rddst);
				deliveryState.setWhen(new Date());

				deliverystateserver.addDeliveryState(deliveryState);

				// Actualizar el mapa modificando el despacho previo con todos sus nuevos par�metros tras la adición a
				// la BD
				deliveryMap.remove(deliveryIndex);
				deliveryMap.put(deliveryIndex, newDelivery);
				delivery = newDelivery;

				datingrequestMap.put(delivery.getId(), basedatingrequest);
			}

			// Actualizar el detalle de despacho de la Orden con el id del despacho creado
			orderDeliveryDetail.setDeliveryid(delivery.getId());

			// Obtener el despacho de la Orden asociado
			OrderDeliveryPK orderDeliveryPK = new OrderDeliveryPK(orderDeliveryDetail.getOrderid(), orderDeliveryDetail.getDeliveryid());

			// Agregar el despacho de la Orden si a�n no se encuentra en la BD
			if (!orderDeliveryMap.containsKey(orderDeliveryPK)) {
				// Obtener los despachos previos de la Orden
				OrderDeliveryW[] orderDeliveries = orderdeliveryserver.getOrderDeliveriesOfOrder(ordersId);

				OrderDeliveryW orderDelivery = new OrderDeliveryW();

				orderDelivery.setClosed(false);
				orderDelivery.setRefdocument(guideNumber);
				orderDelivery.setImp(importationGuide);
				orderDelivery.setDeliveryindex(orderDeliveries.length + 1);
				orderDelivery.setDeliveryid(orderDeliveryDetail.getDeliveryid());
				orderDelivery.setOrderid(orderDeliveryDetail.getOrderid());
				orderDelivery.setDepartmentid(ordersMap.get(orderDeliveryDetail.getOrderid()).getDepartmentid());
				orderDelivery.setFlowtypeid(delivery.getFlowtypeid());
				orderDelivery.setEstimatedunits(orderDeliveryDetail.getAvailableunits());
				
				orderDelivery = orderdeliveryserver.addOrderDelivery(orderDelivery);

				orderDeliveryMap.put(orderDeliveryPK, orderDelivery);
			} else {
				OrderDeliveryW orderDelivery = orderDeliveryMap.get(orderDeliveryPK);
				orderDelivery.setEstimatedunits(orderDelivery.getEstimatedunits() + orderDeliveryDetail.getAvailableunits());
				orderdeliveryserver.updateOrderDelivery(orderDelivery);
			}
			orderdeliveryserver.flush();

			// Agregar el detalle de despacho de la Orden
			orderdeliverydetailserver.addOrderDeliveryDetail(orderDeliveryDetail);
		}
		// Al final del paso 5 est�n creados todos los despachos con sus detalles y las solicitudes de cita

		// //////// 6 - Agregar el packing list //////////

		Map<Long, BoxW> boxMap = new HashMap<Long, BoxW>();
		Map<Long, PalletW> palletMap = new HashMap<Long, PalletW>();

		Long bulkGreaterNumber = 0L;

		// Obtener las cajas del packing list
		for (BoxW box : boxes) {
			boxMap.put(box.getId(), box);
		}

		// Obtener las cajas del packing list
		for (PalletW pallet : pallets) {
			palletMap.put(pallet.getId(), pallet);
		}

		// Explorar nuevamente los detalles de bultos
		DatingRequestW dr = null;
		for (BulkDetailW bulkDetail : bulkdetails) {
			// Obtener el detalle de despacho de Orden asociado por orden-item-local.
			PreDeliveryDetailPK predeliveryDetailPK = new PreDeliveryDetailPK();

			predeliveryDetailPK.setOrderid(bulkDetail.getOrderid());
			predeliveryDetailPK.setItemid(bulkDetail.getItemid());
			predeliveryDetailPK.setLocationid(bulkDetail.getLocationid());

			if (!orderDeliveryDetailMap.containsKey(predeliveryDetailPK))
				throw new OperationFailedException("Error: detalle de despacho no encontrado");

			OrderDeliveryDetailW orderDeliveryDetail = (OrderDeliveryDetailW) orderDeliveryDetailMap.get(predeliveryDetailPK);

			dr = datingrequestMap.get(orderDeliveryDetail.getDeliveryid());
			
			// Obtener el bulto (caja o pallet) asociado al detalle de bulto
			BulkW bulk = null;
			Long bulkId = bulkDetail.getBulkid();

			if (boxMap.containsKey(bulkId)) {
				bulk = (BoxW) boxMap.get(bulkId);
			}
			else if (palletMap.containsKey(bulkId)) {
				bulk = (PalletW) palletMap.get(bulkId);
			}
			else {
				throw new AccessDeniedException("Un detalle de bulto no corresponde a ning�n bulto");
			}

			// Validar el tipo de flujo en el bulto y su detalle
			Long itemId = bulkDetail.getItemid();
			ItemW item = itemMap.get(itemId);

			if (bulk.getFlowtypeid() == null || bulk.getFlowtypeid().longValue() <= 0) {
				bulk.setFlowtypeid(item.getFlowtypeid());
			}

			if (item.getFlowtypeid().longValue() != bulk.getFlowtypeid().longValue())
				throw new AccessDeniedException("Los tipos de flujo de un bulto y su detalle son distintos");

			// Actualizar el bulto con el despacho
			bulk.setDeliveryid(orderDeliveryDetail.getDeliveryid());

			// Agregar el bulto si a�n no se encuentra en la BD
			if (bulk.getId().longValue() < 0) {
				if (bulk instanceof BoxW) {
					bulk = boxserver.addBox((BoxW) bulk);
					boxMap.put(bulkId, (BoxW) bulk);
					dr.setEstimatedboxes(dr.getEstimatedboxes() + 1);
				} else if (bulk instanceof PalletW) {
					bulk = palletserver.addPallet((PalletW) bulk);
					palletMap.put(bulkId, (PalletW) bulk);
					dr.setEstimatedpallets(dr.getEstimatedpallets() + 1);
				}
			}
			
			datingrequestMap.put(orderDeliveryDetail.getDeliveryid(), dr);

			// Agregar el detalle de bulto a la BD
			bulkDetail.setDeliveryid(orderDeliveryDetail.getDeliveryid());
			bulkDetail.setBulkid(bulk.getId());

			bulkDetail = bulkdetailserver.addBulkDetail(bulkDetail);

			// Calcular el �ltimo N°mero de bulto
			if (bulkGreaterNumber < bulk.getNumber())
				bulkGreaterNumber = bulk.getNumber();
		}

		// Actualizar el �ltimo N°mero de bulto en el proveedor si es mayor que el actual
		if (vendor.getLastbulknumber() == null || (bulkGreaterNumber > vendor.getLastbulknumber())) {
			vendor.setLastbulknumber(bulkGreaterNumber.intValue());

			vendor = vendorserver.updateVendor(vendor);
		}

		for (Iterator iter = datingrequestMap.values().iterator(); iter.hasNext();) {
			dr = (DatingRequestW) iter.next();
			datingrequestserver.updateDatingRequest(dr);
		}
		
		// Obtener los despachos creados desde el mapa
		DeliveryW[] deliveries = (DeliveryW[]) deliveryMap.values().toArray(new DeliveryW[deliveryMap.size()]);

		for (int j = 0; j < deliveries.length; j++) {
			doCalculateTotalBulkDetailOfDelivery(deliveries[j].getId());
		}

		return deliveries;
	}

	public AddDeliveryOfOrdersAndFlowTypesResultDTO doAddDeliveryOfOrdersAndFlowTypes(AddDeliveryOfOrdersAndFlowTypesInitParamDTO initParams, boolean byVeVCD) {
		AddDeliveryOfOrdersAndFlowTypesResultDTO result = new AddDeliveryOfOrdersAndFlowTypesResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			long deliverylocationid = 0;
			long departmentid = 0;

			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
			List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
			String error = "";

			// TIPOS DE DESPACHO
			DeliveryTypeW deliverytypeStock = deliverytypeserver.getByPropertyAsSingleResult("code", "STOCK");
			DeliveryTypeW deliverytypeTienda = deliverytypeserver.getByPropertyAsSingleResult("code", "TIENDA");
			DeliveryTypeW deliverytypeVeVCD = deliverytypeserver.getByPropertyAsSingleResult("code", "VEVCD");

			// ESTADOS DE ORDEN
			OrderStateTypeW typeaccepted = orderstatetypeserver.getByPropertyAsSingleResult("code", "ACEPTADA");

			// TIPO POR PREDISTRIBUIR
			OrderTypeW ordertypeppred = ordertypeserver.getByPropertyAsSingleResult("code", "R");

			Calendar calnow = Calendar.getInstance();
			calnow.set(Calendar.HOUR_OF_DAY, 0);
			calnow.set(Calendar.MINUTE, 0);
			calnow.set(Calendar.SECOND, 0);
			calnow.set(Calendar.MILLISECOND, 0);
			Date today = calnow.getTime();

			List<DeliveryW> arrDeliveries = new ArrayList<DeliveryW>();
			LocationW location = null;

			Long[] flowtypeids = initParams.getFlowtypeids();
			Long[] orderids = initParams.getOrderids();

			// TIPOS DE DESPACHO
			Map<Long, DeliveryTypeW> dtypeMap = new HashMap<Long, DeliveryTypeW>();
			DeliveryTypeW[] dtypeArr = deliverytypeserver.getAllAsArray();
			for (DeliveryTypeW dType : dtypeArr) {
				if (!dtypeMap.containsKey(dType.getId())) {
					dtypeMap.put(dType.getId(), dType);
				}
			}

			// TIPO DE FLUJOS
			Map<Long, FlowTypeW> ftypeMap = new HashMap<Long, FlowTypeW>();
			FlowTypeW[] ftypeArr = flowtypeserver.getAllAsArray();
			for (FlowTypeW fType : ftypeArr) {
				if (!ftypeMap.containsKey(fType.getId())) {
					ftypeMap.put(fType.getId(), fType);
				}
			}

			if (flowtypeids != null) {
				for (int i = 0; i < flowtypeids.length; i++) {
					Long ftpk = flowtypeids[i];

					// Arreglo que almacena los detalles de despacho por tipo de flujo que son VeV CD
					List<OrderDeliveryDetailW> detailsVeVCD = new ArrayList<OrderDeliveryDetailW>();

					// Arreglo que almacena los detalles de despacho por tipo de flujo
					List<OrderDeliveryDetailW> detailsTienda = new ArrayList<OrderDeliveryDetailW>();

					// Mapa que almacena los detalles de despacho por tipo de flujo de tipo para Stock (CD). En este
					// caso es por OC
					Map<Long, List<OrderDeliveryDetailW>> odStock = new HashMap<Long, List<OrderDeliveryDetailW>>();
					List<OrderDeliveryDetailW> detailsStock = new ArrayList<OrderDeliveryDetailW>();

					// Mapa para contar las unidades disponibles por orden.
					// Como una OC puede estar en varios despachos, son mapas distintos
					Map<Long, Double> mapAvailableUnitsStock = new HashMap<Long, Double>();
					Map<Long, Double> mapAvailableUnitsTienda = new HashMap<Long, Double>();
					Map<Long, Double> mapAvailableUnitsVeVCD = new HashMap<Long, Double>();

					Map<Long, OrderW> ordersMap = new HashMap<Long, OrderW>();
					for (int j = 0; j < orderids.length; j++) {
						Long orderid = orderids[j];
						// Por cada orden, se verifica:
						// 1) Que la orden no est� vencida
						// 2) Que el tipo de orden, el lugar de entrega y el proveedor sean �nicos;
						// 3) Que la orden est� en estado Aceptada o Modificada (no bloqueada ni eliminada)
						// 4) El sistema valida que no haya seleccionada ninguna OC de tipo "por predistribuir"
						OrderW order = null;
						try {
							order = orderserver.getById(orderid, LockModeType.PESSIMISTIC_WRITE);
						
						} catch (Exception e1) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1801");
						}
						
						if (order == null) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1800");
						}
						
						if (!order.getVendorid().equals(vendor.getId())) {
							error = "Las órdenes seleccionadas no corresponden con el proveedor escogido en el filtro";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));

							// Ordenar errores
							Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
							result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
						}
						
						ordersMap.put(order.getId(), order);

						OrderTypeW currenttype = ordertypeserver.getById(order.getOrdertypeid());

						// VALIDA QUE LA OC NO SEA POR PREDISTRIBUIR
						if (order.getOrdertypeid().equals(ordertypeppred.getId())) {
							error = "No se pueden escoger ordenes de compra de tipo 'Por Predistribuir' (N° " + order.getNumber() + ").";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						OrderStateTypeW currentstate = orderstatetypeserver.getById(order.getCurrentstatetypeid());

						// VALIDA QUE SEA UN ESTADO VIGENTE
						if (!currentstate.getValid()) {
							error = "La orden de compra N° " + order.getNumber() + " no se encuentra en un estado vigente";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// VALIDA QUE NO ESTE VENCIDA
						if (order.getExpiration().before(today)) {
							error = "La orden de compra N° " + order.getNumber() + " ha superado su fecha de vencimiento";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// VALIDA QUE TENGAN EL MISMO LUGAR DE ENTREGA
						if (deliverylocationid == 0) {
							deliverylocationid = order.getDeliverylocationid();
						}
						else if (deliverylocationid != order.getDeliverylocationid()) {
							error = "Las órdenes de compra seleccionadas deben estar asociadas al mismo lugar de entrega";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// EBO 29-10-2014: VALIDA QUE TENGAN EL MISMO DEPARTAMENTO
						if (departmentid == 0)
							departmentid = order.getDepartmentid();
						else if (departmentid != order.getDepartmentid()) {
							error = "Las órdenes de compra seleccionadas deben estar asociadas al mismo departamento";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// VALIDA QUE LA OC TENGA UNIDADES PENDIENTES
						if (order.getPendingunits() <= 0) {
							error = "La orden de compra N° " + order.getNumber() + " no presenta unidades pendientes por entregar";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// VALIDA QUE EL TIPO SEA AGRUPABLE
						if (orderids.length > 1 && !currenttype.getGroupable()) {
							error = "Las órdenes de compra seleccionadas no se pueden mezclar en un mismo despacho debido a su tipo";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// Si hay errores hasta el momento, no se puede continuar
						if (baseresultlist.size() > 0) {
							// Ordenar errores
							Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
							result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
						}

						// SE ACEPTA LA OC
						if (!order.getCurrentstatetypeid().equals(typeaccepted.getId()))
							order = buyorderreportmanager.doAcceptOrders(orderid)[0];

						// Por cada orden, se buscan las predistribuciones asociadas al tipo de flujo
						// Por cada predistribución con unidades pendientes se crea un detalle de despacho correspondiente
						PreDeliveryDetailW[] predeliveries = predeliverydetailserver.getByQueryAsArray("select pdd from PreDeliveryDetail as pdd, OrderDetail as od, Item as it where pdd.orderdetail = od and od.item = it and od.order.id = " + orderid + " and it.flowtype.id = " + ftpk);

						if (byVeVCD) {
							for (int k = 0; k < predeliveries.length; k++) {
								PreDeliveryDetailW predelivery = predeliveries[k];

								if (predelivery.getPendingunits() > 0) {
									OrderDeliveryDetailW oddetail = new OrderDeliveryDetailW();
									oddetail.setOrderid(predelivery.getOrderid());
									oddetail.setItemid(predelivery.getItemid());
									oddetail.setLocationid(predelivery.getLocationid());
									oddetail.setAvailableunits(predelivery.getPendingunits());
									oddetail.setPendingunits(predelivery.getPendingunits());
									oddetail.setReceivedunits(0.0);

									detailsVeVCD.add(oddetail);

									Double availableunits = 0D;

									// Agregar las unidades disponibles al mapa
									if (mapAvailableUnitsVeVCD.containsKey(orderid)) {
										availableunits = mapAvailableUnitsVeVCD.get(orderid);
									}
									Double newavailable = oddetail.getAvailableunits() + availableunits;
									mapAvailableUnitsVeVCD.put(orderid, newavailable);
								}
							}
						} else {
							// 13-03-2008. No se deben generar detalles de despacho para
							// locales sin shortname (porque no existen fisicamente)
							LocationW[] locations = locationserver.getByQueryAsArray("select lo from Location as lo, PreDeliveryDetail as pdd where pdd.location = lo and pdd.orderdetail.order.id = " + orderid);

							Map<Long, LocationW> locationofOrder = new HashMap<Long, LocationW>();
							for (int k = 0; k < locations.length; k++) {
								locationofOrder.put(locations[k].getId(), locations[k]);
							}

							for (int k = 0; k < predeliveries.length; k++) {
								PreDeliveryDetailW predelivery = predeliveries[k];

								location = (LocationW) locationofOrder.get(predelivery.getLocationid());
								if (location.getShortname().trim().equals("")) {
									logger.info("Error en generación de despacho para orden id " + predelivery.getOrderid() + ", el local id " + predelivery.getLocationid() + " no tiene código corto");
									continue;
								}

								if (predelivery.getPendingunits() > 0) {
									OrderDeliveryDetailW oddetail = new OrderDeliveryDetailW();
									oddetail.setOrderid(predelivery.getOrderid());
									oddetail.setItemid(predelivery.getItemid());
									oddetail.setLocationid(predelivery.getLocationid());
									oddetail.setAvailableunits(predelivery.getPendingunits());
									oddetail.setPendingunits(predelivery.getPendingunits());
									oddetail.setReceivedunits(0.0);

									Double availableunits = 0D;

									// Veo a que despacho corresponde
									if (oddetail.getLocationid().equals(order.getDeliverylocationid())) {
										// Es a CD, por lo que se debe guardar el detalle por OC

										// 08052008: Se revisa si desde el cliente se quere stock
										if (!initParams.getStock())
											continue;

										if (!odStock.containsKey(oddetail.getOrderid()))
											detailsStock = new ArrayList();
										else
											detailsStock = (ArrayList) odStock.get(oddetail.getOrderid());
										detailsStock.add(oddetail);
										odStock.put(oddetail.getOrderid(), detailsStock);

										// Agregar las unidades disponibles al mapa
										if (mapAvailableUnitsStock.containsKey(orderid)) {
											availableunits = mapAvailableUnitsStock.get(orderid);
										}
										Double newavailable = oddetail.getAvailableunits() + availableunits;
										mapAvailableUnitsStock.put(orderid, newavailable);

									}
									else {
										// 08052008: Se revisa si desde el cliente se quiere tienda
										if (!initParams.getStore())
											continue;

										// Es predistribuida por lo que se genera un despacho para todos los locales
										detailsTienda.add(oddetail);

										// Agregar las unidades disponibles al mapa
										if (mapAvailableUnitsTienda.containsKey(orderid)) {
											availableunits = mapAvailableUnitsTienda.get(orderid);
										}
										Double newavailable = oddetail.getAvailableunits() + availableunits;
										mapAvailableUnitsTienda.put(orderid, newavailable);
									}
								}
							}
						}
					}

					if (byVeVCD) {
						DeliveryW newdelivery = null;
						if (detailsVeVCD.size() > 0) {
							newdelivery = doAddOrderDeliveryByDetails(detailsVeVCD, ftpk, deliverylocationid, vendor.getId(), deliverytypeVeVCD, mapAvailableUnitsVeVCD, ordersMap);
							// Agregar el lote al mapa
							arrDeliveries.add(newdelivery);
						}
					} else {
						// Para Tienda
						DeliveryW newdelivery = null;
						if (detailsTienda.size() > 0) {
							newdelivery = doAddOrderDeliveryByDetails(detailsTienda, ftpk, deliverylocationid, vendor.getId(), deliverytypeTienda, mapAvailableUnitsTienda, ordersMap);
							// Agregar el lote al mapa
							arrDeliveries.add(newdelivery);
						}
						// Para tienda se debe recorrer por OC
						for (Iterator iter = odStock.values().iterator(); iter.hasNext();) {
							ArrayList detailsSt = (ArrayList) iter.next();
							if (detailsSt.size() > 0) {
								newdelivery = doAddOrderDeliveryByDetails(detailsSt, ftpk, deliverylocationid, vendor.getId(), deliverytypeStock, mapAvailableUnitsStock, ordersMap);
								// Agregar el lote al mapa
								arrDeliveries.add(newdelivery);
							}
						}
					}
				}
			}

			List<AddDeliveryOfOrdersAndFlowTypesDataDTO> reportList = new ArrayList<AddDeliveryOfOrdersAndFlowTypesDataDTO>();

			for (DeliveryW delivery : arrDeliveries) {
				AddDeliveryOfOrdersAndFlowTypesDataDTO report = new AddDeliveryOfOrdersAndFlowTypesDataDTO();

				report.setDeliveryid(delivery.getId());
				report.setDeliverynumber(delivery.getNumber());
				report.setDeliverytype(dtypeMap.get(delivery.getDeliverytypeid()).getName());
				report.setFlowtype(ftypeMap.get(delivery.getFlowtypeid()).getName());

				reportList.add(report);
			}

			result.setReport(reportList.toArray(new AddDeliveryOfOrdersAndFlowTypesDataDTO[reportList.size()]));

			// RECALCULA UNIDADES
			for (Long orderid : initParams.getOrderids()) {
				buyorderreportmanager.doCalculateTotalOfOrder(orderid);
			}

			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public AddDeliveryOfOrdersAndFlowTypesResultDTO doAddImportedDeliveryOfOrders(AddImportedDeliveryOfOrdersInitParamDTO initParams) {

		AddDeliveryOfOrdersAndFlowTypesResultDTO resultDTO = new AddDeliveryOfOrdersAndFlowTypesResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {
			// Obtener el proveedor
			//VendorW vendor = vendorserver.getById(vendor.getId());
			
			// Validar que el proveedor sea importado
			if (vendor.getDomestic()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"El proveedor asociado no es importado", false);
			}
			
			// Validar N°mero de contenedor
			if (initParams.getNCont() == null || "".equals(initParams.getNCont().trim())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"El Número de contenedor es obligatorio", false);
			}
			
			// Valida que se informe Nro de Guía 
			if(initParams.getNGuia() == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
						"El Número de Guía es obligatorio", false);
			}
			
			// Validar datos de la solicitud de cita
			if (initParams.getUserName() == null || "".equals(initParams.getUserName().trim())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"Nombre en solicitud de cita es obligatorio", false);
			}
			
			if (initParams.getEmail() == null || "".equals(initParams.getEmail().trim())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"E-mail en solicitud de cita es obligatorio", false);
			}
			
			// Validar que se incluyan detalles para el despacho
			if (initParams.getPreddetails() == null || initParams.getPreddetails().length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"La solicitud debe incluir al menos un detalle de predistribución", false);
			}
			
			// Listados
			List<Long> orderIds = new ArrayList<Long>();
			List<String> atcCodes = new ArrayList<String>();
			for (PreDeliveryDetailDataDTO detail : initParams.getPreddetails()) {
				if (!orderIds.contains(detail.getOrderid())) {
					orderIds.add(detail.getOrderid());
				}
				
				if (detail.getAtccode() != null && !detail.getAtccode().equals("") && !atcCodes.contains(detail.getAtccode())) {
					atcCodes.add(detail.getAtccode());
				}
			}
			
			// Obtener mapa de órdenes
			OrderW[] orders = orderserver.getOrdersByIds(orderIds);
			HashMap<Long, OrderW> orderMap = new HashMap<Long, OrderW>();
			Long deliverylocationid = null;
			boolean atcLocation = false;
			for (OrderW order : orders) {
				// JPE 20190610
				// Validar que todas las órdenes tengan el mismo local de entrega
				if (deliverylocationid == null) {
					deliverylocationid = order.getDeliverylocationid();
					
					// Determinar si el lugar de entrega de las OCs est� configurado para trabajar con ATC
					PropertyInfoDTO prop1 = new PropertyInfoDTO("code", "code", "RECIBE_ATC");
					PropertyInfoDTO prop2 = new PropertyInfoDTO("location.id", "locationid", deliverylocationid);
					List<PropertyLocationW> propertylocations = propertylocationserver.getByProperties(prop1, prop2);
					atcLocation = propertylocations != null && propertylocations.size() > 0;
				}
				else if (!order.getDeliverylocationid().equals(deliverylocationid)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000",
																	"Las órdenes de compra deben tener el mismo local de entrega", false);
				}
				
				orderMap.put(order.getId(), order);
			}
			
			// Obtener mapa de detalles de órdenes
			OrderDetailW[] orderDetails = orderdetailserver.getOrderDetailsByOrderIds(orderIds);
			HashMap<Long, HashMap<OrderDetailPK, OrderDetailW>> orderDetailMap = new HashMap<Long, HashMap<OrderDetailPK, OrderDetailW>>();
			HashMap<OrderDetailPK, OrderDetailW> odMap = null;
			OrderDetailPK odPk = null;
			for (OrderDetailW orderDetail : orderDetails) {
				if (orderDetailMap.containsKey(orderDetail.getOrderid())) {
					odMap = orderDetailMap.get(orderDetail.getOrderid());
				}
				else {
					odMap = new HashMap<OrderDetailPK, OrderDetailW>();
				}
				
				odPk = new OrderDetailPK(orderDetail.getOrderid(), orderDetail.getItemid());
				odMap.put(odPk, orderDetail);
				orderDetailMap.put(orderDetail.getOrderid(), odMap);
			}
			
			// Obtener mapa de predistribuciones de las órdenes
			// Obtener la cantidad de detalles de predistribución de cada orden
			PreDeliveryDetailW[] preDeliveryDetails = predeliverydetailserver.getPreDeliveryDetailsByOrderIds(orderIds);
			HashMap<Long, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>> preDeliveryDetailMap = new HashMap<Long, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>>();
			HashMap<Long, Integer> pddSequenceMap = new HashMap<Long, Integer>();
			HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> pddMap = null;
			PreDeliveryDetailPK pddPk = null;
			for (PreDeliveryDetailW preDeliveryDetail : preDeliveryDetails) {
				if (preDeliveryDetailMap.containsKey(preDeliveryDetail.getOrderid())) {
					pddMap = preDeliveryDetailMap.get(preDeliveryDetail.getOrderid());
				}
				else {
					pddMap = new HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
				}
				
				pddPk = new PreDeliveryDetailPK(preDeliveryDetail.getOrderid(), preDeliveryDetail.getItemid(), preDeliveryDetail.getLocationid());
				pddMap.put(pddPk, preDeliveryDetail);
				preDeliveryDetailMap.put(preDeliveryDetail.getOrderid(), pddMap);
				
				if (pddSequenceMap.containsKey(preDeliveryDetail.getOrderid())) {
					pddSequenceMap.put(preDeliveryDetail.getOrderid(), pddSequenceMap.get(preDeliveryDetail.getOrderid()) + 1);
				}
				else {
					pddSequenceMap.put(preDeliveryDetail.getOrderid(), 1);
				}
			}
			
			// Obtener mapa con la relación de todos los productos que deben estar asociados a cada ATC
			// Obtener mapa con la relación de todas las curvas de productos asociadas a cada ATC
			HashMap<String, List<Long>> atcMap = null;
			HashMap<String, Long> iaMap = null;
			HashMap<Long, String> itemCodeMap = null;
			List<Long> itemIds = null;
			if (atcLocation && atcCodes.size() > 0) {
				List<ItemAtcDTO> itemAtcs = itematcserver.getItemDataByATCCodes(atcCodes);
				atcMap = new HashMap<String, List<Long>>();
				iaMap = new HashMap<String, Long>();
				itemCodeMap = new HashMap<Long, String>();
				for (ItemAtcDTO itemAtc : itemAtcs) {
					if (atcMap.containsKey(itemAtc.getAtccode())) {
						itemIds = atcMap.get(itemAtc.getAtccode());
					}
					else {
						itemIds = new ArrayList<Long>();
					}
					
					itemIds.add(itemAtc.getItemid());
					atcMap.put(itemAtc.getAtccode(), itemIds);
					
					iaMap.put(itemAtc.getAtccode() + "_" + itemAtc.getItemid(), itemAtc.getCurve());
					if (!itemCodeMap.containsKey(itemAtc.getItemid())) {
						itemCodeMap.put(itemAtc.getItemid(), itemAtc.getItemsku());
					}
				}
			}
						
			// Obtener mapas de detalles válidos (unidades mayores a cero)
			HashMap<String, List<PreDeliveryDetailDataDTO>> bulkDataMap = new HashMap<String, List<PreDeliveryDetailDataDTO>>();
			HashMap<String, List<Long>> orderAtcItemMap = new HashMap<String, List<Long>>();
			HashMap<String, Double> orderAtcItemUnitsMap = new HashMap<String, Double>();			
			HashMap<String, List<PreDeliveryDetailPK>> flowTypeMap = new HashMap<String, List<PreDeliveryDetailPK>>();
			List<PreDeliveryDetailDataDTO> pddDataList = new ArrayList<PreDeliveryDetailDataDTO>();			
			List<PreDeliveryDetailPK> pddPKList = null;
			OrderDetailW orderDetail = null;
			String key = "";
			String ftStr = "";			
			for (PreDeliveryDetailDataDTO detail : initParams.getPreddetails()) {
				// Identificar los detalles con unidades a entregar mayores que cero
				if (detail.getTodeliveryunits() <= 0) {
					continue;
				}

				// Agrupar todos aquellos productos que en cada OC se hayan informado con ATC y su local de entrega est� habilitado para recibirlos
				odMap = orderDetailMap.get(detail.getOrderid());
				odPk = new OrderDetailPK(detail.getOrderid(), detail.getItemid());
				orderDetail = odMap.get(odPk);
				if (atcLocation && orderDetail.getHasatc() != null && orderDetail.getHasatc()) {
					// Mapa con la relación de orden-local-ATC-productos válidos indicados
					key = detail.getOrderid() + "_" + detail.getLocationcode() + "_" + detail.getAtccode();
					if (orderAtcItemMap.containsKey(key)) {
						itemIds = orderAtcItemMap.get(key);
					}
					else {
						itemIds = new ArrayList<Long>();
					}
					itemIds.add(detail.getItemid());
					orderAtcItemMap.put(key, itemIds);
					
					// Mapa con los datos de bultos
					if (bulkDataMap.containsKey(key)) {
						pddDataList = bulkDataMap.get(key);
					}
					else {
						pddDataList = new ArrayList<PreDeliveryDetailDataDTO>();
					}
					pddDataList.add(detail);
					bulkDataMap.put(key, pddDataList);
					
					// Mapa con la relación de orden-local-ATC-producto-unidades v�lidas indicadas
					orderAtcItemUnitsMap.put(detail.getOrderid() + "_" + detail.getLocationcode() + "_" + detail.getAtccode() + "_" + detail.getItemid(), detail.getTodeliveryunits());
					
					// Para los PL de 'Cencosud Importado' se debe agrupar por tipo de flujo-ATC
					ftStr = vendor.getRut().equalsIgnoreCase("IMP") ? detail.getFlowtypeid() + "_ATC" : "";
				}
				// Agrupar todos aquellos productos que en cada OC se hayan informado con SKU o cuyo local de entrega no est� habilitado para ATC
				else {
					key = detail.getOrderid() + "_" + detail.getLocationcode() + "_" + detail.getItemsku();
										
					// Mapa con los datos de bultos
					if (bulkDataMap.containsKey(key)) {
						pddDataList = bulkDataMap.get(key);
					}
					else {
						pddDataList = new ArrayList<PreDeliveryDetailDataDTO>();
					}
					pddDataList.add(detail);
					bulkDataMap.put(key, pddDataList);
					
					// Para los PL de 'Cencosud Importado' se debe agrupar por tipo de flujo-SKU
					ftStr = vendor.getRut().equalsIgnoreCase("IMP") ? detail.getFlowtypeid() + "_SKU" : "";
				}
				
				if (flowTypeMap.containsKey(ftStr)) {
					pddPKList = flowTypeMap.get(ftStr);							
				}
				else {
					pddPKList = new ArrayList<PreDeliveryDetailPK>();
				}
				
				pddPKList.add(new PreDeliveryDetailPK(detail.getOrderid(), detail.getItemid(), detail.getLocationid()));
				flowTypeMap.put(ftStr, pddPKList);
			}
			
			HashMap<String, Integer> atcBulksMap = new HashMap<String, Integer>();
			if (orderAtcItemMap.size() > 0) {
				// Validar los ATC
				List<BaseResultDTO> validationErrors = new ArrayList<BaseResultDTO>();
				String[] keyArray = null;
				String error = "";
				Long orderId = null;
				String atcCode = "";
				Double atcUnits = null;
				Double itemUnits = null;
				Double multiplier = null;
				for (Map.Entry<String, List<Long>> orderAtcData : orderAtcItemMap.entrySet()) {
					keyArray = orderAtcData.getKey().split("_");
					orderId = Long.parseLong(keyArray[0]);
					atcCode = keyArray[2]; // [1] corresponde al local de destino
					
					itemIds = orderAtcData.getValue();
					
					atcUnits = 0D;
					multiplier = null;
					for (Long itemId : atcMap.get(atcCode)) {
						// Validar que se hayan indicado para todas las órdenes todos los productos que deban componer los ATC
						if (itemIds.contains(itemId)) {							
							key = orderAtcData.getKey() + "_" + itemId;
							itemUnits = orderAtcItemUnitsMap.get(key);
							atcUnits += itemUnits;
							
							key = atcCode + "_" + itemId;
							if (multiplier == null) {
								// Validar que las unidades indicadas est�n en la proporción correcta de curvas
								if (itemUnits % iaMap.get(key) != 0) {
									error = "Para la OC " + orderMap.get(orderId).getNumber() + ", la cantidad a entregar del producto " +
												itemCodeMap.get(itemId) + " no es proporcional a su valor de curva";
									validationErrors.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", error));
								}
								else {
									multiplier = itemUnits / iaMap.get(key);
								}							
							}
							// Validar que las unidades indicadas corresponden a curvas completas
							else if (itemUnits / iaMap.get(key) != multiplier) {
								error = "Para la OC " + orderMap.get(orderId).getNumber() + ", las cantidades a entregar indicadas para " +
											"todos los productos del ATC " + atcCode + " no conforman curvas completas";
								validationErrors.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", error));
							}
						}
						else {
							error = "Debe informar cantidades mayores a cero para todos los productos del ATC " + atcCode +
											" en la OC " + orderMap.get(orderId).getNumber();
							validationErrors.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", error));
						}
					}
					
					// Mapa con la cantidad de bultos de cada ATC
					if (multiplier != null) {
						atcBulksMap.put(orderAtcData.getKey(), multiplier.intValue());
					}
				}
				
				if (validationErrors.size() > 0) {
					// Ordenar errores
					BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
					Arrays.sort(validationErrors.toArray(new BaseResultDTO[validationErrors.size()]), comparator);
					resultDTO.setValidationerrors(validationErrors.toArray(new BaseResultDTO[validationErrors.size()]));
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000");
				}
			}
			
			List<PreDeliveryDetailPK> validPddPks = null;
			List<DeliveryW> deliveries = null;
			if (vendor.getRut().equalsIgnoreCase("IMP")) {				
				// Obtener tipo de estado de despacho 'Cita Solicitada'
				DeliveryStateTypeW dstRequested = deliverystatetypeserver.getByPropertyAsSingleResult("name", "Cita Solicitada");
				
				deliveries = new ArrayList<DeliveryW>();
				
				DeliveryDTO[] basedeliveries = null;
				DeliveryW delivery = null;
				String[] keyStr = null;
				for (Map.Entry<String, List<PreDeliveryDetailPK>> ft : flowTypeMap.entrySet()) {
					// Validar si existe un despacho para el mismo N°mero de contenedor, tipo de flujo, local de despacho y si es con ATC o no
					keyStr = ft.getKey().split("_");
					basedeliveries = deliveryserver.getCencosudImportedDeliveriesByContainerDeliveryLocationFlowTypeAndCodeType(initParams.getNCont(), deliverylocationid, Long.parseLong(keyStr[0]), keyStr[1]);
					
					validPddPks = ft.getValue();
					
					if (basedeliveries != null && basedeliveries.length > 0) {
						for (DeliveryDTO dl : basedeliveries) {
							// Validar si el despacho est� en 'Cita Solicitada' (actualizar)
							if (dl.getCurrentstatetypeid().equals(dstRequested.getId())) {
								delivery = deliveryserver.getById(dl.getId());
								break;
							}
						}
					}
					// En las demás condiciones se crea un despacho nuevo
					
					// Crear/actualizar los despachos con los productos/atc correspondientes
					deliveries.addAll(processPL(bulkDataMap, orderMap, delivery, vendor, preDeliveryDetailMap, orderAtcItemMap, atcBulksMap,
												validPddPks, pddSequenceMap, initParams.getNGuia(), initParams.getNCont(), initParams.getNImp(),
													initParams.getRequestDate(), initParams.getUserName(), initParams.getEmail(),
														keyStr[1].equals("ATC")));
				}
			}
			else {
				validPddPks = flowTypeMap.get("");
				
				// Crear los despachos con los productos/atc correspondientes
				deliveries = processPL(bulkDataMap, orderMap, null, vendor,preDeliveryDetailMap, orderAtcItemMap, atcBulksMap, validPddPks,
										pddSequenceMap, initParams.getNGuia(), initParams.getNCont(), initParams.getNImp(),
											initParams.getRequestDate(), initParams.getUserName(), initParams.getEmail(), false);
			}
			
			List<Long> deliveryIds = new ArrayList<Long>();
			for (DeliveryW delivery : deliveries) {
				deliveryIds.add(delivery.getId());
			}
			
			AddDeliveryOfOrdersAndFlowTypesDataDTO[] report = deliveryserver.getAddedDeliveriesByContainerDeliveryLocationFlowTypeAndATC(deliveryIds);
			resultDTO.setReport(report);

			// Recalcular las órdenes
			for (Map.Entry<Long, OrderW> orderSet : orderMap.entrySet()) {
				buyorderreportmanager.doCalculateTotalOfOrder(orderSet.getKey());
			}

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	private List<DeliveryW> processPL(HashMap<String, List<PreDeliveryDetailDataDTO>> bulkDataMap, HashMap<Long, OrderW> orderMap, DeliveryW delivery, VendorW vendor, HashMap<Long, HashMap<PreDeliveryDetailPK, PreDeliveryDetailW>> predeliveryDetailMap, HashMap<String, List<Long>> orderAtcItemMap, HashMap<String, Integer> atcBulksMap, List<PreDeliveryDetailPK> validDeliveryPDDs, HashMap<Long, Integer> pddSequenceMap, Long refdocument, String container, String imp, String requestdate, String username, String useremail, boolean hasatc) throws OperationFailedException, AccessDeniedException, NotFoundException, ServiceException, ParseException {
		
		// Obtener el �ltimo N°mero de bulto del proveedor
		Long bulknumber = vendor.getLastbulknumber().longValue();
		bulknumber++;

		// Iterar en los detalles válidos (unidades mayores que cero)
		HashMap<String, BoxW> bulkMap = new HashMap<String, BoxW>();
		HashMap<PreDeliveryDetailPK, PreDeliveryDetailW> pddMap = null;
		List<PreDeliveryDetailW> pddList = new ArrayList<PreDeliveryDetailW>();
		List<BoxW> boxList = new ArrayList<BoxW>();
		List<PalletW> palletList = new ArrayList<PalletW>();
		List<BulkDetailW> bulkDetailList = new ArrayList<BulkDetailW>();
		BulkDetailW bulkDetailW = null;
		DatingRequestW datingRequest = new DatingRequestW();
		BoxW box = null;
		Long bulkId = -1L;
		PreDeliveryDetailPK pddPk = null;
		for (Map.Entry<String, List<PreDeliveryDetailDataDTO>> bulkData : bulkDataMap.entrySet()) {
			// Se itera por la cantidad de bultos de este código (puede ser diferente de 1 para ATC)
			int bulkUnits = atcBulksMap.containsKey(bulkData.getKey()) ? atcBulksMap.get(bulkData.getKey()) : 1;	
			
			for (int i = 0; i < bulkUnits; i++) {
				for (PreDeliveryDetailDataDTO detail : bulkData.getValue()) {
					// Validar si existe la relación order-local-item (predeliverydata)
					pddMap = predeliveryDetailMap.get(detail.getOrderid());
					pddPk = new PreDeliveryDetailPK(detail.getOrderid(), detail.getItemid(), detail.getLocationid());
					if (!pddMap.containsKey(pddPk)) {
						throw new ServiceException("No se encontr� predistribución");
					}
					
					// Validar si el item est� incluido en este despacho
					if (!validDeliveryPDDs.contains(pddPk)) {
						continue;
					}
					
					if (bulkMap.containsKey(detail.getOrderid() + "_" + detail.getLocationid() + "_" + detail.getAtccode() + "_" + bulkId)) {
						box = bulkMap.get(detail.getOrderid() + "_" + detail.getLocationid() + "_" + detail.getAtccode() + "_" + bulkId);
					}
					else {
						// Todos caja, IRA: 03-03-2008
						// Definir bien si el tipo de bulto viene con algun codigo (cajas = CJ. POR EJ.)
						box = new BoxW();
						box.setCode(hasatc ? "ATC" + StringUtils.getInstance().addLeadingZeros(bulknumber.toString(), 8) : bulknumber.toString());
						box.setNumber(bulknumber++);
						box.setId(bulkId);
						box.setDeliveryid(0L); // Asumimos que delivery_id = 0
						box.setOrderid(detail.getOrderid());
						box.setDepartmentid(orderMap.get(detail.getOrderid()).getDepartmentid());
						box.setLocationid(detail.getLocationid());

						// Se agrega la caja al arreglo del packing list
						boxList.add(box);
						
						bulkMap.put(detail.getOrderid() + "_" + detail.getLocationid() + "_" + detail.getAtccode() + "_" + bulkId, box);
					}

					// Crear el detalle del bulto
					bulkDetailW = new BulkDetailW();
					bulkDetailW.setDeliveryid(0L);
					bulkDetailW.setOrderid(detail.getOrderid());
					bulkDetailW.setItemid(detail.getItemid());
					bulkDetailW.setLocationid(detail.getLocationid());
					bulkDetailW.setBulkid(box.getId());
					bulkDetailW.setRefdocument(refdocument == null ? null : refdocument.toString());
					bulkDetailW.setUnits(detail.getTodeliveryunits() / bulkUnits);

					// Guardamos el bulkdetail en un arraylist
					bulkDetailList.add(bulkDetailW);
					
					pddList.add(pddMap.get(pddPk));
				}
				
				bulkId--;
			}
		}
			
		if (boxList.size() == 0) {
			throw new ServiceException("No hay bultos para generar");
		}
		
		// Arreglos asociados a los bultos del packing list
		BulkDetailW[] bulkDetails = (BulkDetailW[]) bulkDetailList.toArray(new BulkDetailW[bulkDetailList.size()]);
		PalletW[] pallets = (PalletW[]) palletList.toArray(new PalletW[palletList.size()]);
		BoxW[] boxs = (BoxW[]) boxList.toArray(new BoxW[boxList.size()]);
		
		DeliveryW[] deliveries = null;
		if (delivery == null) {
			// Crear despacho base con par�metros comunes procedentes del packing list
			delivery = new DeliveryW();		
			delivery.setRefdocument(refdocument);
			delivery.setContainer(container);
			delivery.setImp(imp);
			
			// Crear solicitud de cita con par�metros procedentes del packing list	
			datingRequest = new DatingRequestW();			
			datingRequest.setRequesteddate(DateConverterFactory2.StrToDate(requestdate));
			datingRequest.setRequester(username);
			datingRequest.setEmail(useremail);
			// Se calculan despu�s
			datingRequest.setEstimatedboxes(0);
			datingRequest.setEstimatedpallets(0);
			
			OrderW[] orders = (OrderW[])orderMap.values().toArray(new OrderW[orderMap.values().size()]);
			
			// Crear nuevo despacho asociado a los productos del packing list
			deliveries = doAddDeliveriesByPackingList(orders, delivery, datingRequest, pallets, boxs, bulkDetails, hasatc, refdocument, "");	
			
			logger.info("Creados los nuevos despachos y solicitudes de cita tras la generación manual de despachos (Internacional), Contenedor " + container);
		}
		else {
			// Actualizar solicitud de cita con par�metros procedentes del packing list
			datingRequest = datingrequestserver.getByPropertyAsSingleResult("delivery.id", delivery.getId());
			
			datingRequest.setRequester(username);
			datingRequest.setEmail(useremail);
			
			PreDeliveryDetailW[] pdds = (PreDeliveryDetailW[])pddList.toArray(new PreDeliveryDetailW[pddList.size()]);
			
			doUpdateDeliveriesByPackingList(orderMap, delivery, datingRequest, pallets, boxs, bulkDetails, pdds, refdocument, "");
			deliveries = new DeliveryW[1];
			deliveries[0] = delivery;
			
			logger.info("Actualizados los despachos y solicitudes de cita tras la generación manual de despachos (Internacional), Contenedor " + container);
		}
		
		return Arrays.asList(deliveries);		
	}

	private DeliveryW doAddOrderDeliveryByDetails(List<OrderDeliveryDetailW> details, Long flowtypeid, Long deliverylocationid, Long vendorid, DeliveryTypeW deliverytype, Map<Long, Double> mapAvailableUnits, Map<Long, OrderW> ordersMap) throws ServiceException {

		DeliveryStateTypeW typedlvcreated = deliverystatetypeserver.getByPropertyAsSingleResult("code", "PREDESPACHO");

		Date now = new Date();
		// Crear un lote de despacho asociado al tipo de flujo, proveedor y tipo de orden
		DeliveryW delivery = new DeliveryW();
		delivery.setNumber(deliveryserver.getNextSequenceDeliveryNumber());
		delivery.setCreated(now);
		delivery.setHasatc(false);
		delivery.setCurrentstatetypedate(now);
		delivery.setCurrentstatetypeid(typedlvcreated.getId());
		delivery.setFlowtypeid(flowtypeid);
		delivery.setLocationid(deliverylocationid);
		delivery.setOrdertypeid(null); // puede tener OC de varios tipos
		delivery.setVendorid(vendorid);
		delivery.setDeliverytypeid(deliverytype.getId());

		DeliveryW newdelivery = deliveryserver.addDelivery(delivery);

		// Agregar el estado del lote
		DeliveryStateW delivstate = new DeliveryStateW();
		delivstate.setDeliveryid(newdelivery.getId());
		delivstate.setDeliverystatetypeid(typedlvcreated.getId());
		delivstate.setWhen(now);

		delivstate = deliverystateserver.addDeliveryState(delivstate);

		// Agregar los despachos de orden y sus detalles
		List<Long> orderidsList = new ArrayList<Long>();

		OrderW order = null;
		for (Iterator iter = details.iterator(); iter.hasNext();) {
			OrderDeliveryDetailW oddetail = (OrderDeliveryDetailW) iter.next();
			// Si el despacho de orden no existe, se agrega
			Long orderid = oddetail.getOrderid();
			order = (OrderW) ordersMap.get(oddetail.getOrderid());

			if (!orderidsList.contains(orderid)) {
				OrderDeliveryW orderdelivery = new OrderDeliveryW();
				orderdelivery.setOrderid(orderid);
				orderdelivery.setDeliveryid(newdelivery.getId());
				orderdelivery.setClosed(false);
				orderdelivery.setFlowtypeid(flowtypeid);
				orderdelivery.setDepartmentid(order.getDepartmentid());
				// Setear las unidades estimadas a despachar por oden
				if (mapAvailableUnits.containsKey(orderid)) {
					Double availableunits = mapAvailableUnits.get(orderid);
					orderdelivery.setEstimatedunits(availableunits);
					orderdelivery.setOriginalestimunits(orderdelivery.getEstimatedunits());
				} else {
					orderdelivery.setEstimatedunits(0D);
					orderdelivery.setOriginalestimunits(0D);
				}
				// Setear el deliveryindex para la orden, contando los despachos previos
				OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("order.id", orderid);
				orderdelivery.setDeliveryindex(orderdeliveries.length + 1);
				orderdeliveryserver.addOrderDelivery(orderdelivery);
				orderidsList.add(orderid);
			}
			oddetail.setDeliveryid(newdelivery.getId());
			orderdeliverydetailserver.addOrderDeliveryDetail(oddetail);
		}

		return newdelivery;
	}

	public BaseResultDTO doDeleteDelivery(DoDeleteDeliveryInitParamDTO initParams) {
		BaseResultDTO resultW = new BaseResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {

			DeliveryStateTypeW predState = deliverystatetypeserver.getByPropertyAsSingleResult("code", "PREDESPACHO");
			DeliveryStateTypeW rejectState = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_RECHAZADA");

			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());

			if (!delivery.getCurrentstatetypeid().equals(predState.getId()) && !delivery.getCurrentstatetypeid().equals(rejectState.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2300"); // El estado
				// actual no
				// permite la
				// eliminación
				// de la entrega
			}

			OrderW[] orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where odl.order = oc and oc.vendor.id = " + vendor.getId() + " and odl.delivery.id = " + initParams.getDeliveryid());

			// JPE 20190904
			// Obtener las facturas asociadas a los detalles de bultos
			Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(initParams.getDeliveryid());

			// Se borran los BulkDetails
			bulkdetailserver.deleteByProperty("id.deliveryid", initParams.getDeliveryid());
			
			// JPE 20190904
			// Eliminar las facturas asociadas a los detalles de bultos
			if (taxdocumentids != null && taxdocumentids.length > 0) {
				taxdocumentserver.deleteByIds(taxdocumentids);
			}
						
			// Se borran los Bulks
			BulkW[] bkArr = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", initParams.getDeliveryid());

			if (bkArr != null) {
				for (BulkW bk : bkArr) {
					BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bk.getId());

					if (boxArr != null && boxArr.length > 0) {
						boxserver.deleteElement(bk.getId());
					} else {
						palletserver.deleteElement(bk.getId());
					}
				}
			}

			bulkserver.flush();

			// Se borran OrderDeliveryDetails
			orderdeliverydetailserver.deleteByProperty("id.deliveryid", initParams.getDeliveryid());
			// Se borran OrderDeliveries
			orderdeliveryserver.deleteByProperty("id.deliveryid", initParams.getDeliveryid());
			// Se borran DeliveryStates
			deliverystateserver.deleteByProperty("delivery.id", initParams.getDeliveryid());
			// Se borra DatingRequest
			datingrequestserver.deleteByProperty("delivery.id", initParams.getDeliveryid());
			// Se borra el delivery
			deliveryserver.deleteElement(initParams.getDeliveryid());

			// se actualizan las cantidades
			for (int i = 0; i < orders.length; i++) {
				buyorderreportmanager.doCalculateTotalOfOrder(orders[i].getId());
			}
			return resultW;

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderW doDeleteOrderInDeliveries(Long orderid) throws LoadDataException, ServiceException {
		
		OrderW order = orderserver.getById(orderid);

		// JPE 20200908
		// Validar si la orden tiene despachos asociados
		DeliveryW[] deliveries = deliveryserver.getDeliveryByOrder(order.getId());
		if (deliveries != null && deliveries.length > 0) {
			DeliveryStateTypeW dstPredelivery = deliverystatetypeserver.getByPropertyAsSingleResult("code", "PREDESPACHO");
			DeliveryStateTypeW dstRequested = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DeliveryStateTypeW dstAssigned = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DeliveryStateTypeW dstRejected = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_RECHAZADA");
			DeliveryStateTypeW dstScheduled = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			
			// Validar si tiene algún despacho en estado 'Agendada'
			for (DeliveryW delivery : deliveries) {
				if (delivery.getCurrentstatetypeid().equals(dstScheduled.getId())) {
					throw new LoadDataException("La OC posee un despacho en estado Agendada");
				}
			}
			
			OrderDeliveryDetailW[] orderDeliveryDetails = null;
			int countDetails = 0;
			for (DeliveryW delivery : deliveries) {
				// Solamente se eliminan los despachos en estado 'Predespacho', 'Cita Solicitada', 'Cita Asignada' y 'Cita Rechazada'
				if (delivery.getCurrentstatetypeid().equals(dstPredelivery.getId()) ||
					delivery.getCurrentstatetypeid().equals(dstRequested.getId()) ||
					delivery.getCurrentstatetypeid().equals(dstAssigned.getId()) ||
					delivery.getCurrentstatetypeid().equals(dstRejected.getId())) {
					
					logger.debug("Eliminando la orden " + order.getNumber() + " del lote " + delivery.getId());

					orderDeliveryDetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", delivery.getId());
					countDetails = orderDeliveryDetails.length;
					int countInOrder = 0;
					for (OrderDeliveryDetailW orderDeliveryDetail : orderDeliveryDetails) {
						// Si el detalle corresponde a la OC, se elimina
						if (orderDeliveryDetail.getOrderid().equals(order.getId())) {
							countInOrder++;
						}
					}

					// Se revisa si quedaron detalles, ya que pudieron eliminarse todos
					// En ese caso se elimina el lote completo
					// DVI 07-02-2008 SI EL LOTE SE BORRA, EL DESPACHO DE ORDEN Y SUS DETALLES
					// SE BORRAN POR CASCADA. SI NO SE BORRA EL LOTE, SE DEBE BORRAR MANUALMENTE EL DESPACHO
					// Y SUS DETALLES DE DESPACHO SE BORRAN POR CASCADA IGUALMENTE
					if (countDetails == countInOrder) {
						logger.debug("Eliminando despacho " + delivery.getId());
						
						// SE ELIMINA ORDERDELIVERYDETAIL
						orderdeliverydetailserver.deleteByProperty("id.deliveryid", delivery.getId());

						// SE ELIMINA ORDERDELIVERY
						orderdeliveryserver.deleteByProperty("id.deliveryid", delivery.getId());

						// ELIMINA ESTADOS DE DESPACHO
						deliverystateserver.deleteByProperty("delivery.id", delivery.getId());

						if (delivery.getCurrentstatetypeid().equals(dstRequested.getId())) {
							// ELIMINA SOLICITUD
							datingrequestserver.deleteByProperty("delivery.id", delivery.getId());
						}
						else if (delivery.getCurrentstatetypeid().equals(dstAssigned.getId())) {
							// ELIMINA SOLICITUD
							datingrequestserver.deleteByProperty("delivery.id", delivery.getId());
							
							DatingW dating = datingserver.getByPropertyAsArray("delivery.id", delivery.getId())[0];
							
							// ELIMINA DETALLES DE CITA
							reservedetailserver.deleteByProperty("id.reserveid", dating.getId());

							// ELIMINA LA CITA
							datingserver.deleteElement(dating.getId());
						}

						// ELIMINA DESPACHO
						deliveryserver.deleteElement(delivery.getId());
					}
					else {
						// Eliminar orden de despacho asociado a los detalles eliminados
						logger.debug("Eliminando lote de despacho " + delivery.getId() + " orden " + order.getNumber());
						PropertyInfoDTO prop1 = new PropertyInfoDTO("id.deliveryid", "deliveryid", delivery.getId());
						PropertyInfoDTO prop2 = new PropertyInfoDTO("id.orderid", "orderid", order.getId());
						orderdeliverydetailserver.deleteByProperties(prop1, prop2);

						OrderDeliveryPK odpk = new OrderDeliveryPK(order.getId(), delivery.getId());
						
						//orderdeliveryserver.deleteIdentifiable(odpk);
						PropertyInfoDTO propodd1 = new PropertyInfoDTO("id.deliveryid", "deliveryid", odpk.getDeliveryid());
						PropertyInfoDTO propodd2 = new PropertyInfoDTO("id.orderid", "orderid", odpk.getOrderid());
						orderdeliveryserver.deleteByProperties(propodd1, propodd2 );						
					}
				}
			}
			
			deliveryserver.flush();
			
			// SE RECALCULA LA OC
			order = buyorderreportmanager.doCalculateTotalOfOrder(orderid);
		}
		
		return order;
	}

	public BaseResultDTO doUpdateOrderDeliveryAvailability(DeliveryAvailabilityInitParamDTO initParams) {

		BaseResultDTO resultDTO = new BaseResultDTO();

		List<DeliveryStateTypeW> predeliveryDeliveryStateTypeList = null;
		List<DeliveryStateTypeW> rejectedDatingDeliveryStateTypeList = null;
		List<DeliveryStateTypeW> assignedDatingDeliveryStateTypeList = null;
		List<DeliveryStateTypeW> requestedDatingDeliveryStateTypeList = null;
		List<OrderDeliveryW> orderDeliveryList = null;
		List<OrderDeliveryDetailW> orderDeliveryDetailList = null;
		Collection<OrderDeliveryW> orderDeliveryCollection = null;
		
		Map<OrderDeliveryPK, OrderDeliveryW> orderDeliveryMap = null;
		OrderDeliveryPK orderDeliveryKey = null;
		TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailDTO> orderDeliveryDetailTmpMap = new TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailDTO>();
		OrderDeliveryDetailPK orderDeliveryDetailKey = null;

		DeliveryStateTypeW predeliveryDelivery = null;
		DeliveryStateTypeW rejectedDatingDelivery = null;
		DeliveryStateTypeW requestedDatingDelivery = null;
		DeliveryStateTypeW assignedDatingDelivery = null;
		DeliveryW delivery = null;
		OrderDeliveryDetailW[] orderDeliveryDetails = null;
		OrderDeliveryW orderDelivery = null;

		Long[] ordersId = null;
		Long deliveryId = null;
		double estimatedUnits = 0;
		double sumNewUnits = 0;

		VendorW vendor = null;
		try {
			// Chequea la existencia del proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			if(vendorArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");
			}
			vendor = vendorArr[0];
			
			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getDeliveryid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DeliveryW> deliveries = deliveryserver.getByProperties(properties);

			if (deliveries == null || deliveries.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2215");
			}

			deliveryId = initParams.getDeliveryid();

			logger.info("Ajustando unidades para el Despacho " + deliveryId);

			predeliveryDeliveryStateTypeList = deliverystatetypeserver.getByProperty("name", "Predespacho");
			rejectedDatingDeliveryStateTypeList = deliverystatetypeserver.getByProperty("name", "Cita Rechazada");
			assignedDatingDeliveryStateTypeList = deliverystatetypeserver.getByProperty("name", "Cita Asignada");
			requestedDatingDeliveryStateTypeList = deliverystatetypeserver.getByProperty("name", "Cita Solicitada");

			if (predeliveryDeliveryStateTypeList.isEmpty() || rejectedDatingDeliveryStateTypeList.isEmpty() || requestedDatingDeliveryStateTypeList.isEmpty() || assignedDatingDeliveryStateTypeList.isEmpty()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}

			predeliveryDelivery = predeliveryDeliveryStateTypeList.get(0);
			rejectedDatingDelivery = rejectedDatingDeliveryStateTypeList.get(0);
			assignedDatingDelivery = assignedDatingDeliveryStateTypeList.get(0);
			requestedDatingDelivery = requestedDatingDeliveryStateTypeList.get(0);

			// Obtener el despacho
			delivery = deliveryserver.getById(deliveryId);

			if (delivery == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se pudo encontrar el Lote de Despacho " + deliveryId, false);
			}

			// Validar el estado del despacho (debe estar en predespacho o cita rechazada)
			if (!vendor.getCode().equalsIgnoreCase("IMP")) {// Si no es importado, se hacen las validaciones de estado
															// comunes
				if (delivery.getCurrentstatetypeid().longValue() != predeliveryDelivery.getId().longValue() && delivery.getCurrentstatetypeid().longValue() != rejectedDatingDelivery.getId().longValue()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200");
				}
			} else {// Si es importado, se agregan dos validaciones de estado extras
				if (delivery.getCurrentstatetypeid().longValue() != predeliveryDelivery.getId().longValue() && delivery.getCurrentstatetypeid().longValue() != rejectedDatingDelivery.getId().longValue() && delivery.getCurrentstatetypeid().longValue() != requestedDatingDelivery.getId().longValue()
						&& delivery.getCurrentstatetypeid().longValue() != assignedDatingDelivery.getId().longValue()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4207");
				}
			}

			// Obtener el despacho de orden para tener el total originalmente estimado
			orderDeliveryList = orderdeliveryserver.getByProperty("delivery.id", deliveryId);
			if (orderDeliveryList.isEmpty()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se pudo encontrar Despacho de Orden (Lote " + deliveryId + ")", false);
			}

			// Inicializar las unidades estimadas del despacho de orden
			orderDeliveryMap = new TreeMap<OrderDeliveryPK, OrderDeliveryW>();
			for (Iterator iterator = orderDeliveryList.iterator(); iterator.hasNext();) {
				OrderDeliveryW od = (OrderDeliveryW) iterator.next();
				orderDeliveryKey = new OrderDeliveryPK(od.getOrderid(), deliveryId);
				od.setEstimatedunits(0.0); // para volver a recalcular con las unidades nuevas
				orderDeliveryMap.put(orderDeliveryKey, od);
			}

			// Obtener todos los detalles actuales del despacho de orden
			orderDeliveryDetailList = orderdeliverydetailserver.getByProperty("orderdelivery.delivery.id", deliveryId);
			if (orderDeliveryDetailList.isEmpty()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se pudo encontrar Detalle de Despacho (Lote " + deliveryId + ")", false);
			}

			orderDeliveryDetails = (OrderDeliveryDetailW[]) orderDeliveryDetailList.toArray(new OrderDeliveryDetailW[orderDeliveryDetailList.size()]);

			// Guardar en mapa los detalles actuales del despacho de orden para su b�squeda
			TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW> orderDeliveryDetailMap = new TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW>();
			List<Long> orderList = new ArrayList<Long>();
			for (OrderDeliveryDetailW odd : orderDeliveryDetails) {
				orderDeliveryDetailKey = new OrderDeliveryDetailPK();
				orderDeliveryDetailKey.setDeliveryid(odd.getDeliveryid());
				orderDeliveryDetailKey.setItemid(odd.getItemid());
				orderDeliveryDetailKey.setLocationid(odd.getLocationid());
				orderDeliveryDetailKey.setOrderid(odd.getOrderid());
				orderDeliveryDetailMap.put(orderDeliveryDetailKey, odd);

				// Llenar la lista de órdenes del despacho para poder actualizar posteriormente sus cantidades
				if (!orderList.contains(odd.getOrderid())) {
					orderList.add(odd.getOrderid());
				}
			}
			
			// JPE 20190424
			HashMap<Long, List<Long>> atcItemMap = new HashMap<Long, List<Long>>();
			HashMap<String, Integer> atcItemCurveMap = new HashMap<String, Integer>();
			List<Long> itemList = null;
			if (!vendor.getDomestic() && delivery.getHasatc()) {
				// Obtener el mapa ATC-productos del despacho
				List<ItemAtcDTO> itemAtcs = itematcserver.getItemDataByDelivery(initParams.getDeliveryid());
				for (ItemAtcDTO itemAtc : itemAtcs) {
					if (atcItemMap.containsKey(itemAtc.getAtcid())) {
						itemList = atcItemMap.get(itemAtc.getAtcid());
					}
					else {
						itemList = new ArrayList<Long>();
					}
					itemList.add(itemAtc.getItemid());
					atcItemMap.put(itemAtc.getAtcid(), itemList);
					
					atcItemCurveMap.put(itemAtc.getAtcid() + "_" + itemAtc.getItemid(), itemAtc.getCurve().intValue());
				}
			}
			
			// Obtener los detalles a modificar del despacho de orden a partir del par�metro de entrada
			HashMap<String, HashMap<Long, Double>> atcItemAvalaibilitiesMap = new HashMap<String, HashMap<Long, Double>>();
			HashMap<Long, Double> itemUnitsMap = null;
			HashMap<String, List<BulkDetailDataDTO>> bulkDetailDataMap = new HashMap<String, List<BulkDetailDataDTO>>();
			List<BulkDetailDataDTO> bulkDetailList = null;
			OrderDeliveryDetailDTO orderDeliveryDetailDTO = null;
			BulkDetailDataDTO bulkDetailData = null;
			String key = "";
			for (DeliveryAvailabilityDTO deliveryAvalaibilityDTO : initParams.getDeliveryavailabilities()) {
				orderDeliveryDetailDTO = getOrderDeliveryDetailByCodes(deliveryAvalaibilityDTO.getOrdernumber(), deliveryAvalaibilityDTO.getParissku(), deliveryAvalaibilityDTO.getLocalcode(), deliveryAvalaibilityDTO.getAtccode(), deliveryAvalaibilityDTO.getDeliveryid()).getOrderdeliverydetail();
				orderDeliveryDetailDTO.setAvailableunits(deliveryAvalaibilityDTO.getAvailableunits());

				// EBO 02-10-2014
				// PONGO LOS DETALLES QUE CAMBIARON EN UN MAPA
				orderDeliveryDetailKey = new OrderDeliveryDetailPK();
				orderDeliveryDetailKey.setDeliveryid(orderDeliveryDetailDTO.getDeliveryid());
				orderDeliveryDetailKey.setItemid(orderDeliveryDetailDTO.getItemid());
				orderDeliveryDetailKey.setLocationid(orderDeliveryDetailDTO.getLocationid());
				orderDeliveryDetailKey.setOrderid(orderDeliveryDetailDTO.getOrderid());
				orderDeliveryDetailTmpMap.put(orderDeliveryDetailKey, orderDeliveryDetailDTO);
				
				// JPE 20190424
				// Determinar los detalles de bulto a generar si es proveedor internacional
				if (!vendor.getDomestic() && deliveryAvalaibilityDTO.getAvailableunits() > 0) {
					bulkDetailData = new BulkDetailDataDTO();
					bulkDetailData.setOrderid(orderDeliveryDetailDTO.getOrderid());
					bulkDetailData.setDeliveryid(orderDeliveryDetailDTO.getDeliveryid());
					bulkDetailData.setItemid(orderDeliveryDetailDTO.getItemid());
					bulkDetailData.setLocationid(orderDeliveryDetailDTO.getLocationid());
					bulkDetailData.setAtcid(orderDeliveryDetailDTO.getAtcid());
					bulkDetailData.setDepartmentid(orderDeliveryDetailDTO.getDepartmentid());
					bulkDetailData.setFlowtypeid(orderDeliveryDetailDTO.getFlowtypeid());
					bulkDetailData.setBulks(1); // puede cambiar si es atc
					bulkDetailData.setUnits(deliveryAvalaibilityDTO.getAvailableunits());
					
					// Llenar las listas de productos a modificar por ATC (donde aplique)
					if (delivery.getHasatc()) {
						key = orderDeliveryDetailDTO.getOrderid() + "_" + orderDeliveryDetailDTO.getLocationid() + "_" + orderDeliveryDetailDTO.getAtcid();
						if (bulkDetailDataMap.containsKey(key)) {
							bulkDetailList = bulkDetailDataMap.get(key);
						}
						else {
							bulkDetailList = new ArrayList<BulkDetailDataDTO>();
						}
						
						if (atcItemAvalaibilitiesMap.containsKey(key)) {
							itemUnitsMap = atcItemAvalaibilitiesMap.get(key);
						}
						else {
							itemUnitsMap = new HashMap<Long, Double>();
						}
						itemUnitsMap.put(orderDeliveryDetailDTO.getItemid(), orderDeliveryDetailDTO.getAvailableunits());
						atcItemAvalaibilitiesMap.put(key, itemUnitsMap);
					}
					else {
						key = orderDeliveryDetailDTO.getOrderid() + "_" + orderDeliveryDetailDTO.getLocationid() + "_" + orderDeliveryDetailDTO.getItemid();
						if (bulkDetailDataMap.containsKey(key)) {
							bulkDetailList = bulkDetailDataMap.get(key);
						}
						else {
							bulkDetailList = new ArrayList<BulkDetailDataDTO>();
						}
					}

					bulkDetailList.add(bulkDetailData);
					bulkDetailDataMap.put(key, bulkDetailList);
				}
			}
			
			// JPE 20190424
			if (!vendor.getDomestic()) {
				if (delivery.getHasatc()) {
					Double multiplier = null;
					for (Map.Entry<String, HashMap<Long, Double>> atc : atcItemAvalaibilitiesMap.entrySet()) {
						// Valida que se hayan indicado todos los productos que componen cada ATC a modificar
						key = atc.getKey().split("_")[2]; // atcid
						itemUnitsMap = atc.getValue();
						if (!itemUnitsMap.keySet().containsAll(atcItemMap.get(Long.valueOf(key)))) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
											"Debe informar cantidades mayores a cero para todos los productos de los ATC modificados", false);
						}
						
						multiplier = null;
						for (Map.Entry<Long, Double> itemdata : itemUnitsMap.entrySet()) {
							if (multiplier == null) {
								// Validar que las unidades indicadas est�n en la proporción correcta de curvas
								if (itemdata.getValue() % atcItemCurveMap.get(key + "_" + itemdata.getKey()) != 0) {
									return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
											"Existen cantidades a entregar no proporcionales con los valores de curva ATC de los productos", false);
								}
								else {
									multiplier = itemdata.getValue() / atcItemCurveMap.get(key + "_" + itemdata.getKey());
								}							
							}
							// Validar que las unidades indicadas corresponden a curvas completas
							else if (itemdata.getValue() / atcItemCurveMap.get(key + "_" + itemdata.getKey()) != multiplier) {
								return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
										"Existen cantidades a entregar para los productos de ATC que no conforman curvas completas", false);
							}
						}
						
						// Actualizar el N°mero de bultos para este ATC
						for (BulkDetailDataDTO bdd : bulkDetailDataMap.get(atc.getKey())) {
							bdd.setBulks(multiplier.intValue());
						}
					}
				}
				
				// Obtener el N°mero de documento de referencia
				String refdocument = bulkdetailserver.getRefDocumentByDeliveryId(initParams.getDeliveryid());
				
				// JPE 20190904
				// Obtener las facturas asociadas a los detalles de bultos
				Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(initParams.getDeliveryid());

				// Eliminar los bultos originales del despacho
				bulkdetailserver.deleteByProperty("id.deliveryid", initParams.getDeliveryid());
				
				// JPE 20190904
				// Eliminar las facturas asociadas a los detalles de bultos
				if (taxdocumentids != null && taxdocumentids.length > 0) {
					taxdocumentserver.deleteByIds(taxdocumentids);
				}
								
				PropertyInfoDTO property1 = new PropertyInfoDTO("orderdelivery.delivery.id", "delivery", initParams.getDeliveryid());
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
				
				bulkserver.deleteByProperty("orderdelivery.delivery.id", initParams.getDeliveryid());	
				
				// Obtener el �ltimo N°mero de bulto del proveedor
				Long bulkNumber = new Long(vendor.getLastbulknumber() == null ? 0 : vendor.getLastbulknumber());
								
				BoxW bulk = null;
				BulkDetailW bulkDetail = null;
				for (Map.Entry<String, List<BulkDetailDataDTO>> atc : bulkDetailDataMap.entrySet()) {
					bulkDetailList = atc.getValue();
					
					for (int i = 0; i < bulkDetailList.get(0).getBulks(); i++) {
						bulkNumber++;
						
						bulk = new BoxW();
						bulk.setCode(delivery.getHasatc() ? "ATC" + StringUtils.getInstance().addLeadingZeros(bulkNumber.toString(), 8) : bulkNumber.toString());
						bulk.setNumber(bulkNumber);
						bulk.setOrderid(bulkDetailList.get(0).getOrderid());
						bulk.setDeliveryid(bulkDetailList.get(0).getDeliveryid());
						bulk.setLocationid(bulkDetailList.get(0).getLocationid());
						bulk.setDepartmentid(bulkDetailList.get(0).getDepartmentid());
						bulk.setFlowtypeid(bulkDetailList.get(0).getFlowtypeid());
						
						bulk = boxserver.addBox(bulk); // se crear�n todos como CAJAS en esta instancia
						
						for (BulkDetailDataDTO bdd : bulkDetailList) {
							bulkDetail = new BulkDetailW();
							bulkDetail.setOrderid(bdd.getOrderid());
							bulkDetail.setDeliveryid(bdd.getDeliveryid());
							bulkDetail.setLocationid(bdd.getLocationid());
							bulkDetail.setBulkid(bulk.getId());
							bulkDetail.setItemid(bdd.getItemid());						
							bulkDetail.setRefdocument(refdocument);
							bulkDetail.setUnits(bdd.getUnits() / bdd.getBulks());
							
							bulkdetailserver.addBulkDetail(bulkDetail);
						}
					}
				}
				
				if (vendor.getLastbulknumber() == null || (bulkNumber > vendor.getLastbulknumber())) {
					vendor.setLastbulknumber(bulkNumber.intValue());

					vendor = vendorserver.updateVendor(vendor);
				}
			}
			
			// EBO 02-10-2014
			// SE DEBE ITERAR POR EL TOTAL DE DETALLES PARA SUMARIZAR LA NUEVA CANTIDAD ESTIMADA
			for (OrderDeliveryDetailW odd : orderDeliveryDetails) {
				orderDeliveryDetailKey = new OrderDeliveryDetailPK();
				orderDeliveryDetailKey.setDeliveryid(odd.getDeliveryid());
				orderDeliveryDetailKey.setItemid(odd.getItemid());
				orderDeliveryDetailKey.setLocationid(odd.getLocationid());
				orderDeliveryDetailKey.setOrderid(odd.getOrderid());

				// VALIDA QUE EXISTA EN EL MAPA DE MODIFICADOS
				// SINO, SUMARIZO LAS CANTIDADES ACTUALES
				if (!orderDeliveryDetailTmpMap.containsKey(orderDeliveryDetailKey)) {
					// Descontar las unidades originalmente estimadas
					orderDeliveryKey = new OrderDeliveryPK(odd.getOrderid(), odd.getDeliveryid());

					if (!orderDeliveryMap.containsKey(orderDeliveryKey)) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4205");
					}

					orderDelivery = orderDeliveryMap.get(orderDeliveryKey);
					estimatedUnits = orderDelivery.getEstimatedunits() + odd.getAvailableunits();
					orderDelivery.setEstimatedunits(estimatedUnits);
					orderDeliveryMap.put(orderDeliveryKey, orderDelivery);
					sumNewUnits += odd.getAvailableunits();
				}
				// SI LO CONTIENE VALIDO LAS NUEVAS UNIDADES CONTRA LAS ACTUALES
				else {
					orderDeliveryDetailDTO = orderDeliveryDetailTmpMap.get(orderDeliveryDetailKey);

					// Las nuevas unidades deben ser mayores o iguales a cero
					if (orderDeliveryDetailDTO.getAvailableunits() < 0) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4203");
					}

					// Las nuevas unidades deben ser menores o iguales a las actuales
					if (odd.getPendingunits().doubleValue() < orderDeliveryDetailDTO.getAvailableunits()) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4204");
					}
					
					// Descontar las unidades originalmente estimadas
					orderDeliveryKey = new OrderDeliveryPK(odd.getOrderid(), odd.getDeliveryid());

					if (!orderDeliveryMap.containsKey(orderDeliveryKey)) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4205");
					}

					orderDelivery = orderDeliveryMap.get(orderDeliveryKey);
					estimatedUnits = orderDelivery.getEstimatedunits() + orderDeliveryDetailDTO.getAvailableunits();
					orderDelivery.setEstimatedunits(estimatedUnits);
					orderDeliveryMap.put(orderDeliveryKey, orderDelivery);
					sumNewUnits += orderDeliveryDetailDTO.getAvailableunits();

					if (odd.getAvailableunits().doubleValue() == orderDeliveryDetailDTO.getAvailableunits()) {
						continue;
					}
					
					// Ajustar las cantidades en el detalle de despacho de orden
					odd.setAvailableunits(orderDeliveryDetailDTO.getAvailableunits());
					
					orderdeliverydetailserver.updateOrderDeliveryDetail(odd);
				}
			}

			if (sumNewUnits == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4206");
			}
			
			// Actualizar los despachos de órdenes
			orderDeliveryCollection = orderDeliveryMap.values();
			for (Iterator iterator = orderDeliveryCollection.iterator(); iterator.hasNext();) {
				OrderDeliveryW od = (OrderDeliveryW) iterator.next();
				orderdeliveryserver.updateOrderDelivery(od);
			}

			// Actualizar las cantidades a nivel de órdenes
			ordersId = orderList.toArray(new Long[orderList.size()]);
			orderdeliveryserver.flush();

			for (Long orderid : ordersId) {
				buyorderreportmanager.doCalculateTotalOfOrder(orderid);
			}

			logger.info("Se han ajustado las cantidades para el Despacho " + deliveryId);

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	@TransactionTimeout(value = 600)
	public BaseResultsDTO doUploadOrderDeliveryAvailability(UploadDeliveryAvailabilityInitParamDTO initParamDTO) {

		BaseResultsDTO resultDTO = new BaseResultsDTO();

		// Almacena los mensajes de error que se vayan produciendo
		List<BaseResultDTO> baseResultList = new ArrayList<BaseResultDTO>();

		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Chequea la existencia del proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}

			// Chequea la existencia del despacho para ese proveedor
			DeliveryW delivery = deliveryserver.getById(initParamDTO.getDeliveryId(), LockModeType.PESSIMISTIC_WRITE);

			if (delivery.getVendorid().longValue() != vendor.getId().longValue()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4401");
			}

			DeliveryStateTypeW dstPredelivery = deliverystatetypeserver.getByProperty("code", "PREDESPACHO").get(0);
			DeliveryStateTypeW dstRejected = deliverystatetypeserver.getByProperty("code", "CITA_RECHAZADA").get(0);
			DeliveryStateTypeW dstAssigned = deliverystatetypeserver.getByProperty("code", "CITA_ASIGNADA").get(0);
			DeliveryStateTypeW dstRequested = deliverystatetypeserver.getByProperty("code", "CITA_SOLICITADA").get(0);

			if (vendor.getCode().equals("IMP")) {
				// Si es 'Cencosud Importado' se permiten dos estados adicionales
				if (dstPredelivery.getId().longValue() != delivery.getCurrentstatetypeid().longValue() &&
						dstRejected.getId().longValue() != delivery.getCurrentstatetypeid().longValue() &&
							dstAssigned.getId().longValue() != delivery.getCurrentstatetypeid().longValue() &&
								dstRequested.getId().longValue() != delivery.getCurrentstatetypeid().longValue()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4404");
				}
			}
			else {
				// De lo contrario se hacen las validaciones de estado comunes
				if (dstPredelivery.getId().longValue() != delivery.getCurrentstatetypeid().longValue() &&
						dstRejected.getId().longValue() != delivery.getCurrentstatetypeid().longValue()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4402");
				}
			}
			
			String csvFilename = initParamDTO.getFileName();
			String error = "";
			int total = 0;
			UploadErrorDTO[] errorArr = null;

			// Crea tabla Temporal para guardar ajuste
			orderdeliverydetailserver.doCreateTempTableAdjustDelivery();

			// Crea tabla temporal para guardar Errores
			orderdeliverydetailserver.doCreateTempTableError();

			// Carga del archivo CSV
			try {
				total = orderdeliverydetailserver.doLoadCSV(csvFilename);

			} catch (Exception e) {
				error = "Error procesando el archivo de ajuste de despacho";
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4400", error, false));
				resultDTO.setBaseresults(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4403");
			}

			// No hay registros, ocurrió un error procesando el archivo
			if (total < 0) {
				error = "Error al cargar el archivo de ajuste de despacho";
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4400", error, false));
				resultDTO.setBaseresults(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4403");
			}

			// Valida que no existan l�neas repetidas
			orderdeliverydetailserver.doCheckUniqueRows();

			// Si hay errores detiene procedimiento y devuelve errores
			errorArr = orderdeliverydetailserver.getErrorsOfAdjustDelivery();
			if (errorArr.length > 0) {
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorArr.length];
				for (int i = 0; i < errorArr.length; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L4400");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setBaseresults(curResultArr);
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4403");
			}

			// Carga IDs
			orderdeliverydetailserver.doFillData(initParamDTO.getDeliveryId());
			
			// Valida que la combinación OC-SKU-Local-ATC/SKU pertenezca al despacho
			orderdeliverydetailserver.doCheckValidOrderItemLocation();
			
			// Valida que las nuevas unidades sean menores o iguales a las originalmente comprometidas para el detalle
			// del despacho correspondiente
			orderdeliverydetailserver.doCheckOrderDeliveryAdjustUnits();

			// JPE 20190429
			// Valida si es un proveedor nacional
			if (vendor.getDomestic()) {
				// Valida que las nuevas unidades sean menores o iguales a lo pendiente en la predistribución
				// correspondiente, considerando aquellos despachos en curso o ya entregados
				orderdeliverydetailserver.doCheckPredeliveryAdjustUnits(initParamDTO.getDeliveryId());
			}
			// JPE 20190412
			// Valida si es un proveedor internacional y tiene ATC
			else if (delivery.getHasatc()) {
				// Estas validaciones se realizar�n sobre los detalles con unidades a despachar mayores a cero 
				
				// Valida que se hayan indicado todos los productos que componen cada ATC
				orderdeliverydetailserver.doCheckATCItems();
				
				// Valida que las unidades indicadas est�n en la proporción correcta de curvas
				orderdeliverydetailserver.doCheckProportionalATCCurves();
				
				// Valida que las unidades indicadas para los productos de un ATC corresponden a curvas completas
				orderdeliverydetailserver.doCheckCompleteATCCurves();
			}
						
			// Si hay errores detiene procedimiento y devuelve errores
			errorArr = orderdeliverydetailserver.getErrorsOfAdjustDelivery();
			if (errorArr.length > 0) {
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorArr.length];
				for (int i = 0; i < errorArr.length; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L4400");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setBaseresults(curResultArr);
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4403");
			}
			
			// JPE 20190412
			// Si es un proveedor internacional
			if (!vendor.getDomestic()) {
				// Obtener el N°mero de documento de referencia
				String refdocument = bulkdetailserver.getRefDocumentByDeliveryId(initParamDTO.getDeliveryId());
				
				// JPE 20190904
				// Obtener las facturas asociadas a los detalles de bultos
				Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(initParamDTO.getDeliveryId());

				// Eliminar los bultos originales del despacho
				bulkdetailserver.deleteByProperty("id.deliveryid", initParamDTO.getDeliveryId());

				// JPE 20190904
				// Eliminar las facturas asociadas a los detalles de bultos
				if (taxdocumentids != null && taxdocumentids.length > 0) {
					taxdocumentserver.deleteByIds(taxdocumentids);
				}
				
				PropertyInfoDTO property1 = new PropertyInfoDTO("orderdelivery.delivery.id", "delivery", initParamDTO.getDeliveryId());
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
				
				bulkserver.deleteByProperty("orderdelivery.delivery.id", initParamDTO.getDeliveryId());	
				
				// Obtener el �ltimo N°mero de bulto del proveedor
				Long bulkNumber = new Long(vendor.getLastbulknumber() == null ? 0 : vendor.getLastbulknumber());
								
				// Crear nuevos bultos para el despacho (ATC/SKU)
				BulkDetailDataDTO[] bulkDetailData = orderdeliverydetailserver.getBulkDetailData();
				
				HashMap<String, List<BulkDetailDataDTO>> atcMap = new HashMap<String, List<BulkDetailDataDTO>>();
				List<BulkDetailDataDTO> atcList = null;
				String key = "";
				for (BulkDetailDataDTO bdd : bulkDetailData) {
					key = bdd.getOrderid() + "_" + bdd.getLocationid() + "_" + bdd.getAtcid();
					if (atcMap.containsKey(key)) {
						atcList = atcMap.get(key);
					}
					else {
						atcList = new ArrayList<BulkDetailDataDTO>();
					}
					atcList.add(bdd);
					atcMap.put(key, atcList);						
				}
				
				BoxW bulk = null;
				BulkDetailW bulkDetail = null;
				for (Map.Entry<String, List<BulkDetailDataDTO>> atc : atcMap.entrySet()) {
					atcList = atc.getValue();
					
					for (int i = 0; i < atcList.get(0).getBulks(); i++) {
						bulkNumber++;
						
						bulk = new BoxW();
						bulk.setCode(delivery.getHasatc() ? "ATC" + StringUtils.getInstance().addLeadingZeros(bulkNumber.toString(), 8) : bulkNumber.toString());
						bulk.setNumber(bulkNumber);
						bulk.setOrderid(atcList.get(0).getOrderid());
						bulk.setDeliveryid(atcList.get(0).getDeliveryid());
						bulk.setLocationid(atcList.get(0).getLocationid());
						bulk.setDepartmentid(atcList.get(0).getDepartmentid());
						bulk.setFlowtypeid(atcList.get(0).getFlowtypeid());
						
						bulk = boxserver.addBox(bulk); // se crear�n todos como CAJAS en esta instancia
						
						for (BulkDetailDataDTO bdd : atcList) {
							bulkDetail = new BulkDetailW();
							bulkDetail.setOrderid(bdd.getOrderid());
							bulkDetail.setDeliveryid(bdd.getDeliveryid());
							bulkDetail.setLocationid(bdd.getLocationid());
							bulkDetail.setBulkid(bulk.getId());
							bulkDetail.setItemid(bdd.getItemid());						
							bulkDetail.setRefdocument(refdocument);
							bulkDetail.setUnits(bdd.getUnits() / bdd.getBulks());
							
							bulkdetailserver.addBulkDetail(bulkDetail);
						}
					}
				}
				
				if (vendor.getLastbulknumber() == null || (bulkNumber > vendor.getLastbulknumber())) {
					vendor.setLastbulknumber(bulkNumber.intValue());

					vendor = vendorserver.updateVendor(vendor);
				}
			}

			// Actualiza las unidades disponibles del detalle de despacho
			orderdeliverydetailserver.doAdjustOrderDeliveryDetailUnits();
			orderdeliverydetailserver.flush();

			// Actualiza las unidades estimadas por orden
			orderdeliverydetailserver.doAdjustOrderDeliveryEstimatedUnits();
			orderdeliveryserver.flush();

			// Re-calcula las unidades y montos de las OC asociadas al despacho
			Long[] orderids = orderdeliverydetailserver.getOrderIdsFromAdjustDelivery();
			for (Long orderid : orderids) {
				buyorderreportmanager.doCalculateTotalOfOrder(orderid);
			}

			logger.info("Se han ajustado las unidades de la Cita para el Despacho " + delivery.getNumber());

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public UploadPackingListResultDTO doUploadPackingList(UploadPackingListInitParamDTO initParams) {
		UploadPackingListResultDTO result = new UploadPackingListResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
			String error = "";

			int maxRowsPL = RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS_PACKINGLIST();
			if (initParams.getPackinglist().length > maxRowsPL) {
				error = "Archivo supera las " + maxRowsPL + " l�neas autorizadas";
				baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
			}
			
			boolean paperlessValidation = Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("paperlessvalidation_pl"));

			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);

			List<Long> orderIds = new ArrayList<Long>();
			List<PackingListDataDTO> newPLList = null;

			HashMap<Long, OrderW> ocMap = new HashMap<Long, OrderW>();
			Map<String, LocationW> loMap = new HashMap<String, LocationW>();
			Map<String, ItemW> itMap = new HashMap<String, ItemW>();

			Map<Long, ItemW> itemMap = new HashMap<Long, ItemW>();
			Map<Long, OrderStateTypeW> osTypeMap = new HashMap<Long, OrderStateTypeW>();
			Map<Long, LocationW> destLocalMap = new HashMap<Long, LocationW>();
			Map<Long, Double> ocPLUnitsMap = new HashMap<Long, Double>();
			Map<Long, Integer> itInnerpackMap = new HashMap<Long, Integer>();
			Map<String, Long> lpnOcMap = new HashMap<String, Long>();
			Map<String, Long> lpnDpMap = new HashMap<String, Long>();
			Map<String, Long> lpnDestLoMap = new HashMap<String, Long>();
			Map<String, Long> lpnFlowMap = new HashMap<String, Long>();
			Map<String, Long> lpnItMap = new HashMap<String, Long>();
			Map<Long, Long> tdMap = new HashMap<Long, Long>();
			Map<String, Double> tdOcMap = new HashMap<String, Double>();
			
			Map<String, BoxW> boxMap = new HashMap<String, BoxW>();
			Map<String, PalletW> palletMap = new HashMap<String, PalletW>();
			Map<String, Boolean> lpnTypeMap = new HashMap<String, Boolean>(); // True: Box - False: Pallet
			Map<String, List<PackingListDataDTO>> plMap = new HashMap<String, List<PackingListDataDTO>>();

			Map<VendorLocationId, Integer> actualCorrMap = new TreeMap<VendorLocationId, Integer>();
			Map<VendorLocationId, Integer> lastCorrMap = new TreeMap<VendorLocationId, Integer>();
			Map<String, List<Integer>> ocLoItCorrList = new HashMap<String, List<Integer>>();

			TreeMap<OrderDetailPK, OrderDetailW> odMap = new TreeMap<OrderDetailPK, OrderDetailW>();
			TreeMap<OrderDeliveryPK, OrderDeliveryW> odlMap = new TreeMap<OrderDeliveryPK, OrderDeliveryW>();
			TreeMap<OrderDeliveryPK, Boolean> odlUsed = new TreeMap<OrderDeliveryPK, Boolean>();
			TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW> oddMap = new TreeMap<OrderDeliveryDetailPK, OrderDeliveryDetailW>();
			TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW> pddMap = new TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
			TreeMap<PreDeliveryDetailPK, Double> pddPLMap = new TreeMap<PreDeliveryDetailPK, Double>();
			TreeMap<OrderDetailPK, Double> ocItPLUnitsMap = new TreeMap<OrderDetailPK, Double>();
			TreeMap<OrderDeliveryDetailPK, Double> itemUnits = new TreeMap<OrderDeliveryDetailPK, Double>();

			String lpn = "";

			int packingListAmount = 0;
			double packingListTotalUnits = 0.0;

			boolean ocExists = false;
			boolean itemExists = false;
			boolean locExists = false;
			boolean stockDL = false;
			boolean vevDL = false;
			boolean lpnOK = true;

			PackingListDataDTO packingData = null;
			PackingListDataDTO[] packingListArr = initParams.getPackinglist();

			// ESTADO 'CITA ASIGNADA'
			DeliveryStateTypeW citAsigSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");

			// TIPO DESPACHO 'STOCK'
			DeliveryTypeW stockType = deliverytypeserver.getByPropertyAsSingleResult("code", "STOCK");

			// TIPO DESPACHO 'VeV'
			DeliveryTypeW vevType = deliverytypeserver.getByPropertyAsSingleResult("code", "VEVCD");

			// DESPACHO
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);

			if (delivery.getDeliverytypeid().equals(stockType.getId()))
				stockDL = true;
			else if (delivery.getDeliverytypeid().equals(vevType.getId()))
				vevDL = true;

			// PROVEEDOR
			//VendorW vendor = vendorserver.getById(vendor.getId());

			// LOCAL DE DESTINO
			LocationW destlocation = null;
			// ORDEN
			OrderW order = null;
			// ITEM
			ItemW item = null;

			// VALIDA QUE EL DESPACHO SE ENCUENTRE EN CITA ASIGNADA
			if (!delivery.getCurrentstatetypeid().equals(citAsigSt.getId())) {
				error = "El estado del despacho debe ser 'Cita Asignada' para poder realizar esta operación";
				baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
			}

			// ORDER-DELIVERY RELACIONADAS AL DESPACHO
			List<OrderDeliveryW> odlArr = orderdeliveryserver.getByQuery("select odl from OrderDelivery as odl where odl.delivery.id = " + delivery.getId() + " and odl.closed = FALSE");
			HashMap<Long, OrderDeliveryW> asnodMap = new HashMap<Long, OrderDeliveryW>();
			OrderDeliveryPK odlPK = null;
			for (OrderDeliveryW odl : odlArr) {
				odlPK = new OrderDeliveryPK(odl.getOrderid(), odl.getDeliveryid());
				if (!odlMap.containsKey(odlPK)) {
					odlMap.put(odlPK, odl);
					odlUsed.put(odlPK, false);
				}
				
				asnodMap.put(odl.getOrderid(), odl);
			}
			
			// DETALLES DE DESPACHO
			OrderDeliveryDetailW[] oddArray = orderdeliverydetailserver.getByPropertyAsArray("orderdelivery.delivery.id", delivery.getId());

			for (OrderDeliveryDetailW odd : oddArray) {
				OrderDeliveryDetailPK key = new OrderDeliveryDetailPK(odd.getOrderid(), odd.getDeliveryid(), odd.getItemid(), odd.getLocationid());
				if (!oddMap.containsKey(key)) {
					oddMap.put(key, odd);
				}
			}

			// ESTADOS DE ORDEN
			OrderStateTypeW[] osTypeArr = orderstatetypeserver.getAllAsArray();
			for (OrderStateTypeW osType : osTypeArr) {
				if (!osTypeMap.containsKey(osType.getId())) {
					osTypeMap.put(osType.getId(), osType);
				}
			}

			// VALIDA QUE HAYA INGRESADO UN N°MERO DE COMPLEMENTO MAYOR O IGUAL A CERO
			if (initParams.getComplementnumber() < 0) {
				baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "Debe ingresar un N°mero de complemento mayor o igual a cero", false));
			}

			TaxDocumentW[] taxdocuments = null;
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			for (int i = 0; i < packingListArr.length; i++) {
				ocExists = false;
				itemExists = false;
				locExists = false;
				lpnOK = true;

				packingData = packingListArr[i];

				if (packingData.isBulk())
					lpn = packingData.getBulknumber().trim();
				else
					lpn = packingData.getPalletnumber().trim();

				// VALIDA QUE LA UNIDADES SEAN MAYORES O IGUALES A CERO
				if (packingData.getUnits() <= 0) {
					error = "Fila " + packingData.getRownumber() + ": No se pueden cargar cantidades en valor " + packingData.getUnits();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				// SE VERIFICA QUE OC, LOCALES Y PRODUCTOS
				// ORDEN
				if (!ocMap.containsKey(packingData.getOrdernumber())) {
					OrderW[] orderArr = orderserver.getByPropertyAsArray("number", packingData.getOrdernumber());
					if (orderArr == null || orderArr.length == 0) {
						ocExists = false;
						ocMap.put(packingData.getOrdernumber(), null);
						error = "Fila " + packingData.getRownumber() + ": La Orden N°mero " + packingData.getOrdernumber() + " no existe";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						ocMap.put(packingData.getOrdernumber(), orderArr[0]);
						ocExists = true;
						packingData.setOrderid(orderArr[0].getId());

						order = orderArr[0];

						if (!orderIds.contains(orderArr[0].getId())) {
							orderIds.add(orderArr[0].getId());

							// VALIDA QUE LA OC ESTE EN UN ESTADO VIGENTE
							if (!osTypeMap.get(orderArr[0].getCurrentstatetypeid()).getValid()) {
								error = "Fila " + packingData.getRownumber() + ": La O/C N° " + packingData.getOrdernumber() + " no se encuentra vigente. El estado actual es " + osTypeMap.get(orderArr[0].getCurrentstatetypeid()).getName();
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}

							// VALIDA QUE LA ORDEN PERTENEZCA AL DESPACHO
							OrderDeliveryPK odlKey = new OrderDeliveryPK(orderArr[0].getId(), delivery.getId());
							if (!odlMap.containsKey(odlKey)) {
								error = "Fila " + packingData.getRownumber() + ": La O/C N° " + packingData.getOrdernumber() + " no pertenece al despacho.";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}

							// LLENA MAPA ORDERDETAIL
							OrderDetailW[] odArr = orderdetailserver.getByPropertyAsArray("id.orderid", orderArr[0].getId());
							OrderDetailPK ocdPk = null;
							for (OrderDetailW od : odArr) {
								ocdPk = new OrderDetailPK(od.getOrderid(), od.getItemid());
								if (!odMap.containsKey(ocdPk)) {
									odMap.put(ocdPk, od);
								}
							}

							// LLENA MAPA DE PREDISTRIBUCION
							PreDeliveryDetailW[] pddArr = predeliverydetailserver.getByPropertyAsArray("id.orderid", orderArr[0].getId());
							PreDeliveryDetailPK pddPK = null;
							for (PreDeliveryDetailW pdd : pddArr) {
								pddPK = new PreDeliveryDetailPK(pdd.getOrderid(), pdd.getItemid(), pdd.getLocationid());
								if (!pddMap.containsKey(pddPK)) {
									pddMap.put(pddPK, pdd);
								}
							}
						}
					}
				} else {
					if (ocMap.get(packingData.getOrdernumber()) == null) {
						ocExists = false;
						error = "Fila " + packingData.getRownumber() + ": La Orden N°mero " + packingData.getOrdernumber() + " no existe";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						ocExists = true;
						order = ocMap.get(packingData.getOrdernumber());
						packingData.setOrderid(order.getId());
					}
				}

				// ITEM
				if (!itMap.containsKey(packingData.getItemsku())) {
					ItemW[] itemArr = itemserver.getByPropertyAsArray("internalcode", packingData.getItemsku());
					if (itemArr == null || itemArr.length == 0) {
						itemExists = false;
						itMap.put(packingData.getItemsku(), null);
						error = "Fila " + packingData.getRownumber() + ": El producto con SKU " + packingData.getItemsku() + " no existe en el sistema";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						itemExists = true;

						itMap.put(packingData.getItemsku(), itemArr[0]);

						packingData.setItemid(itemArr[0].getId());
						if (!itemMap.containsKey(itemArr[0].getId())) {
							itemMap.put(itemArr[0].getId(), itemArr[0]);
						}

						if (itInnerpackMap.containsKey(itemArr[0].getId())) {
							itInnerpackMap.put(itemArr[0].getId(), itemArr[0].getInnerpack());
						}

						item = itemArr[0];
					}
				} else {
					if (itMap.get(packingData.getItemsku()) == null) {
						itemExists = false;
						error = "Fila " + packingData.getRownumber() + ": El producto con SKU " + packingData.getItemsku() + " no existe en el sistema";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						itemExists = true;
						item = itMap.get(packingData.getItemsku());
						packingData.setItemid(item.getId());
					}
				}

				if (ocExists && itemExists) {
					// VERIFICA QUE EL ITEM CORRESPONDA A LA OC
					OrderDetailPK oedPk = new OrderDetailPK(ocMap.get(packingData.getOrdernumber()).getId(), itMap.get(packingData.getItemsku()).getId());
					if (!odMap.containsKey(oedPk)) {
						error = "Fila " + packingData.getRownumber() + ": El producto con SKU " + packingData.getItemsku() + " no pertenece a la O/C N° " + packingData.getOrdernumber();
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}

				// LOCAL DESTINO
				if (!loMap.containsKey(packingData.getLocationcode())) {
					LocationW[] destlocationArr = locationserver.getByPropertyAsArray("code", packingData.getLocationcode());
					if (destlocationArr == null || destlocationArr.length == 0) {
						locExists = false;
						loMap.put(packingData.getLocationcode(), null);
						error = "Fila " + packingData.getRownumber() + ": El local con código " + packingData.getLocationcode() + " no existe en el sistema";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						locExists = true;

						loMap.put(packingData.getLocationcode(), destlocationArr[0]);

						packingData.setLocationid(destlocationArr[0].getId());
						if (!destLocalMap.containsKey(destlocationArr[0].getId())) {
							destLocalMap.put(destlocationArr[0].getId(), destlocationArr[0]);
						}

						destlocation = destlocationArr[0];
					}
				} else {
					if (loMap.get(packingData.getLocationcode()) == null) {
						locExists = false;
						error = "Fila " + packingData.getRownumber() + ": El local con código " + packingData.getLocationcode() + " no existe en el sistema";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						locExists = true;
						destlocation = loMap.get(packingData.getLocationcode());
						packingData.setLocationid(destlocation.getId());
					}
				}

				// VALIDA ESTRUCTURA DE LPN
				if (lpn.length() != 11) {
					// Largo no corresponde
					error = "Fila " + packingData.getRownumber() + ": Formato de LPN err�neo (Largo no corresponde)";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					lpnOK = false;
				}

				if (lpnOK) {
					// LOCAL
					String lpnLocal = lpn.substring(0, 3);

					if (vevDL) {
						if (!lpnLocal.equals("VEV")) {
							error = "Fila " + packingData.getRownumber() + ": Error en formato de LPN, el despacho es de tipo VeV CD";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							lpnOK = false;
						}
					} else {
						if (locExists && !lpnLocal.equals(destlocation.getShortname().toUpperCase())) {
							error = "Fila " + packingData.getRownumber() + ": Error en formato de LPN, local no corresponde";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							lpnOK = false;
						}
					}

					// PROVEEDOR
					String lpnVendor = lpn.substring(3, 6);

					if (!lpnVendor.equals(vendor.getLogisticscode())) {
						error = "Fila " + packingData.getRownumber() + ": El formato del LPN no coincide con el código definido para el proveedor";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						lpnOK = false;
					}

					// CORRELATIVO
					String lpnCorr = lpn.substring(6);
					Integer lpnCorrInt = 0;

					try {
						lpnCorrInt = Integer.valueOf(lpnCorr);
					} catch (NumberFormatException e) {
						error = "Fila " + packingData.getRownumber() + ": Error en formato de LPN, correlativo debe ser un valor N°merico";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						lpnOK = false;
					}

					if (locExists) {
						VendorLocationId vdLoId = new VendorLocationId(vendor.getId(), destlocation.getId());
						List<Integer> ocLoItList = null;
						String ocLoIt = "";

						if (!actualCorrMap.containsKey(vdLoId)) {
							try {
								VendorLocationW vdLoW = vendorlocationserver.getById(vdLoId);
								actualCorrMap.put(vdLoId, vdLoW.getLastnumber());
								lastCorrMap.put(vdLoId, vdLoW.getLastnumber());

								if (lpnCorrInt.intValue() > vdLoW.getLastnumber().intValue()) {
									lastCorrMap.put(vdLoId, lpnCorrInt);
								}
							} catch (NotFoundException e) {
								// ACTUALIZA ULTIMO CORRELATIVO
								actualCorrMap.put(vdLoId, 0);
								lastCorrMap.put(vdLoId, lpnCorrInt);
							}
						} else {
							// Valida que el que viene se mayor al actual
							if (lpnCorrInt.intValue() > actualCorrMap.get(vdLoId).intValue()) {
								if (lastCorrMap.get(vdLoId).intValue() < lpnCorrInt) {
									lastCorrMap.put(vdLoId, lpnCorrInt);
								}
							}
						}

						if (ocExists && itemExists) {
							ocLoIt = order.getId() + "_" + item.getId() + "_" + destlocation.getId();

							if (!ocLoItCorrList.containsKey(ocLoIt)) {
								ocLoItList = new ArrayList<Integer>();
								// ocLoItList.add(actualCorrMap.get(vdLoId));
								ocLoItList.add(lpnCorrInt);
								ocLoItCorrList.put(ocLoIt, ocLoItList);
							} else {
								// Valida que no este siendo usado ya
								if (ocLoItCorrList.get(ocLoIt).contains(lpnCorrInt)) {
									error = "Fila " + packingData.getRownumber() + ": Error en carga de PL, LPN  " + lpn + " para proveedor - local ya esta siendo usado";
									baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
									lpnOK = false;
								} else {
									ocLoItCorrList.get(ocLoIt).add(lpnCorrInt);
								}
							}
						}
					}
				}

				// VALIDA QUE EL DETALLE OC-ITEM-LOCAL DESTINO EXISTAN EN EL SISTEMA
				if (ocExists && itemExists && locExists) {
					PreDeliveryDetailPK pdKey = new PreDeliveryDetailPK(order.getId(), item.getId(), destlocation.getId());

					if (!pddMap.containsKey(pdKey)) {
						error = "Fila " + packingData.getRownumber() + ": La predistribución de O/C, producto y local de destino no existe en el despacho";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}

					// VERIFICA QUE LA COMBINACION OC-PRODUCTO-LOCAL PERTENEZCA AL DESPACHO
					OrderDeliveryDetailPK oddPK = new OrderDeliveryDetailPK(order.getId(), delivery.getId(), item.getId(), destlocation.getId());
					if (!oddMap.containsKey(oddPK)) {
						error = "Fila " + packingData.getRownumber() + ": El detalle de O/C, producto y local de destino no existe en el despacho";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}

					// ALMACENA LA CANTIDAD DE PRODUCTO PARA EL DETALLE INDEPENDIENTE DEL TIPO DE OC
					// SI ES STOCK LA PREDISTRIBUCION ES AL MISMO LOCAL( IGUAL AL DETALLE )
					PreDeliveryDetailPK pddPKTmp = new PreDeliveryDetailPK(order.getId(), item.getId(), destlocation.getId());
					if (!pddPLMap.containsKey(pddPKTmp)) {
						pddPLMap.put(pddPKTmp, packingData.getUnits());
					} else {
						pddPLMap.put(pddPKTmp, pddPLMap.get(pddPKTmp) + packingData.getUnits());
					}

					// POR OC
					if (!ocPLUnitsMap.containsKey(order.getId())) {
						ocPLUnitsMap.put(order.getId(), packingData.getUnits());
					} else {
						ocPLUnitsMap.put(order.getId(), ocPLUnitsMap.get(order.getId()) + packingData.getUnits());
					}

					// POR OC-ITEM
					OrderDetailPK odPk = new OrderDetailPK(order.getId(), item.getId());
					if (!ocItPLUnitsMap.containsKey(odPk)) {
						ocItPLUnitsMap.put(odPk, packingData.getUnits());
					} else {
						ocItPLUnitsMap.put(odPk, ocItPLUnitsMap.get(odPk) + packingData.getUnits());
					}

					packingListArr[i] = packingData;
				}

				// VALIDA QUE EN EL LPN SE INFORMEN PRODUCTOS DE UNA SOLA OC
				if (ocExists && lpnOK) {
					if (!lpnOcMap.containsKey(lpn)) {
						lpnOcMap.put(lpn, order.getId());
						lpnDpMap.put(lpn, order.getDepartmentid());
					} else {
						if (!lpnOcMap.get(lpn).equals(order.getId())) {
							error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " se est� informando con productos para más de una O/C";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
				}

				// VALIDA QUE EL LPN ESTE ASOCIADO A UN LOCAL DE DESTINO
				if (locExists && lpnOK) {
					if (!lpnDestLoMap.containsKey(lpn))
						lpnDestLoMap.put(lpn, destlocation.getId());
					else {
						if (!lpnDestLoMap.get(lpn).equals(destlocation.getId())) {
							error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " se est� informando para más de un local de destino";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
				}

				// VALIDA QUE EL LPN ESTE ASOCIADO A PRODUCTOS DE UN MISMO FLUJO
				if (itemExists && lpnOK) {
					if (!lpnFlowMap.containsKey(lpn))
						lpnFlowMap.put(lpn, item.getFlowtypeid());
					else {
						if (!lpnFlowMap.get(lpn).equals(item.getFlowtypeid())) {
							error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " debe contener productos de un mismo tipo de flujo";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
				}

				if (stockDL && lpnOK) {
					if (itemExists) {
						// VALIDA QUE CADA LPN CONTENGA SOLO UN SKU
						if (!lpnItMap.containsKey(lpn))
							lpnItMap.put(lpn, item.getId());
						else {
							if (!lpnItMap.get(lpn).equals(item.getId())) {
								error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " debe contener solo un producto pues el despacho es de tipo de destino Stock";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
						}

						// VALIDA QUE LA CANTIDAD DE PRODUCTO INFORMADA SEA MULTIPLO DE SU INNERPACK
						if ((packingData.getUnits() % item.getInnerpack()) != 0) {
							error = "Fila " + packingData.getRownumber() + ": La cantidad informada para el producto SKU " + item.getInternalcode() + " debe ser m�ltiplo del innerpack definido";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
				}

				// VALIDA QUE TODOS LOS REGISTROS TIENEN ASOCIADO UN N°MERO DE DOCUMENTO TRIBUTARIO
				if (packingData.getTaxnumber() == null || packingData.getTaxnumber().equals(-1L)) {
					error = "Fila " + packingData.getRownumber() + ": No se informa N°mero de factura";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				// VALIDA QUE EL LPN NO EXISTA EN EL SISTEMA PARA ALGUNA ENTREGA ANTERIOR DEL PROVEEDOR
				if (lpnOK) {
					BoxW[] boxArr = null;
					PalletW[] palletArr = null;

					if (packingData.isBulk()) {
						if (!boxMap.containsKey(lpn)) {
							boxArr = boxserver.getByQueryAsArray("select bo from Box as bo, OrderDelivery as odl, Delivery as dl where bo.orderdelivery = odl and odl.delivery = dl and bo.code = '" + lpn + "' and dl.vendor.id = " + vendor.getId());

							if (boxArr != null && boxArr.length > 0) {
								boxMap.put(lpn, boxArr[0]);
								error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " ya existe para un despacho anterior";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							} else {
								lpnTypeMap.put(lpn, true);
								boxMap.put(lpn, null);
							}
						} else {
							if (boxMap.get(lpn) != null) {
								error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " ya existe para un despacho anterior";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
						}
					} else {
						if (!palletMap.containsKey(lpn)) {
							palletArr = palletserver.getByQueryAsArray("select pa from Pallet as pa, OrderDelivery as odl, Delivery as dl where pa.orderdelivery = odl and odl.delivery = dl and pa.code = '" + lpn + "' and dl.vendor.id = " + vendor.getId());

							if (palletArr != null && palletArr.length > 0) {
								palletMap.put(lpn, palletArr[0]);
								error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " ya existe para un despacho anterior";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							} else {
								lpnTypeMap.put(lpn, false);
								palletMap.put(lpn, null);
							}
						} else {
							if (palletMap.get(lpn) != null) {
								error = "Fila " + packingData.getRownumber() + ": El LPN " + lpn + " ya existe para un despacho anterior";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
						}
					}

					// ALMACENA LOS LPN COMO GRUPO
					if (!plMap.containsKey(lpn))
						newPLList = new ArrayList<PackingListDataDTO>();
					else
						newPLList = plMap.get(lpn);

					newPLList.add(packingData);
					plMap.put(lpn, newPLList);
				}
				
				// JPE 20200420
				if (paperlessValidation) {
					if (ocExists && itemExists) {
						// JPE 20190710
						OrderDetailPK ocdPk = new OrderDetailPK(packingData.getOrderid(), packingData.getItemid());
						if (tdMap.containsKey(packingData.getTaxnumber())) {
							// Validar que no se repita el mismo N°mero de factura para más de una orden
							if (!tdMap.get(packingData.getTaxnumber()).equals(packingData.getOrdernumber())) {
								error = "Fila " + packingData.getRownumber() + ": El N°mero de factura " + packingData.getTaxnumber() + " est� asociado a más de una orden de compra";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
							// Acumular los montos de la factura
							else if (odMap.containsKey(ocdPk)) { // se usa esta condición para el caso de un error previo de detalle de orden a�n no mostrado al usuario
								if (tdOcMap.containsKey(packingData.getTaxnumber() + "_" + packingData.getOrdernumber())) {
									tdOcMap.put(packingData.getTaxnumber() + "_" + packingData.getOrdernumber(),
											tdOcMap.get(packingData.getTaxnumber() + "_" + packingData.getOrdernumber()) + packingData.getUnits() * odMap.get(ocdPk).getFinalcost());
								}
								else {
									tdOcMap.put(packingData.getTaxnumber() + "_" + packingData.getOrdernumber(), packingData.getUnits() * odMap.get(ocdPk).getFinalcost());
								}
							}
						}
						else {
							// Validar que el N°mero de factura no exista en el sistema para el mismo proveedor
							prop1 = new PropertyInfoDTO("number", "number", packingData.getTaxnumber());
							prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
							taxdocuments = taxdocumentserver.getByPropertiesAsArray(prop1, prop2);
							if (taxdocuments != null && taxdocuments.length > 0) {
								error = "Fila " + packingData.getRownumber() + ": El N°mero de factura " + packingData.getTaxnumber() + " ya est� cargado en el sistema para el proveedor";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
							
							tdMap.put(packingData.getTaxnumber(), packingData.getOrdernumber());
							if (odMap.containsKey(ocdPk)) { // se usa esta condición para el caso de un error previo de detalle de orden a�n no mostrado al usuario
								tdOcMap.put(packingData.getTaxnumber() + "_" + packingData.getOrdernumber(), packingData.getUnits() * odMap.get(ocdPk).getFinalcost());
							}
						}
					}
				}
			}

			// Si hay errores hasta el momento, no se puede continuar
			if (baseresultlist.size() > 0) {
				// Ordenar errores
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
			}

			// VALIDA LOS REGISTROS CON LOS DATOS DE BD
			BoxW box = null;
			PalletW pallet = null;
			BulkW bulk = null;
			BulkDetailW bulkdetail = null;

			Map<String, BulkW> bulkMap = new HashMap<String, BulkW>();
			Map<String, List<BulkDetailW>> bulkDetailMap = new HashMap<String, List<BulkDetailW>>();

			List<BulkDetailW> bulkDetailList = null;
			Double totalunits = 0.0;

			Iterator it = plMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry data = (Map.Entry) it.next();
				List<PackingListDataDTO> packing = (List<PackingListDataDTO>) data.getValue();

				packingListAmount++;
				bulkDetailList = new ArrayList<BulkDetailW>();
				totalunits = 0.0;

				// Iteramos por LPN, si válido lo almacenamos como Bulk y Bulk Detail
				for (PackingListDataDTO packingDt : packing) {

					OrderDeliveryDetailPK keyTmp = new OrderDeliveryDetailPK(packingDt.getOrderid(), delivery.getId(), packingDt.getItemid(), packingDt.getLocationid());

					// VALIDA QUE LAS CANTIDADES INFORMADAS NO SOBREPASEN LO COMPROMETIDO PARA ENTREGAR A NIVEL DE
					// DETALLE DEL DESPACHO
					if (packingDt.getUnits().doubleValue() > oddMap.get(keyTmp).getAvailableunits()) {
						error = "No puede cargar cantidades mayores a lo disponible para el producto SKU " + packingDt.getItemsku() + " y local de destino " + packingDt.getLocationcode() + " de la O/C N° " + packingDt.getOrdernumber();
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					} else {
						// Es un detalle válido, se genera BulkDetail
						bulkdetail = new BulkDetailW();
						bulkdetail.setDeliveryid(delivery.getId());
						bulkdetail.setItemid(packingDt.getItemid());
						bulkdetail.setLocationid(packingDt.getLocationid());
						bulkdetail.setOrderid(packingDt.getOrderid());
						bulkdetail.setRefdocument(packingDt.getTaxnumber().toString());
						bulkdetail.setUnits(packingDt.getUnits());

						totalunits = totalunits + packingDt.getUnits();

						if (!bulkDetailMap.containsKey((String) data.getKey())) {
							bulkDetailList = new ArrayList<BulkDetailW>();
						} else {
							bulkDetailList = bulkDetailMap.get((String) data.getKey());
						}
						bulkDetailList.add(bulkdetail);
						bulkDetailMap.put((String) data.getKey(), bulkDetailList);

						// CONTABILIZA UNIDADES POR PRODUCTO
						if (!itemUnits.containsKey(keyTmp)) {
							itemUnits.put(keyTmp, bulkdetail.getUnits());
						} else {
							itemUnits.put(keyTmp, itemUnits.get(keyTmp) + bulkdetail.getUnits());
						}

						OrderDeliveryDetailW oddTmp = oddMap.get(keyTmp);
						oddTmp.setAvailableunits(oddTmp.getAvailableunits() - bulkdetail.getUnits());

						oddMap.put(keyTmp, oddTmp);
					}
				}

				if (lpnTypeMap.get((String) data.getKey())) {
					// BOX
					box = new BoxW();
					box.setCode((String) data.getKey());
					box.setNumber(Long.valueOf(((String) data.getKey()).substring(6)));
					box.setDeliveryid(delivery.getId());
					box.setDepartmentid(lpnDpMap.get((String) data.getKey()));
					box.setFlowtypeid(lpnFlowMap.get((String) data.getKey()));
					box.setLocationid(lpnDestLoMap.get((String) data.getKey()));
					box.setOrderid(lpnOcMap.get((String) data.getKey()));

					if (!bulkMap.containsKey((String) data.getKey())) {
						bulkMap.put((String) data.getKey(), box);
					}
				}
				else {
					// PALLET
					pallet = new PalletW();
					pallet.setCode((String) data.getKey());
					pallet.setNumber(Long.valueOf(((String) data.getKey()).substring(6)));
					pallet.setDeliveryid(delivery.getId());
					pallet.setDepartmentid(lpnDpMap.get((String) data.getKey()));
					pallet.setFlowtypeid(lpnFlowMap.get((String) data.getKey()));
					pallet.setLocationid(lpnDestLoMap.get((String) data.getKey()));
					pallet.setOrderid(lpnOcMap.get((String) data.getKey()));

					if (!bulkMap.containsKey((String) data.getKey())) {
						bulkMap.put((String) data.getKey(), pallet);
					}
				}

				// MARCA COMO UTILIZADO EL DETALLE DE ORDEN - DESPACHO
				odlPK = new OrderDeliveryPK(lpnOcMap.get((String) data.getKey()), delivery.getId());
				odlUsed.put(odlPK, true);

				packingListTotalUnits = packingListTotalUnits + totalunits;
			}

			// Si hay errores hasta el momento, no se puede continuar
			if (baseresultlist.size() > 0) {
				// Ordenar errores
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
			}

			// Agregar facturas (lleva validación con Paperless WS)
			DatingW dating = datingserver.getByPropertyAsSingleResult("delivery.id", delivery.getId());
			
			// JPE 20200420
			TaxDocumentAddResultDTO taxDocumentAddResult = null;
			if (paperlessValidation) {
				// JPE 20190710
				taxDocumentAddResult = taxdocumentreportmanager.doAddTaxDocumentsByOrders(tdOcMap, vendor, dating.getWhen());
							
				// Si hay errores hasta el momento, no se puede continuar
				if (!taxDocumentAddResult.getStatuscode().equals("0")) {
					result.setValidationerrors(taxDocumentAddResult.getValidationerrors());
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
				}
			}
			
			// PL CORRECTO
			Long taxdocumentnumber = null;
			Long taxdocumentid = null;
			String message = "";
			Iterator itBulk = bulkMap.entrySet().iterator();
			while (itBulk.hasNext()) {
				Map.Entry data = (Map.Entry) itBulk.next();

				if (bulkMap.get((String) data.getKey()) instanceof BoxW) {
					box = (BoxW) bulkMap.get((String) data.getKey());

					box = boxserver.addBox(box);

					bulk = box;
				} else if (bulkMap.get((String) data.getKey()) instanceof PalletW) {
					pallet = (PalletW) bulkMap.get((String) data.getKey());

					pallet = palletserver.addPallet(pallet);

					bulk = pallet;
				}

				bulkDetailList = bulkDetailMap.get((String) data.getKey());

				for (int k = 0; k < bulkDetailList.size(); k++) {
					bulkdetail = bulkDetailList.get(k);
					bulkdetail.setBulkid(bulk.getId());
					
					// JPE 20200420
					if (paperlessValidation) {
						// JPE 20190711
						// Agregar la factura
						taxdocumentnumber = Long.parseLong(bulkdetail.getRefdocument());
						if (taxDocumentAddResult.getValidatedtaxdocuments().containsKey(taxdocumentnumber)) {
							taxdocumentid = taxDocumentAddResult.getValidatedtaxdocuments().get(taxdocumentnumber);
						}
						else if (taxDocumentAddResult.getPendingtaxdocuments().containsKey(taxdocumentnumber)) {
							taxdocumentid = taxDocumentAddResult.getPendingtaxdocuments().get(taxdocumentnumber);
							
							message = "Factura " + taxdocumentnumber + " para OC " + tdMap.get(taxdocumentnumber);
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "0", message, false));
						}
						else {
							getSessionContext().setRollbackOnly();
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Error en proceso de validación de facturas", false);
						}
						
						bulkdetail.setTaxdocumentid(taxdocumentid);
					}
					
					// AGREGA DETALLE
					bulkdetail = bulkdetailserver.addBulkDetail(bulkdetail);

					// ACTUALIZA UNIDADES DISPONIBLEs
					OrderDeliveryDetailPK key = new OrderDeliveryDetailPK(bulkdetail.getOrderid(), bulkdetail.getDeliveryid(), bulkdetail.getItemid(), bulkdetail.getLocationid());
					OrderDeliveryDetailW odd = oddMap.get(key);
					odd.setAvailableunits(itemUnits.get(key));
					odd = orderdeliverydetailserver.updateOrderDeliveryDetail(odd);					
				}
			}
						
			// JPE 20190711
			// Si las facturas no pudieron validarse se muestran los mensajes
			if (baseresultlist.size() > 0) {
				// Ordenar mensajes
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
			}

			// VERIFICA SI ALG�N DETALLE DE ENTREGA NO VENIA INFORMADO EN EL PL
			// DEJA LA CANTIDAD DISPOIBLE EN 0, DE MODO DE AJUSTAR LO COMPROMETIDO CON LO INFORMADO
			Iterator oddIt = oddMap.entrySet().iterator();

			while (oddIt.hasNext()) {
				Map.Entry data = (Map.Entry) oddIt.next();
				OrderDeliveryDetailPK key = (OrderDeliveryDetailPK) data.getKey();
				if (!itemUnits.containsKey(key)) {
					OrderDeliveryDetailW odd = oddMap.get(key);
					odd.setAvailableunits(0D);
					odd = orderdeliverydetailserver.updateOrderDeliveryDetail(odd);
				}
			}

			// CIERRA TODOS LOS ORDER - DELIVERY NO USADOS
			Iterator odlIt = odlUsed.entrySet().iterator();

			while (odlIt.hasNext()) {
				Map.Entry data = (Map.Entry) odlIt.next();
				OrderDeliveryPK key = (OrderDeliveryPK) data.getKey();
				if (!(Boolean) data.getValue()) {
					OrderDeliveryW odlTmp = odlMap.get(key);
					odlTmp.setClosed(true);
					odlTmp = orderdeliveryserver.updateOrderDelivery(odlTmp);
				}
			}

			// CAMBIA ESTADO DE ENTREGA A 'AGENDADA'
			Date now = new Date();
			DeliveryStateTypeW dstAg = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			delivery.setCurrentstatetypeid(dstAg.getId());
			delivery.setCurrentstatetypedate(now);

			delivery = deliveryserver.updateDelivery(delivery);

			DeliveryStateW state = new DeliveryStateW();
			state.setDeliveryid(delivery.getId());
			state.setDeliverystatetypeid(dstAg.getId());
			state.setWhen(now);

			state = deliverystateserver.addDeliveryState(state);

			// Actualizacion del reporte
			DeliveryReportDataDTO deliveryreport = new DeliveryReportDataDTO();
			deliveryreport.setDeliveryid(initParams.getDeliveryid());
			deliveryreport.setDeliverystatetypedesc(dstAg.getName());
			deliveryreport.setDeliverystatetypecode(dstAg.getCode());
			deliveryreport.setAction(dstAg.getAction());
			deliveryreport.setDeliverynumber(delivery.getNumber());

			result.setDeliveryreportdata(deliveryreport);

			// ACTUALIZA LOS VENDOR - LOCATION SI SON MAYORES A LOS ACTUALES
			for (Map.Entry<VendorLocationId, Integer> vdlo : lastCorrMap.entrySet()) {
				VendorLocationW vendorlocation = null;
				try {
					vendorlocation = vendorlocationserver.getById(vdlo.getKey());
					vendorlocation.setLastnumber(vdlo.getValue());
					vendorlocation = vendorlocationserver.updateVendorLocation(vendorlocation);

				} catch (Exception e) {
					vendorlocation = new VendorLocationW();
					vendorlocation.setVendorid(vdlo.getKey().getVendorid());
					vendorlocation.setLocationid(vdlo.getKey().getLocationid());
					vendorlocation.setLastnumber(vdlo.getValue()); // Guarda el m�ximo correlativo
					vendorlocation = vendorlocationserver.addVendorLocation(vendorlocation);
				}
			}

			result.setPlamount(packingListAmount);
			result.setPltotalunits(packingListTotalUnits);

			vendorlocationserver.flush();

			/** *********************** */
			/** * RECALCULO UNIDADES ** */
			/** *********************** */
			for (OrderDeliveryW odl : odlArr) {
				buyorderreportmanager.doCalculateTotalOfOrder(odl.getOrderid());
			}

			// CALCULA LOS BULTOS
			doCalculateTotalBulkDetailOfDelivery(delivery.getId());

			// DVI EN TESTING, Cambiar el estado de la cita a "Confirmado" y poner fecha de confirmación
			// DVI ESTE PASO SE DEBE HACER EN LA RESPUESTA DE ASN CUANDO SE INTEGRE
			dating.setConfirmed(true);
			dating.setConfirmationdate(new Date());
			dating = datingserver.updateDating(dating);

			DatingRequestW datingrequest = datingrequestserver.getByPropertyAsSingleResult("delivery.id", delivery.getId());
			
			if (vendor.getDomestic()) {
				/** ************************************* */
				/** ************ GENERAR ASN ************ */
				/** ************************************* */
				asntoxml.processDeliveryMessages(delivery, dating, (Long[])ocMap.keySet().toArray(new Long[ocMap.keySet().size()]), datingrequest.getRequesteddate(), 0, vendor.getCode().equalsIgnoreCase("IMP"), true);
				
				String messagetype = "ASN";
				String numberStr = dating.getNumber().toString();
				String info = "CREACION";
				String exception = "Despacho " + delivery.getNumber();

				// Se registra el resultado de carga de mensajes en un log particular
				logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
			}
			
			// JPE 20200420
			if (paperlessValidation && taxDocumentAddResult.getPendingtaxdocuments() != null && taxDocumentAddResult.getPendingtaxdocuments().size() > 0) {
				// JPE 20190823
				// Si quedan facturas por validar en Paperless				
				try {
					// Generar un correo para informar aquellas facturas que no se pudieron validar
					String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
					String from = RegionalLogisticConstants.getInstance().getMailSender();
					String[] to = {datingrequest.getEmail()}; // al usuario de la solicitud de cita
					String[] cc = RegionalLogisticConstants.getInstance().getPL_PENDING_TAXDOCUMENTS_MAIL_CC(); // usuarios ParisCL
					String bcc[] = null;
					
					SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
					
					String subject = "B2B Paris: OC sin validar en Packing List [Proveedor: " + vendor.getTradename() + " - Cita " +
									 			dating.getNumber() + " para el " + sdfDate.format(dating.getWhen()) + "]";
						
					String mailmessage =
						"<p>Estimado(a) usuario(a):</p>" + //
						"<p>Este correo tiene por motivo informar las facturas que no pudieron ser validadas de manera autom�tica en la " + //
						"carga del Packing List para la cita N° " + dating.getNumber() + ".<br>" + //
						"El detalle se encuentra en el archivo adjunto.</p><br>" + //
						"<p><b><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></b></p>" + //
						"<p>Atte.<br>" + //
						   "B2B Paris</p>"; //

					PendingMailW pm = schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, mailmessage, true, session, "PL_PENDING_TAXDOCUMENTS", delivery.getNumber().toString());

					// JPE 20200122
					// Generar archivo adjunto en formato Excel con la información de las facturas pendientes
					// El adjunto se genera en carpeta con id del mensaje
					generatePendingTaxDocumentsAttachment(pm.getId(), delivery.getNumber(), vendor.getTradename(), dating, taxDocumentAddResult.getPendingtaxdocuments(), tdMap);
										
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("No fue posible enviar email de Facturas en PL sin validar");
				}				
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	private void generatePendingTaxDocumentsAttachment(Long folderName, Long deliveryNumber, String vendorTradeName, DatingW dating, Map<Long, Long> pendingTaxDocuments, Map<Long, Long> taxDocuments) {
		
		if (pendingTaxDocuments != null && pendingTaxDocuments.size() > 0) {
			String formattedDatingDateTime = null;
			String folderPath = null;
			try {
				formattedDatingDateTime = datingserver.getFormattedDatingDateTime(dating.getId());
				folderPath = RegionalLogisticConstants.getInstance().getATTACHMENTS_PATH() + folderName + "/";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("No fue posible generar el adjunto en email de Facturas en PL sin validar, despacho " + deliveryNumber);
				return;
			}
			
			// Construcción del excel
			// Obtener el nombre del archivo
			String filename = "Facturas sin validar Cita " + dating.getNumber() + ".xls";

			DataRow row = null;

			// Escribir descripción del filtro seleccionado
			DataTable dt0 = new DataTable("FACTURAS_SIN_VALIDAR");

			DataColumn col01 = new DataColumn("vendortradename", "Nombre Proveedor", String.class);
			DataColumn col02 = new DataColumn("datingnumber", "N°mero de Cita", Long.class);
			DataColumn col03 = new DataColumn("datingdatetime", "Fecha y Hora Cita", String.class);
			DataColumn col04 = new DataColumn("ordernumber", "Orden de Compra", Long.class);
			DataColumn col05 = new DataColumn("taxdocumentnumber", "Factura", Long.class);
						
			dt0.addColumn(col01);		dt0.addColumn(col02);		dt0.addColumn(col03);		dt0.addColumn(col04);		dt0.addColumn(col05);

			for (Map.Entry<Long, Long> e : pendingTaxDocuments.entrySet()) {
				row = dt0.newRow();

				row.setCellValue("vendortradename", vendorTradeName);
				row.setCellValue("datingnumber", dating.getNumber());
				row.setCellValue("datingdatetime", formattedDatingDateTime);
				row.setCellValue("ordernumber", taxDocuments.get(e.getKey()));
				row.setCellValue("taxdocumentnumber", e.getKey());
								
				dt0.addRow(row);
			}

			// Crear archivo xls
			// Crear carpeta asociada al id donde almacenar el adjunto
			File folder = new File(folderPath);
			if (folder.isDirectory()) {
				if (folder.listFiles() != null && folder.listFiles().length > 0) {
					for (File file : folder.listFiles()) {
						file.delete();
					}
				}
			}
			else {
				folder.mkdir();
			}
			
			XLSConverterPOI converter = new XLSConverterPOI();
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);

			try {
				converter.ExportToXLS(dt0, null, folderPath + filename, Charset.forName("UTF-16"));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("No fue posible generar el adjunto en email de Facturas en PL sin validar, despacho " + deliveryNumber);
			} finally {
				
			}
		}
	}	
	
	public UploadPredistributedPackingListResultDTO doUploadPredistributedPackingListFromStockOrders(UploadPredistributedPackingListInitParamDTO initParamDTO) {

		UploadPredistributedPackingListResultDTO resultDTO = new UploadPredistributedPackingListResultDTO();

		// Almacena los mensajes de error que se vayan produciendo
		List<BaseResultDTO> validationErrors = new ArrayList<BaseResultDTO>();

		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
		}
		location = locations[0];
		
		try {
			// Validar que se hayan ingresado la fecha/hora de cita, duración y and�n
			if (initParamDTO.getDatingdate() == null || initParamDTO.getDatingtime() == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe ingresar fecha y hora programada para la cita", false);
			}
			if (initParamDTO.getDurationinminutes() == null || initParamDTO.getDurationinminutes() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe ingresar la duración que tendr� la cita", false);
			}
			if (initParamDTO.getDockcode() == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe ingresar el and�n reservado para la cita", false);
			}
			
			// Validar la existencia del local
//			LocationW location = null;
//			try {
//				location = locationserver.getById(initParamDTO.getLocationid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1702");
//			}

			String csvFilename = initParamDTO.getFilename();
			String error = "";
			int total = 0;
			UploadErrorDTO[] errorArr = null;

			// Crea tabla Temporal para guardar ajuste
			bulkdetailserver.doCreateTempTablePredistributedPackingList();

			// Crea tabla temporal para guardar Errores
			bulkdetailserver.doCreateTempTableError();

			// Carga del archivo CSV
			try {
				total = bulkdetailserver.doLoadCSV(csvFilename);

			} catch (Exception e) {
				error = "Error procesando el archivo de packing list predistribuido";
				validationErrors.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", error, false));
				resultDTO.setValidationerrors(validationErrors.toArray(new BaseResultDTO[validationErrors.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// No hay registros, ocurrió un error procesando el archivo
			if (total < 0) {
				error = "Error procesando el archivo de packing list predistribuido";
				validationErrors.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", error, false));
				resultDTO.setValidationerrors(validationErrors.toArray(new BaseResultDTO[validationErrors.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// Validar que no se repita la combinación de LPN-SKU
			bulkdetailserver.doCheckUniqueLpnItems();
			
			// Validar que un valor de LPN est� asociado solo a una OC
			bulkdetailserver.doCheckUniqueOrderByLpn();
			
			// Validar que un valor de LPN est� asociado solo a una tienda
			bulkdetailserver.doCheckUniqueDestinationLocationByLpn();
			
			// Validar que el valor para N°mero de contenedor sea el mismo en todas las l�neas
			bulkdetailserver.doCheckUniqueContainer();
			
			// Si hay errores detiene procedimiento y devuelve errores
			errorArr = bulkdetailserver.getErrorsOfPredistributedPackingList();
			if (errorArr.length > 0) {
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorArr.length];
				for (int i = 0; i < errorArr.length; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L6000");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// Validar que las órdenes de compra existan
			bulkdetailserver.doCheckValidOrders();
			
			// Validar que los proveedores asociados a las órdenes sean internacionales
			bulkdetailserver.doCheckValidVendors();
			
			// Validar que las órdenes posean el mismo lugar de entrega
			bulkdetailserver.doCheckUniqueDeliveryLocation();
			
			// Validar que el lugar de entrega sea el mismo del filtro
			bulkdetailserver.doCheckValidDeliveryLocation(location.getId());
			
			// Si hay errores detiene procedimiento y devuelve errores
			errorArr = bulkdetailserver.getErrorsOfPredistributedPackingList();
			if (errorArr.length > 0) {
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorArr.length];
				for (int i = 0; i < errorArr.length; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L6000");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// Carga IDs
			bulkdetailserver.doFillOrderDetailData();
			bulkdetailserver.doFillDestinationLocationData();
			
			// Validar que los productos pertenezcan a cada una de las OC seg�n se indica
			bulkdetailserver.doCheckValidOrderItems();
			
			// Validar que existan las tiendas
			bulkdetailserver.doCheckValidDestinationLocations();
			
			// Validar el LPN con relación al nombre corto de la tienda
			bulkdetailserver.doCheckLpnDestinationLocation();
			
			// Validar el LPN con relación al código logístico asociado al proveedor de la orden
			bulkdetailserver.doCheckLpnVendorLogisticCode();
			
			// Validar el correlativo del LPN
			bulkdetailserver.doCheckLpnCorrelative();
			
			// Validar que todos los productos del LPN sean del mismo tipo de flujo
			bulkdetailserver.doCheckLpnFlowType();
			
			// Si hay errores detiene procedimiento y devuelve errores
			errorArr = bulkdetailserver.getErrorsOfPredistributedPackingList();
			if (errorArr.length > 0) {
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorArr.length];
				for (int i = 0; i < errorArr.length; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L6000");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// Agrupar toda la información por orden de compra y tipo de flujo (el local de entrega es el mismo)
			List<PredistributedPackingListDataDTO> pplData = bulkdetailserver.getPredistributedPackingListDate();
			
			HashMap<String, List<PredistributedPackingListDataDTO>> pplMap = new HashMap<String, List<PredistributedPackingListDataDTO>>();
			List<PredistributedPackingListDataDTO> ppls = null;
			String key = "";
			for (PredistributedPackingListDataDTO ppl : pplData) {
				key = ppl.getOrdernumber() + "_" + ppl.getFlowtypename();
				if (pplMap.containsKey(key)) {
					ppls = pplMap.get(key);
				}
				else {
					ppls = new ArrayList<PredistributedPackingListDataDTO>();
				}
				
				ppls.add(ppl);
				pplMap.put(key, ppls);
			}
			
			/** ************************************* */
			/** ************ GENERAR ASN ************ */
			/** ************************************* */
			List<UploadPredistributedPackingListDataDTO> data = new ArrayList<UploadPredistributedPackingListDataDTO>();
			UploadPredistributedPackingListDataDTO datingData = null;
			Long datingNumber = null;
			String[] keyArray = null;
			for (Map.Entry<String, List<PredistributedPackingListDataDTO>> e : pplMap.entrySet()) {
				keyArray = e.getKey().split("_");
				datingNumber = datingserver.getNextSequenceDatingNumber();
				
				asntoxml.processPredistributedPackingListMessage(e.getValue(), datingNumber.toString(), location.getId(),
																	initParamDTO.getDatingdate(), initParamDTO.getDatingtime(),
																		initParamDTO.getDurationinminutes(), initParamDTO.getDockcode());
				
				datingData = new UploadPredistributedPackingListDataDTO();
				datingData.setAsnnumber(datingNumber.toString());
				datingData.setOrdernumber(keyArray[0]);
				datingData.setFlowtypename(keyArray[1]);
				data.add(datingData);
			}
			
			// Se registra el resultado de carga de mensajes en un log particular
			for (UploadPredistributedPackingListDataDTO info : data) {
				logger_ack.info(",\"ASN\",\"" + info.getAsnnumber() + "\",\"CREACION\",\"PL Predistribuido Importado OC " + info.getOrdernumber() + "\"");
			}

			resultDTO.setData((UploadPredistributedPackingListDataDTO[]) data.toArray(new UploadPredistributedPackingListDataDTO[data.size()]));

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public UploadPreDeliveryDetailDataResultDTO doUploadPreDeliveryDetailsForImportedDelivery(UploadPreDeliveryDetailDataInitParamDTO initParams) {
		UploadPreDeliveryDetailDataResultDTO result = new UploadPreDeliveryDetailDataResultDTO();

		try {
			List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
			String error = "";

			// TIPOS DE FLUJO
			FlowTypeW[] ftArr = flowtypeserver.getAllAsArray();
			Map<Long, FlowTypeW> ftMap = new HashMap<Long, FlowTypeW>();
			for (FlowTypeW ft : ftArr) {
				if (!ftMap.containsKey(ft.getId())) {
					ftMap.put(ft.getId(), ft);
				}
			}

			TreeMap<OrderDetailPK, OrderDetailW> odMap = new TreeMap<OrderDetailPK, OrderDetailW>();
			TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW> pddMap = new TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
			Map<Long, OrderW> orderMap = new HashMap<Long, OrderW>();
			Map<Long, VendorW> orderOriginalVendorMap = new HashMap<Long, VendorW>();
			Map<String, ItemW> itemMap = new HashMap<String, ItemW>();
			Map<String, AtcW> atcMap = new HashMap<String, AtcW>();
			Map<String, LocationW> locationMap = new HashMap<String, LocationW>();
			Map<Long, PreDeliveryDetailW[]> pddOcMap = new HashMap<Long, PreDeliveryDetailW[]>();
			Map<Long, OrderDetailW[]> odOcMap = new HashMap<Long, OrderDetailW[]>();
			for (PreDeliveryDetailDataDTO predeliveryData : initParams.getDetails()) {
				OrderW order = null;
				VendorW originalvendor = null;
				if (orderMap.containsKey(predeliveryData.getOrdernumber())) {
					order = orderMap.get(predeliveryData.getOrdernumber());
					originalvendor = orderOriginalVendorMap.get(predeliveryData.getOrdernumber());
				}
				else {
					try {
						order = orderserver.getByPropertyAsSingleResult("number", predeliveryData.getOrdernumber());
						orderMap.put(predeliveryData.getOrdernumber(), order);
					
						VendorW[] originalvendors = vendorserver.getByPropertyAsArray("code", order.getVendorcodeimp());
						if (originalvendors != null && originalvendors.length > 0) {
							originalvendor = originalvendors[0];
							orderOriginalVendorMap.put(predeliveryData.getOrdernumber(), originalvendor);
						}
						
					} catch (Exception e1) {
						error = "La orden " + predeliveryData.getOrdernumber() + " no existe";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}
				
				predeliveryData.setOriginalvendorid(originalvendor == null ? null : originalvendor.getId());
				predeliveryData.setOriginalvendorcode(originalvendor == null ? "" : originalvendor.getCode());
				predeliveryData.setOriginalvendorname(originalvendor == null ? "" : originalvendor.getName());
								
				// ORDERDETAIL
				if (order != null) {
					OrderDetailW[] odArr = null;
					if (odOcMap.containsKey(order.getId())) {
						odArr = odOcMap.get(order.getId());
					}
					else {
						odArr = orderdetailserver.getByPropertyAsArray("id.orderid", order.getId());
						odOcMap.put(order.getId(), odArr);
						OrderDetailPK ocdPk = null;
						for (OrderDetailW od : odArr) {
							ocdPk = new OrderDetailPK(od.getOrderid(), od.getItemid());
							if (!odMap.containsKey(ocdPk)) {
								odMap.put(ocdPk, od);
							}
						}
					}

					// PREDELIVERYDETAIL
					PreDeliveryDetailW[] pddArr = null;
					if (pddOcMap.containsKey(order.getId())) {
						pddArr = pddOcMap.get(order.getId());
					}
					else {
						pddArr = predeliverydetailserver.getByPropertyAsArray("id.orderid", order.getId());
						pddOcMap.put(order.getId(), pddArr);
						PreDeliveryDetailPK pddPk = null;
						for (PreDeliveryDetailW pdd : pddArr) {
							pddPk = new PreDeliveryDetailPK(pdd.getOrderid(), pdd.getItemid(), pdd.getLocationid());
							if (!pddMap.containsKey(pddPk)) {
								pddMap.put(pddPk, pdd);
							}
						}
					}

					// VALIDA QUE LA UNIDADES SEAN MAYORES O IGUALES A CERO
					if (predeliveryData.getTodeliveryunits() < 0) {
						error = "Fila " + predeliveryData.getRownumber() + ": No se pueden cargar cantidades en valor " + predeliveryData.getTodeliveryunits();
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}

					predeliveryData.setOrderid(order.getId());

					// SE VERIFICA QUE LOCALES Y PRODUCTOS EXISTAN
					// ITEM
					ItemW item = null;
					if (itemMap.containsKey(predeliveryData.getItemsku())) {
						item = itemMap.get(predeliveryData.getItemsku());
					}
					else {
						ItemW[] items = itemserver.getByPropertyAsArray("internalcode", predeliveryData.getItemsku());
						if (items == null || items.length == 0) {
							error = "Fila " + predeliveryData.getRownumber() + ": El producto con SKU " + predeliveryData.getItemsku() + " no existe en el sistema";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						else {
							item = items[0];
							itemMap.put(predeliveryData.getItemsku(), item);
						}
					}
					
					if (item != null) {
						predeliveryData.setItemid(item.getId());
						predeliveryData.setItemdesc(item.getName());
						predeliveryData.setFlowtypeid(item.getFlowtypeid());
						predeliveryData.setFlowtype(ftMap.get(item.getFlowtypeid()).getName());

						// VERIFICA QUE EL ITEM CORRESPONDA A LA OC
						OrderDetailPK oedPk = new OrderDetailPK(order.getId(), item.getId());
						OrderDetailW orderdetail = null;
						if (odMap.containsKey(oedPk)) {
							orderdetail = odMap.get(oedPk);
							
							// ATC
							AtcW atc = null;
							if (orderdetail.getHasatc()) {
								if (predeliveryData.getAtccode() != null && !predeliveryData.getAtccode().equals("")) {
									if (atcMap.containsKey(predeliveryData.getAtccode())) {
										atc = atcMap.get(predeliveryData.getAtccode());
									}
									else {
										AtcW[] atcs = atcserver.getByPropertyAsArray("code", predeliveryData.getAtccode());
										if (atcs == null || atcs.length == 0) {
											error = "Fila " + predeliveryData.getRownumber() + ": El ATC con código " + predeliveryData.getAtccode() + " no existe en el sistema";
											baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
										}
										else {
											atc = atcs[0];
											atcMap.put(predeliveryData.getAtccode(), atc);
										}
									}
									
									if (atc != null) {
										ItemAtcW[] itematcs = itematcserver.getByPropertyAsArray("item.id", item.getId());
										if (itematcs == null || itematcs.length == 0) {
											error = "Fila " + predeliveryData.getRownumber() + ": No se encuentra el ATC asociado al producto con SKU " + predeliveryData.getItemsku();
											baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
										}
										else if (!itematcs[0].getAtcid().equals(atc.getId())) {
												error = "Fila " + predeliveryData.getRownumber() + ": El código ATC del producto con SKU " + predeliveryData.getItemsku() + " de la O/C N° " + order.getNumber() + " no coincide con el del sistema";
												baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
										}
										else {
											predeliveryData.setAtcid(atc.getId());
										}										
									}
								}
								else {
									error = "Fila " + predeliveryData.getRownumber() + ": Falta el código ATC asociado al producto con SKU " + predeliveryData.getItemsku() + " de la O/C N° " + order.getNumber();
									baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
							}								
						}
						else {
							error = "Fila " + predeliveryData.getRownumber() + ": El producto con SKU " + predeliveryData.getItemsku() + " no pertenece a la O/C N° " + order.getNumber();
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					
					// LOCAL DESTINO
					LocationW destlocation = null;
					if (locationMap.containsKey(predeliveryData.getLocationcode())) {
						destlocation = locationMap.get(predeliveryData.getLocationcode());
					}
					else {
						LocationW[] destlocations = locationserver.getByPropertyAsArray("code", predeliveryData.getLocationcode());
						if (destlocations == null || destlocations.length == 0) {
							error = "Fila " + predeliveryData.getRownumber() + ": El local con código " + predeliveryData.getLocationcode() + " no existe en el sistema";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						else {
							destlocation = destlocations[0];
							locationMap.put(predeliveryData.getLocationcode(), destlocation);
						}
					}
					
					if (destlocation != null) {
						predeliveryData.setLocationid(destlocation.getId());
						predeliveryData.setLocationdesc(destlocation.getName());
						
						// VALIDA QUE EL DETALLE OC-ITEM-LOCAL DESTINO EXISTAN EN EL SISTEMA
						if (item != null) {
							PreDeliveryDetailPK pdKey = new PreDeliveryDetailPK(order.getId(), item.getId(), destlocation.getId());

							if (!pddMap.containsKey(pdKey)) {
								error = "Fila " + predeliveryData.getRownumber() + ": El local con código " + predeliveryData.getLocationcode() + " no fue informado en la O/C N° " + order.getNumber();
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							} else {
								predeliveryData.setTotalunits(pddMap.get(pdKey).getUnits());
							}
						}
					}
				}
			}

			// Si hay errores hasta el momento, no se puede continuar
			if (baseresultlist.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
			}

			result.setPredeliverydetail(initParams.getDetails());
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	public DeliveryStateTypeArrayResultDTO getAllDeliveryStateTypes() {
		DeliveryStateTypeArrayResultDTO resultDTO = new DeliveryStateTypeArrayResultDTO();
		try {
			DeliveryStateTypeW[] dstW = deliverystatetypeserver.getAllAsArray();
			DeliveryStateTypeDTO[] dstDTO = new DeliveryStateTypeDTO[dstW.length];
			DateConverterFactory2.copyProperties(dstW, dstDTO, DeliveryStateTypeW.class, DeliveryStateTypeDTO.class);

			resultDTO.setDeliverystatetypes(dstDTO);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	public DeliveryReportResultDTO getDeliveryDataById(Long deliveryid) {
		DeliveryReportResultDTO resultDTO = new DeliveryReportResultDTO();
		
		try {
			DeliveryW delivery = deliveryserver.getById(deliveryid);
			
			VendorW vendor = vendorserver.getByPropertyAsSingleResult("id", delivery.getVendorid());
			
			DeliveryReportInitParamDTO init = new DeliveryReportInitParamDTO();
			init.setDeliverynumber(delivery.getNumber());
			init.setVendorcode(vendor.getRut());
			init.setFiltertype(1);
			init.setOrderby(null);
			init.setPageNumber(1);
			init.setRows(100);

			// TRAE REPORTE POR NUMERO DE CITA
			DeliveryReportDataDTO[] report = getDeliveryReport(init, false).getDeliveryreport();
			resultDTO.setDeliveryreport(report);
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public DeliveryDetailReportResultDTO getDeliveryDetailReport(DeliveryDetailReportInitParamDTO initParams, boolean isByFilter) {
		DeliveryDetailReportResultDTO resultW = new DeliveryDetailReportResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		try {
			DeliveryDetailReportDataDTO[] deliverydetail = null;
			DeliveryDetailReportTotalDataDTO totalresult = null;
			int total;

			deliverydetail = orderdeliverydetailserver.getDeliveryDetailReport(vendor.getId(), initParams.getDeliveryid(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (isByFilter) {
				totalresult = orderdeliverydetailserver.getDeliveryDetailCountReport(vendor.getId(), initParams.getDeliveryid());
				total = totalresult.getTotalreg();
				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(deliverydetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}

			resultW.setTotals(totalresult);
			resultW.setOrderdeliverydetail(deliverydetail);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public DeliveryReportResultDTO getDeliveryReport(DeliveryReportInitParamDTO initParamDTO, boolean byFilter) {
		DeliveryReportResultDTO resultDTO = new DeliveryReportResultDTO();
		DeliveryReportDataDTO[] deliveryreport = null;
		int total = 0;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {

			switch (initParamDTO.getFiltertype()) {
			case 0:
				// VIGENTES
				deliveryreport = deliveryserver.getDeliveryReport(null, null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(null, null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			case 1:
				// NUMERO DE CITA
				deliveryreport = deliveryserver.getDeliveryReport(null, initParamDTO.getDeliverynumber(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(null, initParamDTO.getDeliverynumber(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			case 2:
				// NUMERO DE OC
				deliveryreport = deliveryserver.getDeliveryReport(null, initParamDTO.getOcnumber(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(null, initParamDTO.getOcnumber(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			case 3:
				// ESTADO DE LA CITA
				deliveryreport = deliveryserver.getDeliveryReport(null, initParamDTO.getDeliverystatetypeid(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(null, initParamDTO.getDeliverystatetypeid(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			case 4:
				// N°MERO DE CONTENEDOR
				deliveryreport = deliveryserver.getDeliveryReport(initParamDTO.getContainer(), null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(initParamDTO.getContainer(), null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			// JPE 20190403 (SE ELIMINA DEL FILTRO)
//			case 5:
//				// N°MERO DE GU�A DE IMPORTAción
//				deliveryreport = deliveryserver.getDeliveryReport(null, initParamDTO.getGuide(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
//				if (byFilter) {
//					total = deliveryserver.getDeliveryCountReport(null, initParamDTO.getGuide(), vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
//				}
//				break;
			// JPE 20190403 (SE ELIMINA DEL FILTRO)
//			case 6:
//				// N°MERO DE IMPORTAción
//				deliveryreport = deliveryserver.getDeliveryReport(initParamDTO.getImp(), null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
//				if (byFilter) {
//					total = deliveryserver.getDeliveryCountReport(initParamDTO.getImp(), null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
//				}
//				break;
			default:
				// VIGENTES
				deliveryreport = deliveryserver.getDeliveryReport(null, null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
				if (byFilter) {
					total = deliveryserver.getDeliveryCountReport(null, null, vendor.getId(), initParamDTO.getOriginalvendorid(), initParamDTO.getFiltertype());
				}
				break;
			}

			try{
				DeliveryStateTypeW dss = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
				for (DeliveryReportDataDTO report : deliveryreport) {
					PropertyInfoDTO[] props = new PropertyInfoDTO[2];
					props[0] = new PropertyInfoDTO("delivery.id", "delivery", report.getDeliveryid());
					props[1] = new PropertyInfoDTO("deliverystatetype.id", "deliverystatetype", dss.getId());
					List<DeliveryStateW> ds = deliverystateserver.getByProperties(props);
					
					if(ds != null && !ds.isEmpty())
						report.setScheduling(ds.size());
				}
			}catch(Exception e){
				
			}
			
			if (byFilter) {
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(deliveryreport.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setDeliveryreport(deliveryreport);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public DeliveryStateTypeArrayResultDTO getDeliveryStateTypes() {
		DeliveryStateTypeArrayResultDTO resultDTO = new DeliveryStateTypeArrayResultDTO();
		try {
			DeliveryStateTypeW[] dstW = deliverystatetypeserver.getByPropertyAsArray("showable", true);
			DeliveryStateTypeDTO[] dstDTO = new DeliveryStateTypeDTO[dstW.length];
			DateConverterFactory2.copyProperties(dstW, dstDTO, DeliveryStateTypeW.class, DeliveryStateTypeDTO.class);

			resultDTO.setDeliverystatetypes(dstDTO);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	private DeliveryTypeW getDeliveryTypeByOrderType(Long ordertypeid) throws AccessDeniedException, NotFoundException, OperationFailedException {

		String deliveryTypeName = "";

		OrderTypeW orderType = ordertypeserver.getById(ordertypeid);

		if (orderType.getName().equals("XDB"))
			deliveryTypeName = "Tienda";
		else if (orderType.getName().equals("Stock"))
			deliveryTypeName = "Stock";
		else if (orderType.getName().equals("VeV CD"))
			deliveryTypeName = "VeV CD";
		if (orderType.equals(""))
			throw new AccessDeniedException("No existe tipo de lote para tipo de orden");

		DeliveryTypeW deliveryType = deliverytypeserver.getByPropertyAsSingleResult("name", deliveryTypeName);

		return deliveryType;
	}

	public DeliveryDetailArrayResultDTO getFileDeliveryDetailReport(DeliveryDetailInitParamDTO initParams) {

		DeliveryDetailArrayResultDTO resultW = new DeliveryDetailArrayResultDTO();
		DeliveryW delivery = null;

		try {
			// Chequea la existencia del proveedor
			VendorW[] vendors = null;
			VendorW vendor;
			try{
				vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());

			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
			}
			vendor = vendors[0];

			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getDeliveryid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DeliveryW> deliveries = deliveryserver.getByProperties(properties);

			if (deliveries == null || deliveries.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2215");
			}

			delivery = deliveries.get(0);

			// Chequea la existencia del local de entrega
			if(! initParams.getLocationdcode().equals("-1")) {
				LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParams.getLocationdcode());
				if(locationArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1904");
				}
				// Chequea la existencia del despacho para ese local de entrega
				properties[0] = new PropertyInfoDTO("id", "id", initParams.getDeliveryid());
				properties[1] = new PropertyInfoDTO("location.id", "location", locationArr[0].getId());
				deliveries = deliveryserver.getByProperties(properties);

				if (deliveries == null || deliveries.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2216");
				}
			}

			OrderCriteriaDTO orderCriteria = new OrderCriteriaDTO();
			orderCriteria.setPropertyname("OCNUMBER");
			orderCriteria.setAscending(true);

			OrderCriteriaDTO[] orderBy = new OrderCriteriaDTO[1];
			orderBy[0] = orderCriteria;

			DeliveryDetailReportDataDTO[] deliverydetail = null;

			deliverydetail = orderdeliverydetailserver.getDeliveryDetailReport(vendor.getId(), initParams.getDeliveryid(), 0, 0, orderBy, false);

			resultW.setDeliverydetails(deliverydetail);

			DeliveryStateTypeW currentStateType = deliverystatetypeserver.getByPropertyAsSingleResult("id", delivery.getCurrentstatetypeid());

			resultW.setCurrentstatetype(currentStateType.getName());
			
			resultW.setRetailImported(vendor.getRut().equals("IMP"));

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		return resultW;
	}

	public FlowTypesResultDTO getFlowTypes() {
		FlowTypesResultDTO result = new FlowTypesResultDTO();
		FlowTypeDTO[] flowtypedtos = null;

		try {
			FlowTypeW[] flowtypes = flowtypeserver.getAllAsArray();
			flowtypedtos = new FlowTypeDTO[flowtypes.length];
			DateConverterFactory2.copyProperties(flowtypes, flowtypedtos, FlowTypeW.class, FlowTypeDTO.class);
			result.setFlowtypes(flowtypedtos);
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}
	
	public BaseResultDTO updateFlowTypes(FlowTypesInitParamDTO initParam) {
		BaseResultDTO result = new BaseResultDTO();

		try {
			for(FlowTypeDTO flowtype : initParam.getFlowtypes()){
				FlowTypeW flowtypew = new FlowTypeW();
				DateConverterFactory2.copyProperties(flowtype, flowtypew);
				flowtypeserver.updateFlowType(flowtypew);
			}
			
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	private OrderDeliveryDetailResultDTO getOrderDeliveryDetailByCodes(Long ordernumber, String itemsku, String locationcode, String atccode, Long deliveryid) {

		OrderDeliveryDetailResultDTO resultDTO = new OrderDeliveryDetailResultDTO();
		OrderDeliveryDetailDTO orderdeliverydetailDTO = null;

		Long orderid = null;
		Long itemid = null;
		Long locationid = null;
		Long atcid = null;
		
		try {
			// Obtener la orden
			List<OrderW> orders = orderserver.getByProperty("number", ordernumber);
			if (orders == null || orders.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se encontr� orden con N°mero " + ordernumber, false);
			}
			orderid = orders.get(0).getId();
						
			// Obtener el producto
			List<ItemW> items = itemserver.getByProperty("internalcode", itemsku);
			if (items == null || items.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se encontr� producto con sku " + itemsku, false);
			}
			itemid = items.get(0).getId();
			
			// Obtener el local
			List<LocationW> locations = locationserver.getByProperty("code", locationcode);
			if (locations == null || locations.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se encontr� local con código " + locationcode, false);
			}
			locationid = locations.get(0).getId();
			
			// Obtener el ATC
			if (atccode != null && !atccode.equals("")) {
				List<AtcW> atcs = atcserver.getByProperty("code", atccode);
				if (atcs == null || atcs.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se encontr� ATC con código " + atccode, false);
				}
				atcid = atcs.get(0).getId();
			}
						
			orderdeliverydetailDTO = orderdeliverydetailserver.getOrderDeliveryDetailDataByOrderItemLocationAndATC(orderid, deliveryid, itemid, locationid, atcid);
			if (orderdeliverydetailDTO == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se encontr� detalle de despacho para la combinación, orden " +
									ordernumber + ", sku" + itemsku + ", local " + locationcode +
										(atccode != null && !atccode.equals("") ? ", ATC " +atccode : ""), false);
			}
			
			resultDTO.setOrderdeliverydetail(orderdeliverydetailDTO);

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public OrderUnitsArrayResultDTO getOrderDeliveryUnits(Long deliveryId, String vendorcode) {
		OrderUnitsArrayResultDTO resultW = new OrderUnitsArrayResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
			
		try {
			OrderUnitsDTO[] orderunits = orderdeliveryserver.getOrderDeliveryUnitsByDeliveryAndVendor(deliveryId, vendor.getId());

			resultW.setOrderunits(orderunits);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		
		return resultW;
	}

	public PreDeliveryDetailDataResultDTO getPreDeliveryDetailsForImportedDelivery(PreDeliveryDetailDataInitParamDTO initParams, boolean byFilter) {

		PreDeliveryDetailDataResultDTO result = new PreDeliveryDetailDataResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {
			PreDeliveryDetailDataDTO[] reportData = null;
			int total;

			reportData = deliveryserver.getPreDeliveryDetailsForImportedDelivery(initParams.getOrdersid(), vendor.getId(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), false);

			if (byFilter) {
				total = deliveryserver.getCountPreDeliveryDetailsForImportedDelivery(initParams.getOrdersid(), vendor.getId());

				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(reportData.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				result.setPageInfo(pageInfo);
			}

			result.setPredeliverydetail(reportData);

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public OrderContainerDataResultDTO getOrdersContainersForImportedDelivery(OrderContainerDataInitParamDTO initParams) {

		OrderContainerDataResultDTO result = new OrderContainerDataResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			OrderContainerDataDTO[] ordercontainers = deliveryserver.getOrdersContainersForImportedDelivery(initParams.getOrderids(), vendor.getId(), initParams.getOrderby());

			result.setOrdercontainers(ordercontainers);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public ProposedPackingListDataArrayResultDTO getProposedPackingList(ProposedPackingListReportInitParamDTO initParams, boolean isByPages) {

		ProposedPackingListDataArrayResultDTO resultDTO = new ProposedPackingListDataArrayResultDTO();
		ProposedPackingListDataDTO[] proposedpackinglistdatasDTO = null;
		ProposedPackingListTotalDataDTO totalresult = null;

		int total = 0;

		try {
//			// Chequea la existencia del proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}
//			
			
			// Valida proveedor
			VendorW[] vendors = null;
			VendorW vendor;
			try{
				vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());

			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendor = vendors[0];
			
			resultDTO.setDomesticvendor(vendor.getDomestic());

			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getDeliveryid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DeliveryW> deliveries = deliveryserver.getByProperties(properties);

			if (deliveries == null || deliveries.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2215");
			}

			// Chequea la existencia del local de entrega
			if(! initParams.getLocationdcode().equals("-1")) {
				LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParams.getLocationdcode());
				if(locationArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}
				// Chequea la existencia del despacho para ese local de entrega
				properties[0] = new PropertyInfoDTO("id", "id", initParams.getDeliveryid());
				properties[1] = new PropertyInfoDTO("location.id", "location", locationArr[0].getId());
				deliveries = deliveryserver.getByProperties(properties);

				if (deliveries == null || deliveries.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2216");
				}
			}
			
			proposedpackinglistdatasDTO = bulkdetailserver.getProposedPackingList(initParams.getDeliveryid(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);

			resultDTO.setPackinglistdatas(proposedpackinglistdatasDTO);

			if (isByPages) {
				totalresult = bulkdetailserver.getProposedPackingListCount(initParams.getDeliveryid());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPagenumber());
				pageInfo.setRows(proposedpackinglistdatasDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);

				resultDTO.setTotals(totalresult);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); // Error Interno del
			// Servidor
		}

		return resultDTO;
	}

	public ProposedPackingListResultDTO getProposedPackingListByDetailOrInnerpack(ProposedPackingListInitParamDTO initParams) {

		ProposedPackingListResultDTO result = new ProposedPackingListResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());
			DeliveryTypeW deliverytype = deliverytypeserver.getById(delivery.getDeliverytypeid());
			//VendorW vendor = vendorserver.getById(vendor.getId());

			ProposedPackingListDataDTO[] dataArr = deliveryserver.getProposedPackingListByDetailOrInnerpack(initParams.getDeliveryid(), vendor.getId());
			List<ProposedPackingListDataDTO> dataList = new ArrayList<ProposedPackingListDataDTO>();

			if (initParams.isByinnerpack()) {
				// SI ES POR INNERPACK SE DEBE DIVIDIR EL SKU EN N REGISTROS DONDE LAS UNIDADES SEAN MULTIPLOS DEL
				// INNERPACK
				Map<Long, Integer> itInnerPackMap = new HashMap<Long, Integer>();

				for (ProposedPackingListDataDTO data : dataArr) {
					Integer innerpack = 0;
					Double units = 0D;

					if (!itInnerPackMap.containsKey(data.getItemid())) {
						ItemW item = itemserver.getById(data.getItemid());
						itInnerPackMap.put(data.getItemid(), item.getInnerpack());
					}
					innerpack = itInnerPackMap.get(data.getItemid());
					units = data.getUnits();

					if (innerpack <= 0)
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El innerpack del producto " + data.getItemsku() + " es menor a 1", false);

					do {
						ProposedPackingListDataDTO newData = new ProposedPackingListDataDTO();
						DateConverterFactory2.copyProperties(data, newData);
						newData.setUnits(units > innerpack ? innerpack.doubleValue() : units);

						// AGREGA REGISTRO
						dataList.add(newData);

						// DESCUENTA A LAS UNIDADES
						units -= innerpack;

					} while (units > 0);
				}
			} else {
				dataList = Arrays.asList(dataArr);
			}

			// GENERAR LPNS
			Map<Long, String> loShortCodeMap = new HashMap<Long, String>();
			Map<Long, LocationW> loMap = new HashMap<Long, LocationW>();
			Map<VendorLocationId, Integer> lastBkMap = new TreeMap<VendorLocationId, Integer>();
			List<ProposedPackingListDataDTO> finalDataList = new ArrayList<ProposedPackingListDataDTO>();

			for (ProposedPackingListDataDTO data : dataList) {

				LocationW location = locationserver.getById(data.getLocationid());
				loMap.put(data.getLocationid(), location);

				// BUSCA código CORTO DE LOCAL
				// SI EL DESPACHO ES DE TIPO VeV EL CODIGO ES VEV
				String locationcode = "";

				if (deliverytype.getCode().equalsIgnoreCase("VEVCD")) {
					locationcode = "VEV";
				} else {
					if (!loShortCodeMap.containsKey(data.getLocationid())) {
						loShortCodeMap.put(data.getLocationid(), location.getShortname() != null ? location.getShortname() : "");
					}
					locationcode = loShortCodeMap.get(data.getLocationid()).toUpperCase().trim();
				}

				// BUSCA código GENERADO DE PROVEEDOR
				String vendorcodegen = vendor.getLogisticscode();

				// BUSCA EL �LTIMO N°MERO DE BULTO POR PROVEEDOR-LOCAL
				Integer lastBkNumber = 0;
				VendorLocationId vdloId = new VendorLocationId(vendor.getId(), data.getLocationid());

				if (!lastBkMap.containsKey(vdloId)) {
					try {
						VendorLocationW vendorlocation = vendorlocationserver.getById(vdloId);
						lastBkNumber = vendorlocation.getLastnumber() + 1;

					} catch (NotFoundException e) {
						lastBkNumber = 0;
					}

					lastBkMap.put(vdloId, lastBkNumber);
				}

				lastBkNumber = lastBkMap.get(vdloId);

				// VALIDA QUE EL ULTIMO CORRELATIVO SEA MENOR O IGUAL A 99999
				if (lastBkNumber.intValue() > 99999) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El correlativo para el local con código " + loMap.get(data.getLocationid()).getCode() + " ha alcanzado su valor l�mite de 5 dígitos", false);
				}

				// FORMATO LPN: LLL + VVV + UUUUU
				String lpncode = locationcode + vendorcodegen + StringUtils.getInstance().addLeadingZeros(lastBkNumber.toString(), 5);

				data.setBulkid(lpncode);
				finalDataList.add(data);

				// ACTUALIZA �LTIMO N°MERO DE BULTO
				lastBkMap.put(vdloId, lastBkNumber + 1);
			}

			result.setProposedpackinglist(finalDataList.toArray(new ProposedPackingListDataDTO[finalDataList.size()]));
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public ProposedPackingListDataArrayResultDTO doUpdateAllRefDocumentsOfDelivery(UpdateAllRefDocumentsOfDeliveryInitParamDTO initParams) {

		ProposedPackingListDataArrayResultDTO result = new ProposedPackingListDataArrayResultDTO();

		try {
			List<ProposedPackingListDataDTO> reportList = new ArrayList<ProposedPackingListDataDTO>();
			String error = "";

			// Buscar lote por clave primaria
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());

			// ESTADO AGENDADA
			DeliveryStateTypeW agendst = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");

			// Comprueba que el lote est� en estado "Agendada"
			if (!delivery.getCurrentstatetypeid().equals(agendst.getId())) {
				error = "El despacho " + delivery.getNumber() + " no se encuentra en estado 'Agendada' ";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", error, false);
			}

			BulkDetailW[] bulkdetails = bulkdetailserver.getByPropertyAsArray("id.deliveryid", initParams.getDeliveryid());
			for (int i = 0; i < bulkdetails.length; i++) {
				BulkDetailW detail = bulkdetails[i];

				// Valida que el N°mero a reemplazar exista en el PL indicado
				if (detail.getRefdocument().equals(initParams.getRefdocumentnumber())) {
					detail.setRefdocument(initParams.getNewrefdocumentnumber().toString());
					detail = bulkdetailserver.updateBulkDetail(detail);

					// ACTUALIZO REPORTE
					ProposedPackingListDataDTO report = new ProposedPackingListDataDTO();
					report.setOrderid(detail.getOrderid());
					report.setItemid(detail.getItemid());
					report.setLocationid(detail.getLocationid());
					report.setTaxnumber(detail.getRefdocument());

					reportList.add(report);
				}
			}

			result.setPackinglistdatas(reportList.toArray(new ProposedPackingListDataDTO[reportList.size()]));
			return result;

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public void doCalculateTotalBulkDetailOfDelivery(Long deliveryid) {
		Map<String, OrderDeliveryW> odMap = new HashMap<String, OrderDeliveryW>();

		try {
			BulkDetailSummaryDTO[] bulkdetaildsummaries = bulkdetailserver.getBulkDetailSummary(deliveryid);
			OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("id.deliveryid", deliveryid);

			String key = "";
			OrderDeliveryW od = null;

			// Se setean en cero para hacer el cálculo
			for (int i = 0; i < orderdeliveries.length; i++) {
				od = orderdeliveries[i];
				// seteo del departamento si es que no lo tiene
				if (od.getDepartmentid().equals(-1L)) {
					OrderW oc = orderserver.getById(od.getOrderid());
					od.setDepartmentid(oc.getDepartmentid());
				}
				key = od.getDeliveryid() + "_" + od.getOrderid() + "_" + od.getDepartmentid();
				od.setRealbulkcount(0);
				od.setRealpackedunits(0D);
				od.setRealpalletcount(0);
				odMap.put(key, od);
			}
			BulkDetailSummaryDTO bds = null;
			for (int i = 0; i < bulkdetaildsummaries.length; i++) {
				bds = bulkdetaildsummaries[i];
				key = bds.getDeliveryid() + "_" + bds.getOrderid() + "_" + bds.getDepartmentid();
				od = (OrderDeliveryW) odMap.get(key);
				if (!bds.getBoxcount().equals(0)) {
					od.setRealbulkcount(bds.getBoxcount());
					// En orderdelivery no hay distintas filas por tipo de empaque, por lo tanto se deben sumar, sino se
					// puede pisar el valor previo

				}
				if (!bds.getPalletcount().equals(0)) {
					od.setRealpalletcount(bds.getPalletcount());
					// En orderdelivery no hay distintas filas por tipo de empaque, por lo tanto se deben sumar, sino se
					// puede pisar el valor previo

					// no esta sumado por departamento, por lo tanto se suman
				}
				od.setRealpackedunits(od.getRealpackedunits() + bds.getPackedunits());
				odMap.put(key, od);
			}

			Iterator it = odMap.values().iterator();
			while (it.hasNext()) {
				od = (OrderDeliveryW) it.next();
				orderdeliveryserver.updateOrderDelivery(od);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
