package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.services.data.interfaces.IMessageFormat;

public class MessageFormatDTO extends ElementDTO implements IMessageFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5512688949624065965L;

	private String name;

	private Long servicekey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

}
