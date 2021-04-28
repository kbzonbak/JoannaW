package bbr.b2b.regional.logistic.notifications.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ContactInfoDTO extends BaseResultDTO {

	private Long contactid;
	private String contactname;
	private String contactlastname;
	private String contactposition;
	private String contactemail;
	private String vendorcode;

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactlastname() {
		return contactlastname;
	}

	public void setContactlastname(String contactlastname) {
		this.contactlastname = contactlastname;
	}

	public String getContactposition() {
		return contactposition;
	}

	public void setContactposition(String contactposition) {
		this.contactposition = contactposition;
	}

	public String getContactemail() {
		return contactemail;
	}

	public void setContactemail(String contactemail) {
		this.contactemail = contactemail;
	}



	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getContactid() {
		return contactid;
	}

	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}

}
