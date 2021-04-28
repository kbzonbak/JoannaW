package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFinePeriodW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevFinePeriod;

@Stateless(name = "servers/kpi/KPIvevFinePeriodServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevFinePeriodServer extends LogisticElementServer<KPIvevFinePeriod, KPIvevFinePeriodW> implements KPIvevFinePeriodServerLocal {

	public KPIvevFinePeriodW addKPIvevFinePeriod(KPIvevFinePeriodW kpivevfineperiod) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFinePeriodW) addElement(kpivevfineperiod);
	}
	public KPIvevFinePeriodW[] getKPIvevFinePeriods() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFinePeriodW[]) getIdentifiables();
	}
	public KPIvevFinePeriodW updateKPIvevFinePeriod(KPIvevFinePeriodW kpivevfineperiod) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFinePeriodW) updateElement(kpivevfineperiod);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevFinePeriod entity, KPIvevFinePeriodW wrapper) {
	
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevFinePeriod entity, KPIvevFinePeriodW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevFinePeriod";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevFinePeriod";
	}
}