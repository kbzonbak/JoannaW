package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTicket;

public class CourierTicket implements ICourierTicket {
	
	private Long id;

	private Long ticketnumber;
	
	private Date creationdate;
	
	private Date startdate;
	
	private Date enddate;

	private Long userid;
	
	private String username;
	
	private String usermail;
	
	private boolean processed;
	
	private Courier courier;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public Long getTicketnumber() {
		return ticketnumber;
	}

	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

}
