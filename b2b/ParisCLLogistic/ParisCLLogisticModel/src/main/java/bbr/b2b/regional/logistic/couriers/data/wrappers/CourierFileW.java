package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierFile;

public class CourierFileW extends ElementDTO implements ICourierFile {
	
	private Date uploaddate;
	private String filename;
	private Boolean dayloaded;
	private Long courierid;
	
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Boolean getDayloaded() {
		return dayloaded;
	}
	public void setDayloaded(Boolean dayloaded) {
		this.dayloaded = dayloaded;
	}
	public Long getCourierid() {
		return courierid;
	}
	public void setCourierid(Long courierid) {
		this.courierid = courierid;
	}
}
