package bbr.b2b.logistic.datings.entities;

import bbr.b2b.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.datings.data.interfaces.IPreDatingResourceGroup;

public class PreDatingResourceGroup extends ResourceBlockingGroup implements IPreDatingResourceGroup {

	private Double capacitybymodule;
	private Double totalcapacity;
	private Vendor vendor;

	public Double getCapacitybymodule(){ 
		return this.capacitybymodule;
	}
	public Double getTotalcapacity(){ 
		return this.totalcapacity;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public void setCapacitybymodule(Double capacitybymodule){ 
		this.capacitybymodule = capacitybymodule;
	}
	public void setTotalcapacity(Double totalcapacity){ 
		this.totalcapacity = totalcapacity;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
}
