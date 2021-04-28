package bbr.b2b.regional.logistic.buyorders.managers.classes;

import java.io.StringWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.codehaus.xfire.util.dom.DOMOutHandler;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.classes.BlockedOrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.DetailDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVAuditServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVUpdateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerRemote;
import bbr.b2b.regional.logistic.buyorders.report.classes.BlockedOrderArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.BlockedOrderDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.BlockedOrderInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.CSVOrderVeVReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DownloadOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.ExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.ExcelOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDiscountArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDiscountDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderTypeDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderTypesResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PredeliveryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PredeliveryDetailDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.SchedulePendingOrderArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.SchedulePendingOrderDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.SchedulePendingOrderInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVAvailableStockInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVAvailableStockWSResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVDeliveryCapacityByZoneInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVDeliveryTimesInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVLeadTimeInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadVeVAvailableStockInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadVeVDeliveryCapacitiesByZoneInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadVeVDeliveryTimesInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadVeVLeadTimeInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAuditDetailDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportResultByItemDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockWSResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataChangeDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataChangeInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDExcelOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDMassDataChangeArrayDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDMassDataChangeDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDUnitaryDataChangeInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVDeliveryCapacitiesByZoneReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVDeliveryCapacitiesByZoneReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVDeliveryTimesReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVDeliveryTimesReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVLeadTimeReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVLeadTimeReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditReportArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateTypeDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.report.classes.BaseResultsDTO;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.QSender;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabrication.ConsultLeadTimeFabricationBindingQSServiceClient;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabrication.ConsultLeadTimeFabricationPortType;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabrication.GetVeVLeadTimeHandler;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm.ConsultLeadTimeFabricationEBMDataAreaRequestType;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm.ConsultLeadTimeFabricationEBMType;
import corp.cencosud.dscl_omnichannel_int1855.consultleadtimefabricationebm.ConsultLeadTimeFabricationResponseEBMType;
import corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationinputmessage.ItemProcessingTime;
import corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationinputmessage.ItemProcessingTime.Skus;
import corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationinputmessage.ItemProcessingTime.Skus.Sku;
import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage.AvailabilityRequest;
import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage.AvailabilityRequest.AvailabilityCriteria;
import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage.AvailabilityRequest.AvailabilityCriteria.ItemNames;
import corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage.Availability.AvailabilityDetails.AvailabilityDetail;
import corp.cencosud.dscl_omnichannel_int1859.consultstock.ConsultStockClient;
import corp.cencosud.dscl_omnichannel_int1859.consultstock.ConsultStockPortType;
import corp.cencosud.dscl_omnichannel_int1859.consultstock.GetVeVAvailableStockHandler;
import corp.cencosud.dscl_omnichannel_int1859.consultstockebm.ConsultStockRequestEBMDataAreaRequestType;
import corp.cencosud.dscl_omnichannel_int1859.consultstockebm.ConsultStockRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1859.consultstockebm.ConsultStockResponseEBMType;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitline.ConsultTransitLineClient;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitline.ConsultTransitLinePortType;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitline.GetVeVDeliveryTimesHandler;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm.ConsultTransitLineRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm.ConsultTransitLineResponseEBMType;
import corp.cencosud.dscl_omnichannel_int1873.consulttransitlineebm.ConsultTransitRequestEBMDataAreaRequestType;
import corp.cencosud.dscl_omnichannel_int1873.transittimeinputmessage.TransitTimeProcessing;
import corp.cencosud.dscl_omnichannel_int1873.transittimeinputmessage.TransitTimeProcessing.Lanes;
import corp.cencosud.dscl_omnichannel_int1873.transittimeinputmessage.TransitTimeProcessing.Lanes.Lane;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier.GetCapacityBySupplier;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier.GetCapacityBySupplierClient;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier.GetVeVCapacityBySupplierHandler;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm.GetCapacityBySupplierDataAreaRequestType;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm.GetCapacityBySupplierRequestEBMType;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierebm.GetCapacityBySupplierResponseEBMType;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest.CapacityProcessing;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest.CapacityProcessing.Capacities;
import corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplieroutputmessage.Capacities.Capacity;
import corp.cencosud.xmlns.core.ebm.common.ebm.EBMHeaderType;
import corp.cencosud.xmlns.core.ebm.common.ebm.EBMTrackingType;
import corp.cencosud.xmlns.core.ebm.common.ebm.ObjectFactory;
import corp.cencosud.xmlns.core.ebm.common.ebm.SenderType;
@Stateless(name = "managers/BuyOrderReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BuyOrderReportManager implements BuyOrderReportManagerLocal, BuyOrderReportManagerRemote {

	private static JAXBContext jc1874 = null;

	private static JAXBContext jc1885 = null;

	private static JAXBContext jc1849 = null;

	private static JAXBContext jc1851 = null;

	private static JAXBContext getJC1874() throws JAXBException {
		if (jc1874 == null)
			jc1874 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1874");
		return jc1874;
	}
	private static JAXBContext getJC1885() throws JAXBException {
		if (jc1885 == null)
			jc1885 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1885");
		return jc1885;
	}

	private static JAXBContext getJC1849() throws JAXBException {
		if (jc1849 == null)
			jc1849 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1849");
		return jc1849;
	}

	private static JAXBContext getJC1851() throws JAXBException {
		if (jc1851 == null)
			jc1851 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1851");
		return jc1851;
	}

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	private static Logger logger = Logger.getLogger(BuyOrderReportManager.class);

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	private LocationServerLocal locationserverlocal;
	
	@EJB
	private DetailDiscountServerLocal detaildiscountserverlocal;

	@EJB
	private OrderDiscountServerLocal orderdiscountserverlocal;

	@EJB
	private OrderServerLocal orderserverlocal;

	@EJB
	private VendorServerLocal vendorserverlocal;

	@EJB
	private OrderDetailServerLocal orderdetailserverlocal;

	@EJB
	private OrderStateTypeServerLocal orderstatetypeserverlocal;

	@EJB
	private OrderStateServerLocal orderstateserverlocal;

	@EJB
	private PreDeliveryDetailServerLocal predeliveryserverlocal;

	@EJB
	private OrderDeliveryServerLocal orderdeliveryserverlocal;
	
	@EJB
	private OrderDeliveryDetailServerLocal orderdeliverydetailserverlocal;
	
	@EJB
	private BlockedOrderServerLocal blockedorderserverlocal;

	@EJB
	private OrderTypeServerLocal ordertypeserver;

	@EJB
	private ItemServerLocal itemserver;

	@EJB
	private VendorItemServerLocal vendoritemserver;

	@EJB
	private VeVAuditServerLocal vevauditserverlocal;

	@EJB
	private VeVUpdateTypeServerLocal vevupdatetypeserverlocal;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public OrderW doCalculateTotalOfOrder(Long orderid) throws NotFoundException, AccessDeniedException, OperationFailedException {

		// Detalle de Oc
		OrderDetailW[] orderdetails = orderdetailserverlocal.getByPropertyAsArray("id.orderid", orderid);
		TreeMap<OrderDetailPK, OrderDetailW> orderdetailMap = new TreeMap<OrderDetailPK, OrderDetailW>();
		OrderDetailPK odpk = null;
		for (int i = 0; i < orderdetails.length; i++) {
			odpk = new OrderDetailPK(orderdetails[i].getOrderid(), orderdetails[i].getItemid());

			orderdetails[i].setUnits(0D);
			orderdetails[i].setTotalneed(0D);

			orderdetails[i].setPendingunits(0D);
			orderdetails[i].setReceivedunits(0D);
			orderdetails[i].setTodeliveryunits(0D);
			orderdetails[i].setTotalpending(0D);
			orderdetails[i].setTotalreceived(0D);
			orderdetails[i].setTotaltodelivery(0D);
			orderdetailMap.put(odpk, orderdetails[i]);
		}

		// Predistribución de Oc
		PreDeliveryDetailW[] predeliveries = predeliveryserverlocal.getByPropertyAsArray("id.orderid", orderid);
		TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW> predeliveryMap = new TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
		PreDeliveryDetailPK predpk = null;
		for (int i = 0; i < predeliveries.length; i++) {
			predpk = new PreDeliveryDetailPK(predeliveries[i].getOrderid(), predeliveries[i].getItemid(), predeliveries[i].getLocationid());
			predeliveries[i].setPendingunits(0D);
			predeliveries[i].setReceivedunits(0D);
			predeliveries[i].setTodeliveryunits(0D);
			predeliveries[i].setTotalpending(0D);
			predeliveries[i].setTotalreceived(0D);
			predeliveries[i].setTotaltodelivery(0D);
			predeliveryMap.put(predpk, predeliveries[i]);
		}

		OrderDeliveryDetailW[] odds = orderdeliverydetailserverlocal.getByPropertyAsArray("id.orderid", orderid);

		OrderDeliveryDetailW odd = null;
		OrderDeliveryW[] orderDeliveries = null;
		OrderDeliveryW orderDelivery = null;
		PreDeliveryDetailW predelivery = null;
		OrderDetailW orderdetail = null;

		double temp = 0;

		// PK: id de delivery, value: despacho de orden
		HashMap<Long, OrderDeliveryW> orderDeliveryMap = new HashMap<Long, OrderDeliveryW>(); 
		for (int i = 0; i < odds.length; i++) {
			odd = odds[i];
			predpk = new PreDeliveryDetailPK(odd.getOrderid(), odd.getItemid(), odd.getLocationid());
			predelivery = predeliveryMap.get(predpk);

			// para obtener el precio
			odpk = new OrderDetailPK(odd.getOrderid(), odd.getItemid());
			orderdetail = orderdetailMap.get(odpk);

			// busco el estado del despacho asociado
			if (!orderDeliveryMap.containsKey(odd.getDeliveryid())) {
				PropertyInfoDTO prop1 = new PropertyInfoDTO("order.id", "orderid", orderid);
				PropertyInfoDTO prop2 = new PropertyInfoDTO("delivery.id", "deliveryid", odd.getDeliveryid());
				orderDeliveries = orderdeliveryserverlocal.getByPropertiesAsArray(prop1, prop2);
				orderDelivery = orderDeliveries[0];
				orderDeliveryMap.put(odd.getDeliveryid(), orderDelivery);
			}
			else {
				orderDelivery = orderDeliveryMap.get(odd.getDeliveryid());
			}

			// LO RECEPCIONADO SE SUMA INDEPENDIENTE DEL CLOSED, POR LO TANTO SE RESTA EN LOS NO CLOSED
			// Si el despacho no esta finalizado,
			if (!orderDelivery.getClosed()) {
				temp = odd.getAvailableunits() - odd.getReceivedunits();
				if (temp < 0) {
					temp = 0;
				}

				predelivery.setTodeliveryunits(predelivery.getTodeliveryunits() + temp);
				predelivery.setTotaltodelivery(predelivery.getTodeliveryunits() * orderdetail.getFinalcost());
			}

			// unidades recibidas. Independiente del closed
			if (odd.getReceivedunits() > 0) {
				predelivery.setReceivedunits(predelivery.getReceivedunits() + odd.getReceivedunits());
				predelivery.setTotalreceived(predelivery.getReceivedunits() * orderdetail.getFinalcost());
			}

			predelivery.setTotalneed(predelivery.getUnits() * orderdetail.getFinalcost());

			predeliveryMap.put(predpk, predelivery);
		}

		OrderW order = orderserverlocal.getById(orderid);
		// order = orderserverlocal.getById(orderid);
		order.setTodeliveryunits(0D);
		order.setReceivedunits(0D);
		order.setPendingunits(0D);
		order.setTotaltodelivery(0D);
		order.setTotalreceived(0D);
		order.setTotalpending(0D);

		// OJO-->RAG
		order.setNeedunits(0D);
		order.setTotalneed(0D);

		// se actualiza predistribución y sumariza para OC y Detalle de OC
		Iterator it = predeliveryMap.values().iterator();
		while (it.hasNext()) {
			predelivery = (PreDeliveryDetailW) it.next();
			// calculo pendiente
			// SIN DATOS TODELIVERYUNITS Y RECEIVEDUNITS NO SE PUEDE CALCULAR

			predelivery.setPendingunits(predelivery.getUnits() - (predelivery.getReceivedunits() + predelivery.getTodeliveryunits()));
			predelivery.setTotalpending(predelivery.getTotalneed() - (predelivery.getTotalreceived() + predelivery.getTotaltodelivery()));
			if (predelivery.getPendingunits() < 0) {
				predelivery.setPendingunits(0D);
				predelivery.setTotalpending(0D);
			}

			predelivery = predeliveryserverlocal.updatePreDeliveryDetail(predelivery);

			// actualizo a nivel de detalle de OC
			odpk = new OrderDetailPK(predelivery.getOrderid(), predelivery.getItemid());
			orderdetail = orderdetailMap.get(odpk);
			orderdetail.setUnits(orderdetail.getUnits() + predelivery.getUnits());
			orderdetail.setTotalneed(orderdetail.getTotalneed() + predelivery.getTotalneed());
			orderdetail.setTodeliveryunits(orderdetail.getTodeliveryunits() + predelivery.getTodeliveryunits());
			orderdetail.setReceivedunits(orderdetail.getReceivedunits() + predelivery.getReceivedunits());
			orderdetail.setPendingunits(orderdetail.getPendingunits() + predelivery.getPendingunits());
			orderdetail.setTotaltodelivery(orderdetail.getTotaltodelivery() + predelivery.getTotaltodelivery());
			orderdetail.setTotalreceived(orderdetail.getTotalreceived() + predelivery.getTotalreceived());
			orderdetail.setTotalpending(orderdetail.getTotalpending() + predelivery.getTotalpending());
			orderdetailMap.put(odpk, orderdetail);

			// actualizo OC
			order.setNeedunits(order.getNeedunits() + predelivery.getUnits());
			order.setTotalneed(order.getTotalneed() + predelivery.getTotalneed());
			order.setTodeliveryunits(order.getTodeliveryunits() + predelivery.getTodeliveryunits());
			order.setReceivedunits(order.getReceivedunits() + predelivery.getReceivedunits());
			order.setPendingunits(order.getPendingunits() + predelivery.getPendingunits());
			order.setTotaltodelivery(order.getTotaltodelivery() + predelivery.getTotaltodelivery());
			order.setTotalreceived(order.getTotalreceived() + predelivery.getTotalreceived());
			order.setTotalpending(order.getTotalpending() + predelivery.getTotalpending());
		}

		// se actualiza Detalle de OC
		Iterator it2 = orderdetailMap.values().iterator();
		while (it2.hasNext()) {
			orderdetail = (OrderDetailW) it2.next();
			orderdetailserverlocal.updateOrderDetail(orderdetail);
		}
		// se actualiza OC
		order = orderserverlocal.updateOrder(order);

		// } //LLAVE DEL FOR
		return order;
	}

	public OrderW[] doAcceptOrders(Long... orderids) throws AccessDeniedException, OperationFailedException, NotFoundException {
		try {
			// Se buscan los tipos de estado de orden Liberada, Aceptada
			OrderStateTypeW releasedstate = orderstatetypeserverlocal.getByPropertyAsSingleResult("code", "LIBERADA");
			OrderStateTypeW acceptedstate = orderstatetypeserverlocal.getByPropertyAsSingleResult("code", "ACEPTADA");
			OrderStateTypeW modifiedstate = orderstatetypeserverlocal.getByPropertyAsSingleResult("code", "MODIFICADA");

			ArrayList<OrderW> ordersList = new ArrayList<OrderW>();
			OrderStateW newstate = null;
			Date now = null;
			for (int i = 0; i < orderids.length; i++) {
				Long orderid = orderids[i];
				// Se busca la orden y se verifica su estado
				OrderW order = orderserverlocal.getById(orderid);
				if (order.getCurrentstatetypeid().equals(releasedstate.getId()) || order.getCurrentstatetypeid().equals(modifiedstate.getId())) {
					logger.info("Aceptando Orden de Compra (ID " + orderid + ").");
					// throw new AccessDeniedException("La orden Nro " + order.getNumber() + " no se encuentra en estado
					// Liberada o Modificada");
					// Si la orden est� en estado Liberada, se actualiza y se agrega un registro al historial
					now = new Date();
					newstate = new OrderStateW();
					newstate.setOrderid(order.getId());
					newstate.setOrderstatetypeid(acceptedstate.getId());
					newstate.setWhen(now);
					// nuevo historial
					newstate = orderstateserverlocal.addOrderState(newstate);
					// se actualiza la OC
					order.setCurrentstatetypeid(acceptedstate.getId());
					order = orderserverlocal.updateOrder(order);

					ordersList.add(order);
					logger.info("La orden (ID " + orderid + ") ha cambiado a estado Aceptada.");
				}
			}
			return (OrderW[]) ordersList.toArray(new OrderW[ordersList.size()]);

		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			throw e;
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			throw e;
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return null;
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndValidCriterium(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		Date now = new Date();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, now, null, null, null, null, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			// Si es consulta desde el filtro seteo información de la paginación
			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, now, null, null, null, null, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndSoonToBeValidCriterium(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		Date now = new Date();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, now, null, null, null, null, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, now, null, null, null, null, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndNumber(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, initParams.getOcnumber(), null, null, null, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, initParams.getOcnumber(), null, null, null, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndShowableOrderStateType(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, initParams.getOrderstatetypeid(), null, null, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, initParams.getOrderstatetypeid(), null, null, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndEmittedRange(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
//			Locale locale = new Locale("es", "CL");
//			Calendar cal = Calendar.getInstance(locale);
//
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndValidRange(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			
			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderReportResultDTO getOrdersByVendorLocationAndExpirationRange(OrderReportInitParamDTO initParams, boolean byFilter) {
		OrderReportResultDTO resultW = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersWs = null;
		OrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParams.getOriginalvendorcode() != null && ! initParams.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParams.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			ordersWs = orderserverlocal.getOrdersByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderserverlocal.getOrdersCountByVendorLocationAndCriterium(vendor.getId(), originalvendorid, locationid, null, null, null, since, until, initParams.getFiltertype());

				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndValidCriterium(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		Date now = new Date();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, now, null, null, null, null, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			// Si es consulta desde el filtro seteo información de la paginación
			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, now, null, null, null, null, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndSoonToBeValidCriterium(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		Date now = new Date();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, now, null, null, null, null, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, now, null, null, null, null, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndNumber(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, initParams.getOcnumber(), null, null, null, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, initParams.getOcnumber(), null, null, null, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndShowableOrderStateType(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, initParams.getOrderstatetypeid(), null, null, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, initParams.getOrderstatetypeid(), null, null, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndEmittedRange(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
//			Locale locale = new Locale("es", "CL");
//			Calendar cal = Calendar.getInstance(locale);
//
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndValidRange(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndExpirationRange(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndCommittedRange(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
//			Date since = DateConverterFactory2.StrToDate(initParams.getSince());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(since);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			since = cal.getTime();
//
//			Date until = DateConverterFactory2.StrToDate(initParams.getUntil());
//			cal.setTime(until);
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, since, until, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndValidCriteriums(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, null, null, null, initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			// Si es consulta desde el filtro seteo información de la paginación
			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, null, null, null, initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public VeVCDOrderReportResultDTO getVeVCDOrdersByVendorLocationAndRequestNumber(VeVCDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVCDOrderReportResultDTO resultW = new VeVCDOrderReportResultDTO();
		VeVCDOrderReportDataDTO[] ordersWs = null;
		VeVCDOrderReportTotalDataDTO totalresult = null;
		int total;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {

			ordersWs = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, null, null, initParams.getRequestnumber(), initParams.getFiltertype(), 0, 0, initParams.getOrderby(), false);

			if (byFilter) {
				totalresult = orderserverlocal.getVeVCDOrdersCountByVendorLocationAndCriterium(initParams.getSalestoreid(), vendor.getId(), locationid, null, null, null, null, null, initParams.getRequestnumber(), initParams.getFiltertype());

				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(1);
				pageInfo.setRows(ordersWs.length);
				pageInfo.setTotalpages(1);
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}
			resultW.setOrders(ordersWs);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderDetailReportResultDTO getOrdersDetailByOrder(OrderDetailReportInitParamDTO initParams, boolean providerUser, boolean byFilter) {
		OrderDetailReportResultDTO resultW = new OrderDetailReportResultDTO();

		OrderDetailReportDataDTO[] ordersdetailsWs = null;
		OrderDetailReportTotalDataDTO totalresult = null;
		OrderReportDataDTO[] orderreport = null;
		int total;
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida proveedor original
		Long originalvendorid = -1L;
		if(! initParams.getOriginalvendorcode().equals("-1")) {
			
			VendorW[] originalvendorArr = null;
			try{
				originalvendorArr = vendorserverlocal.getByPropertyAsArray("code", initParams.getOriginalvendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(originalvendorArr == null || originalvendorArr.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
			}
			originalvendorid = originalvendorArr[0].getId();
		}
		

		
		try {
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getOrderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<OrderW> orders = orderserverlocal.getByProperties(properties);

			if (orders.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
			}
			OrderW order = orders.get(0);

			ordersdetailsWs = orderdetailserverlocal.getOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);
			resultW.setOrderdetail(ordersdetailsWs);
			
			if (byFilter) {
				totalresult = orderdetailserverlocal.getCountOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId());

				// Setear Información de la Paginación
				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersdetailsWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);

				resultW.setTotal(totalresult);
			}

			// SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA
			if (providerUser) {
				Long[] ids = new Long[1];
				ids[0] = initParams.getOrderid();
				doAcceptOrders(ids);
				orderserverlocal.flush();
			}

			orderreport = orderserverlocal.getOrdersByVendorLocationAndCriterium(order.getVendorid(), originalvendorid, order.getDeliverylocationid(), null, order.getNumber(), null, null, null, 2, 0, 0, null, false);
			if (orderreport == null || orderreport.length != 1) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			
			resultW.setOrder(orderreport[0]);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		return resultW;
	}

	public VeVCDOrderDetailReportResultDTO getVeVCDOrdersDetailByOrder(VeVCDOrderDetailReportInitParamDTO initParams, boolean providerUser, boolean byFilter) {
		VeVCDOrderDetailReportResultDTO resultW = new VeVCDOrderDetailReportResultDTO();

		VeVCDOrderDetailReportDataDTO[] ordersdetailsWs = null;
		VeVCDOrderDetailReportTotalDataDTO totalresult = null;
		VeVCDOrderReportDataDTO orderreport = null;
		int total;
		OrderW[] order = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Chequea la existencia del vendor
			try {
				vendorserverlocal.getById(vendor.getId());
			} catch (NotFoundException e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getOrderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<OrderW> orders = orderserverlocal.getByProperties(properties);

			if (orders.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
			}

			ordersdetailsWs = orderdetailserverlocal.getVeVCDOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = orderdetailserverlocal.getCountVeVCDOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId());

				// Setear Información de la Paginación
				int rows = initParams.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(ordersdetailsWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);

				resultW.setTotal(totalresult);
			}

			// SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA
			if (providerUser) {
				Long[] ids = new Long[1];
				ids[0] = initParams.getOrderid();
				order = doAcceptOrders(ids);
				orderserverlocal.flush();
			}

			if (order == null || order.length == 0) {
				order = new OrderW[1];
			}
			order[0] = orderserverlocal.getById(initParams.getOrderid());
			orderreport = orderserverlocal.getVeVCDOrdersByVendorLocationAndCriterium(null, order[0].getVendorid(), order[0].getDeliverylocationid(), null, order[0].getNumber(), null, null, null, null, 2, 0, 0, null, false)[0];

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		resultW.setOrderdetail(ordersdetailsWs);
		resultW.setOrder(orderreport);
		return resultW;
	}

	public PreDeliveryDetailReportResultDTO getPreDeliveryDetailByOrder(OrderDetailReportInitParamDTO initParamDTO, boolean byFilter) {
		PreDeliveryDetailReportResultDTO resultW = new PreDeliveryDetailReportResultDTO();

		PreDeliveryDetailReportDataDTO[] predeliverydetailsWs = null;
		PreDeliveryDetailReportTotalDataDTO totalresult = null;
		int total;
		
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		try {
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(initParamDTO.getVendorid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getOrderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<OrderW> orders = orderserverlocal.getByProperties(properties);

			if (orders.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
			}

			predeliverydetailsWs = predeliveryserverlocal.getPreDeliveryDetailsByOrder(initParamDTO.getOrderid(), vendor.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);

			if (byFilter) {
				totalresult = predeliveryserverlocal.getPreDeliveryDetailsCountByOrder(initParamDTO.getOrderid(), vendor.getId());

				// Setear Información de la Paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(predeliverydetailsWs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);

				resultW.setTotal(totalresult);
			}
		} catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setPredeliverydetail(predeliverydetailsWs);
		return resultW;
	}

	public ExcelOrderReportResultDTO getExcelOrdersReportByOrders(DownloadOrderReportInitParamDTO initParamW, boolean providerUser, boolean byPages) {

		ExcelOrderReportResultDTO resultW = new ExcelOrderReportResultDTO();
		ExcelOrderReportDataDTO[] ordersWs = null;
		OrderW[] orders = null;
		List<OrderReportDataDTO> lista = new ArrayList<OrderReportDataDTO>();
		OrderReportDataDTO orderreport = null;
		OrderReportDataDTO[] orderreports = null;
		int totalRows = 0;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamW.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Chequea la existencia del vendor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserverlocal.getById(initParamW.getVendorId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];

			for (Long orderId : initParamW.getOrderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", orderId);
				properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				List<OrderW> ordersTmp = orderserverlocal.getByProperties(properties);

				if (ordersTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = orderserverlocal.getOrdersReportCountByOrders(initParamW.getOrderids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				ordersWs = orderserverlocal.getExcelOrderReportByOrders(initParamW.getOrderids());

				if (providerUser) {
					orders = doAcceptOrders(initParamW.getOrderids()); // ACEPTO ORDENES SI ES PROVEEDOR
					for (int i = 0; i < orders.length; i++) {
						OrderStateTypeW orderstate = orderstatetypeserverlocal.getById(orders[i].getCurrentstatetypeid());
						orderreport = new OrderReportDataDTO();
						BeanExtenderFactory.copyProperties(orders[i], orderreport);

						orderreport.setOrderid(orders[i].getId());
						orderreport.setOrderstatetypedesc(orderstate.getName());
						orderreport.setOrderstatetypecode(orderstate.getCode());
						orderreport.setTotalunits(orders[i].getNeedunits());
						orderreport.setTotalamount(orders[i].getTotalneed());
						orderreport.setOrdernumber(orders[i].getNumber());

						lista.add(orderreport);
					}
					orderreports = (OrderReportDataDTO[]) lista.toArray(new OrderReportDataDTO[lista.size()]);
				}
			} else {
				resultW.setTotalRows(totalRows);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			resultW.setExcelOrders(ordersWs);
			resultW.setOrders(orderreports);
			resultW.setTotalRows(totalRows);
			resultW.setRetailImported(vendor.getRut().equals("IMP"));
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		
		return resultW;
	}

	public OrderIdsByPagesW getExcelOrdersReportByPages(OrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageranges) {
		OrderIdsByPagesW resultW = new OrderIdsByPagesW();
		OrderIdsByPagesW result = null;
		Date now = new Date();
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParamDTO.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParamDTO.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}

		
		try {
			// Proveedor orignal
			Long originalvendorid = -1L;
			if( initParamDTO.getOriginalvendorcode() != null && ! initParamDTO.getOriginalvendorcode().equals("-1")) {
				VendorW[] origVendorArr = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getOriginalvendorcode());
				if(origVendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");
				}
				originalvendorid = origVendorArr[0].getId();		
			}
			
			Date since = null;
			Date until = null;

			if (initParamDTO.getFiltertype() == 4 || initParamDTO.getFiltertype() == 5 || initParamDTO.getFiltertype() == 6) {
//				since = DateConverterFactory.StrToDate(initParamDTO.getSince());
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(since);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				since = cal.getTime();
//
//				until = DateConverterFactory.StrToDate(initParamDTO.getUntil());
//				cal.setTime(until);
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				until = cal.getTime();
				
				since = Date.from(initParamDTO.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				until = Date.from(initParamDTO.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			}

			// Se consulta por la cantidad de registros que implica el reporte excel para las paginas solicitadas
			// Además se traen los ids de las ordenes correspondientes
			result = orderserverlocal.getOrdersIdsByPages(vendor.getId(), originalvendorid, locationid, now, initParamDTO.getOcnumber(), initParamDTO.getOrderstatetypeid(), since, until, initParamDTO.getRows(), initParamDTO.getFiltertype(), initParamDTO.getOrderby(), pageranges);

			// Se valida la cantidad de registros con el m�ximo permitido
			if (result.getTotalRows() < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {

				// Si es válido se envian ids de ordenes al portal
				resultW.setOrderIds(result.getOrderIds());
				resultW.setTotalRows(result.getTotalRows());
			} else {
				resultW.setTotalRows(result.getTotalRows());
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public SchedulePendingOrderArrayResultDTO getSchedulePendingOrdersByValidStateDate(SchedulePendingOrderInitParamDTO initParamDTO) {
		SchedulePendingOrderArrayResultDTO resultDTO = new SchedulePendingOrderArrayResultDTO();
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateConverterFactory2.StrToDate(initParamDTO.getSince()));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();
			
			cal.setTime(DateConverterFactory2.StrToDate(initParamDTO.getUntil()));
			cal.add(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date until = cal.getTime();
			
			// Validar que la fecha Desde sea anterior o igual a la fecha Hasta
			if (!since.before(until)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1003");
			}
			
			// Validar que la fecha Desde corresponde a lo más X meses atr�s con respecto a la fecha actual
			cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("blockingreportmaxmonths")));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date min = cal.getTime();
			
			if (since.before(min)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La fecha Desde no debe ser anterior a " +
						B2BSystemPropertiesUtil.getProperty("blockingreportmaxmonths") + " meses con respecto a la fecha de hoy", false);
			}
						
			SchedulePendingOrderDTO[] orderdata = orderserverlocal.getSchedulePendingOrdersByValidStateDate(since, until);

			if (orderdata == null || orderdata.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1004");
			}
			
			resultDTO.setOrderdata(orderdata);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BlockedOrderArrayResultDTO getBlockedOrdersByBlockingDate(BlockedOrderInitParamDTO initParamDTO) {
		BlockedOrderArrayResultDTO resultDTO = new BlockedOrderArrayResultDTO();
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateConverterFactory2.StrToDate(initParamDTO.getSince()));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();
			
			cal.setTime(DateConverterFactory2.StrToDate(initParamDTO.getUntil()));
			cal.add(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date until = cal.getTime();
			
			// Validar que la fecha Desde sea anterior o igual a la fecha Hasta
			if (!since.before(until)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1003");
			}
			
			// Validar que la fecha Desde corresponde a lo más X meses atr�s con respecto a la fecha actual
			cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("blockingreportmaxmonths")));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date min = cal.getTime();
			
			if (since.before(min)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La fecha Desde no debe ser anterior a " +
						B2BSystemPropertiesUtil.getProperty("blockingreportmaxmonths") + " meses con respecto a la fecha de hoy", false);
			}
			
			BlockedOrderDTO[] orderdata = blockedorderserverlocal.getBlockedOrdersByBlockingDate(since, until);

			if (orderdata == null || orderdata.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1004");
			}
			
			resultDTO.setOrderdata(orderdata);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	public VeVCDExcelOrderReportResultDTO getExcelVeVCDOrdersReportByOrders(DownloadOrderReportInitParamDTO initParamW, boolean providerUser, boolean byPages) {

		VeVCDExcelOrderReportResultDTO resultW = new VeVCDExcelOrderReportResultDTO();
		VeVCDExcelOrderReportDataDTO[] ordersWs = null;
		OrderW[] orders = null;
		List<VeVCDOrderReportDataDTO> lista = new ArrayList<VeVCDOrderReportDataDTO>();
		VeVCDOrderReportDataDTO orderreport = null;
		VeVCDOrderReportDataDTO[] orderreports = null;
		int totalRows = 0;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamW.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(initParamW.getVendorId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];

			for (Long orderId : initParamW.getOrderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", orderId);
				properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				List<OrderW> ordersTmp = orderserverlocal.getByProperties(properties);

				if (ordersTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = orderserverlocal.getOrdersReportCountByOrders(initParamW.getOrderids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				ordersWs = orderserverlocal.getExcelVeVCDOrderReportByOrders(initParamW.getOrderids());

				if (providerUser) {
					orders = doAcceptOrders(initParamW.getOrderids()); // ACEPTO ORDENES SI ES PROVEEDOR
					for (int i = 0; i < orders.length; i++) {
						OrderStateTypeW orderstate = orderstatetypeserverlocal.getById(orders[i].getCurrentstatetypeid());
						orderreport = new VeVCDOrderReportDataDTO();

						//BeanExtenderFactory.copyProperties(orders[i], orderreport);
						orderreport.setOriginaldeliverydate(LocalDateTime.ofInstant(orders[i].getOriginaldeliverydate().toInstant(), ZoneId.systemDefault()));
						orderreport.setRequestnumber(orders[i].getRequestnumber());
						orderreport.setPendingunits(orders[i].getPendingunits());
						orderreport.setTodeliveryunits(orders[i].getTodeliveryunits());
						orderreport.setReceivedunits(orders[i].getReceivedunits());
						orderreport.setOutreceivedunits(orders[i].getOutreceivedunits());
						orderreport.setTotalpending(orders[i].getTotalpending());
						orderreport.setTotalreceived(orders[i].getTotalreceived());
						orderreport.setTotaltodelivery(orders[i].getTotaltodelivery());
						
						orderreport.setOrderid(orders[i].getId());
						orderreport.setOrderstatetypedesc(orderstate.getName());
						orderreport.setOrderstatetypecode(orderstate.getCode());
						orderreport.setTotalunits(orders[i].getNeedunits());
						orderreport.setTotalamount(orders[i].getTotalneed());
						orderreport.setOrdernumber(orders[i].getNumber());

						lista.add(orderreport);
					}
					orderreports = (VeVCDOrderReportDataDTO[]) lista.toArray(new VeVCDOrderReportDataDTO[lista.size()]);
				}
			} else {
				resultW.setTotalRows(totalRows);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			resultW.setExcelOrders(ordersWs);
			resultW.setOrders(orderreports);
			resultW.setTotalRows(totalRows);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderIdsByPagesW getExcelVeVCDOrdersReportByPages(VeVCDOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageranges) {
		OrderIdsByPagesW resultW = new OrderIdsByPagesW();
		OrderIdsByPagesW result = null;
		Date now = new Date();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		Long locationid = -1L;
		if(! initParamDTO.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserverlocal.getByPropertyAsArray("code", initParamDTO.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getFiltertype() == 5 || initParamDTO.getFiltertype() == 6 || initParamDTO.getFiltertype() == 7 || initParamDTO.getFiltertype() == 8) {
//				since = DateConverterFactory.StrToDate(initParamDTO.getSince());
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(since);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				since = cal.getTime();
//
//				until = DateConverterFactory.StrToDate(initParamDTO.getUntil());
//				cal.setTime(until);
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				until = cal.getTime();

				since = Date.from(initParamDTO.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				until = Date.from(initParamDTO.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			}

			// Se consulta por la cantidad de registros que implica el reporte excel para las paginas solicitadas
			// Además se traen los ids de las ordenes correspondientes
			result = orderserverlocal.getVeVCDOrdersIds(initParamDTO.getSalestoreid(), vendor.getId(), locationid, now, initParamDTO.getOcnumber(), initParamDTO.getOrderstatetypeid(), since, until, initParamDTO.getRequestnumber(), initParamDTO.getFiltertype(), initParamDTO.getOrderby());
			
			// Se valida la cantidad de registros con el m�ximo permitido
			if (result.getTotalRows() < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {

				// Si es válido se envian ids de ordenes al portal
				resultW.setOrderIds(result.getOrderIds());
				resultW.setTotalRows(result.getTotalRows());
			} else {
				resultW.setTotalRows(result.getTotalRows());
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public OrderTypesResultDTO getOrderTypes() {
		OrderTypesResultDTO result = new OrderTypesResultDTO();
		OrderTypeDTO[] ordertypedtos = null;

		try {
			OrderTypeW[] ordertypes = ordertypeserver.getAllAsArray();
			ordertypedtos = new OrderTypeDTO[ordertypes.length];
			DateConverterFactory2.copyProperties(ordertypes, ordertypedtos, OrderTypeW.class, OrderTypeDTO.class);
			result.setOrdertypes(ordertypedtos);
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}
	
	public FileDownloadInfoResultDTO getCSVOrderVeVReport(CSVOrderVeVReportInitParamDTO initParamDTO, Long userId) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		// Valida proveedor
		Long vendorid = -1L;
		if (! initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
				
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try {
			locations = locationserverlocal.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		if (locations == null || locations.length == 0) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
		}
		location = locations[0];
			
		try {
			if (initParamDTO.getFiltertype() == 0 || initParamDTO.getFiltertype() == 1) {
				int rangeDays = RegionalLogisticConstants.getInstance().getMAX_DAYS_VEV_REPORT();
				Date since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
				Date until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());

				Calendar calSince = Calendar.getInstance();
				calSince.setTime(since);
				calSince.add(Calendar.DAY_OF_YEAR, rangeDays);
				Calendar calUntil = Calendar.getInstance();
				calUntil.setTime(until);

				// Validar el rango de fechas
				if (calSince.before(calUntil)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L9001");
				}
				
				// Validar la cantidad de registros a descarga
				int total = orderdetailserverlocal.countCSVOrderVeVReport(initParamDTO.getSalestoreid(), vendorid, initParamDTO.getOrderstatetypeid(), since, until, location.getId(), initParamDTO.getFiltertype());
				if (total > RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_CSV_ROWS()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2105");
				}
				
				resultDTO = orderdetailserverlocal.getCSVOrderVeVReport(initParamDTO.getSalestoreid(), vendorid, initParamDTO.getOrderstatetypeid(), since, until, location.getId(), initParamDTO.getFiltertype(), userId);
			}

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	public OrderDiscountArrayResultDTO getOrderDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		OrderDiscountArrayResultDTO result = new OrderDiscountArrayResultDTO();
		OrderDiscountDTO[] orderdiscount;
		try {
			orderdiscount = orderdiscountserverlocal.getOrderDiscountByOrders(orderids);
			result.setOrderdiscount(orderdiscount);
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public DetailDiscountArrayResultDTO getDetailDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		DetailDiscountArrayResultDTO result = new DetailDiscountArrayResultDTO();
		DetailDiscountDTO[] detaildiscount;
		try {
			detaildiscount = detaildiscountserverlocal.getDetailDiscountByOrders(orderids);
			result.setDetaildiscount(detaildiscount);
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public PredeliveryDetailArrayResultDTO getPredeliveryDetailByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		PredeliveryDetailArrayResultDTO result = new PredeliveryDetailArrayResultDTO();
		PredeliveryDetailDTO[] predeliverydetail;
		try {
			predeliverydetail = predeliveryserverlocal.getPredeliveryDetailByOrders(orderids);
			result.setPredeliverydetail(predeliverydetail);
			return result;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public DetailDiscountReportResultDTO getDetailDiscountsOfOrderDetail(DetailDiscountInitParamDTO initParamDTO) {

		DetailDiscountReportResultDTO resultDTO = new DetailDiscountReportResultDTO();

		ItemW item = null;
		VendorItemW vendoritem = null;
		OrderDetailW orderdetail = null;
		DetailDiscountReportDataDTO[] detaildiscounts;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		
		try {
			item = itemserver.getById(initParamDTO.getItemid());

			if (item == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L445");
			}

			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			properties[1] = new PropertyInfoDTO("item.id", "item", initParamDTO.getItemid());
			List<VendorItemW> vendoritems = vendoritemserver.getByProperties(properties);

			if (vendoritems.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L445");
			}

			vendoritem = vendoritems.get(0);

			properties[0] = new PropertyInfoDTO("order.id", "order", initParamDTO.getOrderid());
			properties[1] = new PropertyInfoDTO("item.id", "item", initParamDTO.getItemid());
			List<OrderDetailW> orderdetails = orderdetailserverlocal.getByProperties(properties);

			if (orderdetails.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L445");
			}

			orderdetail = orderdetails.get(0);

			detaildiscounts = detaildiscountserverlocal.getDetailDiscountsOfOrderDetail(initParamDTO.getOrderid(), initParamDTO.getItemid(), initParamDTO.getOrderby());

			resultDTO.setItemdescription(item.getName());
			resultDTO.setItemsku(item.getInternalcode());
			resultDTO.setVendoritemcode(vendoritem.getVendoritemcode());
			resultDTO.setListcost(orderdetail.getListcost());
			resultDTO.setFinalcost(orderdetail.getFinalcost());
			resultDTO.setDetaildiscounts(detaildiscounts);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public VeVDeliveryTimesReportResultDTO getVeVDeliveryTimesReport(String vendorcode) {
		VeVDeliveryTimesReportResultDTO result = new VeVDeliveryTimesReportResultDTO();
		VeVDeliveryTimesReportDataDTO[] report = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		//Long vendorid = vendors[0].getId();
		vendor = vendors[0];
		try {
			//VendorW vendor = vendorserverlocal.getById(vendorid);

			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			ConsultTransitLineRequestEBMType param = doFillParamDeliveryTimes(vendor);
			ConsultTransitLineClient client = new ConsultTransitLineClient(B2BSystemPropertiesUtil.getProperty("WSDL_EOM_DELIVERYTIMES").trim());
			ConsultTransitLinePortType service = client.getConsultTransitLinePort();

			// JPE 20181002
			// Logger
			Client clientLogger = Client.getInstance(service);
			clientLogger.addInHandler(new DOMInHandler());
			clientLogger.addInHandler(new GetVeVDeliveryTimesHandler());
			clientLogger.addOutHandler(new DOMOutHandler());
			clientLogger.addOutHandler(new GetVeVDeliveryTimesHandler());
			
			ConsultTransitLineResponseEBMType response = service.consultTransitLine(param);
			if (!"0".equals(response.getReturnCode())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1000", response.getReturnMessage());
			}

			// Se obtiene el elemento 0 ya que es solo un proveedor
			List<corp.cencosud.dscl_omnichannel_int1873.transittimeoutputmessage.TransitTimeProcessing.Lanes.Lane> detailsreponse = response.getDataArea().getTransitTimeProcessing().getLanes().getLane();
			List<corp.cencosud.dscl_omnichannel_int1873.transittimeoutputmessage.TransitTimeProcessing.Lanes.Lane.Details.Detail> details = detailsreponse.get(0).getDetails().getDetail();

			report = new VeVDeliveryTimesReportDataDTO[details.size()];
			VeVDeliveryTimesReportDataDTO detail = null;
			for (int i = 0; i < details.size(); i++) {
				detail = new VeVDeliveryTimesReportDataDTO();
				detail.setCommune(details.get(i).getComunaName());
				detail.setCommunecode(details.get(i).getLaneId().toString()); // laneId
				detail.setTime(details.get(i).getTime().intValue());
				report[i] = detail;
			}
			result.setTimesreport(report);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	private GetCapacityBySupplierRequestEBMType doFillParamCapacitiesByZones(VendorW vendor) throws Exception {

		GetCapacityBySupplierRequestEBMType param = new GetCapacityBySupplierRequestEBMType();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		SenderType st = new SenderType();
		st.setApplication("BBR00");
		st.setCountry("CL");
		st.setBusinessUnit("DS");
		
		EBMHeaderType ebmht = new EBMHeaderType();
		ebmht.setCreationDateTime(date2); // <ebm:CreationDateTime>2018-09-28T11:15:00.000+02:00</ebm:CreationDateTime>
		ebmht.setEBMID("");
		ebmht.setSender(st);
		ebmht.setTarget("MHTDSCL");
		
		param.setEBMHeader(ebmht);
		
		ObjectFactory factory = new ObjectFactory();
		EBMTrackingType ebt = new EBMTrackingType();
		ebt.setIntegrationCode(factory.createEBMTrackingTypeIntegrationCode("INT1884"));
		ebt.setReferenceID(factory.createEBMTrackingTypeReferenceID("85100100"));

		corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest.CapacityProcessing.Capacities.Capacity capacityinput = new corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest.CapacityProcessing.Capacities.Capacity();
		capacityinput.setDirection("OUTBOUND");
		capacityinput.setJornada("TH");
		capacityinput.setOriginFacility(vendor.getCode());

		Capacities values = new Capacities();
		List<corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage.CapacityRequest.CapacityProcessing.Capacities.Capacity> caplist = values.getCapacity();
		caplist.add(capacityinput);
		
		CapacityProcessing cprocessing = new CapacityProcessing();
		cprocessing.setCapacities(values);

		CapacityRequest crequest = new CapacityRequest();
		crequest.setCapacityProcessing(cprocessing);
		
		GetCapacityBySupplierDataAreaRequestType areatype = new GetCapacityBySupplierDataAreaRequestType();
		areatype.setCapacityRequest(crequest);
		
		param.setDataArea(areatype);
		
		return param;
	}

	private ConsultTransitLineRequestEBMType doFillParamDeliveryTimes(VendorW vendor) throws Exception {

		ConsultTransitLineRequestEBMType param = new ConsultTransitLineRequestEBMType();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		SenderType st = new SenderType();
		st.setApplication("BBR00");
		st.setCountry("CL");
		st.setBusinessUnit("DS");
		
		EBMHeaderType ebmht = new EBMHeaderType();
		ebmht.setCreationDateTime(date2);
		ebmht.setEBMID("");
		ebmht.setSender(st);
		ebmht.setTarget("MHTDSCL");
		
		param.setEBMHeader(ebmht);

		ObjectFactory factory = new ObjectFactory();
		EBMTrackingType ebt = new EBMTrackingType();
		ebt.setIntegrationCode(factory.createEBMTrackingTypeIntegrationCode("INT1873"));
		
		Lane lane = new Lane();
		lane.setOriginFacilityAliasId(BigInteger.valueOf(Long.valueOf(vendor.getCode())));
		
		Lanes lanes = new Lanes();
		List<Lane> laneslist = lanes.getLane();
		laneslist.add(lane);

		TransitTimeProcessing ttime = new TransitTimeProcessing();
		ttime.setLanes(lanes);

		ConsultTransitRequestEBMDataAreaRequestType areatype = new ConsultTransitRequestEBMDataAreaRequestType();
		areatype.setTransitTimeProcessing(ttime);
		
		param.setDataArea(areatype);
		
		return param;
	}

	public VeVDeliveryTimesReportResultDTO doUpdateVeVDeliveryTimes(UpdateVeVDeliveryTimesInitParamDTO initParams) {

		VeVDeliveryTimesReportResultDTO result = new VeVDeliveryTimesReportResultDTO();
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
		String mensaje = "";

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

			List<String> rowsKeys = new ArrayList<String>();
			String rowKey = "";

			List<VeVDeliveryTimesReportDataDTO> dataToUpdate = new ArrayList<VeVDeliveryTimesReportDataDTO>();
			List<Integer> preUpdateData = new ArrayList<Integer>();

			VeVDeliveryTimesReportDataDTO[] dataParam = initParams.getData();
			//VendorW vendor = vendorserverlocal.getById(vendor.getId());

			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			VeVDeliveryTimesReportResultDTO response = getVeVDeliveryTimesReport(vendor.getRut());
			VeVDeliveryTimesReportDataDTO[] datasws = response.getTimesreport();

			// Mapa para consultar si los parametros son consistentes
			HashMap<Long, VeVDeliveryTimesReportDataDTO> dataswsMap = new HashMap<Long, VeVDeliveryTimesReportDataDTO>();
			for (int i = 0; i < datasws.length; i++) {
				dataswsMap.put(Long.valueOf(datasws[i].getCommunecode()), datasws[i]);
			}

			// COMPARAR CON LOS DATOS ACTUALIZADOS
			VeVDeliveryTimesReportDataDTO dataws = null;
			for (int i = 0; i < dataParam.length; i++) {
				dataws = dataswsMap.get(Long.valueOf(dataParam[i].getCommunecode()));

				if (dataParam[i].getTime() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 en la comuna " + dataParam[i].getCommunecode();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}

				if (dataws == null) {
					mensaje = "Comuna " + dataParam[i].getCommunecode() + " no se encuentra para el proveedor";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}

				// VALIDA QUE NO HAYA FILAS REPETIDAS
				rowKey = dataParam[i].getCommunecode().toUpperCase();
				if (rowsKeys.contains(rowKey)) {
					mensaje = "La fila " + (i + 2) + ": El código " + dataParam[i].getCommunecode() + " se est� subiendo más de una vez";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				} else
					rowsKeys.add(rowKey);

				// se envian solo los que cambian
				if (dataws.getTime().intValue() != dataParam[i].getTime().intValue()) {
					dataToUpdate.add(dataParam[i]);
					preUpdateData.add(dataws.getTime());
				}
			}

			if (baseresultlist.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));

				// para conservar el desarrollo que recibia solo un error
				result.setStatuscode(baseresultlist.get(0).getStatuscode());
				result.setStatusmessage(baseresultlist.get(0).getStatusmessage());

				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
			}

			// ENVIAR MENSAJE 1874
			String content = null;
			if (dataToUpdate.size() > 0)
				content = doSendInt1874(dataToUpdate, vendor);

			// Se guarda un registro de la actualización en la tabla para �Auditor�a VeV"
			if (content != null) {
				VeVUpdateTypeW vevUpdateType = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_TIMES");
				Date now = new Date();
				for (int i = 0; i < dataToUpdate.size(); i++) {

					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParams.getUsername());
					vevAudit.setUsertype(initParams.getUsertype());
					vevAudit.setUserenterprisecode(initParams.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParams.getUserenterprisename());
					vevAudit.setVendorid(vendor.getId());
					vevAudit.setInterfacecontent(content);
					vevAudit.setVevupdatetypeid(vevUpdateType.getId());

					vevAudit.setInitialdata(preUpdateData.get(i).toString());
					vevAudit.setFinaldata(dataToUpdate.get(i).getTime().toString());
					vevAudit.setItem("Comuna");
					vevAudit.setItemvalue(dataToUpdate.get(i).getCommune());

					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1"));
		}

		return result;
	}

	public BaseResultsDTO doUploadVeVDeliveryTimes(UploadVeVDeliveryTimesInitParamDTO initParams) {

		BaseResultsDTO result = new BaseResultsDTO();

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
			UpdateVeVDeliveryTimesInitParamDTO paramUpdate = new UpdateVeVDeliveryTimesInitParamDTO();
			paramUpdate.setData(initParams.getTimeslist());
			//paramUpdate.setVendorid(vendor.getId());
			paramUpdate.setVendorcode(vendor.getRut());
			paramUpdate.setUserenterprisecode(initParams.getUserenterprisecode());
			paramUpdate.setUserenterprisename(initParams.getUserenterprisename());
			paramUpdate.setUsername(initParams.getUsername());
			paramUpdate.setUsertype(initParams.getUsertype());

			VeVDeliveryTimesReportResultDTO resultUpdate = doUpdateVeVDeliveryTimes(paramUpdate);
			result.setBaseresults(resultUpdate.getValidationerrors());
			result.setStatuscode(resultUpdate.getStatuscode());
			result.setStatusmessage(resultUpdate.getStatusmessage());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public VeVDeliveryCapacitiesByZoneReportResultDTO getVeVDeliveryCapacitiesByZoneReport(String vendorcode) {
		VeVDeliveryCapacitiesByZoneReportResultDTO result = new VeVDeliveryCapacitiesByZoneReportResultDTO();
		VeVDeliveryCapacitiesByZoneReportDataDTO[] report = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			//VendorW vendor = vendorserverlocal.getById(vendorid);

			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			GetCapacityBySupplierRequestEBMType param = doFillParamCapacitiesByZones(vendor);
			GetCapacityBySupplierClient client = new GetCapacityBySupplierClient(B2BSystemPropertiesUtil.getProperty("WSDL_EOM_CAPACITIES").trim());
			GetCapacityBySupplier service = client.getGetCapacityBySupplierPort();
			
			// JPE 20181002
			// Logger
			Client clientLogger = Client.getInstance(service);
			clientLogger.addInHandler(new DOMInHandler());
			clientLogger.addInHandler(new GetVeVCapacityBySupplierHandler());
			clientLogger.addOutHandler(new DOMOutHandler());
			clientLogger.addOutHandler(new GetVeVCapacityBySupplierHandler());
			
			GetCapacityBySupplierResponseEBMType response = service.getCapacityBySupplier(param);
			if (!"0".equals(response.getReturnCode())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1000", response.getReturnMessage());
			}

			List<Capacity> detailsreponse = response.getDataArea().getCapacities().getCapacity();
			report = new VeVDeliveryCapacitiesByZoneReportDataDTO[detailsreponse.size()];

			VeVDeliveryCapacitiesByZoneReportDataDTO detail = null;
			for (int i = 0; i < detailsreponse.size(); i++) {
				detail = new VeVDeliveryCapacitiesByZoneReportDataDTO();
				detail.setMonday(detailsreponse.get(i).getMondayCapacity());
				detail.setFriday(detailsreponse.get(i).getFridayCapacity());
				detail.setSaturday(detailsreponse.get(i).getSaturdayCapacity());
				detail.setSunday(detailsreponse.get(i).getSundayCapacity());
				detail.setThursday(detailsreponse.get(i).getThursdayCapacity());
				detail.setTuesday(detailsreponse.get(i).getTuesdayCapacity());
				detail.setWednesday(detailsreponse.get(i).getWednesdayCapacity());
				detail.setZone(detailsreponse.get(i).getZona());
				detail.setZoneid(detailsreponse.get(i).getZonaAgendaID());

				report[i] = detail;
			}
			result.setCapacityreport(report);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	public VeVDeliveryCapacitiesByZoneReportResultDTO doUpdateVeVDeliveryCapacityByZone(UpdateVeVDeliveryCapacityByZoneInitParamDTO initParams) {
		VeVDeliveryCapacitiesByZoneReportResultDTO result = new VeVDeliveryCapacitiesByZoneReportResultDTO();
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
		String mensaje = "";
		
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

			List<String> rowsKeys = new ArrayList<String>();
			String rowKey = "";

			
			
			
			//VendorW vendor = vendorserverlocal.getById(vendor.getId());

			VeVDeliveryCapacitiesByZoneReportDataDTO[] dataParam = initParams.getData();
			List<VeVDeliveryCapacitiesByZoneReportDataDTO> dataToUpdate = new ArrayList<VeVDeliveryCapacitiesByZoneReportDataDTO>();
			List<VeVDeliveryCapacitiesByZoneReportDataDTO> preUpdateData = new ArrayList<VeVDeliveryCapacitiesByZoneReportDataDTO>();

			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			VeVDeliveryCapacitiesByZoneReportResultDTO response = getVeVDeliveryCapacitiesByZoneReport(vendor.getRut());
			VeVDeliveryCapacitiesByZoneReportDataDTO[] datasws = response.getCapacityreport();

			// Mapa para consultar si los parametros son consistentes
			HashMap<String, VeVDeliveryCapacitiesByZoneReportDataDTO> dataswsMap = new HashMap<String, VeVDeliveryCapacitiesByZoneReportDataDTO>();
			for (int i = 0; i < datasws.length; i++) {
				dataswsMap.put(datasws[i].getZone(), datasws[i]);
			}

			for (int i = 0; i < dataParam.length; i++) {
				// COMPARAR CON LOS DATOS ACTUALIZADOS
				VeVDeliveryCapacitiesByZoneReportDataDTO dataws = dataswsMap.get(dataParam[i].getZone());

				// seteo el ID ya que no viene desde el flex
				dataParam[i].setZoneid(dataws.getZoneid());

				if (dataParam[i].getMonday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Lunes en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getTuesday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Martes en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getWednesday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Mi�rcoles en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getThursday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Jueves en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getFriday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Viernes en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getSaturday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el S�bado en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				if (dataParam[i].getSunday() > 99999999) {
					mensaje = "Debe ingresar valores menores o iguales a 99.999.999 el Domingo en la Zona " + dataParam[i].getZone();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}

				if (dataws == null) {
					mensaje = "Zona " + dataParam[i].getZone() + " no se encuentra para el proveedor";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}

				// VALIDA QUE NO SE REPITAN LAS FILAS
				rowKey = dataParam[i].getZone().toUpperCase();
				if (rowsKeys.contains(rowKey)) {
					mensaje = "La fila " + (i + 2) + ": La zona " + dataParam[i].getZone() + " se est� subiendo más de una vez";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				} else
					rowsKeys.add(rowKey);

				// se envian solo los que cambian
				if (dataws.getFriday().intValue() != dataParam[i].getFriday().intValue() || dataws.getMonday().intValue() != dataParam[i].getMonday().intValue() || dataws.getSaturday().intValue() != dataParam[i].getSaturday().intValue()
						|| dataws.getSunday().intValue() != dataParam[i].getSunday().intValue() || dataws.getThursday().intValue() != dataParam[i].getThursday().intValue() || dataws.getTuesday().intValue() != dataParam[i].getTuesday().intValue()
						|| dataws.getWednesday().intValue() != dataParam[i].getWednesday().intValue()

				)
					dataToUpdate.add(dataParam[i]);
				for (int a = 0; a < datasws.length; a++) {
					if (datasws[a].getZone().equalsIgnoreCase(dataParam[i].getZone())) {
						preUpdateData.add(datasws[a]);
					}
				}

			}

			if (baseresultlist.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));

				// para conservar el desarrollo que recibia solo un error
				result.setStatuscode(baseresultlist.get(0).getStatuscode());
				result.setStatusmessage(baseresultlist.get(0).getStatusmessage());

				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
			}

			// ENVIAR MENSAJE 1885
			String content = null;
			if (dataToUpdate.size() > 0)
				content = doSendInt1885(dataToUpdate, vendor);

			// Se guarda un registro de la actualización en la tabla para �Auditor�a VeV"

			if (content != null) {
				VeVUpdateTypeW vevUpdateType = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_CAPACITIES");
				Date now = new Date();
				for (int i = 0; i < dataToUpdate.size(); i++) {

					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParams.getUsername());
					vevAudit.setUsertype(initParams.getUsertype());
					vevAudit.setUserenterprisecode(initParams.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParams.getUserenterprisename());
					vevAudit.setVendorid(vendor.getId());
					vevAudit.setInterfacecontent(content);
					vevAudit.setVevupdatetypeid(vevUpdateType.getId());

					vevAudit.setInitialdata("LU:" + preUpdateData.get(i).getMonday() + " - MA:" + preUpdateData.get(i).getTuesday() + " - MI:" + preUpdateData.get(i).getWednesday() + " - JU:" + preUpdateData.get(i).getTuesday() + " - VI:" + preUpdateData.get(i).getFriday() + " - SA:"
							+ preUpdateData.get(i).getSaturday() + " - DO:" + preUpdateData.get(i).getSunday());
					vevAudit.setFinaldata("LU:" + dataToUpdate.get(i).getMonday() + " - MA:" + dataToUpdate.get(i).getTuesday() + " - MI:" + dataToUpdate.get(i).getWednesday() + " - JU:" + dataToUpdate.get(i).getThursday() + " - VI:" + dataToUpdate.get(i).getFriday() + " - SA:"
							+ dataToUpdate.get(i).getSaturday() + " - DO:" + dataToUpdate.get(i).getSunday());
					vevAudit.setItem("Zona");
					vevAudit.setItemvalue(dataToUpdate.get(i).getZone());

					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1"));
		}
		
		return result;
	}

	public BaseResultsDTO doUploadVeVDeliveryCapacitiesByZone(UploadVeVDeliveryCapacitiesByZoneInitParamDTO initParams) {

		BaseResultsDTO result = new BaseResultsDTO();

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
			UpdateVeVDeliveryCapacityByZoneInitParamDTO paramUpdate = new UpdateVeVDeliveryCapacityByZoneInitParamDTO();
			paramUpdate.setData(initParams.getCapacitylist());
			paramUpdate.setVendorcode(vendor.getRut());
			paramUpdate.setUserenterprisecode(initParams.getUserenterprisecode());
			paramUpdate.setUserenterprisename(initParams.getUserenterprisename());
			paramUpdate.setUsername(initParams.getUsername());
			paramUpdate.setUsertype(initParams.getUsertype());

			VeVDeliveryCapacitiesByZoneReportResultDTO resultUpdate = doUpdateVeVDeliveryCapacityByZone(paramUpdate);
			result.setBaseresults(resultUpdate.getValidationerrors());
			result.setStatuscode(resultUpdate.getStatuscode());
			result.setStatusmessage(resultUpdate.getStatusmessage());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public VeVAvailableStockReportResultDTO getVeVAvailableStockReport(String vendorcode) {
		
		VeVAvailableStockReportResultDTO result = new VeVAvailableStockReportResultDTO();
		VeVAvailableStockReportDataDTO[] report = null;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			VendorItemDataW[] items = itemserver.getItemsOfVendorAndVeV(vendor.getId());

			if (items.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000", "No se han encontrado productos configurados como venta en verde para esta consulta");
			}
			
			HashMap<String, VendorItemDataW> itemsMap = new HashMap<String, VendorItemDataW>();
			for (int i = 0; i < items.length; i++) {
				itemsMap.put(items[i].getInternalcode(), items[i]);
			}

			int chunksize = Integer.valueOf(B2BSystemPropertiesUtil.getProperty("EOM_STOCK_CHUNKSIZE"));
			Object[] splititems = ClassUtilities.SplitArray(items, VendorItemDataW.class, chunksize);
			List<AvailabilityDetail> detailsreponse = new ArrayList<AvailabilityDetail>();
			for (int i = 0; i < splititems.length; i++) {
				VendorItemDataW[] itemsblock = (VendorItemDataW[]) splititems[i];
				if (itemsblock.length == 0) {
					continue;
				}
				
				// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
				ConsultStockRequestEBMType param = doFillParamAvailableStock(itemsblock);
				ConsultStockClient client = new ConsultStockClient(B2BSystemPropertiesUtil.getProperty("WSDL_EOM_STOCK").trim());
				ConsultStockPortType service = client.getConsultStockPort();
				
				// JPE 20181002
				// Logger
				Client clientLogger = Client.getInstance(service);
				clientLogger.addInHandler(new DOMInHandler());
				clientLogger.addInHandler(new GetVeVAvailableStockHandler());
				clientLogger.addOutHandler(new DOMOutHandler());
				clientLogger.addOutHandler(new GetVeVAvailableStockHandler());
				
				ConsultStockResponseEBMType response = service.consultStock(param);
				
				// JPE 20180921
				if ("0".equals(response.getReturnCode())) { // Respuesta correcta
					detailsreponse.addAll(response.getDataArea().getAvailability().getAvailabilityDetails().getAvailabilityDetail());
				}
				else if ("La vista no contiene inventario.".equals(response.getReturnMessage())) { // Respuesta "La vista no contiene inventario"
					; // deber�a usarse un código (ver con Paris)
				}
				else { // Respuesta con errores
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1000", response.getReturnMessage());
				}
			}
			
			VeVAvailableStockReportDataDTO detail = null;
			VendorItemDataW itemdata = null;

			List<VeVAvailableStockReportDataDTO> reportList = new ArrayList<VeVAvailableStockReportDataDTO>();
			ArrayList<String> itemsB2B = new ArrayList<String>(itemsMap.keySet());
			ArrayList<String> itemsParis = new ArrayList<String>();
			for (int i = 0; i < detailsreponse.size(); i++) {
				detail = new VeVAvailableStockReportDataDTO();
				itemdata = itemsMap.get(detailsreponse.get(i).getItemName());

				detail.setItemsku(detailsreponse.get(i).getItemName());
				detail.setStock(Integer.valueOf(detailsreponse.get(i).getAtcQuantity()));

				if (itemdata != null) {
					detail.setItemdescription(itemdata.getName());
					detail.setVendorcode(itemdata.getVendoritemcode());
				}
				reportList.add(detail);
				itemsParis.add(detailsreponse.get(i).getItemName());
			}

			// Completar items que est�n en B2b pero no en Paris
			itemsB2B.removeAll(itemsParis); // En itemsB2B quedan solo los que no est�n en Paris
			for (int i = 0; i < itemsB2B.size(); i++) {
				detail = new VeVAvailableStockReportDataDTO();
				itemdata = itemsMap.get(itemsB2B.get(i));
				detail.setItemsku(itemdata.getInternalcode());
				detail.setItemdescription(itemdata.getName());
				detail.setVendorcode(itemdata.getVendoritemcode());
				detail.setStock(Integer.valueOf(0));
				reportList.add(detail);
			}

			report = reportList.toArray(new VeVAvailableStockReportDataDTO[reportList.size()]);

			result.setStockreport(report);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	private ConsultStockRequestEBMType doFillParamAvailableStock(ItemW[] items) throws Exception {

		ConsultStockRequestEBMType param = new ConsultStockRequestEBMType();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		SenderType st = new SenderType();
		st.setApplication("BBR00");
		st.setCountry("CL");
		st.setBusinessUnit("DS");
		
		EBMHeaderType ebmht = new EBMHeaderType();
		ebmht.setCreationDateTime(date2);
		ebmht.setEBMID("");
		ebmht.setSender(st);
		ebmht.setTarget("MHTDSCL");
		
		param.setEBMHeader(ebmht);
		
		ObjectFactory factory = new ObjectFactory();
		EBMTrackingType ebt = new EBMTrackingType();
		ebt.setIntegrationCode(factory.createEBMTrackingTypeIntegrationCode("INT1859"));
		ebt.setReferenceID(factory.createEBMTrackingTypeReferenceID("334727005"));
		
		// Listado de productos a consultar
		ItemNames itemnames = new ItemNames();
		List<String> itemnameslist = itemnames.getItemName();
		for (int i = 0; i < items.length; i++) {
			itemnameslist.add(items[i].getInternalcode());
		}

		AvailabilityCriteria avcrit = new AvailabilityCriteria();
		avcrit.setItemNames(itemnames);
		
		AvailabilityRequest areq = new AvailabilityRequest();
		areq.setViewName("B2B");
		areq.setAvailabilityCriteria(avcrit);
				
		ConsultStockRequestEBMDataAreaRequestType areatype = new ConsultStockRequestEBMDataAreaRequestType();
		areatype.setAvailabilityRequest(areq);
		
		param.setDataArea(areatype);

		return param;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public VeVAvailableStockReportResultDTO doUpdateVeVAvailableStock(UpdateVeVAvailableStockInitParamDTO initParams) {
		VeVAvailableStockReportResultDTO result = new VeVAvailableStockReportResultDTO();
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
		List<VeVAvailableStockReportResultByItemDTO> resultByItem = new ArrayList<VeVAvailableStockReportResultByItemDTO>();
		
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

			List<String> rowsKeys = new ArrayList<String>();
			String rowKey = "";

		//	VendorW vendor = vendorserverlocal.getById(vendor.getId());

			String mensaje = "";
			VeVAvailableStockReportDataDTO[] dataParam = initParams.getData();
			List<VeVAvailableStockReportDataDTO> dataToUpdate = new ArrayList<VeVAvailableStockReportDataDTO>();
			List<VeVAvailableStockReportDataDTO> preUpdateData = new ArrayList<VeVAvailableStockReportDataDTO>();
			List<VeVAvailableStockReportDataDTO> updateData = new ArrayList<VeVAvailableStockReportDataDTO>();
			HashMap<String, String> tipoAjusteMap = new HashMap<String, String>();

			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			VeVAvailableStockReportResultDTO response = getVeVAvailableStockReport(vendor.getRut());
			// Agregado para evitar errores cuando el WS revuelve nulo MHE
			if (response != null && response.getStockreport() != null) {
				VeVAvailableStockReportDataDTO[] datasws = response.getStockreport();

				// Mapa para consultar si los parametros son consistentes
				HashMap<String, VeVAvailableStockReportDataDTO> dataswsMap = new HashMap<String, VeVAvailableStockReportDataDTO>();
				for (int i = 0; i < datasws.length; i++) {
					dataswsMap.put(datasws[i].getItemsku(), datasws[i]);
				}

				// COMPARAR CON LOS DATOS ACTUALIZADOS
				VeVAvailableStockReportDataDTO dataws = null;

				int diff = 0;
				for (int i = 0; i < dataParam.length; i++) {
					if (dataParam[i].getStock() > 99999999) {
						mensaje = "Debe ingresar valores menores o iguales a 99.999.999, Producto " + dataParam[i].getItemsku();
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
						resultByItem.add(new VeVAvailableStockReportResultByItemDTO(dataParam[i].getItemsku(), mensaje, "-1"));
					}

					// Valida que no se repitan las filas
					rowKey = dataParam[i].getItemsku().toUpperCase();
					if (rowsKeys.contains(rowKey)) {
						mensaje = "La fila " + (i + 2) + ": La SKU " + dataParam[i].getItemsku() + " se est� subiendo más de una vez";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
						resultByItem.add(new VeVAvailableStockReportResultByItemDTO(dataParam[i].getItemsku(), mensaje, "-1"));
					}
					else {
						rowsKeys.add(rowKey);
					}

					dataws = dataswsMap.get(dataParam[i].getItemsku());
					
					if (dataws == null) {
						mensaje = "Producto " + dataParam[i].getItemsku() + " no existe para el proveedor";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
						resultByItem.add(new VeVAvailableStockReportResultByItemDTO(dataParam[i].getItemsku(), mensaje, "-1"));
					}
					else {
						// Se envían solo los que cambian
						if (dataws.getStock().intValue() != dataParam[i].getStock().intValue()) {
							// valor absoluto de la resta
							diff = dataws.getStock().intValue() - dataParam[i].getStock().intValue();
							if (diff < 0) {
								diff = diff * -1;
							}

							if (dataws.getStock().intValue() < dataParam[i].getStock().intValue()) {
								tipoAjusteMap.put(dataParam[i].getItemsku(), "A");
							}
							else {
								tipoAjusteMap.put(dataParam[i].getItemsku(), "S");
							}

							for (int a = 0; a < datasws.length; a++) {
								if (datasws[a].getItemsku().equalsIgnoreCase(dataParam[i].getItemsku())) {
									preUpdateData.add(datasws[a]);
									VeVAvailableStockReportDataDTO aux = new VeVAvailableStockReportDataDTO();
									BeanExtenderFactory.copyProperties(dataParam[i], aux);
									dataToUpdate.add(dataParam[i]);
									updateData.add(aux);
								}
							}

							// se envia la diferencia en la interfaz
							dataParam[i].setStock(new Integer(diff));

						}
						else {
							resultByItem.add(new VeVAvailableStockReportResultByItemDTO(dataParam[i].getItemsku(), "El stock no cambi� con el almacenado actualmente", "-1"));
						}
					}
				}
			}
			else {
				mensaje = "Error actualizando stock. No se obtuvo ning�n dato desde el servicio web";
				baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1200", mensaje));
			}
			
			if (baseresultlist.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				result.setStockreportbyitem(resultByItem.toArray(new VeVAvailableStockReportResultByItemDTO[resultByItem.size()]));
				
				// para conservar el desarrollo que recibia solo un error
				result.setStatuscode(baseresultlist.get(0).getStatuscode());
				result.setStatusmessage(baseresultlist.get(0).getStatusmessage());

				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
			}

			// ENVIAR MENSAJE 1849
			String content = null;
			if (dataToUpdate.size() > 0) {
				content = doSendInt1849(dataToUpdate, tipoAjusteMap, vendor);
			}

			// Se guarda un registro de la actualización en la tabla para �Auditor�a VeV"
			if (content != null) {
				VeVUpdateTypeW vevUpdateType = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "AVAILABLE_STOCK");
				Date now = new Date();
				for (int i = 0; i < dataToUpdate.size(); i++) {

					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParams.getUsername());
					vevAudit.setUsertype(initParams.getUsertype());
					vevAudit.setUserenterprisecode(initParams.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParams.getUserenterprisename());
					vevAudit.setVendorid(vendor.getId());
					vevAudit.setInterfacecontent(content);
					vevAudit.setVevupdatetypeid(vevUpdateType.getId());

					vevAudit.setInitialdata(preUpdateData.get(i).getStock().toString());
					vevAudit.setFinaldata(updateData.get(i).getStock().toString());
					vevAudit.setItem("SKU");
					vevAudit.setItemvalue(updateData.get(i).getItemsku());

					vevauditserverlocal.addVeVAudit(vevAudit);
					
					resultByItem.add(new VeVAvailableStockReportResultByItemDTO(dataToUpdate.get(i).getItemsku(), "Stock enviado al Retail", "1"));
				}
			}
		
			// Guardar un arreglo con la lista de los elementos actualizados
			VeVAvailableStockReportDataDTO[] updateReport = new VeVAvailableStockReportDataDTO[dataToUpdate.size()];
			VeVAvailableStockReportResultByItemDTO[] resultByItemDTO = new VeVAvailableStockReportResultByItemDTO[resultByItem.size()];
			updateReport = dataToUpdate.toArray(updateReport);
			resultByItemDTO = resultByItem.toArray(resultByItemDTO);
			result.setStockreport(updateReport);
			result.setStockreportbyitem(resultByItemDTO);
		
		} catch (Exception e) {
			e.printStackTrace();
			baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1"));
			result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
			return result;
		}
		
		return result;
	}
	
	public UpdateVeVAvailableStockWSResultDTO doUpdateVeVAvailableStockWS(UpdateVeVAvailableStockInitParamDTO initParamDTO){
		
		UpdateVeVAvailableStockWSResultDTO resultDTO = new UpdateVeVAvailableStockWSResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Obtener el proveedor
			//VendorW vendor = vendorserverlocal.getById(initParamDTO.getVendorid());
			
			// Invocar el WS que entrega la información del reporte
			VeVAvailableStockReportResultDTO response = getVeVAvailableStockReport(vendor.getRut());
			
			// Validar que la respuesta no sea nula
			if (response != null && response.getStockreport() != null) {
				// Mapa para consultar si los par�metros son consistentes
				HashMap<String, VeVAvailableStockReportDataDTO> stockMap = new HashMap<String, VeVAvailableStockReportDataDTO>();
				for (VeVAvailableStockReportDataDTO stock : response.getStockreport()) {
					stockMap.put(stock.getItemsku(), stock);
				}
				
				// Analizar toda la lista de productos informados y la respuesta del servicio de actualización
				VeVAvailableStockWSResultDTO[] details = new VeVAvailableStockWSResultDTO[initParamDTO.getData().length];
				VeVAvailableStockReportDataDTO available = null;
				VeVAvailableStockReportDataDTO stock = null;
				HashMap<String, String> adjusttypeMap = new HashMap<String, String>();
				List<String> iList = new ArrayList<String>();
				List<VeVAvailableStockReportDataDTO> preupdateList = new ArrayList<VeVAvailableStockReportDataDTO>();
				List<VeVAvailableStockReportDataDTO> availableList = new ArrayList<VeVAvailableStockReportDataDTO>();
				List<VeVAvailableStockReportDataDTO> updateList = new ArrayList<VeVAvailableStockReportDataDTO>();
				VeVAvailableStockReportDataDTO aux = null;
				ItemW item = null;
				String internalcode = "";
				int diff = 0;
				for(int i = 0; i < initParamDTO.getData().length; i++){
					available = initParamDTO.getData()[i];
					internalcode = available.getItemsku().trim();
					
					details[i] = new VeVAvailableStockWSResultDTO();
					
					// Validar que todos los campos marcados como obligatorios en el detalle vengan en la llamada
					if(available.getItemsku() == null || available.getItemsku().trim().equals("")){
						details[i].setIteminternalcode(internalcode);
						details[i].setStatuscode("1");
						details[i].setStatusmessage("El código SKU Paris es obligatorio para todos los productos de la lista");
						continue;
					}
					
					if(available.getStock() == null){
						details[i].setIteminternalcode(internalcode);
						details[i].setStatuscode("1");
						details[i].setStatusmessage("La Cantidad de Stock es obligatoria para todos los productos de la lista");
						continue;
					}
					
					// Validar que no se repitan los productos
					if(!iList.contains(internalcode)){
						// Validar que 'código SKU Paris' exista para alg�n producto del sistema
						try {
							item = itemserver.getByPropertyAsSingleResult("internalcode", internalcode);
						} catch (Exception e) {
						}
						
						if(item != null){
							// Validar que el producto exista para el proveedor
							if(stockMap.containsKey(available.getItemsku())){
								stock = stockMap.get(available.getItemsku());
								
								// Validar que 'Cantidad de Stock' a actualizar sea mayor o igual que cero y con un m�ximo de 8 dígitos
								if(available.getStock() >= 0 && available.getStock() <= 99999999){
									// Se actualizan solo los productos sin errores y con cambios en el stock
									if(stock.getStock().intValue() != available.getStock().intValue()) {
										// Valor absoluto de la resta
										diff = Math.abs(stock.getStock().intValue() - available.getStock().intValue());
										
										if(stock.getStock().intValue() < available.getStock().intValue())
											adjusttypeMap.put(internalcode, "A");
										else
											adjusttypeMap.put(internalcode, "S");

										preupdateList.add(stock);	// Stock previo

										availableList.add(available);	// Disponible a actualizar
										
										aux = new VeVAvailableStockReportDataDTO();
										BeanExtenderFactory.copyProperties(available, aux);
										aux.setVendorcode(stock.getVendorcode());
										aux.setItemdescription(stock.getItemdescription());
										aux.setStock(new Integer(diff)); // se envía la diferencia en la interfaz
										
										updateList.add(aux);	// Diferencia a ajustar
									}
									
									details[i].setIteminternalcode(internalcode);
									details[i].setStatuscode("0");
									details[i].setStatusmessage("OK");
								}
								else{
									details[i].setIteminternalcode(internalcode);
									details[i].setStatuscode("1");
									details[i].setStatusmessage("El producto con SKU Paris '" + internalcode + "' tiene Cantidad de Stock menor o igual que cero o mayor que 99.999.999");
								}
							}
							else{
								details[i].setIteminternalcode(internalcode);
								details[i].setStatuscode("1");
								details[i].setStatusmessage("El producto con SKU Paris '" + internalcode + "' no existe para el proveedor");
							}
						}
						else{
							details[i].setIteminternalcode(internalcode);
							details[i].setStatuscode("1");
							details[i].setStatusmessage("El código SKU Paris '" + internalcode + "' no existe");
						}
						
						iList.add(internalcode);
					}
					else{
						details[i].setIteminternalcode(internalcode);
						details[i].setStatuscode("1");
						details[i].setStatusmessage("El producto con SKU Paris '" + internalcode + "' est� repetido. No se envía para actualización de stock");
					}
				}
				
				// Enviar mensaje 1849 si existen actualizaciones
				String content = null;
				if(updateList.size() > 0){
					content = doSendInt1849(updateList, adjusttypeMap, vendor);
					
					// Se guarda un registro de la actualización en la tabla para 'Auditor�a VeV'
					if (content != null) {
						// Obtener el tipo de actualización 'Capacidad Disponible VeV'
						VeVUpdateTypeW vevUpdateType = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "AVAILABLE_STOCK");
						
						Date now = new Date();
						for (int i = 0; i < availableList.size(); i++) {

							VeVAuditW vevAudit = new VeVAuditW();
							vevAudit.setWhen(now);
							vevAudit.setUsername(initParamDTO.getUsername());
							vevAudit.setUsertype(initParamDTO.getUsertype());
							vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
							vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
							vevAudit.setVendorid(vendor.getId());
							vevAudit.setInterfacecontent(content);
							vevAudit.setVevupdatetypeid(vevUpdateType.getId());
							vevAudit.setInitialdata(preupdateList.get(i).getStock().toString());
							vevAudit.setFinaldata(availableList.get(i).getStock().toString());
							vevAudit.setItem("SKU");
							vevAudit.setItemvalue(availableList.get(i).getItemsku());

							vevauditserverlocal.addVeVAudit(vevAudit);
						}
					}
				}
					
				// Mensaje de respuesta
				resultDTO.setDetails(details);
				resultDTO.setStatuscode("0");
				resultDTO.setStatusmessage("OK");
			
			}
			else {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Error actualizando stock. No se obtuvo ning�n dato desde el servicio web");
			}
				
		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}

	public BaseResultsDTO doUploadVeVAvailableStock(UploadVeVAvailableStockInitParamDTO initParams) {
		BaseResultsDTO result = new BaseResultsDTO();

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
			UpdateVeVAvailableStockInitParamDTO paramUpdate = new UpdateVeVAvailableStockInitParamDTO();
			paramUpdate.setData(initParams.getStocklist());
			paramUpdate.setVendorcode(vendor.getRut());
			paramUpdate.setUserenterprisecode(initParams.getUserenterprisecode());
			paramUpdate.setUserenterprisename(initParams.getUserenterprisename());
			paramUpdate.setUsername(initParams.getUsername());
			paramUpdate.setUsertype(initParams.getUsertype());

			VeVAvailableStockReportResultDTO resultUpdate = doUpdateVeVAvailableStock(paramUpdate);
			result.setBaseresults(resultUpdate.getValidationerrors());
			result.setStatuscode(resultUpdate.getStatuscode());
			result.setStatusmessage(resultUpdate.getStatusmessage());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public VeVLeadTimeReportResultDTO getVeVLeadTimeReport(String vendorcode) {
		
		VeVLeadTimeReportResultDTO result = new VeVLeadTimeReportResultDTO();
		VeVLeadTimeReportDataDTO[] report = null;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		Long vendorid = vendors[0].getId();
		
		try {
			vendor = vendors[0];
			VendorItemDataW[] items = itemserver.getItemsOfVendorAndVeV(vendorid);

			if (items.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000", "No se han encontrado productos configurados como venta en verde para esta consulta");
			}
			
			HashMap<String, VendorItemDataW> itemsMap = new HashMap<String, VendorItemDataW>();
			for (int i = 0; i < items.length; i++) {
				itemsMap.put(items[i].getInternalcode(), items[i]);
			}

			int chunksize = Integer.valueOf(B2BSystemPropertiesUtil.getProperty("EOM_LEADTIME_CHUNKSIZE"));
			Object[] splititems = ClassUtilities.SplitArray(items, VendorItemDataW.class, chunksize);
			List<corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationoutputmessage.Skus.Sku> detailsreponse = new ArrayList<corp.cencosud.dscl_omnichannel_int1855.leadtimefabricationoutputmessage.Skus.Sku>();
			for (int i = 0; i < splititems.length; i++) {
				VendorItemDataW[] itemsblock = (VendorItemDataW[]) splititems[i];
				if (itemsblock.length == 0) {
					continue;
				}
				
				// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
				ConsultLeadTimeFabricationEBMType param = doFillParamLeadTime(itemsblock, vendor);
				ConsultLeadTimeFabricationBindingQSServiceClient client = new ConsultLeadTimeFabricationBindingQSServiceClient(B2BSystemPropertiesUtil.getProperty("WSDL_EOM_LEADTIME").trim());
				ConsultLeadTimeFabricationPortType service = client.getConsultLeadTimeFabricationBindingQSPort();
				
				// JPE 20181002
				// Logger
				Client clientLogger = Client.getInstance(service);
				clientLogger.addInHandler(new DOMInHandler());
				clientLogger.addInHandler(new GetVeVLeadTimeHandler());
				clientLogger.addOutHandler(new DOMOutHandler());
				clientLogger.addOutHandler(new GetVeVLeadTimeHandler());

				ConsultLeadTimeFabricationResponseEBMType response = service.consultLeadTimeFabrication(param);

				if (!"0".equals(response.getReturnCode())) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1000", response.getReturnMessage());
				}
				else if (response.getDataArea().getSkus().getSku() == null || response.getDataArea().getSkus().getSku().size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1001", response.getReturnMessage());
				}
				else {
					detailsreponse.addAll(response.getDataArea().getSkus().getSku());
				}
			}
			
			List<VeVLeadTimeReportDataDTO> reportList = new ArrayList<VeVLeadTimeReportDataDTO>();
			ArrayList<String> itemsB2B = new ArrayList<String>(itemsMap.keySet());
			ArrayList<String> itemsParis = new ArrayList<String>();
			VeVLeadTimeReportDataDTO detail = null;
			VendorItemDataW itemdata = null;			
			for (int i = 0; i < detailsreponse.size(); i++) {
				detail = new VeVLeadTimeReportDataDTO();
				itemdata = itemsMap.get(detailsreponse.get(i).getSkuName());

				detail.setItemsku(detailsreponse.get(i).getSkuName());
				try{
					detail.setLeadtime(detailsreponse.get(i).getLeadTime().intValue());
				}catch(NullPointerException e){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1001", "SKU "+detail.getItemsku()+ " no tiene LeadTime. Comun�quese con ParisCL");
				}

				if (itemdata != null) {
					detail.setItemdescription(itemdata.getName());
					detail.setVendorcode(itemdata.getVendoritemcode());
				}
				reportList.add(detail);
				itemsParis.add(detailsreponse.get(i).getSkuName());
			}

			// Se completan items que est�n en B2B pero no en Paris
			// en itemsB2B quedan solo los que no est�n en Paris
			itemsB2B.removeAll(itemsParis);

			for (int i = 0; i < itemsB2B.size(); i++) {
				detail = new VeVLeadTimeReportDataDTO();
				itemdata = itemsMap.get(itemsB2B.get(i));
				detail.setItemsku(itemdata.getInternalcode());
				detail.setItemdescription(itemdata.getName());
				detail.setVendorcode(itemdata.getVendoritemcode());
				detail.setLeadtime(Integer.valueOf(0));
				reportList.add(detail);
			}

			report = reportList.toArray(new VeVLeadTimeReportDataDTO[reportList.size()]);

			result.setLeadtimereport(report);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	private ConsultLeadTimeFabricationEBMType doFillParamLeadTime(ItemW[] items, VendorW vendor) throws Exception {

		ConsultLeadTimeFabricationEBMType param = new ConsultLeadTimeFabricationEBMType();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		SenderType st = new SenderType();
		st.setApplication("BBR00");
		st.setCountry("CL");
		st.setBusinessUnit("DS");
		
		EBMHeaderType ebmht = new EBMHeaderType();
		ebmht.setCreationDateTime(date2);
		ebmht.setEBMID("");
		ebmht.setSender(st);
		ebmht.setTarget("MHTDSCL");
		
		param.setEBMHeader(ebmht);
		
		ObjectFactory factory = new ObjectFactory();
		EBMTrackingType ebt = new EBMTrackingType();
		ebt.setIntegrationCode(factory.createEBMTrackingTypeIntegrationCode("INT1855"));
		
		// listado de productos a consultar
		Skus skus = new Skus();
		List<Sku> skuslist = skus.getSku();
		for (int i = 0; i < items.length; i++) {
			Sku sku = new Sku();
			sku.setSkuName(items[i].getInternalcode());
			sku.setOriginFacilityAliasId(vendor.getCode());
			skuslist.add(sku);
		}

		ItemProcessingTime itime = new ItemProcessingTime();
		itime.setSkus(skus);

		ConsultLeadTimeFabricationEBMDataAreaRequestType areatype = new ConsultLeadTimeFabricationEBMDataAreaRequestType();
		areatype.setItemProcessingTime(itime);
		
		param.setDataArea(areatype);

		return param;
	}

	public VeVLeadTimeReportResultDTO doUpdateVeVLeadTime(UpdateVeVLeadTimeInitParamDTO initParams) {
		VeVLeadTimeReportResultDTO result = new VeVLeadTimeReportResultDTO();
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();

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
			List<String> rowsKeys = new ArrayList<String>();
			String rowKey = "";

			//VendorW vendor = vendorserverlocal.getById(vendor.getId());

			String mensaje = "";
			VeVLeadTimeReportDataDTO[] dataParam = initParams.getData();
			List<VeVLeadTimeReportDataDTO> dataToUpdate = new ArrayList<VeVLeadTimeReportDataDTO>();
			List<VeVLeadTimeReportDataDTO> preUpdateData = new ArrayList<VeVLeadTimeReportDataDTO>();
			// INVOCAR UN WS QUE ENTREGUE LA INFORMACION DEL REPORTE
			VeVLeadTimeReportResultDTO response = getVeVLeadTimeReport(vendor.getRut());
			VeVLeadTimeReportDataDTO[] datasws = response.getLeadtimereport();

			// Mapa para consultar si los parametros son consistentes
			HashMap<String, VeVLeadTimeReportDataDTO> dataswsMap = new HashMap<String, VeVLeadTimeReportDataDTO>();
			for (int i = 0; i < datasws.length; i++) {
				dataswsMap.put(datasws[i].getItemsku(), datasws[i]);
			}

			// COMPARAR CON LOS DATOS ACTUALIZADOS
			// HashMap<String, String> tipoAjusteMap = new HashMap<String, String>();
			VeVLeadTimeReportDataDTO dataws = null;
			for (int i = 0; i < dataParam.length; i++) {
				if (dataParam[i].getLeadtime() > 9999) {
					mensaje = "Debe ingresar valores menores o iguales a 9.999, Producto " + dataParam[i].getItemsku();
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}

				// Valida que no se repitan las filas
				rowKey = dataParam[i].getItemsku().toUpperCase();
				if (rowsKeys.contains(rowKey)) {
					mensaje = "La fila " + (i + 2) + ": La SKU " + dataParam[i].getItemsku() + " se est� subiendo más de una vez";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				else {
					rowsKeys.add(rowKey);
				}
				
				dataws = dataswsMap.get(dataParam[i].getItemsku());

				if (dataws == null) {
					mensaje = "Producto " + dataParam[i].getItemsku() + " no existe para el proveedor";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
				}
				else {
					if (dataParam[i].getLeadtime() <= 0 && dataws.getLeadtime().intValue() != dataParam[i].getLeadtime().intValue()) {
						mensaje = "Debe ingresar valores de lead time mayores a cero y expresado en horas, Producto " + dataParam[i].getItemsku();
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6000", mensaje));
					}

					// Se envían solo los que cambian
					if (dataws.getLeadtime().intValue() != dataParam[i].getLeadtime().intValue()) {
						dataToUpdate.add(dataParam[i]);
						preUpdateData.add(datasws[i]);
					}
				}
			}

			if (baseresultlist.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				result.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));

				// para conservar el desarrollo que recibia solo un error
				result.setStatuscode(baseresultlist.get(0).getStatuscode());
				result.setStatusmessage(baseresultlist.get(0).getStatusmessage());

				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
			}

			// ENVIAR MENSAJE 1851
			String content = null;
			if (dataToUpdate.size() > 0) {
				content = doSendInt1851(dataToUpdate, vendor);
			}

			// Se guarda un registro de la actualización en la tabla para �Auditor�a VeV"
			if (content != null) {
				VeVUpdateTypeW vevUpdateType = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "LEAD_TIME");
				Date now = new Date();
				for (int i = 0; i < dataToUpdate.size(); i++) {

					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParams.getUsername());
					vevAudit.setUsertype(initParams.getUsertype());
					vevAudit.setUserenterprisecode(initParams.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParams.getUserenterprisename());
					vevAudit.setVendorid(vendor.getId());
					vevAudit.setInterfacecontent(content);
					vevAudit.setVevupdatetypeid(vevUpdateType.getId());

					vevAudit.setInitialdata(preUpdateData.get(i).getLeadtime().toString());
					vevAudit.setFinaldata(dataToUpdate.get(i).getLeadtime().toString());
					vevAudit.setItem("SKU");
					vevAudit.setItemvalue(dataToUpdate.get(i).getItemsku());

					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1"));
		}
		return result;
	}

	public BaseResultsDTO doUploadVeVLeadTime(UploadVeVLeadTimeInitParamDTO initParams) {
		BaseResultsDTO result = new BaseResultsDTO();

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
			UpdateVeVLeadTimeInitParamDTO paramUpdate = new UpdateVeVLeadTimeInitParamDTO();
			paramUpdate.setData(initParams.getLeadimelist());
			paramUpdate.setVendorcode(vendor.getRut());
			paramUpdate.setUserenterprisecode(initParams.getUserenterprisecode());
			paramUpdate.setUserenterprisename(initParams.getUserenterprisename());
			paramUpdate.setUsername(initParams.getUsername());
			paramUpdate.setUsertype(initParams.getUsertype());

			VeVLeadTimeReportResultDTO resultUpdate = doUpdateVeVLeadTime(paramUpdate);
			result.setBaseresults(resultUpdate.getValidationerrors());
			result.setStatuscode(resultUpdate.getStatuscode());
			result.setStatusmessage(resultUpdate.getStatusmessage());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public VeVUpdateTypeArrayResultDTO getVeVUpdateType() {

		VeVUpdateTypeArrayResultDTO result = new VeVUpdateTypeArrayResultDTO();

		VeVUpdateTypeW[] typeArray = null;
		VeVUpdateTypeDTO[] typedtoArray = null;

		try {
			typeArray = vevupdatetypeserverlocal.getAllAsArray();

			typedtoArray = new VeVUpdateTypeDTO[typeArray.length];
			BeanExtenderFactory.copyProperties(typeArray, typedtoArray, VeVUpdateTypeDTO.class);

			result.setUpdatetypes(typedtoArray);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public VeVUpdateAuditReportArrayResultDTO getVeVUpdateAuditReportByVendorDateTypeAndSearchText(VeVUpdateAuditReportInitParamDTO initParams, boolean isByFilter, boolean isPaginated) {

		VeVUpdateAuditReportArrayResultDTO result = new VeVUpdateAuditReportArrayResultDTO();
		VeVUpdateAuditReportDataDTO[] vevUpdateAuditReportData = null;

		Locale locale = new Locale("es", "CL");
		Calendar cal = Calendar.getInstance(locale);
		Date since = null;
		Date until = null;
		int total = 0;

		
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParams.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			since = Date.from( initParams.getSince().atZone( ZoneId.systemDefault()).toInstant()); 
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			since = cal.getTime();

			until = Date.from( initParams.getUntil().atZone( ZoneId.systemDefault()).toInstant());
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			until = cal.getTime();

			vevUpdateAuditReportData = vevauditserverlocal.getVeVUpdateAuditReportByVendorDateTypeAndSearchText(vendorid, since, until, initParams.getUpdatetypeid(), initParams.getSearchtext(), initParams.getRows(), initParams.getPageNumber(), initParams.getOrderby(), isPaginated);

			// Si es consulta desde el filtro seteo información de la paginación
			if (isByFilter) {
				total = vevauditserverlocal.countVeVUpdateAuditReportByVendorDateTypeAndSearchText(vendorid, since, until, initParams.getUpdatetypeid(), initParams.getSearchtext());

				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(vevUpdateAuditReportData.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);

				result.setPageinfo(pageInfo);
			}

			result.setVevupdates(vevUpdateAuditReportData);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}

	public VeVUpdateAuditDetailReportArrayResultDTO getExcelVeVUpdateAuditReportData(VeVUpdateAuditReportInitParamDTO initParams) {
		VeVUpdateAuditDetailReportArrayResultDTO result = new VeVUpdateAuditDetailReportArrayResultDTO();
		
		// Valida proveedor
		Long vendorid = -1L;
		if (!initParams.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserverlocal.getByPropertyAsArray("rut", initParams.getVendorcode());				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			Calendar cal = Calendar.getInstance();
			
			Date since = Date.from(initParams.getSince().atZone(ZoneId.systemDefault()).toInstant()); 
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			since = cal.getTime();

			Date until = Date.from(initParams.getUntil().atZone(ZoneId.systemDefault()).toInstant());
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			until = cal.getTime();
			
			VeVAuditDetailDTO[] audit = vevauditserverlocal.getExcelVeVUpdateAuditReportData(vendorid, since, until, initParams.getUpdatetypeid(), initParams.getSearchtext());
			result.setVevauditdto(audit);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	public VeVUpdateAuditDetailReportArrayResultDTO getVeVUpdateAuditDetailReport(VeVUpdateAuditDetailReportInitParamDTO initParams) {

		VeVUpdateAuditDetailReportArrayResultDTO result = new VeVUpdateAuditDetailReportArrayResultDTO();

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
			VeVAuditDetailDTO[] audit = vevauditserverlocal.getVeVAuditDetailData(vendor.getId(), initParams.getUpdatedate(), initParams.getUpdatetype());
			result.setVevauditdto(audit);
			result.setTotaldata(audit.length);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public VeVCDDataArrayResultDTO doValidateVeVCDUnitaryDataChange(VeVCDUnitaryDataChangeInitParamDTO initparams){
		
		VeVCDDataArrayResultDTO resultDTO = new VeVCDDataArrayResultDTO();

		try {
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			
			// Validar que el N°mero de OC sea mayor que cero y tenga como m�ximo 6 dígitos
			if (initparams.getOrdernumber() <= 0 || initparams.getOrdernumber() > 999999) {
				error = "Los datos ingresados para N° de OC deben corresponder a N°meros mayores a cero, con un m�ximo de 6 dígitos";
				baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			VeVCDDataDTO data = orderserverlocal.getVeVCDDataByOrderNumber(initparams.getOrdernumber());
			
			if(data != null){
				switch(initparams.getFiltertype()){
				case 0:
					// ESTADO DE LA OC
					// Obtener el tipo de estado deseado de la OC
					OrderStateTypeW ost = orderstatetypeserverlocal.getByPropertyAsSingleResult("id", initparams.getOrderstatetypeid());
					
					data.setNeworderstatetypeid(ost.getId());
					data.setNeworderstatetype(ost.getName());
					
					break;
				case 3:
					// FECHA DE �LTIMO DESPACHO
					// Validar que la OC posea despachos asociados para el filtro FECHA DE �LTIMO DESPACHO
					if (data.getDeliverycurrentdate() == null || data.getDeliverycurrentdate().equals("")) {
						error = "La OC " + initparams.getOrdernumber() + " no posee despachos asociados, por lo tanto, no posee fecha de estado de despacho";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					
					if (baseresulList.size() > 0) {
						// Ordenar errores
						resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
					}
					
				case 1:
					// FPI
				case 2:
					// FECHA DE COMPROMISO
					data.setNewdate(initparams.getDate());
					
					break;
				default:
				}
				
				data.setValid(true);
			}
			else{
				data = new VeVCDDataDTO();
				data.setOrdernumber(initparams.getOrdernumber());
				data.setValid(false);
			}
												
			VeVCDDataDTO[] dataArray = new VeVCDDataDTO[1];
			dataArray[0] = data;
			
			resultDTO.setData(dataArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
		
	}
	
	public VeVCDDataArrayResultDTO doValidateVeVCDMassDataChange(VeVCDMassDataChangeArrayDTO initparams){
		
		VeVCDDataArrayResultDTO resultDTO = new VeVCDDataArrayResultDTO();

		try {
			// Obtener mapa con los tipos de estado de OC
			OrderStateTypeW[] osts = orderstatetypeserverlocal.getAllAsArray();
			Map<String, OrderStateTypeW> ostMap = new HashMap<String, OrderStateTypeW>();
			for(OrderStateTypeW ost : osts){
				ostMap.put(ost.getCode(), ost);
			}
			
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			OrderStateTypeW ost = null;
			VeVCDDataDTO data = null;
			List<VeVCDDataDTO> dataList = new ArrayList<VeVCDDataDTO>();
			for(VeVCDMassDataChangeDTO datachange : initparams.getData()){
				data = orderserverlocal.getVeVCDDataByOrderNumber(datachange.getOrdernumber());
				
				if(data != null){
					switch(datachange.getFiltertype()){
					case 0:
						// ESTADO DE LA OC
						// Obtener el tipo de estado deseado de la OC
						ost = ostMap.get(datachange.getOrderstatetypecode());
						
						data.setNeworderstatetypeid(ost.getId());
						data.setNeworderstatetype(ost.getName());
						
						break;
					case 3:
						// FECHA DE �LTIMO DESPACHO
						// Validar que la OC posea despachos asociados para el filtro FECHA DE �LTIMO DESPACHO
						if (data.getDeliverycurrentdate() == null || data.getDeliverycurrentdate().equals("")) {
							error = "La OC " + datachange.getOrdernumber() + " no posee despachos asociados, por lo tanto, no posee fecha de estado de despacho";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					case 1:
						// FPI
					case 2:
						// FECHA DE COMPROMISO
						data.setNewdate(datachange.getDate());
						
						break;
					default:
					}
					
					data.setValid(true);
				}
				else{
					data = new VeVCDDataDTO();
					data.setOrdernumber(datachange.getOrdernumber());
					data.setValid(false);
				}
								
				dataList.add(data);
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			VeVCDDataDTO[] dataArray = (VeVCDDataDTO[]) dataList.toArray(new VeVCDDataDTO[dataList.size()]);;
						
			resultDTO.setData(dataArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
		
	}
	
	public VeVCDDataArrayResultDTO doChangeVeVCDData(VeVCDDataChangeInitParamDTO initparams){
		
		VeVCDDataArrayResultDTO resultDTO = new VeVCDDataArrayResultDTO();
		
		try{
			// Obtener mapa con los tipos de estado de OC
			OrderStateTypeW[] osts = orderstatetypeserverlocal.getAllAsArray();
			Map<Long, OrderStateTypeW> ostMap = new HashMap<Long, OrderStateTypeW>();
			for(OrderStateTypeW ost : osts){
				ostMap.put(ost.getId(), ost);
			}
			
			// Validaciones
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			String ostCode = "";
			OrderW order = null;
			OrderDeliveryW[] orderDeliveries = null;
			Map<Long, OrderW> oMap = new HashMap<Long, OrderW>();
			for(VeVCDDataChangeDTO dataChange : initparams.getData()){
				// Obtener la OC
				try{
					order = orderserverlocal.getByPropertyAsSingleResult("id", dataChange.getOrderid());
				
				} catch (Exception e) {
					error = "No se pudo obtener la orden de compra N° " + dataChange.getOrdernumber();
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				oMap.put(order.getId(), order);
				
				// Validar que la OC se encuentre en estado actual 'ELIMINADA' o 'BLOQUEADA'
				ostCode = ostMap.get(order.getCurrentstatetypeid()).getCode();
				if(initparams.getFiltertype() == 0 && !ostCode.equals("ELIMINADA") && !ostCode.equals("BLOQUEADA")){
					error = "Orden de compra N° " + order.getNumber() + " debe estar en estado Eliminada o Bloqueada";
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				if(initparams.getFiltertype() == 3){
					orderDeliveries = orderdeliveryserverlocal.getByPropertyAsArray("order.id", dataChange.getOrderid()); 
					if(orderDeliveries == null || orderDeliveries.length <= 0){
						error = "La OC " + order.getNumber() + " no posee despachos asociados, por lo tanto, no posee fecha de estado de despacho";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			// Obtener los tipos de actualización VeV
			VeVUpdateTypeW schut = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW dchut = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_DATE_CHANGE");
			
			// Modificar las OC y crear los registros de auditor�a
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			OrderStateW orderState = null;
			List<OrderStateW> osList = new ArrayList<OrderStateW>();
			VeVAuditW vevAudit = null;
			List<VeVAuditW> aList = new ArrayList<VeVAuditW>();
			Date now = null;
			Long prestid = null;
			String predate = null;
			Date newdate = null;
			for(VeVCDDataChangeDTO dataChange : initparams.getData()){
				order = oMap.get(dataChange.getOrderid());
				
				now = new Date();
				
				vevAudit = new VeVAuditW();
				switch(initparams.getFiltertype()){
				case 0:
					// ESTADO DE LA OC
					prestid = order.getCurrentstatetypeid();
					
					order.setCurrentstatetypeid(dataChange.getNeworderstatetypeid());
					order.setCurrentstatetypedate(now);
					
					orderState = new OrderStateW();
					orderState.setOrderid(order.getId());
					orderState.setOrderstatetypeid(order.getCurrentstatetypeid());
					orderState.setWhen(now);
					
					osList.add(orderState);
					
					vevAudit.setVevupdatetypeid(schut.getId());
					vevAudit.setItem("Estado OC VEV CD");
					vevAudit.setInitialdata(ostMap.get(prestid).getName());
					vevAudit.setFinaldata(ostMap.get(dataChange.getNeworderstatetypeid()).getName());
					
					break;
				case 1:
					// FPI
					predate = order.getValid() != null ? dateFormat.format(order.getValid()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
						
					order.setValid(newdate);
					
					vevAudit.setVevupdatetypeid(dchut.getId());
					vevAudit.setItem("FPI");
					vevAudit.setInitialdata(predate);
					vevAudit.setFinaldata(dateFormat.format(newdate));
					
					break;
				case 2:
					// FECHA DE COMPROMISO
					predate = order.getOriginaldeliverydate() != null ? dateFormat.format(order.getOriginaldeliverydate()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
					
					order.setOriginaldeliverydate(newdate);
					
					vevAudit.setVevupdatetypeid(dchut.getId());
					vevAudit.setItem("Fecha compromiso VeV CD");
					vevAudit.setInitialdata(predate);
					vevAudit.setFinaldata(dateFormat.format(newdate));
					
					break;
				case 3:
					// FECHA DE �LTIMO DESPACHO
					predate = order.getCurrentstatetypedate() != null ? dateFormat.format(order.getCurrentstatetypedate()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
					
					order.setCurrentstatetypedate(newdate);
					
					vevAudit.setVevupdatetypeid(dchut.getId());
					vevAudit.setItem("�ltimo desp. VeV CD");
					vevAudit.setInitialdata(predate);
					vevAudit.setFinaldata(dateFormat.format(newdate));
					
					break;
				}
				
				oMap.put(order.getId(), order);
				
				vevAudit.setWhen(now);
				vevAudit.setUsername(initparams.getUsername());
				vevAudit.setUsertype(initparams.getUsertype());
				vevAudit.setUserenterprisecode(initparams.getUserenterprisecode());
				vevAudit.setUserenterprisename(initparams.getUserenterprisename());
				vevAudit.setVendorid(order.getVendorid());
				vevAudit.setItemvalue(dataChange.getOrdernumber().toString());
				vevAudit.setInterfacecontent("");
				
				aList.add(vevAudit);				
			}
			
			// Agregar los estados de OC
			if(initparams.getFiltertype() == 0){
				for(OrderStateW os : osList){
					orderstateserverlocal.addOrderState(os);
				}
			}
			orderstateserverlocal.flush();
			
			// Actualizar las OC
			for(Map.Entry<Long, OrderW> o : oMap.entrySet()) {
				orderserverlocal.updateOrder(o.getValue());
			}
			orderserverlocal.flush();
			
			// Agregar los registros de auditor�a
			for(VeVAuditW a : aList){
				vevauditserverlocal.addVeVAudit(a);
			}
			vevauditserverlocal.flush();
			
			VeVCDDataDTO[] data = new VeVCDDataDTO[initparams.getData().length];
			for(int i = 0; i < initparams.getData().length; i++){
				data[i] = orderserverlocal.getVeVCDDataByOrderNumber(initparams.getData()[i].getOrdernumber());
			}
			
			resultDTO.setData(data);
							
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}
	
	private String doSendInt1874(List<VeVDeliveryTimesReportDataDTO> dataToUpdate, VendorW vendor) {

		String result = null;

		try {
			JAXBContext jc = getJC1874();
			bbr.b2b.regional.logistic.xml.int1874.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1874.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1874.Message message1874 = objFactory.createMessage();
			message1874.setOrigen("B2B");
			message1874.setAccion("Update");
			message1874.setTipo("UpdateTransitLane");
			message1874.setIdCompania("1001");

			bbr.b2b.regional.logistic.xml.int1874.Detalles detalles = objFactory.createDetalles();

			for (int i = 0; i < dataToUpdate.size(); i++) {
				bbr.b2b.regional.logistic.xml.int1874.EntregaDestino entr = objFactory.createEntregaDestino();
				entr.setCodDestino(Integer.valueOf(dataToUpdate.get(i).getCommunecode())); // laneID
				entr.setTiempoEntrega(dataToUpdate.get(i).getTime());

				detalles.getEntregaDestino().add(entr);
			}

			message1874.setDetalles(detalles);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1874, sw);
			result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, vendor.getCode(), "INT1874");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1874" + "\",\"" + vendor.getCode() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1874" + "\"");

			// Se envía mensaje de ACK a la cola
			try {
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1874", result);
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1874_" + vendor.getCode();
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
					result = null;
				}
			}

		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String doSendInt1885(List<VeVDeliveryCapacitiesByZoneReportDataDTO> dataToUpdate, VendorW vendor) {

		String result = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			JAXBContext jc = getJC1885();
			bbr.b2b.regional.logistic.xml.int1885.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1885.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1885.Message message1885 = objFactory.createMessage();
			message1885.setOrigen("B2B");
			message1885.setAccion("Update");
			message1885.setTipo("UpdateCapacity");
			message1885.setIdCompania("1001");

			bbr.b2b.regional.logistic.xml.int1885.Capacidades capacidades = objFactory.createCapacidades();

			for (int i = 0; i < dataToUpdate.size(); i++) {
				bbr.b2b.regional.logistic.xml.int1885.Capacidad cap = objFactory.createCapacidad();
				cap.setCodZona(dataToUpdate.get(i).getZoneid()); // zonaAgendaID
				cap.setCapLunes(dataToUpdate.get(i).getMonday());
				cap.setCapMartes(dataToUpdate.get(i).getTuesday());
				cap.setCapMiercoles(dataToUpdate.get(i).getWednesday());
				cap.setCapJueves(dataToUpdate.get(i).getThursday());
				cap.setCapViernes(dataToUpdate.get(i).getFriday());
				cap.setCapSabado(dataToUpdate.get(i).getSaturday());
				cap.setCapDomingo(dataToUpdate.get(i).getSunday());
				cap.setJerarquia(0); // rank

				cap.setFechaInicio(sdf.format(new Date())); // startDTTM

				// calculo fecha actual + 2 a�os
				Calendar calnow = Calendar.getInstance();
				calnow.add(Calendar.YEAR, 2);
				cap.setFechaTermino(sdf.format(calnow.getTime())); // endDTTM

				capacidades.getCapacidad().add(cap);
			}

			message1885.setCapacidades(capacidades);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1885, sw);
			result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, vendor.getCode(), "INT1885");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1885" + "\",\"" + vendor.getCode() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1885" + "\"");
			// Se envía mensaje de ACK a la cola
			try {
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1885", result);
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1885_" + vendor.getCode();
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
					result = null;
				}
			}
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String doSendInt1849(List<VeVAvailableStockReportDataDTO> dataToUpdate, HashMap<String, String> tipoAjusteMap, VendorW vendor) {

		String result = null;

		try {

			JAXBContext jc = getJC1849();
			bbr.b2b.regional.logistic.xml.int1849.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1849.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1849.Message message1849 = objFactory.createMessage();
			message1849.setOrigen("B2B");
			message1849.setAccion("Update");
			message1849.setTipo("InventoryEvent");
			message1849.setIdCompania("1001");
			message1849.setIdReferencia(vendor.getCode());
			message1849.setTipoTransaccion("Adjustment");

			bbr.b2b.regional.logistic.xml.int1849.ListadoProductos productos = objFactory.createListadoProductos();

			for (int i = 0; i < dataToUpdate.size(); i++) {
				bbr.b2b.regional.logistic.xml.int1849.Producto prod = objFactory.createProducto();
				prod.setCodProducto(dataToUpdate.get(i).getItemsku()); // zonaAgendaID
				prod.setCodProveedor(vendor.getCode());
				prod.setCantidad(dataToUpdate.get(i).getStock());
				prod.setUnidMedida("CU");
				prod.setTipoAjuste(tipoAjusteMap.get(dataToUpdate.get(i).getItemsku()));

				productos.getProducto().add(prod);
			}

			message1849.setListadoProductos(productos);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1849, sw);
			result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, vendor.getCode(), "INT1849");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1849" + "\",\"" + vendor.getCode() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1849" + "\"");

			// Se envía mensaje de ACK a la cola
			try {
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1849", result);
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1849_" + vendor.getCode();
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
					result = null;
				}
			}
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String doSendInt1851(List<VeVLeadTimeReportDataDTO> dataToUpdate, VendorW vendor) {

		String result = null;

		try {
			JAXBContext jc = getJC1851();
			bbr.b2b.regional.logistic.xml.int1851.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1851.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1851.Message message1851 = objFactory.createMessage();
			message1851.setOrigen("B2B");
			message1851.setAccion("Update");
			message1851.setTipo("SKULOCATION");
			message1851.setIdCompania("1001");
			message1851.setLoc("Spanish (Mexico)");

			bbr.b2b.regional.logistic.xml.int1851.ListadoProductos productos = objFactory.createListadoProductos();

			for (int i = 0; i < dataToUpdate.size(); i++) {
				bbr.b2b.regional.logistic.xml.int1851.Producto prod = objFactory.createProducto();
				prod.setCodProducto(dataToUpdate.get(i).getItemsku()); // zonaAgendaID
				prod.setCodProveedor(vendor.getCode());
				prod.setLeadTime(dataToUpdate.get(i).getLeadtime());

				productos.getProducto().add(prod);
			}

			message1851.setListadoProductos(productos);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1851, sw);
			result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, vendor.getCode(), "INT1851");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1851" + "\",\"" + vendor.getCode() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1851" + "\"");

			// Se envía mensaje de ACK a la cola
			try {
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1851", result);
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1851_" + vendor.getCode();
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
					result = null;
				}
			}
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
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
