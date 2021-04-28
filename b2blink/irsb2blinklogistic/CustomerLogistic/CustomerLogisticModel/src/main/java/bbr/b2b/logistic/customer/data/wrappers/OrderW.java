package bbr.b2b.logistic.customer.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IOrder;

public class OrderW extends ElementDTO implements IOrder {

	private Date created;
	private Long number;
	private String ticket;
	private String receiptnumber;
	private String request;
	private String status;
	private String numref1;
	private String numref2;
	private String numref3;
	private Double total;
	private Date issue_date;
	private Date effectiv_date;
	private Date expiration_date;
	private Date commitment_date;
	private String payment_condition;
	private String observation;
	private String responsible;
	private String responsible_email;
	private Boolean valid;
	private Boolean complete;
	private Long buyerid;
	private Long vendorid;
	private Long ordertypeid;
	private Long soastatetypeid;
	private Long orderstatetypeid;
	private Long deliveryplaceid;
	private Long saleplaceid;
	private Long clientid;
	private Long sectionid;
	private Long actionid;
	private String currency;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}
	public Date getEffectiv_date() {
		return effectiv_date;
	}
	public void setEffectiv_date(Date effectiv_date) {
		this.effectiv_date = effectiv_date;
	}
	public Date getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}
	public Date getCommitment_date() {
		return commitment_date;
	}
	public void setCommitment_date(Date commitment_date) {
		this.commitment_date = commitment_date;
	}
	public String getPayment_condition() {
		return payment_condition;
	}
	public void setPayment_condition(String payment_condition) {
		this.payment_condition = payment_condition;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public String getResponsible_email() {
		return responsible_email;
	}
	public void setResponsible_email(String responsible_email) {
		this.responsible_email = responsible_email;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public Long getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(Long buyerid) {
		this.buyerid = buyerid;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getOrdertypeid() {
		return ordertypeid;
	}
	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
	}
	public Long getSoastatetypeid() {
		return soastatetypeid;
	}
	public void setSoastatetypeid(Long soastatetypeid) {
		this.soastatetypeid = soastatetypeid;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
	}
	public Long getDeliveryplaceid() {
		return deliveryplaceid;
	}
	public void setDeliveryplaceid(Long deliveryplaceid) {
		this.deliveryplaceid = deliveryplaceid;
	}
	public Long getSaleplaceid() {
		return saleplaceid;
	}
	public void setSaleplaceid(Long saleplaceid) {
		this.saleplaceid = saleplaceid;
	}
	public Long getClientid() {
		return clientid;
	}
	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}
	public Long getSectionid() {
		return sectionid;
	}
	public void setSectionid(Long sectionid) {
		this.sectionid = sectionid;
	}
	public String getNumref1() {
		return numref1;
	}
	public void setNumref1(String numref1) {
		this.numref1 = numref1;
	}
	public String getNumref2() {
		return numref2;
	}
	public void setNumref2(String numref2) {
		this.numref2 = numref2;
	}
	public String getNumref3() {
		return numref3;
	}
	public void setNumref3(String numref3) {
		this.numref3 = numref3;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getReceiptnumber() {
		return receiptnumber;
	}
	public void setReceiptnumber(String receiptnumber) {
		this.receiptnumber = receiptnumber;
	}
	public Long getActionid() {
		return actionid;
	}
	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
