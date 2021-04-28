package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDRuleW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDRule;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDType;

@Stateless(name = "servers/kpi/KPIvevCDRuleServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevCDRuleServer extends LogisticElementServer<KPIvevCDRule, KPIvevCDRuleW> implements KPIvevCDRuleServerLocal {

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;
	
	@EJB
	KPIvevCDTypeServerLocal kpivevcdtypeserver;
	
	public KPIvevCDRuleW addKPIvevCDRule(KPIvevCDRuleW kpivevcdrule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDRuleW) addElement(kpivevcdrule);
	}
	public KPIvevCDRuleW[] getKPIvevCDRules() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDRuleW[]) getIdentifiables();
	}
	public KPIvevCDRuleW updateKPIvevCDRule(KPIvevCDRuleW kpivevcdrule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDRuleW) updateElement(kpivevcdrule);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevCDRule entity, KPIvevCDRuleW wrapper) {
		wrapper.setOrderstatetypeid(entity.getOrderstatetype() != null ? new Long(entity.getOrderstatetype().getId()) : new Long(0));
		wrapper.setIntimekpivevcdtypeid(entity.getIntimekpivevcdtype() != null ? new Long(entity.getIntimekpivevcdtype().getId()) : new Long(0));
		wrapper.setOntimekpivevcdtypeid(entity.getOntimekpivevcdtype() != null ? new Long(entity.getOntimekpivevcdtype().getId()) : new Long(0));
		wrapper.setDelayedkpivevcdtypeid(entity.getDelayedkpivevcdtype() != null ? new Long(entity.getDelayedkpivevcdtype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevCDRule entity, KPIvevCDRuleW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderstatetypeid() != null && wrapper.getOrderstatetypeid() > 0) { 
			OrderStateType orderstatetype = orderstatetypeserver.getReferenceById(wrapper.getOrderstatetypeid());
			if (orderstatetype != null) { 
				entity.setOrderstatetype(orderstatetype);
			}
		}
		if (wrapper.getIntimekpivevcdtypeid() != null && wrapper.getIntimekpivevcdtypeid() > 0) { 
			KPIvevCDType intimekpivevcdtype = kpivevcdtypeserver.getReferenceById(wrapper.getIntimekpivevcdtypeid());
			if (intimekpivevcdtype != null) { 
				entity.setIntimekpivevcdtype(intimekpivevcdtype);
			}
		}
		if (wrapper.getOntimekpivevcdtypeid() != null && wrapper.getOntimekpivevcdtypeid() > 0) { 
			KPIvevCDType ontimekpivevcdtype = kpivevcdtypeserver.getReferenceById(wrapper.getOntimekpivevcdtypeid());
			if (ontimekpivevcdtype != null) { 
				entity.setOntimekpivevcdtype(ontimekpivevcdtype);
			}
		}
		if (wrapper.getDelayedkpivevcdtypeid() != null && wrapper.getDelayedkpivevcdtypeid() > 0) { 
			KPIvevCDType delayedkpivevcdtype = kpivevcdtypeserver.getReferenceById(wrapper.getDelayedkpivevcdtypeid());
			if (delayedkpivevcdtype != null) { 
				entity.setDelayedkpivevcdtype(delayedkpivevcdtype);
			}
		}
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevCDRule";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevCDRule";
	}
}
