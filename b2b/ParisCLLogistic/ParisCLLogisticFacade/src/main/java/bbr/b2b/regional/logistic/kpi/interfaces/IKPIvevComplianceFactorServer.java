package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevComplianceFactorW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevComplianceFactor;

public interface IKPIvevComplianceFactorServer extends IElementServer<KPIvevComplianceFactor, KPIvevComplianceFactorW>{
	
	KPIvevComplianceFactorW addKPIvevComplianceFactor(KPIvevComplianceFactorW kpivevcompliancefactor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevComplianceFactorW[] getKPIvevComplianceFactors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevComplianceFactorW updateKPIvevComplianceFactor(KPIvevComplianceFactorW kpivevcompliancefactor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}