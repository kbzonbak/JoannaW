package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyReportDataDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.basics.Rut;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;

public class CompanyDetailsGenerator implements com.vaadin.ui.components.grid.DetailsGenerator<CompanyReportDataDTO> {

	private static final long serialVersionUID = -7307069774960676583L;


	@Override
	public Component apply(CompanyReportDataDTO company) 
	{
		Panel resultPanel = new Panel(); 
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setHeight(100, Unit.PERCENTAGE);
		
		//rowReference.getGrid().scrollTo(rowReference.getRow(), ScrollDestination.START);


		if(company.getMasterUsers() != null && company.getMasterUsers().length > 0)
		{
			for (UserDTO user : company.getMasterUsers()) 
			{
				HorizontalLayout layout = new HorizontalLayout();
				layout.setHeight(180, Unit.PIXELS);
				MarginInfo margin = new MarginInfo(false, true, true, true);
				layout.setMargin(margin);
				layout.setSpacing(true);
				
				
				String fullUserName = user.getName() + " " + user.getLastname();
				Label lblUserName = new Label(fullUserName);
				lblUserName.setWidthUndefined();
				lblUserName.addStyleName("bbr-user-details-header-label");

				String res_user = BbrUtils.getInstance().getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "default_user");
				Resource res = new ThemeResource(res_user);

				Image imgUser = new Image(null, res);
				imgUser.setWidth("100px");
				imgUser.setHeight("100px");

				Link linkEmail = new Link(user.getEmail(), new ExternalResource("mailto:" + user.getEmail()));

				VerticalLayout imageLayout = new VerticalLayout();
				imageLayout.setWidth("150px");
				imageLayout.addComponents(imgUser,lblUserName,linkEmail);
				imageLayout.setExpandRatio(lblUserName, 1F);
				imageLayout.setComponentAlignment(imgUser, Alignment.TOP_CENTER);
				imageLayout.setComponentAlignment(lblUserName, Alignment.MIDDLE_CENTER);
				imageLayout.setComponentAlignment(linkEmail, Alignment.BOTTOM_CENTER);


				Label lblRut 			= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "rut"));
				lblRut.addStyleName("bbr-user-details-field-label");
				
				Label lblUserType 		= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"));
				lblUserType.addStyleName("bbr-user-details-field-label");
				
				Label lblUserPosition 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"));
				lblUserPosition.addStyleName("bbr-user-details-field-label");

				Label lblUserPhone 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_phone"));
				lblUserPhone.addStyleName("bbr-user-details-field-label");

				Label lblCreationDate 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date"));
				lblCreationDate.addStyleName("bbr-user-details-field-label");
				
				Label lblUpdateDate 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date"));
				lblUpdateDate.addStyleName("bbr-user-details-field-label");
				
				GridLayout layoutUserData = new GridLayout(3, 6);
				layoutUserData.addComponent(lblRut, 0, 0);
				layoutUserData.addComponent(lblUserType, 0, 1);
				layoutUserData.addComponent(lblUserPosition, 0, 2);
				layoutUserData.addComponent(lblUserPhone, 0, 3);
				layoutUserData.addComponent(lblCreationDate, 0, 4);
				layoutUserData.addComponent(lblUpdateDate, 0, 5);
				
				layoutUserData.addComponent(new Label(":"), 1, 0);
				layoutUserData.addComponent(new Label(":"), 1, 1);
				layoutUserData.addComponent(new Label(":"), 1, 2);
				layoutUserData.addComponent(new Label(":"), 1, 3);
				layoutUserData.addComponent(new Label(":"), 1, 4);
				layoutUserData.addComponent(new Label(":"), 1, 5);

				Label lblRutValue 			= new Label((user.getPersonalid()!=null)?Rut.getFormattedRut(user.getPersonalid()):"-");
				lblRutValue.addStyleName("bbr-user-details-field-value");

				Label lblUserTypeValue 		= new Label(company.getCompanyType());
				lblUserTypeValue.addStyleName("bbr-user-details-field-value");
				
				Label lblUserPositionValue 	= new Label(user.getPosition());
				lblUserPositionValue.addStyleName("bbr-user-details-field-value");

				Label lblUserPhoneValue 	= new Label(user.getPhone());
				lblUserPhoneValue.addStyleName("bbr-user-details-field-value");
				
				Label lblCreationDateValue 	= new Label(BbrDateUtils.getInstance().toShortDateTime(user.getCreation()));
				lblCreationDateValue.addStyleName("bbr-user-details-field-value");
				
				Label lblUpdateDateValue 	= new Label(BbrDateUtils.getInstance().toShortDateTime(user.getLastupdate()));
				lblUpdateDateValue.addStyleName("bbr-user-details-field-value");
								
				layoutUserData.addComponent(lblRutValue, 2, 0);
				layoutUserData.addComponent(lblUserTypeValue, 2, 1);
				layoutUserData.addComponent(lblUserPositionValue, 2, 2);
				layoutUserData.addComponent(lblUserPhoneValue, 2, 3);
				layoutUserData.addComponent(lblCreationDateValue, 2, 4);
				layoutUserData.addComponent(lblUpdateDateValue, 2, 5);
				
				layout.addComponents(imageLayout,layoutUserData);
				layout.setComponentAlignment(imageLayout, Alignment.MIDDLE_LEFT);
				layout.setComponentAlignment(layoutUserData, Alignment.MIDDLE_RIGHT);
				
				
				mainLayout.addComponent(layout);
				
			}
			
			resultPanel.setContent(mainLayout);
			resultPanel.getScrollTop();
		}
		else
		{
			resultPanel.setContent(new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_master_users")));
		}
		

		return resultPanel;
	}
}
