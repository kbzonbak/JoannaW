package bbr.b2b.regional.logistic.couriers.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.RescheduleReasonW;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.couriers.report.classes.RescheduleReasonDTO;

@Stateless(name = "servers/couriers/RescheduleReasonServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RescheduleReasonServer extends LogisticElementServer<RescheduleReason, RescheduleReasonW> implements RescheduleReasonServerLocal {
	
	public RescheduleReasonW updateRescheduleReason(RescheduleReasonW reschedulereason) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RescheduleReasonW) updateElement(reschedulereason);
	}

	public RescheduleReasonW addRescheduleReason(RescheduleReasonW reschedulereason) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RescheduleReasonW) addElement(reschedulereason);
	}
	
	public RescheduleReasonW[] getRescheduleReasons() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RescheduleReasonW[]) getIdentifiables();
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(RescheduleReason entity, RescheduleReasonW wrapper) {
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(RescheduleReason entity, RescheduleReasonW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "RescheduleReason";
	}
	@Override
	protected void setEntityname() {
		entityname = "RescheduleReason";
	}
	
	public RescheduleReasonDTO[] getShowableRescheduleReasons() {
		
		String SQL =
			"SELECT " + //
				"id, " + //
				"code, " + //
				"description, " + //
				"responsibility " + //
			"FROM " + //
				"logistica.reschedulereason " + //
			"WHERE " + //
				"showable IS TRUE " + //
			"ORDER BY " + //
				"visualorder ASC"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(RescheduleReasonDTO.class));
		
		List list = query.list();
		return (RescheduleReasonDTO[]) list.toArray(new RescheduleReasonDTO[list.size()]);		
	}
}