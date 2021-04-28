package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DOReceptionType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DOReceptionTypeW;

public interface IDOReceptionTypeServer extends IElementServer<DOReceptionType, DOReceptionTypeW> {

	DOReceptionTypeW addDOReceptionType(DOReceptionTypeW doreceptiontype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOReceptionTypeW[] getDOReceptionTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOReceptionTypeW updateDOReceptionType(DOReceptionTypeW doreceptiontype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
