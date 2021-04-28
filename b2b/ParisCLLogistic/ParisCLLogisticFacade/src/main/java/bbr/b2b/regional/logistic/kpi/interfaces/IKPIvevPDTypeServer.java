package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDTypeW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDType;

public interface IKPIvevPDTypeServer extends IElementServer<KPIvevPDType, KPIvevPDTypeW>{

	public KPIvevPDTypeW addKPIvevPDType(KPIvevPDTypeW kpivevpdtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDTypeW[] getKPIvevPDTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDTypeW updateKPIvevPDType(KPIvevPDTypeW kpivevpdtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}