package bbr.b2b.logistic.ddcdeliveries.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcFile;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;

public class DdcFile implements IDdcFile {

	private Long id;
	private LocalDateTime when;
	private String filename;
	private String filetype;
	private DdcOrder ddcorder;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public DdcOrder getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrder ddcorder) {
		this.ddcorder = ddcorder;
	}

}
