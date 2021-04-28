package bbr.b2b.logistic.ddcdeliveries.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryStateType;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/ddcdeliveries/DdcDeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcDeliveryServer extends LogisticElementServer<DdcDelivery, DdcDeliveryW> implements DdcDeliveryServerLocal {

	@EJB
	DdcOrderServerLocal ddcorderserver;
	
	@EJB
	DdcDeliveryStateTypeServerLocal currentstatetypeserver;

	@EJB
	VendorServerLocal vendorserver;

	public DdcDeliveryW addDdcDelivery(DdcDeliveryW ddcdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryW) addElement(ddcdelivery);
	}
	public DdcDeliveryW[] getDdcDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryW[]) getIdentifiables();
	}
	public DdcDeliveryW updateDdcDelivery(DdcDeliveryW ddcdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryW) updateElement(ddcdelivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcDelivery entity, DdcDeliveryW wrapper) {
		wrapper.setDdcorderid(entity.getDdcorder() != null ? new Long(entity.getDdcorder().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcDelivery entity, DdcDeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) { 
			DdcOrder ddcorder = ddcorderserver.getReferenceById(wrapper.getDdcorderid());
			if (ddcorder != null) { 
				entity.setDdcorder(ddcorder);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DdcDeliveryStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}

	public DdcDeliveryW[] getDdcDeliveryByOrderids(Long[] ddcorderids) throws NotFoundException, OperationFailedException {
		DdcDeliveryW[] result = null;
		List<DdcDeliveryW> resultList = new ArrayList<DdcDeliveryW>();
		
		List<Long> ddcorderidsList = Arrays.asList(ddcorderids);
		String queryStr = 	"select ddcd from DdcDelivery as ddcd " + // 
							"where " + //
							"ddcd.ddcorder.id in (:ddcorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("ddcd.ddcorder.id ", "ddcorderids", ddcorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result = (DdcDeliveryW[]) resultList.toArray(new DdcDeliveryW[resultList.size()]);
		return result;
	}	
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcDelivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcDelivery";
	}
	
	public Long getNextSequenceDeliveryNumber() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("SELECT NEXTVAL('logistica.ddcdeliverynumber_seq')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
}
