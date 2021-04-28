package bbr.b2b.logistic.locations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.locations.entities.Location;

public interface ILocationServer extends IElementServer<Location, LocationW> {

	LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getCountLocations();
	LocationW[] getPredeliveryLocationsOfOrder(Long orderid) throws OperationFailedException, NotFoundException;
}
