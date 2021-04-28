package bbr.b2b.logistic.items.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.items.entities.ItemAttribute;
import bbr.b2b.logistic.items.data.wrappers.ItemAttributeW;

@Stateless(name = "servers/items/ItemAttributeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemAttributeServer extends LogisticElementServer<ItemAttribute, ItemAttributeW> implements ItemAttributeServerLocal {


	@EJB
	ItemServerLocal itemserver;

	public ItemAttributeW addItemAttribute(ItemAttributeW itemattribute) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAttributeW) addElement(itemattribute);
	}
	public ItemAttributeW[] getItemAttributes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAttributeW[]) getIdentifiables();
	}
	public ItemAttributeW updateItemAttribute(ItemAttributeW itemattribute) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAttributeW) updateElement(itemattribute);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ItemAttribute entity, ItemAttributeW wrapper) {
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ItemAttribute entity, ItemAttributeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ItemAttribute";
	}
	@Override
	protected void setEntityname() {
		entityname = "ItemAttribute";
	}
}
