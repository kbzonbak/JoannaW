package bbr.b2b.regional.logistic.evaluations.report.classes;

import java.io.Serializable;


public class EvaluationDetailReportDataDTO implements Serializable {

	private Long deliveryid;   
	private String vendorrut;
	private String vendorname;				
	private Long deliverynumber;    
	private String deliverystatetype;
	private String datingdate;
	private Double receptionscore;
	private String receptionerrorcode;
	private String receptionerrorname;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getDeliverystatetype() {
		return deliverystatetype;
	}
	public void setDeliverystatetype(String deliverystatetype) {
		this.deliverystatetype = deliverystatetype;
	}
	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	public Double getReceptionscore() {
		return receptionscore;
	}
	public void setReceptionscore(Double receptionscore) {
		this.receptionscore = receptionscore;
	}
	public String getReceptionerrorcode() {
		return receptionerrorcode;
	}
	public void setReceptionerrorcode(String receptionerrorcode) {
		this.receptionerrorcode = receptionerrorcode;
	}
	public String getReceptionerrorname() {
		return receptionerrorname;
	}
	public void setReceptionerrorname(String receptionerrorname) {
		this.receptionerrorname = receptionerrorname;
	}	
}
