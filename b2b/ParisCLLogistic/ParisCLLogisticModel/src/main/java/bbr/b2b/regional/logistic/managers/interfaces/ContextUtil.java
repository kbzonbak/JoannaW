package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextUtil {

	private static ContextUtil instance = null;

	public static ContextUtil getInstance() {
		if (instance == null)
			synchronized (ContextUtil.class) {
				instance = new ContextUtil();
			}
		return instance;
	}

	private InitialContext getInitialContext() throws NamingException {
		Properties env = new Properties();
		env.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		InitialContext anotherctx = new InitialContext(env);

		return anotherctx;

	}
	
	public ILogisticReportManager getLogisticReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		ILogisticReportManager manager = (ILogisticReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/LogisticReportManager!bbr.b2b.regional.logistic.managers.interfaces.LogisticReportManagerRemote");
		return manager;		
	}
	
	public IBuyOrderReportManager getBuyOrderReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IBuyOrderReportManager manager = (IBuyOrderReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/BuyOrderReportManager!bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerRemote");
		return manager;	
	}
	
	public IDirectOrderReportManager getDirectOrderReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDirectOrderReportManager manager = (IDirectOrderReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/DirectOrderReportManager!bbr.b2b.regional.logistic.buyorders.managers.interfaces.DirectOrderReportManagerRemote");
		return manager;	
	}
	
	public IDeliveryReportManager getDeliveryReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDeliveryReportManager manager = (IDeliveryReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/DeliveryReportManager!bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerRemote");
		return manager;	
	}
	
	public IDODeliveryReportManager getDODeliveryReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDODeliveryReportManager manager = (IDODeliveryReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/DODeliveryReportManager!bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerRemote");
		return manager;	
	}
	
	public IDatingReportManager getDatingReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDatingReportManager manager = (IDatingReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/DatingReportManager!bbr.b2b.regional.logistic.datings.managers.interfaces.DatingReportManagerRemote");
		return manager;	
	}	
	
	public ITaxDocumentReportManager getTaxDocumentReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		ITaxDocumentReportManager manager = (ITaxDocumentReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/TaxDocumentReportManager!bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.TaxDocumentReportManagerRemote");
		return manager;	
	}
	
	public IDOTaxDocumentReportManager getDOTaxDocumentReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IDOTaxDocumentReportManager manager = (IDOTaxDocumentReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/DOTaxDocumentReportManager!bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.DOTaxDocumentReportManagerRemote");
		return manager;	
	}
	
	public IEvaluationReportManager getEvaluationReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IEvaluationReportManager manager = (IEvaluationReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/EvaluationReportManager!bbr.b2b.regional.logistic.evaluations.managers.interfaces.EvaluationReportManagerRemote");
		return manager;	
	}
	
	public IKPIReportManager getKPIReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IKPIReportManager manager = (IKPIReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/KPIReportManager!bbr.b2b.regional.logistic.kpi.managers.interfaces.KPIReportManagerRemote");
		return manager;		
	}
	
	public IMobileDeliveryReportManager getMobileDeliveryReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		IMobileDeliveryReportManager manager = (IMobileDeliveryReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/MobileDeliveryReportManager!bbr.b2b.regional.logistic.mobile.managers.interfaces.MobileDeliveryReportManagerRemote");
		return manager;
	}
	
	public INotificationsReportManager getNotificationsReportManager() throws NamingException {
		InitialContext ctx = ContextUtil.getInstance().getInitialContext();
		INotificationsReportManager manager = (INotificationsReportManager) ctx.lookup("ejb:ParisCLLogisticEAR/ParisCLLogisticManager//managers/NotificationsReportManager!bbr.b2b.regional.logistic.notifications.managers.interfaces.NotificationsReportManagerRemote");
		return manager;
	}
}
