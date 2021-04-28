package bbr.b2b.logistic.datings.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.data.wrappers.PreDatingResourceGroupW;
import bbr.b2b.logistic.datings.entities.PreDatingResourceGroup;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/datings/PreDatingResourceGroupServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PreDatingResourceGroupServer extends LogisticElementServer<PreDatingResourceGroup, PreDatingResourceGroupW> implements PreDatingResourceGroupServerLocal {

	@EJB
	LocationServerLocal locationserver;

	@EJB
	VendorServerLocal vendorserver;

	public PreDatingResourceGroupW addPreDatingResourceGroup(PreDatingResourceGroupW predatingresourcegroup) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDatingResourceGroupW) addElement(predatingresourcegroup);
	}
	public PreDatingResourceGroupW[] getPreDatingResourceGroups() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDatingResourceGroupW[]) getIdentifiables();
	}
	public PreDatingResourceGroupW updatePreDatingResourceGroup(PreDatingResourceGroupW predatingresourcegroup) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDatingResourceGroupW) updateElement(predatingresourcegroup);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PreDatingResourceGroup entity, PreDatingResourceGroupW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(PreDatingResourceGroup entity, PreDatingResourceGroupW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0){
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if(location != null) {
				entity.setLocation(location);
			}
		}
	}


	public PreDatingResourceGroupW[] getPreDatingResourceGroupsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException {
		
		PreDatingResourceGroupW[] result = null;
		List<PreDatingResourceGroupW> resultList = new ArrayList<PreDatingResourceGroupW>();

		String queryStr = 	"select pdr " + //
							"from PreDatingResourceGroup as pdr, " + //
							"ResourceBlocking as rb, " + //
							"Reserve as re " + //
							"where " + //
							"rb.blockinggroup.id = pdr.id " + //
							"and re.id = rb.id " + //
							"and re.when >= :since " + //
							"and re.when <= :until " + //
							"and re.location.id = :locationid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("re.when", "since", since);
		properties[1] = new PropertyInfoDTO("re.when", "until", until);
		properties[2] = new PropertyInfoDTO("re.location.id", "locationid", locationid);
		
				
		resultList = getByQuery(queryStr, properties);
		result = (PreDatingResourceGroupW[]) resultList.toArray(new PreDatingResourceGroupW[resultList.size()]);
		return result;
	}
	
	
	public int getCountPreDatingResourceGroupByLocationAndDate(Long vendorid, Long locationid, LocalDateTime reservedate) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.PreDatingResourceGroup.getCountPreDatingResourceGroupByLocationAndDate");
		query.setLong("vendorid", vendorid);
		query.setLong("locationid", locationid);
		query.setParameter("reservedate", reservedate);		
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "PreDatingResourceGroup";
	}
	@Override
	protected void setEntityname() {
		entityname = "PreDatingResourceGroup";
	}
}
