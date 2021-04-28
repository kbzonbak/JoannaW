package bbr.b2b.regional.logistic.evaluations.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class EvaluationReportDataResultDTO extends BaseResultDTO{

	private Long datingnumber;
	private double score;
	private String comment;
	private ReceptionErrorReportDataDTO[] receptionerrors;
	
	public Long getDatingnumber() {
		return datingnumber;
	}
	public void setDatingnumber(Long datingnumber) {
		this.datingnumber = datingnumber;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ReceptionErrorReportDataDTO[] getReceptionerrors() {
		return receptionerrors;
	}
	public void setReceptionerrors(ReceptionErrorReportDataDTO[] receptionerrors) {
		this.receptionerrors = receptionerrors;
	}
	
	
}
