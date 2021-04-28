package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDTypeW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDType;

@Stateless(name = "servers/kpi/KPIvevCDTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevCDTypeServer extends LogisticElementServer<KPIvevCDType, KPIvevCDTypeW> implements KPIvevCDTypeServerLocal {

	public KPIvevCDTypeW addKPIvevCDType(KPIvevCDTypeW kpivevcdtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDTypeW) addElement(kpivevcdtype);
	}
	public KPIvevCDTypeW[] getKPIvevCDTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDTypeW[]) getIdentifiables();
	}
	public KPIvevCDTypeW updateKPIvevCDType(KPIvevCDTypeW kpivevcdtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDTypeW) updateElement(kpivevcdtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevCDType entity, KPIvevCDTypeW wrapper) {
	
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevCDType entity, KPIvevCDTypeW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevCDType";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevCDType";
	}
}
