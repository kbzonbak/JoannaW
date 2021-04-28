package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class DepartmentDTO implements Serializable{

	private Long id;
	private String code;
	private String name;
	private String codenamestr;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodenamestr() {
		return codenamestr;
	}
	public void setCodenamestr(String codenamestr) {
		this.codenamestr = codenamestr;
	}
	
}
