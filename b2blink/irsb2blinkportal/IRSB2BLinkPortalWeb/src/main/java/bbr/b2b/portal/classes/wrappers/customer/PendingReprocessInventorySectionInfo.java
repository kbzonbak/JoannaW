package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;

public class PendingReprocessInventorySectionInfo
{
	private CompanyDataDTO					selectedCompany;
	private PendingReprocessInventoryDTO	selectedClient;

	public CompanyDataDTO getSelectedCompany()
	{
		return selectedCompany;
	}

	public void setSelectedCompany(CompanyDataDTO selectedCompany)
	{
		this.selectedCompany = selectedCompany;
	}

	public PendingReprocessInventoryDTO getSelectedClient()
	{
		return selectedClient;
	}

	public void setSelectedClient(PendingReprocessInventoryDTO selectedClient)
	{
		this.selectedClient = selectedClient;
	}

}
