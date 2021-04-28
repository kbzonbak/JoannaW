package bbr.b2b.regional.logistic.couriers.report.classes;

import java.util.Date;

public class CourierStateFileDataDTO {

	private int line;
	private String description;
	private Date uploaddate;
	private Date startdate;
	private String couriertagsendnumber;
	private String filename;
	private Long chilexpresscourierstatetmpid;
	private Long dodeliveryid;
	private Long couriertagid;
	private Long courierfileid;
		
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public String getCouriertagsendnumber() {
		return couriertagsendnumber;
	}
	public void setCouriertagsendnumber(String couriertagsendnumber) {
		this.couriertagsendnumber = couriertagsendnumber;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getChilexpresscourierstatetmpid() {
		return chilexpresscourierstatetmpid;
	}
	public void setChilexpresscourierstatetmpid(Long chilexpresscourierstatetmpid) {
		this.chilexpresscourierstatetmpid = chilexpresscourierstatetmpid;
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
	public Long getCourierfileid() {
		return courierfileid;
	}
	public void setCourierfileid(Long courierfileid) {
		this.courierfileid = courierfileid;
	}
			
}
