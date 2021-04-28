package bbr.b2b.portal.classes.utils.users.user_request;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.UserCompanyArrayResultDTO;
import bbr.b2b.users.report.classes.UserProfileArrayResultDTO;

public class UserRequestResultUtilsDTO extends BaseResultDTO
{
	private static final long			serialVersionUID		= 6971812021706154543L;
	private PositionResultDTO			positionResult			= null;
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
