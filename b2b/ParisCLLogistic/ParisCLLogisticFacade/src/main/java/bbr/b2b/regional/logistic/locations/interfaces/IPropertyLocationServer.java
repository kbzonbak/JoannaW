package bbr.b2b.regional.logistic.locations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.locations.data.wrappers.PropertyLocationW;
import bbr.b2b.regional.logistic.locations.entities.PropertyLocation;

public interface IPropertyLocationServer extends IElementServer<PropertyLocation, PropertyLocationW> {

	PropertyLocationW addPropertyLocation(PropertyLocationW propertylocation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PropertyLocationW[] getPropertyLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PropertyLocationW updatePropertyLocation(PropertyLocationW propertylocation) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
