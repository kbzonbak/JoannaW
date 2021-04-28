package bbr.b2b.logistic.buyorders.data.dto;

import java.io.Serializable;
import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class OrderReportInitParamDTO implements Serializable {

	private String vendorcode;
	private String clientrut;
	private Long orderstatetypeid;
	private String emittedsince;
	private String emitteduntil;
	private String receptionsince;
	private String receptionuntil;
	private String ordernumber;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	private int filtertype;
	private boolean byfilter;
	private boolean paginated;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
	}
	public String getEmittedsince() {
		return emittedsince;
	}
	public void setEmittedsince(String emittedsince) {
		this.emittedsince = emittedsince;
	}
	public String getEmitteduntil() {
		return emitteduntil;
	}
	public void setEmitteduntil(String emitteduntil) {
		this.emitteduntil = emitteduntil;
	}
	public String getReceptionsince() {
		return receptionsince;
	}
	public void setReceptionsince(String receptionsince) {
		this.receptionsince = receptionsince;
	}
	public String getReceptionuntil() {
		return receptionuntil;
	}
	public void setReceptionuntil(String receptionuntil) {
		this.receptionuntil = receptionuntil;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
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
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	public boolean isByfilter() {
		return byfilter;
	}
	public void setByfilter(boolean byfilter) {
		this.byfilter = byfilter;
	}
	public boolean isPaginated() {
		return paginated;
	}
	public void setPaginated(boolean paginated) {
		this.paginated = paginated;
	}
}
