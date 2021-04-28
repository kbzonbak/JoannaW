package bbr.b2b.regional.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.items.data.interfaces.IItem;

public class ItemW extends ElementDTO implements IItem {

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
	private Long itemclassid;
	private Long unitid;
	private Long flowtypeid;

	public ItemW() {
		
	}
	
	public ItemW(String name, String barcode, String internalcode, String ean1, String ean2, String ean3, String trademark, String color, String dimension, String size, Integer innerpack, Integer casepack, Boolean vev, Long itemclassid, Long unitid, Long flowtypeid) {
		super();
		this.name = name;
		this.barcode = barcode;
		this.internalcode = internalcode;
		this.ean1 = ean1;
		this.ean2 = ean2;
		this.ean3 = ean3;
		this.trademark = trademark;
		this.color = color;
		this.dimension = dimension;
		this.size = size;
		this.innerpack = innerpack;
		this.casepack = casepack;
		this.vev = vev;
		this.itemclassid = itemclassid;
		this.unitid = unitid;
		this.flowtypeid = flowtypeid;
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
	public Long getItemclassid(){ 
		return this.itemclassid;
	}
	public Long getUnitid(){ 
		return this.unitid;
	}
	public Long getFlowtypeid(){ 
		return this.flowtypeid;
	}	
	public void setName(String name){ 
		this.name = name;
	}
	public void setItemclassid(Long itemclassid){ 
		this.itemclassid = itemclassid;
	}
	public void setUnitid(Long unitid){ 
		this.unitid = unitid;
	}
	public void setFlowtypeid(Long flowtypeid){ 
		this.flowtypeid = flowtypeid;
	}
}
