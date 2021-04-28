package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Location;

@Stateless(name = "servers/customer/LocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LocationServer extends LogisticElementServer<Location, LocationW> implements LocationServerLocal {


	@EJB
	BuyerServerLocal buyerserver;

	public LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) addElement(location);
	}
	public LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW[]) getIdentifiables();
	}
	public LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) updateElement(location);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Location entity, LocationW wrapper) {
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Location entity, LocationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Location";
	}
	@Override
	protected void setEntityname() {
		entityname = "Location";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
