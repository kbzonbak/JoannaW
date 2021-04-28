package bbr.b2b.regional.logistic.directorders.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.buyorders.entities.Client;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.entities.Responsable;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrder;

public class DirectOrder implements IDirectOrder {

	private Long id;
	private Long number;
	private String distributionordernumber;
	private String requestnumber;
	private Boolean active;
	private Date emitted;
	private Date valid;
	private Date expiration;
	private Date currentdeliverydate;
	private Date originaldeliverydate;
	private Date currentstatetypedate;
	private String currentstatetypecomment;
	private String currentstatetypewho;
	private Date paymentdate;
	private String paymenttype;
	private String season;
	private String clientaddress;
	private String clientcity;
	private String clientcommune;
	private String clientdeptnumber;
	private String clienthousenumber;
	private String clientphone;
	private String clientregion;
	private String clientroadnumber;
	private Double needunits;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private Double totalneed;
	private String comment;
	private Boolean courierreceived;
	private Client client;
	private Vendor vendor;
	private Responsable responsable;
	private DirectOrderStateType currentstatetype;
	private Department department;
	private DODelivery currentdelivery;
	private SOAStateType currentsoastatetype;
	private Date currentsoastatetypedate;
	private Location salestore;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getDistributionordernumber() {
		return distributionordernumber;
	}
	public void setDistributionordernumber(String distributionordernumber) {
		this.distributionordernumber = distributionordernumber;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getEmitted() {
		return emitted;
	}
	public void setEmitted(Date emitted) {
		this.emitted = emitted;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Date getCurrentdeliverydate() {
		return currentdeliverydate;
	}
	public void setCurrentdeliverydate(Date currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}
	public Date getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public void setOriginaldeliverydate(Date originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public Date getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public String getCurrentstatetypecomment() {
		return currentstatetypecomment;
	}
	public void setCurrentstatetypecomment(String currentstatetypecomment) {
		this.currentstatetypecomment = currentstatetypecomment;
	}
	public String getCurrentstatetypewho() {
		return currentstatetypewho;
	}
	public void setCurrentstatetypewho(String currentstatetypewho) {
		this.currentstatetypewho = currentstatetypewho;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getClientaddress() {
		return clientaddress;
	}
	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}
	public String getClientcity() {
		return clientcity;
	}
	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}
	public String getClientdeptnumber() {
		return clientdeptnumber;
	}
	public void setClientdeptnumber(String clientdeptnumber) {
		this.clientdeptnumber = clientdeptnumber;
	}
	public String getClienthousenumber() {
		return clienthousenumber;
	}
	public void setClienthousenumber(String clienthousenumber) {
		this.clienthousenumber = clienthousenumber;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getClientregion() {
		return clientregion;
	}
	public void setClientregion(String clientregion) {
		this.clientregion = clientregion;
	}
	public String getClientroadnumber() {
		return clientroadnumber;
	}
	public void setClientroadnumber(String clientroadnumber) {
		this.clientroadnumber = clientroadnumber;
	}
	public Double getNeedunits() {
		return needunits;
	}
	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getTotalpending() {
		return totalpending;
	}
	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
	public Double getTotaltodelivery() {
		return totaltodelivery;
	}
	public void setTotaltodelivery(Double totaltodelivery) {
		this.totaltodelivery = totaltodelivery;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Boolean getCourierreceived() {
		return courierreceived;
	}
	public void setCourierreceived(Boolean courierreceived) {
		this.courierreceived = courierreceived;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Responsable getResponsable() {
		return responsable;
	}
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}
	public DirectOrderStateType getCurrentstatetype() {
		return currentstatetype;
	}
	public void setCurrentstatetype(DirectOrderStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public DODelivery getCurrentdelivery() {
		return currentdelivery;
	}
	public void setCurrentdelivery(DODelivery currentdelivery) {
		this.currentdelivery = currentdelivery;
	}
	public SOAStateType getCurrentsoastatetype() {
		return currentsoastatetype;
	}
	public void setCurrentsoastatetype(SOAStateType currentsoastatetype) {
		this.currentsoastatetype = currentsoastatetype;
	}
	public Date getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}
	public Location getSalestore() {
		return salestore;
	}
	public void setSalestore(Location salestore) {
		this.salestore = salestore;
	}

}
