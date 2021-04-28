//package bbr.b2b.portal.components.filter_section.logistic;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.vaadin.data.HasValue.ValueChangeEvent;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//
//import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.classes.wrappers.logistic.DeliveryLocation;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.utils.logistic.FindLocation;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.users.report.classes.LocationArrayResultDTO;
//import bbr.b2b.users.report.classes.LocationDTO;
//import bbr.b2b.users.report.classes.LocationDataResultDTO;
//import cl.bbr.core.classes.container.BbrSectionEvent;
//import cl.bbr.core.classes.container.BbrVerticalSection;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrUI;
//
//public class DeliveryLocationFilterSection extends BbrVerticalSection<DeliveryLocation>
//{
//	// =====================================================================================================================================
//	// BEGINNING SECTION ----> PROPERTIES
//	// =====================================================================================================================================
//	// Constants
//	private static final long			serialVersionUID				= -2707652819481964570L;
//	private static final int			MAX_LOCALS						= 20;
//	private static final String			BBR_PANEL_SPACE_STYLE			= "bbr-panel-space";
//	private static final String			BBR_FILTER_LABEL_HEADER_STYLE	= "bbr-filter-label-header";
//	private static final String			BBR_FILTER_SECTIONS_STYLE		= "bbr-filter-sections";
//	private static final String			BBR_FILTER_FIELDS_STYLE			= "bbr-filter-fields";
//	private static final String			LINK_FILTER_BUTTON_STYLE		= "link-filter-button";
//	private static final String			SEARCH_PANEL_STYLE				= "search-panel";
//	// Components
//	private BbrComboBox<LocationDTO>	cbx_Locations					= null;
//	private HorizontalLayout			pnlSearchPanel					= null;
//	// Variables
//	private ArrayList<LocationDTO>		locations						= null;
//	private boolean						hasOptionAll					= false;
//	private Boolean						hasAllLocals					= null;
//	private boolean						isByOC							= false;					// Show_all_locals
//
//	public void setOptionAll(boolean hasOptionAll)
//	{
//		this.hasOptionAll = hasOptionAll;
//	}
//
//	// =====================================================================================================================================
//	// ENDING SECTION ----> PROPERTIES
//	// =====================================================================================================================================
//
//	// =====================================================================================================================================
//	// BEGINNING SECTION ----> CONSTRUCTORS
//	// =====================================================================================================================================
//	public DeliveryLocationFilterSection(BbrUI parent)
//	{
//		this(parent, false);
//	}
//
//	public DeliveryLocationFilterSection(BbrUI parent, boolean isByOEDvr)
//	{
//		super(parent);
//		this.isByOC = isByOEDvr;
//	}
//
//	// =====================================================================================================================================
//	// ENDING SECTION ----> CONSTRUCTORS
//	// =====================================================================================================================================
//
//	// =====================================================================================================================================
//	// BEGINNING SECTION ----> OVERRIDDEN METHODS
//	// =====================================================================================================================================
//
//	@Override
//	public void initializeView()
//	{
//		// Header
//		Label lbl_Locations = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_location"));
//		lbl_Locations.addStyleName(BBR_PANEL_SPACE_STYLE);
//		lbl_Locations.setWidth("220px");
//
//		this.pnlSearchPanel = new HorizontalLayout();
//		this.pnlSearchPanel.addStyleName(SEARCH_PANEL_STYLE);
//		this.pnlSearchPanel.setWidth("160px");
//
//		HorizontalLayout pnlHeader = new HorizontalLayout();
//		pnlHeader.addStyleName(BBR_FILTER_LABEL_HEADER_STYLE);
//		pnlHeader.addComponents(lbl_Locations, this.pnlSearchPanel);
//		pnlHeader.setExpandRatio(this.pnlSearchPanel, 1F);
//		pnlHeader.setWidth("100%");
//
//		Label lbl_Location = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "local"));
//		lbl_Location.setWidth("120px");
//
//		this.cbx_Locations = this.updateLocationComboBox();
//
//		HorizontalLayout pnlLocationSubsection = new HorizontalLayout();
//		pnlLocationSubsection.setWidth("100%");
//		pnlLocationSubsection.addComponents(lbl_Location, this.cbx_Locations);
//		pnlLocationSubsection.setExpandRatio(this.cbx_Locations, 1F);
//		pnlLocationSubsection.addStyleName(BBR_PANEL_SPACE_STYLE);
//
//		this.setWidth("400px");
//		this.addStyleName(BBR_FILTER_SECTIONS_STYLE);
//		this.addStyleName(BBR_PANEL_SPACE_STYLE);
//		this.setSpacing(false);
//		this.addComponents(pnlHeader, pnlLocationSubsection);
//	}
//
//	@Override
//	public DeliveryLocation getSectionResult()
//	{
//		LocationDTO selectedLocation = this.cbx_Locations.getSelectedValue();
//		DeliveryLocation result = new DeliveryLocation(selectedLocation);
//		return result;
//	}
//
//	@Override
//	public String validateData()
//	{
//		String result = null;
//		LocationDTO selectedFormat = this.cbx_Locations.getSelectedValue();
//
//		if (selectedFormat == null || selectedFormat.getId() == null)
//		{
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_location_required");
//		}
//		return result;
//	}
//
//	// =====================================================================================================================================
//	// ENDING SECTION ----> OVERRIDDEN METHODS
//	// =====================================================================================================================================
//
//	// =====================================================================================================================================
//	// BEGINNING SECTION ----> EVENT HANDLERS
//	// =====================================================================================================================================
//	private void btn_SearchLocal_clickHandler(ClickEvent e)
//	{
//		if (e != null)
//		{
//			FindLocation winFindLocal = new FindLocation(this.getBbrUIParent(), this.isByOC);
//			winFindLocal.initializeView();
//			winFindLocal.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.locationSelectedHandler(bbrEvent));
//			winFindLocal.open(true);
//		}
//	}
//
//	private void locationSelectedHandler(BbrEvent bbrEvent)
//	{
//		if (bbrEvent != null && bbrEvent.getResultObject() != null)
//		{
//			LocationDTO location = (LocationDTO) bbrEvent.getResultObject();
//			this.updateLocation(location);
//		}
//	}
//
//	// =====================================================================================================================================
//	// ENDING SECTION ----> EVENT HANDLERS
//	// =====================================================================================================================================
//
//	// =====================================================================================================================================
//	// BEGINNING SECTION ----> PRIVATE METHODS
//	// =====================================================================================================================================
//
//	private void updateLocation(LocationDTO location)
//	{
//		if (location != null)
//		{
//			this.cbx_Locations.setSelectedItem(location);
//		}
//	}
//
//	private void location_changeHandler(ValueChangeEvent<LocationDTO> e)
//	{
//		if (DeliveryLocationFilterSection.this.cbx_Locations != null && DeliveryLocationFilterSection.this.cbx_Locations.getSelectedValue() != null && e != null)
//		{
//			BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_PROVIDER);
//			bbrEvent.setResultObject(DeliveryLocationFilterSection.this.cbx_Locations.getSelectedValue());
//			dispatchBbrSectionEvent(bbrEvent);
//		}
//	}
//
//	private BbrComboBox<LocationDTO> updateLocationComboBox()
//	{
//		BbrComboBox<LocationDTO> result = null;
//		try
//		{
//			result = this.initializeCombobox();
//
//			LocationDataResultDTO locationsResult = null;
//			ArrayList<LocationDTO> locationsList = null;
//
//			BbrError error = null;
//			if (!isByOC)
//			{
//				locationsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).getMostUsedLocationsByUserId(this.getBbrUIParent().getUser().getId());
//				error = ErrorsMngr.getInstance().getError(locationsResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
//			}
//			else
//			{
//				// OEP SHOW ALL LOCALS
//				LocationArrayResultDTO locationsNotAssigned = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(this.getBbrUIParent()).findAllLocalsAssigned(this.getBbrUIParent().getUser().getId(), 1, 100, false, false, null);
//				LocationArrayResultDTO locationsAssigned = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(this.getBbrUIParent()).findAllLocalsNotAssigned(this.getBbrUIParent().getUser().getId(), 1, 100, false, false, null);
//				locationsList = new ArrayList<>(Arrays.asList(locationsNotAssigned.getLocationDTOs()));
//				locationsList.addAll(Arrays.asList(locationsAssigned.getLocationDTOs()));
//			}
//
//			if (error != null && !error.hasError() && locationsResult != null)
//			{
//				result.addValueChangeListener(this::location_changeHandler);
//				this.hasAllLocals = locationsResult.getAlllocations();
//				LocationDTO lastUsedLocation = locationsResult.getLastUsedLocationDataDTOs() != null ? locationsResult.getLastUsedLocationDataDTOs() : null;
//
//				// todas las empresas y ultimo local utilizado
//				if (this.hasAllLocals)
//				{
//					if (lastUsedLocation != null)
//					{
//						// muestra el panel de busqueda
//						this.updateLocalsSearchPanel(0, this.hasAllLocals);
//
//						this.locations = new ArrayList<>();
//						// this.locations = new
//						// ArrayList<>(Arrays.asList(lastUsedLocation));
//						// this.showSearchPnl();
//						if (this.hasOptionAll)
//						{
//							LocationDTO allLocals = this.getOptionAllLocation();
//							// setted as first item
//							this.locations.add(0, allLocals);
//						}
//
//						this.locations.add(lastUsedLocation);
//
//						// option all is true and has lastUsedCompany
//						// when i have 2 locals is selected the first
//						this.setComboboxItemsAndSelect(result, this.locations);
//					}
//					else
//					{
//						this.setComboboxEmptyItem(result);
//					}
//				}
//				// ultimo local utilizado
//				else if ((locationsResult.getMostUsedLocationDataDTOs() != null) && (locationsResult.getMostUsedLocationDataDTOs().length > 0))
//				{
//					// muestra el panel de busqueda
//					this.updateLocalsSearchPanel(locationsResult.getTotallocations(), this.hasAllLocals);
//					// this.showSearchPnl();
//					LocationDTO[] locationslist = locationsResult.getMostUsedLocationDataDTOs();
//					Arrays.sort(locationslist, (p1, p2) -> p1.getName().compareTo(p2.getName()));
//					this.locations = new ArrayList<>(Arrays.asList(locationslist));
//
//					if (this.hasOptionAll)
//					{
//						LocationDTO allLocations = this.getOptionAllLocation();
//						// setted as first item
//						this.locations.add(0, allLocations);
//
//						// option all is true and has lastUsedLocation
//						// when i have 2 providers is selected allLocations
//						this.setComboboxItemsAndSelect(result, this.locations, allLocations);
//					}
//					else if (lastUsedLocation != null)
//					{
//						// option all is false and has lastUsedCompany
//						// when i have 2 providers is selected lastUsedCompany
//						this.setComboboxItemsAndSelect(result, this.locations, lastUsedLocation);
//					}
//					else
//					{
//						// option all is false and lastUsedCompany null
//						// when i have 2 providers is selected the first
//						this.setComboboxItemsAndSelect(result, this.locations);
//					}
//				}
//				else if (lastUsedLocation != null)
//				{
//					this.cbx_Locations.addItem(lastUsedLocation);
//					this.cbx_Locations.selectFirstItem();
//				}
//			}
//			else if (isByOC)
//			{
//				this.locations = new ArrayList<>(locationsList);
//				this.showSearchPnl();
//				if (this.hasOptionAll)
//				{
//					LocationDTO locationDefaultValue = this.getOptionAllLocation();
//					this.locations.add(0, locationDefaultValue);
//				}
//				this.setComboboxItemsAndSelect(result, this.locations);
//			}
//			else
//			{
//				this.setComboboxEmptyItem(result);
//			}
//		}
//
//		catch (BbrUserException e)
//		{
//			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
//		}
//
//		catch (BbrSystemException e)
//		{
//			e.printStackTrace();
//		}
//
//		result.markAsDirty();
//		return result;
//	}
//
//	private LocationDTO getOptionAllLocation()
//	{
//		LocationDTO locationDefaultValue = new LocationDTO();
//		locationDefaultValue.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all"));
//		locationDefaultValue.setId(-1L);
//		locationDefaultValue.setCode("-1");
//		return locationDefaultValue;
//	}
//
//	private BbrComboBox<LocationDTO> initializeCombobox()
//	{
//		BbrComboBox<LocationDTO> result = new BbrComboBox<>();
//		result.setItemCaptionGenerator(LocationDTO::getName);
//		result.setTextInputAllowed(false);
//		result.setEmptySelectionAllowed(false);
//		result.setWidth("255px");
//		result.addStyleName(BBR_FILTER_FIELDS_STYLE);
//		return result;
//	}
//
//	private void showSearchPnl()
//	{
//		Button btn_SearchLocal = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "search_local"));
//		btn_SearchLocal.addStyleName(LINK_FILTER_BUTTON_STYLE);
//		btn_SearchLocal.addClickListener((ClickListener & Serializable) e -> this.btn_SearchLocal_clickHandler(e));
//
//		this.pnlSearchPanel.setWidth("160px");
//		this.pnlSearchPanel.addComponent(btn_SearchLocal);
//		this.pnlSearchPanel.addStyleName(SEARCH_PANEL_STYLE);
//	}
//
//	/**
//	 * Generates the locals combo with emptyItem (no locals assigned)
//	 */
//	private BbrComboBox<LocationDTO> setComboboxEmptyItem(BbrComboBox<LocationDTO> result)
//	{
//		LocationDTO emptyResult = this.getLocationEmpty();
//		result.setItems(emptyResult);
//		result.setSelectedItem(emptyResult);
//		result.setEnabled(false);
//		return result;
//	}
//
//	private void updateLocalsSearchPanel(int totallocals, Boolean alllocals)
//	{
//		if (alllocals || totallocals > MAX_LOCALS)
//		{
//			showSearchPnl();
//		}
//	}
//
//	private LocationDTO getLocationEmpty()
//	{
//		LocationDTO locationDefaultValue = new LocationDTO();
//		locationDefaultValue.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_locations_assigned"));
//		return locationDefaultValue;
//	}
//
//	/**
//	 * By default is selected the first of the items list
//	 */
//	private void setComboboxItemsAndSelect(BbrComboBox<LocationDTO> result, ArrayList<LocationDTO> locations)
//	{
//		this.setComboboxItemsAndSelect(result, locations, null);
//	}
//
//	/**
//	 * By default is selected the <b>selectItem</b> of the items list
//	 */
//	private void setComboboxItemsAndSelect(BbrComboBox<LocationDTO> result, ArrayList<LocationDTO> locations, LocationDTO selectItem)
//	{
//		result.setItems(locations);
//		if (selectItem != null)
//		{
//			result.setSelectedItem(selectItem);
//		}
//		else
//		{
//			result.selectFirstItem();
//		}
//	}
//
//	// =====================================================================================================================================
//	// ENDING SECTION ----> PRIVATE METHODS
//	// =====================================================================================================================================
//
//}
