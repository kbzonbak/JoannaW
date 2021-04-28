package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UploadPredistributedPackingListDataDTO implements Serializable {

	private String asnnumber;
	private String ordernumber;
	private String flowtypename;
	
	public String getAsnnumber() {
		return asnnumber;
	}
	public void setAsnnumber(String asnnumber) {
		this.asnnumber = asnnumber;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
	
}
