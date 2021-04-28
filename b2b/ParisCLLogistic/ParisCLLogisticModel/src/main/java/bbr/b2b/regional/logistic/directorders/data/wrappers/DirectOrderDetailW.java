package bbr.b2b.regional.logistic.directorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderDetail;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderDetailPK;

public class DirectOrderDetailW implements IDirectOrderDetail, IDirectOrderDetailPK {

	private Integer visualorder;
	private Double units = 0D;
	private Double listprice;
	private Double listcost;
	private Double finalprice;
	private Double finalcost;
	private Double pendingunits = 0D;
	private Double receivedunits = 0D;
	private Double todeliveryunits = 0D;
	private Double totalneed = 0D;
	private Double totalpending = 0D;
	private Double totalreceived = 0D;
	private Double totaltodelivery = 0D;
	private Long orderid;
	private Long itemid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IDirectOrderDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDirectOrderDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Double getUnits(){ 
		return this.units;
	}
	public Double getListprice(){ 
		return this.listprice;
	}
	public Double getListcost(){ 
		return this.listcost;
	}
	public Double getFinalprice(){ 
		return this.finalprice;
	}
	public Double getFinalcost(){ 
		return this.finalcost;
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

	public Double getTotalneed() {
		return totalneed;
	}

	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
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

	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setListprice(Double listprice){ 
		this.listprice = listprice;
	}
	public void setListcost(Double listcost){ 
		this.listcost = listcost;
	}
	public void setFinalprice(Double finalprice){ 
		this.finalprice = finalprice;
	}
	public void setFinalcost(Double finalcost){ 
		this.finalcost = finalcost;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
