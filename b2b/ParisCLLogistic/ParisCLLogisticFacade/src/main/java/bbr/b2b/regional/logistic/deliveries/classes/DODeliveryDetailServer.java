package bbr.b2b.regional.logistic.deliveries.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryItemsMobileDataDTO;

@Stateless(name = "servers/deliveries/DODeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryDetailServer extends BaseLogisticEJB3Server<DODeliveryDetail, DODeliveryDetailId, DODeliveryDetailW> implements DODeliveryDetailServerLocal {

	private Map<String, String> mapGetDeliveryDetailSQL = new HashMap<String, String>();
	{
		mapGetDeliveryDetailSQL.put("DELIVERYID", "DODL.ID");
		mapGetDeliveryDetailSQL.put("ITEMID", "IT.ID");
		mapGetDeliveryDetailSQL.put("ITEMSKU", "IT.INTERNALCODE");
		mapGetDeliveryDetailSQL.put("VENDORITEMCODE", "VI.VENDORITEMCODE");
		mapGetDeliveryDetailSQL.put("ITEMDESC", "IT.NAME");
		mapGetDeliveryDetailSQL.put("PENDINGUNITS", "DODD.PENDINGUNITS");
		mapGetDeliveryDetailSQL.put("AVAILABLEUNITS", "DODD.AVAILABLEUNITS");
		mapGetDeliveryDetailSQL.put("RECEIVEDUNITS", "DODD.RECEIVEDUNITS");
	}		

	@EJB
	DODeliveryServerLocal dodeliveryserver;

	@EJB
	ItemServerLocal itemserver;

	public DODeliveryDetailW addDODeliveryDetail(DODeliveryDetailW dodeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryDetailW) addIdentifiable(dodeliverydetail);
	}
	public DODeliveryDetailW[] getDODeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryDetailW[]) getIdentifiables();
	}
	public DODeliveryDetailW updateDODeliveryDetail(DODeliveryDetailW dodeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryDetailW) updateIdentifiable(dodeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DODeliveryDetail entity, DODeliveryDetailW wrapper) {
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DODeliveryDetail entity, DODeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) { 
			DODelivery dodelivery = dodeliveryserver.getReferenceById(wrapper.getDodeliveryid());
			if (dodelivery != null) { 
				entity.setDodelivery(dodelivery);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}

	private String getDeliveryDetailReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
				
		if (queryType == 1){
			SQL = 
				"SELECT CAST(COUNT(DODL.ID) AS integer) AS TOTALREG, " +
					"SUM(DODD.PENDINGUNITS) AS PENDINGUNITS, "+
					"SUM(DODD.AVAILABLEUNITS) AS AVAILABLEUNITS, "+
					"SUM(DODD.RECEIVEDUNITS) AS RECEIVEDUNITS ";					
		}
		else {
			SQL = 	
			"SELECT " +  
				"DODL.ID AS DELIVERYID, " +  
				"IT.ID AS ITEMID, " +
				"IT.INTERNALCODE AS ITEMSKU, " +  
				"VI.VENDORITEMCODE, " +  
				"IT.NAME AS ITEMDESC, " +			
				"DODD.PENDINGUNITS, " +  
				"DODD.AVAILABLEUNITS, " +  
				"DODD.RECEIVEDUNITS ";	
						
				condicionOrderBy = getOrderByString(mapGetDeliveryDetailSQL, orderby);				
		}
		SQL = SQL + 
			"FROM " +   
				"LOGISTICA.DODELIVERY AS DODL JOIN " +  
				"LOGISTICA.DODELIVERYDETAIL AS DODD ON DODL.ID = DODD.DODELIVERY_ID JOIN " +
				"LOGISTICA.ITEM AS IT ON IT.ID = DODD.ITEM_ID JOIN " +
				"LOGISTICA.VENDOR AS VD ON VD.ID = DODL.VENDOR_ID JOIN " +
				"LOGISTICA.VENDORITEM AS VI ON VI.VENDOR_ID = VD.ID AND VI.ITEM_ID = IT.ID " + 					 
			"WHERE " +  
				"DODL.ID = :deliveryid AND " +
				"VD.ID = :vendorid ";		
		SQL = 
			SQL +		  
			condicionOrderBy;		
		
		return SQL;		
	}
	
	public DODeliveryDetailReportDataDTO[] getDODeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException {
		String SQL = getDeliveryDetailReportQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);	
		query.setLong("deliveryid", deliveryid);	
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		DODeliveryDetailReportDataDTO[] result = (DODeliveryDetailReportDataDTO[]) list.toArray(new DODeliveryDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public DODeliveryDetailReportTotalDataDTO getDODeliveryDetailCountReport(Long vendorid, Long deliveryid) throws ServiceException{
		String SQL = getDeliveryDetailReportQueryString(1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);		
		query.setLong("deliveryid", deliveryid);	
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryDetailReportTotalDataDTO.class));
		DODeliveryDetailReportTotalDataDTO result = (DODeliveryDetailReportTotalDataDTO) query.uniqueResult();				
		return result;
	}	
	
	public DODeliveryItemsMobileDataDTO[] getDODeliveryItemsReport(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODeliveryDetail.getDODeliveryItems");
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryItemsMobileDataDTO.class));
		List list = query.list();
		DODeliveryItemsMobileDataDTO[] result = (DODeliveryItemsMobileDataDTO[]) list.toArray(new DODeliveryItemsMobileDataDTO[list.size()]);
		return result;
	}
	
	
	private String getOrderByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DODeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DODeliveryDetail";
	}
}
