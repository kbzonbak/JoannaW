package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class ResourceBlockingGroupDetailDataInitParamDTO implements Serializable {

	private Long blockinggroupid;
	private String locationcode;
	private int pagenumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	private Boolean ispageinfo;

	public Long getBlockinggroupid() {
		return blockinggroupid;
	}

	public void setBlockinggroupid(Long blockinggroupid) {
		this.blockinggroupid = blockinggroupid;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public int getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
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

	public Boolean getIspageinfo() {
		return ispageinfo;
	}

	public void setIspageinfo(Boolean ispageinfo) {
		this.ispageinfo = ispageinfo;
	}

}
