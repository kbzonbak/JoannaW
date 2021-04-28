package bbr.b2b.logistic.scheduler.classes;

import java.time.LocalDateTime;
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
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.scheduler.entities.PendingMessage;
import bbr.b2b.logistic.scheduler.entities.PendingMessageType;
import bbr.b2b.logistic.scheduler.report.classes.PendingMessageDTO;

@Stateless(name = "servers/scheduler/PendingMessageServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageServer extends LogisticElementServer<PendingMessage, PendingMessageW> implements PendingMessageServerLocal {


	@EJB
	PendingMessageTypeServerLocal pendingmessagetypeserver;

	public PendingMessageW addPendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW) addElement(pendingmessage);
	}
	public PendingMessageW[] getPendingMessages() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW[]) getIdentifiables();
	}
	public PendingMessageW updatePendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW) updateElement(pendingmessage);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMessage entity, PendingMessageW wrapper) {
		wrapper.setPendingmessagetypeid(entity.getPendingmessagetype() != null ? new Long(entity.getPendingmessagetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(PendingMessage entity, PendingMessageW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getPendingmessagetypeid() != null && wrapper.getPendingmessagetypeid() > 0) { 
			PendingMessageType pendingmessagetype = pendingmessagetypeserver.getReferenceById(wrapper.getPendingmessagetypeid());
			if (pendingmessagetype != null) { 
				entity.setPendingmessagetype(pendingmessagetype);
			}
		}
	}

	public PendingMessageDTO[] getMessageToSend(int size) throws OperationFailedException, NotFoundException {

		String SQL = "SELECT " + //
						"pm.id, " + //
						"pm.when1 as when, " + //
						"pm.code, " + //
						"pm.factory, " + //
						"pm.queue, " + //
						"pm.charset, " + //
						"pm.content, " + //"cast (pm.content as varchar(32672)) as content, "
						"pm.attempts, " + //
						"pm.lastattempt, " + //
						"pm.status, " + //
						"pm.datatosend as datatosend, " + //
						"pm.headervalues as headervalues, " + //
						"pm.pendingmessagetype_id as pendingmessagetypeid " + //
					"FROM logistica.pendingmessage pm " + //
					"JOIN logistica.pendingmessagetype pmt " + //
					"ON (pm.pendingmessagetype_id=pmt.id) " + //
					"WHERE pm.status = 0 " + // 
					"ORDER BY pmt.priority asc , lastattempt asc fetch first " + size + " rows only";
		SQLQuery query =  getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingMessageDTO.class));
		List<?> list = query.list();
		return list.toArray(new PendingMessageDTO[list.size()]);
		
	}

	public int doDeletePendingMessage() {
		Integer weekstodelete = LogisticConstants.getDELETE_PREVIOUS_WEEKS();
		Integer daystodelete = 7 * weekstodelete;
		LocalDateTime deletedate = LocalDateTime.now().minusDays(daystodelete);
		
		String SQL = 	"DELETE FROM LOGISTICA.PENDINGMESSAGE " + //
						"WHERE STATUS = 1 " + //
						"AND LASTATTEMPT < :deletedate ";

		SQLQuery query =  getSession().createSQLQuery(SQL);
		query.setParameter("deletedate", deletedate);
		
		return query.executeUpdate();
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMessage";
	}
	@Override
	protected void setEntityname() {
		entityname = "PendingMessage";
	}
}
