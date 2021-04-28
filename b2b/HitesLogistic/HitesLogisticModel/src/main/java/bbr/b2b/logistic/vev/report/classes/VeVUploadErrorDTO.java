package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class VeVUploadErrorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8346312794125638022L;
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
