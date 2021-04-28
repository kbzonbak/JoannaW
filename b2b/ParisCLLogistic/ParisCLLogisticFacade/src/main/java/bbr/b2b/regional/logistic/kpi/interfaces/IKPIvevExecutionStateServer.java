package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevExecutionStateW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevExecutionState;

public interface IKPIvevExecutionStateServer extends IElementServer<KPIvevExecutionState, KPIvevExecutionStateW>{
	
	public KPIvevExecutionStateW addKPIvevExecutionState(KPIvevExecutionStateW kpivevexecutionstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevExecutionStateW[] getKPIvevExecutionStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevExecutionStateW updateKPIvevExecutionState(KPIvevExecutionStateW kpivevexecutionstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public KPIvevExecutionStateW getLastSuccessfulKPIvevExecution(String type);
}
