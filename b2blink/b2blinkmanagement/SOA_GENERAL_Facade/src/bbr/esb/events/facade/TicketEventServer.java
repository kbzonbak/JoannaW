package bbr.esb.events.facade;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import bbr.esb.events.data.classes.TicketEventDTO;
import bbr.esb.events.data.classes.TicketReportDataResultDTO;
import bbr.esb.events.data.classes.TicketReportInitParamDTO;
import bbr.esb.events.data.classes.TicketReportResultDTO;
import bbr.esb.events.entities.TicketEvent;
import bbr.esb.events.entities.TicketStateType;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.User;
import bbr.esb.users.facade.CompanyServerLocal;
import bbr.esb.users.facade.UserServerLocal;

@Stateless(name = "servers/events/TicketEventServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketEventServer extends ElementServer<TicketEvent, TicketEventDTO> implements TicketEventServerLocal {

	@EJB
	private CompanyServerLocal companyserver;

	@EJB
	private ServiceServerLocal serviceserver;

	@EJB
	private SiteServerLocal siteserver;

	@EJB
	private TicketStateTypeServerLocal ticketstatetypeserver;
	
	@EJB
	private UserServerLocal userserver;
	
	
	
	
	private Map<String, String> mapSQLOrderBy = new HashMap<String, String>();
	{
		mapSQLOrderBy.put("nrosolicitud", "te.ticketnumber");
		mapSQLOrderBy.put("CLIENTE", "si.retail_name");
		mapSQLOrderBy.put("FECHASOLICITUD", "te.date_created");
		mapSQLOrderBy.put("ESTADO", "tst.name");
	}

	public TicketEventDTO addTicketEvent(TicketEventDTO ticketevent) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(ticketevent);
	}

	@Override
	protected void copyRelationsEntityToWrapper(TicketEvent entity, TicketEventDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO EBO TEST
		wrapper.setSitekey(entity.getSite() != null ? new Long(entity.getSite().getId()) : new Long(0));
		wrapper.setServicekey(entity.getService() != null ? new Long(entity.getService().getId()) : new Long(0));
		wrapper.setCompanykey(entity.getCompany() != null ? new Long(entity.getCompany().getId()) : new Long(0));
		wrapper.setCurrentstatetypekey(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setUserid(entity.getUser() != null ? new Long(entity.getUser().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(TicketEvent entity, TicketEventDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO EBO TEST
		if (wrapper.getSitekey() != null && wrapper.getSitekey() > 0) {
			Site site = siteserver.getReferenceById(wrapper.getSitekey());
			if (site != null) {
				entity.setSite(site);
			}
		}
		if (wrapper.getServicekey() != null && wrapper.getServicekey() > 0) {
			Service service = serviceserver.getReferenceById(wrapper.getServicekey());
			if (service != null) {
				entity.setService(service);
			}
		}
		if (wrapper.getCompanykey() != null && wrapper.getCompanykey() > 0) {
			Company company = companyserver.getReferenceById(wrapper.getCompanykey());
			if (company != null) {
				entity.setCompany(company);
			}
		}
		if (wrapper.getCurrentstatetypekey() != null && wrapper.getCurrentstatetypekey() > 0) {
			TicketStateType ticketstatetype = ticketstatetypeserver.getReferenceById(wrapper.getCurrentstatetypekey());
			if (ticketstatetype != null) {
				entity.setCurrentstatetype(ticketstatetype);
			}
		}
		if (wrapper.getUserid() != null && wrapper.getUserid() > 0) {
			User user = userserver.getReferenceById(wrapper.getUserid());
			if (user != null) {
				entity.setUser(user);
			}
		}
	}

	public void deleteTicketEvent(Long ticketeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(ticketeventid);
	}

	public TicketEventDTO getTicketEventByPK(Long ticketeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(ticketeventid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Evento de Ticket";
	}

	@Override
	protected void setEntityname() {
		entityname = "TicketEvent";
	}

	public TicketEventDTO updateTicketEvent(TicketEventDTO ticketevent) throws OperationFailedException, NotFoundException {
		return updateElement(ticketevent);
	}
	
	public int getCountTicketReport(TicketReportInitParamDTO initparam) throws ParseException {

		String SQL = getTicketreportQuery(initparam);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);

		// SETEA LOS PARAMETROS DE LA QUERY
		query.setString("companyrut", initparam.getCompanyrut());

		if (initparam.getFilterType().equals(new Integer(1))) {
			// DATE FILTER
			
			String str = initparam.getUntil();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until = LocalDate.parse(str, formatter);
			until = until.plusDays(1);
			String strUntil = until.format(formatter);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(strUntil));
			
		}else if (initparam.getFilterType().equals(new Integer(2))) {
			// DOCUMENT FILTER
			query.setLong("nrosolicitud", initparam.getNumdoc());			
		} else if (initparam.getFilterType().equals(new Integer(3))) {
			// REFERENCE FILTER
			query.setString("reference", initparam.getReference());
		}
		
		if (initparam.getFilterTypeSol().equals(new Integer(1)) && !initparam.getServiceid().equals("-1")){
			query.setInteger("service", Integer.parseInt(initparam.getServiceid()));
		}else if(initparam.getFilterTypeSol().equals(new Integer(2)) && !initparam.getStatusid().equals("-1")){
			query.setLong("estado", Long.parseLong(initparam.getStatusid()));
		}
		if(!initparam.getSiteid().equals(-1L)){
			query.setLong("siteid", initparam.getSiteid());
		}

		
		Integer result = new Long(((BigInteger) query.uniqueResult()).longValue()).intValue();
		if (result == null)
			result = 0;
		return result.intValue();

	}

	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO initparam) throws ParseException {
		TicketReportResultDTO result = new TicketReportResultDTO();

		String SQL = getTicketreportQuery(initparam);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);

		// SETEA LOS PARAMETROS DE LA QUERY
		query.setString("companyrut", initparam.getCompanyrut());

		if (initparam.getFilterType().equals(new Integer(1))) {
			// DATE FILTER
			
			String str = initparam.getUntil();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until = LocalDate.parse(str, formatter);
			until = until.plusDays(1);
			String strUntil = until.format(formatter);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("since", format.parse(initparam.getSince()));
			query.setDate("until", format.parse(strUntil));
			
		}else if (initparam.getFilterType().equals(new Integer(2))) {
			// DOCUMENT FILTER
			query.setLong("nrosolicitud", initparam.getNumdoc());			
		} else if (initparam.getFilterType().equals(new Integer(3))) {
			// REFERENCE FILTER
			query.setString("reference", initparam.getReference());
		}
		
		if ( initparam.getFilterTypeSol().equals(new Integer(1)) && !initparam.getServiceid().equals("-1")){
			query.setInteger("service", Integer.parseInt(initparam.getServiceid()));
		}else if(initparam.getFilterTypeSol().equals(new Integer(2)) && !initparam.getStatusid().equals("-1")){
			query.setLong("estado", Long.parseLong(initparam.getStatusid()));
		}
		if(!initparam.getSiteid().equals(-1L)){
			query.setLong("siteid", initparam.getSiteid());
		}

		query.setResultTransformer(new LowerCaseResultTransformer(TicketReportDataResultDTO.class));
		
		if (initparam.isPaginated()) {
			query.setFirstResult((initparam.getPageNumber() - 1) * initparam.getRows());
			query.setMaxResults(initparam.getRows());
		}

		List list = query.list();

		TicketReportDataResultDTO[] resultArray = (TicketReportDataResultDTO[]) list.toArray(new TicketReportDataResultDTO[list.size()]);
		result.setTickets(resultArray);

		return result;
	}

	public String getTicketreportQuery(TicketReportInitParamDTO initparam) {

		String orderby = "";
		String filter = "";

//		FILTROS
		if (initparam.getFilterType() == 1) {
			// DATE FILTER
			filter = "WHERE te.date_created >= date(:since) AND  te.date_created < date(:until) ";
		}else if (initparam.getFilterType() == 2) {
			// DOCUMENT FILTER
			filter = "WHERE te.ticketnumber = :nrosolicitud ";
		} else if (initparam.getFilterType() == 3) {
			// REFERENCE FILTER
			filter = "WHERE te.referencia = :reference ";
		}
//		SOLICITUDES
		if(initparam.getFilterTypeSol()== 1 && !initparam.getServiceid().equals("-1")){
			filter += " AND se.id = :service";
		}else if(initparam.getFilterTypeSol()== 2 && !initparam.getStatusid().equals("-1")){
			filter += " AND tst.id = :estado";
		}
		if(!initparam.getSiteid().equals(-1L)){
			filter += " AND si.id = :siteid";
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
			select = "SELECT "
				+ "te.ticketnumber  nrosolicitud, se.name tiposolicitud,si.retail_name cliente, "
				+ "te.date_created fechasolicitud, te.currentstatetypedate fechaproceso, tst.name as estado, te.adjunto, te.referencia";
		}
		
		String SQL = select + " FROM public.ticketevent AS te "
					+ "JOIN site AS si ON te.site_id = si.id "
					+ "JOIN service AS se ON te.service_id = se.id "
					+ "JOIN ticketstatetype AS tst ON te.currentstatetype_id= tst.id "
					+ "JOIN company as com ON te.company_id = com.id "
					+ filter + " AND com.rut = :companyrut " + orderby; //
		
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
