package bbr.b2b.regional.logistic.notifications.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.notifications.entities.Contact;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportDTO;
import bbr.b2b.regional.logistic.notifications.data.wrappers.ContactW;

public interface IContactServer extends IElementServer<Contact, ContactW> {

	ContactW addContact(ContactW contact) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ContactW[] getContacts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ContactW updateContact(ContactW contact) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public ContactReportDTO[] getContactsReport(Long vendorid,Integer pagenumber,Integer rows,OrderCriteriaDTO[] order, Boolean isPaginated) throws AccessDeniedException;
	public int countContactsReport(Long vendorid) throws AccessDeniedException;
	
}
