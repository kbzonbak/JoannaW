package bbr.b2b.logistic.locations.classes;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.locations.entities.Location;

@Stateless(name = "servers/locations/LocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LocationServer extends LogisticElementServer<Location, LocationW> implements LocationServerLocal {


	public LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) addElement(location);
	}
	public LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW[]) getIdentifiables();
	}
	public LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) updateElement(location);
	}

	public int getCountLocations(){
		String sql = "SELECT COUNT(1) FROM logistica.location ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	public LocationW[] getPredeliveryLocationsOfOrder(Long orderid) throws OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.locations.entities.Location.getPredeliveryLocationsOfOrder");
		query.setLong("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(LocationW.class));
		List list = query.list();
		LocationW[] result = (LocationW[]) list.toArray(new LocationW[list.size()]);
		return result;
	}	
	
	@Override
	protected void copyRelationsEntityToWrapper(Location entity, LocationW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Location entity, LocationW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Location";
	}
	@Override
	protected void setEntityname() {
		entityname = "Location";
	}
}
