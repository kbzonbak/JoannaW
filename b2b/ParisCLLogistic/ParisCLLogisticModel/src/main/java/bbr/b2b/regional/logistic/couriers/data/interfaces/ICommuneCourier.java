package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICommuneCourier extends IElement {

	public String getPariscommune();

	public void setPariscommune(String pariscommune);

	public String getCouriercommune();

	public void setCouriercommune(String couriercommune);

}
