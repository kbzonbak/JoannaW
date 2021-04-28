package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class AsnUploadErrorDTO implements Serializable {

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
