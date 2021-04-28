package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.ITicketStateType;

public class TicketStateTypeDTO extends ElementDTO implements ITicketStateType {

	private static final long serialVersionUID = -7380338910614198185L;

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}
}
