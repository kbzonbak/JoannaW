package bbr.b2b.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.buyorders.data.wrappers.RetailerW;

@Stateless(name = "servers/buyorders/RetailerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RetailerServer extends LogisticElementServer<Retailer, RetailerW> implements RetailerServerLocal {


	public RetailerW addRetailer(RetailerW retailer) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RetailerW) addElement(retailer);
	}
	public RetailerW[] getRetailers() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RetailerW[]) getIdentifiables();
	}
	public RetailerW updateRetailer(RetailerW retailer) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RetailerW) updateElement(retailer);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Retailer entity, RetailerW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Retailer entity, RetailerW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Retailer";
	}
	@Override
	protected void setEntityname() {
		entityname = "Retailer";
	}
}
