package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeInitParamDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.PrivilegesFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.utilities.BbrLambdaFunctions;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class ManagePrivileges extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID			= 7957381000708615470L;

	private static final String						ARTICLE_TYPE_NAME			= "articletypename";
	private static final String						TRADE_MARK					= "trade";
	private static final String						ITEM_COUNT					= "itemcount";

	private VerticalLayout								mainLayout;
	private BbrTextField									txtAtributeName			= null;
	private RadioButtonGroup<MDM_PRIVILEGE>		rbg_PrivilegesGroup		= null;

	private BbrAdvancedGrid<CPPrivilegeDTO>		datGrid_Privileges		= null;
	private ArrayList<CPPrivilegeDTO>				lstPrivileges				= null;

	private CPPrivilegeInitParamDTO					initParam					= null;

	private Button											btn_Refresh					= null;
	private Button											btn_DownloadPrivileges	= null;
	private Button											btn_SaveEditor				= null;

	private Boolean										trackReport					= true;
	private Boolean										resetPageInfo				= true;

	private BbrWork<CPPrivilegeArrayResultDTO>	reportWork					= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork				= null;
	private BbrWork<ErrorInfoArrayResultDTO>		saveWork						= null;
	private VerticalLayout								pnlEditor					= null;

	private enum MDM_PRIVILEGE
	{
		NO_VISIBLE(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_visible"), 0), ONLY_READ(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "only_read"), 1), CAN_WRITE(I18NManager.getI18NString(
		BbrUtilsResources.RES_MODULES_FEP, "can_write"), 2);

		String	name;
		int		value;


		MDM_PRIVILEGE(String name, int value)
		{
			this.name = name;
			this.value = value;
		}


		public String getName()
		{
			return name;
		}


		public int getValue()
		{
			return value;
		}
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ManagePrivileges(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
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
		BbrUser user = this.getUser();

		if (user != null)
		{
			// Filtro
			PrivilegesFilter filterLayout = new PrivilegesFilter(this);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

			this.btn_DownloadPrivileges = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			this.btn_DownloadPrivileges.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_privileges"));
			this.btn_DownloadPrivileges.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadPrivileges.addStyleName("toolbar-button");

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadPrivileges, this.btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			// Panel de la grilla
			this.datGrid_Privileges = new BbrAdvancedGrid<>(this.getUser().getLocale());

			this.initializeDataGridColumns();

			this.datGrid_Privileges.addStyleName("report-grid");
			this.datGrid_Privileges.setSizeFull();
			this.datGrid_Privileges.setSelectionMode(SelectionMode.MULTI);
			this.datGrid_Privileges.addSelectionListener((SelectionListener<CPPrivilegeDTO> & Serializable) e -> selection_gridHandler(e));

			this.pnlEditor = this.getPrivilegeEditorPanel();
			this.pnlEditor.addStyleName("panel-item-editor");
			this.pnlEditor.setWidth("100%");
			this.pnlEditor.setHeight("100%");

			VerticalLayout dataLayout = new VerticalLayout();
			dataLayout.setMargin(false);
			dataLayout.setSizeFull();
			dataLayout.addComponents(this.datGrid_Privileges);

			VerticalLayout editorLayout = new VerticalLayout();
			editorLayout.setMargin(false);
			editorLayout.setWidth("500px");
			editorLayout.setHeight("100%");
			editorLayout.addComponents(this.pnlEditor);

			HorizontalLayout pnl_content = new HorizontalLayout(dataLayout, editorLayout);
			pnl_content.setExpandRatio(dataLayout, 1F);
			pnl_content.setSizeFull();

			this.mainLayout = new VerticalLayout();
			this.mainLayout.addStyleName("report-layout");
			this.mainLayout.setSizeFull();
			this.mainLayout.setMargin(false);
			this.mainLayout.addComponents(toolBar, pnl_content);
			this.mainLayout.setExpandRatio(pnl_content, 2F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, CPPrivilegeArrayResultDTO>()
			{
				@Override
				public CPPrivilegeArrayResultDTO apply(Object t)
				{
					return executeService(ManagePrivileges.this.getBbrUIParent());
				}
			});
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}


	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (CPPrivilegeInitParamDTO) event.getResultObject();
			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ManagePrivileges.this.getBbrUIParent(), selectedFormat);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_SaveEditor)
			{
				this.doSaveReport(result, sender);
			}
			else if (triggerObject instanceof Button)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
		}
		else
		{
			ManagePrivileges senderReport = (ManagePrivileges) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC,
			"unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void selection_gridHandler(SelectionEvent<CPPrivilegeDTO> e)
	{
		this.txtAtributeName.clear();

		Set<CPPrivilegeDTO> selectedItems = this.datGrid_Privileges.getSelectedItems();

		if (selectedItems != null && !selectedItems.isEmpty())
		{
			List<String> namesList = selectedItems.stream().map(d -> d.getAttributename()).collect(Collectors.toList());
			String namesString = String.join(", ", namesList);
			StringBuilder namesStringBuilder = new StringBuilder(namesString);

			if (namesStringBuilder.length() > 50)
			{
				StringBuilder stringBuilder = new StringBuilder(namesStringBuilder.substring(0, 45).concat("..."));
				namesStringBuilder = stringBuilder;
			}

			this.txtAtributeName.setValue(namesStringBuilder.toString());
		}

		this.updatePrivilegesGroup(selectedItems);

		this.updateButtons(true);
	}


	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadPrivileges)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}
	
	
	private void btn_saveEditor_clickHandler(ClickEvent e)
	{
		BbrMessageBox.createQuestion(this.getBbrUIParent())
		.withYesButton((Runnable & Serializable) () -> this.handleSaveEditorYes())
		.withNoButton()
		.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
		.withHtmlMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_save_privilege_setting"))
		.open();
	}


	private void handleSaveEditorYes()
	{
		if ((this.datGrid_Privileges.getSelectedItems() != null) && (this.datGrid_Privileges.getSelectedItems().size() > 0) && this.rbg_PrivilegesGroup.getSelectedItem().isPresent())
		{
			ArrayList<CPPrivilegeDTO> selectedResource = new ArrayList<CPPrivilegeDTO>(this.datGrid_Privileges.getSelectedItems().stream().collect(Collectors.toList()));

			MDM_PRIVILEGE selectedPrivilege = this.rbg_PrivilegesGroup.getSelectedItem().get();

			for (CPPrivilegeDTO privilege : selectedResource)
			{
				privilege.setPrivilegestatus(selectedPrivilege.getValue());
			}

			saveWork = new BbrWork<>(ManagePrivileges.this, ManagePrivileges.this.getBbrUIParent(), this.btn_SaveEditor);
			saveWork.addFunction(new Function<Object, ErrorInfoArrayResultDTO>()
			{
				@Override
				public ErrorInfoArrayResultDTO apply(Object t)
				{
					return executeAddOrUpdateService(ManagePrivileges.this.getBbrUIParent(), selectedResource);
				}
			});

			ManagePrivileges.this.startWaiting();

			ManagePrivileges.this.executeBbrWork(saveWork);
		}
	}


	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumns()
	{
		this.datGrid_Privileges.addCustomColumn(CPPrivilegeDTO::getAttributename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attrib_name"), true).setId(ARTICLE_TYPE_NAME);
		this.datGrid_Privileges.addCustomColumn(CPPrivilegeDTO::getDatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_data_type"), true).setId(TRADE_MARK);
		this.datGrid_Privileges.addCustomColumn(a -> this.getPrivilegesStatus(a.getPrivilegestatus()).getName(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_privilege"), true)
		.setStyleGenerator(item -> "v-align-center")
		.setWidth(160)
		.setId(ITEM_COUNT);
	}


	private MDM_PRIVILEGE getPrivilegesStatus(int privilegeStatus)
	{

		MDM_PRIVILEGE result = null;
		switch (privilegeStatus)
		{
			case 0:
				result = MDM_PRIVILEGE.NO_VISIBLE;
				break;

			case 1:
				result = MDM_PRIVILEGE.ONLY_READ;
				break;
			case 2:
				result = MDM_PRIVILEGE.CAN_WRITE;
				break;

			default:
				result = MDM_PRIVILEGE.NO_VISIBLE;
				break;
		}

		return result;
	}


	private VerticalLayout getPrivilegeEditorPanel()
	{
		VerticalLayout form = new VerticalLayout();

		// header
		Label header = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "privilege_editor"));
		header.addStyleNames(ValoTheme.LABEL_H3, "bbr-bold-label");

		// texto
		Label text = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "privilege_text_editor"));
		text.setWidth("100%");

		// Nombre atributo
		Label lbl_AttributeName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_attribute_name"));
		lbl_AttributeName.addStyleName("v-caption");
		lbl_AttributeName.setWidth("150px");

		this.txtAtributeName = new BbrTextField();
		this.txtAtributeName.setReadOnly(true);
		this.txtAtributeName.addStyleName("bbr-text-field");
		this.txtAtributeName.setWidth(100, Unit.PERCENTAGE);

		HorizontalLayout pnl_AttributeName = new HorizontalLayout(lbl_AttributeName, this.txtAtributeName);
		pnl_AttributeName.setExpandRatio(lbl_AttributeName, 0.35F);
		pnl_AttributeName.setExpandRatio(this.txtAtributeName, 0.65F);
		pnl_AttributeName.setSizeFull();

		Label lbl_PrivilegesGroup = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "privilege_selected"));
		lbl_PrivilegesGroup.addStyleName("v-caption");
		lbl_PrivilegesGroup.setWidth("150px");

		this.rbg_PrivilegesGroup = new RadioButtonGroup<>();
		this.rbg_PrivilegesGroup.setItems(MDM_PRIVILEGE.ONLY_READ, MDM_PRIVILEGE.CAN_WRITE, MDM_PRIVILEGE.NO_VISIBLE);
		this.rbg_PrivilegesGroup.setItemCaptionGenerator(item -> item.getName());

		HorizontalLayout pnl_PrivilegesGroup = new HorizontalLayout(lbl_PrivilegesGroup, this.rbg_PrivilegesGroup);
		pnl_PrivilegesGroup.setExpandRatio(lbl_PrivilegesGroup, 0.35F);
		pnl_PrivilegesGroup.setExpandRatio(this.rbg_PrivilegesGroup, 0.65F);
		pnl_PrivilegesGroup.setSizeFull();

		FormLayout subForm = new FormLayout();

		subForm.addStyleName("panel-item-editor");
		subForm.addComponents(pnl_AttributeName, pnl_PrivilegesGroup);

		this.btn_SaveEditor = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		this.btn_SaveEditor.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		this.btn_SaveEditor.addClickListener((ClickListener & Serializable) e -> this.btn_saveEditor_clickHandler(e));
		this.btn_SaveEditor.addStyleName("btn-generic");
		this.btn_SaveEditor.setWidth("150px");

		HorizontalLayout pnlSaveButton = new HorizontalLayout();
		pnlSaveButton.addComponents(this.btn_SaveEditor);
		pnlSaveButton.setComponentAlignment(this.btn_SaveEditor, Alignment.TOP_CENTER);
		pnlSaveButton.setSpacing(false);
		pnlSaveButton.setMargin(false);
		pnlSaveButton.setHeight("100%");
		pnlSaveButton.setWidth("100%");

		form.setMargin(false);
		form.setWidth("100%");
		form.setHeight("100%");
		form.addStyleNames("generic-form");
		form.addStyleNames("bbr-container-space");
		form.addComponents(header, text, subForm, pnlSaveButton);
		form.setSpacing(true);
		form.setComponentAlignment(pnlSaveButton, Alignment.BOTTOM_CENTER);
		form.setExpandRatio(pnlSaveButton, 1F);
		return form;
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_SaveEditor.setEnabled(this.datGrid_Privileges.getSelectedItems() != null && this.datGrid_Privileges.getSelectedItems().size() > 0);
		this.pnlEditor.setEnabled(this.datGrid_Privileges.getSelectedItems() != null && this.datGrid_Privileges.getSelectedItems().size() > 0);
		this.btn_DownloadPrivileges.setEnabled(initParam != null);
		this.btn_Refresh.setEnabled(initParam != null);
	}


	private CPPrivilegeArrayResultDTO executeService(BbrUI bbrUI)
	{
		CPPrivilegeArrayResultDTO result = null;

		if (this.initParam != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();

				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).getPrivilegies(initParam);
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
		}

		return result;
	}


	private ErrorInfoArrayResultDTO executeAddOrUpdateService(BbrUI bbrUI, ArrayList<CPPrivilegeDTO> selectedResource)
	{
		ErrorInfoArrayResultDTO result = null;

		if (this.initParam != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				CPPrivilegeDTO[] privilegesArray = selectedResource.toArray(new CPPrivilegeDTO[selectedResource.size()]);
				this.initParam.setPrivilegies(privilegesArray);

				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).addOrEditPrivilegies(initParam);
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
		}

		return result;
	}


	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				CPPrivilegeDTO[] privilegesArray = this.lstPrivileges.toArray(new CPPrivilegeDTO[this.lstPrivileges.size()]);
				this.initParam.setPrivilegies(privilegesArray);
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadPrivilegesByItemStateAndUserType(initParam, bbrUIParent.getUser().getId(), selectedFormat.toString());
			}
			catch (BbrUserException ex)
			{
				AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException ex)
			{
				ex.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return fileResult;
	}


	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ManagePrivileges senderReport = (ManagePrivileges) sender;

		if (result != null)
		{
			CPPrivilegeArrayResultDTO reportResult = (CPPrivilegeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				this.lstPrivileges = new ArrayList<CPPrivilegeDTO>(Arrays.asList(reportResult.getValues()));
				ListDataProvider<CPPrivilegeDTO> dataprovider = new ListDataProvider<>(this.lstPrivileges);

				senderReport.datGrid_Privileges.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
		senderReport.stopWaiting();
	}


	private void doSaveReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ManagePrivileges senderReport = (ManagePrivileges) sender;

		if (result != null)
		{
			ErrorInfoArrayResultDTO reportResult = (ErrorInfoArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				this.startWaiting();
				this.executeBbrWork(reportWork);
				this.txtAtributeName.clear();
				this.rbg_PrivilegesGroup.clear();
				
				if (!this.getBbrUIParent().hasAlertWindowOpen())
				{
					this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));
				}
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
		senderReport.stopWaiting();
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ManagePrivileges senderReport = (ManagePrivileges) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}


	private void updatePrivilegesGroup(Set<CPPrivilegeDTO> selectedItems)
	{
		rbg_PrivilegesGroup.setSelectedItem(null);
		if (selectedItems != null && !selectedItems.isEmpty())
		{

			List<CPPrivilegeDTO> lstDistinct = BbrSetsUtils.getInstance().toList(selectedItems, CPPrivilegeDTO.class).stream().filter(BbrLambdaFunctions.distinctByKey(p -> p.getPrivilegestatus())).collect(Collectors.toList());

			if (lstDistinct.size() == 1)
			{
				MDM_PRIVILEGE value = this.getPrivilegesStatus(lstDistinct.get(0).getPrivilegestatus());
				rbg_PrivilegesGroup.setSelectedItem(value);
			}
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
