package bbr.esb.services.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class QuestionChallengeResultDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1684648401591630877L;

	private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
