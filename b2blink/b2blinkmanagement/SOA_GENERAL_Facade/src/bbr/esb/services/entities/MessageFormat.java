package bbr.esb.services.entities;

import bbr.esb.services.data.interfaces.IMessageFormat;

public class MessageFormat implements IMessageFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3293230320051397061L;

	private Long id;

	private String name;

	private Service service;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Service getService() {
		return service;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
