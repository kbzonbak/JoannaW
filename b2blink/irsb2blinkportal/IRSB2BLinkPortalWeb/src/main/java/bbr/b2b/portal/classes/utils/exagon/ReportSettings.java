package bbr.b2b.portal.classes.utils.exagon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReportSettings")
public class ReportSettings {

	@XmlElement(name = "ReportPath", nillable = true)
	private String ReportPath;

	@XmlElement(name = "Id", nillable = true)
	private String Id;

	@XmlElement(name = "IsError", nillable = true)
	private String IsError;
	
	@XmlElement(name = "FilterItems")
	private Filter[] FilterItems;

	public String getId() {
		return Id;
	}

	public String getIsError() {
		return IsError;
	}
	
	public String getReportPath() {
		return ReportPath;
	}

	public void setId(String id) {
		Id = id;
	}

	public void setIsError(String isError) {
		IsError = isError;
	}

	public void setReportPath(String reportPath) {
		ReportPath = reportPath;
	}

}