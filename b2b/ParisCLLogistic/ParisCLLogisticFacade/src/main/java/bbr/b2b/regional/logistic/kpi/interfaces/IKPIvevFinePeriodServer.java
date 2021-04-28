package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFinePeriodW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevFinePeriod;

public interface IKPIvevFinePeriodServer extends IElementServer<KPIvevFinePeriod, KPIvevFinePeriodW>{
	
	public KPIvevFinePeriodW addKPIvevFinePeriod(KPIvevFinePeriodW kpivevfineperiod) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevFinePeriodW[] getKPIvevFinePeriods() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevFinePeriodW updateKPIvevFinePeriod(KPIvevFinePeriodW kpivevfineperiod) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}