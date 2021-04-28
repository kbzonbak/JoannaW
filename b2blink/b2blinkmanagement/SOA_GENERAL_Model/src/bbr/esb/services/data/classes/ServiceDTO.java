package bbr.esb.services.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.services.data.interfaces.IService;

public class ServiceDTO extends ElementDTO implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363037924562447886L;

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

}
