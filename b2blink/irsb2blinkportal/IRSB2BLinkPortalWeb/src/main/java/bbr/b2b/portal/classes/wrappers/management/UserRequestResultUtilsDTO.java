package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.UserCompanyArrayResultDTO;
import bbr.b2b.users.report.classes.UserProfileArrayResultDTO;

public class UserRequestResultUtilsDTO
{
	private PositionResultDTO		positionResult		= null;
	private UserProfileArrayResultDTO	userProfileArrayResult	= null;
	private UserCompanyArrayResultDTO	userCompanyArrayResult	= null;

	public PositionResultDTO getPositionResult()
	{
		return positionResult;
	}

	public void setPositionResult(PositionResultDTO positionResult)
	{
		this.positionResult = positionResult;
	}

	public UserProfileArrayResultDTO getUserProfileArrayResult()
	{
		return userProfileArrayResult;
	}

	public void setUserProfileArrayResult(UserProfileArrayResultDTO userProfileArrayResult)
	{
		this.userProfileArrayResult = userProfileArrayResult;
	}

	public UserCompanyArrayResultDTO getUserCompanyArrayResult()
	{
		return userCompanyArrayResult;
	}

	public void setUserCompanyArrayResult(UserCompanyArrayResultDTO userCompanyArrayResult)
	{
		this.userCompanyArrayResult = userCompanyArrayResult;
	}

}
