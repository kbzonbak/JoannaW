package bbr.b2b.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.items.entities.ItemAttribute;
import bbr.b2b.logistic.items.data.wrappers.ItemAttributeW;

public interface IItemAttributeServer extends IElementServer<ItemAttribute, ItemAttributeW> {

	ItemAttributeW addItemAttribute(ItemAttributeW itemattribute) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemAttributeW[] getItemAttributes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemAttributeW updateItemAttribute(ItemAttributeW itemattribute) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
