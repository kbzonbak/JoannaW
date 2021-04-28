package bbr.b2b.regional.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.items.entities.ItemClass;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemClassW;

public interface IItemClassServer extends IElementServer<ItemClass, ItemClassW> {

	ItemClassW addItemClass(ItemClassW itemclass) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemClassW[] getItemClasss() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemClassW updateItemClass(ItemClassW itemclass) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
