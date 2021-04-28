package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class ReceptionReportDataDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private Long ordertypeid;
	private String ordertypecode;
	private String ordertypename;
	private Long deliverylocationid;
	private String deliverylocationcode;
	private String deliverylocationname;
	private Long taxdocumentid;
	private Long taxdocumentnumber;
	private Double amount;
	private String receptionnumber;
	private String receptiondate;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getOrdertypeid() {
		return ordertypeid;
	}
	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
	}
	public String getOrdertypecode() {
		return ordertypecode;
	}
	public void setOrdertypecode(String ordertypecode) {
		this.ordertypecode = ordertypecode;
	}
	public String getOrdertypename() {
		return ordertypename;
	}
	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename;
	}
	public Long getDeliverylocationid() {
		return deliverylocationid;
	}
	public void setDeliverylocationid(Long deliverylocationid) {
		this.deliverylocationid = deliverylocationid;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public Long getTaxdocumentid() {
		return taxdocumentid;
	}
	public void setTaxdocumentid(Long taxdocumentid) {
		this.taxdocumentid = taxdocumentid;
	}
	public Long getTaxdocumentnumber() {
		return taxdocumentnumber;
	}
	public void setTaxdocumentnumber(Long taxdocumentnumber) {
		this.taxdocumentnumber = taxdocumentnumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getReceptionnumber() {
		return receptionnumber;
	}
	public void setReceptionnumber(String receptionnumber) {
		this.receptionnumber = receptionnumber;
	}
	public String getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(String receptiondate) {
		this.receptiondate = receptiondate;
	}	
}
