package bbr.esb.services.entities;

import bbr.esb.services.data.interfaces.IService;

public class Service implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -633494685219938551L;

	private Long id;

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
