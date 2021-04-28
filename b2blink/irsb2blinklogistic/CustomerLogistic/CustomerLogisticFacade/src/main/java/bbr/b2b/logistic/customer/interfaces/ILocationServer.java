package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;

public interface ILocationServer extends IElementServer<Location, LocationW> {

	LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
