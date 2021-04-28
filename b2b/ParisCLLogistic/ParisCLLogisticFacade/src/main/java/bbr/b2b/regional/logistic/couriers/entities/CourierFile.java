package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierFile;

public class CourierFile implements ICourierFile {
	private Long id;
	private Date uploaddate;
	private String filename;
	private Boolean dayloaded;
	
	private Courier courier;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	
}
