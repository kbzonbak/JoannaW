package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.customer.data.wrappers.PredistributionW;
import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.DetailId;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.entities.Predistribution;
import bbr.b2b.logistic.customer.entities.PredistributionId;

@Stateless(name = "servers/customer/PredistributionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PredistributionServer extends BaseLogisticEJB3Server<Predistribution, PredistributionId, PredistributionW> implements PredistributionServerLocal {


	@EJB
	LocationServerLocal localserver;

	@EJB
	DetailServerLocal detailserver;

	public PredistributionW addPredistribution(PredistributionW predistribution) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PredistributionW) addIdentifiable(predistribution);
	}
	public PredistributionW[] getPredistributions() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PredistributionW[]) getIdentifiables();
	}
	public PredistributionW updatePredistribution(PredistributionW predistribution) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PredistributionW) updateIdentifiable(predistribution);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Predistribution entity, PredistributionW wrapper) {
		wrapper.setLocalid(entity.getLocal() != null ? new Long(entity.getLocal().getId()) : new Long(0));
		wrapper.setOrderid(entity.getDetail().getId() != null ? new Long(entity.getDetail().getId().getOrderid()) : new Long(0));
		wrapper.setSkubuyer(entity.getDetail().getId() != null ? entity.getDetail().getId().getSkubuyer() : "");
		wrapper.setPosition(entity.getDetail().getId() != null ? entity.getDetail().getId().getPosition() : new Integer(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Predistribution entity, PredistributionW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocalid() != null && wrapper.getLocalid() > 0) { 
			Location local = localserver.getReferenceById(wrapper.getLocalid());
			if (local != null) { 
				entity.setLocal(local);
			}
		}
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getSkubuyer() != null && !wrapper.getSkubuyer().isEmpty())) {
			DetailId key = new DetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setSkubuyer(wrapper.getSkubuyer());
			key.setPosition(wrapper.getPosition());
			Detail var = detailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Predistribution";
	}
	@Override
	protected void setEntityname() {
		entityname = "Predistribution";
	}
}
