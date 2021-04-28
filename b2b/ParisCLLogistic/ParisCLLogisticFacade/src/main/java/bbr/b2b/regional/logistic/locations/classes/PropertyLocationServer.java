package bbr.b2b.regional.logistic.locations.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.locations.data.wrappers.PropertyLocationW;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.entities.PropertyLocation;

@Stateless(name = "servers/vendors/PropertyLocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PropertyLocationServer extends LogisticElementServer<PropertyLocation, PropertyLocationW> implements PropertyLocationServerLocal {

	@EJB
	LocationServerLocal locationserver;
	
	@Override
	protected void copyRelationsEntityToWrapper(PropertyLocation entity, PropertyLocationW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(PropertyLocation entity, PropertyLocationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "PropertyLocation";
	}
	@Override
	protected void setEntityname() {
		entityname = "PropertyLocation";
	}
	
	public PropertyLocationW addPropertyLocation(PropertyLocationW propertylocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyLocationW) addElement(propertylocation);
	}
	
	public PropertyLocationW[] getPropertyLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyLocationW[]) getIdentifiables();
	}
	
	public PropertyLocationW updatePropertyLocation(PropertyLocationW propertylocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyLocationW) updateElement(propertylocation);
	}
	
}