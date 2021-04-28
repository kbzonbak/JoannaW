package bbr.b2b.logistic.vev.report.classes;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;

public class DoUploadStockVeVResultDTO extends BaseResultsDTO {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
		
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
