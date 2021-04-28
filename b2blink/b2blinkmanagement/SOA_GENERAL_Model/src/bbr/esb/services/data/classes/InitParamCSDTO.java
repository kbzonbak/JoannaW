package bbr.esb.services.data.classes;

import bbr.esb.services.data.interfaces.IInitParamCSDTO;

public class InitParamCSDTO implements IInitParamCSDTO{

	String credenciales;
	String accesscode;
	String companyname;
	
	public String getCredenciales() {
		return credenciales;
	}
	public void setCredenciales(String credenciales) {
		this.credenciales = credenciales;
	}	
	public String getAccesscode() {
		return accesscode;
	}
	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	
	
}
