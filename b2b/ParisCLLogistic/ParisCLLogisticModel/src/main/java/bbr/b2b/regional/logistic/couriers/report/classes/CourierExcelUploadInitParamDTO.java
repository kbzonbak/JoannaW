package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CourierExcelUploadInitParamDTO implements Serializable {
	
	private CourierExcelUploadDTO[] uploadlines;

	public CourierExcelUploadDTO[] getUploadlines() {
		return uploadlines;
	}

	public void setUploadlines(CourierExcelUploadDTO[] uploadlines) {
		this.uploadlines = uploadlines;
	}

}
