package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DockDTO implements Serializable{

	private Long id;
	private String code;
	private int visualorder;
	private boolean active;
	private Long docktypeid;
	private Long locationid;
		
	public DockDTO(){		
	}
	
	public DockDTO(String code, int visualorder, boolean active, Long docktypeid, Long locationid) {
		this.code = code;
		this.visualorder = visualorder;
		this.active = active;
		this.docktypeid = docktypeid;
		this.locationid = locationid;	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(int visualorder) {
		this.visualorder = visualorder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getDocktypeid() {
		return docktypeid;
	}

	public void setDocktypeid(Long docktypeid) {
		this.docktypeid = docktypeid;
	}

	public Long getLocationid() {
		return locationid;
	}

	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
}