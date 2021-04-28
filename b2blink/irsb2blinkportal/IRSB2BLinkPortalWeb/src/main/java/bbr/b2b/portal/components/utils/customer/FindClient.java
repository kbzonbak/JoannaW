package bbr.b2b.portal.components.utils.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.customer.report.classes.ClientDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindClient extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long				serialVersionUID	= 3247626779164695209L;
	// Components
	private BbrComboBox<SearchCriterion>	cbx_Criteria		= null;
	private BbrTextField					txt_Value			= null;
	private FormLayout						siteForm			= null;
	private BbrAdvancedGrid<ClientDTO>		dgd_Site			= null;
	private Button							btn_Select			= null;
	// Variables
	private List<ClientDTO>					listOfSites			= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public FindClient(BbrUI parent, List<ClientDTO> listOfSites)
	{
		super(parent);
		this.listOfSites = listOfSites;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		cbx_Criteria = this.getSearchCriterionComboBox();

		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		txt_Value.setMaxLength(50);
		txt_Value.setWidth("100%");
		txt_Value.addStyleName("bbr-filter-fields");

		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable) e -> search_clickHandler(e));

		// Form Component
		siteForm = new FormLayout();
		siteForm.addComponents(cbx_Criteria, txt_Value, btn_Search);

		dgd_Site = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeGridColumns();
		dgd_Site.addStyleName("report-grid");
		dgd_Site.setWidth("100%");
		dgd_Site.addSelectionListener(e -> toggleSelectBtn());

		// Buttons Layout
		this.btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"));
		this.btn_Select.addClickListener((ClickListener & Serializable) e -> select_clickHandler(e));
		this.btn_Select.setStyleName("primary");
		this.btn_Select.addStyleName("btn-generic");
		this.btn_Select.setWidth("140px");
		this.btn_Select.setEnabled(false);

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.addClickListener(e -> close());
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("140px");

		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsLayout.setWidth(100, Unit.PERCENTAGE);
		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");

		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addComponents(dgd_Site, buttonsLayout);
		gridLayout.setExpandRatio(dgd_Site, 1F);
		gridLayout.setSpacing(true);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false);

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(siteForm, gridLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(gridLayout, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.setMargin(false);

		// Main Windows
		this.setWidth("450px");
		this.setHeight("480px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_sites"));
		this.setContent(mainLayout);

		txt_Value.focus();
	}

	private void selectSite(ClientDTO site)
	{
		if (site != null)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
			event.setResultObject(site);
			dispatchBbrEvent(event);
			this.close();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "site_required"));
		}
	}

	private void initializeGridColumns()
	{
		this.dgd_Site.addColumn(ClientDTO::getClientname).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "description"));
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void toggleSelectBtn()
	{
		boolean isSelectEnabled = (this.dgd_Site.getSelectedItems() != null && this.dgd_Site.getSelectedItems().size() > 0);

		this.btn_Select.setEnabled(isSelectEnabled);
	}

	private void select_clickHandler(ClickEvent e)
	{
		if (dgd_Site.getSelectedItems() != null && dgd_Site.getSelectedItems().size() > 0)
		{
			ClientDTO site = (ClientDTO) dgd_Site.getSelectedItems().iterator().next();
			this.selectSite(site);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "site_required"));
		}
	}

	private void search_clickHandler(ClickEvent e)
	{
		String errorMessage = validateData();
		if (errorMessage == null || errorMessage.length() <= 0)
		{
			try
			{
				String value = txt_Value.getValue();
				if (this.listOfSites.size() > 0)
				{
					List<ClientDTO> items = listOfSites.stream()
							.sorted(Comparator.comparing(ClientDTO::getClientname))
							.filter(str -> str.getClientname().contains(value.toUpperCase().trim()))
							.collect(Collectors.toList());

					this.dgd_Site.setItems(items);
				}
				else
				{
					this.dgd_Site.setItems(new ArrayList<ClientDTO>());
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getSearchSiteCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);
		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addStyleName("bbr-filter-fields");
		result.setTextInputAllowed(false);
		result.setWidth("100%");

		result.addValueChangeListener(new ValueChangeListener<SearchCriterion>()
		{
			private static final long serialVersionUID = -885062614408629961L;

			@Override
			public void valueChange(ValueChangeEvent<SearchCriterion> event)
			{
				if (cbx_Criteria.getSelectedValue() != null)
				{
					txt_Value.setValue("");
					txt_Value.focus();
				}
			}
		});

		return result;
	}

	private String validateData()
	{
		String result = "";
		String value = txt_Value.getValue().trim();
		if (value.length() < 3)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
		}
		else if (value.length() > 50)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search");
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
