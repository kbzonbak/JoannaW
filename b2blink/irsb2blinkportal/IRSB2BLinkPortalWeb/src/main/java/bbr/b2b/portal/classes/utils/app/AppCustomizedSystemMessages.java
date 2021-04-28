package bbr.b2b.portal.classes.utils.app;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

public class AppCustomizedSystemMessages implements SystemMessagesProvider {
	/**
	* 
	*/
	private static final long serialVersionUID = 2995187788077342056L;

	@Override
	public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
		CustomizedSystemMessages messages = new CustomizedSystemMessages();
		messages.setCommunicationErrorNotificationEnabled(false);
		return messages;
	}
}