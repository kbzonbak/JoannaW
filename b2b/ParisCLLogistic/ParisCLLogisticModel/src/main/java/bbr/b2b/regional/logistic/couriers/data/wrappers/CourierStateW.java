package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierState;

public class CourierStateW extends ElementDTO implements ICourierState {
	
	private String description;
	private Date uploaddate;
	private Date startdate;
	private Long couriertagid;
	private Long courierfileid;
	
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
