package bbr.b2b.regional.logistic.managers.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.CheckBuyOrderStateManagerRemote;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;

@Stateless(name = "managers/CheckBuyOrderStateManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CheckBuyOrderStateManager implements CheckBuyOrderStateManagerLocal, CheckBuyOrderStateManagerRemote {

	private static Logger logger = Logger.getLogger(CheckBuyOrderStateManager.class);	

	private static boolean isServiceStarted = false;

	@Resource
	private SessionContext ctx;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	DirectOrderServerLocal directorderserver;
		
	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de Vencimiento de Ordenes");
		isServiceStarted = true;
	}

	@Timeout
	@TransactionTimeout(value = 3600)
	public synchronized void timeoutHandler(Timer timer) throws OperationFailedException, NotFoundException {
		try{
			if (RegionalLogisticConstants.getInstance().getSTOP_CHECK_STATE() && !isServiceStarted)
				return;
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				
			Calendar calnow = Calendar.getInstance();
			calnow.setTime(today);
			logger.info(" ----- " + sdf.format(today) + "  Evento Timer Recibido: " + timer.getInfo() + " ----- ");
	
			Integer countorders = 0;
			
			// Llama a procedimiento almacenado de vencimiento de oc					
			countorders = orderserver.doExpireBuyOrders();		
			logger.info("Se vencieron " + countorders + " ??rdenes de compra");	
					
			// Llama a procedimiento almacenado de recepcion de oc directa
			countorders = directorderserver.doReceiveDirectOrders();		
			logger.info("Se recibieron " + countorders + " ??rdenes directas");				
			
		}catch (Exception e) {
			logger.error("DEMONIO_CAMBIO_ESTADO: Error excepcional!");
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
}
