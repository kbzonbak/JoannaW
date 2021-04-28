package bbr.b2b.portal.components.walldiary;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class NewsBanner extends BbrWindow 
{
	public NewsBanner(BbrUI parent) 
	{
		super(parent);
	}

	private static final long serialVersionUID = -2924756000851125461L;

	public void initializeView() 
	{
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"));
		this.setWidth("600px");
		this.setHeight("370px");
		this.setResizable(false);

		// Main Layout
		String res_Float = BbrUtilsResources.PATH_BASE_IMAGES_BASIC + "banners-GrupoSocofar.png";
		Resource resFloat = new ThemeResource(res_Float);
		Image image = new Image(null, resFloat);
		
		VerticalLayout mainLayout = new VerticalLayout();
		
		mainLayout.setSizeFull();		
		mainLayout.addComponent(image);
		setContent(mainLayout);
	}

}
