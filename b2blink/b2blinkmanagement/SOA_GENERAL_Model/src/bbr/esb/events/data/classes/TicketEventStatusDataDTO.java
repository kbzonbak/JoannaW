package bbr.esb.events.data.classes;

import java.io.Serializable;

public class TicketEventStatusDataDTO implements Serializable {

	private static final long serialVersionUID = 2549912659610298938L;

	private String code;

	private String type;

	private String description;

	private String state;

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
