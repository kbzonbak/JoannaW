package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDRuleW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDRule;

public interface IKPIvevPDRuleServer extends IElementServer<KPIvevPDRule, KPIvevPDRuleW>{
	
	public KPIvevPDRuleW addKPIvevPDRule(KPIvevPDRuleW kpivevpdrule) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDRuleW[] getKPIvevPDRules() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDRuleW updateKPIvevPDRule(KPIvevPDRuleW kpivevpdrule) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
