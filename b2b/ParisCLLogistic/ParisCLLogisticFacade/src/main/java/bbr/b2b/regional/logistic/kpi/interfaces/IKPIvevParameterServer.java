package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevParameterW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevParameter;

public interface IKPIvevParameterServer extends IElementServer<KPIvevParameter, KPIvevParameterW>{
	
	public KPIvevParameterW addKPIvevParameter(KPIvevParameterW kpivevfineperiod) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevParameterW[] getKPIvevParameters() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevParameterW updateKPIvevParameter(KPIvevParameterW kpivevparameter) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}