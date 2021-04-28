package bbr.esb.events.entities;

import bbr.esb.events.data.interfaces.ITicketStateType;

public class TicketStateType implements ITicketStateType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4492721766031133639L;

	private Long id;

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return this.name;
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
