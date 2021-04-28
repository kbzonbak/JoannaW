package bbr.b2b.regional.logistic.buyorders.managers.classes;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.LockModeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.jboss.annotation.IgnoreDependency;

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
import bbr.b2b.common.util.Mailer;
import bbr.b2b.regional.logistic.buyorders.classes.VeVAuditServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVUpdateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.DirectOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.DirectOrderReportManagerRemote;
import bbr.b2b.regional.logistic.buyorders.report.classes.CSVOrderVeVReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DoChangeOrderStateInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DoCloseOrderInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DownloadOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportResultListResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDExcelOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderDetailServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailPK;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataArrayResultDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataChangeDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataChangeInitParamDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDMassDataChangeArrayDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDMassDataChangeDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDUnitaryDataChangeInitParamDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.QSender;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.ResponsableW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/DirectOrderReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderReportManager implements DirectOrderReportManagerLocal, DirectOrderReportManagerRemote {

	private static JAXBContext jc1887 = null;

	private static JAXBContext getJC1887() throws JAXBException {
		if (jc1887 == null)
			jc1887 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1887");
		return jc1887;
	}

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	private static Logger logger = Logger.getLogger(BuyOrderReportManager.class);

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	private LocationServerLocal locationserverlocal;
	
	@EJB
	private VendorServerLocal vendorserverlocal;

	@EJB
	private DirectOrderServerLocal directorderserverlocal;

	@EJB
	private DirectOrderStateTypeServerLocal directorderstatetypeserverlocal;

	@EJB
	private DirectOrderStateServerLocal directorderstateserverlocal;

	@EJB
	private DirectOrderDetailServerLocal directorderdetailserverlocal;

	@EJB
	private ResponsableServerLocal responsableserver;

	@EJB
	private DODeliveryStateTypeServerLocal dodeliverystatetypeserverlocal;

	@EJB
	private DODeliveryDetailServerLocal dodeliverydetailserverlocal;

	@EJB
	private DODeliveryServerLocal dodeliveryserverlocal;
	
	@EJB
	private DODeliveryStateServerLocal dodeliverystateserverlocal;

	@EJB
	private VeVAuditServerLocal vevauditserverlocal;

	@EJB
	private VeVUpdateTypeServerLocal vevupdatetypeserverlocal;

	@EJB
	@IgnoreDependency
	private DODeliveryReportManagerLocal dodeliveryreportmanager;


	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public DirectOrderW doCalculateTotalOfDirectOrder(Long orderid) throws NotFoundException, AccessDeniedException, OperationFailedException {

		// Estado de despachos
		DODeliveryStateTypeW[] deliverystatetypes = dodeliverystatetypeserverlocal.getAllAsArray();

		HashMap<Long, DODeliveryStateTypeW> dstMap = new HashMap<Long, DODeliveryStateTypeW>();
		for (int i = 0; i < deliverystatetypes.length; i++) {
			dstMap.put(deliverystatetypes[i].getId(), deliverystatetypes[i]);
		}

		// Detalle de Doc
		DirectOrderDetailW[] orderdetails = directorderdetailserverlocal.getByPropertyAsArray("id.orderid", orderid);
		TreeMap<DirectOrderDetailPK, DirectOrderDetailW> orderdetailMap = new TreeMap<DirectOrderDetailPK, DirectOrderDetailW>();
		DirectOrderDetailPK odpk = null;
		for (int i = 0; i < orderdetails.length; i++) {
			odpk = new DirectOrderDetailPK(orderdetails[i].getOrderid(), orderdetails[i].getItemid());

			orderdetails[i].setPendingunits(0D);
			orderdetails[i].setReceivedunits(0D);
			orderdetails[i].setTodeliveryunits(0D);
			orderdetails[i].setTotalpending(0D);
			orderdetails[i].setTotalreceived(0D);
			orderdetails[i].setTotaltodelivery(0D);
			orderdetailMap.put(odpk, orderdetails[i]);
		}

		// Busca los despachos de la oc
		DODeliveryW[] dodArr = dodeliveryserverlocal.getByPropertyAsArray("directorder.id", orderid);
		Map<Long, Long> dodOcMap = new HashMap<Long, Long>();
		for (int i = 0; i < dodArr.length; i++) {
			dodOcMap.put(dodArr[i].getId(), dodArr[i].getOrderid());
		}

		// Busca los detalles de despacho de la oc
		DODeliveryDetailW[] odds = dodeliverydetailserverlocal.getByQueryAsArray("select dodd from DODeliveryDetail as dodd, DODelivery as dod where dodd.dodelivery = dod and dod.directorder.id = " + orderid);

		DODeliveryDetailW odd = null;
		DODeliveryW delivery = null;
		DODeliveryStateTypeW deliverystatetype = null;
		DirectOrderDetailW orderdetail = null;

		double temp = 0;

		// PK: id de delivery, value: estado actual
		HashMap<Long, DODeliveryStateTypeW> deliverystateMap = new HashMap<Long, DODeliveryStateTypeW>();

		for (int i = 0; i < odds.length; i++) {
			odd = odds[i];

			// para obtener el precio
			odpk = new DirectOrderDetailPK(dodOcMap.get(odd.getDodeliveryid()), odd.getItemid());
			orderdetail = orderdetailMap.get(odpk);

			// busco el estado del lote asociado
			if (!deliverystateMap.containsKey(odd.getDodeliveryid())) {
				delivery = dodeliveryserverlocal.getById(odd.getDodeliveryid());
				deliverystatetype = dstMap.get(delivery.getCurrentstatetypeid());
				deliverystateMap.put(odd.getDodeliveryid(), deliverystatetype);
			} else
				deliverystatetype = deliverystateMap.get(odd.getDodeliveryid());

			// LO RECEPCIONADO SE SUMA INDEPENDIENTE DEL CLOSED, POR LO TANTO SE RESTA EN LOS NO CLOSED
			// Si el despacho no esta finalizado,
			if (!deliverystatetype.getClosed()) {
				temp = odd.getAvailableunits() - odd.getReceivedunits();
				if (temp < 0)
					temp = 0;

				orderdetail.setTodeliveryunits(orderdetail.getTodeliveryunits() + temp);
				orderdetail.setTotaltodelivery(orderdetail.getTodeliveryunits() * orderdetail.getFinalcost());
			}

			// unidades recibidas. Independiente del closed
			if (odd.getReceivedunits() > 0) {
				orderdetail.setReceivedunits(orderdetail.getReceivedunits() + odd.getReceivedunits());
				orderdetail.setTotalreceived(orderdetail.getReceivedunits() * orderdetail.getFinalcost());
			}

			orderdetail.setTotalneed(orderdetail.getUnits() * orderdetail.getFinalcost());
			orderdetailMap.put(odpk, orderdetail);
		}

		DirectOrderW order = directorderserverlocal.getById(orderid);
		order.setTodeliveryunits(0D);
		order.setReceivedunits(0D);
		order.setPendingunits(0D);
		order.setTotaltodelivery(0D);
		order.setTotalreceived(0D);
		order.setTotalpending(0D);

		// OJO-->RAG
		order.setNeedunits(0D);
		order.setTotalneed(0D);

		// se actualiza el detalle y sumariza para OC
		Iterator it = orderdetailMap.values().iterator();
		while (it.hasNext()) {
			orderdetail = (DirectOrderDetailW) it.next();
			// calculo pendiente
			// SIN DATOS TODELIVERYUNITS Y RECEIVEDUNITS NO SE PUEDE CALCULAR

			orderdetail.setPendingunits(orderdetail.getUnits() - (orderdetail.getReceivedunits() + orderdetail.getTodeliveryunits()));
			orderdetail.setTotalpending(orderdetail.getTotalneed() - (orderdetail.getTotalreceived() + orderdetail.getTotaltodelivery()));
			if (orderdetail.getPendingunits() < 0) {
				orderdetail.setPendingunits(0D);
				orderdetail.setTotalpending(0D);
			}

			orderdetail = directorderdetailserverlocal.updateDirectOrderDetail(orderdetail);

			// actualizo OC
			order.setNeedunits(order.getNeedunits() + orderdetail.getUnits());
			order.setTotalneed(order.getTotalneed() + orderdetail.getTotalneed());
			order.setTodeliveryunits(order.getTodeliveryunits() + orderdetail.getTodeliveryunits());
			order.setReceivedunits(order.getReceivedunits() + orderdetail.getReceivedunits());
			order.setPendingunits(order.getPendingunits() + orderdetail.getPendingunits());
			order.setTotaltodelivery(order.getTotaltodelivery() + orderdetail.getTotaltodelivery());
			order.setTotalreceived(order.getTotalreceived() + orderdetail.getTotalreceived());
			order.setTotalpending(order.getTotalpending() + orderdetail.getTotalpending());
		}

		// se actualiza OC
		order = directorderserverlocal.updateDirectOrder(order);

		// } //LLAVE DEL FOR
		return order;
	}

	public DirectOrderW[] doAcceptDirectOrders(Long... orderids) throws AccessDeniedException, OperationFailedException, NotFoundException {
		try {
			// Se buscan los tipos de estado de orden Liberada, Aceptada
			DirectOrderStateTypeW releasedstate = directorderstatetypeserverlocal.getByPropertyAsSingleResult("code", "LIBERADA");
			DirectOrderStateTypeW acceptedstate = directorderstatetypeserverlocal.getByPropertyAsSingleResult("code", "ACEPTADA");
			DirectOrderStateTypeW modifiedstate = directorderstatetypeserverlocal.getByPropertyAsSingleResult("code", "MOD_PARIS");

			ArrayList<DirectOrderW> ordersList = new ArrayList<DirectOrderW>();
			DirectOrderStateW newstate = null;
			Date now = null;
			for (int i = 0; i < orderids.length; i++) {
				Long orderid = orderids[i];
				// Se busca la orden y se verifica su estado
				DirectOrderW order = directorderserverlocal.getById(orderid);
				if (order.getCurrentstatetypeid().equals(releasedstate.getId()) || order.getCurrentstatetypeid().equals(modifiedstate.getId())) {
					logger.info("Aceptando Orden de Compra (ID " + orderid + ").");
					// throw new AccessDeniedException("La orden Nro " + order.getNumber() + " no se encuentra en estado
					// Liberada o Modificada");
					// Si la orden est� en estado Liberada, se actualiza y se agrega un registro al historial
					now = new Date();
					newstate = new DirectOrderStateW();
					newstate.setOrderid(order.getId());
					newstate.setOrderstatetypeid(acceptedstate.getId());
					newstate.setWho("Sistema");
					newstate.setWhen(now);
					// nuevo historial
					newstate = directorderstateserverlocal.addDirectOrderState(newstate);
					// se actualiza la OC
					order.setCurrentstatetypeid(acceptedstate.getId());
					order.setCurrentstatetypedate(now);
					order.setCurrentstatetypewho("Sistema");
					order = directorderserverlocal.updateDirectOrder(order);

					ordersList.add(order);
					logger.info("La orden (ID " + orderid + ") ha cambiado a estado Aceptada.");
				}
			}
			return (DirectOrderW[]) ordersList.toArray(new DirectOrderW[ordersList.size()]);

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndShowableOrderStateType(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndOrderStateType(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndRequestNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndClientRut(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndCommittedDate(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
//			// cal.add(Calendar.DAY_OF_MONTH, 1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			until = cal.getTime();

			Date since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date until = Date.from(initParams.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), since, until, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), since, until, initParams.getFiltertype(),initParams.getSalestoreid(),initParams.getSendnumber());

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
	
	public VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndSendNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) {
		VeVPDOrderReportResultDTO resultW = new VeVPDOrderReportResultDTO();
		VeVPDOrderReportDataDTO[] ordersWs = null;
		VeVPDOrderReportTotalDataDTO totalresult = null;
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
		
		try {
			ordersWs = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(), initParams.getPageNumber(),
					initParams.getRows(), initParams.getOrderby(), true, initParams.getSalestoreid(), initParams.getSendnumber());

			if (byFilter) {
				totalresult = directorderserverlocal.getVeVPDOrdersCountByVendorAndCriterium(vendor.getId(), initParams.getOcnumber(), initParams.getOrderstatetypeid(), initParams.getRequestnumber(), initParams.getClientrut(), null, null, initParams.getFiltertype(),initParams.getSalestoreid(), initParams.getSendnumber());

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

	public VeVPDOrderDetailReportResultDTO getVeVPDOrdersDetailByOrder(VeVPDOrderDetailReportInitParamDTO initParams, boolean providerUser, boolean byFilter) {
		VeVPDOrderDetailReportResultDTO resultW = new VeVPDOrderDetailReportResultDTO();

		VeVPDOrderDetailReportDataDTO[] ordersdetailsWs = null;
		VeVPDOrderDetailReportTotalDataDTO totalresult = null;
		VeVPDOrderReportDataDTO orderreport = null;
		int total;
		DirectOrderW[] order = null;
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(initParams.getVendorid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getOrderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DirectOrderW> orders = directorderserverlocal.getByProperties(properties);

			if (orders.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
			}

			ordersdetailsWs = directorderdetailserverlocal.getVeVPDOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (byFilter) {
				totalresult = directorderdetailserverlocal.getCountVeVPDOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId());

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
				order = doAcceptDirectOrders(ids);
				directorderserverlocal.flush();
			}

			if (order == null || order.length == 0) {
				order = new DirectOrderW[1];
			}

			order[0] = directorderserverlocal.getById(initParams.getOrderid());
			orderreport = directorderserverlocal.getVeVPDOrdersByVendorAndCriterium(order[0].getVendorid(), order[0].getNumber(), null, null, null, null, null, 1, 0, 0, null, false, null, null)[0];

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
	
	public PDFVeVPDOrderDetailReportResultDTO getPDFVeVPDOrdersDetailByOrder(VeVPDOrderDetailReportInitParamDTO initParams) {
		PDFVeVPDOrderDetailReportResultDTO resultW = new PDFVeVPDOrderDetailReportResultDTO();

		PDFVeVPDOrderDetailReportDataDTO[] ordersdetailsWs = null;
		PDFVeVPDOrderReportDataDTO orderreport = null;
		DirectOrderW[] order = null;
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(initParams.getVendorid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParams.getOrderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DirectOrderW> orders = directorderserverlocal.getByProperties(properties);

			if (orders.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
			}

			ordersdetailsWs = directorderdetailserverlocal.getPDFVeVPDOrdersDetailsByOrder(initParams.getOrderid(), vendor.getId());

			// SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA

			if (order == null || order.length == 0) {
				order = new DirectOrderW[1];
			}

			order[0] = directorderserverlocal.getById(initParams.getOrderid());
			orderreport = directorderserverlocal.getPDFVeVPDOrder(order[0].getVendorid(), order[0].getNumber())[0];

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
	

	public PDFVeVPDOrderDetailReportResultListResultDTO getPDFVeVPDOrdersDetailByOrders(VeVPDOrderDetailReportInitParamDTO initParams) {
		PDFVeVPDOrderDetailReportResultListResultDTO resultW = new PDFVeVPDOrderDetailReportResultListResultDTO();

		PDFVeVPDOrderDetailReportDataDTO[] ordersdetailsWs = null;
		PDFVeVPDOrderReportDataDTO orderreport[] = null;
		
		List<PDFVeVPDOrderDetailReportResultDTO> detailReportResultDTOList = new ArrayList<PDFVeVPDOrderDetailReportResultDTO>();

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
//			// Chequea la existencia del vendor
//			try {
//				vendorserverlocal.getById(initParams.getVendorid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			orderreport = directorderserverlocal.getPDFVeVPDOrderByIDs(vendor.getId(),initParams.getOrdersId());
			
			for (int i = 0; i < orderreport.length; i++) {
				PDFVeVPDOrderDetailReportResultDTO detailReportResultDTO = new PDFVeVPDOrderDetailReportResultDTO();
				detailReportResultDTO.setOrder(orderreport[i]);
				ordersdetailsWs = directorderdetailserverlocal.getPDFVeVPDOrdersDetailsByOrder(orderreport[i].getId(), vendor.getId());
				detailReportResultDTO.setOrderdetail(ordersdetailsWs);
				detailReportResultDTOList.add(detailReportResultDTO);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		resultW.setOrders(detailReportResultDTOList);
		
		return resultW;
	}

	public VeVPDExcelOrderReportResultDTO getExcelVeVPDOrdersReportByOrders(DownloadOrderReportInitParamDTO initParamW, boolean providerUser, boolean byPages) {

		VeVPDExcelOrderReportResultDTO resultW = new VeVPDExcelOrderReportResultDTO();
		VeVPDExcelOrderReportDataDTO[] ordersWs = null;
		DirectOrderW[] orders = null;
		List<VeVPDOrderReportDataDTO> lista = new ArrayList<VeVPDOrderReportDataDTO>();
		VeVPDOrderReportDataDTO orderreport = null;
		VeVPDOrderReportDataDTO[] orderreports = null;
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
				List<DirectOrderW> ordersTmp = directorderserverlocal.getByProperties(properties);

				if (ordersTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = directorderserverlocal.getDirectOrdersReportCountByOrders(initParamW.getOrderids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				ordersWs = directorderserverlocal.getExcelVeVPDOrderReportByOrders(initParamW.getOrderids());

				if (providerUser) {
					orders = doAcceptDirectOrders(initParamW.getOrderids()); // ACEPTO ORDENES SI ES PROVEEDOR
					for (int i = 0; i < orders.length; i++) {
						DirectOrderStateTypeW orderstate = directorderstatetypeserverlocal.getById(orders[i].getCurrentstatetypeid());
						orderreport = new VeVPDOrderReportDataDTO();

						BeanExtenderFactory.copyProperties(orders[i], orderreport);
						orderreport.setId(orders[i].getId());
						orderreport.setDorderestatetypename(orderstate.getName());
						orderreport.setDorderestatetypecode(orderstate.getCode());

						// se setea en null para que el flex no las actualice
						orderreport.setCurrentstatetypedate(null);
						orderreport.setEmitted(null);
						orderreport.setReprogrammingdate(null);
						orderreport.setValid(null);

						orderreport.setDocnumber(orders[i].getNumber());

						lista.add(orderreport);
					}
					orderreports = (VeVPDOrderReportDataDTO[]) lista.toArray(new VeVPDOrderReportDataDTO[lista.size()]);
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

	public OrderIdsByPagesW getExcelVeVPDOrdersReportByPages(VeVPDOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageranges) {
		OrderIdsByPagesW resultW = new OrderIdsByPagesW();
		OrderIdsByPagesW result = null;
		
		
		
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
		
		
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getFiltertype() == 5) {
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
//				// cal.add(Calendar.DAY_OF_MONTH, 1);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				until = cal.getTime();
				
				since = Date.from(initParamDTO.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				until = Date.from(initParamDTO.getUntil().plusDays(1).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			}

			// Se consulta por la cantidad de registros que implica el reporte excel para las paginas solicitadas
			// Además se traen los ids de las ordenes correspondientes
			result = directorderserverlocal.getVeVPDOrdersIdsByPages(vendor.getId(), initParamDTO.getOcnumber(), initParamDTO.getOrderstatetypeid(), initParamDTO.getRequestnumber(), initParamDTO.getClientrut(), since, until, initParamDTO.getRows(), initParamDTO.getFiltertype(),
					initParamDTO.getOrderby(), pageranges, initParamDTO.getSalestoreid(), initParamDTO.getSendnumber());

			// Se valida la cantidad de registros con el m�ximo permitido
			if (result.getTotalRows() < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				// Si es válido se envian ids de ordenes al portal
				resultW.setOrderIds(result.getOrderIds());
				resultW.setTotalRows(result.getTotalRows());
			}
			else {
				resultW.setTotalRows(result.getTotalRows());
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		
		return resultW;
	}

	public BaseResultDTO doCloseDirectOrder(DoCloseOrderInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			
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
			
			
//			// Obtener el proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserverlocal.getById(initParamDTO.getVendorid());
//			
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}
			
			DirectOrderStateTypeW[] dosts = directorderstatetypeserverlocal.getDirectOrderStateTypes();
			HashMap<Long, DirectOrderStateTypeW> dostMap = new HashMap<Long, DirectOrderStateTypeW>();
			Long dostVendorClosed = null;
			for (DirectOrderStateTypeW dost : dosts) {
				dostMap.put(dost.getId(), dost);

				if (dost.getCode().equals("CERRADA_PROV")) {
					dostVendorClosed = dost.getId();
				}
			}

			// Obtener el tipo de estado de despacho 'En Ruta'
			DODeliveryStateTypeW dodstOnRoute = dodeliverystatetypeserverlocal.getByPropertyAsSingleResult("code", "EN_RUTA");

			// Obtener el tipo de auditor�a VeV 'Cambio estado OC VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			
			List<DirectOrderW> directOrders = new ArrayList<DirectOrderW>();
			List<Long> directOrdersWithoutDeliveries = new ArrayList<Long>();
			DODeliveryW[] doDeliveries = null;
			DirectOrderW directOrder = null;
			VeVAuditW vevAudit = null;
			Long dostid = null;
			for (Long doid : initParamDTO.getOrdersid()) {
				directOrder = directorderserverlocal.getById(doid);

				// Validar que todas las OC son para el mismo proveedor
				if (directOrder.getVendorid().longValue() != vendor.getId().longValue()) {
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "La orden de compra N° " + directOrder.getNumber() + " corresponde a otro proveedor");
				}

				// Validar que las OC est�n en estados vigentes
				if (!dostMap.get(directOrder.getCurrentstatetypeid()).getValid()) {
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "La orden de compra N° " + directOrder.getNumber() + " no se encuentra en un estado vigente");
				}

				// Validar que las OC no poseen despachos en estado 'En Ruta'
				doDeliveries = dodeliveryserverlocal.getByPropertyAsArray("directorder.id", directOrder.getId());
				for (DODeliveryW doDelivery : doDeliveries) {
					if (doDelivery.getCurrentstatetypeid().longValue() == dodstOnRoute.getId().longValue()) {
						getSessionContext().setRollbackOnly();
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "La orden de compra N° " + directOrder.getNumber() + " posee despachos en estado 'En Ruta'");
					}
				}

				// Actualizar el estado de las OC a 'Cerrada Proveedor' y guardar el historial
				dostid = directOrder.getCurrentstatetypeid();

				Date now = new Date();
				directOrder.setCurrentstatetypeid(dostVendorClosed);
				directOrder.setCurrentstatetypedate(now);
				directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
				directOrder.setCurrentstatetypecomment("Cerrada por el proveedor");
				directorderserverlocal.updateDirectOrder(directOrder);

				DirectOrderStateW directOrderState = new DirectOrderStateW();
				directOrderState.setComment("Cerrada por el proveedor");
				directOrderState.setOrderid(directOrder.getId());
				directOrderState.setOrderstatetypeid(dostVendorClosed);
				directOrderState.setWhen(now);
				directOrderState.setWho(initParamDTO.getUsername());
				directorderstateserverlocal.addDirectOrderState(directOrderState);
				
				// 20200611
				// Registro de auditor�a
				vevAudit = new VeVAuditW();
				vevAudit.setWhen(now);
				vevAudit.setUsername(initParamDTO.getUsername());
				vevAudit.setUsertype(initParamDTO.getUsertype());
				vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
				vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
				vevAudit.setInterfacecontent("");
				vevAudit.setItem("Estado OC VeV PD");
				vevAudit.setItemvalue(directOrder.getNumber().toString());
				vevAudit.setInitialdata(dostMap.get(dostid).getName());
				vevAudit.setFinaldata(dostMap.get(dostVendorClosed).getName());
				vevAudit.setVendorid(directOrder.getVendorid());
				vevAudit.setVevupdatetypeid(utOrderState.getId());					
				
				vevauditserverlocal.addVeVAudit(vevAudit);

				directOrder = doCalculateTotalOfDirectOrder(directOrder.getId());
				directOrders.add(directOrder);

				if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
					directOrdersWithoutDeliveries.add(directOrder.getId());
				}
			}
			directorderdetailserverlocal.flush();

			boolean wait = false;
			for (DirectOrderW doW : directOrders) {
				DirectOrderDetailW[] orderdetails = directorderdetailserverlocal.getByPropertyAsArray("id.orderid", doW.getId());

				// Si la OC nunca ha tenido despachos se envía el mensaje 1846
				if (directOrdersWithoutDeliveries.contains(doW.getId())) {
					dodeliveryreportmanager.doSendInt1846(doW, null, null, orderdetails, "2");
					wait = true;
				}
			}

			// JMA 20150604
			// Se esperan 3 segundos para que el sistema de PARIS funciones :)
			if (wait) {
				try {
					Thread.sleep(3 * 1000);
				} catch (Exception e) {

				}
			}

			for (DirectOrderW doW : directOrders) {
				DirectOrderDetailW[] orderdetails = directorderdetailserverlocal.getByPropertyAsArray("id.orderid", doW.getId());

				if (doW.getNeedunits().doubleValue() == doW.getPendingunits().doubleValue()) {
					dodeliveryreportmanager.doSendInt1879(doW, 0, null, orderdetails, "3");
				}
				else {
					dodeliveryreportmanager.doSendInt1879(doW, 0, null, orderdetails, "4");
				}
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

	public VeVPDOrderReportResultDTO doChangeDirectOrderState(DoChangeOrderStateInitParamDTO initParamDTO) {
		VeVPDOrderReportResultDTO resultDTO = new VeVPDOrderReportResultDTO();
		
		try {
			
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
			
			
			// Obtener el proveedor
//			VendorW vendor = null;
//			try {
//				vendor = vendorserverlocal.getById(initParamDTO.getVendorid());
//			
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}
			
			// Obtener los tipos de estado de orden directa 'Reprograma Proveedor' y 'Reprograma Cliente'
			DirectOrderStateTypeW dostVendorRescheduled = directorderstatetypeserverlocal.getByPropertyAsSingleResult("code", "REPROG_PROV");
			DirectOrderStateTypeW dostClientRescheduled = directorderstatetypeserverlocal.getByPropertyAsSingleResult("code", "REPROG_CLIENTE");

			// Obtener la orden
			DirectOrderW directOrder = directorderserverlocal.getById(initParamDTO.getOrderid());

			// Validar que la orden pertenezca al proveedor
			if (!directOrder.getVendorid().equals(vendor.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			// Validar si su estado es vigente
			DirectOrderStateTypeW dost = directorderstatetypeserverlocal.getById(directOrder.getCurrentstatetypeid());
			if (!dost.getValid()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La orden no se encuentra en un estado vigente", false);
			}

			// Verificar si el nuevo estado es seleccionable por el usuario
			DirectOrderStateTypeW newdost = directorderstatetypeserverlocal.getById(initParamDTO.getOrderstatetypeid());
			if (!newdost.getVendorselectable()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "El nuevo estado de la orden no es seleccionable por el usuario", false);
			}

			Date now = new Date();
			boolean sendMsg = false;
			
			// Actualizar la orden
			directOrder.setCurrentstatetypedate(now);
			directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
			directOrder.setCurrentstatetypecomment(initParamDTO.getComment() == null ? "" : initParamDTO.getComment().trim());
			directOrder.setCurrentstatetypeid(newdost.getId());
			// Si el nuevo estado es 'Reprograma Proveedor' o 'Reprograma Cliente', cambiar la fecha de reprogramación
			if (newdost.getId().equals(dostVendorRescheduled.getId()) || newdost.getId().equals(dostClientRescheduled.getId())) {
				directOrder.setCurrentdeliverydate(DateConverterFactory2.StrToDate(initParamDTO.getReprogdate()));
				sendMsg = true;
			}
			directOrder = directorderserverlocal.updateDirectOrder(directOrder);			
			
			// Agregar el nuevo estado
			DirectOrderStateW directOrderState = new DirectOrderStateW();
			directOrderState.setOrderid(directOrder.getId());
			directOrderState.setOrderstatetypeid(newdost.getId());
			directOrderState.setWhen(now);
			directOrderState.setWho(initParamDTO.getUsername());
			directOrderState.setComment(initParamDTO.getComment() == null ? "" : initParamDTO.getComment().trim());
			directorderstateserverlocal.addDirectOrderState(directOrderState);

			// 20200611
			// Obtener el tipo de auditor�a VevV 'Cambio estado OC VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			
			// Registro de auditor�a
			VeVAuditW vevAudit = new VeVAuditW();
			vevAudit.setWhen(now);
			vevAudit.setUsername(initParamDTO.getUsername());
			vevAudit.setUsertype(initParamDTO.getUsertype());
			vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
			vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
			vevAudit.setInterfacecontent("");
			vevAudit.setItem("Estado OC VeV PD");
			vevAudit.setItemvalue(directOrder.getNumber().toString());
			vevAudit.setInitialdata(dost.getName());
			vevAudit.setFinaldata(newdost.getName());
			vevAudit.setVendorid(directOrder.getVendorid());
			vevAudit.setVevupdatetypeid(utOrderState.getId());					
			
			vevauditserverlocal.addVeVAudit(vevAudit);

			// Enviar mail al responsable de la orden
			ResponsableW responsable = responsableserver.getById(directOrder.getResponsableid());

			String subject = "B2B Paris: Cambio de estado Orden VeV Nro. " + directOrder.getNumber();
			Mailer mailer = Mailer.getInstance();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String[] mailreciever = new String[1];
			mailreciever[0] = responsable.getEmail();

			if (mailreciever[0] != null && !mailreciever[0].equals("")) {
				String msgtext =
					"Estimado(a) usuario(a):\n\nComunicamos a usted que se ha cambiado el estado de la orden " + //
					"VeV Nro. " + directOrder.getNumber() + ". El nuevo estado es " + newdost.getName() + "." + //
					"\n\nAtte.\nParis.";
				try {
					mailer.sendMailBySession(mailreciever, null, null, mailsender, subject, msgtext, false, null, mailSession);
				} catch (Exception e) {
					logger.error("No fue posible enviar email por cambio de estado de la OC VeV PD " + directOrder.getNumber());
				}
			}

			// Actualizar el reporte
			VeVPDOrderReportDataDTO[] report = new VeVPDOrderReportDataDTO[1];
			report[0] = new VeVPDOrderReportDataDTO();
			report[0].setId(directOrder.getId());
			report[0].setDocnumber(directOrder.getNumber());
			report[0].setDorderestatetypecode(newdost.getCode());
			report[0].setDorderestatetypename(newdost.getName());
			report[0].setReprogrammingdate(initParamDTO.getReprogdate());

			resultDTO.setOrders(report);

			String datePrefix = new String();
			// 'Reprograma Proveedor'
			if (newdost.getId().equals(dostVendorRescheduled.getId())) {
				datePrefix = "Reprogramación proveedor-";
			}
			// 'Reprograma Cliente'
			else if (newdost.getId().equals(dostClientRescheduled.getId())) {
				datePrefix = "Reprogramación cliente-";
			}
			// Otro caso
			else {
				datePrefix = "";
			}

			// Enviar mensaje 1887
			if (sendMsg) {
				doSendInt1887(directOrder, DateConverterFactory2.StrToDate(initParamDTO.getReprogdate()), datePrefix, initParamDTO.getComment());
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

	public FileDownloadInfoResultDTO getCSVDirectOrderVeVReport(CSVOrderVeVReportInitParamDTO initParamDTO, Long userId) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			Long vendorid = -1L;
			if (!initParamDTO.getVendorcode().equals("-1")) {
				// Valida proveedor
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
			int total = directorderdetailserverlocal.countCSVDirectOrderVeVReport(initParamDTO.getSalestoreid(), vendorid, initParamDTO.getOrderstatetypeid(), since, until, false);
			if (total > RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_CSV_ROWS()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2105");
			}

			resultDTO = directorderdetailserverlocal.getCSVDirectOrderVeVReport(initParamDTO.getSalestoreid(), vendorid, initParamDTO.getOrderstatetypeid(), since, until, userId, false);
		
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
	
	public FileDownloadInfoResultDTO getCSVDirectOrderVeVCourierReport(CSVOrderVeVReportInitParamDTO initParamDTO, Long userId) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		try {
			Long vendorid = -1L;
			if (!initParamDTO.getVendorcode().equals("-1")) {
				// Valida proveedor
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
			int total = directorderdetailserverlocal.countCSVDirectOrderVeVReport(initParamDTO.getSalestoreid() ,vendorid, initParamDTO.getOrderstatetypeid(), since, until, true);
			if (total > RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_CSV_ROWS()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2105");
			}
			
			resultDTO = directorderdetailserverlocal.getCSVDirectOrderVeVReport(initParamDTO.getSalestoreid() ,vendorid, initParamDTO.getOrderstatetypeid(), since, until, userId, true);
		
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

	public VeVPDDataArrayResultDTO doValidateVeVPDUnitaryDataChange(VeVPDUnitaryDataChangeInitParamDTO initparams){
		
		VeVPDDataArrayResultDTO resultDTO = new VeVPDDataArrayResultDTO();

		try {
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			
			// Validar que el N°mero de Orden Directa sea mayor que cero y tenga como m�ximo 6 dígitos
			if (initparams.getDirectordernumber() <= 0 || initparams.getDirectordernumber() > 999999) {
				error = "Los datos ingresados para N° de OC deben corresponder a N°meros mayores a cero, con un m�ximo de 6 dígitos";
				baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			VeVPDDataDTO data = directorderserverlocal.getVeVPDDataByDirectOrderNumber(initparams.getDirectordernumber());
			
			if (data != null) {
				switch(initparams.getFiltertype()){
				// Estado de la orden directa
				case 0:
					// Obtener el tipo de estado deseado de la orden directa
					DirectOrderStateTypeW dost = directorderstatetypeserverlocal.getByPropertyAsSingleResult("id", initparams.getDirectorderstatetypeid());
					
					data.setNewdirectorderstatetypeid(dost.getId());
					data.setNewdirectorderstatetype(dost.getName());
					
					break;
				// Fecha del �ltimo despacho
				case 2:
					// Validar que la orden posea un despacho asociado
					if (data.getDodeliveryid() == null || data.getDodeliveryid() == 0) {
						error = "La OC " + initparams.getDirectordernumber() + " no tiene un despacho asociado, por lo tanto, no posee fecha de estado de despacho";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					
				// Fecha de compromiso
				case 1:
					data.setNewdate(initparams.getDate());
					
					break;
				// Estado del despacho de la orden directa
				case 3:
					// Validar que la orden posea un despacho asociado
					if (data.getDodeliveryid() == null || data.getDodeliveryid() == 0) {
						error = "La OC " + initparams.getDirectordernumber() + " no tiene un despacho asociado";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
									
					// Obtener el tipo de estado deseado del despacho de la orden directa
					DODeliveryStateTypeW dodst = dodeliverystatetypeserverlocal.getByPropertyAsSingleResult("id", initparams.getDodeliverystatetypeid());
					
					data.setNewdodeliverystatetypeid(dodst.getId());
					data.setNewdodeliverystatetype(dodst.getName());
					data.setNewdate(initparams.getDate());
					
					break;
				default:
				}
				
				data.setValid(true);
			}
			else {
				data = new VeVPDDataDTO();
				data.setDirectordernumber(initparams.getDirectordernumber());
				data.setValid(false);
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
												
			VeVPDDataDTO[] dataArray = new VeVPDDataDTO[1];
			dataArray[0] = data;
			
			resultDTO.setData(dataArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}
	
	public VeVPDDataArrayResultDTO doValidateVeVPDMassDataChange(VeVPDMassDataChangeArrayDTO initparams){
		
		VeVPDDataArrayResultDTO resultDTO = new VeVPDDataArrayResultDTO();

		try {
			// Obtener mapa con los tipos de estado de la orden directa
			DirectOrderStateTypeW[] dosts = directorderstatetypeserverlocal.getAllAsArray();
			Map<Long, DirectOrderStateTypeW> dostMap = new HashMap<Long, DirectOrderStateTypeW>();
			for(DirectOrderStateTypeW dost : dosts){
				dostMap.put(dost.getId(), dost);
			}
			
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			DirectOrderStateTypeW dost = null;
			VeVPDDataDTO data = null;
			List<VeVPDDataDTO> dataList = new ArrayList<VeVPDDataDTO>();
			for(VeVPDMassDataChangeDTO datachange : initparams.getData()){
				data = directorderserverlocal.getVeVPDDataByDirectOrderNumber(datachange.getDirectordernumber());
								
				if (data != null) {
					switch(datachange.getFiltertype()) {
					// Estado de la orden directa
					case 0:
						// Obtener el tipo de estado deseado de la orden directa
						dost = dostMap.get(datachange.getDirectorderstatetypeid());
						
						data.setNewdirectorderstatetypeid(dost.getId());
						data.setNewdirectorderstatetype(dost.getName());
						
						break;
					// Fecha del �ltimo despacho
					case 2:
						// Validar que la orden posea un despacho asociado
						if (data.getDodeliveryid() == null || data.getDodeliveryid() == 0) {
							error = "La OC " + datachange.getDirectordernumber() + " no tiene un despacho asociado, por lo tanto, no posee fecha de estado de despacho";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					
					// Fecha de compromiso
					case 1:
						data.setNewdate(datachange.getDate());
						
						break;
					// Estado del despacho de la orden directa
					case 3:
						// Validar que la orden posea un despacho asociado
						if (data.getDodeliveryid() == null || data.getDodeliveryid() == 0) {
							error = "La OC " + datachange.getDirectordernumber() + " no tiene un despacho asociado";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						
						// Obtener el tipo de estado deseado del despacho de la orden directa
						DODeliveryStateTypeW dodst = dodeliverystatetypeserverlocal.getByPropertyAsSingleResult("id", datachange.getDodeliverystatetypeid());
						
						data.setNewdodeliverystatetypeid(dodst.getId());
						data.setNewdodeliverystatetype(dodst.getName());
						data.setNewdate(datachange.getDate());
						
						break;						
					default:
					}
					
					data.setValid(true);
				}
				else {
					data = new VeVPDDataDTO();
					data.setDirectordernumber(datachange.getDirectordernumber());
					data.setValid(false);
				}
								
				dataList.add(data);
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			VeVPDDataDTO[] dataArray = (VeVPDDataDTO[]) dataList.toArray(new VeVPDDataDTO[dataList.size()]);
						
			resultDTO.setData(dataArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}
	
	public VeVPDDataArrayResultDTO doChangeVeVPDData(VeVPDDataChangeInitParamDTO initParamDTO){		
		VeVPDDataArrayResultDTO resultDTO = new VeVPDDataArrayResultDTO();
		
		try{
			// Obtener mapa con los tipos de estado de orden directa
			DirectOrderStateTypeW[] dosts = directorderstatetypeserverlocal.getAllAsArray();
			DirectOrderStateTypeW dostAccepted = null;
			DirectOrderStateTypeW dostReceived = null;
			DirectOrderStateTypeW dostRejected = null;
			DirectOrderStateTypeW dostLost = null;
			Map<Long, DirectOrderStateTypeW> dostMap = new HashMap<Long, DirectOrderStateTypeW>();
			for(DirectOrderStateTypeW dost : dosts) {
				if (dost.getCode().equals("ACEPTADA")) {
					dostAccepted = dost;
				}
				if (dost.getCode().equals("RECEPCIONADA")) {
					dostReceived = dost;
				}
				if (dost.getCode().equals("RECHAZADA")) {
					dostRejected = dost;
				}
				if (dost.getCode().equals("EXTRAVIADO")) {
					dostLost = dost;
				}
				dostMap.put(dost.getId(), dost);
			}
			
			// Obtener mapa con los tipos de estado de despacho de orden directa
			DODeliveryStateTypeW[] dodsts = dodeliverystatetypeserverlocal.getAllAsArray();
			DODeliveryStateTypeW dodstLost = null;
			DODeliveryStateTypeW dodstReceived = null;
			DODeliveryStateTypeW dodstExpectations = null;
			Map<Long, DODeliveryStateTypeW> dodstMap = new HashMap<Long, DODeliveryStateTypeW>();
			for(DODeliveryStateTypeW dodst : dodsts) {
				if (dodst.getCode().equals("EXTRAVIADO")) {
					dodstLost = dodst;
				}
				if (dodst.getCode().equals("REC_CONFORME")) {
					dodstReceived = dodst;
				}
				if (dodst.getCode().equals("EXPECTATIVAS")) {
					dodstExpectations = dodst;
				}
				dodstMap.put(dodst.getId(), dodst);
			}
			
			// Validaciones
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			
			Map<Long, DirectOrderW> doMap = new HashMap<Long, DirectOrderW>();
			Map<Long, DODeliveryW> dodMap = new HashMap<Long, DODeliveryW>();
			DirectOrderW directOrder = null;
			DODeliveryW doDelivery = null;
			String dostCode = "";
			boolean dostLostChange = false;
			boolean dostReceivedChange = false;
			boolean dostRejectedChange = false;
			boolean dodstOnRouteChange = false;
			boolean dodstDeliveredChange = false;
			boolean dodstExpectationsChange = false;
			boolean dodstLostChange = false;
			for (VeVPDDataChangeDTO dataChange : initParamDTO.getData()) {
				// Obtener la orden directa
				directOrder = null;
				try {
					directOrder = directorderserverlocal.getByPropertyAsSingleResult("id", dataChange.getDirectorderid());
				
				} catch (Exception e) {
					error = "No se pudo obtener la orden directa N° " + dataChange.getDirectordernumber();
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				if (directOrder != null) {
					doMap.put(directOrder.getId(), directOrder);
					
					// Validar cambio de estado de orden directa
					if (initParamDTO.getFiltertype() == 0) {
						// JPE 20180814
						// Validar que exista el nuevo tipo de estado de orden directa
						if (dostMap.containsKey(dataChange.getNewdirectorderstatetypeid())) {
							// JPE 20180814
							// Validar si el cambio de estado de orden directa es 'Aceptada' 
							if (dostMap.get(dataChange.getNewdirectorderstatetypeid()).getCode().equals("ACEPTADA")) {
								// Validar que la orden directa se encuentre en estado actual 'CANC_PARIS', 'BLOQ_PARIS', 'RECHAZADA' o 'RECEPCIONADA'
								dostCode = dostMap.get(directOrder.getCurrentstatetypeid()).getCode();
								if (!dostCode.equals("CANC_PARIS") && !dostCode.equals("BLOQ_PARIS") &&
										!dostCode.equals("RECHAZADA") && !dostCode.equals("RECEPCIONADA")){
									error = "Orden de compra N° " + directOrder.getNumber() + " debe estar en estado Cancelada Paris, Bloqueada Paris, Rechazada o Recepcionada";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
							}
							// JPE 20180814
							// Validar si el cambio de estado de orden directa es 'Extraviado'
							else if (dostMap.get(dataChange.getNewdirectorderstatetypeid()).getCode().equals("EXTRAVIADO")) {
								// Validar que la orden tenga un despacho creado
								if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
									error = "La orden directa N° " + dataChange.getDirectordernumber() + " no tiene un despacho creado";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
								else {
									doDelivery = dodeliveryserverlocal.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
									// Validar que el despacho actual se encuentre en estado 'Pendiente de Agendar', 'Pendiente de Retiro', 'En Ruta',
									// 'Entregado' o 'Expectativas'
									if (!dodstMap.get(doDelivery.getCurrentstatetypeid()).getCode().equals("PEND_AGENDAR") &&
											!dodstMap.get(doDelivery.getCurrentstatetypeid()).getCode().equals("PEND_RETIRO") &&
												!dodstMap.get(doDelivery.getCurrentstatetypeid()).getCode().equals("EN_RUTA") &&
													!dodstMap.get(doDelivery.getCurrentstatetypeid()).getCode().equals("REC_CONFORME") &&
														!dodstMap.get(doDelivery.getCurrentstatetypeid()).getCode().equals("EXPECTATIVAS")) {
										error = "El despacho de la orden directa N° " + dataChange.getDirectordernumber() + " debe encontrase en estado 'Pendiente de Agendar', 'Pendiente de Retiro', 'En Ruta', 'Entregado' o 'Expectativas'";
										baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
									}
									else {
										dodMap.put(directOrder.getId(), doDelivery);
									}
								}
								
								dostLostChange = true;
							}
							// JPE 20200604
							// Validar si el cambio de estado de orden directa es 'Cancelada Paris'
							else if (dostMap.get(dataChange.getNewdirectorderstatetypeid()).getCode().equals("CANC_PARIS")) {
								// Validar que la orden no posea despacho asociado
								if (directOrder.getDodeliveryid() != null && directOrder.getDodeliveryid() > 0) {
									error = "La orden directa N° " + dataChange.getDirectordernumber() + " no debe tener un despacho";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
							}
							// JPE 20200604
							// Validar si el cambio de estado de orden directa es 'Recepcionada'
							else if (dostMap.get(dataChange.getNewdirectorderstatetypeid()).getCode().equals("RECEPCIONADA")) {
								// Validar que la orden tenga un despacho creado
								if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
									error = "La orden directa N° " + dataChange.getDirectordernumber() + " no tiene un despacho creado";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
								else {
									doDelivery = dodeliveryserverlocal.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
									dodMap.put(directOrder.getId(), doDelivery);
								}

								dostReceivedChange = true;
							}
							// JPE 20200604
							// Validar si el cambio de estado de orden directa es 'Rechazada'
							else if (dostMap.get(dataChange.getNewdirectorderstatetypeid()).getCode().equals("RECHAZADA")) {
								// Validar que la orden tenga un despacho creado
								if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
									error = "La orden directa N° " + dataChange.getDirectordernumber() + " no tiene un despacho creado";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
								else {
									doDelivery = dodeliveryserverlocal.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
									dodMap.put(directOrder.getId(), doDelivery);
								}
								
								dostRejectedChange = true;
							}
							// 20200604
							// Otro estado
							else {
								error = "El cambio de estado de la orden directa N° " + dataChange.getDirectordernumber() + " no es válido";
								baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
						}
						else {
							error = "El nuevo estado de la orden directa N° " + dataChange.getDirectordernumber() + " no existe en el sistema";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					// Validar cambio de fecha de compromiso
					else if (initParamDTO.getFiltertype() == 1) {
						;
					}
					// Validar cambio de fecha de estado de despacho
					else if (initParamDTO.getFiltertype() == 2) {
						// Validar que la orden tenga un despacho creado
						if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
							error = "La OC " + directOrder.getNumber() + " no posee despachos asociados, por lo tanto, no posee fecha de estado de despacho";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						else {
							doDelivery = dodeliveryserverlocal.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
							dodMap.put(directOrder.getId(), doDelivery);
						}
					}
					// JPE 20200604
					// Validar cambio de estado de despacho de orden directa
					else if (initParamDTO.getFiltertype() == 3) {
						// Validar que la orden tenga un despacho creado
						if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
							error = "La orden directa N° " + dataChange.getDirectordernumber() + " no tiene un despacho creado";
							baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						else {
							doDelivery = dodeliveryserverlocal.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
							dodMap.put(directOrder.getId(), doDelivery);
							
							// Validar que exista el nuevo tipo de estado de despacho
							if (dodstMap.containsKey(dataChange.getNewdodeliverystatetypeid())) {
								// Validar si el cambio de estado de despacho es 'En Ruta'
								if (dodstMap.get(dataChange.getNewdodeliverystatetypeid()).getCode().equals("EN_RUTA")) {
									dodstOnRouteChange = true;
								}
								// Validar si el cambio de estado de despacho es 'Entregado'
								else if (dodstMap.get(dataChange.getNewdodeliverystatetypeid()).getCode().equals("REC_CONFORME")) {
									dodstDeliveredChange = true;
								}
								// Validar si el cambio de estado de despacho es 'Expectativas'
								else if (dodstMap.get(dataChange.getNewdodeliverystatetypeid()).getCode().equals("EXPECTATIVAS")) {
									dodstExpectationsChange = true;
								}
								// Validar si el cambio de estado de despacho es 'Extraviado'
								else if (dodstMap.get(dataChange.getNewdodeliverystatetypeid()).getCode().equals("EXTRAVIADO")) {
									dodstLostChange = true;
								}
								// Otro estado
								else {
									error = "El cambio de estado de despacho de la orden directa N° " + dataChange.getDirectordernumber() + " no es válido";
									baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
							}
							else {
								error = "El nuevo estado de despacho de la orden directa N° " + dataChange.getDirectordernumber() + " no existe en el sistema";
								baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
						}						
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1802");
			}
			
			// Obtener los tipos de actualización VeV
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW utOrderDate = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_DATE_CHANGE");
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");
			
			// Modificar las orden directa y crear los registros de auditor�a
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			
			DirectOrderStateW directOrderState = null;
			DODeliveryStateW doDeliveryState = null;
			DODeliveryDetailW[] doDeliveryDetails = null;
			VeVAuditW vevAudit = null;
			Date now = null;
			Long dostid = null;
			Long newdostid = null;
			Long dodstid = null;
			Long newdodstid = null;
			String date = null;
			Date newdate = null;
			for (VeVPDDataChangeDTO dataChange : initParamDTO.getData()) {
				now = new Date();
				
				switch(initParamDTO.getFiltertype()){
				// Estado de la orden directa
				case 0:
					directOrder = doMap.get(dataChange.getDirectorderid());
					dostid = directOrder.getCurrentstatetypeid();
					newdostid = dataChange.getNewdirectorderstatetypeid();
						
					directOrder.setCurrentstatetypeid(newdostid);
					directOrder.setCurrentstatetypedate(now);
					directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
					directOrder.setCurrentstatetypecomment("Modificación Manual - Autogestión");
					
					directOrder = directorderserverlocal.updateDirectOrder(directOrder);
										
					directOrderState = new DirectOrderStateW();
					directOrderState.setOrderid(directOrder.getId());
					directOrderState.setOrderstatetypeid(newdostid);
					directOrderState.setWhen(now);
					directOrderState.setWho(initParamDTO.getUsername());
					directOrderState.setComment("Modificación Manual - Autogestión");
					
					directorderstateserverlocal.addDirectOrderState(directOrderState);
					
					// JPE 20200604
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VEV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(dostMap.get(dostid).getName());
					vevAudit.setFinaldata(dostMap.get(newdostid).getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);
					
					if (dostLostChange || dostReceivedChange || dostRejectedChange) {
						// Estado del despacho asociado
						doDelivery = dodMap.get(directOrder.getId());
						dodstid = doDelivery.getCurrentstatetypeid();
						
						// 'Extraviado'
						if (dostLostChange) {
							newdodstid = dodstLost.getId();
						}
						// 'Entregado'
						else if (dostReceivedChange) {
							newdodstid = dodstReceived.getId();
						}
						// 'Expectativas'
						else {
							newdodstid = dodstExpectations.getId();
						}
						
						doDelivery.setCurrentstatetypeid(newdodstid);
						doDelivery.setCurrentstwho(initParamDTO.getUsername());
						doDelivery.setCurrentstcomment("Modificación Manual - Autogestión");
						
						doDelivery = dodeliveryserverlocal.updateDODelivery(doDelivery);
						
						doDeliveryState = new DODeliveryStateW();
						doDeliveryState.setDeliveryid(doDelivery.getId());
						doDeliveryState.setDeliverystatetypeid(newdodstid);
						doDeliveryState.setWhen(now);
						doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
						doDeliveryState.setComment("Modificación Manual - Autogestión");
						doDeliveryState.setWho(initParamDTO.getUsername());

						dodeliverystateserverlocal.addDODeliveryState(doDeliveryState);
						
						// Registro de auditor�a
						vevAudit = new VeVAuditW();
						vevAudit.setWhen(now);
						vevAudit.setUsername(initParamDTO.getUsername());
						vevAudit.setUsertype(initParamDTO.getUsertype());
						vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
						vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
						vevAudit.setInterfacecontent("");
						vevAudit.setItem("Estado Despacho VeV PD");
						vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
						vevAudit.setInitialdata(dodstMap.get(dodstid).getName());
						vevAudit.setFinaldata(dodstMap.get(newdodstid).getName());
						vevAudit.setVendorid(directOrder.getVendorid());
						vevAudit.setVevupdatetypeid(utDeliveryState.getId());
						
						vevauditserverlocal.addVeVAudit(vevAudit);
						
						// Actualizar las cantidades/montos del despacho
						doDeliveryDetails = dodeliverydetailserverlocal.getByPropertyAsArray("id.dodeliveryid", doDelivery.getId());
						// 'Entregado'
						if (dostReceivedChange) {
							// Cantidades/montos entregados
							for (DODeliveryDetailW doDeliveryDetail : doDeliveryDetails) {
								doDeliveryDetail.setReceivedunits(doDeliveryDetail.getAvailableunits());
								dodeliverydetailserverlocal.updateDODeliveryDetail(doDeliveryDetail);							
	 						}
						}
						// 'Extraviado' o 'Expectativas'
						else {
							// Cantidades/montos pendientes de entrega
							for (DODeliveryDetailW doDeliveryDetail : doDeliveryDetails) {
								doDeliveryDetail.setReceivedunits(0D);
								dodeliverydetailserverlocal.updateDODeliveryDetail(doDeliveryDetail);							
	 						}
						}					
					}
					
					break;
				
				// Fecha de compromiso
				case 1:
					directOrder = doMap.get(dataChange.getDirectorderid());
					date = directOrder.getOriginaldeliverydate() != null ? dateFormat.format(directOrder.getOriginaldeliverydate()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
						
					directOrder.setOriginaldeliverydate(newdate);
					
					directOrder = directorderserverlocal.updateDirectOrder(directOrder);
					
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Fecha compromiso VeV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(date);
					vevAudit.setFinaldata(dateFormat.format(newdate));					
					vevAudit.setVendorid(directOrder.getVendorid());				
					vevAudit.setVevupdatetypeid(utOrderDate.getId());

					vevauditserverlocal.addVeVAudit(vevAudit);
					
					break;
				
				// Fecha de �ltimo despacho
				case 2:
					doDelivery = dodMap.get(dataChange.getDirectorderid());
					date = doDelivery.getCurrentstdate() != null ? dateFormat.format(doDelivery.getCurrentstdate()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
					
					doDelivery.setCurrentstdate(newdate);
					
					doDelivery = dodeliveryserverlocal.updateDODelivery(doDelivery);
					
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("�ltimo desp. VeV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(date);
					vevAudit.setFinaldata(dateFormat.format(newdate));
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderDate.getId());					
			
					vevauditserverlocal.addVeVAudit(vevAudit);
					
					break;
				
				// Estado de despacho de la orden directa
				case 3:
					doDelivery = dodMap.get(dataChange.getDirectorderid());
					dodstid = doDelivery.getCurrentstatetypeid();
					newdodstid = dataChange.getNewdodeliverystatetypeid();
					date = doDelivery.getCurrentstdate() != null ? dateFormat.format(doDelivery.getCurrentstdate()) : "";
					newdate = DateConverterFactory2.StrToDate(dataChange.getNewdate());
					
					doDelivery.setCurrentstatetypeid(newdodstid);
					doDelivery.setCurrentstwho(initParamDTO.getUsername());
					doDelivery.setCurrentstcomment("Modificación Manual - Autogestión");
					doDelivery.setCurrentstdate(newdate); // Actualizar la fecha de estado de cliente
					
					doDelivery = dodeliveryserverlocal.updateDODelivery(doDelivery);
					
					doDeliveryState = new DODeliveryStateW();
					doDeliveryState.setDeliveryid(doDelivery.getId());
					doDeliveryState.setDeliverystatetypeid(newdodstid);
					doDeliveryState.setWhen(now);
					doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
					doDeliveryState.setComment("Modificación Manual - Autogestión");
					doDeliveryState.setWho(initParamDTO.getUsername());

					dodeliverystateserverlocal.addDODeliveryState(doDeliveryState);
					
					// JPE 20200615
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado Despacho VeV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(dodstMap.get(dodstid).getName());
					vevAudit.setFinaldata(dodstMap.get(newdodstid).getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);
					
					// Actualizar las cantidades/montos del despacho
					doDeliveryDetails = dodeliverydetailserverlocal.getByPropertyAsArray("id.dodeliveryid", doDelivery.getId());
					// 'Entregado'
					if (dodstDeliveredChange) {
						// Cantidades/montos entregados
						for (DODeliveryDetailW doDeliveryDetail : doDeliveryDetails) {
							doDeliveryDetail.setReceivedunits(doDeliveryDetail.getAvailableunits());
							dodeliverydetailserverlocal.updateDODeliveryDetail(doDeliveryDetail);							
 						}
					}
					// 'En Ruta', 'Expectativas' o 'Extraviado'
					else {
						// Cantidades/montos pendientes de entrega
						for (DODeliveryDetailW doDeliveryDetail : doDeliveryDetails) {
							doDeliveryDetail.setReceivedunits(0D);
							dodeliverydetailserverlocal.updateDODeliveryDetail(doDeliveryDetail);							
 						}
					}			
					
					directOrder = doMap.get(dataChange.getDirectorderid());
					dostid = directOrder.getCurrentstatetypeid();
					
					// 'En Ruta'
					if (dodstOnRouteChange) {
						newdostid = dostAccepted.getId();
					}
					// 'Entregado'
					else if (dodstDeliveredChange) {
						newdostid = dostReceived.getId();
					}
					// 'Expectativas'
					else if (dodstExpectationsChange) {
						newdostid = dostRejected.getId();
					}
					// 'Extraviado'
					else {
						newdostid = dostLost.getId();
					}
					
					directOrder.setCurrentstatetypeid(newdostid);
					directOrder.setCurrentstatetypedate(now);
					directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
					directOrder.setCurrentstatetypecomment("Modificación Manual - Autogestión");
					
					directOrder = directorderserverlocal.updateDirectOrder(directOrder);
										
					directOrderState = new DirectOrderStateW();
					directOrderState.setOrderid(directOrder.getId());
					directOrderState.setOrderstatetypeid(newdostid);
					directOrderState.setWhen(now);
					directOrderState.setWho(initParamDTO.getUsername());
					directOrderState.setComment("Modificación Manual - Autogestión");
					
					directorderstateserverlocal.addDirectOrderState(directOrderState);
					
					// Registros de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VEV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(dostMap.get(dostid).getName());
					vevAudit.setFinaldata(dostMap.get(newdostid).getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);
					
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Fecha estado despacho VeV PD");
					vevAudit.setItemvalue(dataChange.getDirectordernumber().toString());
					vevAudit.setInitialdata(date);
					vevAudit.setFinaldata(dateFormat.format(newdate));
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderDate.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);				
					
					break;
				}
			}
			
			directorderserverlocal.flush();
			directorderstateserverlocal.flush();
			directorderserverlocal.flush();
			dodeliverystateserverlocal.flush();
			dodeliverydetailserverlocal.flush();
			vevauditserverlocal.flush();
			
			// JPE 20180824
			// Orden directa 'Extraviada'
			// Despacho 'Extraviado'
			if (dostLostChange || dodstLostChange) {
				DirectOrderDetailW[] directOrderDetails = null;
				for (VeVPDDataChangeDTO dataChange : initParamDTO.getData()) {
					// Recalcular unidades/montos de órdenes directas
					directOrder = doCalculateTotalOfDirectOrder(dataChange.getDirectorderid());
					
					// JPE 20200520
					// Enviar la interfaz INT1879
					doDelivery = dodMap.get(dataChange.getDirectorderid());
					directOrderDetails = directorderdetailserverlocal.getByPropertyAsArray("id.orderid", directOrder.getId());
					doDeliveryDetails = dodeliverydetailserverlocal.getByPropertyAsArray("id.dodeliveryid", doDelivery.getId());
					
					dodeliveryreportmanager.doSendInt1879(directOrder, doDelivery.getNumber().longValue(), doDeliveryDetails, directOrderDetails, "5");
				}
			}
			// JPE 20200604
			// Orden directa 'Recepcionada' o 'Rechazada'
			// Despacho 'En Ruta', 'Entregado' o 'Expectativas'
			else if (dostReceivedChange || dostRejectedChange || dodstOnRouteChange || dodstDeliveredChange || dodstExpectationsChange) {
				// Recalcular unidades/montos de órdenes directas
				for (VeVPDDataChangeDTO dataChange : initParamDTO.getData()) {
					doCalculateTotalOfDirectOrder(dataChange.getDirectorderid());
				}				
			}

			VeVPDDataDTO[] data = new VeVPDDataDTO[initParamDTO.getData().length];
			for(int i = 0; i < initParamDTO.getData().length; i++){
				data[i] = directorderserverlocal.getVeVPDDataByDirectOrderNumber(initParamDTO.getData()[i].getDirectordernumber());
			}
			
			resultDTO.setData(data);
				
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}

	private void doSendInt1887(DirectOrderW directorder, Date date, String datePrefix, String comment) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			JAXBContext jc = getJC1887();
			bbr.b2b.regional.logistic.xml.int1887.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1887.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1887.Message message1887 = objFactory.createMessage();
			message1887.setOrigen("B2B");
			message1887.setAccion("Update");
			message1887.setTipo("CustomerCommunication");
			message1887.setIdCompania("1001");
			message1887.setTipoEntidad("Customer Order");
			message1887.setNumOrdencompra(directorder.getRequestnumber());
			message1887.setTipoNota("Cambio Fecha B2B");
			message1887.setNuevaFecha(datePrefix + sdf.format(date) + "-" + comment);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1887, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, directorder.getNumber().toString(), "INT1887");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1887" + "\",\"" + directorder.getNumber().toString() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1887" + "\"");

			// Se envía mensaje de ACK a la cola
			try {
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1887", result);
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1887_" + directorder.getNumber();
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
				}
			}
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
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