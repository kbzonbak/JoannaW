package bbr.b2b.regional.logistic.buyorders.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrder;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Responsable;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class Order implements IOrder {

	private Long id;
	private Long number;
	private String distributionordernumber;
	private String requestnumber;
	private Boolean active;
	private Date emitted;
	private Date valid;
	private Date expiration;
	private Date originaldeliverydate;
	private Date currentstatetypedate;
	private Date currentsoastatetypedate;
	private String comment;
	private Double needunits;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double outreceivedunits;
	private Double totalneed;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private Boolean vevcd;	
	private String vendorcodeimp;
	private Client client;
	private OrderStateType currentstatetype;
	private Responsable responsable;
	private Department department;
	private Location deliverylocation;
	private Vendor vendor;
	private OrderType ordertype;
	private SOAStateType currentsoastatetype;
	private Location salestore;
	
	public String getVendorcodeimp() {
		return vendorcodeimp;
	}
	public void setVendorcodeimp(String vendorcodeimp) {
		this.vendorcodeimp = vendorcodeimp;
	}
	public Long getId(){ 
		return this.id;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public String getRequestnumber(){ 
		return this.requestnumber;
	}
	public Boolean getActive(){ 
		return this.active;
	}
	public Date getEmitted(){ 
		return this.emitted;
	}
	public Date getValid(){ 
		return this.valid;
	}
	public Date getExpiration(){ 
		return this.expiration;
	}
	public Date getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public Date getCurrentstatetypedate(){ 
		return this.currentstatetypedate;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Double getNeedunits(){ 
		return this.needunits;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getTodeliveryunits(){ 
		return this.todeliveryunits;
	}
	public Double getOutreceivedunits(){ 
		return this.outreceivedunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotalpending(){ 
		return this.totalpending;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Double getTotaltodelivery(){ 
		return this.totaltodelivery;
	}
	public Boolean getVevcd(){ 
		return this.vevcd;
	}	
	public Client getClient(){ 
		return this.client;
	}
	public OrderStateType getCurrentstatetype(){ 
		return this.currentstatetype;
	}
	public Responsable getResponsable(){ 
		return this.responsable;
	}
	public Department getDepartment(){ 
		return this.department;
	}
	public Location getDeliverylocation(){ 
		return this.deliverylocation;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public OrderType getOrdertype(){ 
		return this.ordertype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setRequestnumber(String requestnumber){ 
		this.requestnumber = requestnumber;
	}
	public void setActive(Boolean active){ 
		this.active = active;
	}
	public void setEmitted(Date emitted){ 
		this.emitted = emitted;
	}
	public void setValid(Date valid){ 
		this.valid = valid;
	}
	public void setExpiration(Date expiration){ 
		this.expiration = expiration;
	}
	public void setOriginaldeliverydate(Date originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate){ 
		this.currentstatetypedate = currentstatetypedate;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setNeedunits(Double needunits){ 
		this.needunits = needunits;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setTodeliveryunits(Double todeliveryunits){ 
		this.todeliveryunits = todeliveryunits;
	}
	public void setOutreceivedunits(Double outreceivedunits){ 
		this.outreceivedunits = outreceivedunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotalpending(Double totalpending){ 
		this.totalpending = totalpending;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setTotaltodelivery(Double totaltodelivery){ 
		this.totaltodelivery = totaltodelivery;
	}
	public void setVevcd(Boolean vevcd){ 
		this.vevcd = vevcd;
	}	
	public void setClient(Client client){ 
		this.client = client;
	}
	public void setCurrentstatetype(OrderStateType currentstatetype){ 
		this.currentstatetype = currentstatetype;
	}
	public void setResponsable(Responsable responsable){ 
		this.responsable = responsable;
	}
	public void setDepartment(Department department){ 
		this.department = department;
	}
	public void setDeliverylocation(Location deliverylocation){ 
		this.deliverylocation = deliverylocation;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setOrdertype(OrderType ordertype){ 
		this.ordertype = ordertype;
	}
	public Date getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}
	public SOAStateType getCurrentsoastatetype() {
		return currentsoastatetype;
	}
	public void setCurrentsoastatetype(SOAStateType currentsoastatetype) {
		this.currentsoastatetype = currentsoastatetype;
	}
	public String getDistributionordernumber() {
		return distributionordernumber;
	}
	public void setDistributionordernumber(String distributionordernumber) {
		this.distributionordernumber = distributionordernumber;
	}
	public Location getSalestore() {
		return salestore;
	}
	public void setSalestore(Location salestore) {
		this.salestore = salestore;
	}
}
