package bbr.b2b.regional.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IItem extends IElement {

	public String getName();
	public String getBarcode();
	public String getInternalcode();
	public String getEan1();
	public String getEan2();
	public String getEan3();
	public String getTrademark();
	public String getColor();
	public String getDimension();
	public String getSize();
	public Integer getInnerpack();
	public Integer getCasepack();	
	public Boolean getVev();	
	public void setName(String name);	 
	public void setBarcode(String barcode);	 
	public void setInternalcode(String internalcode);	
	public void setEan1(String ean1); 	 
	public void setEan2(String ean2);	
	public void setEan3(String ean3);
	public void setTrademark(String trademark);
	public void setColor(String color);	 
	public void setDimension(String dimension);	 
	public void setSize(String size); 	
	public void setInnerpack(Integer innerpack);	
	public void setCasepack(Integer casepack);
	public void setVev(Boolean vev);

}
