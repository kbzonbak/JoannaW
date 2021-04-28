package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.util.List;

public class DvrOrderPdfReportDataDTO implements Serializable {

	private DvrOrderPdfDataDTO data;
	private List<DvrOrderPdfDetailDTO> details;
		
	public DvrOrderPdfDataDTO getData() {
		return data;
	}
	public void setData(DvrOrderPdfDataDTO data) {
		this.data = data;
	}
	public List<DvrOrderPdfDetailDTO> getDetails() {
		return details;
	}
	public void setDetails(List<DvrOrderPdfDetailDTO> details) {
		this.details = details;
	}
}
