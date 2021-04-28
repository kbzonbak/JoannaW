package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.AttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.FoundAttribSearchInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filter_section.fep.TemplateSearchAttribSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class StandardizedAttributeEditor extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID				= -8454585578751490L;

	private static final String						FOUND_ATTRIB_B2B_NAME			= "internalname";
	private static final String						FOUND_ATTRIB_VENDOR_NAME		= "vendorname";
	private static final String						FOUND_ATTRIB_DATA_TYPE			= "datatypename";

	private Button									btn_Save						= null;
	private Button									btn_RemoveSelectedAttributes	= null;
	private BbrAdvancedGrid<AttributeDTO>			datGrid_FoundAttribs			= null;
	private BbrAdvancedGrid<AttributeDTO>			datGrid_SelectedAttribs			= null;
	private Label									lblCounterAssigned				= new Label();
	private Label									lblCounterAvailable				= new Label();

	private ArrayList<AttributeDTO>					dsAssignedAttribs				= new ArrayList<>();
	private ArrayList<AttributeDTO>					dsAvailableAttribs				= null;
	private ArrayList<AttributeDTO>					selectedDefAttribList			= new ArrayList<AttributeDTO>();
	private HashMap<Long, AttributeDTO>				selectedAttribList				= new HashMap<>();
	private HashMap<Long, AttributeDTO>				toAssignAttribs					= new HashMap<>();
	private ArticleTypeDataDTO						selectedTemplate				= null;
	private BbrWork<AttributeArrayResultDTO>		searchResultsWork				= null;
	private TemplateSearchAttribSection				templateSearchAttribSection		= null;
	private ArrayList<AttributeDTO>					listFound						= null;

	private AttributeDTO 							retailAttribute 				= null;
	private BbrWork<BaseResultDTO>					saveWork						= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public StandardizedAttributeEditor(BbrUI parent)
	{
		super(parent);
	}

	public StandardizedAttributeEditor(BbrUI parent, AttributeDTO retailAttribute, ArrayList<AttributeDTO> currentlySelectedAttributes)
	{
		super(parent);
		this.retailAttribute = retailAttribute;
		this.selectedDefAttribList = currentlySelectedAttributes;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		Label lbl_AttRetailName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_attribute_name"));
		lbl_AttRetailName.setWidth("150px");

		BbrTextField txt_AttRetailName = new BbrTextField();
		txt_AttRetailName.setValue(this.retailAttribute.getInternalname());
		txt_AttRetailName.setWidth(100, Unit.PERCENTAGE);
		txt_AttRetailName.addStyleName("bbr-filter-fields");
		txt_AttRetailName.setEnabled(false);

		// Encabezado: Atributos Encontrados
		HorizontalLayout headerleft = new HorizontalLayout();
		headerleft.addComponents(lbl_AttRetailName, txt_AttRetailName);
		headerleft.setWidth("100%");
		headerleft.setExpandRatio(txt_AttRetailName, 1F);
		headerleft.setComponentAlignment(lbl_AttRetailName, Alignment.MIDDLE_LEFT);
		headerleft.setComponentAlignment(txt_AttRetailName, Alignment.MIDDLE_LEFT);

		Label lbl_AttRetailVendorName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_attribute_vendorname"));
		lbl_AttRetailVendorName.setWidth("150px");

		BbrTextField txt_AttRetailVendorName = new BbrTextField();
		txt_AttRetailVendorName.setValue(this.retailAttribute.getVendorname());
		txt_AttRetailVendorName.setWidth(100, Unit.PERCENTAGE);
		txt_AttRetailVendorName.addStyleName("bbr-filter-fields");
		txt_AttRetailVendorName.setEnabled(false);

		// Titulo Grilla Encontrados
		Label lbl_GenericAttribFound = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attributes_found") + ":");
		lbl_GenericAttribFound.addStyleName("bold_style");

		Button btn_AttribSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_AttribSearch.setStyleName("btn-filter-search");
		btn_AttribSearch.setClickShortcut(KeyCode.ENTER);
		btn_AttribSearch.addClickListener((ClickListener & Serializable) e -> this.attribSearch_clickHandler(e));

		// Seccion Atributos Encontrados
		templateSearchAttribSection = new TemplateSearchAttribSection(this.getBbrUIParent(), btn_AttribSearch);
		templateSearchAttribSection.initializeView();

		// Grilla Atributos Encontrados
		this.datGrid_FoundAttribs = new BbrAdvancedGrid<>(this.getUser().getLocale());

		this.initializeFoundAttribsGridColumns();

		this.datGrid_FoundAttribs.addStyleName("report-grid");
		this.datGrid_FoundAttribs.addStyleName("bbr-multi-grid");
		this.datGrid_FoundAttribs.setSortable(false);
		this.datGrid_FoundAttribs.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_FoundAttribs.setWidth("100%");
		this.datGrid_FoundAttribs.addSelectionListener((SelectionListener<AttributeDTO> & Serializable) e -> selectedItemFound_handler(e));

		HorizontalLayout pnlCounterAvailable = new HorizontalLayout();
		pnlCounterAvailable.setWidth("100%");
		pnlCounterAvailable.setHeight("30px");

		pnlCounterAvailable.addComponent(this.lblCounterAvailable);
		pnlCounterAvailable.setComponentAlignment(this.lblCounterAvailable, Alignment.MIDDLE_LEFT);

		// Texto atributos encontrados
		HorizontalLayout pnl_GenericAttribFound= new HorizontalLayout();
		pnl_GenericAttribFound.setWidth("100%");
		pnl_GenericAttribFound.setHeight("30px");
		pnl_GenericAttribFound.addComponents(lbl_GenericAttribFound);
		pnl_GenericAttribFound.setComponentAlignment(lbl_GenericAttribFound, Alignment.MIDDLE_LEFT);

		// Espacio General: Atributos Encontrados
		VerticalLayout pnl_FoundAttribsSpace = new VerticalLayout();
		pnl_FoundAttribsSpace.setHeight("100%");
		pnl_FoundAttribsSpace.addComponents(headerleft, pnl_GenericAttribFound, templateSearchAttribSection, this.datGrid_FoundAttribs, pnlCounterAvailable);
		pnl_FoundAttribsSpace.setExpandRatio(this.datGrid_FoundAttribs, 1F);
		pnl_FoundAttribsSpace.setMargin(false);
		pnl_FoundAttribsSpace.setSizeFull();

		// Encabezado: Atributos Seleccionados OK
		HorizontalLayout headerright = new HorizontalLayout();
		headerright.setWidth("100%");
		headerright.addComponents(lbl_AttRetailVendorName, txt_AttRetailVendorName);
		headerright.setExpandRatio(txt_AttRetailVendorName, 1F);

		headerright.setComponentAlignment(lbl_AttRetailVendorName, Alignment.MIDDLE_LEFT);
		headerright.setComponentAlignment(txt_AttRetailVendorName, Alignment.MIDDLE_LEFT);

		// Boton Quitar Atributos Seleccionados
		this.btn_RemoveSelectedAttributes = new Button("",
				BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
		this.btn_RemoveSelectedAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_remove_generic_attribute"));
		this.btn_RemoveSelectedAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveAttribute_clickHandler(e));
		this.btn_RemoveSelectedAttributes.addStyleName("toolbar-button");

		// Titulo Grilla
		Label lbl_GenericAttribSelected = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attributes_selected") + ":");
		lbl_GenericAttribSelected.addStyleName("bold_style");

		// Espacio Boton Eliminar
		HorizontalLayout pnl_RemoveBtnSpace = new HorizontalLayout();
		pnl_RemoveBtnSpace.setWidth("100%");
		pnl_RemoveBtnSpace.setHeight("30px");
		pnl_RemoveBtnSpace.addComponents(lbl_GenericAttribSelected, btn_RemoveSelectedAttributes);
		pnl_RemoveBtnSpace.setComponentAlignment(lbl_GenericAttribSelected, Alignment.MIDDLE_LEFT);
		pnl_RemoveBtnSpace.setComponentAlignment(btn_RemoveSelectedAttributes, Alignment.MIDDLE_RIGHT);

		// Grilla Atributos Seleccionados
		this.datGrid_SelectedAttribs = new BbrAdvancedGrid<>(this.getUser().getLocale());

		this.initializeSelectedAttribGridColumns();

		this.datGrid_SelectedAttribs.addStyleName("report-grid");
		this.datGrid_SelectedAttribs.setSortable(false);
		this.datGrid_SelectedAttribs.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_SelectedAttribs.setWidth("100%");
		this.datGrid_SelectedAttribs.addSelectionListener((SelectionListener<AttributeDTO> & Serializable) e -> {
			this.updateButtons();
		});

		HorizontalLayout pnlCounterAssigned = new HorizontalLayout();
		pnlCounterAssigned.setWidth("100%");
		pnlCounterAssigned.setHeight("30px");

		pnlCounterAssigned.addComponent(this.lblCounterAssigned);
		pnlCounterAssigned.setComponentAlignment(this.lblCounterAssigned, Alignment.MIDDLE_LEFT);

		// Espacio General: Atributos Seleccionados
		VerticalLayout pnl_SelectedAttribsSpace = new VerticalLayout();
		pnl_SelectedAttribsSpace.setHeight("100%");
		pnl_SelectedAttribsSpace.addComponents(headerright, pnl_RemoveBtnSpace, this.datGrid_SelectedAttribs, pnlCounterAssigned);
		pnl_SelectedAttribsSpace.setExpandRatio(this.datGrid_SelectedAttribs, 1F);
		pnl_SelectedAttribsSpace.setSpacing(true);
		pnl_SelectedAttribsSpace.setMargin(false);

		// Ambas Grillas
		HorizontalLayout pnl_GeneralGridsSpace = new HorizontalLayout();
		pnl_GeneralGridsSpace.setSizeFull();
		pnl_GeneralGridsSpace.addComponents(pnl_FoundAttribsSpace, pnl_SelectedAttribsSpace);
		pnl_GeneralGridsSpace.setMargin(false);

		// Botón Editar
		this.btn_Save = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "edit"));
		this.btn_Save.setStyleName("primary");
		this.btn_Save.addStyleName("btn-generic");
		this.btn_Save.setWidth("150px");
		this.btn_Save.addClickListener((ClickListener & Serializable) e -> btn_UpdateAttribs_clickHandler(e));

		// Botón Cancelar
		Button btn_Cancel = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btn_Cancel_clickHandler(e));

		// Layout Botones
		HorizontalLayout pnl_ButtonsGrp = new HorizontalLayout(this.btn_Save, btn_Cancel);
		pnl_ButtonsGrp.addStyleName("bbr-buttons-panel");
		pnl_ButtonsGrp.setSpacing(true);

		HorizontalLayout pnl_ButtonsSpace = new HorizontalLayout(pnl_ButtonsGrp);
		pnl_ButtonsSpace.setWidth("100%");
		pnl_ButtonsSpace.setComponentAlignment(pnl_ButtonsGrp, Alignment.MIDDLE_CENTER);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(pnl_GeneralGridsSpace, pnl_ButtonsSpace);
		mainLayout.setMargin(false);
		mainLayout.setExpandRatio(pnl_GeneralGridsSpace, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		this.initilizeSelectedAttribsGrid();
		this.updateAvailableGridSelection();
		this.updateSelectionLabel();
		this.updateButtons();
		// Ventana
		this.setWidth("90%");
		this.setHeight("90%");
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_standard_attributes_editor"));
		this.setContent(mainLayout);
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		StandardizedAttributeEditor bbrSender = (StandardizedAttributeEditor) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateDetails(result, sender);

			}else if (triggerObject == this.btn_Save)
			{
				bbrSender.doSaveStandardArticleType(result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
		}
		bbrSender.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Cancel_clickHandler(ClickEvent event)
	{
		this.close();
	}


	private void attribSearch_clickHandler(ClickEvent e)
	{
		String errorMessage = this.validateData();

		if (errorMessage == null || errorMessage.length() <= 0)
		{
			this.startWaiting();

			this.searchResultsWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			this.searchResultsWork.addFunction(new Function<Object, AttributeArrayResultDTO>()
			{

				@Override
				public AttributeArrayResultDTO apply(Object t)
				{
					return executeSearchAttributesService(StandardizedAttributeEditor.this.getBbrUIParent());
				}
			});

			this.executeBbrWork(this.searchResultsWork);
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
		}
	}


	private void btn_RemoveAttribute_clickHandler(ClickEvent event)
	{
		this.datGrid_SelectedAttribs.getSelectedItems().forEach(attrib -> {

			AttributeDTO pdt = this.toAssignAttribs.remove(attrib.getId());
			
			if (pdt != null)
				this.datGrid_FoundAttribs.deselect(pdt);

			this.dsAssignedAttribs.removeIf(a -> a.getId().equals(attrib.getId()));
		});

		this.updateToAssignDataSource();
		this.updateSelectionLabel();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeFoundAttribsGridColumns()
	{
		// Grid Atributos Encontrados
		this.datGrid_FoundAttribs.addCustomColumn(AttributeDTO::getInternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "b2b_Name"), true)
		.setId(FOUND_ATTRIB_B2B_NAME);

		this.datGrid_FoundAttribs.addCustomColumn(AttributeDTO::getVendorname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name"), true)
		.setId(FOUND_ATTRIB_VENDOR_NAME);

		this.datGrid_FoundAttribs.addCustomColumn(AttributeDTO::getDatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true)
		.setId(FOUND_ATTRIB_DATA_TYPE);
	}


	private void initializeSelectedAttribGridColumns()
	{
		// Grid Atributos Seleccionados
		this.datGrid_SelectedAttribs.addCustomColumn(AttributeDTO::getInternalname,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_name"), true);

		this.datGrid_SelectedAttribs.addCustomColumn(AttributeDTO::getVendorname,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_vendorname"), true);

		this.datGrid_SelectedAttribs.addCustomColumn(AttributeDTO::getDatatypename,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true);
	}


	private AttributeArrayResultDTO executeSearchAttributesService(BbrUI bbrUI)
	{
		AttributeArrayResultDTO result = null;

		try
		{
			AttributeInitParamDTO initParam = new AttributeInitParamDTO();
			initParam.setInternalname(this.getInitParam().getParameter());
			initParam.setClientname(FEPConstants.INTERNAL_CLIENT_NAME);

			OrderCriteriaDTO[] order = new OrderCriteriaDTO[] { new OrderCriteriaDTO(FOUND_ATTRIB_B2B_NAME, true) };

			Long idSelectedTemplate = this.selectedTemplate != null ? this.selectedTemplate.getId() : null;
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUI).getAttributesNotInArticleType(initParam, idSelectedTemplate, order,
					this.getBbrUIParent().getLocale().getLanguage());

		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut();
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


	private void selectedItemFound_handler(SelectionEvent<AttributeDTO> e)
	{
		MultiSelectionEvent<AttributeDTO> event = (MultiSelectionEvent<AttributeDTO>) e;
		updateToAssignSelection(event.getAddedSelection(), event.getRemovedSelection(), e.isUserOriginated());
	}


	private void updateToAssignSelection(Set<AttributeDTO> addedSelection, Set<AttributeDTO> removedSelection, boolean isUserOriginated)
	{
		if (addedSelection != null && addedSelection.size() > 0)
		{
			addedSelection.forEach(attrib -> {
				this.toAssignAttribs.put(attrib.getId(), attrib);

				if (this.dsAssignedAttribs != null && !this.containSameAttrib(attrib))
				{
					AttributeDTO asignedAttribute = new AttributeDTO();
					asignedAttribute.setId(attrib.getId());
					asignedAttribute.setInternalname(attrib.getInternalname());
					asignedAttribute.setVendorname(attrib.getVendorname());
					asignedAttribute.setDatatypename(attrib.getDatatypename());

					this.dsAssignedAttribs.add(asignedAttribute);
				}
			});
			this.updateToAssignDataSource();
		}
		else if (removedSelection != null && removedSelection.size() > 0 && isUserOriginated)
		{
			removedSelection.forEach(attrib -> {
				toAssignAttribs.remove(attrib.getId());
				if (this.dsAssignedAttribs != null)
				{
					this.dsAssignedAttribs.removeIf(a -> a.getId().equals(attrib.getId()));
				}
			});
			this.updateToAssignDataSource();
		}

		this.updateSelectionLabel();
		this.updateAllSelectedItems();
	}


	private boolean containSameAttrib(AttributeDTO fepattrib)
	{
		return this.dsAssignedAttribs.stream().filter(a -> a.getId().equals(fepattrib.getId())).findAny().isPresent();
	}


	private void updateToAssignDataSource()
	{
		ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(this.dsAssignedAttribs);
		this.datGrid_SelectedAttribs.setDataProvider(dataprovider);
	}


	private void updateAllSelectedItems()
	{
		if (listFound != null && listFound.size() > 0)
		{
			for (AttributeDTO attributeDTO : this.listFound)
			{
				if (this.dsAssignedAttribs != null && this.dsAssignedAttribs.size() > 0)
				{
					this.dsAssignedAttribs.forEach(f -> {
						if (attributeDTO.getId().equals(f.getId()))
						{
							selectedAttribList.put(attributeDTO.getId(), attributeDTO);
						}
					});
				}
			}
		}

	}


	private void updateSelectionLabel()
	{
		if (this.dsAssignedAttribs != null && this.dsAssignedAttribs.size() > 0)
		{
			this.lblCounterAssigned.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": " + String.valueOf(dsAssignedAttribs.size()));
		}
		else
		{
			this.lblCounterAssigned.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": 0");
		}

		if (this.dsAvailableAttribs != null && this.dsAvailableAttribs.size() > 0)
		{
			this.lblCounterAvailable
			.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": " + String.valueOf(dsAvailableAttribs.size()));
		}
		else
		{
			this.lblCounterAvailable.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": 0");
		}

	}


	private void updateAvailableGridSelection()
	{
		if (this.dsAvailableAttribs != null && this.dsAvailableAttribs.size() > 0)
		{
			this.dsAvailableAttribs.forEach(attrib -> {
				if (this.toAssignAttribs.get(attrib.getId()) != null)
				{
					this.datGrid_FoundAttribs.select(attrib);
				}
			});
		}
	}


	private void initilizeSelectedAttribsGrid()
	{
		this.dsAssignedAttribs.clear();
		if (this.selectedDefAttribList != null && this.selectedDefAttribList.size() > 0){
			this.dsAssignedAttribs.addAll(this.selectedDefAttribList);
			ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(this.selectedDefAttribList);
			this.datGrid_SelectedAttribs.setDataProvider(dataprovider);
		}

		this.datGrid_SelectedAttribs.getDataProvider().refreshAll();
	}


	private void selectAvailableAttribs()
	{
		this.updateAllSelectedItems();
		ArrayList<AttributeDTO> selectedlist = new ArrayList<>(selectedAttribList.values().stream().collect(Collectors.toList()));
		ArrayList<AttributeDTO> resultOperation = this.intersection(dsAvailableAttribs, selectedlist);
		if (resultOperation != null && resultOperation.size() > 0)
		{
			resultOperation.forEach(r -> {
				this.datGrid_FoundAttribs.select(r);
			});
		}
		this.datGrid_FoundAttribs.getDataProvider().refreshAll();
	}


	private ArrayList<AttributeDTO> intersection(ArrayList<AttributeDTO> list1, ArrayList<AttributeDTO> list2)
	{
		ArrayList<AttributeDTO> result = new ArrayList<>();
		if (list1 != null && list2 != null) // -> Se hace la interseccion.
		{
			for (AttributeDTO attrib1 : list1)
			{
				for (AttributeDTO attrib2 : list2)
				{
					if (attrib1.getId().equals(attrib2.getId()))
					{
						result.add(attrib1);
					}
				}
			}
		}
		else if (list1 != null || list2 != null)
		{
			if (list1 != null)
			{
				result = list1;
			}
			else if (list2 != null)
			{
				result = list2;
			}
		}
		return result;
	}


	private void doUpdateDetails(Object result, BbrWorkExecutor sender)
	{
		StandardizedAttributeEditor senderReport = (StandardizedAttributeEditor) sender;

		if (result != null)
		{
			AttributeArrayResultDTO detailsResult = (AttributeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(detailsResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				AttributeDTO[] data = (detailsResult.getValues() != null) ? detailsResult.getValues() : new AttributeDTO[0];
				listFound = (ArrayList<AttributeDTO>) Arrays.stream(data).collect(Collectors.toList());
				ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(listFound);

				senderReport.datGrid_FoundAttribs.setDataProvider(dataprovider, false);
				this.dsAvailableAttribs = new ArrayList<>(Arrays.asList(data));
				this.selectAvailableAttribs();
				senderReport.updateButtons();
				this.updateSelectionLabel();
			}
		}

		senderReport.stopWaiting();
	}


	private String validateData()
	{
		String errorResult = null;

		errorResult = this.templateSearchAttribSection.validateData();

		return errorResult;
	}


	private FoundAttribSearchInfo getInitParam()
	{
		FoundAttribSearchInfo attributeSearchInfo = this.templateSearchAttribSection.getSectionResult();

		return attributeSearchInfo;
	}


	private void updateButtons()
	{
		Boolean selectedItems = ((this.datGrid_SelectedAttribs.getSelectedItems() != null) && !this.datGrid_SelectedAttribs.getSelectedItems().isEmpty());

		this.btn_RemoveSelectedAttributes.setEnabled(selectedItems);
	}


	private void btn_UpdateAttribs_clickHandler(ClickEvent e)
	{

		AttributeDTO[] fepattributes = this.dsAssignedAttribs != null && this.dsAssignedAttribs.size() > 0 
				? this.dsAssignedAttribs.stream().toArray(AttributeDTO[]::new)
						: null;

		StandardArticleTypeDTO[] standArticleType = new StandardArticleTypeDTO[1];
		standArticleType[0] = new StandardArticleTypeDTO();
		standArticleType[0].setRetailattribute(this.retailAttribute);
		standArticleType[0].setStandardcode("AAA");
		standArticleType[0].setFepattributes(fepattributes);

		this.startWaiting();
		this.saveWork = new BbrWork<>(StandardizedAttributeEditor.this, StandardizedAttributeEditor.this.getBbrUIParent(), this.btn_Save);
		this.saveWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeSaveStandardArticleTypeService(StandardizedAttributeEditor.this.getBbrUIParent(), standArticleType);
			}
		});

		this.executeBbrWork(this.saveWork);


	}

	private BaseResultDTO executeSaveStandardArticleTypeService(BbrUI bbrUIParent, StandardArticleTypeDTO[] standArticleType)
	{
		BaseResultDTO result = null;

		if (standArticleType != null && standArticleType.length > 0 )
		{
			try
			{
				// Start Tracking
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal().addOrEditStandardArticleTypes(standArticleType);
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}


	private void doSaveStandardArticleType(Object result, BbrWorkExecutor sender)
	{
		StandardizedAttributeEditor senderReport = (StandardizedAttributeEditor) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));

				BbrEvent event = new BbrEvent(BbrEvent.ITEMS_UPDATED);

				event.setResultObject(this.dsAssignedAttribs);

				this.dispatchBbrEvent(event);

				this.close();
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
