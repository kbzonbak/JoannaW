package bbr.b2b.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPreDatingResourceGroup extends IElement {

	public Double getCapacitybymodule();
	public Double getTotalcapacity();
	public void setCapacitybymodule(Double capacitybymodule);
	public void setTotalcapacity(Double totalcapacity);
}
