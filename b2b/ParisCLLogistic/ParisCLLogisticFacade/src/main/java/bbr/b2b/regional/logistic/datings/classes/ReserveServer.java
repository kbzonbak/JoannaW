package bbr.b2b.regional.logistic.datings.classes;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveW;
import bbr.b2b.regional.logistic.datings.entities.Reserve;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDataDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;

@Stateless(name = "servers/datings/ReserveServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReserveServer extends LogisticElementServer<Reserve, ReserveW> implements ReserveServerLocal {


	@EJB
	LocationServerLocal locationserver;

	public ReserveW addReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW) addElement(reserve);
	}
	public ReserveW[] getReserves() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW[]) getIdentifiables();
	}
	public ReserveW updateReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW) updateElement(reserve);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Reserve entity, ReserveW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Reserve entity, ReserveW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}
	
	public ReserveDataDTO[] getReserveData(Long reserveid) throws NotFoundException, OperationFailedException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Reserve.getReserveData");
		query.setLong("reserveid", reserveid.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDataDTO.class));
		List list = query.list();
		ReserveDataDTO[] result = (ReserveDataDTO[]) list.toArray(new ReserveDataDTO[list.size()]);
		return result;
	}
	
	public ReserveDTO[] getReservesByDateAndLocation(Date since, Date until, Long locationid) throws NotFoundException, OperationFailedException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Reserve.getReservesByDateAndLocation");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("locationid", locationid.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDTO.class));
		List<?> list = query.list();
		ReserveDTO[] result = (ReserveDTO[]) list.toArray(new ReserveDTO[list.size()]);
		return result;
	}
	
	public void doDeleteReserves(Long[] reserveids) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Reserve.doDeleteReserves");
		query.setParameterList("reserveids", reserveids);
		query.executeUpdate();
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Reserve";
	}
	@Override
	protected void setEntityname() {
		entityname = "Reserve";
	}
}
