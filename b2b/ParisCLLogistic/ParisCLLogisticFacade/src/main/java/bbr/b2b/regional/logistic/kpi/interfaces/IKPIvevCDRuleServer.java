package bbr.b2b.regional.logistic.kpi.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDRuleW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDRule;

public interface IKPIvevCDRuleServer extends IElementServer<KPIvevCDRule, KPIvevCDRuleW>{
	
	public KPIvevCDRuleW addKPIvevCDRule(KPIvevCDRuleW kpivevcdrule) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDRuleW[] getKPIvevCDRules() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDRuleW updateKPIvevCDRule(KPIvevCDRuleW kpivevcdrule) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
