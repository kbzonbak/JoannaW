package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderDetaiPK;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderDetail;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public class DvrOrderDetailW implements IDvrOrderDetaiPK, IDvrOrderDetail {

	private Double basecost;
	private Double finalcost;
	private String umbbarcode;
	private String umbdescription;
	private String umcbarcode;
	private String umcdescription;
	private Double umbxumc;
	private String umccode;
	private String umbcode;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double totalneed;
	private Double totaltodelivery;
	private Double totalreceived;
	private Double totalpending;
	private Integer tolerance;
	private String innerpack;
	private String casepack;
	private LocalDateTime itemdeliverydate;
	private String comment;
	private Double normalprice;
	private Double offerprice;
	private String barcode1;
	private String barcode2;
	private Long dvrorderid;
	private Long itemid;
	private Integer position;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrOrderDetaiPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDvrOrderDetaiPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDvrOrderDetaiPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getBasecost(){ 
		return this.basecost;
	}
	public Double getFinalcost(){ 
		return this.finalcost;
	}
	public String getUmbbarcode(){ 
		return this.umbbarcode;
	}
	public String getUmbdescription(){ 
		return this.umbdescription;
	}
	public String getUmcbarcode(){ 
		return this.umcbarcode;
	}
	public String getUmcdescription(){ 
		return this.umcdescription;
	}
	public Double getUmbxumc(){ 
		return this.umbxumc;
	}
	public String getUmccode(){ 
		return this.umccode;
	}
	public String getUmbcode(){ 
		return this.umbcode;
	}
	public Double getTotalunits(){ 
		return this.totalunits;
	}
	public Double getTodeliveryunits(){ 
		return this.todeliveryunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotaltodelivery(){ 
		return this.totaltodelivery;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Double getTotalpending(){ 
		return this.totalpending;
	}
	public Integer getTolerance(){ 
		return this.tolerance;
	}
	public String getInnerpack(){ 
		return this.innerpack;
	}
	public String getCasepack(){ 
		return this.casepack;
	}
	public LocalDateTime getItemdeliverydate(){ 
		return this.itemdeliverydate;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Double getNormalprice(){ 
		return this.normalprice;
	}
	public Double getOfferprice(){ 
		return this.offerprice;
	}
	public String getBarcode1(){ 
		return this.barcode1;
	}
	public String getBarcode2(){ 
		return this.barcode2;
	}
	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Integer getPosition(){ 
		return this.position;
	}
	public void setBasecost(Double basecost){ 
		this.basecost = basecost;
	}
	public void setFinalcost(Double finalcost){ 
		this.finalcost = finalcost;
	}
	public void setUmbbarcode(String umbbarcode){ 
		this.umbbarcode = umbbarcode;
	}
	public void setUmbdescription(String umbdescription){ 
		this.umbdescription = umbdescription;
	}
	public void setUmcbarcode(String umcbarcode){ 
		this.umcbarcode = umcbarcode;
	}
	public void setUmcdescription(String umcdescription){ 
		this.umcdescription = umcdescription;
	}
	public void setUmbxumc(Double umbxumc){ 
		this.umbxumc = umbxumc;
	}
	public void setUmccode(String umccode){ 
		this.umccode = umccode;
	}
	public void setUmbcode(String umbcode){ 
		this.umbcode = umbcode;
	}
	public void setTotalunits(Double totalunits){ 
		this.totalunits = totalunits;
	}
	public void setTodeliveryunits(Double todeliveryunits){ 
		this.todeliveryunits = todeliveryunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotaltodelivery(Double totaltodelivery){ 
		this.totaltodelivery = totaltodelivery;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setTotalpending(Double totalpending){ 
		this.totalpending = totalpending;
	}
	public void setTolerance(Integer tolerance){ 
		this.tolerance = tolerance;
	}
	public void setInnerpack(String innerpack){ 
		this.innerpack = innerpack;
	}
	public void setCasepack(String casepack){ 
		this.casepack = casepack;
	}
	public void setItemdeliverydate(LocalDateTime itemdeliverydate){ 
		this.itemdeliverydate = itemdeliverydate;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setNormalprice(Double normalprice){ 
		this.normalprice = normalprice;
	}
	public void setOfferprice(Double offerprice){ 
		this.offerprice = offerprice;
	}
	public void setBarcode1(String barcode1){ 
		this.barcode1 = barcode1;
	}
	public void setBarcode2(String barcode2){ 
		this.barcode2 = barcode2;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
