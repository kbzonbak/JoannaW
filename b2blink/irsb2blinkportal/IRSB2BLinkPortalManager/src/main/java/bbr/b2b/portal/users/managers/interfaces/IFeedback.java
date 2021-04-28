package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.portal.wrapper.FeedbackItemDTO;
import bbr.b2b.users.report.classes.UserDTO;


public interface IFeedback extends IGenericEJBInterface{

	public BaseResultDTO sendFeedback(UserDTO user, FeedbackItemDTO feedbackItem);
	
}
