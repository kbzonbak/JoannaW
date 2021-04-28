package bbr.b2b.regional.logistic.report.classes;

import java.io.Serializable;

public class ValidationErrorsInitParamsDTO implements Serializable{

	private String[] data;
	private String filename;
	
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}	
}
