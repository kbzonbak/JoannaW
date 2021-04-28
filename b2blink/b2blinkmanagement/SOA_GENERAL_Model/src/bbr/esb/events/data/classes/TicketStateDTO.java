package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.ITicketState;

public class TicketStateDTO extends ElementDTO implements ITicketState {

	private static final long serialVersionUID = -6564878868303321796L;

	private Date when;

	private String code;

	private String type;

	private String state;

	private String description;

	private Long ticketkey;

	private Long ticketstatetypekey;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getState() {
		return state;
	}

	public Long getTicketkey() {
		return ticketkey;
	}

	public Long getTicketstatetypekey() {
		return ticketstatetypekey;
	}

	public String getType() {
		return type;
	}

	public Date getWhen() {
		return when;
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

	public void setTicketkey(Long ticketkey) {
		this.ticketkey = ticketkey;
	}

	public void setTicketstatetypekey(Long ticketstatetypekey) {
		this.ticketstatetypekey = ticketstatetypekey;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWhen(Date when) {
		this.when = when;
	}
}
