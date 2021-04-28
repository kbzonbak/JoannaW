package bbr.b2b.regional.logistic.managers.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
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
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.evaluations.classes.EvaluationTypeServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationTypeW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.OcDelEvaluationW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.managers.interfaces.MakeRankingManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.MakeRankingManagerRemote;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.RankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.RankingW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorDepartmentRankingPK;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorDepartmentRankingW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorRankingW;

@Stateless(name = "managers/MakeRankingManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MakeRankingManager implements MakeRankingManagerLocal, MakeRankingManagerRemote {

	private static Logger logger = Logger.getLogger(MakeRankingManager.class);

	private static boolean isServiceStarted = false;

	private static int IMPORTANCE_RANKING = 80;

	@Resource
	private SessionContext ctx;

	@EJB
	RankingServerLocal rankingserver;

	// @EJB
	// VendorRankingServerLocal vendorrankingserver;
	//
	// @EJB
	// VendorDepartmentRankingServerLocal vendordepartmentrankingserver;

	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	// @EJB
	// OcDelEvaluationServerLocal ocdelevaluationserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

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

	@TransactionTimeout(value = 7200)
	@Timeout
	public synchronized void timeoutHandler(Timer timer) throws OperationFailedException, NotFoundException {

		try {
			if (!isServiceStarted)
				return;

			if (RegionalLogisticConstants.getInstance().getSTOP_MAKE_RANKING())
				return;

			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

			Calendar calnow = Calendar.getInstance();
			calnow.setTime(today);

			// se ejecuta solo los 2 de cada mes
			if (calnow.get(Calendar.DAY_OF_MONTH) != 2)
				return;

			logger.info(" ----- " + sdf.format(today) + "  Evento Timer Recibido: " + timer.getInfo() + " ----- ");

			Calendar cal = Calendar.getInstance(new Locale("es", "CL"));

			cal.setTime(today);
			cal.add(Calendar.MONTH, -3); // 3 meses atr�s
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();

			cal.setTime(today);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.SECOND, -1);
			Date until = cal.getTime();

			// veo si ese rango ya existe
			try {
				PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
				properties[0] = new PropertyInfoDTO("since", "since", since);
				properties[1] = new PropertyInfoDTO("until", "until", until);
				RankingW rankingPeriod = rankingserver.getByPropertiesAsArray(properties)[0];

				if (rankingPeriod != null) {
					logger.error("Periodo de Ranking ya existe");
					logger.error("DEMONIO_MAKE_RANKING: Error excepcional!");
					logger.info("******* FIN  CRON *******");
					return;
				}
			} catch (ArrayIndexOutOfBoundsException e1) {
				// no existe :)
			}

			logger.info("Inicio de creación de ranking:" + sdf.format(today));
			prepareData(since, until);

			TreeMap<VendorDepartmentRankingPK, VendorDepartmentRankingW> vendorDeptRankingMap = new TreeMap<VendorDepartmentRankingPK, VendorDepartmentRankingW>();
			TreeMap vendorRankingMap = new TreeMap();
			HashMap ordersMap = null;
			ArrayList ocdelEvaluationsList = new ArrayList();

			// Se obtienen los estados de lotes que existen en la BD y se almacenan en un Mapa
			HashMap dstMap = new HashMap();
			DeliveryStateTypeW[] dstW = deliverystatetypeserver.getDeliveryStateTypes();

			for (int i = 0; i < dstW.length; i++) {
				DeliveryStateTypeW delstatetype = dstW[i];
				dstMap.put(delstatetype.getCode(), delstatetype);
			}

			// se obtienen los tipos de evaluaciones
			EvaluationTypeW[] evaluationtypes = evaluationtypeserver.getEvaluationTypes();
			HashMap evaluationTypesMap = new HashMap();
			for (int i = 0; i < evaluationtypes.length; i++) {
				evaluationTypesMap.put(evaluationtypes[i].getCode(), evaluationtypes[i]);
			}

			// Creación del Ranking
			RankingW ranking = new RankingW();
			ranking.setSince(since);
			ranking.setUntil(until);
			ranking.setWhen(new Date());
			// ranking = rankingserver.addRanking(ranking);
			ranking = addRanking(ranking);

			/** ******APROBACION********** */

			// Para obtener evaluaciones que indiquen aprobación, se analizan aquellos registros que presenten
			// "score" mayor a cero en la tabla de evaluación de recepción (RECEPTION-EVALUATION). Esto se da cuando
			// el lote se encuentra en estado recepcionado.
			DeliveryStateTypeW delstatetypeW = (DeliveryStateTypeW) dstMap.get("RECEPCIONADA");
			ReceptionEvaluationW[] receptionevaluations = receptionevaluationserver.getReceptionEvaluationsofDeliveryStateTypeBetweenDates(delstatetypeW.getId(), since, until);

			double ec = 0;
			double ns;
			// float weight;
			int totalavailable;
			int totalreceived;
			int oddlength;
			long deliveryid;
			int totalbinario;

			long vendorid;
			long departmentid;

			Totals totales = null;

			Long orderidL;
			
			String key = "";
			ArrayList<Long> datingList = null;
			Map<String, ArrayList<Long>> datingCountMap = new HashMap<String, ArrayList<Long>>();

			VendorDepartmentRankingW vendordepartmentanking = null;
			VendorRankingW vendorranking = null;
			// VendorRankingPK vendorRankPK = null;
			VendorDepartmentRankingPK vendordeprankPK = null;

			EvaluationTypeW evaluationtype = (EvaluationTypeW) evaluationTypesMap.get("APROBADA");
			OrderDeliveryDetailW[] orderdeliverydetails = null;
			OrderW[] orders = null;

			float A = (float) IMPORTANCE_RANKING / 100;

			// busco todas las citas
			HashMap<Long, DatingW> datingMap = getDating(receptionevaluations);

			// busco todas las OC
			// HashMap<Long, OrderW> orderMap = getOrders(datingMap);

			Long datingid;
			DatingW dating;
			for (int i = 0; i < receptionevaluations.length; i++) {

				datingid = receptionevaluations[i].getDatingid();

				// dating = datingserver.getById(datingid);
				dating = datingMap.get(datingid);
				deliveryid = dating.getDeliveryid();
				System.out.println("A- " + (i + 1) + " de " + receptionevaluations.length + ", lote;" + deliveryid);
				// orders = orderserver.getOrdersofDelivery(deliveryid);
				// orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where
				// odl.order = oc and odl.delivery.id = " + deliveryid);
				orders = getOrderByDelivery(deliveryid);

				/* calculo EC */
				ec = receptionevaluations[i].getScore();

				// nota a %
				ec = ec * 100; // / 7;

				/* calculo NS */
				ns = 0;
				totalbinario = 0;
				totalavailable = 0;
				totalreceived = 0;
				oddlength = 0;
				// busco detalles
				// orderdeliverydetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", deliveryid);
				orderdeliverydetails = getOrderDeliveryDetailByDelivery(deliveryid);

				ordersMap = new HashMap();

				for (int j = 0; j < orderdeliverydetails.length; j++) {

					orderidL = orderdeliverydetails[j].getOrderid();

					if (ordersMap.containsKey(orderidL)) {
						totales = (Totals) ordersMap.get(orderidL);
						totalavailable = totales.getTotalavailable();
						totalreceived = totales.getTotalreceived();
						totalbinario = totales.getTotalbinario();
						oddlength = totales.getOddlength();
					} else {
						totales = new Totals();
						totalavailable = 0;
						totalreceived = 0;
						totalbinario = 0;
						oddlength = 0;
					}
					// cálculo de total binario para aquellos detalles con cantidades disponibles mayores a cero
					if (orderdeliverydetails[j].getAvailableunits().doubleValue() == orderdeliverydetails[j].getReceivedunits().doubleValue() && orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						totalbinario++;
					// cuento los detalles que tienen cantidades disponibles mayor que cero para cálculo de NS
					if (orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						oddlength++;

					totalavailable += orderdeliverydetails[j].getAvailableunits();
					totalreceived += orderdeliverydetails[j].getReceivedunits();

					totales.setTotalavailable(totalavailable);
					totales.setTotalreceived(totalreceived);
					totales.setTotalbinario(totalbinario);
					totales.setOddlength(oddlength);
					ordersMap.put(orderidL, totales);
				}
				// ns = (float) totalbinario * 100 / (float) orderdeliverydetails.length;

				for (int j = 0; j < orders.length; j++) {

					vendorid = dating.getVendorid();
					departmentid = orders[j].getDepartmentid();

					//contador de citas
					key = vendorid + "_" + departmentid;
					datingList = datingCountMap.get(key);
					if(datingList == null)
						datingList = new ArrayList<Long>();
					
					if(!datingList.contains(dating.getId()))
						datingList.add(dating.getId());
					datingCountMap.put(key, datingList);
					//contador de citas

					OcDelEvaluationW ocdeldvaluation = new OcDelEvaluationW();
					ocdeldvaluation.setDelivery(deliveryid);
					ocdeldvaluation.setDepartmentid(departmentid);
					ocdeldvaluation.setEvaluationtypeid(evaluationtype.getId());

					if (ordersMap.containsKey(new Long(orders[j].getId()))) {
						totales = (Totals) ordersMap.get(new Long(orders[j].getId()));

						// no considera ordenes que no fueron entregadas en el PL pero si est�n en el despacho
						if (totales.getTotalavailable() == totales.getTotalreceived() && totales.getTotalavailable() == 0)
							continue;

						ocdeldvaluation.setTotalavailableunits((double) totales.totalavailable);
						ocdeldvaluation.setTotalreceivedunits((double) totales.totalreceived);
						// cálculo del NS para cada orden asociada al detalle
						if (totales.getOddlength() > 0)
							ns = (float) totales.getTotalbinario() * 100 / (float) totales.getOddlength();
						else
							ns = 0;

					}
					ocdeldvaluation.setDeliveryscore((ec * A) + (ns * (1 - A)));
					ocdeldvaluation.setLevelservicepercent(ns);
					ocdeldvaluation.setOrder(orders[j].getNumber());
					ocdeldvaluation.setQualitypercent(ec);
					ocdeldvaluation.setVendorid(vendorid);
					ocdeldvaluation.setRankingid(ranking.getId());
					ocdeldvaluation.setDatingdate(dating.getWhen());
					// se almacena el un ArrayList las OcDelEvaluations
					ocdelEvaluationsList.add(ocdeldvaluation);

					// VendorDepartmentRanking
					vendordeprankPK = new VendorDepartmentRankingPK();
					vendordeprankPK.setVendorid(new Long(vendorid));
					vendordeprankPK.setDepartmentid(new Long(departmentid));
					vendordeprankPK.setRankingid(new Long(ranking.getId()));
					if (vendorDeptRankingMap.containsKey(vendordeprankPK)) {
						vendordepartmentanking = (VendorDepartmentRankingW) vendorDeptRankingMap.get(vendordeprankPK);
					} else {
						vendordepartmentanking = new VendorDepartmentRankingW();
						vendordepartmentanking.setApprovedcount(0);
						vendordepartmentanking.setDepartmentid(new Long(departmentid));
						vendordepartmentanking.setNonassistancecount(0);
						vendordepartmentanking.setRejectedcount(0);
						vendordepartmentanking.setSumscoreweighed(0d);
						vendordepartmentanking.setTotalavailableunits(0d);
						vendordepartmentanking.setTotalreceivedunits(0d);
						vendordepartmentanking.setVendorid(new Long(vendorid));
						vendordepartmentanking.setRankingid(new Long(ranking.getId()));
					}

					vendordepartmentanking.setApprovedcount(datingCountMap.get(key).size());
					vendordepartmentanking.setSumscoreweighed(vendordepartmentanking.getSumscoreweighed() + ocdeldvaluation.getDeliveryscore() * ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalavailableunits(vendordepartmentanking.getTotalavailableunits() + ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalreceivedunits(vendordepartmentanking.getTotalreceivedunits() + ocdeldvaluation.getTotalreceivedunits());
					vendorDeptRankingMap.put(vendordeprankPK, vendordepartmentanking);

				}
			}
			/** ****** FIN APROBACION********** */

			/** ****** RECHAZO ********** */
			// Para obtener evaluaciones que indiquen rechazo, se analizan aquellos registros que presenten
			// lote en estado "Rechazada", lo que significa que su score en la tabla de evaluacion sea cero.
			// HashMap rejectionindicatorMap = new HashMap();
			datingCountMap.clear();

			DeliveryStateTypeW rejectedstateW = (DeliveryStateTypeW) dstMap.get("RECHAZADA");
			ReceptionEvaluationW[] rejectionevaluations = receptionevaluationserver.getReceptionEvaluationsofDeliveryStateTypeBetweenDates(rejectedstateW.getId(), since, until);

			long rejectionevaluationid = -1;
			ec = 0;
			ns = 0;

			totalavailable = 0;
			totalreceived = 0;
			deliveryid = -1;
			totalbinario = 0;
			evaluationtype = null;
			evaluationtype = (EvaluationTypeW) evaluationTypesMap.get("RECHAZADA");

			orderdeliverydetails = null;
			orders = null;
			A = (float) IMPORTANCE_RANKING / 100;
			for (int i = 0; i < rejectionevaluations.length; i++) {
				System.out.println("R- " + (i + 1) + " de " + rejectionevaluations.length);
				datingid = rejectionevaluations[i].getDatingid();

				dating = datingserver.getById(datingid);
				deliveryid = dating.getDeliveryid();
				// orders = orderserver.getOrdersofDelivery(deliveryid);
				// orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where
				// odl.order = oc and odl.delivery.id = " + deliveryid);
				orders = getOrderByDelivery(deliveryid);

				/* calculo EC */
				ec = 0;
				ec = rejectionevaluations[i].getScore();

				rejectionevaluationid = rejectionevaluations[i].getId();

				// nota a %
				ec = ec * 100; // / 7;

				/* calculo NS */
				ns = 0;
				totalbinario = 0;
				totalavailable = 0;
				totalreceived = 0;
				oddlength = 0;

				// busco detalles
				// orderdeliverydetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", deliveryid);
				orderdeliverydetails = getOrderDeliveryDetailByDelivery(deliveryid);

				ordersMap = new HashMap();

				for (int j = 0; j < orderdeliverydetails.length; j++) {

					orderidL = orderdeliverydetails[j].getOrderid();

					if (ordersMap.containsKey(orderidL)) {
						totales = (Totals) ordersMap.get(orderidL);
						totalavailable = totales.getTotalavailable();
						totalreceived = totales.getTotalreceived();
						totalbinario = totales.getTotalbinario();
						oddlength = totales.getOddlength();
					} else {
						totales = new Totals();
						totalavailable = 0;
						totalreceived = 0;
						totalbinario = 0;
						oddlength = 0;
					}
					if (orderdeliverydetails[j].getAvailableunits().doubleValue() == orderdeliverydetails[j].getReceivedunits().doubleValue() && orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						totalbinario++;

					if (orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						oddlength++;

					totalavailable += orderdeliverydetails[j].getAvailableunits();
					totalreceived += orderdeliverydetails[j].getReceivedunits();
					totales.setTotalavailable(totalavailable);
					totales.setTotalreceived(totalreceived);
					totales.setTotalbinario(totalbinario);
					totales.setOddlength(oddlength);
					ordersMap.put(orderidL, totales);
				}
				// ns = (float) totalbinario * 100 / (float) orderdeliverydetails.length;

				// se repite para cada orden que posee el delivery (IGOR)
				for (int j = 0; j < orders.length; j++) {
					vendorid = dating.getVendorid();
					departmentid = orders[j].getDepartmentid();
					
					
					
					//contador de citas
					key = vendorid + "_" + departmentid;
					datingList = datingCountMap.get(key);
					if(datingList == null)
						datingList = new ArrayList<Long>();
					
					if(!datingList.contains(dating.getId()))
						datingList.add(dating.getId());
					datingCountMap.put(key, datingList);
					//contador de citas
					
					
					
					
					OcDelEvaluationW ocdeldvaluation = new OcDelEvaluationW();
					ocdeldvaluation.setDelivery(deliveryid);

					if (ordersMap.containsKey(new Long(orders[j].getId()))) {
						totales = (Totals) ordersMap.get(new Long(orders[j].getId()));

						// no considera ordenes que no fueron entregadas en el PL pero si est�n en el despacho
						if (totales.getTotalavailable() == totales.getTotalreceived() && totales.getTotalavailable() == 0)
							continue;

						ocdeldvaluation.setTotalavailableunits((double) totales.totalavailable);
						ocdeldvaluation.setTotalreceivedunits((double) totales.totalreceived);
						// cálculo del NS para cada orden asociada al detalle
						if (totales.getOddlength() > 0)
							ns = (float) totales.getTotalbinario() * 100 / (float) totales.getOddlength();
						else
							ns = 0;
					}
					ocdeldvaluation.setDeliveryscore((ec * A) + (ns * (1 - A)));
					ocdeldvaluation.setDepartmentid(orders[j].getDepartmentid());
					ocdeldvaluation.setEvaluationtypeid(evaluationtype.getId());
					ocdeldvaluation.setLevelservicepercent(ns);
					ocdeldvaluation.setOrder(orders[j].getNumber());
					ocdeldvaluation.setQualitypercent(ec);
					ocdeldvaluation.setVendorid(dating.getVendorid());
					ocdeldvaluation.setDepartmentid(departmentid);
					ocdeldvaluation.setRankingid(ranking.getId());
					ocdeldvaluation.setDatingdate(dating.getWhen());

					ocdelEvaluationsList.add(ocdeldvaluation);

					// VendorDepartmentRanking
					vendordeprankPK = new VendorDepartmentRankingPK();
					vendordeprankPK.setVendorid(new Long(vendorid));
					vendordeprankPK.setDepartmentid(new Long(departmentid));
					vendordeprankPK.setRankingid(new Long(ranking.getId()));

					if (vendorDeptRankingMap.containsKey(vendordeprankPK)) {
						vendordepartmentanking = (VendorDepartmentRankingW) vendorDeptRankingMap.get(vendordeprankPK);
					} else {
						vendordepartmentanking = new VendorDepartmentRankingW();
						vendordepartmentanking.setApprovedcount(0);
						vendordepartmentanking.setDepartmentid(new Long(departmentid));
						vendordepartmentanking.setNonassistancecount(0);
						vendordepartmentanking.setRejectedcount(0);
						vendordepartmentanking.setSumscoreweighed(0d);
						vendordepartmentanking.setTotalavailableunits(0d);
						vendordepartmentanking.setTotalreceivedunits(0d);
						vendordepartmentanking.setVendorid(new Long(vendorid));
						vendordepartmentanking.setRankingid(new Long(ranking.getId()));

					}
					vendordepartmentanking.setRejectedcount(datingCountMap.get(key).size());
					vendordepartmentanking.setSumscoreweighed(vendordepartmentanking.getSumscoreweighed() + ocdeldvaluation.getDeliveryscore() * ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalavailableunits(vendordepartmentanking.getTotalavailableunits() + ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalreceivedunits(vendordepartmentanking.getTotalreceivedunits() + ocdeldvaluation.getTotalreceivedunits());
					vendorDeptRankingMap.put(vendordeprankPK, vendordepartmentanking);
				}
			}

			/** ****** FIN RECHAZO********** */

			/** ****** NO ASISTE********** */
			// evaluaciones
			// con score = 0 y lote en no asiste
			datingCountMap.clear();
			DeliveryStateTypeW nonsssistancesstateW = (DeliveryStateTypeW) dstMap.get("NO_ASISTE");
			ReceptionEvaluationW[] nonsssistances = receptionevaluationserver.getReceptionEvaluationsofDeliveryStateTypeBetweenDates(nonsssistancesstateW.getId(), since, until);

			ec = 0;
			ns = 0;

			totalavailable = 0;
			totalreceived = 0;
			deliveryid = -1;
			totalbinario = 0;
			evaluationtype = (EvaluationTypeW) evaluationTypesMap.get("NO_ASISTE");

			orderdeliverydetails = null;
			orders = null;
			A = (float) IMPORTANCE_RANKING / 100;
			System.out.println(nonsssistances.length);
			for (int i = 0; i < nonsssistances.length; i++) {
				System.out.println("NA- " + (i + 1) + " de " + nonsssistances.length);
				datingid = nonsssistances[i].getDatingid();

				dating = datingserver.getById(datingid);
				deliveryid = dating.getDeliveryid();
				// orders = orderserver.getOrdersofDelivery(deliveryid);
				// orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where
				// odl.order = oc and odl.delivery.id = " + deliveryid);
				orders = getOrderByDelivery(deliveryid);

				/* calculo EC, no tiene detalles de evaluacion */
				ec = 0;

				/* calculo NS */
				ns = 0;
				totalbinario = 0;
				totalavailable = 0;
				totalreceived = 0;
				oddlength = 0;
				// busco detalles
				// orderdeliverydetails = orderdeliverydetailserver.getByPropertyAsArray("id.deliveryid", deliveryid);
				orderdeliverydetails = getOrderDeliveryDetailByDelivery(deliveryid);
				ordersMap = new HashMap();

				for (int j = 0; j < orderdeliverydetails.length; j++) {

					orderidL = orderdeliverydetails[j].getOrderid();

					if (ordersMap.containsKey(orderidL)) {
						totales = (Totals) ordersMap.get(orderidL);
						totalavailable = totales.getTotalavailable();
						totalreceived = totales.getTotalreceived();
						totalbinario = totales.getTotalbinario();
						oddlength = totales.getOddlength();
					} else {
						totales = new Totals();
						totalavailable = 0;
						totalreceived = 0;
						oddlength = 0;
						totalbinario = 0;
					}
					if (orderdeliverydetails[j].getAvailableunits().doubleValue() == orderdeliverydetails[j].getReceivedunits().doubleValue() && orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						totalbinario++;
					if (orderdeliverydetails[j].getAvailableunits().doubleValue() > 0)
						oddlength++;
					totalavailable += orderdeliverydetails[j].getAvailableunits();
					totalreceived += orderdeliverydetails[j].getReceivedunits();
					totales.setTotalavailable(totalavailable);
					totales.setTotalreceived(totalreceived);
					totales.setTotalbinario(totalbinario);
					totales.setOddlength(oddlength);
					ordersMap.put(orderidL, totales);
				}
				// ns = (float) totalbinario * 100 / (float) orderdeliverydetails.length;

				// se repite para cada orden que posee el deliver (IGOR)
				for (int j = 0; j < orders.length; j++) {
					vendorid = dating.getVendorid();
					departmentid = orders[j].getDepartmentid();
					
					
					//contador de citas
					key = vendorid + "_" + departmentid;
					datingList = datingCountMap.get(key);
					if(datingList == null)
						datingList = new ArrayList<Long>();
					
					if(!datingList.contains(dating.getId()))
						datingList.add(dating.getId());
					datingCountMap.put(key, datingList);
					//contador de citas
					
					
					
					OcDelEvaluationW ocdeldvaluation = new OcDelEvaluationW();
					ocdeldvaluation.setDelivery(deliveryid);

					if (ordersMap.containsKey(new Long(orders[j].getId()))) {
						totales = (Totals) ordersMap.get(new Long(orders[j].getId()));

						// no considera ordenes que no fueron entregadas en el PL pero si est�n en el despacho
						if (totales.getTotalavailable() == totales.getTotalreceived() && totales.getTotalavailable() == 0)
							continue;

						ocdeldvaluation.setTotalavailableunits((double) totales.totalavailable);
						ocdeldvaluation.setTotalreceivedunits((double) totales.totalreceived);
						// cálculo del NS para cada orden asociada al detalle
						if (totales.getOddlength() > 0)
							ns = (float) totales.getTotalbinario() * 100 / (float) totales.getOddlength();
						else
							ns = 0;
					}
					ocdeldvaluation.setDeliveryscore((ec * A) + (ns * (1 - A)));
					ocdeldvaluation.setDepartmentid(orders[j].getDepartmentid());
					ocdeldvaluation.setEvaluationtypeid(evaluationtype.getId());
					ocdeldvaluation.setLevelservicepercent(ns);
					ocdeldvaluation.setOrder(orders[j].getNumber());
					ocdeldvaluation.setQualitypercent(ec);
					ocdeldvaluation.setVendorid(dating.getVendorid());
					ocdeldvaluation.setRankingid(ranking.getId());
					ocdeldvaluation.setDatingdate(dating.getWhen());

					ocdelEvaluationsList.add(ocdeldvaluation);

					// VendorDepartmentRanking
					vendordeprankPK = new VendorDepartmentRankingPK();
					vendordeprankPK.setVendorid(new Long(vendorid));
					vendordeprankPK.setDepartmentid(new Long(departmentid));
					vendordeprankPK.setRankingid(new Long(ranking.getId()));

					if (vendorDeptRankingMap.containsKey(vendordeprankPK)) {
						vendordepartmentanking = (VendorDepartmentRankingW) vendorDeptRankingMap.get(vendordeprankPK);
					} else {
						vendordepartmentanking = new VendorDepartmentRankingW();
						vendordepartmentanking.setApprovedcount(0);
						vendordepartmentanking.setDepartmentid(new Long(departmentid));
						vendordepartmentanking.setNonassistancecount(0);
						vendordepartmentanking.setRejectedcount(0);
						vendordepartmentanking.setSumscoreweighed(0d);
						vendordepartmentanking.setTotalavailableunits(0d);
						vendordepartmentanking.setTotalreceivedunits(0d);
						vendordepartmentanking.setVendorid(new Long(vendorid));
						vendordepartmentanking.setRankingid(new Long(ranking.getId()));
					}

					vendordepartmentanking.setNonassistancecount(datingCountMap.get(key).size());
					vendordepartmentanking.setSumscoreweighed(vendordepartmentanking.getSumscoreweighed() + ocdeldvaluation.getDeliveryscore() * ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalavailableunits(vendordepartmentanking.getTotalavailableunits() + ocdeldvaluation.getTotalavailableunits());
					vendordepartmentanking.setTotalreceivedunits(vendordepartmentanking.getTotalreceivedunits() + ocdeldvaluation.getTotalreceivedunits());
					vendorDeptRankingMap.put(vendordeprankPK, vendordepartmentanking);

				}
			}

			/** ****** FIN NO ASISTE********** */

			Iterator it = vendorDeptRankingMap.entrySet().iterator();
			Long vendoridL;
			Long rankingidL;

			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				vendordepartmentanking = (VendorDepartmentRankingW) e.getValue();

				// resumen por vendor
				vendoridL = vendordepartmentanking.getVendorid();
				rankingidL = vendordepartmentanking.getRankingid();
				if (vendorRankingMap.containsKey(vendoridL))
					vendorranking = (VendorRankingW) vendorRankingMap.get(vendoridL);
				else {
					vendorranking = new VendorRankingW();
					vendorranking.setApprovedcount(0);
					vendorranking.setNonassistancecount(0);
					vendorranking.setRejectedcount(0);
					vendorranking.setSumscoreweighed(0d);
					vendorranking.setTotalavailableunits(0d);
					vendorranking.setTotalreceivedunits(0d);
					vendorranking.setVendorid(vendoridL);
					vendorranking.setRankingid(rankingidL);
					// vendorranking = vendorrankingserver.addVendorRanking(vendorranking);
					vendorranking = addVendorRanking(vendorranking);
				}
				vendorranking.setApprovedcount(vendorranking.getApprovedcount() + vendordepartmentanking.getApprovedcount());
				vendorranking.setNonassistancecount(vendorranking.getNonassistancecount() + vendordepartmentanking.getNonassistancecount());
				vendorranking.setRejectedcount(vendorranking.getRejectedcount() + vendordepartmentanking.getRejectedcount());
				vendorranking.setSumscoreweighed(vendorranking.getSumscoreweighed() + vendordepartmentanking.getSumscoreweighed());
				vendorranking.setTotalavailableunits(vendorranking.getTotalavailableunits() + vendordepartmentanking.getTotalavailableunits());
				vendorranking.setTotalreceivedunits(vendorranking.getTotalreceivedunits() + vendordepartmentanking.getTotalreceivedunits());
				vendorRankingMap.put(vendoridL, vendorranking);
			}
			System.out.println("Guardado Ranking 1 ");
			Iterator it2 = vendorRankingMap.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry e = (Map.Entry) it2.next();
				vendorranking = (VendorRankingW) e.getValue();
				// vendorranking = vendorrankingserver.updateVendorRanking(vendorranking);
				vendorranking = updateVendorRanking(vendorranking);
			}
			System.out.println("Guardado Ranking 2 ");
			Iterator it3 = vendorDeptRankingMap.entrySet().iterator();
			while (it3.hasNext()) {
				Map.Entry e = (Map.Entry) it3.next();
				VendorDepartmentRankingW vdrTmp = (VendorDepartmentRankingW) e.getValue();
				// vendordepartmentrankingserver.addVendorDepartmentRanking(vdrTmp);
				addVendorDepartmentRanking(vdrTmp);

			}

			System.out.println("Guardado Ranking 3 ");
			// almaceno Ranking-Desp en la BD
			OcDelEvaluationW[] ocdelevaluationsW = null;
			ocdelevaluationsW = (OcDelEvaluationW[]) ocdelEvaluationsList.toArray(new OcDelEvaluationW[ocdelEvaluationsList.size()]);

			for (int j = 0; j < ocdelevaluationsW.length; j++) {
				OcDelEvaluationW ocdelevaluations = ocdelevaluationsW[j];
				// ocdelevaluationserver.addOcDelEvaluation(ocdelevaluations);
				addOcDelEvaluation(ocdelevaluations);
			}
			c.commit();

			System.out.println("FIN RANKING ");
			datingMap.clear();
			vendorDeptRankingMap.clear();
			vendorRankingMap.clear();
			ordersMap.clear();
			ocdelEvaluationsList.clear();
			dstMap.clear();
			evaluationTypesMap.clear();
		} catch (Exception e) {
			logger.error("DEMONIO_MAKE_RANKING: Error excepcional!");
			logger.info("******* FIN  CRON *******");
			getCtx().setRollbackOnly();
			closeConnection();
			e.printStackTrace();
		} finally {

		}
	}

	private HashMap getDating(ReceptionEvaluationW[] receptionevaluations) throws NotFoundException, OperationFailedException {
		int chunksize = 1000;
		HashMap<Long, DatingW> datingMap = new HashMap<Long, DatingW>();
		Object[] splitids = ClassUtilities.SplitArray(receptionevaluations, ReceptionEvaluationW.class, chunksize);
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];

			StringBuffer datingsid = new StringBuffer();
			for (int j = 0; j < ids.length - 1; j++) {
				datingsid.append(((ReceptionEvaluationW) ids[j]).getDatingid().longValue() + ",");
			}
			datingsid.append(((ReceptionEvaluationW) ids[ids.length - 1]).getDatingid().longValue());

			DatingW[] datings = datingserver.getByQueryAsArray("select dt from Dating as dt where dt.id in (" + datingsid.toString() + ")");

			for (int j = 0; j < datings.length; j++) {
				datingMap.put(datings[j].getId(), datings[j]);
			}
		}
		return datingMap;
	}

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	private class Totals {
		private int totalavailable;

		private int totalreceived;

		private int totalbinario;

		private int oddlength;

		/**
		 * @return
		 */
		public int getTotalavailable() {
			return totalavailable;
		}

		/**
		 * @return
		 */
		public int getTotalreceived() {
			return totalreceived;
		}

		/**
		 * @param i
		 */
		public void setTotalavailable(int i) {
			totalavailable = i;
		}

		/**
		 * @param i
		 */
		public void setTotalreceived(int i) {
			totalreceived = i;
		}

		/**
		 * @return
		 */
		public int getTotalbinario() {
			return totalbinario;
		}

		/**
		 * @param i
		 */
		public void setTotalbinario(int i) {
			totalbinario = i;
		}

		/**
		 * @return
		 */
		public int getOddlength() {
			return oddlength;
		}

		/**
		 * @param i
		 */
		public void setOddlength(int i) {
			oddlength = i;
		}

	}

	private Connection c = null;

	private void closeConnection() {
		try {
			if (c != null) {
				System.out.println("Cerrando conección directa");
				c.close();
				c = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(B2BSystemPropertiesUtil.getProperty("rankingurldb"), B2BSystemPropertiesUtil.getProperty("rankinguser"), B2BSystemPropertiesUtil.getProperty("rankingpass"));
			c.setAutoCommit(false);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
		} finally {

		}
	}

	private OrderW[] getOrderByDelivery(Long id) {
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<OrderW> orderlist = new ArrayList<OrderW>();
		try {
			if (c == null)
				openConnection();

			stmt = c.createStatement();
			String SQL = "SELECT distinct o.id, o.number, o.department_id from logistica.order_tmprkg o inner join logistica.orderdeliverydetail_tmprkg odd on (o.id=odd.order_id) where odd.delivery_id=" + id.longValue() + ";";
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				OrderW order = new OrderW();
				order.setId(rs.getLong("id"));
				order.setNumber(rs.getLong("number"));
				order.setDepartmentid(rs.getLong("department_id"));
				orderlist.add(order);
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (orderlist.size() == 0)
			System.out.println("Despacho " + id.longValue() + " no encontr� OC");

		return orderlist.toArray(new OrderW[orderlist.size()]);
	}

	private OrderDeliveryDetailW[] getOrderDeliveryDetailByDelivery(Long id) {
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<OrderDeliveryDetailW> oddlist = new ArrayList<OrderDeliveryDetailW>();
		try {
			if (c == null)
				openConnection();

			stmt = c.createStatement();
			String SQL = "SELECT * from logistica.orderdeliverydetail_tmprkg odd where odd.delivery_id=" + id.longValue() + ";";
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				OrderDeliveryDetailW odd = new OrderDeliveryDetailW();
				odd.setOrderid(rs.getLong("order_id"));
				odd.setAvailableunits(rs.getDouble("availableunits"));
				odd.setReceivedunits(rs.getDouble("receivedunits"));
				oddlist.add(odd);
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return oddlist.toArray(new OrderDeliveryDetailW[oddlist.size()]);
	}

	SimpleDateFormat sdfpsql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private void prepareData(Date since, Date until) {
		Statement stmt = null;
		try {

			// limpia tabla temporales
			if (c == null)
				openConnection();

			stmt = c.createStatement();
			String SQL = "drop table IF EXISTS logistica.order_tmprkg;";
			stmt.executeUpdate(SQL);
			String SQL2 = "drop table IF EXISTS logistica.orderdeliverydetail_tmprkg;";
			stmt.executeUpdate(SQL2);
			c.commit();

			if (c == null)
				openConnection();
			stmt = c.createStatement();
			String SQL3 = "SELECT * INTO logistica.order_tmprkg FROM logistica.order WHERE id in (SELECT distinct(O.ID) FROM LOGISTICA.RECEPTIONEVALUATION RE INNER JOIN LOGISTICA.DATING DT ON (RE.DATING_ID = DT.ID) INNER JOIN LOGISTICA.DELIVERY D ON (DT.DELIVERY_ID = D.ID) "
					+ "INNER JOIN LOGISTICA.ORDERDELIVERYDETAIL ODD ON (DT.DELIVERY_ID = ODD.DELIVERY_ID) INNER JOIN LOGISTICA.ORDER O ON (O.ID = ODD.ORDER_ID) where RE.WHEN1 >= '" + sdfpsql.format(since) + "' AND RE.WHEN1 < '" + sdfpsql.format(until) + "')";
			stmt.executeUpdate(SQL3);

			String SQL4 = "SELECT * INTO logistica.orderdeliverydetail_tmprkg FROM logistica.orderdeliverydetail WHERE delivery_id in (SELECT distinct(D.ID) FROM LOGISTICA.RECEPTIONEVALUATION RE INNER JOIN LOGISTICA.DATING DT ON (RE.DATING_ID = DT.ID) "
					+ "INNER JOIN LOGISTICA.DELIVERY D ON (DT.DELIVERY_ID = D.ID) INNER JOIN LOGISTICA.ORDERDELIVERYDETAIL ODD ON (DT.DELIVERY_ID = ODD.DELIVERY_ID) where RE.WHEN1 >= '" + sdfpsql.format(since) + "' AND RE.WHEN1 < '" + sdfpsql.format(until) + "')";
			stmt.executeUpdate(SQL4);
			String SQL5 = "CREATE INDEX orderdeliverydetail_tmprkg_delivery_id_idx  ON logistica.orderdeliverydetail_tmprkg  USING btree(delivery_id);";
			stmt.executeUpdate(SQL5);
			c.commit();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
		}

	}

	private RankingW addRanking(RankingW ranking) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		long idRanking = -1;
		try {
			if (c == null)
				openConnection();

			stmt = c.createStatement();
			String SQL = "insert into LOGISTICA.RANKING (when1, since, until) values ('" + sdfpsql.format(ranking.getWhen()) + "', '" + sdfpsql.format(ranking.getSince()) + "', '" + sdfpsql.format(ranking.getUntil()) + "')";
			stmt.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				idRanking = rs.getLong(1);
			}
			ranking.setId(idRanking);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			throw new Exception("Error en addRanking");
		}
		return ranking;
	}

	private VendorRankingW addVendorRanking(VendorRankingW vendorranking) throws Exception {

		Statement stmt = null;
		try {

			stmt = c.createStatement();
			String SQL = "insert into LOGISTICA.VENDORRANKING (totalreceivedunits, totalavailableunits, sumscoreweighed, rejectedcount, nonassistancecount, approvedcount, VENDOR_ID, RANKING_ID) values (" + vendorranking.getTotalreceivedunits().doubleValue() + ", "
					+ vendorranking.getTotalavailableunits().doubleValue() + ", " + vendorranking.getSumscoreweighed().doubleValue() + ", " + vendorranking.getRejectedcount().intValue() + ", " + vendorranking.getNonassistancecount().intValue() + ", " + vendorranking.getApprovedcount().intValue()
					+ "," + vendorranking.getVendorid().longValue() + "," + vendorranking.getRankingid().longValue() + ")";
			int i = stmt.executeUpdate(SQL);
			if (i <= 0)
				System.out.println("Error insertando: " + SQL);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			throw new Exception("Error en addVendorRanking");
		}
		return vendorranking;
	}

	private VendorRankingW updateVendorRanking(VendorRankingW vendorranking) throws Exception {

		Statement stmt = null;
		try {

			stmt = c.createStatement();
			String SQL = "update LOGISTICA.VENDORRANKING set totalreceivedunits=" + vendorranking.getTotalreceivedunits().doubleValue() + ", totalavailableunits=" + vendorranking.getTotalavailableunits().doubleValue() + ", sumscoreweighed=" + vendorranking.getSumscoreweighed().doubleValue()
					+ ", rejectedcount=" + vendorranking.getRejectedcount().intValue() + ", nonassistancecount=" + vendorranking.getNonassistancecount().intValue() + ", approvedcount=" + vendorranking.getApprovedcount().intValue() + " where VENDOR_ID=" + vendorranking.getVendorid().longValue()
					+ " and RANKING_ID=" + vendorranking.getRankingid().longValue() + "";
			int i = stmt.executeUpdate(SQL);
			if (i <= 0)
				System.out.println("Error update: " + SQL);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			throw new Exception("Error en updateVendorRanking");
		}
		return vendorranking;
	}

	private VendorDepartmentRankingW addVendorDepartmentRanking(VendorDepartmentRankingW vendordepartmentranking) throws Exception {

		Statement stmt = null;
		try {

			stmt = c.createStatement();
			String SQL = "insert into LOGISTICA.VENDORDEPARTMENTRANKING (totalreceivedunits, totalavailableunits, sumscoreweighed, " + "rejectedcount, nonassistancecount, approvedcount, VENDOR_ID, RANKING_ID, DEPARTMENT_ID) values (" + vendordepartmentranking.getTotalreceivedunits().doubleValue()
					+ ", " + vendordepartmentranking.getTotalavailableunits().doubleValue() + ", " + vendordepartmentranking.getSumscoreweighed().doubleValue() + ", " + vendordepartmentranking.getRejectedcount().intValue() + ", " + vendordepartmentranking.getNonassistancecount().intValue() + ", "
					+ vendordepartmentranking.getApprovedcount().intValue() + ", " + vendordepartmentranking.getVendorid().longValue() + ", " + vendordepartmentranking.getRankingid().longValue() + ", " + vendordepartmentranking.getDepartmentid().longValue() + ")";
			int i = stmt.executeUpdate(SQL);
			if (i <= 0)
				System.out.println("Error insertando: " + SQL);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			throw new Exception("Error en addVendorDepartmentRanking");
		}
		return vendordepartmentranking;
	}

	private OcDelEvaluationW addOcDelEvaluation(OcDelEvaluationW ccDelEvaluation) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		long idoceval = -1;

		try {

			stmt = c.createStatement();
			String SQL = "INSERT INTO logistica.ocdelevaluation( delivery, order1, datingdate, " + "deliveryscore, qualitypercent,levelservicepercent, totalreceivedunits, totalavailableunits, evaluationtype_id, vendor_id, ranking_id, department_id)VALUES ( "
					+ ccDelEvaluation.getDelivery().longValue() + ", " + ccDelEvaluation.getOrder().longValue() + ", '" + sdfpsql.format(ccDelEvaluation.getDatingdate()) + "', " + ccDelEvaluation.getDeliveryscore().doubleValue() + ", " + ccDelEvaluation.getQualitypercent().doubleValue() + ","
					+ ccDelEvaluation.getLevelservicepercent().doubleValue() + ", " + ccDelEvaluation.getTotalreceivedunits().doubleValue() + ", " + ccDelEvaluation.getTotalavailableunits().doubleValue() + ", " + ccDelEvaluation.getEvaluationtypeid().longValue() + ", "
					+ ccDelEvaluation.getVendorid().longValue() + ", " + ccDelEvaluation.getRankingid().longValue() + ", " + ccDelEvaluation.getDepartmentid().longValue() + ")";
			stmt.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				idoceval = rs.getLong(1);
			}
			ccDelEvaluation.setId(idoceval);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			throw new Exception("Error en addRanking");
		}
		return ccDelEvaluation;
	}
}
