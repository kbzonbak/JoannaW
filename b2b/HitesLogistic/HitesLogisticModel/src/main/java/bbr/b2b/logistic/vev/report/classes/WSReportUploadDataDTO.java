package bbr.b2b.logistic.vev.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class WSReportUploadDataDTO extends BaseResultDTO {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int itemstatus;
	private String itemmessage;
	private String codews;
	private String descriptionws;
	private Integer numRegsws;
	private Integer numErrorsws;
	private BaseResultDTO[] errorsws;
	
	public int getItemstatus() {
		return itemstatus;
	}
	public void setItemstatus(int itemstatus) {
		this.itemstatus = itemstatus;
	}
	public String getItemmessage() {
		return itemmessage;
	}
	public void setItemmessage(String itemmessage) {
		this.itemmessage = itemmessage;
	}
	public String getCodews() {
		return codews;
	}
	public void setCodews(String codews) {
		this.codews = codews;
	}
	public String getDescriptionws() {
		return descriptionws;
	}
	public void setDescriptionws(String descriptionws) {
		this.descriptionws = descriptionws;
	}
	public Integer getNumRegsws() {
		return numRegsws;
	}
	public void setNumRegsws(Integer numRegsws) {
		this.numRegsws = numRegsws;
	}
	public Integer getNumErrorsws() {
		return numErrorsws;
	}
	public void setNumErrorsws(Integer numErrorsws) {
		this.numErrorsws = numErrorsws;
	}
	public BaseResultDTO[] getErrorsws() {
		return errorsws;
	}
	public void setErrorsws(BaseResultDTO[] errorsws) {
		this.errorsws = errorsws;
	}
}
