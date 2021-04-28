package bbr.b2b.regional.logistic.managers.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO.ComparisonOperator;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailFillRateDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.EvaluationTypeServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.OcDelEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.fillrate.classes.FillRateDetailServerLocal;
import bbr.b2b.regional.logistic.fillrate.classes.FillRatePeriodServerLocal;
import bbr.b2b.regional.logistic.fillrate.classes.FillRateServerLocal;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateDetailPK;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateDetailW;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRatePeriodW;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.managers.interfaces.MakeFillRateManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.MakeFillRateManagerRemote;
import bbr.b2b.regional.logistic.vendors.classes.VendorDepartmentRankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorRankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorLogDTO;

@Stateless(name = "managers/MakeFillRateManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MakeFillRateManager implements MakeFillRateManagerLocal, MakeFillRateManagerRemote {

	private static Logger logger = Logger.getLogger(MakeFillRateManager.class);

	private static boolean isServiceStarted = false;

	@Resource
	private SessionContext ctx;

	@EJB
	FillRateDetailServerLocal fillratedetailserver;

	@EJB
	FillRateServerLocal fillrateserver;

	@EJB
	FillRatePeriodServerLocal fillrateperiodserver;

	@EJB
	VendorRankingServerLocal vendorrankingserver;

	@EJB
	VendorDepartmentRankingServerLocal vendordepartmentrankingserver;

	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	@EJB
	OcDelEvaluationServerLocal ocdelevaluationserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	EvaluationTypeServerLocal evaluationtypeserver;

	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;

	@EJB
	DatingServerLocal datingserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	LocationServerLocal locationserver;

	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {

		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		// Obtiene todos los timers asociados al bean
		Collection<Timer> timers = ts.getTimers();
		// ... y los cancela
		for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de Generación de Ranking");
		isServiceStarted = true;
	}

	@TransactionTimeout(value = 36000)
	@Timeout
	public synchronized void timeoutHandler(Timer timer) throws OperationFailedException, NotFoundException {

		try {
			if (!isServiceStarted)
				return;

			if (RegionalLogisticConstants.getInstance().getSTOP_MAKE_FILLRATE())
				return;

			
			
			Locale locale = new Locale("es", "CL");
			Date now = new Date();
			Calendar calnow = Calendar.getInstance(locale);
			calnow.setTime(now);

			// se ejecuta solo los 15 de cada mes
			if (calnow.get(Calendar.DAY_OF_MONTH) != 15)
				return;


			Calendar cal = Calendar.getInstance(locale);
			Calendar caltoday = Calendar.getInstance(locale);
			locale = new Locale("es", "CL");
			cal = Calendar.getInstance(locale);

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			SimpleDateFormat sdfDescr = new SimpleDateFormat("dd/MM/yy");
			boolean existFillRate = false;
			TreeMap fillRateMap = new TreeMap();
			TreeMap fillRateDetailMap = new TreeMap();
			FillRateW fillrate = null;
			String frPK;
			OrderDetailFillRateDTO[] orderdetails = null;
			FillRateDetailPK frdpk;
			FillRateDetailW fillratedetail = null;
			FillRatePeriodW fillRatePeriod;
			VendorLogDTO[] vendorWs = null;

			logger.info("******* CRON FILLRATE " + sdf.format(now) + " *******");

			Date today = new Date();
			caltoday.setTime(today);

			cal.setTime(today);
			cal.add(Calendar.MONTH, -1); // un mes atras
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();
			logger.info("since: " + sdf.format(since));

			cal.setTime(today);
			cal.set(Calendar.DAY_OF_MONTH, 1);

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.SECOND, -1);
			Date until = cal.getTime();
			logger.info("until: " + sdf.format(until));

			// veo si ese rango ya existe
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("since", "since", since);
			properties[1] = new PropertyInfoDTO("until", "until", until);

			FillRatePeriodW[] fillRatePeriods = fillrateperiodserver.getByPropertiesAsArray(properties);
			if (fillRatePeriods != null && fillRatePeriods.length > 0) {
				logger.info("Periodo de fillrate ya existe");
				return;
			}

			// creo el nuevo periodo
			fillRatePeriod = new FillRatePeriodW();
			fillRatePeriod.setSince(since);
			fillRatePeriod.setUntil(until);
			fillRatePeriod.setName(sdfDescr.format(since) + " - " + sdfDescr.format(until));

			fillRatePeriod = fillrateperiodserver.addFillRatePeriod(fillRatePeriod);

			int vendorlength = 0;
			try {
				vendorWs = vendorserver.getVendorsByExpirationRange(since, until);
			} catch (Exception e) {
				logger.error("DEMONIO_FILLRATE: No se puede obtener los proveedores!");
				throw e;
			}
			vendorlength = vendorWs.length;
			// locales
			LocationW cd = null;
			try {
				cd = locationserver.getByPropertyAsArray("code", "12")[0];
			} catch (Exception e) {
				throw e;
			}

			ArrayList ordersArray = new ArrayList();

			// Se obtiene detalles de OC por ordenados por vendedor
			HashMap<Long, HashMap<Long, ArrayList<OrderDetailFillRateDTO>>> ocdetailbyvendor = getOrderDetailbyDates(since, until);

			// Obtengo OC por ordenados por vendedor
			HashMap<Long, ArrayList<OrderW>> getOrdersbyDates = getOrdersbyDates(since, until);
			OrderW[] orderWs;
			OrderW order;
			for (int i = 0; i < vendorWs.length; i++) {

				// Busca las O/Cs
				VendorLogDTO vendor = vendorWs[i];
				HashMap ordertailByID = ocdetailbyvendor.get(vendor.getId());

				if (ordersArray != null)
					ordersArray.clear();

				ordersArray = getOrdersbyDates.get(vendor.getId());

				// proveedores que tienen OC solo vev o para otros locales
				if (ordersArray == null || ordersArray.size() == 0)
					continue;

				orderWs = (OrderW[]) ordersArray.toArray(new OrderW[ordersArray.size()]);

				//
				logger.info((i + 1) + " de " + vendorlength + " - Vendor: " + vendor.getName() + " orderWs: " + orderWs.length);
				//
				// guardo valores en mapa

				for (int j = 0; j < orderWs.length; j++) {
					order = orderWs[j];

					// 20090303 Mariana Espinoza: Solo OC que van al CD
					if (cd.getId().longValue() != order.getDeliverylocationid().longValue())
						continue;

					// formo pk del mapa
					frPK = order.getDepartmentid() + "_" + order.getVendorid();
					if (fillRateMap.containsKey(frPK)) {
						existFillRate = true;
						fillrate = (FillRateW) fillRateMap.get(frPK);
					} else {
						existFillRate = false;
						fillrate = new FillRateW();
						fillrate.setCountorders(0);
						fillrate.setReceivedunits(0d);
						fillrate.setTotalneed(0d);
						fillrate.setTotalreceived(0d);
						fillrate.setTotalunits(0d);
					}
					fillrate.setDepartmentid(order.getDepartmentid());
					fillrate.setVendorid(order.getVendorid());
					fillrate.setCountorders(fillrate.getCountorders() + 1);
					fillrate.setReceivedunits(fillrate.getReceivedunits() + order.getReceivedunits());
					fillrate.setTotalreceived(fillrate.getTotalreceived() + order.getTotalreceived());
					fillrate.setTotalneed(fillrate.getTotalneed() + order.getTotalneed());
					fillrate.setTotalunits(fillrate.getTotalunits() + order.getNeedunits());
					fillrate.setFillrateperiodid(fillRatePeriod.getId());
					if (!existFillRate) // lo guardo para obtener el id
						fillrate = fillrateserver.addFillRate(fillrate);

					fillRateMap.put(frPK, fillrate);

					// ahora completo los detalles

					ArrayList detailList = (ArrayList) ordertailByID.get(order.getId());
					if (detailList != null) {
						orderdetails = (OrderDetailFillRateDTO[]) detailList.toArray(new OrderDetailFillRateDTO[detailList.size()]);
					} else {
						System.out.println("Detalle NO encontrado en MAPA para oc id " + order.getId());
					}
					for (int k = 0; k < orderdetails.length; k++) {
						frdpk = new FillRateDetailPK();
						frdpk.setFillrateid(new Long(fillrate.getId()));
						frdpk.setItemid(orderdetails[k].getItemid());
						if (fillRateDetailMap.containsKey(frdpk)) {
							fillratedetail = (FillRateDetailW) fillRateDetailMap.get(frdpk);
						} else {
							fillratedetail = new FillRateDetailW();
							fillratedetail.setReceivedunits(0d);
							fillratedetail.setTotalunits(0d);
						}

						fillratedetail.setFillrateid(frdpk.getFillrateid());
						fillratedetail.setItemid(frdpk.getItemid());
						fillratedetail.setReceivedunits(fillratedetail.getReceivedunits() + orderdetails[k].getReceivedunits());
						fillratedetail.setTotalunits(fillratedetail.getTotalunits() + orderdetails[k].getNeedunits());
						fillRateDetailMap.put(frdpk, fillratedetail);
					}
				}
			}
			//
			// guardo fillrate
			if (!fillRateMap.isEmpty()) {

				//
				fillrate = null;
				Set keys = fillRateMap.keySet();
				Iterator itKeys = keys.iterator();
				System.out.println("Guardando fillrate" + fillRateMap.size());
				while (itKeys.hasNext()) {
					frPK = (String) itKeys.next();
					fillrate = (FillRateW) fillRateMap.get(frPK);
					fillrate.setFillrateperiodid(fillRatePeriod.getId());
					fillrateserver.updateFillRate(fillrate);
					// deberia haberse creado antes, ahora solo actualizo
				}
				// guardo detalles
				fillratedetail = null;
				Set keysd = fillRateDetailMap.keySet();
				Iterator itKeysd = keysd.iterator();
				System.out.println("Guardando detalle de fillrate:" + fillRateDetailMap.size());
				while (itKeysd.hasNext()) {
					frdpk = (FillRateDetailPK) itKeysd.next();
					fillratedetail = (FillRateDetailW) fillRateDetailMap.get(frdpk);
					fillratedetailserver.addFillRateDetail(fillratedetail);
				}

			}

			fillRateMap.clear();
			fillRateDetailMap.clear();
			getOrdersbyDates.clear();
			ocdetailbyvendor.clear();
			logger.info("DEMONIO_FILLRATE: Daily run finished!");
		} catch (Exception e) {
			logger.error("DEMONIO_MAKE_FILLRATE: Error excepcional!");
			logger.info("******* FIN  CRON *******");
			getCtx().setRollbackOnly();
			e.printStackTrace();
		}

	}

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	private HashMap<Long, HashMap<Long, ArrayList<OrderDetailFillRateDTO>>> getOrderDetailbyDates(Date since, Date until) {
		// TODO RAG

		HashMap ocbyvendor = new HashMap();

		try {
			HashMap orderDetailMap;
			OrderDetailFillRateDTO[] ods = orderdetailserver.getOrderDetailFillrate(since, until);
			OrderDetailFillRateDTO od;
			for (int i = 0; i < ods.length; i++) {
				od = ods[i];
				Long vendorid = new Long(od.getVendorid());
				Long orderid = new Long(od.getOrderid());
				if (ocbyvendor.containsKey(vendorid))
					orderDetailMap = (HashMap) ocbyvendor.get(vendorid);
				else
					orderDetailMap = new HashMap();

				ArrayList details = new ArrayList();
				if (orderDetailMap.containsKey(orderid))
					details = (ArrayList) orderDetailMap.get(orderid);
				details.add(od);

				orderDetailMap.put(orderid, details);

				ocbyvendor.put(vendorid, orderDetailMap);

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ocbyvendor;

	}

	private HashMap<Long, ArrayList<OrderW>> getOrdersbyDates(Date since, Date until) {
		HashMap ocbyvendor = new HashMap();
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("vevcd", "vevcd", false);
		properties[1] = new PropertyInfoDTO("expiration", "expiration1", since, ComparisonOperator.GREATER_THAN_OR_EQUALS);
		properties[2] = new PropertyInfoDTO("expiration", "expiration2", until, ComparisonOperator.LESS_THAN_OR_EQUALS);

		OrderW[] orders;
		try {
			orders = orderserver.getByPropertiesAsArray(properties);
			// orders = orderserver.getByQueryAsArray("")
			for (int i = 0; i < orders.length; i++) {

				Long vendorid = new Long(orders[i].getVendorid());

				ArrayList ocsList = new ArrayList();
				if (ocbyvendor.containsKey(vendorid))
					ocsList = (ArrayList) ocbyvendor.get(vendorid);
				ocsList.add(orders[i]);

				ocbyvendor.put(vendorid, ocsList);

			}
		} catch (OperationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ocbyvendor;

	}

}
