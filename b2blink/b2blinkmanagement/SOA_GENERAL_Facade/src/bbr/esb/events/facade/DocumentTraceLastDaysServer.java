package bbr.esb.events.facade;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.DocumentTraceLastDaysDTO;
import bbr.esb.events.data.classes.InternalScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardTableDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyReturnDTO;
import bbr.esb.events.entities.DocumentTraceLastDays;
import bbr.esb.events.entities.DocumentTraceType;

@Stateless(name = "servers/events/DocumentTraceLastDaysServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentTraceLastDaysServer extends ElementServer<DocumentTraceLastDays, DocumentTraceLastDaysDTO> implements DocumentTraceLastDaysServerLocal {

	@EJB
	DocumentTraceTypeServerLocal DocumentTraceLastDaystypeserver;

	private Map<String, String> mapSQLOrderBy = new HashMap<String, String>();
	{
		mapSQLOrderBy.put("NODOC", "ld.num_doc");
		mapSQLOrderBy.put("SITIO", "site_cod");
		mapSQLOrderBy.put("FECHA", "fecha");
		mapSQLOrderBy.put("ESTADO", "estado");
	}

	public DocumentTraceLastDaysDTO addDocumentTraceLastDays(DocumentTraceLastDaysDTO DocumentTraceLastDays) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(DocumentTraceLastDays);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DocumentTraceLastDays entity, DocumentTraceLastDaysDTO wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setDocumenttracetypekey((entity.getDocumenttracetype() != null ? new Long(entity.getDocumenttracetype().getId()) : new Long(0)));
	}

	@Override
	protected void copyRelationsWrapperToEntity(DocumentTraceLastDays entity, DocumentTraceLastDaysDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDocumenttracetypekey() != null && wrapper.getDocumenttracetypekey() > 0) {
			DocumentTraceType DocumentTraceLastDaystype = DocumentTraceLastDaystypeserver.getReferenceById(wrapper.getDocumenttracetypekey());
			if (DocumentTraceLastDaystype != null) {
				entity.setDocumenttracetype(DocumentTraceLastDaystype);
			}
		}
	}

	public void deleteDocumentTraceLastDays(Long DocumentTraceLastDaysid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(DocumentTraceLastDaysid);
	}

	public DocumentTraceLastDaysDTO getDocumentTraceLastDaysByPK(Long DocumentTraceLastDaysid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(DocumentTraceLastDaysid);
	}

	@Override
	public InternalScoreCardDTO[] getScordCardValuesForSupplier(String companyrut, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException {

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.DocumentTraceLastDays.getScoreCardSumaryValues");
		query.setResultTransformer(new LowerCaseResultTransformer(InternalScoreCardDTO.class));
		query.setString("companyrut", companyrut);
		query.setString("servicename", servicename);
		List list = query.list();
		InternalScoreCardDTO[] result = (InternalScoreCardDTO[]) list.toArray(new InternalScoreCardDTO[list.size()]);
		return result;
	}

	public int getCountScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam) throws ParseException {

		String SQL = getScoreCardTableOfCompanyQuery(initparam);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);

		// SETEA LOS PARAMETROS DE LA QUERY
		query.setString("companyrut", initparam.getCompanyrut());
		query.setString("servicename", initparam.getServicename());

		if (initparam.getFilterType().equals(new Integer(1))) {
			// DATE FILTER
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(initparam.getUntil()));
			
		} else if (initparam.getFilterType().equals(new Integer(2)) && !initparam.getSince().isEmpty() && !initparam.getUntil().isEmpty()) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(initparam.getUntil()));
		
		} else if (initparam.getFilterType().equals(new Integer(3))) {
			// DOCUMENT FILTER
			query.setLong("numdoc", initparam.getNumdoc());
			
		} else if (initparam.getFilterType().equals(new Integer(4))) {
			// RETAIL FILTER
			query.setString("sitecode", initparam.getSitename());
			
		}
		Integer result = new Long(((BigInteger) query.uniqueResult()).longValue()).intValue();
		if (result == null)
			result = 0;
		return result.intValue();

	}

	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam) throws ParseException {
		ScoreCardTableOfCompanyReturnDTO result = new ScoreCardTableOfCompanyReturnDTO();

		String SQL = getScoreCardTableOfCompanyQuery(initparam);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);

		// SETEA LOS PARAMETROS DE LA QUERY
		query.setString("companyrut", initparam.getCompanyrut());
		query.setString("servicename", initparam.getServicename());

		if (initparam.getFilterType().equals(new Integer(1))) {
			// DATE FILTER
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(initparam.getUntil()));
			
		} else if (initparam.getFilterType().equals(new Integer(2)) && !initparam.getSince().isEmpty() && !initparam.getUntil().isEmpty()) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(initparam.getUntil()));
			
		} else if (initparam.getFilterType().equals(new Integer(3))) {
			// DOCUMENT FILTER
			query.setLong("numdoc", initparam.getNumdoc());			
		} else if (initparam.getFilterType().equals(new Integer(4))) {
			// RETAIL FILTER
			query.setString("sitecode", initparam.getSitename());
		}

		query.setResultTransformer(new LowerCaseResultTransformer(ScoreCardTableDTO.class));
		if (initparam.isPaginated()) {
			query.setFirstResult((initparam.getPageNumber() - 1) * initparam.getRows());
			query.setMaxResults(initparam.getRows());
		}

		List list = query.list();

		ScoreCardTableDTO[] resultArray = (ScoreCardTableDTO[]) list.toArray(new ScoreCardTableDTO[list.size()]);
		result.setScorecards(resultArray);

		return result;
	}

	public String getScoreCardTableOfCompanyQuery(ScoreCardTableOfCompanyInitParamDTO initparam) {

		String orderby = "";
		String filter = "";

		if (initparam.getFilterType() == 1) {
			// DATE FILTER
			filter = "WHERE date(\"timestamp\") >= date(:since) AND  date(\"timestamp\") <= date(:until) ";
		} else if (initparam.getFilterType() == 2) {			
			if(!initparam.getStatus().equals("TODOS")){
				// STATE FILTER
				if(initparam.getStatus().equals("En proceso")){
					filter = "WHERE document_tracetype_id in (1,2,3) ";
				}else if(initparam.getStatus().equals("Recibido")){
					filter = "WHERE document_tracetype_id = 4 ";
				}else if(initparam.getStatus().equals("Error")){
					filter = "WHERE document_tracetype_id = 5 ";
				}
			}
			if(!initparam.getSince().isEmpty() && !initparam.getUntil().isEmpty()){
				filter +=  "AND date(\"timestamp\") >= date(:since) AND  date(\"timestamp\") <= date(:until) ";
			}
		} else if (initparam.getFilterType() == 3) {
			// DOCUMENT FILTER
			filter = "WHERE ld.num_doc = :numdoc ";
		} else if (initparam.getFilterType() == 4) {
			// RETAIL FILTER
			filter = "WHERE site_cod = :sitecode ";
		}
		try {
			orderby = getOrderByString(mapSQLOrderBy, initparam.getOrderBy());
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		String select = "";
		if (initparam.isQueryToCount()) {
			select = "SELECT COUNT(1) ";
			orderby = "";
		} else {
			select = "SELECT ld.num_doc AS numdoc, site_cod AS sitio, \"timestamp\" AS fecha," 
					+ "CASE WHEN(document_tracetype_id in (4,8)) THEN 'Recibido' " 
					+ "WHEN(document_tracetype_id in (5)) THEN 'Error' " 
					+ "WHEN(document_tracetype_id in (6)) THEN 'Reenviando' " 
					+ "ELSE " 
						+ "'En proceso' END AS estado, s.retail_name as retail, s.id as siteid, se.id as serviceid, co.id as companyid ";
		}
		String SQL = "WITH maxdocs AS (" //
				+ "SELECT max(id) as maxid, " //
				+ "num_doc FROM public.document_trace "//
				+ "WHERE service_cod = :servicename and company_rut= :companyrut " + "GROUP BY num_doc" + ") " // 
				+ select //
				+ "FROM public.document_trace ld " //
				+ "INNER JOIN maxdocs m ON (m.maxid = ld.id) " //
				+ "INNER JOIN public.site s ON s.name=site_cod "
				+ "INNER JOIN public.service se ON se.name= ld.service_cod "
				+ "INNER JOIN public.company co on co.rut=ld.company_rut "
				+ "INNER JOIN public.access ac ON co.id = ac.company_id AND s.id = ac.site_id "
				+ filter + orderby; //
		
		return SQL;
	}


	@Override
	protected void setEntitylabel() {
		entitylabel = "Traza de documento ultimos siete dias";

	}

	@Override
	protected void setEntityname() {
		entityname = "DocumentTraceLastDays";
	}

	public DocumentTraceLastDaysDTO updateDocumentTraceLastDays(DocumentTraceLastDaysDTO DocumentTraceLastDays) throws OperationFailedException, NotFoundException {
		return updateElement(DocumentTraceLastDays);
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
