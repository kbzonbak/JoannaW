package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class SaleStoreDTO implements Serializable{

	private Long id;
	private String code;
	private String description;
	private String codedescriptionstr;

	public String getCode(){ 
		return this.code;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodedescriptionstr() {
		return codedescriptionstr;
	}
	public void setCodedescriptionstr(String codedescriptionstr) {
		this.codedescriptionstr = codedescriptionstr;
	}
		
}
