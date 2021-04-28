package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrder;

public class OrderW extends ElementDTO implements IOrder {

	private Long number;
	private String requestnumber;
	private String distributionordernumber;
	private Boolean active;
	private Date emitted;
	private Date valid;
	private Date expiration;
	private Date originaldeliverydate;
	private Date currentstatetypedate;
	private Date currentsoastatetypedate;
	private String comment;
	private Double needunits = 0D;
	private Double pendingunits = 0D;
	private Double receivedunits = 0D;
	private Double todeliveryunits = 0D;
	private Double outreceivedunits = 0D;
	private Double totalneed = 0D;
	private Double totalpending = 0D;
	private Double totalreceived = 0D;
	private Double totaltodelivery = 0D;
	private Boolean vevcd;
	private Boolean senttosoa;
	private String vendorcodeimp;
	private Long clientid;
	private Long currentstatetypeid;
	private Long responsableid;
	private Long departmentid;
	private Long deliverylocationid;
	private Long vendorid;
	private Long ordertypeid;
	private Long currentsoastatetypeid;
	private Long salestoreid;

	public String getVendorcodeimp() {
		return vendorcodeimp;
	}
	public void setVendorcodeimp(String vendorcodeimp) {
		this.vendorcodeimp = vendorcodeimp;
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
	public Boolean getSenttosoa(){ 
		return this.senttosoa;
	}
	public Long getClientid(){ 
		return this.clientid;
	}
	public Long getCurrentstatetypeid(){ 
		return this.currentstatetypeid;
	}
	public Long getResponsableid(){ 
		return this.responsableid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public Long getDeliverylocationid(){ 
		return this.deliverylocationid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getOrdertypeid(){ 
		return this.ordertypeid;
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
	public void setSenttosoa(Boolean senttosoa){ 
		this.senttosoa = senttosoa;
	}
	public void setClientid(Long clientid){ 
		this.clientid = clientid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid){ 
		this.currentstatetypeid = currentstatetypeid;
	}
	public void setResponsableid(Long responsableid){ 
		this.responsableid = responsableid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
	public void setDeliverylocationid(Long deliverylocationid){ 
		this.deliverylocationid = deliverylocationid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setOrdertypeid(Long ordertypeid){ 
		this.ordertypeid = ordertypeid;
	}
	public Date getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}
	public Long getCurrentsoastatetypeid() {
		return currentsoastatetypeid;
	}
	public void setCurrentsoastatetypeid(Long currentsoastatetypeid) {
		this.currentsoastatetypeid = currentsoastatetypeid;
	}
	public String getDistributionordernumber() {
		return distributionordernumber;
	}
	public void setDistributionordernumber(String distributionordernumber) {
		this.distributionordernumber = distributionordernumber;
	}
	public Long getSalestoreid() {
		return salestoreid;
	}
	public void setSalestoreid(Long salestoreid) {
		this.salestoreid = salestoreid;
	}	
}
