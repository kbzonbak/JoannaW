package bbr.b2b.logistic.datings.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.datings.entities.Dating;
import bbr.b2b.logistic.datings.report.classes.DatingDataToMessageDTO;
import bbr.b2b.logistic.datings.report.classes.DatingInformationDTO;
import bbr.b2b.logistic.datings.report.classes.DatingTimeDockDataDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/datings/DatingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingServer extends LogisticElementServer<Dating, DatingW> implements DatingServerLocal {


	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	LocationServerLocal locationserver;
	
	public DatingW addDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW) addElement(dating);
	}
	public DatingW[] getDatings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW[]) getIdentifiables();
	}
	public DatingW updateDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW) updateElement(dating);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Dating entity, DatingW wrapper) {
		wrapper.setDvrdeliveryid(entity.getDvrdelivery() != null ? new Long(entity.getDvrdelivery().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Dating entity, DatingW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) { 
			DvrDelivery dvrdelivery = dvrdeliveryserver.getReferenceById(wrapper.getDvrdeliveryid());
			if (dvrdelivery != null) { 
				entity.setDvrdelivery(dvrdelivery);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if(wrapper.getLocationid() != null && wrapper.getLocationid() > 0){
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if(location != null){
				entity.setLocation(location);
			}
		}
	}

	public DatingInformationDTO getDatingInformation(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.Dating.getDatingInformation");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DatingInformationDTO.class));
		DatingInformationDTO result = (DatingInformationDTO) query.uniqueResult();
		return result;	
	}
	
	public DatingTimeDockDataDTO[] getDatingTimeDockData(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.Dating.getDatingTimeDockData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DatingTimeDockDataDTO.class));
		List<?> list = query.list();
		DatingTimeDockDataDTO[] result = (DatingTimeDockDataDTO[]) list.toArray(new DatingTimeDockDataDTO[list.size()]);
		return result;
	}
	
	public DatingW[] getDatingByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException {
		
		DatingW[] result = null;
		List<DatingW> resultList = new ArrayList<DatingW>();

		String queryStr = 	"select dt " + //
							"from Dating dt, " + 
							"Reserve as re " + //
							"where " + //
							"re.id = dt.id " + //
							"and re.when >= :since " + //
							"and re.when <= :until " + //
							"and re.location.id = :locationid ";
		
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("re.when", "since", since);
		properties[1] = new PropertyInfoDTO("re.when", "until", until);
		properties[2] = new PropertyInfoDTO("re.location.id", "locationid", locationid);
		
				
		resultList = getByQuery(queryStr, properties);
		result = (DatingW[]) resultList.toArray(new DatingW[resultList.size()]);
		return result;
		
	}
	
	public DatingW[] getDatingByDvrDeliveryIds(Long[] dvrdeliveryids) throws NotFoundException, OperationFailedException {

		DatingW[] result = null;
		List<DatingW> resultList = new ArrayList<DatingW>();
		
		List<Long> dvrdeliveryidsList = Arrays.asList(dvrdeliveryids);
		String queryStr = 	"select dt " + //
							"from Dating as dt " + // 
							"where dt.dvrdelivery.id in (:dvrdeliveryids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("dt.delivery.id", "dvrdeliveryids", dvrdeliveryidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DatingW[]) resultList.toArray(new DatingW[resultList.size()]);
		return result;
		
	}
	

	public DatingDataToMessageDTO[] getDatingDataToMessage(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.Dating.getDatingDataToMessage");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DatingDataToMessageDTO.class));
		List<?> list = query.list();
		DatingDataToMessageDTO[] result = (DatingDataToMessageDTO[]) list.toArray(new DatingDataToMessageDTO[list.size()]);
		return result;
		
	} 
	
	
	public ExcelDatingReportDataDTO[] getExcelDatingReport(Long locationid, LocalDateTime since, LocalDateTime until) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.Dating.getExcelDatingReport");
		query.setLong("locationid", locationid);
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(ExcelDatingReportDataDTO.class));
		List<?> list = query.list();
		ExcelDatingReportDataDTO[] result = (ExcelDatingReportDataDTO[]) list.toArray(new ExcelDatingReportDataDTO[list.size()]);
		return result;
	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Dating";
	}
	@Override
	protected void setEntityname() {
		entityname = "Dating";
	}
}
