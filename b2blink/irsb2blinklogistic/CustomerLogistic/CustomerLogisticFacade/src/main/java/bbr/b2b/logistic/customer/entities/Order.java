package bbr.b2b.logistic.customer.entities;

import java.util.Date;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Vendor;
import bbr.b2b.logistic.customer.entities.OrderType;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.entities.Client;
import bbr.b2b.logistic.customer.data.interfaces.IOrder;

public class Order implements IOrder {

	private Long id;
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
	private Buyer buyer;
	private Vendor vendor;
	private OrderType ordertype;
	private SoaStateType soastatetype;
	private OrderStateType orderstatetype;
	private Location deliveryplace;
	private Location saleplace;
	private Client client;
	private Section section;
	private Action action;
	private String currency;

	public Long getId(){ 
		return this.id;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getTicket(){ 
		return this.ticket;
	}
	public String getRequest(){ 
		return this.request;
	}
	public Date getIssue_date(){ 
		return this.issue_date;
	}
	public Date getEffectiv_date(){ 
		return this.effectiv_date;
	}
	public Date getExpiration_date(){ 
		return this.expiration_date;
	}
	public Date getCommitment_date(){ 
		return this.commitment_date;
	}
	public String getPayment_condition(){ 
		return this.payment_condition;
	}
	public String getObservation(){ 
		return this.observation;
	}
	public String getResponsible(){ 
		return this.responsible;
	}
	public String getResponsible_email(){ 
		return this.responsible_email;
	}
	public Boolean getValid(){ 
		return this.valid;
	}
	public Buyer getBuyer(){ 
		return this.buyer;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public OrderType getOrdertype(){ 
		return this.ordertype;
	}
	public SoaStateType getSoastatetype(){ 
		return this.soastatetype;
	}
	public OrderStateType getOrderstatetype(){ 
		return this.orderstatetype;
	}
	public Location getDeliveryplace(){ 
		return this.deliveryplace;
	}
	public Location getSaleplace(){ 
		return this.saleplace;
	}
	public Client getClient(){ 
		return this.client;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setTicket(String ticket){ 
		this.ticket = ticket;
	}
	public void setRequest(String request){ 
		this.request = request;
	}
	public void setIssue_date(Date issue_date){ 
		this.issue_date = issue_date;
	}
	public void setEffectiv_date(Date effectiv_date){ 
		this.effectiv_date = effectiv_date;
	}
	public void setExpiration_date(Date expiration_date){ 
		this.expiration_date = expiration_date;
	}
	public void setCommitment_date(Date commitment_date){ 
		this.commitment_date = commitment_date;
	}
	public void setPayment_condition(String payment_condition){ 
		this.payment_condition = payment_condition;
	}
	public void setObservation(String observation){ 
		this.observation = observation;
	}
	public void setResponsible(String responsible){ 
		this.responsible = responsible;
	}
	public void setResponsible_email(String responsible_email){ 
		this.responsible_email = responsible_email;
	}
	public void setValid(Boolean valid){ 
		this.valid = valid;
	}
	public void setBuyer(Buyer buyer){ 
		this.buyer = buyer;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setOrdertype(OrderType ordertype){ 
		this.ordertype = ordertype;
	}
	public void setSoastatetype(SoaStateType soastatetype){ 
		this.soastatetype = soastatetype;
	}
	public void setOrderstatetype(OrderStateType orderstatetype){ 
		this.orderstatetype = orderstatetype;
	}
	public void setDeliveryplace(Location deliveryplace){ 
		this.deliveryplace = deliveryplace;
	}
	public void setSaleplace(Location saleplace){ 
		this.saleplace = saleplace;
	}
	public void setClient(Client client){ 
		this.client = client;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
