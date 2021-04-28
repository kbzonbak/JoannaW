package bbr.b2b.portal.classes.utils.exagon.client.gson;

public class ReportSettingsGson {

	private String ReportPath;

	private String Id;

	private Boolean IsError;

	private FilterGson[] FilterItems;

	public FilterGson[] getFilterItems() {
		return FilterItems;
	}

	public String getId() {
		return Id;
	}

	public Boolean getIsError() {
		return IsError;
	}

	public String getReportPath() {
		return ReportPath;
	}

	public void setFilterItems(FilterGson[] filterItems) {
		FilterItems = filterItems;
	}

	public void setId(String id) {
		Id = id;
	}

	public void setIsError(Boolean isError) {
		IsError = isError;
	}

	public void setReportPath(String reportPath) {
		ReportPath = reportPath;
	}

}