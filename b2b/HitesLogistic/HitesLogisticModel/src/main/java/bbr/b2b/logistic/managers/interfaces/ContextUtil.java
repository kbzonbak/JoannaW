package bbr.b2b.logistic.managers.interfaces;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ContextUtil {

	private static ContextUtil instance = null;

	public static ContextUtil getInstance() {
		if (instance == null) {
			synchronized (ContextUtil.class) {
				instance = new ContextUtil();
			}
		}
		return instance;
	}

	private InitialContext getInitialContext() throws NamingException {
		Properties env = new Properties();
		env.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		InitialContext anotherctx = new InitialContext(env);
		return anotherctx;
	}
	
	public IBuyOrderReportManager getBuyOrderReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IBuyOrderReportManager manager = (IBuyOrderReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/BuyOrderReportManager!bbr.b2b.logistic.managers.interfaces.BuyOrderReportManagerRemote");
		return manager;
	}
	
	public IDeliveryReportManager getDeliveryReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDeliveryReportManager manager = (IDeliveryReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/DeliveryReportManager!bbr.b2b.logistic.managers.interfaces.DeliveryReportManagerRemote");
		return manager;
	}
	
	public IDatingReportManager getDatingReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDatingReportManager manager = (IDatingReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/DatingReportManager!bbr.b2b.logistic.managers.interfaces.DatingReportManagerRemote");
		return manager;
	}
	
	
	public ILogisticReportManager getLogisticReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		ILogisticReportManager manager = (ILogisticReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/LogisticReportManager!bbr.b2b.logistic.managers.interfaces.LogisticReportManagerRemote"); 
		return manager;
	}
	
	public INotificationsReportManager getNotificationsReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		INotificationsReportManager manager = (INotificationsReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/NotificationsReportManager!bbr.b2b.logistic.managers.interfaces.NotificationsReportManagerRemote"); 
		return manager;
	}
	
	public IVeVReportManager getVeVReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IVeVReportManager manager = (IVeVReportManager) ctx.lookup("ejb:HitesLogisticEAR/HitesLogisticManager//managers/VeVReportManager!bbr.b2b.logistic.managers.interfaces.VeVReportManagerRemote"); 
		return manager;
	}
	
}
