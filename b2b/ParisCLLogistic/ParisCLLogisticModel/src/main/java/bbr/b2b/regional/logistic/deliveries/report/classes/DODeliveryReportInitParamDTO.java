package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DODeliveryReportInitParamDTO implements Serializable {

	private String vendorcode;
	private Long deliveryid;
	private Long[] deliveryids;
	private Long ocnumber;
	private String requestnumber;
	private String clientrut;
	private LocalDateTime since;
	private LocalDateTime until;
	private String dostate;	
	private String sendnumber;	
	private Long withdrawalnumber;
	private int filtertype;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

	public Long[] getDeliveryids() {
		return deliveryids;
	}

	public void setDeliveryids(Long[] deliveryids) {
		this.deliveryids = deliveryids;
	}

	public Long getOcnumber() {
		return ocnumber;
	}

	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}

	public String getRequestnumber() {
		return requestnumber;
	}

	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}

	public String getClientrut() {
		return clientrut;
	}

	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}



	public LocalDateTime getSince() {
		return since;
	}

	public void setSince(LocalDateTime since) {
		this.since = since;
	}

	public LocalDateTime getUntil() {
		return until;
	}

	public void setUntil(LocalDateTime until) {
		this.until = until;
	}

	public String getDostate() {
		return dostate;
	}

	public void setDostate(String dostate) {
		this.dostate = dostate;
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

	public String getSendnumber() {
		return sendnumber;
	}

	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}

	public Long getWithdrawalnumber() {
		return withdrawalnumber;
	}

	public void setWithdrawalnumber(Long withdrawalnumber) {
		this.withdrawalnumber = withdrawalnumber;
	}
	
}
