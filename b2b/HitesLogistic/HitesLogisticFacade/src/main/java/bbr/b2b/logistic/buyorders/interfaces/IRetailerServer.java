package bbr.b2b.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.buyorders.data.wrappers.RetailerW;

public interface IRetailerServer extends IElementServer<Retailer, RetailerW> {

	RetailerW addRetailer(RetailerW retailer) throws AccessDeniedException, OperationFailedException, NotFoundException;
	RetailerW[] getRetailers() throws AccessDeniedException, OperationFailedException, NotFoundException;
	RetailerW updateRetailer(RetailerW retailer) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
