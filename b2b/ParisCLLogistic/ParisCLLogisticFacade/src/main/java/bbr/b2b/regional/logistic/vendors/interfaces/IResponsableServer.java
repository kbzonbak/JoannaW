package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.entities.Responsable;
import bbr.b2b.regional.logistic.vendors.data.wrappers.ResponsableW;

public interface IResponsableServer extends IElementServer<Responsable, ResponsableW> {

	ResponsableW addResponsable(ResponsableW responsable) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResponsableW[] getResponsables() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResponsableW updateResponsable(ResponsableW responsable) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
