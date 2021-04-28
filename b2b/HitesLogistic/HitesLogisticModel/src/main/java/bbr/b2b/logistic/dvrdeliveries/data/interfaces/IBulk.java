package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IBulk extends IElement {

	public String getLpncode();
	public Double getTotalunits();
	public Boolean getActive();
	public void setLpncode(String lpncode);
	public void setTotalunits(Double totalunits);
	public void setActive(Boolean active);
}
