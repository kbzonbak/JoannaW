package bbr.b2b.regional.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.datings.entities.DatingResource;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingResourceW;
import bbr.b2b.regional.logistic.datings.entities.DatingResourceId;
public interface IDatingResourceServer extends IGenericServer<DatingResource, DatingResourceId, DatingResourceW> {

	DatingResourceW addDatingResource(DatingResourceW datingresource) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingResourceW[] getDatingResources() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingResourceW updateDatingResource(DatingResourceW datingresource) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
