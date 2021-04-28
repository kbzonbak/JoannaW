package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class ContractedServiceOfRutDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762311556910448623L;

	private Long servicekey;
	
	private String servicecode;
	
	private String servicename;


	public String getServicecode() {
		return servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	public Long getServicekey() {
		return servicekey;
	}


	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}


	public String getServicename() {
		return servicename;
	}


	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	
}
