package bbr.b2b.logistic.dvrorders.classes;

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
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrPreDeliveryDetailW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailExcelReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportTotalDataDTO;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;

@Stateless(name = "servers/dvrorders/DvrPreDeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrPreDeliveryDetailServer extends BaseLogisticEJB3Server<DvrPreDeliveryDetail, DvrPreDeliveryDetailId, DvrPreDeliveryDetailW> implements DvrPreDeliveryDetailServerLocal {
	
	private Map<String, String> mapGetPredeliveryDetailSQL = new HashMap<String, String>();
	{
		mapGetPredeliveryDetailSQL.put("SKU", "it.sku");
		mapGetPredeliveryDetailSQL.put("DEPARTMENT", "dep.deptoname");
		mapGetPredeliveryDetailSQL.put("COLOR", "col.colorname");
		mapGetPredeliveryDetailSQL.put("ITEMDESCRIPTION", "it.name");
		mapGetPredeliveryDetailSQL.put("DESTLOCATIONCODE", "loc.code");
		mapGetPredeliveryDetailSQL.put("DESTLOCATIONDESCRIPTION", "loc.name");
		mapGetPredeliveryDetailSQL.put("TOTALUNITS", "dvrpre.totalunits");
		mapGetPredeliveryDetailSQL.put("TODELIVERYUNITS", "dvrpre.todeliveryunits");
		mapGetPredeliveryDetailSQL.put("RECEIVEDUNITS", "dvrpre.receivedunits");
		mapGetPredeliveryDetailSQL.put("PENDINGUNITS", "dvrpre.pendingunits");
	}
	
	
	@EJB
	DvrOrderDetailServerLocal dvrorderdetailserver;

	@EJB
	LocationServerLocal locationserver;

	public DvrPreDeliveryDetailW addDvrPreDeliveryDetail(DvrPreDeliveryDetailW dvrpredeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrPreDeliveryDetailW) addIdentifiable(dvrpredeliverydetail);
	}
	public DvrPreDeliveryDetailW[] getDvrPreDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrPreDeliveryDetailW[]) getIdentifiables();
	}
	public DvrPreDeliveryDetailW updateDvrPreDeliveryDetail(DvrPreDeliveryDetailW dvrpredeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrPreDeliveryDetailW) updateIdentifiable(dvrpredeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrPreDeliveryDetail entity, DvrPreDeliveryDetailW wrapper) {
		wrapper.setDvrorderid(entity.getDvrorderdetail().getId() != null ? new Long(entity.getDvrorderdetail().getId().getDvrorderid()) : new Long(0));
		wrapper.setItemid(entity.getDvrorderdetail().getId() != null ? new Long(entity.getDvrorderdetail().getId().getItemid()) : new Long(0));
		wrapper.setPosition(entity.getDvrorderdetail().getId() != null ? new Integer(entity.getDvrorderdetail().getId().getPosition()) : new Integer(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrPreDeliveryDetail entity, DvrPreDeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getPosition() != null && wrapper.getPosition() > 0)) {
			DvrOrderDetailId key = new DvrOrderDetailId();
			key.setDvrorderid(wrapper.getDvrorderid());
			key.setItemid(wrapper.getItemid());
			key.setPosition(wrapper.getPosition());
			DvrOrderDetail var = dvrorderdetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDvrorderdetail(var);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
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
	
	private String getDvrPredeliveryDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL = "";
		String sqlView1 = "";
		String sqlView2 = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String sqlFrom2 = "";
		String sqlWhere = "";
		String orderByCondition = ""; 

		if(queryType == 1){
			
			sqlSelect = "SELECT CAST(COUNT(dvrpre.dvrorder_id) AS INTEGER) AS totalreg, " +
					"	SUM(dvrpre.totalunits) AS totalunits, " +
					"	SUM(dvrpre.pendingunits) AS pendingunits, " +
					"	SUM(dvrpre.receivedunits) AS receivedunits, " +
					"	SUM(dvrpre.todeliveryunits) AS todeliveryunits ";
		}
		
		else if(queryType == 2) {
			sqlView1 = 	"WITH color AS ( " + // 
				    	"	SELECT 	it.id as itemid, " + // 
				    	"			ita.value as colorname " + // 
				    	"	FROM logistica.dvrorderdetail as dvrod " + // 
				    	"	JOIN logistica.order as oc " + // 
				    	"	ON oc.id = dvrod.dvrorder_id " + // 						
				    	"	JOIN logistica.item as it " + // 
				    	"	ON it.id = dvrod.item_id " + // 
				    	"	JOIN logistica.itemattribute ita " + // 
				    	"	ON ita.item_id = it.id " + // 
				    	"	WHERE oc.id = :dvrorderid " + // 
				    	"	AND oc.vendor_id = :vendorid " + //
				    	"	AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_COLOR + "' " + // 
				    	"), ";
			
			sqlView2 = 	"depto AS ( " + //
						"	SELECT 	it.id as itemid, " + // 
						"			ita.value as deptoname " + //  
						"	FROM logistica.dvrorderdetail as dvrod " + 
						"   JOIN logistica.order as oc " + 
						"   ON oc.id = dvrod.dvrorder_id " + 
						"   JOIN logistica.item as it " + 
						"   ON it.id = dvrod.item_id " + 
						"   JOIN  logistica.itemattribute ita " + 
						"   ON ita.item_id = it.id " + 
						"	WHERE oc.id = :dvrorderid " + // 
				    	"	AND oc.vendor_id = :vendorid " + //
						"   AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_DEPARTAMENTO + "' " + // 
						") ";
			
			sqlSelect =	"SELECT " + // 
						"	it.sku as sku, " + //
						"	dep.deptoname as department, " + // 
						"	col.colorname as color, " + // 
						"	it.name as itemdescription, " +
						"	loc.code as destlocationcode, " + //
						"	loc.name AS destlocationname, " + //
						"	dvrpre.totalunits, " + //
						"	dvrpre.todeliveryunits, " + // 
						"	dvrpre.receivedunits, " + //
						"	dvrpre.pendingunits "; 
			
			sqlFrom2 = 	"JOIN logistica.location as loc " + // 
						"ON loc.id = dvrpre.location_id " + //
						"LEFT JOIN color AS col " + 
						"ON col.itemid = it.id " + 
						"LEFT JOIN depto AS dep " + 
						"ON dep.itemid = it.id ";
		
			orderByCondition = getOrderByString(mapGetPredeliveryDetailSQL, orderby);
		}
		
		else{
			throw new AccessDeniedException("Tipo de query no válido");
		}
		
		
		
		sqlFrom1 = 	"FROM " + //
					"logistica.dvrpredeliverydetail as dvrpre " + // 
					"JOIN logistica.item as it " + //
					"ON it.id = dvrpre.item_id " + //
					"JOIN logistica.order as oc " + //
					"ON oc.id = dvrpre.dvrorder_id " ;
		
		sqlWhere = 	"WHERE oc.id = :dvrorderid " + // 
	    			"AND oc.vendor_id = :vendorid ";
		
		SQL  = 	sqlView1 + //
				sqlView2 + //
				sqlSelect + //
				sqlFrom1 + //
				sqlFrom2 + //
				sqlWhere + //
				orderByCondition;
		
		return SQL;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void doCalculateTotalDvrPredelivery(Long[] dvrorderids){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail.doCalculateTotalDvrPredelivery");
		query.setParameterList("dvrorderids", dvrorderids);
		query.executeUpdate();
	}
	
	
	public DvrPredeliveryDetailDataDTO[] getDvrPredeliveryDetailByOrder(Long dvrorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getDvrPredeliveryDetailQueryString(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("dvrorderid", dvrorderid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrPredeliveryDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrPredeliveryDetailDataDTO[] result = (DvrPredeliveryDetailDataDTO[]) list.toArray(new DvrPredeliveryDetailDataDTO[list.size()]);		
		return result;	
		
	}
	
	public DvrPredeliveryDetailReportTotalDataDTO getCountDvrPredeliveryDetailsByOrder(Long dvrorderid, Long vendorid) throws AccessDeniedException{
		String SQL = getDvrPredeliveryDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dvrorderid", dvrorderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrPredeliveryDetailReportTotalDataDTO.class));
		DvrPredeliveryDetailReportTotalDataDTO result = (DvrPredeliveryDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}
	
	public DvrPredeliveryDetailExcelReportResultDTO getDvrPredeliveryDetailExcelReportByOrder(Long dvrorderid) throws OperationFailedException {
		DvrPredeliveryDetailExcelReportResultDTO resultDTO = new DvrPredeliveryDetailExcelReportResultDTO();
		
		if (dvrorderid == null) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail.getDvrPredeliveryDetailExcelReportByOrder");
		query.setLong("dvrorderid", dvrorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DvrPredeliveryDetailExcelReportDataDTO.class));
			
		List<?> list = query.list();
		DvrPredeliveryDetailExcelReportDataDTO[] dvrPredeliveryDetails = list.toArray(new DvrPredeliveryDetailExcelReportDataDTO[list.size()]);
		
		resultDTO.setDvrpredeliverydetails(dvrPredeliveryDetails);
		
		return resultDTO;
	}	 
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrPreDeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrPreDeliveryDetail";
	}
}
