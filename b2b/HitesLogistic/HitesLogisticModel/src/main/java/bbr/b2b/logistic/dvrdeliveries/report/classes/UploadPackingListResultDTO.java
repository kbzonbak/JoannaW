package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class UploadPackingListResultDTO extends BaseResultDTO {

	private Integer totalbulks;
	private Double totalunits;
	private LocalDateTime datingdatetime;
	private DvrDeliveryStateTypeDataDTO currentstatetype;
	private BaseResultDTO[] validationerrors;

	public Integer getTotalbulks() {
		return totalbulks;
	}

	public void setTotalbulks(Integer totalbulks) {
		this.totalbulks = totalbulks;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public LocalDateTime getDatingdatetime() {
		return datingdatetime;
	}

	public void setDatingdatetime(LocalDateTime datingdatetime) {
		this.datingdatetime = datingdatetime;
	}

	public DvrDeliveryStateTypeDataDTO getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(DvrDeliveryStateTypeDataDTO currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

}
