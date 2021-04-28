package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.IChilexpressCourierStateTmp;

public class ChilexpressCourierStateTmpW extends ElementDTO implements IChilexpressCourierStateTmp {

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
	private Long directorderid;
	private Long dodeliveryid;
	private Long couriertagid;
	
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
	public Long getDirectorderid() {
		return directorderid;
	}
	public void setDirectorderid(Long directorderid) {
		this.directorderid = directorderid;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public Long getCouriertagid() {
		return couriertagid;
	}
	public void setCouriertagid(Long couriertagid) {
		this.couriertagid = couriertagid;
	}
	
}
