package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;

@Stateless(name = "servers/deliveries/DeliveryStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryStateTypeServer extends LogisticElementServer<DeliveryStateType, DeliveryStateTypeW> implements DeliveryStateTypeServerLocal {


	public DeliveryStateTypeW addDeliveryStateType(DeliveryStateTypeW deliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateTypeW) addElement(deliverystatetype);
	}
	public DeliveryStateTypeW[] getDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateTypeW[]) getIdentifiables();
	}
	public DeliveryStateTypeW updateDeliveryStateType(DeliveryStateTypeW deliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateTypeW) updateElement(deliverystatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DeliveryStateType entity, DeliveryStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DeliveryStateType entity, DeliveryStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DeliveryStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DeliveryStateType";
	}
}
