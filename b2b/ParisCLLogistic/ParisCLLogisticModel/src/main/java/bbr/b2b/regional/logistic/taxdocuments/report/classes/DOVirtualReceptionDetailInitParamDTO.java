package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOVirtualReceptionDetailInitParamDTO implements Serializable{

	private Long doid;
	private Long clientid;

	public Long getDoid() {
		return doid;
	}

	public void setDoid(Long doid) {
		this.doid = doid;
	}

	public Long getClientid() {
		return clientid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}
	
}
