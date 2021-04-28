package bbr.common.adtclasses.classes;

public class PageInitParamDTO  {
	private Integer pageNumber;
	private Integer rows;
	private Boolean needPageInfo;
	private OrderCriteriaDTO[] orderBy;

	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRows() {
		return this.rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Boolean getNeedPageInfo() {
		return this.needPageInfo;
	}

	public void setNeedPageInfo(Boolean needPageInfo) {
		this.needPageInfo = needPageInfo;
	}

	public OrderCriteriaDTO[] getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(OrderCriteriaDTO[] orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean isPaginated() {
		return this.pageNumber != null && this.rows != null ? true : false;
	}

	public Boolean isByFilter() {
		return this.needPageInfo != null && this.needPageInfo && this.isPaginated() != null && this.isPaginated()
				? true
				: false;
	}
}