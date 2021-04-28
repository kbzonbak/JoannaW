package bbr.esb.events.data.classes;

import java.io.Serializable;

public class TicketEventDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -218099516442763075L;

	String code;

	String type;

	String description;

	String state;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}
}
