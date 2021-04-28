package bbr.b2b.regional.logistic.vev.rest.interfaces;

import javax.ws.rs.core.Response;

import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryWSInitParamDTO;

public interface IDODeliveryAPIRESTManager {

	public Response sayHello();
	public Response doAddDODeliveryOfDirectOrdersWS(AddDODeliveryOfDirectOrdersWSInitParamDTO initParams);
	public Response doUpdateDODeliveryWS(UpdateDODeliveryWSInitParamDTO initParams);
}