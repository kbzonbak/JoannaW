package bbr.b2b.regional.logistic.items.classes;

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
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.entities.VendorItem;
import bbr.b2b.regional.logistic.items.entities.VendorItemId;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportDataDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemVevReportDataDTO;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/items/VendorItemServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorItemServer extends BaseLogisticEJB3Server<VendorItem, VendorItemId, VendorItemW> implements VendorItemServerLocal {

	private Map<String, String> mapGetVendorItemsSQL = new HashMap<String, String>();
	{
		
		mapGetVendorItemsSQL.put("ITEMSKU", "IT.INTERNALCODE");
		mapGetVendorItemsSQL.put("DESCRIPTION", "IT.NAME");
		mapGetVendorItemsSQL.put("ITEMCLASS", "IC.NAME");
		mapGetVendorItemsSQL.put("VENDORITEMCODE", "VI.VENDORITEMCODE");
		mapGetVendorItemsSQL.put("FLOWTYPE", "FT.NAME");
		mapGetVendorItemsSQL.put("SIZE", "IT.SIZE");
		mapGetVendorItemsSQL.put("COLOR", "IT.COLOR");
		mapGetVendorItemsSQL.put("DIMENSION", "IT.DIMENSION");
		mapGetVendorItemsSQL.put("CASEPACK", "IT.CASEPACK");
		mapGetVendorItemsSQL.put("INNERPACK", "IT.INNERPACK");	
		mapGetVendorItemsSQL.put("BARCODE", "IT.BARCODE");
		mapGetVendorItemsSQL.put("EAN1", "IT.EAN1");
		mapGetVendorItemsSQL.put("EAN2", "IT.EAN2");
		mapGetVendorItemsSQL.put("EAN3", "IT.EAN3");
		
	}
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ItemServerLocal itemserver;

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
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorItem entity, VendorItemW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}
	
	public VendorItemDataW[] getVendorItemDataofOrder(Long orderid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.items.entities.VendorItem.getVendorItemDataofOrder");
		query.setLong("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataW.class));
		List list = query.list();
		VendorItemDataW[] result = (VendorItemDataW[]) list.toArray(new VendorItemDataW[list.size()]);
		return result;
	}
	
	public VendorItemDataW[] getVendorItemDataofDirectOrder(Long orderid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.items.entities.VendorItem.getVendorItemDataofDirectOrder");
		query.setLong("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataW.class));
		List list = query.list();
		VendorItemDataW[] result = (VendorItemDataW[]) list.toArray(new VendorItemDataW[list.size()]);
		return result;
	}
	
	public VendorItemReportDataDTO[] getVendorItemsByInternalCode(Long vendorid, String itemsku, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String whereCondition = "AND IT.INTERNALCODE LIKE :itemsku ";
		
		String SQL = getVendorItemsQueryString(2, orderby, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("itemsku", itemsku + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemReportDataDTO.class));
		
		if(ispaginated){	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		return (VendorItemReportDataDTO[]) list.toArray(new VendorItemReportDataDTO[list.size()]);

	}
	
	
	public VendorItemReportDataDTO[] getVendorItemsByVendorItemCode(Long vendorid, String vendoritemcode, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{

		String whereCondition = "AND VI.VENDORITEMCODE LIKE :vendoritemcode ";
		
		String SQL = getVendorItemsQueryString(2, orderby, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("vendoritemcode", vendoritemcode + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemReportDataDTO.class));
		
		if(ispaginated){	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		return (VendorItemReportDataDTO[]) list.toArray(new VendorItemReportDataDTO[list.size()]);
		
	}
	
	public VendorItemReportDataDTO[] getVendorItemsByName(Long vendorid, String description, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String whereCondition = "AND IT.NAME LIKE :description ";
		
		String SQL = getVendorItemsQueryString(2, orderby, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("description", "%" + description + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemReportDataDTO.class));
		
		if(ispaginated){	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		return (VendorItemReportDataDTO[]) list.toArray(new VendorItemReportDataDTO[list.size()]);
		
	}
	
	public VendorItemReportDataDTO[] getVendorItemsByOrderNumber(Long vendorid, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String fromCondition = "JOIN LOGISTICA.ORDERDETAIL AS OD ON (OD.ITEM_ID = IT.ID) " +
								"JOIN LOGISTICA.ORDER AS O ON (O.ID = OD.ORDER_ID) ";
		String whereCondition = "AND O.NUMBER = :ordernumber ";
		
		String SQL = getVendorItemsQueryString(2, orderby, fromCondition, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setLong("ordernumber", ordernumber.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemReportDataDTO.class));
		
		if(ispaginated){	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		return (VendorItemReportDataDTO[]) list.toArray(new VendorItemReportDataDTO[list.size()]);	
		
	}
	
	public int getVendorItemsCountByInternalCode(Long vendorid, String itemsku) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String whereCondition = "AND IT.INTERNALCODE LIKE :itemsku ";
		
		String SQL = getVendorItemsQueryString(1, null, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("itemsku", itemsku + "%");
		
		int total = ((BigInteger)query.list().get(0)).intValue();	
		
		return total;	
		
	}
	
	public int getVendorItemsCountByVendorItemCode(Long vendorid, String vendoritemcode) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String whereCondition = "AND VI.VENDORITEMCODE LIKE :vendoritemcode ";;
		
		String SQL = getVendorItemsQueryString(1, null, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("vendoritemcode", vendoritemcode + "%");
		
		int total = ((BigInteger)query.list().get(0)).intValue();	
		
		return total;
		
	}
	
	public int getVendorItemsCountByName(Long vendorid, String description) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String whereCondition = "AND IT.NAME LIKE :description ";;
		
		String SQL = getVendorItemsQueryString(1, null, "", whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setString("description", "%" + description + "%");
		
		int total = ((BigInteger)query.list().get(0)).intValue();	
		
		return total;
		
	}
	
	public int getVendorItemsCountByOrderNumber(Long vendorid, Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String fromCondition = "JOIN LOGISTICA.ORDERDETAIL AS OD ON (OD.ITEM_ID = IT.ID) " +
								"JOIN LOGISTICA.ORDER AS O ON (O.ID = OD.ORDER_ID) ";
		String whereCondition = "AND O.NUMBER = :ordernumber ";
		
		String SQL = getVendorItemsQueryString(1, null, fromCondition, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid.longValue());
		query.setLong("ordernumber", ordernumber.longValue());
		
		int total = ((BigInteger)query.list().get(0)).intValue();	
		
		return total;
		
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorItem";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorItem";
	}
	
	private String getVendorItemsQueryString(int queryType, OrderCriteriaDTO[] orderBy, String fromCondition, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL		 			= "";
		String condicionOrderBy		= "";		
				
		if (queryType == 1){
			SQL = 
				"SELECT " +
					"COUNT(IT.ID) ";			
		}
		else {
			SQL = 	
				"SELECT " +
					"IT.INTERNALCODE AS ITEMSKU, " +
					"IT.NAME AS DESCRIPTION, " + 
					"IC.NAME AS ITEMCLASS, " +
					"VI.VENDORITEMCODE AS VENDORITEMCODE, " + 
					"FT.NAME AS FLOWTYPE, " + 
					"IT.SIZE AS SIZE, " +
					"IT.COLOR AS COLOR, " +
					"IT.DIMENSION AS DIMENSION, " +
					"IT.CASEPACK AS CASEPACK, " +
					"IT.INNERPACK AS INNERPACK, " +
					"IT.BARCODE AS BARCODE, " +
					"IT.EAN1 AS EAN1, " +
					"IT.EAN2 AS EAN2, " +
					"IT.EAN3 AS EAN3 ";
			
			condicionOrderBy = getVendorItemsByString(mapGetVendorItemsSQL, orderBy);				
		}
		
		String fromSQL = "FROM " + 
							"LOGISTICA.VENDORITEM AS VI " + 
							"JOIN LOGISTICA.ITEM AS IT ON (IT.ID = VI.ITEM_ID) " +
							"JOIN LOGISTICA.ITEMCLASS AS IC ON (IC.ID = IT.ITEMCLASS_ID) " +
							"LEFT JOIN LOGISTICA.FLOWTYPE AS FT ON (FT.ID = IT.FLOWTYPE_ID) ";
		
		String whereSQL = "WHERE " +
							"VI.VENDOR_ID = :vendorid ";
		
		SQL = SQL + 
				fromSQL + fromCondition +
					whereSQL + whereCondition +
						condicionOrderBy;		
		
		return SQL;		
	}	
	
	private String getVendorItemsByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderBy) throws AccessDeniedException {
		
		if (orderBy == null || orderBy.length == 0)
			return "";
		
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderBy) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
	/*
	 * Agregado para solicitar el vendorItem vev basado en el sku del inventario y el vendor
	 */
	public VendorItemVevReportDataDTO getVendorItemByVendorAndSku(Long vendorid, String itemcode) throws ServiceException{
		 
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.items.entities.VendorItem.getVendorItemVevByVendorAndSku");
		query.setLong("vendorid", vendorid);
		query.setString("itemcode", itemcode);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemVevReportDataDTO.class));
		List list = query.list();
		VendorItemVevReportDataDTO[] resultArr = (VendorItemVevReportDataDTO[]) list.toArray(new VendorItemVevReportDataDTO[list.size()]);
		
		VendorItemVevReportDataDTO result = null;
		if (resultArr != null && resultArr.length >0 )
			result = resultArr[0];
		
		return result;
	}
	
}
