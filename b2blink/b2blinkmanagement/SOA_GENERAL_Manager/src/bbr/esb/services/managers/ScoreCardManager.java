package bbr.esb.services.managers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.classes.PageInfoDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.InternalScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardSiteFilterDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyReturnDTO;
import bbr.esb.events.data.classes.SiteProgress;
import bbr.esb.events.data.classes.SitesFilterDTO;
import bbr.esb.events.data.classes.TicketReportInitParamDTO;
import bbr.esb.events.data.classes.TicketReportResultDTO;
import bbr.esb.events.data.classes.TicketStateTypeDTO;
import bbr.esb.events.data.classes.TicketStateTypeFilterDTO;
import bbr.esb.events.facade.DocumentTraceLastDaysServerLocal;
import bbr.esb.events.facade.TicketEventServerLocal;
import bbr.esb.events.facade.TicketStateTypeServerLocal;
import bbr.esb.services.data.classes.ContractedServiceOfRutDTO;
import bbr.esb.services.data.classes.RequestFilterForTicketDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.ServiceFilterDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.facade.ContractedServiceServerLocal;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.users.facade.CompanyServerLocal;

@Stateless(name = "managers/ScoreCardManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScoreCardManager implements ScoreCardManagerLocal {

	@EJB
	DocumentTraceLastDaysServerLocal documenttracelastdaysserver;
	
	@EJB
	ContractedServiceServerLocal contractedserviceserver;
	
	@EJB
	CompanyServerLocal companyserver;
	
	@EJB
	ServiceServerLocal serviceserver;
	
	@EJB
	TicketEventServerLocal ticketeventserver;
	
	@EJB
	TicketStateTypeServerLocal ticketstatetypeserver;
	
	@EJB
	SiteServerLocal siteserver;

	
		
	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam) {
		// Método para la table de resumen por número de orden
		int total = 0;
		ScoreCardTableOfCompanyReturnDTO scoreCardTableDTO;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if(initparam.getSince().equals(""))
				initparam.setSince(format.format(timestamp) );					
			if(initparam.getUntil().equals(""))	
				initparam.setUntil(format.format(timestamp));
					

			scoreCardTableDTO = documenttracelastdaysserver.getScoreCardTableOfCompany(initparam);
			if (initparam.isPaginated()) {
				initparam.setQueryToCount(true);
				total = documenttracelastdaysserver.getCountScoreCardTableOfCompany(initparam);

				PageInfoDTO pageinfo = new PageInfoDTO();
				pageinfo.setPagenumber(initparam.getPageNumber());
				pageinfo.setRows(scoreCardTableDTO.getScorecards().length);
				pageinfo.setTotalrows(total);
				pageinfo.setTotalpages((total % initparam.getRows()) != 0 ? (total / initparam.getRows()) + 1 : (total / initparam.getRows()));

				scoreCardTableDTO.setPageInfoDTO(pageinfo);

			}
			return scoreCardTableDTO;

		} catch (Exception e) {
			e.printStackTrace();
			scoreCardTableDTO = new ScoreCardTableOfCompanyReturnDTO();
			scoreCardTableDTO.setStatuscode("1");
			scoreCardTableDTO.setStatusmessage("Error al construir la respuesta");
			return scoreCardTableDTO;
		}
	}

	public ScoreCardDTO getScoreCardOfCompany(String companyrut, String servicename) {
		// Metodo para el scorecard general, para la primera pantalla con gráficos
		ScoreCardDTO result = new ScoreCardDTO();
		Integer success = 0;
		Integer inprocess = 0;
		Integer error = 0;

		Hashtable<String, SiteProgress> siteprogressHash = new Hashtable<String, SiteProgress>();

		try {
			InternalScoreCardDTO[] internalsScoreCardDTO = documenttracelastdaysserver.getScordCardValuesForSupplier(companyrut, servicename);
			for (InternalScoreCardDTO internalScoreCardDTO : internalsScoreCardDTO) {

				String sitename = internalScoreCardDTO.getSitename();
				String retailname = internalScoreCardDTO.getRetailname();
				int cuantity = internalScoreCardDTO.getCount().intValue();
				String status = internalScoreCardDTO.getStatus();

				SiteProgress sp;

				if (!siteprogressHash.containsKey(sitename)) { // si el hash no contiene el sitio lo crea y agrega
					sp = new SiteProgress();

					if (status.equals("Recibido")) {
						success += cuantity;
						sp.setSuccess(cuantity);
					} else if (status.equals("Error")) {
						error += cuantity;
						sp.setError(cuantity);
					} else {
						inprocess += cuantity;
						sp.setInprogress(cuantity);
					}

					sp.setSitename(sitename);
					sp.setRetailname(retailname);
					siteprogressHash.put(sitename, sp);
				} else { // si el sitio ya esta creado lo modifica

					sp = siteprogressHash.get(sitename);

					if (status.equals("Recibido")) {
						success += cuantity;
						sp.setSuccess(cuantity);
					} else if (status.equals("Error")) {
						error += cuantity;
						sp.setError(cuantity);
					} else {
						inprocess += cuantity;
						sp.setInprogress(cuantity);
					}

					siteprogressHash.replace(sitename, sp);
				}
			}

			result.setSiteprogressarray((SiteProgress[]) siteprogressHash.values().toArray(new SiteProgress[siteprogressHash.size()]));

			result.setTotalerror(error);
			result.setTotalinprogress(inprocess);
			result.setTotalsucess(success);

			return result;

		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			e.printStackTrace();
			result.setStatuscode("1");
			result.setStatusmessage("error al construir la respuesta");
			return result;
		}

	}
	
	public ScoreCardSiteFilterDTO getAvailableSite(Long proveedor, String servicename) {	
		//filtro de sitios		
		ScoreCardSiteFilterDTO result = new ScoreCardSiteFilterDTO();
		try {
			SiteDTO[] sites =contractedserviceserver.getContractedServicesofCompanyRut(proveedor.toString(), servicename);
			result.setSites(sites);
			return result;
			
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {			
			e.printStackTrace();
			result.setStatuscode("1");
			result.setStatusmessage("error al revisar los contratos del proveedor "+proveedor);
			return result;
		}
	}	
	public ArrayList<RequestFilterForTicketDTO> getRequestFilter(String companyrut, int criteriafilter){
		ArrayList<RequestFilterForTicketDTO> result = new ArrayList<RequestFilterForTicketDTO>();
		
		if(criteriafilter == 1){
			ServiceFilterDTO services = getServices(companyrut);
			
			ServiceDTO[] servicefilters =services.getServiceforfilterArray();
			for (ServiceDTO serviceDTO : servicefilters) {
				RequestFilterForTicketDTO rfft = new RequestFilterForTicketDTO();
				rfft.setIds(serviceDTO.getId().longValue());//long
				rfft.setValues(serviceDTO.getName());//string
				result.add(rfft);
			}
			
		}else if( criteriafilter == 2){
			TicketStateTypeFilterDTO tickets = getTickesStates();
			TicketStateTypeDTO[] ticketsfilter = tickets.getTicketstatetype();
			for (TicketStateTypeDTO ticketStateTypeDTO : ticketsfilter) {
				RequestFilterForTicketDTO rfft = new RequestFilterForTicketDTO();
				rfft.setIds(ticketStateTypeDTO.getId().longValue());//long
				rfft.setValues(ticketStateTypeDTO.getName());//string
				result.add(rfft);				
			}
		}
		return result;
	}
	
	public ServiceFilterDTO getServices(String companyrut) {
		
		ServiceFilterDTO result = new ServiceFilterDTO();	
		ArrayList<ServiceDTO> services = new ArrayList<ServiceDTO>();
		
		try {			
			ContractedServiceOfRutDTO[] csddto = contractedserviceserver.getContractedServicesofCompany(companyrut);
			for (ContractedServiceOfRutDTO contractedServiceOfRutDTO : csddto) {
				ServiceDTO service = new ServiceDTO(); 
				service.setCode(contractedServiceOfRutDTO.getServicecode());
				service.setName(contractedServiceOfRutDTO.getServicename());
				service.setId(contractedServiceOfRutDTO.getServicekey());
				
				services.add(service);
			}
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {			
			result.setStatuscode("1");
			result.setStatusmessage("error al otener servicios del proveedor");
			return result;
		}
		result.setServiceforfilterArray((ServiceDTO[]) services.toArray(new ServiceDTO[services.size()]));
		return result;
	}
	
	public TicketStateTypeFilterDTO getTickesStates(){
		
		TicketStateTypeFilterDTO result = new TicketStateTypeFilterDTO();
		try {			
			result.setTicketstatetype(ticketstatetypeserver.getAllAsArray());
		} catch (OperationFailedException | NotFoundException e) {
			
			result.setStatuscode("1");
			result.setStatusmessage("error al construir la respuesta");
			return result;
		}
		return result;
	}
	
	public SitesFilterDTO getSites(String companyrut) {	
		//filtro de sitios		
		SitesFilterDTO result = new SitesFilterDTO();
		try {
			SiteDTO[] sites =siteserver.getSitesByCompanyRut(companyrut);
			result.setSites(sites);
			return result;
			
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {			
			e.printStackTrace();
			result.setStatuscode("1");
			result.setStatusmessage("error al revisar los contratos del proveedor "+companyrut);
			return result;
		}
	}	
	
	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO initparam){
		int total = 0;
		TicketReportResultDTO result = new TicketReportResultDTO();
		
			try {
				if(initparam.getFilterType().equals(new Integer(1))){
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					if(initparam.getSince().equals(""))
						initparam.setSince(format.format(timestamp) );					
					if(initparam.getUntil().equals(""))	
						initparam.setUntil(format.format(timestamp));	
				}
				
				result = ticketeventserver.getTicketReport(initparam);
				
				if (initparam.isPaginated()) {
					initparam.setQueryToCount(true);
					total = ticketeventserver.getCountTicketReport(initparam);
					
					PageInfoDTO pageinfo = new PageInfoDTO();
					pageinfo.setPagenumber(initparam.getPageNumber());
					pageinfo.setRows(result.getTickets().length);
					pageinfo.setTotalrows(total);
					pageinfo.setTotalpages((total % initparam.getRows()) != 0 ? (total / initparam.getRows()) + 1 : (total / initparam.getRows()));
					
					result.setPageInfoDTO(pageinfo);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				result.setStatuscode("1");
				result.setStatusmessage("error al otener servicios del proveedor");
				return result;
			}			
		return result;
	}
	

}