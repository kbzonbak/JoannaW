package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;


public class DeliveryReportInitParamDTO implements Serializable {

	private String vendorcode;
	private String originalvendorcode;
	private Long originalvendorid;
	private Long deliverynumber;
	private Long ocnumber;	
	private Long deliverystatetypeid;
	private String container;
	private Long guide;
	private String imp;
	private int filtertype;
	private int pageNumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	
	
	
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Long getDeliverystatetypeid() {
		return deliverystatetypeid;
	}
	public void setDeliverystatetypeid(Long deliverystatetypeid) {
		this.deliverystatetypeid = deliverystatetypeid;
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
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
	
}
