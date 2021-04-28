package bbr.b2b.portal.components.login;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class LoginMoreInformation extends BbrWindow 
{
	public LoginMoreInformation(BbrUI parent) 
	{
		super(parent);
	}

	private static final long serialVersionUID = -2924756000851125461L;

	public void initializeView() 
	{
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"));
		this.setWidth("450px");
		this.setHeight("250px");
		this.setResizable(false);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		Label lblInfo = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "more_information_message"));
		lblInfo.setContentMode(ContentMode.HTML);
		lblInfo.addStyleName("lblInfo");		
		lblInfo.setWidth("100%");
		mainLayout.setSizeFull();		
		mainLayout.addComponent(lblInfo);
		mainLayout.setMargin(false);
		setContent(mainLayout);
	}

}
