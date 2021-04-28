package bbr.common.adtclasses.classes;

import java.io.Serializable;

public class LastLoadProcessDateDTO implements Serializable {

	private String process = null;
	private String date = null;

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
