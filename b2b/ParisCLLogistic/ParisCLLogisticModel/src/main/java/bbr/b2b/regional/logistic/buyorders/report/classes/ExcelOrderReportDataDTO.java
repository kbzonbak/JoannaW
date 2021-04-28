package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ExcelOrderReportDataDTO implements Serializable {
	
	private Long ocnumber;
	private String linedesc;
	private String departmentcode;
	private String departmentdesc;
	private String orderstatedesc;
	private String ordertypedesc;
	private String deliverylocationcode;
	private String deliverylocationdesc;
	private String destinationlocationcode;
	private String destinationlocationdesc;
	private LocalDateTime validdate;
	private LocalDateTime expirationdate;
	private LocalDateTime emitteddate;
	private String itemsku;
	private String vendoritemcode;
	private String barcode;
	private String flowtypedesc;
	private String itemdesc;
	private String color;
	private String dimension;
	private String size;
	private Integer innerpack;
	private Double listprice;
	private Double finalprice;
	private Double listcost;
	private Double discount;
	private Double finalcost;
	private Double totalunits;		
	private Double receivedunits;
	private Double todeliveryunits;			
	private Double pendingunits;	
	private Integer maxtolerance;
	private Integer mintolerance;
	private String ean1;
	private String ean2;
	private String ean3;
	private String comment;
	private String responsablename;
	private String responsablelastname;
	private String responsableemail;
	private String responsablephone;
	private String responsableposition;
	private String discountcomment;
	private String unit;
	private Integer visualorder;
	private String discountcode;
	private Long orderid;
	private Long itemid;
	private Long curve;
	private Long atcid;
	private String atccode;
	private Long originalvendorid;
	private String originalvendorcode;
	private String originalvendorname;
	
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getLinedesc() {
		return linedesc;
	}
	public void setLinedesc(String linedesc) {
		this.linedesc = linedesc;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getDepartmentdesc() {
		return departmentdesc;
	}
	public void setDepartmentdesc(String departmentdesc) {
		this.departmentdesc = departmentdesc;
	}
	public String getOrderstatedesc() {
		return orderstatedesc;
	}
	public void setOrderstatedesc(String orderstatedesc) {
		this.orderstatedesc = orderstatedesc;
	}
	public String getOrdertypedesc() {
		return ordertypedesc;
	}
	public void setOrdertypedesc(String ordertypedesc) {
		this.ordertypedesc = ordertypedesc;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationdesc() {
		return deliverylocationdesc;
	}
	public void setDeliverylocationdesc(String deliverylocationdesc) {
		this.deliverylocationdesc = deliverylocationdesc;
	}
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}
	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}
	public String getDestinationlocationdesc() {
		return destinationlocationdesc;
	}
	public void setDestinationlocationdesc(String destinationlocationdesc) {
		this.destinationlocationdesc = destinationlocationdesc;
	}
	public LocalDateTime getValiddate() {
		return validdate;
	}
	public void setValiddate(LocalDateTime validdate) {
		this.validdate = validdate;
	}
	public LocalDateTime getExpirationdate() {
		return expirationdate;
	}
	public void setExpirationdate(LocalDateTime expirationdate) {
		this.expirationdate = expirationdate;
	}
	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}
	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getFlowtypedesc() {
		return flowtypedesc;
	}
	public void setFlowtypedesc(String flowtypedesc) {
		this.flowtypedesc = flowtypedesc;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getInnerpack() {
		return innerpack;
	}
	public void setInnerpack(Integer innerpack) {
		this.innerpack = innerpack;
	}	
	public Double getListprice() {
		return listprice;
	}
	public void setListprice(Double listprice) {
		this.listprice = listprice;
	}
	public Double getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(Double finalprice) {
		this.finalprice = finalprice;
	}	
	public Double getListcost() {
		return listcost;
	}
	public void setListcost(Double listcost) {
		this.listcost = listcost;
	}	
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
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
	public Integer getMaxtolerance() {
		return maxtolerance;
	}
	public void setMaxtolerance(Integer maxtolerance) {
		this.maxtolerance = maxtolerance;
	}
	public Integer getMintolerance() {
		return mintolerance;
	}
	public void setMintolerance(Integer mintolerance) {
		this.mintolerance = mintolerance;
	}
	public String getEan1() {
		return ean1;
	}
	public void setEan1(String ean1) {
		this.ean1 = ean1;
	}
	public String getEan2() {
		return ean2;
	}
	public void setEan2(String ean2) {
		this.ean2 = ean2;
	}
	public String getEan3() {
		return ean3;
	}
	public void setEan3(String ean3) {
		this.ean3 = ean3;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getResponsablename() {
		return responsablename;
	}
	public void setResponsablename(String responsablename) {
		this.responsablename = responsablename;
	}
	public String getResponsablelastname() {
		return responsablelastname;
	}
	public void setResponsablelastname(String responsablelastname) {
		this.responsablelastname = responsablelastname;
	}
	public String getResponsableemail() {
		return responsableemail;
	}
	public void setResponsableemail(String responsableemail) {
		this.responsableemail = responsableemail;
	}
	public String getResponsablephone() {
		return responsablephone;
	}
	public void setResponsablephone(String responsablephone) {
		this.responsablephone = responsablephone;
	}
	public String getResponsableposition() {
		return responsableposition;
	}
	public void setResponsableposition(String responsableposition) {
		this.responsableposition = responsableposition;
	}
	public String getDiscountcomment() {
		return discountcomment;
	}
	public void setDiscountcomment(String discountcomment) {
		this.discountcomment = discountcomment;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getVisualorder() {
		return visualorder;
	}
	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}
	public String getDiscountcode() {
		return discountcode;
	}
	public void setDiscountcode(String discountcode) {
		this.discountcode = discountcode;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
		
}
