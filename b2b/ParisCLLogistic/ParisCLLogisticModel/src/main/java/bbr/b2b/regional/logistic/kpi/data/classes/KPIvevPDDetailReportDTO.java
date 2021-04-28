package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevPDDetailReportDTO implements Serializable{
	
	private Long id;
	private Long ordernumber;
	private String orderrequest;
	private String departmentcode;
	private String departmentname;
	private String salestorecode;
	private String salestorename;
	private String currentorderstate;
	private String currentdeliverystate;
	private String sendingdate;
	private String deliverycurrentdate;
	private String fcm;
	private String frep;
	private String iteminternalcode;
	private String description;
	private String itemname;
	private Double requestedunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrderrequest() {
		return orderrequest;
	}
	public void setOrderrequest(String orderrequest) {
		this.orderrequest = orderrequest;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getCurrentorderstate() {
		return currentorderstate;
	}
	public void setCurrentorderstate(String currentorderstate) {
		this.currentorderstate = currentorderstate;
	}
	public String getCurrentdeliverystate() {
		return currentdeliverystate;
	}
	public void setCurrentdeliverystate(String currentdeliverystate) {
		this.currentdeliverystate = currentdeliverystate;
	}
	public String getSendingdate() {
		return sendingdate;
	}
	public void setSendingdate(String sendingdate) {
		this.sendingdate = sendingdate;
	}
	public String getDeliverycurrentdate() {
		return deliverycurrentdate;
	}
	public void setDeliverycurrentdate(String deliverycurrentdate) {
		this.deliverycurrentdate = deliverycurrentdate;
	}
	public String getFcm() {
		return fcm;
	}
	public void setFcm(String fcm) {
		this.fcm = fcm;
	}
	public String getFrep() {
		return frep;
	}
	public void setFrep(String frep) {
		this.frep = frep;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getSalestorecode() {
		return salestorecode;
	}
	public void setSalestorecode(String salestorecode) {
		this.salestorecode = salestorecode;
	}
	public String getSalestorename() {
		return salestorename;
	}
	public void setSalestorename(String salestorename) {
		this.salestorename = salestorename;
	}
	public Double getRequestedunits() {
		return requestedunits;
	}
	public void setRequestedunits(Double requestedunits) {
		this.requestedunits = requestedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	
}