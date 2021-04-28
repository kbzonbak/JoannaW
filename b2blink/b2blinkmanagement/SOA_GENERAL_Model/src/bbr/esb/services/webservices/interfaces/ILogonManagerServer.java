package bbr.esb.services.webservices.interfaces;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.services.data.classes.QuestionChallengeResultDTO;
import bbr.esb.users.data.classes.FunctionalityArrayResultDTO;

public interface ILogonManagerServer {

	public BaseResultDTO doChangePassword(String logid, String oldpassword, String newpassword);

	public BaseResultDTO doChangeQuestionAndAnswerChallenge(Integer userid, String password, String newquestion, String newanswer);

	public LogonResultDTO doLogonUser(String logid, String password);

	public BaseResultDTO doValidateQuestionChallengeAnswer(String logid, String answer, boolean newpassword);

	public FunctionalityArrayResultDTO getFunctionalitiesByUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public QuestionChallengeResultDTO getQuestionChallengeByUser(String logid);

	public LogonResultDTO getUserBySession(String sessionid);

}