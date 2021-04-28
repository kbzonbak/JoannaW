package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.customer.report.classes.PendingReprocessSalesDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;

public class PendingReprocessSalesSectionInfo
{
	private CompanyDataDTO				selectedCompany;
	private PendingReprocessSalesDTO	selectedClient;

	public CompanyDataDTO getSelectedCompany()
	{
		return selectedCompany;
	}

	public void setSelectedCompany(CompanyDataDTO selectedCompany)
	{
		this.selectedCompany = selectedCompany;
	}

	public PendingReprocessSalesDTO getSelectedClient()
	{
		return selectedClient;
	}

	public void setSelectedClient(PendingReprocessSalesDTO selectedClient)
	{
		this.selectedClient = selectedClient;
	}

}
