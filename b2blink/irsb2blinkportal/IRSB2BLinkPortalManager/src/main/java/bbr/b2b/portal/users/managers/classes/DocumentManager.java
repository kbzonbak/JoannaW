package bbr.b2b.portal.users.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.IDocumentManager;
import bbr.b2b.users.report.classes.DocDocumentFunctionalityDTO;

@Stateless(name = "managers/DocumentManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentManager implements DocumentManagerLocal {

	private IDocumentManager documentManager = null;
	
	@PostConstruct
	public void getRemote() {
		try {
			documentManager = ContextUtil.getInstance().getIDocumentManager();
		} catch (NamingException e) {
			e.printStackTrace();			
		}
	}	
	
	@Override
	public DocDocumentFunctionalityDTO[] getDocumentsByFunctionalityName(String name) {
		DocDocumentFunctionalityDTO[] docDocumentFunctionalityDTOs = null;
		try {
			docDocumentFunctionalityDTOs = documentManager.getDocumentsByFunctionalityName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docDocumentFunctionalityDTOs;
	}
}
