package bbr.b2b.regional.logistic.kpi.classes;

import java.time.ZoneId;
import java.util.Date;

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
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevExecutionStateDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevExecutionStateW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevExecutionState;

@Stateless(name = "servers/kpi/KPIvevExecutionStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevExecutionStateServer extends LogisticElementServer<KPIvevExecutionState, KPIvevExecutionStateW> implements KPIvevExecutionStateServerLocal {

	public KPIvevExecutionStateW addKPIvevExecutionState(KPIvevExecutionStateW kpivevexecutionstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevExecutionStateW) addElement(kpivevexecutionstate);
	}
	public KPIvevExecutionStateW[] getKPIvevExecutionStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevExecutionStateW[]) getIdentifiables();
	}
	public KPIvevExecutionStateW updateKPIvevExecutionState(KPIvevExecutionStateW kpivevexecutionstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevExecutionStateW) updateElement(kpivevexecutionstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevExecutionState entity, KPIvevExecutionStateW wrapper) {
		
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevExecutionState entity, KPIvevExecutionStateW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevExecutionState";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevExecutionState";
	}	
	
	public KPIvevExecutionStateW getLastSuccessfulKPIvevExecution(String type){
		
		KPIvevExecutionStateDTO resulttmp = null;
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevExecutionState.getLastSuccessfulKPIvevExecution");
		query.setString("type", type);
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevExecutionStateDTO.class));	
		
		resulttmp = (KPIvevExecutionStateDTO)query.uniqueResult();
		
		resulttmp.setWhen1(Date.from(resulttmp.getWhen1ldt().atZone(ZoneId.systemDefault()).toInstant()));
		
		KPIvevExecutionStateW result = new KPIvevExecutionStateW();
		
		try {
			BeanExtenderFactory.copyProperties(resulttmp, result);
		} catch (OperationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return result;	
	}
}
