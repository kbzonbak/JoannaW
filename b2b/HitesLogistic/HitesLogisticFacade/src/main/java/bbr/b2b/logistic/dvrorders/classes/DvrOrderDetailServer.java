package bbr.b2b.logistic.dvrorders.classes;

import java.math.BigInteger;
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
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailExcelReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailExcelReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.entities.Item;

@Stateless(name = "servers/dvrorders/DvrOrderDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderDetailServer extends BaseLogisticEJB3Server<DvrOrderDetail, DvrOrderDetailId, DvrOrderDetailW> implements DvrOrderDetailServerLocal {

	private Map<String, String> mapGetOrderDetailSQL = new HashMap<String, String>();
	{
		mapGetOrderDetailSQL.put("DVRORDERID", "dvrod.dvrorder_id");
		mapGetOrderDetailSQL.put("ITEMID", "dvrod.item_id");
		mapGetOrderDetailSQL.put("POSITION", "dvrod.position");
		mapGetOrderDetailSQL.put("SKU", "it.sku");
		mapGetOrderDetailSQL.put("VENDORITEMCODE", "vi.vendoritemcode");
		mapGetOrderDetailSQL.put("ITEMDDESCRIPTION", "it.name");
		mapGetOrderDetailSQL.put("COLOR", "col.colorname");
		mapGetOrderDetailSQL.put("SIZE", "siz.sizename");
		mapGetOrderDetailSQL.put("SECTION", "se.name");
		mapGetOrderDetailSQL.put("BASECOST", "dvrod.basecost");
		mapGetOrderDetailSQL.put("FINALCOST", "dvrod.finalcost");
		mapGetOrderDetailSQL.put("TOTALUNITS", "dvrod.totalunits");
		mapGetOrderDetailSQL.put("TOTALAMOUNT", "dvrod.totalneed");
	}
	
	@EJB
	DvrOrderServerLocal dvrorderserver;

	@EJB
	ItemServerLocal itemserver;

	public DvrOrderDetailW addDvrOrderDetail(DvrOrderDetailW dvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDetailW) addIdentifiable(dvrorderdetail);
	}
	public DvrOrderDetailW[] getDvrOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDetailW[]) getIdentifiables();
	}
	public DvrOrderDetailW updateDvrOrderDetail(DvrOrderDetailW dvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDetailW) updateIdentifiable(dvrorderdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrderDetail entity, DvrOrderDetailW wrapper) {
		wrapper.setDvrorderid(entity.getDvrorder() != null ? new Long(entity.getDvrorder().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrderDetail entity, DvrOrderDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) { 
			DvrOrder dvrorder = dvrorderserver.getReferenceById(wrapper.getDvrorderid());
			if (dvrorder != null) { 
				entity.setDvrorder(dvrorder);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
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
	
	private String getDvrOrderDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL = "";
		String sqlView1 = "";
		String sqlView2 = "";
		String sqlView3 = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String sqlFrom2 = "";
		String sqlWhere = "";
		String orderByCondition = ""; 
		
		if(queryType == 1){
			sqlSelect = "SELECT CAST(COUNT(dvrod.dvrorder_id) AS INTEGER) AS totalreg, " + //
						"		SUM(dvrod.basecost) AS totalbasecost, " + //
						"		SUM(dvrod.finalcost) AS totalfinalcost, " + //
						"		SUM(dvrod.totalunits) AS totalunits, " + //
						"		SUM(dvrod.totalneed) AS totalamount ";			
		}
		
		else if(queryType == 2){
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
			
			sqlView2 = 	"size AS ( " + //
						"	SELECT 	it.id as itemid, " + // 
						"			ita.value as sizename " + //
						"	FROM logistica.dvrorderdetail as dvrod " + //
						"	JOIN logistica.order as oc " + //
						"	ON oc.id = dvrod.dvrorder_id " + //						
						"	JOIN logistica.item as it " + //
						"	ON it.id = dvrod.item_id " + //
						"	join  logistica.itemattribute ita " + //
						"	ON ita.item_id = it.id " + //
						"	WHERE oc.id = :dvrorderid " + //
						"	AND oc.vendor_id = :vendorid " + //
						"	AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_TALLA + "' " + //
						"), ";

			sqlView3 = 	"section AS ( " + //
						"	SELECT 	it.id as itemid, " + // 
						"			ita.value as sectionname " + //
						"	FROM logistica.dvrorderdetail as dvrod " + //
						"	JOIN logistica.order as oc " + //
						"	ON oc.id = dvrod.dvrorder_id " + //						
						"	JOIN logistica.item as it " + //
						"	ON it.id = dvrod.item_id " + //
						"	join  logistica.itemattribute ita " + //
						"	ON ita.item_id = it.id " + //
						"	WHERE oc.id = :dvrorderid " + //
						"	AND oc.vendor_id = :vendorid " + //
						"	AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_SECCION +  "' " + //
						") ";
			

			sqlSelect = "SELECT " + //
						"	dvrod.dvrorder_id as dvrorderid, " + // 
						"	dvrod.item_id as itemid, " + // 
						"	dvrod.position as position, " + 
						"	it.sku, " + 
						"	vi.vendoritemcode as vendoritemcode, " + 
						"	it.name as itemddescription, " + 
						"	col.colorname as color, " + 
						"	siz.sizename as size, " + 
						"	sec.sectionname as section, " + 
						"	dvrod.basecost, " + 
						"	dvrod.finalcost, " + 
						"	dvrod.totalunits as totalunits, " + 
						"	dvrod.totalneed as totalamount ";
			
			sqlFrom2 =	"JOIN logistica.item AS it " + //
						"ON it.id = dvrod.item_id " + //
						"LEFT JOIN logistica.vendoritem AS vi " + //
						"ON vi.item_id = it.id AND vi.vendor_id = oc.vendor_id " + //
						"LEFT JOIN color AS col " + //
						"ON col.itemid = it.id " + //
						"LEFT JOIN size AS siz " + //
						"ON siz.itemid = it.id " + //
						"LEFT JOIN section AS sec " + //
						"ON sec.itemid = it.id ";

			orderByCondition = getOrderByString(mapGetOrderDetailSQL, orderby);
		}
		
		
		else {
			throw new AccessDeniedException("El tipo de query noes válido");
			
		}
		
		sqlFrom1 = 	"FROM logistica.dvrorderdetail as dvrod " + //
					"JOIN logistica.order as oc " + // 
					"ON oc.id = dvrod.dvrorder_id ";
			
	
		sqlWhere = 	"WHERE oc.id = :dvrorderid " + //
					"AND oc.vendor_id = :vendorid " + 
					"AND oc.ordertype_id != :excludetypeid ";
	
	
		SQL  = 	sqlView1 + //
				sqlView2 + //
				sqlView3 + //
				sqlSelect + //
				sqlFrom1 + //
				sqlFrom2 + //
				sqlWhere + //
				orderByCondition;
		
		return SQL;
	
	}
	
	private String getDvhOrderDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL = "";
		String sqlView1 = "";
		String sqlView2 = "";
		String sqlView3 = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String sqlFrom2 = "";
		String sqlWhere = "";
		String orderByCondition = ""; 
		
		if(queryType == 1){
			sqlSelect = "SELECT CAST(COUNT(dvrod.dvrorder_id) AS INTEGER) AS totalreg, " + //
						"		SUM(dvrod.basecost) AS totalbasecost, " + //
						"		SUM(dvrod.finalcost) AS totalfinalcost, " + //
						"		SUM(dvrod.totalunits) AS totalunits, " + //
						"		SUM(dvrod.totalneed) AS totalamount ";			
		}
		
		else if(queryType == 2){
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
			
			sqlView2 = 	"size AS ( " + //
						"	SELECT 	it.id as itemid, " + // 
						"			ita.value as sizename " + //
						"	FROM logistica.dvrorderdetail as dvrod " + //
						"	JOIN logistica.order as oc " + //
						"	ON oc.id = dvrod.dvrorder_id " + //						
						"	JOIN logistica.item as it " + //
						"	ON it.id = dvrod.item_id " + //
						"	join  logistica.itemattribute ita " + //
						"	ON ita.item_id = it.id " + //
						"	WHERE oc.id = :dvrorderid " + //
						"	AND oc.vendor_id = :vendorid " + //
						"	AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_TALLA + "' " + //
						"), ";

			sqlView3 = 	"section AS ( " + //
						"	SELECT 	it.id as itemid, " + // 
						"			ita.value as sectionname " + //
						"	FROM logistica.dvrorderdetail as dvrod " + //
						"	JOIN logistica.order as oc " + //
						"	ON oc.id = dvrod.dvrorder_id " + //						
						"	JOIN logistica.item as it " + //
						"	ON it.id = dvrod.item_id " + //
						"	join  logistica.itemattribute ita " + //
						"	ON ita.item_id = it.id " + //
						"	WHERE oc.id = :dvrorderid " + //
						"	AND oc.vendor_id = :vendorid " + //
						"	AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_SECCION +  "' " + //
						") ";
			

			sqlSelect = "SELECT " + //
						"	dvrod.dvrorder_id as dvrorderid, " + // 
						"	dvrod.item_id as itemid, " + // 
						"	dvrod.position as position, " + 
						"	it.sku, " + 
						"	vi.vendoritemcode as vendoritemcode, " + 
						"	it.name as itemddescription, " + 
						"	col.colorname as color, " + 
						"	siz.sizename as size, " + 
						"	sec.sectionname as section, " + 
						"	dvrod.basecost, " + 
						"	dvrod.finalcost, " + 
						"	dvrod.totalunits as totalunits, " + 
						"	dvrod.totalneed as totalamount ";
			
			sqlFrom2 =	"JOIN logistica.item AS it " + //
						"ON it.id = dvrod.item_id " + //
						"LEFT JOIN logistica.vendoritem AS vi " + //
						"ON vi.item_id = it.id AND vi.vendor_id = oc.vendor_id " + //
						"LEFT JOIN color AS col " + //
						"ON col.itemid = it.id " + //
						"LEFT JOIN size AS siz " + //
						"ON siz.itemid = it.id " + //
						"LEFT JOIN section AS sec " + //
						"ON sec.itemid = it.id ";

			orderByCondition = getOrderByString(mapGetOrderDetailSQL, orderby);
		}
		
		
		else {
			throw new AccessDeniedException("El tipo de query noes válido");
			
		}
		
		sqlFrom1 = 	"FROM logistica.dvrorderdetail as dvrod " + //
					"JOIN logistica.order as oc " + // 
					"ON oc.id = dvrod.dvrorder_id ";
			
	
		sqlWhere = 	"WHERE oc.id = :dvrorderid " + //
					"AND oc.vendor_id = :vendorid " + 
					"AND oc.ordertype_id = :dvhtypeid ";
	
	
		SQL  = 	sqlView1 + //
				sqlView2 + //
				sqlView3 + //
				sqlSelect + //
				sqlFrom1 + //
				sqlFrom2 + //
				sqlWhere + //
				orderByCondition;
		
		return SQL;
	
	}

		
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void doCalculateTotalDvrOrderDetail(Long[] dvrorderids){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail.doCalculateTotalDvrOrderDetail");
		query.setParameterList("dvrorderids", dvrorderids);
		query.executeUpdate();
	}
	
	public DvrOrderDetailDataDTO[] getDvrOrdersDetailByDvrOrder(Long dvrorderid, Long vendorid, Long excludetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getDvrOrderDetailQueryString(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("excludetypeid", excludetypeid);
		query.setLong("vendorid", vendorid);
		query.setLong("dvrorderid", dvrorderid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrOrderDetailDataDTO[] result = (DvrOrderDetailDataDTO[]) list.toArray(new DvrOrderDetailDataDTO[list.size()]);		
		return result;	
		
	}
	
	public DvrOrderDetailReportTotalDataDTO getCountDvrOrdersDetailsByOrder(Long dvrorderid, Long vendorid, Long excludetypeid) throws AccessDeniedException{
		String SQL = getDvrOrderDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("excludetypeid", excludetypeid);
		query.setLong("dvrorderid", dvrorderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderDetailReportTotalDataDTO.class));
		DvrOrderDetailReportTotalDataDTO result = (DvrOrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}
	
	public DvrOrderDetailExcelReportResultDTO getDvrOrderDetailExcelReportByOrder(Long dvrorderid) throws OperationFailedException {
		DvrOrderDetailExcelReportResultDTO resultDTO = new DvrOrderDetailExcelReportResultDTO();
		
		if (dvrorderid == null) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail.getDvrOrderDetailExcelReportByOrder");
		query.setLong("dvrorderid", dvrorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderDetailExcelReportDataDTO.class));
			
		List<?> list = query.list();
		DvrOrderDetailExcelReportDataDTO[] dvrOrderDetails = list.toArray(new DvrOrderDetailExcelReportDataDTO[list.size()]);
		
		resultDTO.setDvrorderdetails(dvrOrderDetails);
		
		return resultDTO;
	}
	
	public int countDvrOrderDetailExcelReportByOrder(Long dvrorderid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail.countDvrOrderDetailExcelReportByOrder");
		query.setLong("dvrorderid", dvrorderid);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
 
	public DvhOrderDetailExcelReportResultDTO getDvhOrderDetailExcelReportByOrder(Long dvhorderid) throws OperationFailedException {
		DvhOrderDetailExcelReportResultDTO resultDTO = new DvhOrderDetailExcelReportResultDTO();
		
		if (dvhorderid == null) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail.getDvhOrderDetailExcelReportByOrder");
		query.setLong("dvhorderid", dvhorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DvhOrderDetailExcelReportDataDTO.class));
			
		List<?> list = query.list();
		DvhOrderDetailExcelReportDataDTO[] dvhOrderDetails = list.toArray(new DvhOrderDetailExcelReportDataDTO[list.size()]);
		
		resultDTO.setDvhorderdetails(dvhOrderDetails);
		
		return resultDTO;
	}
	
	public int countDvhOrderDetailExcelReportByOrder(Long dvhorderid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail.countDvhOrderDetailExcelReportByOrder");
		query.setLong("dvhorderid", dvhorderid);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	
	public DvhOrderDetailDataDTO[] getDvhOrdersDetailByDvrOrder(Long dvrorderid, Long vendorid, Long dvhtypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getDvhOrderDetailQueryString(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dvhtypeid", dvhtypeid);
		query.setLong("vendorid", vendorid);
		query.setLong("dvrorderid", dvrorderid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvhOrderDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvhOrderDetailDataDTO[] result = (DvhOrderDetailDataDTO[]) list.toArray(new DvhOrderDetailDataDTO[list.size()]);		
		return result;	
		
	}
	
	public DvhOrderDetailReportTotalDataDTO getCountDvhOrdersDetailsByOrder(Long dvrorderid, Long vendorid, Long dvhtypeid) throws AccessDeniedException{
		String SQL = getDvhOrderDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dvhtypeid", dvhtypeid);
		query.setLong("dvrorderid", dvrorderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(DvhOrderDetailReportTotalDataDTO.class));
		DvhOrderDetailReportTotalDataDTO result = (DvhOrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrderDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrderDetail";
	}
}
