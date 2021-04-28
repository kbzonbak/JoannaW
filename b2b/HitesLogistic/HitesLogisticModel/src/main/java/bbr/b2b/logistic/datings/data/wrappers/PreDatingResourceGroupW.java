package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.logistic.datings.data.interfaces.IPreDatingResourceGroup;
import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public class PreDatingResourceGroupW extends ResourceBlockingGroupW implements IPreDatingResourceGroup {

	private Double capacitybymodule;
	private Double totalcapacity;
	private Long vendorid;

	public Double getCapacitybymodule(){ 
		return this.capacitybymodule;
	}
	public Double getTotalcapacity(){ 
		return this.totalcapacity;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public void setCapacitybymodule(Double capacitybymodule){ 
		this.capacitybymodule = capacitybymodule;
	}
	public void setTotalcapacity(Double totalcapacity){ 
		this.totalcapacity = totalcapacity;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
}
