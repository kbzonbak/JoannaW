package bbr.b2b.logistic.notifications.report.classes;

import java.io.Serializable;

public class DeleteContactInitParamDTO implements Serializable {

	private Long[] contactids;

	public Long[] getContactids() {
		return contactids;
	}

	public void setContactids(Long[] contactids) {
		this.contactids = contactids;
	}
	
}
