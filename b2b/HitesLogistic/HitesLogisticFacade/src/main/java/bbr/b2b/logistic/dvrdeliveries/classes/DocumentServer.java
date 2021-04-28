package bbr.b2b.logistic.dvrdeliveries.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.entities.Document;
import bbr.b2b.logistic.dvrdeliveries.entities.DocumentType;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailReportDataDTO;

@Stateless(name = "servers/dvrdeliveries/DocumentServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentServer extends LogisticElementServer<Document, DocumentW> implements DocumentServerLocal{
	
	private Map<String, String> mapGetDvrDeliveryDatingDocumentDetailSQL = new HashMap<String, String>();
	{
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("DOCUMENTNUMBER", "doc.number");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("ORDERNUMBER", "oc.number");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("DOCUMENTTYPE", "dt.name");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("EMITTEDDATE", "doc.receptiondate");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("NETAMOUNT", "doc.netamount");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("TOTALAMOUNT", "doc.totalamount");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("TAXAMOUNT", "doc.iva");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("VALIDATED", "doc.validated ");
		mapGetDvrDeliveryDatingDocumentDetailSQL.put("SENTSTATUS", "doc.status");
	}

	@EJB
	DocumentTypeServerLocal documenttypeserver;

	public DocumentW addDocument(DocumentW document) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DocumentW) addElement(document);	
	}
	
	public DocumentW[] getDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DocumentW[]) getIdentifiables();
	}
	
	public DocumentW updateDocument(DocumentW document) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DocumentW) updateElement(document);
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(Document entity, DocumentW wrapper) {
		wrapper.setDocumenttypeid(entity.getDocumenttype() != null ? new Long(entity.getDocumenttype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(Document entity, DocumentW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDocumenttypeid() != null && wrapper.getDocumenttypeid() > 0) { 
			DocumentType documentyype = documenttypeserver.getReferenceById(wrapper.getDocumenttypeid());
			if (documentyype != null) { 
				entity.setDocumenttype(documentyype);
			}
		}
	}
	
	public Long getNextSequenceAsnNumber() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.ASNNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}

	
	public DvrDeliveryDatingDocumentDetailReportDataDTO[] getDvrDeliveryDatingDocumentDetailData(Long dvrdeliveryid, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException {	
		
		String sql = getDvrDeliveryDatingDocumentDetailDataQuery(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryDatingDocumentDetailReportDataDTO.class));
		List<?> list = query.list();
		DvrDeliveryDatingDocumentDetailReportDataDTO[] result = (DvrDeliveryDatingDocumentDetailReportDataDTO[]) list.toArray(new DvrDeliveryDatingDocumentDetailReportDataDTO[list.size()]);		
		return result;
	}
	
	public int getCountDvrDeliveryDatingDocumentDetailData(Long dvrdeliveryid) throws AccessDeniedException, OperationFailedException {
		
		String sql = getDvrDeliveryDatingDocumentDetailDataQuery(1, null);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		query.setLong("dvrdeliveryid", dvrdeliveryid);		
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	private String getDvrDeliveryDatingDocumentDetailDataQuery(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException {
		
		String sql = "";
		String orderByCondition = "";
						
		// Count
		if (queryType == 1) {	
			sql =
				"SELECT " + //
					"COUNT(DISTINCT doc.id) "; //
		}
		
		// Datos
		else if (queryType == 2) {
			sql =
				"SELECT DISTINCT "+ //
					"doc.id AS documentid, " + //
					"doc.number AS documentnumber, " + //
					"oc.number AS ordernumber, " + //
					"dt.name AS documenttype, " + //
					"doc.receptiondate AS emitteddate, " + // 
					"doc.netamount AS netamount, " + //
					"doc.totalamount AS totalamount, " + //
					"doc.iva AS taxamount, " + //
					"doc.validated AS validated, " + //
					"doc.status AS sentstatus "; //
			
			orderByCondition = getOrderByString(mapGetDvrDeliveryDatingDocumentDetailSQL, orderby);			
		}
		else {
			throw new OperationFailedException("El tipo de query no es válido: " + queryType);
		}
				
		sql += 
			"FROM " + //
				"logistica.dvrdelivery AS dvrdel JOIN " + //
				"logistica.dvrorderdeliverydetail AS dvrodd ON dvrodd.dvrdelivery_id = dvrdel.id JOIN " + //
				"logistica.bulkdetail AS bd ON bd.dvrorder_id = dvrodd.dvrorder_id AND bd.dvrdelivery_id = dvrodd.dvrdelivery_id AND " + //
											  "bd.item_id = dvrodd.item_id AND bd.location_id = dvrodd.location_id AND bd.position = dvrodd.position JOIN " + //
				"logistica.order AS oc ON oc.id = bd.dvrorder_id JOIN " + //
				"logistica.bulk AS bu ON bu.id = bd.bulk_id JOIN " + //
				"logistica.document AS doc ON doc.id = bu.document_id JOIN " + //
				"logistica.documenttype AS dt ON dt.id = doc.documenttype_id " + //
			"WHERE " + //
				"dvrdel.id = :dvrdeliveryid " + //
			orderByCondition; //
		
		return sql;		
	}
	
	public DocumentW[] getDocumentByIds(Long[] documentids) throws NotFoundException, OperationFailedException {
		DocumentW[] result = null;
		List<DocumentW> resultList = new ArrayList<DocumentW>();
		
		List<Long> documentidsList = Arrays.asList(documentids);
		String queryStr = 	"select doc from Document as doc " + // 
							"where doc.id in (:documentids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("doc.id", "documentids", documentidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DocumentW[]) resultList.toArray(new DocumentW[resultList.size()]);
		return result;
	}	
	
	public DocumentW[] getDocumentRelatedToOrderDelivery(Long dvrorderid, Long dvrdeliveryid) throws NotFoundException, OperationFailedException {
		DocumentW[] result = null;
		List<DocumentW> resultList = new ArrayList<DocumentW>();
		
		String queryStr = 	"select distinct  doc " + //
							"from Document as doc," + //
							"Bulk as bu, " + //
							"BulkDetail as bd " + //
							"where " + //
							"bu.document.id = doc.id " + //
							"and bd.bulk.id = bu.id " + //
							"and bd.dvrorderdeliverydetail.dvrpredeliverydetail.dvrorderdetail.dvrorder.id = :dvrorderid " + //
							"and bd.dvrorderdeliverydetail.dvrorderdelivery.dvrdelivery.id = :dvrdeliveryid ";
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("bd.dvrorderdeliverydetail.dvrorderdelivery.dvrorder.id", "dvrorderid", dvrorderid);
		properties[1] = new PropertyInfoDTO("bd.dvrorderdeliverydetail.dvrorderdelivery.dvrdelivery.id", "dvrdeliveryid", dvrdeliveryid);
		
		resultList = getByQuery(queryStr, properties);
		result =(DocumentW[]) resultList.toArray(new DocumentW[resultList.size()]);
		return result;
		
	}
	
	public DocumentW[] getDocumentRelatedToDelivery(Long dvrdeliveryid) throws NotFoundException, OperationFailedException {
		DocumentW[] result = null;
		List<DocumentW> resultList = new ArrayList<DocumentW>();
		
		String queryStr = 	"select distinct  doc " + //
							"from Document as doc," + //
							"Bulk as bu, " + //
							"BulkDetail as bd " + //
							"where " + //
							"bu.document.id = doc.id " + //
							"and bd.bulk.id = bu.id " + //
							"and bd.dvrorderdeliverydetail.dvrorderdelivery.dvrdelivery.id = :dvrdeliveryid ";
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("bd.dvrorderdeliverydetail.dvrorderdelivery.dvrdelivery.id", "dvrdeliveryid", dvrdeliveryid);
		
		resultList = getByQuery(queryStr, properties);
		result =(DocumentW[]) resultList.toArray(new DocumentW[resultList.size()]);
		return result;
	}
	
	
	
	public int getCorrelativeFromASNNumber(String documentnumber, Long vendorid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Document.getCorrelativeFromASNNumber");
		query.setString("documentnumber", documentnumber);
		query.setLong("vendorid", vendorid);
		List<?> list = query.list();
		if(list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}
	
	
	public ASNDataToMessageDTO[] getASNHeaderData(Long dvrdeliveryid, Long documentid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Document.getASNHeaderData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setLong("documentid", documentid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(ASNDataToMessageDTO.class));
		List<?> list = query.list();
		ASNDataToMessageDTO[] result = (ASNDataToMessageDTO[]) list.toArray(new ASNDataToMessageDTO[list.size()]);		
		return result;		
	}
	
	public ASNDetailDataToMessageDTO[] getASNDetailData(Long dvrdeliveryid, Long documentid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Document.getASNDetailData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setLong("documentid", documentid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(ASNDetailDataToMessageDTO.class));
		List<?> list = query.list();
		ASNDetailDataToMessageDTO[] result = (ASNDetailDataToMessageDTO[]) list.toArray(new ASNDetailDataToMessageDTO[list.size()]);		
		return result;
	}
	
	public ASNDataToMessageDTO[] getDeliveryASNHeaderData(Long dvrdeliveryid, Long[] documentids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Document.getDeliveryASNHeaderData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setParameterList("documentids", documentids);		
		query.setResultTransformer(new LowerCaseResultTransformer(ASNDataToMessageDTO.class));
		
		List<?> list = query.list();
		ASNDataToMessageDTO[] result = (ASNDataToMessageDTO[]) list.toArray(new ASNDataToMessageDTO[list.size()]);		
		return result;		
	}
	
	public ASNDetailDataToMessageDTO[] getDeliveryASNDetailData(Long dvrdeliveryid, Long[] documentids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Document.getDeliveryASNDetailData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setParameterList("documentids", documentids);
		query.setResultTransformer(new LowerCaseResultTransformer(ASNDetailDataToMessageDTO.class));
		
		List<?> list = query.list();
		ASNDetailDataToMessageDTO[] result = (ASNDetailDataToMessageDTO[]) list.toArray(new ASNDetailDataToMessageDTO[list.size()]);		
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
		entitylabel = "Document";
	}
	@Override
	protected void setEntityname() {
		entityname = "Document";
	}
		
}
