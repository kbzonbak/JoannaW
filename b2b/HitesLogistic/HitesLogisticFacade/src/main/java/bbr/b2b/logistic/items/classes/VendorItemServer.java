package bbr.b2b.logistic.items.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.items.entities.VendorItem;
import bbr.b2b.logistic.items.entities.VendorItemId;
import bbr.b2b.logistic.report.classes.VendorItemDataDTO;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/items/VendorItemServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorItemServer extends BaseLogisticEJB3Server<VendorItem, VendorItemId, VendorItemW> implements VendorItemServerLocal {


	@EJB
	ItemServerLocal itemserver;

	@EJB
	VendorServerLocal vendorserver;

	public VendorItemW addVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorItemW) addIdentifiable(vendoritem);
	}
	public VendorItemW[] getVendorItems() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorItemW[]) getIdentifiables();
	}
	public VendorItemW updateVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorItemW) updateIdentifiable(vendoritem);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorItem entity, VendorItemW wrapper) {
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorItem entity, VendorItemW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}
	
	public VendorItemDataDTO[] getVendorItemDatasofOrder(Long orderid, Long vendorid) throws OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.VendorItem.getVendorItemDatasofOrder");
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataDTO.class));
		List list = query.list();
		VendorItemDataDTO[] result = (VendorItemDataDTO[]) list.toArray(new VendorItemDataDTO[list.size()]);
		return result;
	}
	
	public VendorItemDataDTO[] getVendorItemDataofDirectOrder(Long orderid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.VendorItem.getVendorItemDataofDirectOrder");
		query.setLong("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataDTO.class));
		List list = query.list();
		VendorItemDataDTO[] result = (VendorItemDataDTO[]) list.toArray(new VendorItemDataDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorItem";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorItem";
	}
}
