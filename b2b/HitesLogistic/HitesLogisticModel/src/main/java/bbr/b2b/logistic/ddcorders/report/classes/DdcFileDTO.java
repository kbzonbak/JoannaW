package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

public class DdcFileDTO implements Serializable {

	private Long ddcfileid;
	private String ddcfilename;
	private String ddcfiletype;
	private Long ddcorderid;
	private Long ddcordernumber;
	
	public Long getDdcfileid() {
		return ddcfileid;
	}
	public void setDdcfileid(Long ddcfileid) {
		this.ddcfileid = ddcfileid;
	}
	public String getDdcfilename() {
		return ddcfilename;
	}
	public void setDdcfilename(String ddcfilename) {
		this.ddcfilename = ddcfilename;
	}
	public String getDdcfiletype() {
		return ddcfiletype;
	}
	public void setDdcfiletype(String ddcfiletype) {
		this.ddcfiletype = ddcfiletype;
	}
	public Long getDdcorderid() {
		return ddcorderid;
	}
	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}
	public Long getDdcordernumber() {
		return ddcordernumber;
	}
	public void setDdcordernumber(Long ddcordernumber) {
		this.ddcordernumber = ddcordernumber;
	}
	
}
