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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.DetailsGenerator;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserDataDTO;
import cl.bbr.core.classes.basics.Rut;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrHSeparator;

public class UserDetailsGenerator implements DetailsGenerator<UserDataDTO> {

	private static final long serialVersionUID = -7307069774960676583L;


	@Override
	public Component apply(UserDataDTO user) 
	{
		HorizontalLayout layout = new HorizontalLayout();
		layout.setHeight(180, Unit.PIXELS);
		MarginInfo margin = new MarginInfo(false, true, true, true);
		layout.setMargin(margin);
		layout.setSpacing(true);

		String res_user = BbrUtils.getInstance().getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "default_user");
		Resource res = new ThemeResource(res_user);

		Image imgUser = new Image(null, res);
		imgUser.setWidth("100px");
		imgUser.setHeight("100px");

		Link linkEmail = new Link(user.getEmail(), new ExternalResource("mailto:" + user.getEmail()));

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.addComponents(imgUser,linkEmail);
		imageLayout.setExpandRatio(linkEmail, 1F);
		imageLayout.setComponentAlignment(imgUser, Alignment.TOP_CENTER);
		imageLayout.setComponentAlignment(linkEmail, Alignment.BOTTOM_CENTER);


		Label lblRut 			= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
		lblRut.addStyleName("bbr-user-details-field-label");

		Label lblUserName	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		lblUserName.addStyleName("bbr-user-details-field-label");

		Label lblUserLastName	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		lblUserLastName.addStyleName("bbr-user-details-field-label");

		Label lblUserState	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state"));
		lblUserState.addStyleName("bbr-user-details-field-label");
		
		Label lblUserType 		= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"));
		lblUserType.addStyleName("bbr-user-details-field-label");

		
		GridLayout layoutUserData = new GridLayout(7, 5);
		
		//Primera Columna de Datos
		layoutUserData.addComponent(lblRut, 0, 0);
		layoutUserData.addComponent(lblUserName, 0, 1);
		layoutUserData.addComponent(lblUserLastName, 0, 2);
		layoutUserData.addComponent(lblUserState, 0, 3);
		layoutUserData.addComponent(lblUserType, 0, 4);
		
		
		layoutUserData.addComponent(new Label(":"), 1, 0);
		layoutUserData.addComponent(new Label(":"), 1, 1);
		layoutUserData.addComponent(new Label(":"), 1, 2);
		layoutUserData.addComponent(new Label(":"), 1, 3);
		layoutUserData.addComponent(new Label(":"), 1, 4);

		Label lblRutValue 			= new Label((user.getPersonalid()!=null)?Rut.getFormattedRut(user.getPersonalid()):"-");
		lblRutValue.addStyleName("bbr-user-details-field-value");

		Label lblUserNameValue 		= new Label(user.getUsername());
		lblUserNameValue.addStyleName("bbr-user-details-field-value");

		Label lblUserLastNameValue 		= new Label(user.getLastname());
		lblUserLastNameValue.addStyleName("bbr-user-details-field-value");

		String state = (user.isStateindicator()==true)?I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active"):I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive");

		Label lblUserStateValue 		= new Label(state);
		lblUserStateValue.addStyleName("bbr-user-details-field-value");

		Label lblUserTypeValue 		= new Label(user.getUsertype());
		lblUserTypeValue.addStyleName("bbr-user-details-field-value");

		
		layoutUserData.addComponent(lblRutValue, 2, 0);
		layoutUserData.addComponent(lblUserNameValue, 2, 1);
		layoutUserData.addComponent(lblUserLastNameValue, 2, 2);
		layoutUserData.addComponent(lblUserStateValue, 2, 3);
		layoutUserData.addComponent(lblUserTypeValue, 2, 4);
		
		//Separador
		layoutUserData.addComponent(new BbrHSeparator("50px"), 3, 0);


		//Segunda Columna de Datos
		Label lblCompanyCode	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code"));
		lblCompanyCode.addStyleName("bbr-user-details-field-label");
		
		Label lblCompanySocialReason	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_social_reason"));
		lblCompanySocialReason.addStyleName("bbr-user-details-field-label");
		
		Label lblCreationDate	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date"));
		lblCreationDate.addStyleName("bbr-user-details-field-label");
		
		Label lblLastLogin	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date"));
		lblLastLogin.addStyleName("bbr-user-details-field-label");
		
		Label lblLastEdited 		= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date"));
		lblLastEdited.addStyleName("bbr-user-details-field-label");
		
		layoutUserData.addComponent(lblCompanyCode, 4, 0);
		layoutUserData.addComponent(lblCompanySocialReason, 4, 1);
		layoutUserData.addComponent(lblCreationDate, 4, 2);
		layoutUserData.addComponent(lblLastLogin, 4, 3);
		layoutUserData.addComponent(lblLastEdited, 4, 4);
		
		
		layoutUserData.addComponent(new Label(":"), 5, 0);
		layoutUserData.addComponent(new Label(":"), 5, 1);
		layoutUserData.addComponent(new Label(":"), 5, 2);
		layoutUserData.addComponent(new Label(":"), 5, 3);
		layoutUserData.addComponent(new Label(":"), 5, 4);

		Label lblCompanyCodeValue 		= new Label(user.getCompanycode());
		lblCompanyCodeValue.addStyleName("bbr-user-details-field-value");	

		Label lblCompanyNameValue 		= new Label(user.getCompanyname());
		lblCompanyNameValue.addStyleName("bbr-user-details-field-value");	
		
		Label lblCreationDateValue 	= new Label(BbrDateUtils.getInstance().toShortDateTime(user.getCreationdate()));
		lblCreationDateValue.addStyleName("bbr-user-details-field-value");

		Label lblLastLoginDateValue 	= new Label(BbrDateUtils.getInstance().toShortDateTime(user.getLastlogin()));
		lblLastLoginDateValue.addStyleName("bbr-user-details-field-value");
		
		Label lblUpdateDateValue 	= new Label(BbrDateUtils.getInstance().toShortDateTime(user.getLastupdate()));
		lblUpdateDateValue.addStyleName("bbr-user-details-field-value");
		
		layoutUserData.addComponent(lblCompanyCodeValue, 6, 0);
		layoutUserData.addComponent(lblCompanyNameValue, 6, 1);
		layoutUserData.addComponent(lblCreationDateValue, 6, 2);
		layoutUserData.addComponent(lblLastLoginDateValue, 6, 3);
		layoutUserData.addComponent(lblUpdateDateValue, 6, 4);
		
		layout.addComponents(imageLayout,layoutUserData);
		layout.setComponentAlignment(imageLayout, Alignment.MIDDLE_LEFT);
		layout.setComponentAlignment(layoutUserData, Alignment.MIDDLE_RIGHT);



		return layout;
	}
}
