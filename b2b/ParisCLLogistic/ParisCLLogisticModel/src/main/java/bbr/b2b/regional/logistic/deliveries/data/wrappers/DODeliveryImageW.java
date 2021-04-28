package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryImage;

public class DODeliveryImageW extends ElementDTO implements IDODeliveryImage  {

	private Date currenttimestamp;
	private String receivername;
	private String receiverrut;
	private Date receptiondate;
	private Long truckdispatcherid;
	private Long dodeliveryid;
	private String filename;

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getCurrenttimestamp() {
		return currenttimestamp;
	}
	public void setCurrenttimestamp(Date currenttimestamp) {
		this.currenttimestamp = currenttimestamp;
	}
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getReceiverrut() {
		return receiverrut;
	}
	public void setReceiverrut(String receiverrut) {
		this.receiverrut = receiverrut;
	}
	public Date getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(Date receptiondate) {
		this.receptiondate = receptiondate;
	}
	public Long getTruckdispatcherid() {
		return truckdispatcherid;
	}
	public void setTruckdispatcherid(Long truckdispatcherid) {
		this.truckdispatcherid = truckdispatcherid;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
}
