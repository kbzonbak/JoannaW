package bbr.esb.services.interfaces;

import java.util.ArrayList;

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

public interface IScoreCardManager {
	
	public ScoreCardDTO getScoreCardOfCompany(String companyrut, String servicename);
	
	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam);
	
	public ScoreCardSiteFilterDTO getAvailableSite(Long proveedor, String servicename);

	public ServiceFilterDTO getServices(String companyrut);
	
	public TicketStateTypeFilterDTO getTickesStates();
	
	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO tickets);	
	
	public ArrayList<RequestFilterForTicketDTO> getRequestFilter(String companyrut, int criteriafilter);
	
	public SitesFilterDTO getSites(String companyrut);
}
