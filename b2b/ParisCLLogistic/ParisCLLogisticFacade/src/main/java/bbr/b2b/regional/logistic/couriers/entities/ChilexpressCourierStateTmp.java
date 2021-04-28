package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.IChilexpressCourierStateTmp;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;

public class ChilexpressCourierStateTmp implements IChilexpressCourierStateTmp {

	private Long id;
	private String filename;
	private Date filedate;
	private Date when;
	private Integer line;
	private String workordernumber;
	private String workorderreference;
	private String eventcode;
	private String event;
	private String eventdate;
	private String eventtime;
	private Date eventfulldate;
	private DirectOrder directorder;
	private DODelivery dodelivery;
	private CourierTag couriertag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getFiledate() {
		return filedate;
	}
	public void setFiledate(Date filedate) {
		this.filedate = filedate;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getWorkordernumber() {
		return workordernumber;
	}
	public void setWorkordernumber(String workordernumber) {
		this.workordernumber = workordernumber;
	}
	public String getWorkorderreference() {
		return workorderreference;
	}
	public void setWorkorderreference(String workorderreference) {
		this.workorderreference = workorderreference;
	}
	public String getEventcode() {
		return eventcode;
	}
	public void setEventcode(String eventcode) {
		this.eventcode = eventcode;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public String getEventtime() {
		return eventtime;
	}
	public void setEventtime(String eventtime) {
		this.eventtime = eventtime;
	}
	public Date getEventfulldate() {
		return eventfulldate;
	}
	public void setEventfulldate(Date eventfulldate) {
		this.eventfulldate = eventfulldate;
	}
	public DirectOrder getDirectorder() {
		return directorder;
	}
	public void setDirectorder(DirectOrder directorder) {
		this.directorder = directorder;
	}
	public DODelivery getDodelivery() {
		return dodelivery;
	}
	public void setDodelivery(DODelivery dodelivery) {
		this.dodelivery = dodelivery;
	}
	public CourierTag getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(CourierTag couriertag) {
		this.couriertag = couriertag;
	}
		
}
