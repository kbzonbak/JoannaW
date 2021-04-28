package bbr.b2b.portal.classes.factory;

public class EJBFactory {
	
	public static UserEJBFactory getUserEJBFactory(){
		return UserEJBFactory.getFactory();		
	}
	
	public static CustomerEJBFactory getCustomerEJBFactory(){
		return CustomerEJBFactory.getFactory();		
	}

	public static OpenServicesEJBFactory getUserOpenEJBFactory(){
		return OpenServicesEJBFactory.getFactory();		
	}	

	public static MessageEJBFactory getMessageEJBFactory(){
		return MessageEJBFactory.getFactory();		
	}	

	public static MonitorEJBFactory getMonitorEJBFactory(){
		return MonitorEJBFactory.getFactory();		
	}
	
	public static FEPEJBFactory getFEPEJBFactory(){
		return FEPEJBFactory.getFactory();
	}
	
	public static StockpoolEJBFactory getStockpoolEJBFactory(){
		return StockpoolEJBFactory.getFactory();
	}
	
	public static MarketPlaceEJBFactory getMarketPlaceEJBFactory(){
		return MarketPlaceEJBFactory.getFactory();
	}
	
}
