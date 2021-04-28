package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOVirtualReceptionCSVReportInitParamDTO implements Serializable{

	private String vendortradename; // vacío si es TODOS
	private Long[] doids;
	private String username;
	private String usertype;

	public String getVendortradename() {
		return vendortradename;
	}

	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}

	public Long[] getDoids() {
		return doids;
	}

	public void setDoids(Long[] doids) {
		this.doids = doids;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
			
}
