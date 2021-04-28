package bbr.b2b.portal.classes.utils.exagon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SessionResponse")
public class SessionResponse {

	@XmlElement(name = "AppUrl")
	private String AppUrl;

	@XmlElement(name = "ApiKey")
	private String ApiKey;

	@XmlElement(name = "Id")
	private String Id;

	@XmlElement(name = "Page")
	private String Page;

	@XmlElement(name = "ApiAction")
	private String ApiAction;

	@XmlElement(name = "ExportType", nillable = true)
	private String ExportType;

	@XmlElement(name = "ShowTabs")
	private Boolean ShowTabs;

	@XmlElement(name = "ShowErrorDetail")
	private Boolean ShowErrorDetail;

	@XmlElement(name = "ReportSettings")
	private ReportSettings ReportSettings;

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

	public ReportSettings getReportSettings() {
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

	public void setReportSettings(ReportSettings reportSettings) {
		ReportSettings = reportSettings;
	}

	public void setShowErrorDetail(Boolean showErrorDetail) {
		ShowErrorDetail = showErrorDetail;
	}

	public void setShowTabs(Boolean showTabs) {
		ShowTabs = showTabs;
	}

}
