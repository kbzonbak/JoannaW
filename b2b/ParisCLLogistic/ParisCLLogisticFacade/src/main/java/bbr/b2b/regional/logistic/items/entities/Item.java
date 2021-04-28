package bbr.b2b.regional.logistic.items.entities;

import bbr.b2b.regional.logistic.items.entities.ItemClass;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.items.data.interfaces.IItem;

public class Item implements IItem {

	private Long id;
	private String name;
	private String barcode;
	private String internalcode;
	private String ean1;
	private String ean2;
	private String ean3;
	private String trademark;
	private String color;
	private String dimension;
	private String size;
	private Integer innerpack;
	private Integer casepack;
	private Boolean vev;
	private ItemClass itemclass;
	private Unit unit;
	private FlowType flowtype;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
	}	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getInternalcode() {
		return internalcode;
	}
	public void setInternalcode(String internalcode) {
		this.internalcode = internalcode;
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
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
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
	public Integer getCasepack() {
		return casepack;
	}
	public void setCasepack(Integer casepack) {
		this.casepack = casepack;
	}	
	public Boolean getVev() {
		return vev;
	}
	public void setVev(Boolean vev) {
		this.vev = vev;
	}
	public ItemClass getItemclass(){ 
		return this.itemclass;
	}
	public Unit getUnit(){ 
		return this.unit;
	}
	public FlowType getFlowtype(){ 
		return this.flowtype;
	}
	public void setId(Long id){ 
		this.id = id;
	}	
	public void setName(String name){ 
		this.name = name;
	}
	public void setItemclass(ItemClass itemclass){ 
		this.itemclass = itemclass;
	}
	public void setUnit(Unit unit){ 
		this.unit = unit;
	}
	public void setFlowtype(FlowType flowtype){ 
		this.flowtype = flowtype;
	}
}
