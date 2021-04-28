package bbr.b2b.portal.marketplace.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IMarketPlaceManager extends IGenericEJBInterface{
	
	public BaseResultDTO doAuthenticateML(String vendorcode, String code, Long uskey);
}
