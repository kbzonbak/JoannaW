package bbr.b2b.logistic.notifications.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ContactReportArrayDTO extends BaseResultDTO {

	private ContactReportDTO[] contacts;
	private PageInfoDTO pageinfo;
	
	public ContactReportDTO[] getContacts() {
		return contacts;
	}
	public void setContacts(ContactReportDTO[] contacts) {
		this.contacts = contacts;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
}
