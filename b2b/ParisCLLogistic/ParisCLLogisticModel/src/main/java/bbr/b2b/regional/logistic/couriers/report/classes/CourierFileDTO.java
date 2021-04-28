package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CourierFileDTO implements Serializable{
	
	private LocalDateTime uploaddate;
	private String filename;
	
	public LocalDateTime getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(LocalDateTime uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
