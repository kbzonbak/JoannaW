package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DeliveryReportDataDTO implements Serializable {

	private Long deliveryid;    
	private Long deliverynumber;    
	private String deliverytypecode;  
	private String deliverytypename;  		
	private String flowtypecode;  
	private String flowtypedesc;  
	private String creationdate;   
	private String deliverystatetypecode;    
	private String deliverystatetypedesc;
	private String deliverylocationcode;
	private String deliverylocation;   
	private String container;
	private Long guide;
	private String imp;
	private String action;
	private Integer scheduling;
	
	
	public Integer getScheduling() {
		return scheduling;
	}
	public void setScheduling(Integer scheduling) {
		this.scheduling = scheduling;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public Long getGuide() {
		return guide;
	}
	public void setGuide(Long guide) {
		this.guide = guide;
	}
	public String getImp() {
		return imp;
	}
	public void setImp(String imp) {
		this.imp = imp;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getDeliverytypecode() {
		return deliverytypecode;
	}
	public void setDeliverytypecode(String deliverytypecode) {
		this.deliverytypecode = deliverytypecode;
	}
	public String getDeliverytypename() {
		return deliverytypename;
	}
	public void setDeliverytypename(String deliverytypename) {
		this.deliverytypename = deliverytypename;
	}
	public String getFlowtypecode() {
		return flowtypecode;
	}
	public void setFlowtypecode(String flowtypecode) {
		this.flowtypecode = flowtypecode;
	}
	public String getFlowtypedesc() {
		return flowtypedesc;
	}
	public void setFlowtypedesc(String flowtypedesc) {
		this.flowtypedesc = flowtypedesc;
	}
	public String getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}
	public String getDeliverystatetypecode() {
		return deliverystatetypecode;
	}
	public void setDeliverystatetypecode(String deliverystatetypecode) {
		this.deliverystatetypecode = deliverystatetypecode;
	}
	public String getDeliverystatetypedesc() {
		return deliverystatetypedesc;
	}
	public void setDeliverystatetypedesc(String deliverystatetypedesc) {
		this.deliverystatetypedesc = deliverystatetypedesc;
	}	
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocation() {
		return deliverylocation;
	}
	public void setDeliverylocation(String deliverylocation) {
		this.deliverylocation = deliverylocation;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}	
}
