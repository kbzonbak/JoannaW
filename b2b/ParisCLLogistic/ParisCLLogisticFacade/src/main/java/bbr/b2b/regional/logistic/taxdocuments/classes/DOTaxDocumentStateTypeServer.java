package bbr.b2b.regional.logistic.taxdocuments.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateTypeW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentStateType;

@Stateless(name = "servers/taxdocuments/DOTaxDocumentStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOTaxDocumentStateTypeServer extends LogisticElementServer<DOTaxDocumentStateType, DOTaxDocumentStateTypeW> implements DOTaxDocumentStateTypeServerLocal {

	public DOTaxDocumentStateTypeW addDOTaxDocumentStateType(DOTaxDocumentStateTypeW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateTypeW) addElement(dotaxdocumentstatetype);
	}
	
	public DOTaxDocumentStateTypeW[] getDOTaxDocumentStateTypes() throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateTypeW[]) getIdentifiables();
	}
	
	public DOTaxDocumentStateTypeW updateDOTaxDocumentStateType(DOTaxDocumentStateTypeW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateTypeW) updateElement(dotaxdocumentstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DOTaxDocumentStateType entity, DOTaxDocumentStateTypeW wrapper) {

	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DOTaxDocumentStateType entity, DOTaxDocumentStateTypeW wrapper) throws OperationFailedException, NotFoundException {
		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DOTaxDocumentStateType";
	}
	
	@Override
	protected void setEntityname() {
		entityname = "DOTaxDocumentStateType";
	}
	
}