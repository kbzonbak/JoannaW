package bbr.b2b.logistic.ddcorders.classes;

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
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetailId;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailExcelReportDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.entities.Item;

@Stateless(name = "servers/ddcorders/DdcOrderDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderDetailServer extends BaseLogisticEJB3Server<DdcOrderDetail, DdcOrderDetailId, DdcOrderDetailW> implements DdcOrderDetailServerLocal {

	private Map<String, String> mapGetOrderDetailSQL = new HashMap<String, String>();
	{
		mapGetOrderDetailSQL.put("POSITION", "ddcod.position");
		mapGetOrderDetailSQL.put("ITEMSKU", "it.sku");
		mapGetOrderDetailSQL.put("ITEMNAME", "it.name");
		mapGetOrderDetailSQL.put("VENDORITEMCODE", "vendoritemcode");
		mapGetOrderDetailSQL.put("ITEMSIZE", "itemsize");
		mapGetOrderDetailSQL.put("ITEMCOLOR", "itemcolor");
		mapGetOrderDetailSQL.put("ITEMSECTION", "itemsection");
		mapGetOrderDetailSQL.put("BASECOST", "ddcod.basecost");
		mapGetOrderDetailSQL.put("FINALCOST", "ddcod.finalcost");
		mapGetOrderDetailSQL.put("NEEDUNITS", "ddcod.needunits");
		mapGetOrderDetailSQL.put("TOTALNEED", "ddcod.totalneed");
	}
	
	@EJB
	DdcOrderServerLocal ddcorderserver;

	@EJB
	ItemServerLocal itemserver;

	public DdcOrderDetailW addDdcOrderDetail(DdcOrderDetailW ddcorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderDetailW) addIdentifiable(ddcorderdetail);
	}
	public DdcOrderDetailW[] getDdcOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderDetailW[]) getIdentifiables();
	}
	public DdcOrderDetailW updateDdcOrderDetail(DdcOrderDetailW ddcorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderDetailW) updateIdentifiable(ddcorderdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrderDetail entity, DdcOrderDetailW wrapper) {
		wrapper.setDdcorderid(entity.getDdcorder() != null ? new Long(entity.getDdcorder().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrderDetail entity, DdcOrderDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) { 
			DdcOrder ddcorder = ddcorderserver.getReferenceById(wrapper.getDdcorderid());
			if (ddcorder != null) { 
				entity.setDdcorder(ddcorder);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrderDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrderDetail";
	}
	
	public void doCalculateTotalDdcOrderDetails(Long[] ddcorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail.doCalculateTotalDdcOrderDetails");
		query.setParameterList("ddcorderids", ddcorderids);
		query.executeUpdate();
	}
	
	public DdcOrderDetailDataDTO[] getDdcOrderDetailByDdcOrder(Long ddcorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getDdcOrderDetailsQueryString(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ddcorderid", ddcorderid);
		query.setLong("vendorid", vendorid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderDetailDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List<?> list = query.list();
		DdcOrderDetailDataDTO[] result = (DdcOrderDetailDataDTO[]) list.toArray(new DdcOrderDetailDataDTO[list.size()]);		
		
		return result;
	}
	
	public DdcOrderDetailReportTotalDataDTO countDdcOrderDetailByDdcOrder(Long ddcorderid, Long vendorid) throws AccessDeniedException {
		String SQL = getDdcOrderDetailsQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ddcorderid", ddcorderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderDetailReportTotalDataDTO.class));
		
		DdcOrderDetailReportTotalDataDTO result = (DdcOrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}
	
	private String getDdcOrderDetailsQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL = "";
		String sqlView1 = "";
		String sqlView2 = "";
		String sqlView3 = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String sqlFrom2 = "";
		String sqlWhere = "";
		String orderByCondition = ""; 
		
		if (queryType == 1) {
			sqlSelect =
				"SELECT " + //
					"CAST(COUNT(ddcod.ddcorder_id) AS INTEGER) AS totalreg, " + //
					"SUM(ddcod.needunits) AS needunits, " + //
					"SUM(ddcod.totalneed) AS totalneed ";
		}
		
		else if(queryType == 2) {
			sqlView1 =
				"WITH color AS (" + //
						"SELECT " + //
							"it.id AS itemid, " + //
							"ita.value AS colorname " + //
						"FROM " + //
							"logistica.ddcorderdetail AS ddcod JOIN " + //
							"logistica.order AS oc ON oc.id = ddcod.ddcorder_id JOIN " + //
							"logistica.item AS it ON it.id = ddcod.item_id JOIN " + //
							"logistica.itemattribute ita ON ita.item_id = it.id " + //
						"WHERE " + //
							"oc.id = :ddcorderid AND oc.vendor_id = :vendorid AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_COLOR + "'), "; //
			
			sqlView2 =
					 "size AS (" + //
						"SELECT " + //
							 "it.id AS itemid, " + //
							 "ita.value AS sizename " + //
						 "FROM " + //
							"logistica.ddcorderdetail AS ddcod JOIN " + //
							"logistica.order AS oc ON oc.id = ddcod.ddcorder_id JOIN " + //
							"logistica.item AS it ON it.id = ddcod.item_id JOIN " + //
							"logistica.itemattribute ita ON ita.item_id = it.id " + //
						"WHERE " + //
							"oc.id = :ddcorderid AND oc.vendor_id = :vendorid AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_TALLA + "'), "; //

			sqlView3 =
					"section AS (" + //
						"SELECT " + //
							"it.id AS itemid, " + //
							"ita.value AS sectionname " + //
						"FROM " + //
							"logistica.ddcorderdetail AS ddcod JOIN " + //
							"logistica.order AS oc ON oc.id = ddcod.ddcorder_id JOIN " + //
							"logistica.item AS it ON it.id = ddcod.item_id JOIN " + //
							"logistica.itemattribute ita ON ita.item_id = it.id " + //
						"WHERE " + //
							"oc.id = :ddcorderid AND oc.vendor_id = :vendorid AND ita.attributetype = '" + LogisticConstants.PROD_ATRIB_SECCION +  "') "; //
			
			sqlSelect =
				"SELECT " + //
					"ddcod.position AS position, " + //
					"ddcod.item_id AS itemid, " + // 
					"it.sku AS itemsku, " + //
					"it.name AS itemname, " + //
					"COALESCE(vi.vendoritemcode, '') AS vendoritemcode, " + //
					"COALESCE(si.sizename, '') AS itemsize, " + //
					"COALESCE(co.colorname, '') AS itemcolor, " + //
					"COALESCE(se.sectionname, '') AS itemsection, " + // 
					"ddcod.basecost, " + //
					"ddcod.finalcost, " + //
					"ddcod.needunits, " + 
					"ddcod.totalneed "; //
			
			sqlFrom2 =
				"JOIN " + //
				"logistica.item AS it ON it.id = ddcod.item_id LEFT JOIN " + //
				"logistica.vendoritem AS vi ON vi.vendor_id = oc.vendor_id AND vi.item_id = it.id LEFT JOIN " + //
				"color AS co ON co.itemid = it.id LEFT JOIN " + //
				"size AS si ON si.itemid = it.id LEFT JOIN " + //
				"section AS se ON se.itemid = it.id "; //

			orderByCondition = getOrderByString(mapGetOrderDetailSQL, orderby);
		}	
		
		else {
			throw new AccessDeniedException("El tipo de query no es válido");
		}
		
		sqlFrom1 =
			"FROM " + //
				"logistica.ddcorderdetail AS ddcod JOIN " + //
				"logistica.order AS oc ON oc.id = ddcod.ddcorder_id "; //			
	
		sqlWhere =
			"WHERE " + //
				"oc.id = :ddcorderid AND oc.vendor_id = :vendorid "; //
		
		SQL =
			sqlView1 + //
				sqlView2 + //
				sqlView3 + //
			sqlSelect + //
			sqlFrom1 + //
				sqlFrom2 + //
			sqlWhere + //
			orderByCondition; //
		
		return SQL;	
	}
	
	public DdcOrderDetailW[] getDirectOrderDetailDataofDirectOrder(Long directorderid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.ddcorders.entities.DdcOrderDetail.getDirectOrderDetailDataofDirectOrder");
		query.setLong("orderid", directorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderDetailW.class));
		List list = query.list();
		DdcOrderDetailW[] result = (DdcOrderDetailW[]) list.toArray(new DdcOrderDetailW[list.size()]);
		return result;
	}
	
	public DdcOrderDetailExcelReportResultDTO getDdcOrderDetailExcelReportByOrder(Long ddcorderid) throws OperationFailedException {
		DdcOrderDetailExcelReportResultDTO resultDTO = new DdcOrderDetailExcelReportResultDTO();
		
		if (ddcorderid == null) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail.getDdcOrderDetailExcelReportByOrder");
		query.setLong("ddcorderid", ddcorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderDetailExcelReportDataDTO.class));
			
		List<?> list = query.list();
		DdcOrderDetailExcelReportDataDTO[] ddcOrderDetails = list.toArray(new DdcOrderDetailExcelReportDataDTO[list.size()]);
		
		resultDTO.setDdcorderdetails(ddcOrderDetails);
		
		return resultDTO;
	}
	
	public int countDdcOrderDetailExcelReportByOrder(Long ddcorderid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail.countDdcOrderDetailExcelReportByOrder");
		query.setLong("ddcorderid", ddcorderid);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
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
}
