package bbr.b2b.portal.classes.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

@SuppressWarnings("serial")
@Theme("irsb2blinkportalweb")
public class GenericUI extends BbrUI 
{
	@Override
	protected void init(VaadinRequest request) 
	{
		String loginUrl = "";
		if(this.getPage().getWebBrowser().isTouchDevice())
		{
			loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "mlogin");
		}
		else
		{
			loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
		}
		this.goToURL(loginUrl);
		
	}
}
