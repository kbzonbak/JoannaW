package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateOrEditTemplate extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long									serialVersionUID					= 4584127889063410648L;

	private static final String								ATTRIB_VISUAL_ORDER				= "visualorder";

	private BbrTabSheet											tabNav_TemplateGenAttributes	= null;
	private Button													btn_Save								= null;
	private BbrComboBox<SearchCriterion>					cbx_TemplateStates				= null;
	private BbrTextField											txt_TemplateName					= null;
	private BbrAdvancedGrid<DefinableAttributeDataDTO>	dgd_GenericAttribs				= null;
	private BbrAdvancedGrid<DefinableAttributeDataDTO>	dgd_VariantAttribs				= null;
	private Button													btn_MoveGenAttribsVOUp			= null;
	private Button													btn_MoveVarAttribsVOUp			= null;
	private Button													btn_MoveGenAttribsVODown		= null;
	private Button													btn_MoveVarAttribsVODown		= null;
	private Button													btn_RemoveGenAttributes			= null;
	private Button													btn_RemoveVariantAttributes	= null;

	private Boolean												editionMode							= false;
	private BbrWork<ArticleTypeResultDTO>					templateDetailsWork				= null;
	private BbrWork<BaseResultDTO>							saveWork								= null;
	private ArticleTypeDataDTO									selectedTemplate					= null;
	private ArrayList<DefinableAttributeDataDTO>			genAttribsList						= new ArrayList<DefinableAttributeDataDTO>();
	private ArrayList<DefinableAttributeDataDTO>			variantAttribsList				= new ArrayList<DefinableAttributeDataDTO>();
	private String													type									= null;
	private String													selectedClientName				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CreateOrEditTemplate(BbrUI parent, ArticleTypeDataDTO selectedTemplate, boolean editionMode, String type, String selectedClient)
	{
		super(parent);
		this.editionMode = editionMode;
		this.selectedTemplate = selectedTemplate;
		this.selectedClientName = selectedClient;
		this.type = type;
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
		// Nombre de Plantilla
		Label lbl_TemplateName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template_name"));
		lbl_TemplateName.setWidth("120px");
		lbl_TemplateName.addStyleName("bold_style");

		this.txt_TemplateName = new BbrTextField();
		this.txt_TemplateName.setWidth(100, Unit.PERCENTAGE);
		this.txt_TemplateName.setRequiredIndicatorVisible(true);
		this.txt_TemplateName.addStyleName("bbr-filter-fields");

		HorizontalLayout pnlTemplateName = new HorizontalLayout(lbl_TemplateName, txt_TemplateName);
		pnlTemplateName.setExpandRatio(txt_TemplateName, 1F);
		pnlTemplateName.setSizeFull();

		// Estado
		Label lbl_State = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"));
		lbl_State.setWidth("120px");
		lbl_State.addStyleName("bold_style");

		this.cbx_TemplateStates = this.getTemplateStatesComboBox();

		HorizontalLayout pnlState = new HorizontalLayout(lbl_State, cbx_TemplateStates);
		pnlState.setExpandRatio(cbx_TemplateStates, 1F);
		pnlState.setSizeFull();

		VerticalLayout pnlDataHeader = new VerticalLayout();
		pnlDataHeader.addComponents(pnlTemplateName, pnlState);
		pnlDataHeader.setWidth("100%");
		pnlDataHeader.setMargin(false);

		// Tabs
		BbrTabContent genericAttribsTab = this.getGenericAttributesTab();
		genericAttribsTab.setSizeFull();
		BbrTabContent variableAttribsTab = this.getVariantAttributesTab();
		variableAttribsTab.setSizeFull();

		this.tabNav_TemplateGenAttributes = new BbrTabSheet();
		this.tabNav_TemplateGenAttributes.setSizeFull();

		this.tabNav_TemplateGenAttributes.addBbrTab(genericAttribsTab, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "tb_generic_attributes"),
		false);
		this.tabNav_TemplateGenAttributes.addBbrTab(variableAttribsTab, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "tb_variant_attributes"),
		false);

		// Botón Guardar
		this.btn_Save = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "save"));
		this.btn_Save.setStyleName("primary");
		this.btn_Save.addStyleName("btn-generic");
		this.btn_Save.setWidth("150px");
		this.btn_Save.addClickListener((ClickListener & Serializable) e -> btn_SaveTemplate_clickHandler(e));

		// Botón Cancelar
		Button btn_Cancel = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btn_Cancel_clickHandler(e));

		// Layout Botones
		HorizontalLayout buttonsGrp = new HorizontalLayout(this.btn_Save, new BbrHSeparator("40px"), btn_Cancel);
		buttonsGrp.setSpacing(true);

		HorizontalLayout buttonsSpace = new HorizontalLayout(buttonsGrp);
		buttonsSpace.setWidth("100%");
		buttonsSpace.addStyleName("bbr-buttons-panel");
		buttonsSpace.setComponentAlignment(buttonsGrp, Alignment.MIDDLE_CENTER);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(pnlDataHeader, this.tabNav_TemplateGenAttributes, buttonsSpace);
		mainLayout.setSpacing(true);
		mainLayout.setExpandRatio(this.tabNav_TemplateGenAttributes, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		String strWinTitle = (this.editionMode == true) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_edit_template")
		: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_add_template");
		this.setWidth("1000px");
		this.setHeight("600px");
		this.setCaption(strWinTitle);
		this.setContent(mainLayout);

		if (this.editionMode)
		{
			// Llamar a servicio que trae atributos ya seleccionados
			// para la
			// plantilla que se esta editando

			this.startWaiting();

			templateDetailsWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			templateDetailsWork.addFunction(new Function<Object, ArticleTypeResultDTO>()
			{

				@Override
				public ArticleTypeResultDTO apply(Object t)
				{
					return executeGetTemplateService(CreateOrEditTemplate.this.getBbrUIParent());
				}
			});

			this.executeBbrWork(this.templateDetailsWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CreateOrEditTemplate bbrSender = (CreateOrEditTemplate) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateDetails(result, sender);
			}
			else if (triggerObject == this.btn_Save)
			{
				bbrSender.updateSavedArticle(result, sender);
			}
			// else if (triggerObject ==
			// this.btn_DownloadTriggerButton)
			// {
			// this.doDownloadFile(result, sender, triggerObject);
			// }
			// else if (triggerObject == btn_DeleteTemplates)
			// {
			// bbrSender.doDeleteReport(result, sender);
			// }
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


	private void btn_RemoveAttribute_clickHandler(ClickEvent event, BbrAdvancedGrid<DefinableAttributeDataDTO> dgd_Attrib, ArrayList<DefinableAttributeDataDTO> selectedList)
	{
		if ((dgd_Attrib.getSelectedItems() != null) && (dgd_Attrib.getSelectedItems().size() > 0))
		{
			dgd_Attrib.getSelectedItems().forEach(a -> selectedList.remove(a));

			this.updateVisualOrderValue(selectedList);

			this.updateDataProvider(dgd_Attrib, selectedList);
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
			I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_required"));
		}
	}


	private void btn_MoveAttribute_clickHandler(ClickEvent event, BbrAdvancedGrid<DefinableAttributeDataDTO> dgd_Attrib, ArrayList<DefinableAttributeDataDTO> selectedList,
	boolean isUp)
	{
		if ((dgd_Attrib.getSelectedItems() != null) && (dgd_Attrib.getSelectedItems().size() > 0))
		{
			List<DefinableAttributeDataDTO> lstSelectedItems = BbrSetsUtils.getInstance().toList(dgd_Attrib.getSelectedItems(), DefinableAttributeDataDTO.class);

			lstSelectedItems = this.getListSortedByVisualOrder(lstSelectedItems.stream(), !isUp);

			for (DefinableAttributeDataDTO defAttribute : lstSelectedItems)
			{
				boolean canMove = (isUp == true) ? (defAttribute.getVisualorder() > 0) : (defAttribute.getVisualorder() < selectedList.size() - 1);

				if (canMove)
				{
					int step = (isUp == true) ? 1 : -1;

					Collections.swap(selectedList, defAttribute.getVisualorder(), defAttribute.getVisualorder() - step);

					this.updateVisualOrderValue(selectedList);

					this.updateDataProvider(dgd_Attrib, selectedList);
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
			I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_required"));
		}
	}


	private void btn_addGenAttributes_clickHandler(ClickEvent event)
	{
		TemplateGenAttributesEditor editorCtrl = new TemplateGenAttributesEditor(this.getBbrUIParent(),
		this.genAttribsList != null ? this.genAttribsList : new ArrayList<DefinableAttributeDataDTO>());
		editorCtrl.initializeView();
		editorCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.addedGenAttribute_selectedHandler(e));
		editorCtrl.open(true);
	}


	@SuppressWarnings("unchecked")
	private void addedGenAttribute_selectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null)
		{
			ArrayList<DefinableAttributeDataDTO> result = (ArrayList<DefinableAttributeDataDTO>) bbrEvent.getResultObject();

			if (result != null)
			{
				this.genAttribsList = result;
				this.updateVisualOrderValue(this.genAttribsList);

				ListDataProvider<DefinableAttributeDataDTO> dataprovider = new ListDataProvider<>(this.genAttribsList);

				this.dgd_GenericAttribs.setDataProvider(dataprovider);
				this.dgd_GenericAttribs.getDataProvider().refreshAll();
			}

		}
	}


	private void btn_addVariantAttributes_clickHandler(ClickEvent event)
	{
		TemplateGenAttributesEditor editorCtrl = new TemplateGenAttributesEditor(this.getBbrUIParent(), this.variantAttribsList);
		editorCtrl.initializeView();
		editorCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.addedVariantAttribute_selectedHandler(e));
		editorCtrl.open(true);
	}


	@SuppressWarnings("unchecked")
	private void addedVariantAttribute_selectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null)
		{
			ArrayList<DefinableAttributeDataDTO> result = (ArrayList<DefinableAttributeDataDTO>) bbrEvent.getResultObject();

			if (result != null)
			{
				this.variantAttribsList = result;
				this.updateVisualOrderValue(this.variantAttribsList);
			}

			this.variantAttribsList.forEach(p -> {
				p.setForvariant(true);
			});

			ListDataProvider<DefinableAttributeDataDTO> dataprovider = new ListDataProvider<>(this.variantAttribsList);
			this.dgd_VariantAttribs.setDataProvider(dataprovider);
			this.dgd_VariantAttribs.getDataProvider().refreshAll();

		}

	}


	private void btn_SaveTemplate_clickHandler(ClickEvent e)
	{
		String errorMsg = this.validateData();

		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			if (this.selectedTemplate == null)
			{
				this.selectedTemplate = new ArticleTypeDataDTO();
				this.selectedTemplate.setTypename(this.type);
			}

			String articleName = this.txt_TemplateName.getValue();
			this.selectedTemplate.setName(articleName);
			this.selectedTemplate.setDescription(articleName);
			this.selectedTemplate.setClientinternalname(this.selectedClientName);
			this.selectedTemplate.setActive(this.cbx_TemplateStates.getSelectedValue().getId() == 1 ? true : false);

			ArrayList<DefinableAttributeDataDTO> resultOperation = this.intersection(this.genAttribsList, this.variantAttribsList);

			if (resultOperation == null || resultOperation.size() == 0)
			{
				ArrayList<DefinableAttributeDataDTO> unionList = new ArrayList<>();

				if (this.genAttribsList != null && this.genAttribsList.size() > 0)
				{
					unionList.addAll(this.genAttribsList);
				}

				if (this.variantAttribsList != null && this.variantAttribsList.size() > 0)
				{
					unionList.addAll(this.variantAttribsList);
				}

				DefinableAttributeDataDTO[] attributes = unionList.stream().toArray(DefinableAttributeDataDTO[]::new);

				this.startWaiting();
				this.saveWork = new BbrWork<>(CreateOrEditTemplate.this, CreateOrEditTemplate.this.getBbrUIParent(), this.btn_Save);
				this.saveWork.addFunction(new Function<Object, BaseResultDTO>()
				{
					@Override
					public BaseResultDTO apply(Object t)
					{
						return executeSaveArticleService(CreateOrEditTemplate.this.getBbrUIParent(), attributes, CreateOrEditTemplate.this.selectedTemplate);
					}
				});

				this.executeBbrWork(this.saveWork);
			}
			else
			{
				CreateOrEditTemplate.this.showErrorMessage(I18NManager.getI18NString(CreateOrEditTemplate.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
				I18NManager.getI18NString(CreateOrEditTemplate.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "no_attributes_repeated"));
			}
		}
		else
		{
			CreateOrEditTemplate.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private String validateData()
	{
		String result = null;

		if (this.txt_TemplateName.isEmpty())
		{
			result = I18NManager.getI18NString(getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "required_template_name");
		}
		else if (this.cbx_TemplateStates.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "invalid_state");
		}

		return result;
	}


	private BbrComboBox<SearchCriterion> getTemplateStatesComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = FepUtils.getStatesCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setRequiredIndicatorVisible(true);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addStyleName("bbr-filter-fields");
		result.setWidth(100F, Unit.PERCENTAGE);

		return result;
	}


	private void updateDataProvider(BbrAdvancedGrid<DefinableAttributeDataDTO> dgd_Attrib, List<DefinableAttributeDataDTO> selectedList)
	{
		ListDataProvider<DefinableAttributeDataDTO> dataprovider = new ListDataProvider<>(selectedList);

		Set<DefinableAttributeDataDTO> selectedAttributesInGrid = dgd_Attrib.getSelectedItems();
		dgd_Attrib.setDataProvider(dataprovider);
		selectedAttributesInGrid.forEach(s -> dgd_Attrib.select(s));
		dgd_Attrib.getDataProvider().refreshAll();
	}


	private BbrTabContent getGenericAttributesTab()
	{
		// Boton Editar Atributos
		Button btn_EditGenericAttributes = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		btn_EditGenericAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_include_attribute"));
		btn_EditGenericAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_addGenAttributes_clickHandler(e));
		btn_EditGenericAttributes.addStyleName("toolbar-button");

		// Boton Quitar Atributos
		this.btn_RemoveGenAttributes = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveGenAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_remove_generic_attribute"));
		this.btn_RemoveGenAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveAttribute_clickHandler(e, this.dgd_GenericAttribs, this.genAttribsList));
		this.btn_RemoveGenAttributes.addStyleName("toolbar-button");
		this.btn_RemoveGenAttributes.setEnabled(false);

		// Espacio Botones
		HorizontalLayout btnsGrp = new HorizontalLayout();
		btnsGrp.addComponents(btn_EditGenericAttributes, this.btn_RemoveGenAttributes);
		btnsGrp.setHeight("30px");
		btnsGrp.setSpacing(true);

		HorizontalLayout btnsSpace = new HorizontalLayout();
		btnsSpace.setWidth("100%");
		btnsSpace.addComponents(btnsGrp);
		btnsSpace.setComponentAlignment(btnsGrp, Alignment.MIDDLE_LEFT);

		// Grilla Atributos Genericos
		this.dgd_GenericAttribs = new BbrAdvancedGrid<>(this.getUser().getLocale());

		this.initializeAttributesGridColumns(this.dgd_GenericAttribs);

		this.dgd_GenericAttribs.addStyleName("report-grid");
		this.dgd_GenericAttribs.setSelectionMode(SelectionMode.MULTI);
		this.dgd_GenericAttribs.setWidth("100%");
		this.dgd_GenericAttribs.addSelectionListener((SelectionListener<DefinableAttributeDataDTO> & Serializable) e -> this.updateButtons(true));

		// Botones Manejo Orden Visual
		this.btn_MoveGenAttribsVOUp = new Button("", EnumToolbarButton.UPLOAD_PRIMARY.getResource());
		this.btn_MoveGenAttribsVOUp.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "up"));
		this.btn_MoveGenAttribsVOUp.addClickListener((ClickListener & Serializable) e -> this.btn_MoveAttribute_clickHandler(e, this.dgd_GenericAttribs, this.genAttribsList, true));
		this.btn_MoveGenAttribsVOUp.addStyleName("toolbar-button");
		this.btn_MoveGenAttribsVOUp.setEnabled(false);

		this.btn_MoveGenAttribsVODown = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_MoveGenAttribsVODown.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "down"));
		this.btn_MoveGenAttribsVODown
		.addClickListener((ClickListener & Serializable) e -> this.btn_MoveAttribute_clickHandler(e, this.dgd_GenericAttribs, this.genAttribsList, false));
		this.btn_MoveGenAttribsVODown.addStyleName("toolbar-button");
		this.btn_MoveGenAttribsVODown.setEnabled(false);

		// Botones de Orden Visual
		VerticalLayout visualOrderButtonsGrp = new VerticalLayout();
		visualOrderButtonsGrp.addComponents(this.btn_MoveGenAttribsVOUp, this.btn_MoveGenAttribsVODown);
		visualOrderButtonsGrp.setSpacing(true);
		visualOrderButtonsGrp.setMargin(false);

		VerticalLayout visualOrderBtnSpace = new VerticalLayout();
		visualOrderBtnSpace.addComponent(visualOrderButtonsGrp);
		visualOrderBtnSpace.setMargin(false);
		visualOrderBtnSpace.setWidth("30px");
		visualOrderBtnSpace.setHeight("100%");
		visualOrderBtnSpace.setComponentAlignment(visualOrderButtonsGrp, Alignment.MIDDLE_CENTER);

		// Grid + Botones Orden Visual
		HorizontalLayout gridAndVOBtnsSpace = new HorizontalLayout();
		gridAndVOBtnsSpace.addComponents(this.dgd_GenericAttribs, visualOrderBtnSpace);
		gridAndVOBtnsSpace.setExpandRatio(this.dgd_GenericAttribs, 1F);
		gridAndVOBtnsSpace.setSizeFull();
		gridAndVOBtnsSpace.setSpacing(true);
		gridAndVOBtnsSpace.setMargin(false);

		// Grupo General Tab
		VerticalLayout generalGenAttTabSpace = new VerticalLayout();
		generalGenAttTabSpace.setSizeFull();
		generalGenAttTabSpace.addComponents(btnsSpace, gridAndVOBtnsSpace);
		generalGenAttTabSpace.setExpandRatio(gridAndVOBtnsSpace, 1F);
		generalGenAttTabSpace.setMargin(false);
		generalGenAttTabSpace.setSpacing(true);

		// Tab
		BbrTabContent genericAttribsTab = new BbrTabContent();
		genericAttribsTab.setSizeFull();
		genericAttribsTab.addComponent(generalGenAttTabSpace);

		return genericAttribsTab;
	}


	private BbrTabContent getVariantAttributesTab()
	{
		// Boton Editar Atributos
		Button btn_EditVariantAttributes = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		btn_EditVariantAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_include_attribute"));
		btn_EditVariantAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_addVariantAttributes_clickHandler(e));
		btn_EditVariantAttributes.addStyleName("toolbar-button");

		// Boton Quitar Atributos
		this.btn_RemoveVariantAttributes = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveVariantAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_remove_variant_attribute"));
		this.btn_RemoveVariantAttributes
		.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveAttribute_clickHandler(e, this.dgd_VariantAttribs, this.variantAttribsList));
		this.btn_RemoveVariantAttributes.addStyleName("toolbar-button");
		this.btn_RemoveVariantAttributes.setEnabled(false);

		// Espacio Botones
		HorizontalLayout btnsGrp = new HorizontalLayout();
		btnsGrp.addComponents(btn_EditVariantAttributes, this.btn_RemoveVariantAttributes);
		btnsGrp.setHeight("30px");
		btnsGrp.setSpacing(true);

		HorizontalLayout btnsSpace = new HorizontalLayout();
		btnsSpace.setWidth("100%");
		btnsSpace.addComponents(btnsGrp);
		btnsSpace.setComponentAlignment(btnsGrp, Alignment.MIDDLE_LEFT);

		// Grilla Variante de Atributos
		this.dgd_VariantAttribs = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeAttributesGridColumns(this.dgd_VariantAttribs);

		this.dgd_VariantAttribs.addStyleName("report-grid");
		this.dgd_VariantAttribs.setSelectionMode(SelectionMode.MULTI);
		this.dgd_VariantAttribs.setWidth("100%");
		this.dgd_VariantAttribs.addSelectionListener((SelectionListener<DefinableAttributeDataDTO> & Serializable) e -> this.updateButtons(true));

		// Botones Manejo Orden Visual
		this.btn_MoveVarAttribsVOUp = new Button("", EnumToolbarButton.UPLOAD_PRIMARY.getResource());
		this.btn_MoveVarAttribsVOUp.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "up"));
		this.btn_MoveVarAttribsVOUp
		.addClickListener((ClickListener & Serializable) e -> this.btn_MoveAttribute_clickHandler(e, this.dgd_VariantAttribs, this.variantAttribsList, true));
		this.btn_MoveVarAttribsVOUp.addStyleName("toolbar-button");
		this.btn_MoveVarAttribsVOUp.setEnabled(false);

		this.btn_MoveVarAttribsVODown = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_MoveVarAttribsVODown.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "down"));
		this.btn_MoveVarAttribsVODown
		.addClickListener((ClickListener & Serializable) e -> this.btn_MoveAttribute_clickHandler(e, this.dgd_VariantAttribs, this.variantAttribsList, false));
		this.btn_MoveVarAttribsVODown.addStyleName("toolbar-button");
		this.btn_MoveVarAttribsVODown.setEnabled(false);

		// Botones de Orden Visual
		VerticalLayout visualOrderButtonsGrp = new VerticalLayout();
		visualOrderButtonsGrp.addComponents(this.btn_MoveVarAttribsVOUp, this.btn_MoveVarAttribsVODown);
		visualOrderButtonsGrp.setSpacing(true);
		visualOrderButtonsGrp.setMargin(false);

		VerticalLayout visualOrderBtnSpace = new VerticalLayout();
		visualOrderBtnSpace.addComponent(visualOrderButtonsGrp);
		visualOrderBtnSpace.setHeight("100%");
		visualOrderBtnSpace.setWidth("30px");
		visualOrderBtnSpace.setMargin(false);
		visualOrderBtnSpace.setComponentAlignment(visualOrderButtonsGrp, Alignment.MIDDLE_CENTER);

		// Grid + Botones Orden Visual
		HorizontalLayout gridAndVOBtnsSpace = new HorizontalLayout();
		gridAndVOBtnsSpace.setSizeFull();
		gridAndVOBtnsSpace.addComponents(this.dgd_VariantAttribs, visualOrderBtnSpace);
		gridAndVOBtnsSpace.setExpandRatio(this.dgd_VariantAttribs, 1F);
		gridAndVOBtnsSpace.setSpacing(true);
		gridAndVOBtnsSpace.setMargin(false);

		// Grupo General Tab
		VerticalLayout generalGenAttTabSpace = new VerticalLayout();
		generalGenAttTabSpace.setSizeFull();
		generalGenAttTabSpace.addComponents(btnsSpace, gridAndVOBtnsSpace);
		generalGenAttTabSpace.setExpandRatio(gridAndVOBtnsSpace, 1F);
		generalGenAttTabSpace.setMargin(false);

		// Tab
		BbrTabContent genericAttribsTab = new BbrTabContent();
		genericAttribsTab.setSizeFull();
		genericAttribsTab.addComponent(generalGenAttTabSpace);

		return genericAttribsTab;
	}


	private void initializeAttributesGridColumns(BbrAdvancedGrid<DefinableAttributeDataDTO> dgd_Attribs)
	{
		dgd_Attribs.addCustomColumn(DefinableAttributeDataDTO::getVisualorder, "").setId(ATTRIB_VISUAL_ORDER).setHidden(true).setSortable(false);

		dgd_Attribs.addCustomColumn(DefinableAttributeDataDTO::getAttributeinternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "b2b_Name"), true)
		.setSortable(false);

		dgd_Attribs.addCustomColumn(DefinableAttributeDataDTO::getAttributevendorname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name"), true)
		.setSortable(false);

		dgd_Attribs.addCustomColumn(DefinableAttributeDataDTO::getAtributedatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true)
		.setSortable(false);

		dgd_Attribs.addCustomComponentColumn(d -> this.getMandatoryCheckBoxField(d), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_require"), true)
		.setStyleGenerator(p -> BbrAlignment.CENTER.getValue()).setWidth(120D).setSortable(false);

		dgd_Attribs.addCustomComponentColumn(d -> this.getBulletCheckBoxField(d), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_bullet"), true)
		.setStyleGenerator(p -> BbrAlignment.CENTER.getValue()).setWidth(120D).setSortable(false);
	}


	private ArticleTypeResultDTO executeGetTemplateService(BbrUI bbrUI)
	{
		ArticleTypeResultDTO result = null;

		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUI).getArticleType(this.selectedTemplate.getId(), this.getBbrUIParent().getLocale().getLanguage());
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


	private BaseResultDTO executeSaveArticleService(BbrUI bbrUIParent, DefinableAttributeDataDTO[] attributes, ArticleTypeDTO articleTypeDto)
	{
		BaseResultDTO result = null;
		if (articleTypeDto != null)
		{
			try
			{
				// Start Tracking
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal().addOrEditArticleType(articleTypeDto, attributes, this.getBbrUIParent().getUser().getFullName(),
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
		}
		return result;
	}


	private void updateSavedArticle(Object result, BbrWorkExecutor sender)
	{
		CreateOrEditTemplate senderReport = (CreateOrEditTemplate) sender;
		BbrEvent bbrEvent = null;
		if (this.editionMode)
		{
			bbrEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
		}
		else
		{
			bbrEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
		}

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				if (!this.getBbrUIParent().hasAlertWindowOpen())
				{
					this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));
				}
				this.dispatchBbrEvent(bbrEvent);
				this.close();
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}

		}
		senderReport.stopWaiting();
	}


	private void doUpdateDetails(Object result, BbrWorkExecutor sender)
	{
		CreateOrEditTemplate senderReport = (CreateOrEditTemplate) sender;

		if (result != null)
		{
			ArticleTypeResultDTO detailsResult = (ArticleTypeResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(detailsResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				DefinableAttributeDataDTO[] data = (detailsResult.getDefattributes() != null) ? detailsResult.getDefattributes() : new DefinableAttributeDataDTO[0];

				senderReport.genAttribsList = senderReport.getSortedList(data, false);
				senderReport.variantAttribsList = senderReport.getSortedList(data, true);

				senderReport.dgd_GenericAttribs
				.setDataProvider(new ListDataProvider<>(senderReport.genAttribsList != null ? senderReport.genAttribsList : new ArrayList<DefinableAttributeDataDTO>()), false);
				senderReport.dgd_VariantAttribs
				.setDataProvider(new ListDataProvider<>(senderReport.variantAttribsList != null ? senderReport.variantAttribsList : new ArrayList<DefinableAttributeDataDTO>()), false);

				senderReport.updateButtons(false);
				senderReport.loadTemplateData();
			}
		}

		senderReport.stopWaiting();
	}


	private void loadTemplateData()
	{
		if (this.selectedTemplate != null)
		{
			this.txt_TemplateName.setValue(this.selectedTemplate.getName());
			this.cbx_TemplateStates.setSelectedItem(this.selectedTemplate.getActive() ? FepUtils.getStatesCriteria()[0] : FepUtils.getStatesCriteria()[1]);
		}
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		Boolean selectedItemsGen = ((this.dgd_GenericAttribs.getSelectedItems() != null) && !this.dgd_GenericAttribs.getSelectedItems().isEmpty());
		Boolean selectedItemsVariant = ((this.dgd_VariantAttribs.getSelectedItems() != null) && !this.dgd_VariantAttribs.getSelectedItems().isEmpty());
		this.btn_RemoveGenAttributes.setEnabled(selectedItemsGen);
		this.btn_RemoveVariantAttributes.setEnabled(selectedItemsVariant);
		if (this.btn_MoveGenAttribsVOUp != null && this.btn_MoveGenAttribsVODown != null)
		{
			this.btn_MoveGenAttribsVOUp.setEnabled(selectedItemsGen);
			this.btn_MoveGenAttribsVODown.setEnabled(selectedItemsGen);
		}
		if (this.btn_MoveVarAttribsVOUp != null && this.btn_MoveVarAttribsVODown != null)
		{
			this.btn_MoveVarAttribsVOUp.setEnabled(selectedItemsVariant);
			this.btn_MoveVarAttribsVODown.setEnabled(selectedItemsVariant);
		}
	}


	private CheckBox getMandatoryCheckBoxField(DefinableAttributeDataDTO attributeItem)
	{
		CheckBox result = new CheckBox();
		result.setValue((attributeItem != null) ? attributeItem.getMandatory() : false);
		result.addValueChangeListener(event -> attributeItem.setMandatory(result.getValue()));

		return result;
	}


	private CheckBox getBulletCheckBoxField(DefinableAttributeDataDTO attributeItem)
	{
		CheckBox result = new CheckBox();
		result.setValue(((attributeItem != null) && (attributeItem.getBullet() != null)) ? attributeItem.getBullet() : false);
		result.addValueChangeListener(event -> attributeItem.setBullet(result.getValue()));

		return result;
	}


	private ArrayList<DefinableAttributeDataDTO> getSortedList(DefinableAttributeDataDTO[] attributes, boolean isVariantFilter)
	{
		ArrayList<DefinableAttributeDataDTO> result = null;

		if (attributes != null && attributes.length > 0)
		{
			Stream<DefinableAttributeDataDTO> filteredStream = Arrays.asList(attributes).stream().filter(p -> p.getForvariant() == isVariantFilter);

			result = this.getListSortedByVisualOrder(filteredStream, false);

			this.updateVisualOrderValue(result);
		}

		return result;
	}


	private ArrayList<DefinableAttributeDataDTO> getListSortedByVisualOrder(Stream<DefinableAttributeDataDTO> filteredStream, boolean reversed)
	{
		ArrayList<DefinableAttributeDataDTO> result;
		if (reversed)
		{
			result = new ArrayList<>(filteredStream.sorted(Comparator.comparing(DefinableAttributeDataDTO::getVisualorder).reversed()).collect(Collectors.toList()));
		}
		else
		{
			result = new ArrayList<>(filteredStream.sorted(Comparator.comparing(DefinableAttributeDataDTO::getVisualorder)).collect(Collectors.toList()));
		}

		return result;
	}


	private void updateVisualOrderValue(ArrayList<DefinableAttributeDataDTO> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).setVisualorder(i);
		}
	}


	private ArrayList<DefinableAttributeDataDTO> intersection(ArrayList<DefinableAttributeDataDTO> list1, ArrayList<DefinableAttributeDataDTO> list2)
	{
		ArrayList<DefinableAttributeDataDTO> result = new ArrayList<>();
		if (list1 != null && list2 != null) // -> Se hace la interseccion.
		{
			for (DefinableAttributeDataDTO attrib1 : list1)
			{
				for (DefinableAttributeDataDTO attrib2 : list2)
				{
					if (attrib1.getAttributeid().equals(attrib2.getAttributeid()))
					{
						result.add(attrib1);
					}
				}
			}
		}
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
