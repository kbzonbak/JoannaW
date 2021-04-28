package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.DocDocumentFunctionalityDTO;


public interface IDocumentManager extends IGenericEJBInterface{

	public DocDocumentFunctionalityDTO[] getDocumentsByFunctionalityName(String name);

}
