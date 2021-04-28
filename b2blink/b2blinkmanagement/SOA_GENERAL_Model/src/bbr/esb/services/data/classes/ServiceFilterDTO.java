package bbr.esb.services.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class ServiceFilterDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ServiceDTO[] serviceforfilterArray;

	public ServiceDTO[] getServiceforfilterArray() {
		return serviceforfilterArray;
	}

	public void setServiceforfilterArray(ServiceDTO[] serviceforfilterArray) {
		this.serviceforfilterArray = serviceforfilterArray;
	}
	
	

}
