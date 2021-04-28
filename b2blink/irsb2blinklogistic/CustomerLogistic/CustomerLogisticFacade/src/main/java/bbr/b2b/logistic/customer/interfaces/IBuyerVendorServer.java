package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.entities.BuyerVendor;
import bbr.b2b.logistic.customer.entities.BuyerVendorId;

public interface IBuyerVendorServer extends IGenericServer<BuyerVendor, BuyerVendorId, BuyerVendorW> {
  BuyerVendorW addBuyerVendor(BuyerVendorW paramBuyerVendorW) throws AccessDeniedException, OperationFailedException, NotFoundException;
  
  BuyerVendorW[] getBuyerVendor() throws AccessDeniedException, OperationFailedException, NotFoundException;
  
  BuyerVendorW updateBuyerVendor(BuyerVendorW paramBuyerVendorW) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
