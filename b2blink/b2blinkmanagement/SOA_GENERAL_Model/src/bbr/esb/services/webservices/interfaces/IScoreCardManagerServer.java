package bbr.esb.services.webservices.interfaces;

import java.util.ArrayList;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.ScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardSiteFilterDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyReturnDTO;
import bbr.esb.events.data.classes.SitesFilterDTO;
import bbr.esb.events.data.classes.TicketReportInitParamDTO;
import bbr.esb.events.data.classes.TicketReportResultDTO;
import bbr.esb.events.data.classes.TicketStateTypeFilterDTO;
import bbr.esb.services.data.classes.RequestFilterForTicketDTO;
import bbr.esb.services.data.classes.ServiceFilterDTO;
import bbr.esb.users.data.classes.CompanyDTO;

public interface IScoreCardManagerServer {
	
	public ScoreCardDTO getScoreCardOfCompany(String companyrut, String servicename);
	
	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam);
	
	public ScoreCardSiteFilterDTO getAvailableSite(Long proveedor, String servicename);
	
	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public ServiceFilterDTO getServices(String companyrut);
	
	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO tickets);
	
	public TicketStateTypeFilterDTO getTickesStates();
	
	public SitesFilterDTO getSites(String companyrut);
	
	
	
}	