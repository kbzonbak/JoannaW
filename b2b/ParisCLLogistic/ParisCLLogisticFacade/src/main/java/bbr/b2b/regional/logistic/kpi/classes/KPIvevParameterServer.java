package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevParameterW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevParameter;

@Stateless(name = "servers/kpi/KPIvevParameterServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevParameterServer extends LogisticElementServer<KPIvevParameter, KPIvevParameterW> implements KPIvevParameterServerLocal {

	public KPIvevParameterW addKPIvevParameter(KPIvevParameterW kpivevparameter) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevParameterW) addElement(kpivevparameter);
	}
	public KPIvevParameterW[] getKPIvevParameters() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevParameterW[]) getIdentifiables();
	}
	public KPIvevParameterW updateKPIvevParameter(KPIvevParameterW kpivevparameter) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevParameterW) updateElement(kpivevparameter);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevParameter entity, KPIvevParameterW wrapper) {
	
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevParameter entity, KPIvevParameterW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevParameter";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevParameter";
	}
}