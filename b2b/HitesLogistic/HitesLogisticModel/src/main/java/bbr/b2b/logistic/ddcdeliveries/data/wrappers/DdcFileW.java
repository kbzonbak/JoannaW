package bbr.b2b.logistic.ddcdeliveries.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcFile;

public class DdcFileW extends ElementDTO implements IDdcFile {

	private LocalDateTime when;
	private String filename;
	private String filetype;
	private Long ddcorderid;
	
	public LocalDateTime getWhen() {
		return when;
	}
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public Long getDdcorderid() {
		return ddcorderid;
	}
	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}

}
