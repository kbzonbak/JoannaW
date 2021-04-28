package bbr.b2b.logistic.items.interfaces;

import java.io.IOException;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.vev.report.classes.AvailableStockSendDataWSDTO;
import bbr.b2b.logistic.vev.report.classes.VeVUploadErrorDTO;
import bbr.b2b.logistic.items.data.wrappers.ItemW;

public interface IItemServer extends IElementServer<Item, ItemW> {

	ItemW addItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemW[] getItems() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemW updateItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public int doLoadCSV(String file) throws IOException;
	public void doCreateTempTableStock();
	public void doCreateTempTableError();
	public void doCheckDocumentData();
	public int doCleanErrorRowsFromStockUpload();
	public VeVUploadErrorDTO[] getErrorsOfStockUpload();
	public AvailableStockSendDataWSDTO[] getValidStockUpload();
	public ItemW[] getItemByIds(Long[] itemids) throws NotFoundException, OperationFailedException ;

}
