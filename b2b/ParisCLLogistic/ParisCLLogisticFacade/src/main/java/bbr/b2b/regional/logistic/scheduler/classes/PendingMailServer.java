package bbr.b2b.regional.logistic.scheduler.classes;

import java.math.BigInteger;
import java.util.Calendar;
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
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMail;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMailType;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "servers/scheduler/PendingMailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMailServer extends LogisticElementServer<PendingMail, PendingMailW>  implements PendingMailServerLocal{
	
	@EJB
	PendingMailTypeServerLocal pendingmailtypeserver;
	
	public PendingMailW addPendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW) addElement(pendingmail);
	}

	public PendingMailW[] getPendingMails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW[]) getIdentifiables();	
	}

	public PendingMailW updatePendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW) updateElement(pendingmail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMail entity, PendingMailW wrapper){
		wrapper.setPendingmailtypeid(entity.getPendingmailtype() != null ? Long.valueOf(entity.getPendingmailtype().getId()) : Long.valueOf(0));		
	}

	@Override
	protected void copyRelationsWrapperToEntity(PendingMail entity, PendingMailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getPendingmailtypeid() != null && wrapper.getPendingmailtypeid() > 0) { 
			PendingMailType pendingmailtype = pendingmailtypeserver.getReferenceById(wrapper.getPendingmailtypeid());
			if (pendingmailtype != null) { 
				entity.setPendingmailtype(pendingmailtype);
			}
		}
	}
	
	public Long[] getPendingMailsToDelete() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7 * Integer.parseInt(B2BSystemPropertiesUtil.getProperty("deletepreviousweeks")));
		Date deletedate = cal.getTime();
		
		String SQL =
			"SELECT " + //
				"id " + //
			"FROM " + //
				"logistica.pendingmail " + //
			"WHERE " + //
				"status = 1 AND lastattempt < :deletedate"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("deletedate", deletedate);
		
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;	
	}
	
	public int doDeletePendingMails(Long[] pendingmailids) {
		
		String SQL =
			"DELETE " + //
			"FROM " + //
				"logistica.pendingmail " + //
			"WHERE " + //
				"id IN (:pendingmailids)"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("pendingmailids", pendingmailids);
		
		return query.executeUpdate();		
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMail";
	}

	@Override
	protected void setEntityname() {
		entityname = "PendingMail";
	}

}
