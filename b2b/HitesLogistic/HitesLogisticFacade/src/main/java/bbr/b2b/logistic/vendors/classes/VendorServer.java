package bbr.b2b.logistic.vendors.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/vendors/VendorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorServer extends LogisticElementServer<Vendor, VendorW> implements VendorServerLocal {


	public VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) addElement(vendor);
	}
	public VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW[]) getIdentifiables();
	}
	public VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) updateElement(vendor);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Vendor entity, VendorW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Vendor entity, VendorW wrapper) throws OperationFailedException, NotFoundException {
	}

	public VendorW[] getVendorsByLocationWithDating(Long locationid, LocalDateTime when) throws NotFoundException, OperationFailedException {
		
		VendorW[] result = null;
		List<VendorW> resultList = new ArrayList<VendorW>();
		
		String queryStr = 	"select ve " + //
							"from Vendor as ve, " + //
							"Dating as dt, " + //
							"Reserve as re " + //
							"where " + //
							"ve.id = dt.vendor.id " + 
							"and dt.id = re.id " + //
							"and re.when = :when " + //
							"and re.location.id = :locationid ";
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("re.when", "when", when);
		properties[1] = new PropertyInfoDTO("re.location.id", "locationid", locationid);

		resultList = getByQuery(queryStr, properties);
		result = (VendorW[]) resultList.toArray(new VendorW[resultList.size()]);
		return result;
	}

	
	public int getCountVendors(){
		String sql = "SELECT COUNT(1) FROM logistica.vendor ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	public VendorW getVendorByAsnNumber(String asnnumber) throws NotFoundException, OperationFailedException {
		
		String queryStr = 
				"SELECT ve " + //
				"FROM "+ //
					"Vendor AS ve, " + //
					"DvrDelivery AS de, " + //
					"Bulk AS bu, " + //
					"Document doc " + //
				"WHERE " + //
					"ve.id = de.vendor.id AND de.id = bu.dvrdelivery.id AND bu.document.id = doc.id AND doc.asnnumber = :asnnumber"; //
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("doc.asnnumber", "asnnumber", asnnumber);
		
		List<VendorW> resultList = getByQuery(queryStr, properties);
		VendorW result = resultList != null && resultList.size() > 0 ? (VendorW) resultList.get(0) : null;
		return result;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Vendor";
	}
	@Override
	protected void setEntityname() {
		entityname = "Vendor";
	}
}
