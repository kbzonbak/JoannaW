package bbr.b2b.portal.classes.utils.exagon.client.gson;

public class SessionGson {

	private String AppUrl;

	private String ApiKey;

	private String Id;

	private String Page;

	private String ApiAction;

	private String ExportType;

	private Boolean ShowTabs;

	private Boolean ShowErrorDetail;

	private ReportSettingsGson ReportSettings;

	public String getApiAction() {
		return ApiAction;
	}

	public String getApiKey() {
		return ApiKey;
	}

	public String getAppUrl() {
		return AppUrl;
	}

	public String getExportType() {
		return ExportType;
	}

	public String getId() {
		return Id;
	}

	public String getPage() {
		return Page;
	}

	public ReportSettingsGson getReportSettings() {
		return ReportSettings;
	}

	public Boolean getShowErrorDetail() {
		return ShowErrorDetail;
	}

	public Boolean getShowTabs() {
		return ShowTabs;
	}

	public void setApiAction(String apiAction) {
		ApiAction = apiAction;
	}

	public void setApiKey(String apiKey) {
		ApiKey = apiKey;
	}

	public void setAppUrl(String appUrl) {
		AppUrl = appUrl;
	}

	public void setExportType(String exportType) {
		ExportType = exportType;
	}

	public void setId(String id) {
		Id = id;
	}

	public void setPage(String page) {
		Page = page;
	}

	public void setReportSettings(ReportSettingsGson reportSettings) {
		ReportSettings = reportSettings;
	}

	public void setShowErrorDetail(Boolean showErrorDetail) {
		ShowErrorDetail = showErrorDetail;
	}

	public void setShowTabs(Boolean showTabs) {
		ShowTabs = showTabs;
	}

}
