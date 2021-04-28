package bbr.b2b.portal.components.walldiary;

import java.io.InputStream;
import java.util.Properties;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;

public class StateInstock extends VerticalLayout 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 422110660891507871L;

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	public Properties getProperties(String resourceName) 
	{
		Properties result = null;
		InputStream inputStream ;
		try 
		{
			inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
			
			if (inputStream != null) 
			{
				result = new Properties();
				result.load(inputStream);
				inputStream.close();
			} 
		} 
		catch (Exception e) 
		{
			result = null ;
			e.printStackTrace();
		}
		
		return result;
	}

	
	public String getPropertyValue(String resourceName, String propertyName) 
	{
		String result = "";
		
		Properties properties = this.getProperties(resourceName);
		if(properties != null)
		{
			result = properties.getProperty(propertyName);
		}
		
		return result;
	}

	public StateInstock()
	{
		initializeControl();
	}
	
	private void initializeControl()
	{
		Component toolBar			= this.getToolBar();
				
		Label lblData = new Label("97,3%");				
		lblData.setStyleName("lbl-state-instock");
		Label lblDetailData = new Label("15,5 %");
		lblDetailData.setStyleName("lblDetail-state-instock");
		String res_logo = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "state_instock");
		Resource res = new ThemeResource(res_logo);
		Image imgData = new Image(null, res);
		imgData.setStyleName("image-state-instock");		
		
		HorizontalLayout content 		= new HorizontalLayout(lblData, imgData);
		content.setExpandRatio(lblData, 1F);
		content.setComponentAlignment(lblData, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(imgData, Alignment.MIDDLE_RIGHT);
		content.setWidth("100%");
		
		HorizontalLayout contentDetail 		= new HorizontalLayout(lblDetailData);
		contentDetail.setExpandRatio(lblDetailData, 1F);		
		contentDetail.setComponentAlignment(lblDetailData, Alignment.BOTTOM_LEFT);
		contentDetail.setWidth("100%");		
		
		this.addComponents(toolBar, content, contentDetail);		
		this.setExpandRatio(content, 1);		
		content.setMargin(true);
		content.setStyleName("content-standard-state");		
	}
	
	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("stats-header-instock");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "instock"));
		
		headerCaption.addStyleName(ValoTheme.LABEL_H4);
		headerCaption.addStyleName(ValoTheme.LABEL_COLORED);
		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	
		MenuBar leftMenuBar = new MenuBar();
		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		
		//leftMenuBar.addItem("", VaadinIcons.NEWSPAPER, null);

		result.addComponents(leftMenuBar, headerCaption);
		result.setExpandRatio(headerCaption, 1);
		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_LEFT);
		
		return result;
	}
}
