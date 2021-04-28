package bbr.b2b.regional.logistic.report.classes;

import java.io.Serializable;

public class UploadErrorDTO implements Serializable {

	private Integer line;
	private String error;
	
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}