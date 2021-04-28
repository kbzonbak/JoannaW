package bbr.b2b.regional.logistic.taxdocuments.classes;

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
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentState;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentStateType;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateCommentDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateDTO;

@Stateless(name = "servers/taxdocuments/DOTaxDocumentStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOTaxDocumentStateServer extends LogisticElementServer<DOTaxDocumentState, DOTaxDocumentStateW> implements DOTaxDocumentStateServerLocal {

	private Map<String, String> mapGetDOTaxDocumentStateSQL = new HashMap<String, String>();
	{
		mapGetDOTaxDocumentStateSQL.put("VENDORNAME", "vd.name");
		mapGetDOTaxDocumentStateSQL.put("DIRECTORDERNUMBER", "doc.number");
		mapGetDOTaxDocumentStateSQL.put("DOTAXDOCUMENTNUMBER", "dotd.number");
		mapGetDOTaxDocumentStateSQL.put("DOTAXDOCUMENTSTATEWHEN", "dotds.when1");
		mapGetDOTaxDocumentStateSQL.put("DOTAXDOCUMENTSTATETYPENAME", "dotdst.name");
		mapGetDOTaxDocumentStateSQL.put("DOTAXDOCUMENTSTATEUSERNAME", "dotds.who");
		mapGetDOTaxDocumentStateSQL.put("DOTAXDOCUMENTSTATEUSERTYPE", "dotds.usertype");
	}	
	
	@EJB
	DOTaxDocumentServerLocal dotaxdocumentserver;

	@EJB
	DOTaxDocumentStateTypeServerLocal dotaxdocumentstatetypeserver;

	public DOTaxDocumentStateW addDOTaxDocumentState(DOTaxDocumentStateW dotaxdocumentstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateW) addElement(dotaxdocumentstate);
	}
	
	public DOTaxDocumentStateW[] getDOTaxDocumentStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateW[]) getIdentifiables();
	}
	
	public DOTaxDocumentStateW updateDOTaxDocumentState(DOTaxDocumentStateW dotaxdocumentstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentStateW) updateElement(dotaxdocumentstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DOTaxDocumentState entity, DOTaxDocumentStateW wrapper) {
		wrapper.setDotaxdocumentid(entity.getDotaxdocument() != null ? new Long(entity.getDotaxdocument().getId()) : new Long(0));
		wrapper.setDotaxdocumentstatetypeid(entity.getDotaxdocumentstatetype() != null ? new Long(entity.getDotaxdocumentstatetype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DOTaxDocumentState entity, DOTaxDocumentStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDotaxdocumentid() != null && wrapper.getDotaxdocumentid() > 0) { 
			DOTaxDocument dotaxdocument = dotaxdocumentserver.getReferenceById(wrapper.getDotaxdocumentid());
			if (dotaxdocument != null) { 
				entity.setDotaxdocument(dotaxdocument);
			}
		}
		if (wrapper.getDotaxdocumentstatetypeid() != null && wrapper.getDotaxdocumentstatetypeid() > 0) { 
			DOTaxDocumentStateType dotaxdocumentstatetype = dotaxdocumentstatetypeserver.getReferenceById(wrapper.getDotaxdocumentstatetypeid());
			if (dotaxdocumentstatetype != null) { 
				entity.setDotaxdocumentstatetype(dotaxdocumentstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DOTaxDocumentState";
	}
	
	@Override
	protected void setEntityname() {
		entityname = "DOTaxDocumentState";
	}
	
	public List<DOTaxDocumentStateCommentDTO> getCommentsByDOTaxDocumentTicket(Long ticketid) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentState.getCommentsByDOTaxDocumentTicket");
		query.setLong("ticketid", ticketid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentStateCommentDTO.class));
		List result = (List<DOTaxDocumentStateCommentDTO>)query.list();
		return result;		
	}
	
	public DOTaxDocumentStateDTO[] getDOTaxDocumentStateReportByDOTaxDocuments(Long[] dotaxdocumentids, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		
		String SQL = getDOTaxDocumentStateReportQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("dotaxdocumentids", dotaxdocumentids);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentStateDTO.class));
		
		if (ispaginated) {//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentStateDTO[] result = (DOTaxDocumentStateDTO[]) list.toArray(new DOTaxDocumentStateDTO[list.size()]);		
		
		return result;
	}
	
	public int countDOTaxDocumentStateReportByDOTaxDocuments(Long[] dotaxdocumentids) throws AccessDeniedException {
		
		String SQL = getDOTaxDocumentStateReportQueryString(1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("dotaxdocumentids", dotaxdocumentids);
						
		int total = ((BigInteger)query.list().get(0)).intValue();		
		return total;
	}
	
	private String getDOTaxDocumentStateReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL 					= "";
		String conditionOrderBy		= "";				
		
		if (queryType == 1){
			SQL = 
				"SELECT " + //
					"COUNT(dotds.id) "; //
		}
		else {
			SQL =
				"SELECT " + //
					"vd.id AS vendorid, " + //
					"vd.name AS vendorname, " + //
					"doc.id AS directorderid, " + //
					"doc.number AS directordernumber, " + //
					"dotd.id AS dotaxdocumentid, " + //
					"dotd.number AS dotaxdocumentnumber, " + //
					"logistica.tostr(dotds.when1) AS dotaxdocumentstatewhen, " + //
					"dotdst.id AS dotaxdocumentstatetypeid, " + //
					"dotdst.name AS dotaxdocumentstatetypename, " + //
					"dotds.who AS dotaxdocumentstateusername, " + //
					"dotds.usertype AS dotaxdocumentstateusertype "; //
						
			conditionOrderBy = getOrderByString(mapGetDOTaxDocumentStateSQL, orderby);				
		}
		
		SQL += 
			"FROM " + //
				"logistica.dotaxdocument AS dotd JOIN " + //
				"logistica.directorder AS doc ON doc.id = dotd.directorder_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = dotd.vendor_id JOIN " + //   
				"logistica.dotaxdocumentstate AS dotds ON dotds.dotaxdocument_id = dotd.id JOIN " + //
				"logistica.dotaxdocumentstatetype AS dotdst ON dotdst.id = dotds.dotaxdocumentstatetype_id " + //
			"WHERE " + //
				"dotd.id IN (:dotaxdocumentids) " + //
			conditionOrderBy; //
		
		return SQL;		
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