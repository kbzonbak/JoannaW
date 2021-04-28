package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICorreosChileTag extends IElement {

	public String getAbbreviationcenter();

	public void setAbbreviationcenter(String abbreviationcenter);

	public String getDelegationcode();

	public void setDelegationcode(String delegationcode);

	public String getDelegationname();

	public void setDelegationname(String delegationname);

	public String getDestinationaddress();

	public void setDestinationaddress(String destinationaddress);

	public String getRoutingcode();

	public void setRoutingcode(String routingcode);

	public String getRecordsend();

	public void setRecordsend(String recordsend);

	public String getDestinationcommune();

	public void setDestinationcommune(String destinationcommune);

	public String getAbbreviationservice();

	public void setAbbreviationservice(String abbreviationservice);

	public String getAdmissioncode();

	public void setAdmissioncode(String admissioncode);
	
	public String getCuartel();

	public void setCuartel(String cuartel);

	public String getSector();

	public void setSector(String sector);

	public String getSdp();

	public void setSdp(String sdp);

	public String getMovil();

	public void setMovil(String movil);

}
