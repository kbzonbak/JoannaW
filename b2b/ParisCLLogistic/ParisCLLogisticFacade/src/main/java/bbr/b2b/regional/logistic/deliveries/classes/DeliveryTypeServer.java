package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryTypeW;

@Stateless(name = "servers/deliveries/DeliveryTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryTypeServer extends LogisticElementServer<DeliveryType, DeliveryTypeW> implements DeliveryTypeServerLocal {


	public DeliveryTypeW addDeliveryType(DeliveryTypeW deliverytype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryTypeW) addElement(deliverytype);
	}
	public DeliveryTypeW[] getDeliveryTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryTypeW[]) getIdentifiables();
	}
	public DeliveryTypeW updateDeliveryType(DeliveryTypeW deliverytype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryTypeW) updateElement(deliverytype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DeliveryType entity, DeliveryTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DeliveryType entity, DeliveryTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DeliveryType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DeliveryType";
	}
}
