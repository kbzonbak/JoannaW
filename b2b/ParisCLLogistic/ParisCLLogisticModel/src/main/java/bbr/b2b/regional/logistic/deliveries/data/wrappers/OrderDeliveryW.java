package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDelivery;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDeliveryPK;

public class OrderDeliveryW implements IOrderDeliveryPK, IOrderDelivery {

	private Boolean closed;
	private Long asnimp;
	private Long refdocument;
	private String imp;
	private Integer deliveryindex;
	private Double estimatedunits;
	private Double originalestimunits;
	private Integer realbulkcount;
	private Integer realpalletcount;
	private Double realpackedunits;
	private Long deliveryid;
	private Long orderid;
	private Long departmentid;
	private Long flowtypeid;
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IOrderDeliveryPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = deliveryid.longValue() - ((IOrderDeliveryPK) arg0).getDeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Boolean getClosed(){ 
		return this.closed;
	}
	public Long getAsnimp() {
		return asnimp;
	}
	public Long getRefdocument() {
		return refdocument;
	}
	public String getImp() {
		return imp;
	}
	public Integer getDeliveryindex(){ 
		return this.deliveryindex;
	}
	public Double getEstimatedunits(){ 
		return this.estimatedunits;
	}
	public Double getOriginalestimunits(){ 
		return this.originalestimunits;
	}
	public Integer getRealbulkcount(){ 
		return this.realbulkcount;
	}
	public Integer getRealpalletcount(){ 
		return this.realpalletcount;
	}
	public Double getRealpackedunits(){ 
		return this.realpackedunits;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public Long getFlowtypeid(){ 
		return this.flowtypeid;
	}
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setAsnimp(Long asnimp) {
		this.asnimp = asnimp;
	}
	public void setRefdocument(Long refdocument) {
		this.refdocument = refdocument;
	}
	public void setImp(String imp) {
		this.imp = imp;
	}
	public void setDeliveryindex(Integer deliveryindex){ 
		this.deliveryindex = deliveryindex;
	}
	public void setEstimatedunits(Double estimatedunits){ 
		this.estimatedunits = estimatedunits;
	}
	public void setOriginalestimunits(Double originalestimunits){ 
		this.originalestimunits = originalestimunits;
	}
	public void setRealbulkcount(Integer realbulkcount){ 
		this.realbulkcount = realbulkcount;
	}
	public void setRealpalletcount(Integer realpalletcount){ 
		this.realpalletcount = realpalletcount;
	}
	public void setRealpackedunits(Double realpackedunits){ 
		this.realpackedunits = realpackedunits;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
	public void setFlowtypeid(Long flowtypeid){ 
		this.flowtypeid = flowtypeid;
	}
	
}
