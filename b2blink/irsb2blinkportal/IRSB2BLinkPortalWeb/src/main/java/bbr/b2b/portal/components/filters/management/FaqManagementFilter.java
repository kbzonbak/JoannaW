package bbr.b2b.portal.components.filters.management;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrModule;
//import cl.bbr.core.components.basics.BbrLineSeparator;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class FaqManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 5712042403953778345L;

	private BbrComboBox<ProfileDTO> cbx_Profiles = null ;
	private Button btn_FilterSearch = null;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public FaqManagementFilter(BbrModule parentModule)
	{
		super(parentModule);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void buttonClick(ClickEvent event) 
	{
		ProfileDTO selectedValue = cbx_Profiles.getSelectedValue();
		if(selectedValue != null)
		{
			BbrFilterEvent filterEvent = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
			filterEvent.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "profile")) ;
			filterEvent.setSecondaryCaption("- " + selectedValue.getName());
			filterEvent.setResultObject(selectedValue);
			dispatchBbrFilterEvent(filterEvent);	
		}
	}
	// ****************************************************************************************
	// ENDING SECTION 8		---->			OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		Label lbl_FilterCriteria = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "criteria"));
		lbl_FilterCriteria.addStyleName("label-criteria");

		cbx_Profiles = this.getProfilesComboBox();

		VerticalLayout mainLayout = new VerticalLayout();		
		mainLayout.setSizeFull();

		this.addStyleName("bbr-filter");
		this.setWidth("350px");
		this.setHeight("110px");

		FormLayout form = new FormLayout();
		form.setSizeFull();

		form.addComponent(cbx_Profiles);

		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);		
		btn_FilterSearch.setStyleName("btn-filter-search");
		mainLayout.addComponent(lbl_FilterCriteria);
		mainLayout.setMargin(false);
		mainLayout.addComponent(form);
		mainLayout.addComponent(btn_FilterSearch);
		mainLayout.setExpandRatio(form, 1F);
		mainLayout.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);
		this.addComponent(mainLayout);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<ProfileDTO> getProfilesComboBox() 
	{
		BbrComboBox<ProfileDTO> result = null;
		try 
		{
			ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getProfiles();
			if(profilesResult != null && profilesResult.getProfiles() != null && profilesResult.getProfiles().length > 0)
			{
				result = new BbrComboBox<ProfileDTO>(profilesResult.getProfiles());
				result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "profile"));
				result.setItemCaptionGenerator(ProfileDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.selectFirstItem();
				result.setWidth(100F, Unit.PERCENTAGE);
				
			}
		} 
		catch (BbrUserException e) 
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		} 
		catch (BbrSystemException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************
}
