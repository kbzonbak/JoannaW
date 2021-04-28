package bbr.b2b.regional.logistic.notifications.data.classes;

import java.io.Serializable;

public class DeleteContactInitParam implements Serializable{

	private Long[] contactids;

	public Long[] getContactids() {
		return contactids;
	}

	public void setContactids(Long[] contactids) {
		this.contactids = contactids;
	}
	
}
