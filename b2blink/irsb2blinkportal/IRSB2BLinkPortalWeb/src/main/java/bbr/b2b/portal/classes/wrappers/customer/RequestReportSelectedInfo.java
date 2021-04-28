package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.users.report.classes.CompanyDataDTO;

public class RequestReportSelectedInfo
{

	CompanyDataDTO							selectedProviderFilterSection					= null;
	RequestReportFilterSectionInfo			selectedRequestReportFilterSection				= null;
	RequestReportPeriodFilterSectionInfo	selectedRequestReportPeriodFilterSection		= null;
	RequestClientReportFilterSectionInfo	selectedRequestClientReportFilterSectionInfo	= null;

	public CompanyDataDTO getSelectedProviderFilterSection()
	{
		return selectedProviderFilterSection;
	}

	public void setSelectedProviderFilterSection(CompanyDataDTO selectedProviderFilterSection)
	{
		this.selectedProviderFilterSection = selectedProviderFilterSection;
	}

	public RequestReportFilterSectionInfo getSelectedRequestReportFilterSection()
	{
		return selectedRequestReportFilterSection;
	}

	public void setSelectedRequestReportFilterSection(RequestReportFilterSectionInfo selectedRequestReportFilterSection)
	{
		this.selectedRequestReportFilterSection = selectedRequestReportFilterSection;
	}

	public RequestReportPeriodFilterSectionInfo getSelectedRequestReportPeriodFilterSection()
	{
		return selectedRequestReportPeriodFilterSection;
	}

	public void setSelectedRequestReportPeriodFilterSection(RequestReportPeriodFilterSectionInfo selectedRequestReportPeriodFilterSection)
	{
		this.selectedRequestReportPeriodFilterSection = selectedRequestReportPeriodFilterSection;
	}

	public RequestClientReportFilterSectionInfo getSelectedRequestClientReportFilterSectionInfo()
	{
		return selectedRequestClientReportFilterSectionInfo;
	}

	public void setSelectedRequestClientReportFilterSectionInfo(RequestClientReportFilterSectionInfo selectedRequestClientReportFilterSectionInfo)
	{
		this.selectedRequestClientReportFilterSectionInfo = selectedRequestClientReportFilterSectionInfo;
	}

}
