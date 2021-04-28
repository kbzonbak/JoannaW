package bbr.b2b.regional.logistic.scheduler.classes;

import java.time.ZoneId;
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
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRatePeriodW;
import bbr.b2b.regional.logistic.report.classes.PendingMessageDTO;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMessage;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMessageType;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

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

	public PendingMessageW updatePendingMessage(PendingMessageW pendingstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW) updateElement(pendingstate);
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

	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMessage";
	}

	@Override
	protected void setEntityname() {
		entityname = "PendingMessage";
	}

	public PendingMessageW[] getMessageToSend(int size) {
		
		String SQL = "SELECT pm.id, pm.when1 as whenldt, pm.code, pm.factory, pm.queue, pm.content, pm.attempts, pm.lastattempt as lastattemptldt,"
				+ " pm.status, pm.pendingmessagetype_id as pendingmessagetypeid FROM logistica.pendingmessage pm inner join logistica.pendingmessagetype pmt on (pm.pendingmessagetype_id=pmt.id) WHERE pm.status=0 ORDER BY pmt.priority asc , lastattempt asc LIMIT " + size;

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingMessageDTO.class));

		List<PendingMessageDTO> list = query.list();
		
		//List<PendingMessageDTO> dataList = (List<PendingMessageDTO>)Arrays.asList(list);
		list.stream()
		            .forEach(s -> {
		                ((PendingMessageDTO) s).setLastattempt(Date.from(((PendingMessageDTO) s).getLastattemptldt().atZone(ZoneId.systemDefault()).toInstant()));
		                ((PendingMessageDTO) s).setWhen(Date.from(((PendingMessageDTO) s).getWhenldt().atZone(ZoneId.systemDefault()).toInstant()));
		}); 

		PendingMessageW[] resultW = new PendingMessageW[list.size()];
		try {
			BeanExtenderFactory.copyProperties(list.toArray(new PendingMessageDTO[list.size()]), resultW, PendingMessageW.class);
		} catch (OperationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//PendingMessageW[] result = (PendingMessageW[]) list.toArray(new PendingMessageW[list.size()]);
		return resultW;
	}

	public int doDeletePendingMessage(){
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7 * Integer.parseInt(B2BSystemPropertiesUtil.getProperty("deletepreviousweeks")));
		Date deletedate = cal.getTime();
		
		String SQL =
			"DELETE FROM LOGISTICA.PENDINGMESSAGE " +
			"WHERE STATUS = 1 AND LASTATTEMPT < :deletedate ";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("deletedate", deletedate);
		
		return query.executeUpdate();	
		
	}
	
}
