package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IDepartment;

public class DepartmentW extends ElementDTO implements IDepartment {

	private String code;
	private String name;

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
}
