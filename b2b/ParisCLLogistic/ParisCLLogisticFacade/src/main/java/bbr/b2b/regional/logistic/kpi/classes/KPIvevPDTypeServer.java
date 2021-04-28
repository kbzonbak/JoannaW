package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDTypeW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDType;

@Stateless(name = "servers/kpi/KPIvevPDTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevPDTypeServer extends LogisticElementServer<KPIvevPDType, KPIvevPDTypeW> implements KPIvevPDTypeServerLocal {

	public KPIvevPDTypeW addKPIvevPDType(KPIvevPDTypeW kpivevpdtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDTypeW) addElement(kpivevpdtype);
	}
	public KPIvevPDTypeW[] getKPIvevPDTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDTypeW[]) getIdentifiables();
	}
	public KPIvevPDTypeW updateKPIvevPDType(KPIvevPDTypeW kpivevpdtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDTypeW) updateElement(kpivevpdtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevPDType entity, KPIvevPDTypeW wrapper) {
	
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevPDType entity, KPIvevPDTypeW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevPDType";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevPDType";
	}
}