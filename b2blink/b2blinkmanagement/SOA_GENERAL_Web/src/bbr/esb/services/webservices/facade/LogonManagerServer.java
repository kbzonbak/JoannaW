package bbr.esb.services.webservices.facade;

import javax.ejb.EJB;
import javax.jws.WebService;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.services.data.classes.QuestionChallengeResultDTO;
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.services.webservices.interfaces.ILogonManagerServer;
import bbr.esb.users.data.classes.FunctionalityArrayResultDTO;

@WebService
public class LogonManagerServer implements ILogonManagerServer {

	@EJB
	private UserManagerLocal usermanagerlocal = null;

	
	public BaseResultDTO doChangePassword(String logid, String oldpassword, String newpassword) {
		return usermanagerlocal.doChangePassword(logid, oldpassword, newpassword);
	}

	public BaseResultDTO doChangeQuestionAndAnswerChallenge(Integer userid, String password, String newquestion, String newanswer) {
		return usermanagerlocal.doChangeQuestionAndAnswerChallenge(userid, password, newquestion, newanswer);
	}

	public LogonResultDTO doLogonUser(String logid, String password) {
		return usermanagerlocal.doLogonUser(logid, password);
	}

	public BaseResultDTO doValidateQuestionChallengeAnswer(String logid, String answer, boolean newpassword) {
		return usermanagerlocal.doValidateQuestionChallengeAnswer(logid, answer, newpassword);
	}

	public FunctionalityArrayResultDTO getFunctionalitiesByUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return usermanagerlocal.getFunctionalitiesByUserType(usertypeid);
	}

	public QuestionChallengeResultDTO getQuestionChallengeByUser(String logid) {
		return usermanagerlocal.getQuestionChallengeByUser(logid);
	}

	public LogonResultDTO getUserBySession(String sessionid) {
		return usermanagerlocal.getUserBySession(sessionid);
	}

}
