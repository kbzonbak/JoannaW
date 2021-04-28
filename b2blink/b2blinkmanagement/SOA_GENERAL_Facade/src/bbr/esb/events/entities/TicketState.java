package bbr.esb.events.entities;

import java.util.Date;

import bbr.esb.events.data.interfaces.ITicketState;

public class TicketState implements ITicketState {

	private static final long serialVersionUID = -5653480892652058006L;

	private Long id;

	private Date when;

	private String code;

	private String type;

	private String state;

	private String description;

	private TicketEvent ticketevent;

	private TicketStateType ticketstatetype;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public TicketEvent getTicketevent() {
		return ticketevent;
	}

	public TicketStateType getTicketstatetype() {
		return ticketstatetype;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTicketevent(TicketEvent ticketevent) {
		this.ticketevent = ticketevent;
	}

	public void setTicketstatetype(TicketStateType ticketstatetype) {
		this.ticketstatetype = ticketstatetype;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWhen(Date when) {
		this.when = when;
	}
}
