package bbr.b2b.regional.logistic.items.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.items.entities.ItemClass;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemClassW;

@Stateless(name = "servers/items/ItemClassServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemClassServer extends LogisticElementServer<ItemClass, ItemClassW> implements ItemClassServerLocal {


	public ItemClassW addItemClass(ItemClassW itemclass) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemClassW) addElement(itemclass);
	}
	public ItemClassW[] getItemClasss() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemClassW[]) getIdentifiables();
	}
	public ItemClassW updateItemClass(ItemClassW itemclass) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemClassW) updateElement(itemclass);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ItemClass entity, ItemClassW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(ItemClass entity, ItemClassW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ItemClass";
	}
	@Override
	protected void setEntityname() {
		entityname = "ItemClass";
	}
}
