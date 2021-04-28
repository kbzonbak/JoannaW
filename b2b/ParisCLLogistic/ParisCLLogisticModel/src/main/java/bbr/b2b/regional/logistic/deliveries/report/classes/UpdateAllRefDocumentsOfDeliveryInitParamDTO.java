package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UpdateAllRefDocumentsOfDeliveryInitParamDTO implements Serializable {

	private String locationcode;
	private Long deliveryid;
	private String refdocumentnumber;
	private String newrefdocumentnumber;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getRefdocumentnumber() {
		return refdocumentnumber;
	}
	public void setRefdocumentnumber(String refdocumentnumber) {
		this.refdocumentnumber = refdocumentnumber;
	}
	public String getNewrefdocumentnumber() {
		return newrefdocumentnumber;
	}
	public void setNewrefdocumentnumber(String newrefdocumentnumber) {
		this.newrefdocumentnumber = newrefdocumentnumber;
	}	
}
