package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.entities.Courier;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationDTO;

public interface ICourierServer  extends IElementServer<Courier, CourierW>  {
	
	CourierInformationDTO[] getCourierInformation(Long vendorid);
	CourierInformationDTO[] getCourierInformationByDODelivery(Long dodeliveryid);
	Long getCourierIdByDODelivery(Long dodeliveryid);
	Long getCourierIdByVendor(Long vendorid);
	
}
