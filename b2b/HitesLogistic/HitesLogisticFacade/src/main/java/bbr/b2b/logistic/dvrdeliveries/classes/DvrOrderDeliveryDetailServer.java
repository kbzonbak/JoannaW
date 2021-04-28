package bbr.b2b.logistic.dvrdeliveries.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryDetailW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetailId;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryId;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailExcelDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetailId;

@Stateless(name = "servers/dvrdeliveries/DvrOrderDeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderDeliveryDetailServer extends BaseLogisticEJB3Server<DvrOrderDeliveryDetail, DvrOrderDeliveryDetailId, DvrOrderDeliveryDetailW> implements DvrOrderDeliveryDetailServerLocal {


	@EJB
	DvrPreDeliveryDetailServerLocal dvrpredeliverydetailserver;

	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver;

	public DvrOrderDeliveryDetailW addDvrOrderDeliveryDetail(DvrOrderDeliveryDetailW dvrorderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryDetailW) addIdentifiable(dvrorderdeliverydetail);
	}
	public DvrOrderDeliveryDetailW[] getDvrOrderDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryDetailW[]) getIdentifiables();
	}
	public DvrOrderDeliveryDetailW updateDvrOrderDeliveryDetail(DvrOrderDeliveryDetailW dvrorderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryDetailW) updateIdentifiable(dvrorderdeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrderDeliveryDetail entity, DvrOrderDeliveryDetailW wrapper) {
		wrapper.setDvrorderid(entity.getDvrpredeliverydetail().getId() != null ? new Long(entity.getDvrpredeliverydetail().getId().getDvrorderid()) : new Long(0));
		wrapper.setItemid(entity.getDvrpredeliverydetail().getId() != null ? new Long(entity.getDvrpredeliverydetail().getId().getItemid()) : new Long(0));
		wrapper.setLocationid(entity.getDvrpredeliverydetail().getId() != null ? new Long(entity.getDvrpredeliverydetail().getId().getLocationid()) : new Long(0));
		wrapper.setPosition(entity.getDvrpredeliverydetail().getId() != null ? new Integer(entity.getDvrpredeliverydetail().getId().getPosition()) : new Integer(0));
		wrapper.setDvrdeliveryid(entity.getDvrorderdelivery().getId() != null ? new Long(entity.getDvrorderdelivery().getId().getDvrdeliveryid()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrderDeliveryDetail entity, DvrOrderDeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) && (wrapper.getPosition() != null && wrapper.getPosition() > 0)) {
			DvrPreDeliveryDetailId key = new DvrPreDeliveryDetailId();
			key.setDvrorderid(wrapper.getDvrorderid());
			key.setItemid(wrapper.getItemid());
			key.setLocationid(wrapper.getLocationid());
			key.setPosition(wrapper.getPosition());
			DvrPreDeliveryDetail var = dvrpredeliverydetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDvrpredeliverydetail(var);
			}
		}
		if ((wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) && (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0)) {
			DvrOrderDeliveryId key = new DvrOrderDeliveryId();
			key.setDvrorderid(wrapper.getDvrorderid());
			key.setDvrdeliveryid(wrapper.getDvrdeliveryid());
			DvrOrderDelivery var = dvrorderdeliveryserver.getReferenceById(key);
			if (var != null) { 
				entity.setDvrorderdelivery(var);
			}
		}
	}


	public AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerDockByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail.getAssignedDatingTotalizerDockByDateAndLocation");
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setLong("locationid", locationid);		
		query.setResultTransformer(new LowerCaseResultTransformer(AssignedDatingTotalizerByDockDTO.class));
		List<?> list = query.list();
		AssignedDatingTotalizerByDockDTO[] result = (AssignedDatingTotalizerByDockDTO[]) list.toArray(new AssignedDatingTotalizerByDockDTO[list.size()]);	
		return result;
	}
	
	public DvrDeliveryDetailExcelReportResultDTO getDvrDeliveryDetailExcelReportByDelivery(Long dvrdeliveryid) {
		DvrDeliveryDetailExcelReportResultDTO resultDTO = new DvrDeliveryDetailExcelReportResultDTO();
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail.getDvrDeliveryDetailExcelReportByDelivery");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryDetailExcelDataDTO.class));
			
		List<?> list = query.list();
		DvrDeliveryDetailExcelDataDTO[] dvrorderdetails = (DvrDeliveryDetailExcelDataDTO[]) list.toArray(new DvrDeliveryDetailExcelDataDTO[list.size()]);

		resultDTO.setDvrorderdetails(dvrorderdetails);
		
		return resultDTO;
	}
	
	public int countDvrDeliveryDetailExcelReportByDelivery(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail.countDvrDeliveryDetailExcelReportByDelivery");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	public int doUpdateDvrOrderDeliveryDetails(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail.doUpdateDvrOrderDeliveryDetails");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public void deleteDvrOrderDeliveryDetailByDvrDeliveryById(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail.deleteDvrOrderDeliveryDetailByDvrDeliveryById");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.executeUpdate();
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrderDeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrderDeliveryDetail";
	}
}
