package bbr.b2b.regional.logistic.taxdocuments.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateTypeW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentStateType;

public interface IDOTaxDocumentStateTypeServer extends IElementServer<DOTaxDocumentStateType, DOTaxDocumentStateTypeW> {

	public DOTaxDocumentStateTypeW addDOTaxDocumentStateType(DOTaxDocumentStateTypeW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException;
	public DOTaxDocumentStateTypeW[] getDOTaxDocumentStateTypes() throws OperationFailedException, NotFoundException;
	public DOTaxDocumentStateTypeW updateDOTaxDocumentStateType(DOTaxDocumentStateTypeW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException;

}
