package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDTypeW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDType;

public interface IKPIvevCDTypeServer extends IElementServer<KPIvevCDType, KPIvevCDTypeW>{

	public KPIvevCDTypeW addKPIvevCDType(KPIvevCDTypeW kpivevcdtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDTypeW[] getKPIvevCDTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDTypeW updateKPIvevCDType(KPIvevCDTypeW kpivevcdtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
