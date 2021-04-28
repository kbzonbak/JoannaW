package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevComplianceFactorW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevComplianceFactor;

@Stateless(name = "servers/kpi/KPIvevComplianceFactorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevComplianceFactorServer extends LogisticElementServer<KPIvevComplianceFactor, KPIvevComplianceFactorW> implements KPIvevComplianceFactorServerLocal {

	public KPIvevComplianceFactorW addKPIvevComplianceFactor(KPIvevComplianceFactorW kpivevcompliancefactor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevComplianceFactorW) addElement(kpivevcompliancefactor);
	}
	public KPIvevComplianceFactorW[] getKPIvevComplianceFactors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevComplianceFactorW[]) getIdentifiables();
	}
	public KPIvevComplianceFactorW updateKPIvevComplianceFactor(KPIvevComplianceFactorW kpivevcompliancefactor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevComplianceFactorW) updateElement(kpivevcompliancefactor);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevComplianceFactor entity, KPIvevComplianceFactorW wrapper) {
	
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevComplianceFactor entity, KPIvevComplianceFactorW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevComplianceFactor";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevComplianceFactor";
	}
	
}