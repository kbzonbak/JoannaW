package bbr.b2b.logistic.managers.classes;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal;

@Stateless(name = "managers/CheckBuyOrderStateManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CheckBuyOrderStateManager implements CheckBuyOrderStateManagerLocal {

private static Logger logger = Logger.getLogger(CheckBuyOrderStateManager.class);	
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@Resource
	private SessionContext ctx;
	
	
	@TransactionTimeout(3600)
	public synchronized void doProcess() throws Exception {
	
		try{
			Integer countdvrorders = 0;

			// Llama a función SQL vencimiento de dvroc
			countdvrorders = dvrorderserver.doExpireDvrOrders();		
			logger.info("Se vencieron " + countdvrorders + " órdenes de compra DVR");
			
		} catch (Exception e) {
			logger.error("DEMONIO_CAMBIO_ESTADO: Error excepcional!");
			e.printStackTrace();
			getCtx().setRollbackOnly();
			throw e;
		}
	}
	

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
}
