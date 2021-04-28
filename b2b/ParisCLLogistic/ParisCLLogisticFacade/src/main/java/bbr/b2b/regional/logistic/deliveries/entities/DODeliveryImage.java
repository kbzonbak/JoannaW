package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryImage;

public class DODeliveryImage implements IDODeliveryImage {

	private Long id;
	private String filename;
	private Date currenttimestamp;
	private String receivername;
	private String receiverrut;
	private Date receptiondate;
	private TruckDispatcher truckdispatcher;
	private DODelivery dodelivery;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public TruckDispatcher getTruckdispatcher() {
		return truckdispatcher;
	}
	public void setTruckdispatcher(TruckDispatcher truckdispatcher) {
		this.truckdispatcher = truckdispatcher;
	}
	public DODelivery getDodelivery() {
		return dodelivery;
	}
	public void setDodelivery(DODelivery dodelivery) {
		this.dodelivery = dodelivery;
	}
	
}
