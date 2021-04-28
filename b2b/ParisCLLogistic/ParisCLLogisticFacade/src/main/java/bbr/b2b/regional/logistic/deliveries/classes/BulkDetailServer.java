package bbr.b2b.regional.logistic.deliveries.classes;

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
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.Bulk;
import bbr.b2b.regional.logistic.deliveries.entities.BulkDetail;
import bbr.b2b.regional.logistic.deliveries.entities.BulkDetailId;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.report.classes.AsnDetailW;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailSummaryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListTotalDataDTO;
import bbr.b2b.regional.logistic.report.classes.UploadErrorDTO;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.entities.TaxDocument;

@Stateless(name = "servers/deliveries/BulkDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BulkDetailServer extends BaseLogisticEJB3Server<BulkDetail, BulkDetailId, BulkDetailW> implements BulkDetailServerLocal {


	private Map<String, String> mapGetProposedPackingListSQL = new HashMap<String, String>();
	{
		mapGetProposedPackingListSQL.put("ORDERNUMBER", "oc.number");
		mapGetProposedPackingListSQL.put("DELIVERYNUMBER", "dl.number");
		mapGetProposedPackingListSQL.put("TAXNUMBER", "taxnumber");
		mapGetProposedPackingListSQL.put("DESTINATIONCODE", "lo.code");
		mapGetProposedPackingListSQL.put("DESTINATIONDESC", "lo.name");
		mapGetProposedPackingListSQL.put("DEPARTMENTCODE", "dp.code");
		mapGetProposedPackingListSQL.put("DEPARTMENTDESC", "dp.name");
		mapGetProposedPackingListSQL.put("BULKID", "bulkid");
		mapGetProposedPackingListSQL.put("PALLETID", "palletid");
		mapGetProposedPackingListSQL.put("ATCCODE", "atccode");
		mapGetProposedPackingListSQL.put("ITEMSKU", "it.internalcode");
		mapGetProposedPackingListSQL.put("VENDORITEMCODE", "vi.vendoritemcodeE");
		mapGetProposedPackingListSQL.put("ITEMDESC", "it.name");
		mapGetProposedPackingListSQL.put("FLOWTYPE", "ft.name");		
		mapGetProposedPackingListSQL.put("UNITS", "bd.units");
	}	
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	@EJB
	TaxDocumentServerLocal taxdocumentserver;
	
	public BulkDetailW addBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW) addIdentifiable(bulkdetail);
	}
	public BulkDetailW[] getBulkDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW[]) getIdentifiables();
	}
	public BulkDetailW updateBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW) updateIdentifiable(bulkdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(BulkDetail entity, BulkDetailW wrapper) {
		wrapper.setBulkid(entity.getBulk() != null ? new Long(entity.getBulk().getId()) : new Long(0));
		wrapper.setOrderid(entity.getOrderdeliverydetail().getId() != null ? new Long(entity.getOrderdeliverydetail().getId().getOrderid()) : new Long(0));
		wrapper.setDeliveryid(entity.getOrderdeliverydetail().getId() != null ? new Long(entity.getOrderdeliverydetail().getId().getDeliveryid()) : new Long(0));
		wrapper.setItemid(entity.getOrderdeliverydetail().getId() != null ? new Long(entity.getOrderdeliverydetail().getId().getItemid()) : new Long(0));
		wrapper.setLocationid(entity.getOrderdeliverydetail().getId() != null ? new Long(entity.getOrderdeliverydetail().getId().getLocationid()) : new Long(0));
		wrapper.setTaxdocumentid(entity.getTaxdocument() != null ? new Long(entity.getTaxdocument().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(BulkDetail entity, BulkDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBulkid() != null && wrapper.getBulkid() > 0) { 
			Bulk bulk = bulkserver.getReferenceById(wrapper.getBulkid());
			if (bulk != null) { 
				entity.setBulk(bulk);
			}
		}
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getLocationid() != null && wrapper.getLocationid() > 0)) {
			OrderDeliveryDetailId key = new OrderDeliveryDetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setDeliveryid(wrapper.getDeliveryid());
			key.setItemid(wrapper.getItemid());
			key.setLocationid(wrapper.getLocationid());
			OrderDeliveryDetail var = orderdeliverydetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setOrderdeliverydetail(var);
			}
		}
		if (wrapper.getTaxdocumentid() != null && wrapper.getTaxdocumentid() > 0) { 
			TaxDocument taxdocument = taxdocumentserver.getReferenceById(wrapper.getTaxdocumentid());
			if (taxdocument != null) { 
				entity.setTaxdocument(taxdocument);
			}
		}
	}
	
	public ProposedPackingListDataDTO[] getProposedPackingList(Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean isByPages) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getProposedPackingListQueryString(2, orderby, "", "");
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(ProposedPackingListDataDTO.class));

		if(isByPages){	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		return (ProposedPackingListDataDTO[]) list.toArray(new ProposedPackingListDataDTO[list.size()]);
	}
	
	public ProposedPackingListTotalDataDTO getProposedPackingListCount(Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getProposedPackingListQueryString(1, null, "", "");
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
				
		query.setResultTransformer(new LowerCaseResultTransformer(ProposedPackingListTotalDataDTO.class));
		ProposedPackingListTotalDataDTO result = (ProposedPackingListTotalDataDTO) query.uniqueResult();		
		return result;		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "BulkDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "BulkDetail";
	}
	
	private String getProposedPackingListQueryString(int queryType, OrderCriteriaDTO[] orderby, String fromCondition, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL		 			= "";
		String condicionOrderBy		= "";		
				
		if (queryType == 1){
			SQL = 
				"SELECT " + //
					"CAST(COUNT(oc.id) AS INTEGER) AS totalreg, " + //
					"SUM(bd.units) AS units "; //		
		}
		else {
			SQL = 	
				"SELECT " + //
					"oc.id AS orderid, " + //
					"lo.id AS locationid, " + //
					"atc.id AS atcid, " + //
					"it.id AS itemid, " + //
					"oc.number AS ordernumber, " + //
					"dl.number AS deliverynumber, " + //
					"COALESCE(bd.refdocument, '') AS taxnumber, " + //
					"lo.code AS destinationcode, " + //
					"lo.name AS destinationdesc, " + //
					"dp.code AS departmentcode, " + //
					"dp.name AS departmentdesc, " + //
					"CASE WHEN bk.id = bx.id " + //
						 "THEN bk.code " + //
						 "ELSE '' " + //
						 "END AS bulkid, " + //
					"CASE WHEN bk.id = pl.id " + //
						 "THEN bk.code " + //
						 "ELSE '' " + //
						 "END AS palletid, " + //
					"COALESCE(atc.code, '') AS atccode, " + //
					"it.internalcode AS itemsku, " + //
					"vi.vendoritemcode AS vendoritemcode, " + //
					"it.name AS itemdesc, " + //
					"ft.name AS flowtype, " + //					
					"bd.units AS units "; //
					
			 condicionOrderBy = getProposedPackingListByString(mapGetProposedPackingListSQL, orderby);				
		}
		
		String fromSQL =
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.orderdelivery AS odl ON odl.order_id = oc.id JOIN " + //
				"logistica.delivery AS dl ON dl.id = odl.delivery_id JOIN " + //
				"logistica.orderdeliverydetail AS odd ON odd.order_id = oc.id AND odd.delivery_id = dl.id JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = odd.order_id AND od.item_id = odd.item_id JOIN " + //
				"logistica.item AS it ON it.id = odd.item_id JOIN " + //
				"logistica.location AS lo ON lo.id = odd.location_id JOIN " + //
				"logistica.bulkdetail AS bd ON bd.order_id = oc.id AND bd.delivery_id = dl.id AND bd.item_id = it.id AND " + //
												"bd.location_id = lo.id JOIN " + //
				"logistica.bulk AS bk ON bk.id = bd.bulk_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.vendoritem AS vi ON vi.item_id = it.id AND vi.vendor_id = vd.id JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id LEFT JOIN " + //
				"logistica.box AS bx ON bx.id = bk.id LEFT JOIN " + //
				"logistica.pallet AS pl ON pl.id = bk.id LEFT JOIN " + //
				"logistica.item_atc AS ia ON od.hasatc IS TRUE AND ia.item_id = it.id LEFT JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id "; //
		
		String whereSQL =
			"WHERE " + //
				"dl.id = :deliveryid ";  //
		
		SQL +=
			fromSQL + fromCondition + //
			whereSQL + whereCondition +  //
			condicionOrderBy; //		
		
		return SQL;		
	}	

	public BulkDetailSummaryDTO[] getBulkDetailSummary(Long deliveryid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.getBulkDetailSummary");
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(BulkDetailSummaryDTO.class));
		List list = query.list();
		BulkDetailSummaryDTO[] result = (BulkDetailSummaryDTO[]) list.toArray(new BulkDetailSummaryDTO[list.size()]);
		return result;	
	}
	
	public AsnDetailW[] getAsnDetailsByDelivery(Long deliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.getAsnDetailsByDelivery");
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(AsnDetailW.class));
		List list = query.list();
		AsnDetailW[] result = (AsnDetailW[]) list.toArray(new AsnDetailW[list.size()]);
		return result;
	}
	
	private String getProposedPackingListByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
	
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
	
	public String getRefDocumentByDeliveryId(Long deliveryid) {
		
		String SQL =
				"SELECT DISTINCT " + //
					"refdocument " + //
				"FROM " + //
					"logistica.bulkdetail " + //
				"WHERE " + //
					"delivery_id = :deliveryid"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		String result = (String)query.uniqueResult();
		return result;
	}
	
	public void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids) {
		
		String SQL =
				"DELETE " + //
				"FROM logistica.bulkdetail " + //
				"WHERE " + //
					"delivery_id = :deliveryid AND order_id IN (:orderids)"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setParameterList("orderids", orderids);

		query.executeUpdate();
	}
	
	public Long[] getTaxDocumentIdsByDelivery(Long deliveryid) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.getTaxDocumentIdsByDelivery");
		query.setLong("deliveryid", deliveryid);
				
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;	
	}
	
	public Long[] getTaxDocumentIdsByBulks(Long[] bulkids) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.getTaxDocumentIdsByBulks");
		query.setParameterList("bulkids", bulkids);
				
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;	
	}
	
	//****************************************************************************************************//
	//******************** SET DE QUERIES USADAS PARA AJUSTE DE DESPACHO ******************************// 
	//****************************************************************************************************//	
	public int doLoadCSV(String filename) {
		String SQL =
			"COPY predistributedplupload (line, containernumber, lpncode, locationcode, iteminternalcode, vendoritemcode, ordernumber, " + //
										 "units, taxdocumentnumber) " + //
			"FROM " + //
				"'" + filename + "' " + //
			"WITH DELIMITER ';' CSV "; //
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate();
		return total;		
	}
	
	public void doCreateTempTablePredistributedPackingList() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCreateTempTablePredistributedPackingList");
		query.executeUpdate();
	}
	
	public void doCreateTempTableError() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCreateTempTableError");
		query.executeUpdate();
	}
	
	public UploadErrorDTO[] getErrorsOfPredistributedPackingList() {
		
		String SQL =
			"SELECT " + //
				"line, " + //
				"error " + //
			"FROM " + //
				"predistributedplerror " + //
			"ORDER BY line ";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(UploadErrorDTO.class));
		List list = query.list();
		UploadErrorDTO[] result = (UploadErrorDTO[]) list.toArray(new UploadErrorDTO[list.size()]);
		return result;
	}
	
	public List<PredistributedPackingListDataDTO> getPredistributedPackingListDate() {
		
		String SQL =
			"SELECT " + //
				"order_id AS orderid, " + //
				"ordernumber, " + //
				"distributionordernumber, " + //
				"sequence, " + //
				"item_id AS itemid, " + //
				"iteminternalcode, " + //
				"COALESCE(vendoritemcode, '') AS vendoritemcode, " + //
				"innerpack, " + //
				"location_id AS locationid, " + //
				"locationcode, " + //
				"locationshortname, " + //
				"vendor_id AS vendorid, " + //
				"vendorcode, " + //
				"vendorname, " + //
				"logisticcode, " + //
				"flowtype_id AS flowtypeid, " + //
				"flowtypename, " + //
				"units, " + //
				"containernumber, " + //
				"lpncode, " + //
				"taxdocumentnumber " + //				
			"FROM " + //
				"predistributedplupload";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(PredistributedPackingListDataDTO.class));
		List<PredistributedPackingListDataDTO> list = (List<PredistributedPackingListDataDTO>)query.list();
		return list;
	}
	
	public int doFillOrderDetailData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doFillOrderDetailData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDestinationLocationData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doFillDestinationLocationData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueLpnItems() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckUniqueLpnItems");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueOrderByLpn() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckUniqueOrderByLpn");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueDestinationLocationByLpn() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckUniqueDestinationLocationByLpn");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueContainer() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckUniqueContainer");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidOrders() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckValidOrders");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidVendors() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckValidVendors");
		int total = query.executeUpdate();
		return total;
	}

	public int doCheckUniqueDeliveryLocation() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckUniqueDeliveryLocation");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidDeliveryLocation(Long locationid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckValidDeliveryLocation");
		query.setLong("locationid", locationid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidOrderItems(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckValidOrderItems");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidDestinationLocations() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckValidDestinationLocations");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLpnDestinationLocation() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckLpnDestinationLocation");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLpnVendorLogisticCode() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckLpnVendorLogisticCode");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLpnCorrelative() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckLpnCorrelative");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLpnFlowType() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.BulkDetail.doCheckLpnFlowType");
		int total = query.executeUpdate();
		return total;
	}
}
