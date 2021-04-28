package bbr.esb.services.webservices.facade;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.jws.WebService;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.DocumentsToResendInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardSiteFilterDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyReturnDTO;
import bbr.esb.events.data.classes.SitesFilterDTO;
import bbr.esb.events.data.classes.TicketReportInitParamDTO;
import bbr.esb.events.data.classes.TicketReportResultDTO;
import bbr.esb.events.data.classes.TicketStateTypeDTO;
import bbr.esb.events.data.classes.TicketStateTypeFilterDTO;
import bbr.esb.services.data.classes.RequestFilterForTicketDTO;
import bbr.esb.services.data.classes.ServiceFilterDTO;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.managers.ScoreCardManagerLocal;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.services.webservices.interfaces.IScoreCardManagerServer;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.facade.CompanyServerLocal;

@WebService
public class ScoreCardManagerServer implements IScoreCardManagerServer {
	
	
	@EJB
	private ScoreCardManagerLocal scordcardmanagerlocal = null;
	
	@EJB
	private ServiceManagerLocal servicemanagerlocal = null;
	
	@EJB
	private ServiceServerLocal serviceserver = null;
	
	@EJB
	private CompanyServerLocal companyserver = null;
	
	

	@Override
	public ScoreCardDTO getScoreCardOfCompany(String companyrut, String servicename) {		
		return scordcardmanagerlocal.getScoreCardOfCompany(companyrut,  servicename);
	}

	@Override
	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam){
		return scordcardmanagerlocal.getScoreCardTableOfCompany(initparam);
	}
	
	public ScoreCardSiteFilterDTO getAvailableSite(Long proveedor, String servicename) {	
		return scordcardmanagerlocal.getAvailableSite(proveedor,servicename);
	}
	
	public 	BaseResultDTO doAddServiceEventByContracted(DocumentsToResendInitParamDTO documentstoresend ) {
		return servicemanagerlocal.doAddServiceEventByContracted(documentstoresend);
		
	}
	
	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.getAllAsArrayOrdered(new OrderCriteriaDTO("name", true));
	}
	
	public ServiceFilterDTO getServices(String companyrut) {
		return scordcardmanagerlocal.getServices(companyrut);
	}
	
	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO initparam){
		return scordcardmanagerlocal.getTicketReport(initparam);
	}

	public TicketStateTypeFilterDTO getTickesStates(){
		return scordcardmanagerlocal.getTickesStates();
	}

	public ArrayList<RequestFilterForTicketDTO> getRequestFilter(String companyrut, int criteriafilter){
		return scordcardmanagerlocal.getRequestFilter(companyrut, criteriafilter);
	}
	
	public SitesFilterDTO getSites(String companyrut){
		return scordcardmanagerlocal.getSites(companyrut);
	}
	
}
