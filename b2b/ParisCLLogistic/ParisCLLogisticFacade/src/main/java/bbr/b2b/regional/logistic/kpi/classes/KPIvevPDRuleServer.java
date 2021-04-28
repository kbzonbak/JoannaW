package bbr.b2b.regional.logistic.kpi.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDRuleW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDRule;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDType;

@Stateless(name = "servers/kpi/KPIvevPDRuleServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevPDRuleServer extends LogisticElementServer<KPIvevPDRule, KPIvevPDRuleW> implements KPIvevPDRuleServerLocal {

	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;
	
	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;
	
	@EJB
	KPIvevPDTypeServerLocal kpivevpdtypeserver;
	
	public KPIvevPDRuleW addKPIvevPDRule(KPIvevPDRuleW kpivevpdrule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDRuleW) addElement(kpivevpdrule);
	}
	public KPIvevPDRuleW[] getKPIvevPDRules() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDRuleW[]) getIdentifiables();
	}
	public KPIvevPDRuleW updateKPIvevPDRule(KPIvevPDRuleW kpivevpdrule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDRuleW) updateElement(kpivevpdrule);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevPDRule entity, KPIvevPDRuleW wrapper) {
		wrapper.setDirectorderstatetypeid(entity.getDirectorderstatetype() != null ? new Long(entity.getDirectorderstatetype().getId()) : new Long(0));
		wrapper.setCurrentdodeliverystatetypeid(entity.getCurrentdodeliverystatetype() != null ? new Long(entity.getCurrentdodeliverystatetype().getId()) : new Long(0));
		wrapper.setIntimekpivevpdtypeid(entity.getIntimekpivevpdtype() != null ? new Long(entity.getIntimekpivevpdtype().getId()) : new Long(0));
		wrapper.setOntimekpivevpdtypeid(entity.getOntimekpivevpdtype() != null ? new Long(entity.getOntimekpivevpdtype().getId()) : new Long(0));
		wrapper.setDelayedkpivevpdtypeid(entity.getDelayedkpivevpdtype() != null ? new Long(entity.getDelayedkpivevpdtype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevPDRule entity, KPIvevPDRuleW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDirectorderstatetypeid() != null && wrapper.getDirectorderstatetypeid() > 0) { 
			DirectOrderStateType directorderstatetype = directorderstatetypeserver.getReferenceById(wrapper.getDirectorderstatetypeid());
			if (directorderstatetype != null) { 
				entity.setDirectorderstatetype(directorderstatetype);
			}
		}
		if (wrapper.getCurrentdodeliverystatetypeid() != null && wrapper.getCurrentdodeliverystatetypeid() > 0) { 
			DODeliveryStateType currentdodeliverystatetype = dodeliverystatetypeserver.getReferenceById(wrapper.getCurrentdodeliverystatetypeid());
			if (currentdodeliverystatetype != null) { 
				entity.setCurrentdodeliverystatetype(currentdodeliverystatetype);
			}
		}
		if (wrapper.getIntimekpivevpdtypeid() != null && wrapper.getIntimekpivevpdtypeid() > 0) { 
			KPIvevPDType intimekpivevpdtype = kpivevpdtypeserver.getReferenceById(wrapper.getIntimekpivevpdtypeid());
			if (intimekpivevpdtype != null) { 
				entity.setIntimekpivevpdtype(intimekpivevpdtype);
			}
		}
		if (wrapper.getOntimekpivevpdtypeid() != null && wrapper.getOntimekpivevpdtypeid() > 0) { 
			KPIvevPDType ontimekpivevpdtype = kpivevpdtypeserver.getReferenceById(wrapper.getOntimekpivevpdtypeid());
			if (ontimekpivevpdtype != null) { 
				entity.setOntimekpivevpdtype(ontimekpivevpdtype);
			}
		}
		if (wrapper.getDelayedkpivevpdtypeid() != null && wrapper.getDelayedkpivevpdtypeid() > 0) { 
			KPIvevPDType delayedkpivevpdtype = kpivevpdtypeserver.getReferenceById(wrapper.getDelayedkpivevpdtypeid());
			if (delayedkpivevpdtype != null) { 
				entity.setDelayedkpivevpdtype(delayedkpivevpdtype);
			}
		}
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevPDRule";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevPDRule";
	}
}
