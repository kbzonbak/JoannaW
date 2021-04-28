package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.users.management.users.CreateEditClassification;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyClassificationArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyClassificationDTO;
import bbr.b2b.users.report.classes.LogInfoResultDTO;
import bbr.b2b.users.report.classes.LogInfoUserDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class MandatoryPositionsManagement extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long								serialVersionUID				= 4982315246309578782L;
	private static final String								MANDATORY_POSITIONS_COLUMN_ID	= "name";
	private static final String								NAME_COLUMN_ID					= "name";
	private static final String								COMMENT_COLUMN_ID				= "comment";
	private Integer											mandatoryPoistionsLogType		= 1;
	// Components
	private VerticalLayout									mainLayout						= null;
	private Button											btn_AddClassification			= null;
	private Button											btn_EditClassification			= null;
	private Button											btn_RemoveClassifications		= null;
	private Label											lbl_LastUpdate					= null;
	private BbrAdvancedGrid<CompanyClassificationDTO>		dgd_Classifications				= null;
	private BbrAdvancedGrid<PositionDTO>					dgd_MandatoryPositions			= null;
	// Variables
	private BbrWork<CompanyClassificationArrayResultDTO>	classificationsReportWork		= null;
	private BbrWork<BaseResultDTO>							removeAdminsWork				= null;
	private BbrWork<LogInfoResultDTO>						getUpdateInfoWork				= null;
	private Boolean											trackReport						= true;
	private CompanyClassificationDTO						selectedClassification			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public MandatoryPositionsManagement(BbrUI bbrUIParent)
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
		// Encabezado Titulo

		Label lbl_HeadTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "registered_classifications_mandatorypositions_in_system"));
		lbl_HeadTitle.addStyleName("header_style");

		Button btn_Refresh = new Button("",
				EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler());
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		titleBar.addComponents(lbl_HeadTitle, btn_Refresh);
		titleBar.setComponentAlignment(lbl_HeadTitle, Alignment.MIDDLE_LEFT);
		titleBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
		titleBar.addStyleName("filter-toolbar");
		titleBar.addStyleName("toolbar-layout");
		titleBar.setExpandRatio(lbl_HeadTitle, 1F);

		this.btn_AddClassification = new Button("",
				EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		this.btn_AddClassification.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_classification"));
		this.btn_AddClassification.addClickListener((ClickListener & Serializable) e -> this.btn_AddClassification_clickHandler(e));
		this.btn_AddClassification.addStyleName("toolbar-button");

		this.btn_EditClassification = new Button("",
				EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
		this.btn_EditClassification.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_classification"));
		this.btn_EditClassification.addClickListener((ClickListener & Serializable) e -> this.btn_EditClassification_clickHandler(e));
		this.btn_EditClassification.addStyleName("toolbar-button");
		this.btn_EditClassification.setEnabled(false);

		this.btn_RemoveClassifications = new Button("",
				EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveClassifications.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_classification"));
		this.btn_RemoveClassifications.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveClassifications_clickHandler(e));
		this.btn_RemoveClassifications.addStyleName("toolbar-button");
		this.btn_RemoveClassifications.setEnabled(false);

		HorizontalLayout adminsButtons = new HorizontalLayout();
		adminsButtons.addStyleName("toolbar-layout");
		adminsButtons.setHeight("30px");
		adminsButtons.addComponents(this.btn_AddClassification, this.btn_EditClassification, this.btn_RemoveClassifications);
		adminsButtons.setComponentAlignment(this.btn_AddClassification, Alignment.MIDDLE_LEFT);
		adminsButtons.setComponentAlignment(this.btn_EditClassification, Alignment.MIDDLE_LEFT);
		adminsButtons.setComponentAlignment(this.btn_RemoveClassifications, Alignment.MIDDLE_LEFT);

		// Grilla Perfiles

		this.dgd_Classifications = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Classifications.setSortable(false);
		this.dgd_Classifications.setSelectionMode(SelectionMode.SINGLE);
		this.initializeProfilesGridColumns();

		this.dgd_Classifications.addStyleName("report-grid");
		this.dgd_Classifications.setWidth("550px");
		this.dgd_Classifications.setHeight("100%");
		this.dgd_Classifications.addSelectionListener((SelectionListener<CompanyClassificationDTO> & Serializable) e -> this.dgd_Classifications_SelectionHandler(e));

		// Grilla Administradores

		this.dgd_MandatoryPositions = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_MandatoryPositions.setSortable(false);
		this.initializeAdminsGridColumns();

		this.dgd_MandatoryPositions.addStyleName("report-grid");
		this.dgd_MandatoryPositions.addSelectionListener((SelectionListener<PositionDTO> & Serializable) e -> this.adminRow_SelectedHandler(e));
		this.dgd_MandatoryPositions.setSizeFull();

		// AQUI VAN LAS 2 GRILLAS
		HorizontalLayout dataGridsSpace = new HorizontalLayout();
		dataGridsSpace.addComponents(this.dgd_Classifications, this.dgd_MandatoryPositions);
		dataGridsSpace.setComponentAlignment(this.dgd_Classifications, Alignment.MIDDLE_CENTER);
		dataGridsSpace.setComponentAlignment(this.dgd_MandatoryPositions, Alignment.MIDDLE_CENTER);
		dataGridsSpace.setHeight("100%");
		dataGridsSpace.setMargin(false);
		dataGridsSpace.setSpacing(false);

		// Texto Ultima Actualizacion

		this.lbl_LastUpdate = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date") + ": ");

		VerticalLayout buttonsAndGrids = new VerticalLayout();
		buttonsAndGrids.addComponents(adminsButtons, dataGridsSpace, this.lbl_LastUpdate);
		buttonsAndGrids.setExpandRatio(adminsButtons, 0.1F);
		buttonsAndGrids.setExpandRatio(dataGridsSpace, 0.9F);
		buttonsAndGrids.setExpandRatio(this.lbl_LastUpdate, 0.1F);
		buttonsAndGrids.setComponentAlignment(adminsButtons, Alignment.MIDDLE_LEFT);
		buttonsAndGrids.setComponentAlignment(this.lbl_LastUpdate, Alignment.MIDDLE_RIGHT);
		buttonsAndGrids.setHeight("100%");
		buttonsAndGrids.setWidth("1050px");
		buttonsAndGrids.setMargin(false);
		buttonsAndGrids.setSpacing(false);

		// Main Layout
		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout-no-filter");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(titleBar, buttonsAndGrids);
		this.mainLayout.setComponentAlignment(buttonsAndGrids, Alignment.MIDDLE_CENTER);
		this.mainLayout.setExpandRatio(buttonsAndGrids, 1F);

		this.addComponent(this.mainLayout);

		this.updateAdminActionButtons();

		this.classificationsReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.classificationsReportWork.addFunction(new Function<Object, CompanyClassificationArrayResultDTO>()
		{
			@Override
			public CompanyClassificationArrayResultDTO apply(Object t)
			{
				return executeService(MandatoryPositionsManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.classificationsReportWork);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateClassificationsReport(result, sender);
				this.refreshLastUpdateInfo();
			}
			else if ((triggerObject == this.btn_AddClassification) || (triggerObject == this.btn_RemoveClassifications))
			{
				this.updateAdminsReportAfterAction(result, sender);
				this.refreshReport_clickHandler();
				this.refreshLastUpdateInfo();
			}

			else if (triggerObject == this.lbl_LastUpdate)
			{
				this.updateInfoLabel(result, sender);
			}
		}

		else
		{
			MandatoryPositionsManagement senderReport = (MandatoryPositionsManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void btn_AddClassification_clickHandler(ClickEvent event)
	{
		CreateEditClassification winCreateEditClassification = new CreateEditClassification(this.getBbrUIParent(), null);
		winCreateEditClassification.addBbrEventListener((BbrEventListener & Serializable) e -> this.refreshReport_clickHandler());
		winCreateEditClassification.open(true, true, this);
	}

	private void btn_EditClassification_clickHandler(ClickEvent event)
	{
		if (this.selectedClassification != null)
		{
			CreateEditClassification winCreateEditClassification = new CreateEditClassification(this.getBbrUIParent(), this.selectedClassification);
			winCreateEditClassification.addBbrEventListener((BbrEventListener & Serializable) e -> this.refreshReport_clickHandler());
			winCreateEditClassification.open(true, true, this);
		}
	}

	private void btn_RemoveClassifications_clickHandler(ClickEvent event)
	{
		if (this.selectedClassification != null)
		{
			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "confirm_delete_classification"),
					() -> this.executeRemoveService());
		}
		else
		{
			this.btn_RemoveClassifications.setEnabled(false);
		}
	}

	private void executeRemoveService()
	{
		if ((this.dgd_Classifications.getSelectedItems() != null) && (this.dgd_Classifications.getSelectedItems().size() > 0))
		{
			CompanyClassificationDTO[] selectedClassifications = this.dgd_Classifications.getSelectedItems()
					.toArray(new CompanyClassificationDTO[this.dgd_Classifications.getSelectedItems().size()]);

			this.removeAdminsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_RemoveClassifications);
			this.removeAdminsWork.addFunction(new Function<Object, BaseResultDTO>()
			{
				@Override
				public BaseResultDTO apply(Object t)
				{
					return executeRemoveClassificationService(MandatoryPositionsManagement.this.getBbrUIParent(), selectedClassifications);
				}
			});
			this.startWaiting();
			this.executeBbrWork(this.removeAdminsWork);
		}
	}

	private LogInfoUserDTO getLogInfoUser(BbrUI bbrUI)
	{
		LogInfoUserDTO loginfo = new LogInfoUserDTO();
		loginfo.setUsername(bbrUI.getUser().getName());
		loginfo.setUserlastname(bbrUI.getUser().getLastName());
		loginfo.setWhen(LocalDateTime.now());
		return loginfo;
	}

	private BaseResultDTO executeRemoveClassificationService(BbrUI bbrUI, CompanyClassificationDTO[] selectedClassifications)
	{
		BaseResultDTO result = null;
		try
		{
			Long[] selectedPositionsIds = Arrays.stream(selectedClassifications).map(p -> p.getId()).toArray(Long[]::new);

			LogInfoUserDTO loginfo = this.getLogInfoUser(bbrUI);

			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).doDeleteCompanyClassification(selectedPositionsIds, loginfo);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void dgd_Classifications_SelectionHandler(SelectionEvent<CompanyClassificationDTO> e)
	{
		if (e.getFirstSelectedItem().isPresent() && e.getAllSelectedItems().size() == 1)
		{
			// Usar item seleccionado para llenar el listado de cargos
			// obligtorios
			this.selectedClassification = e.getFirstSelectedItem().get();
			this.doUpdateMandatoryPositionsGrid(this.selectedClassification.getPositions());
			this.btn_EditClassification.setEnabled(true);
			this.btn_RemoveClassifications.setEnabled(true);
		}
		else
		{
			this.doUpdateMandatoryPositionsGrid(new PositionDTO[0]);
			this.btn_EditClassification.setEnabled(false);
			this.btn_RemoveClassifications.setEnabled(true);
		}
	}

	private void doUpdateMandatoryPositionsGrid(PositionDTO[] positions)
	{
		PositionDTO[] provider = positions;
		ListDataProvider<PositionDTO> dataprovider = new ListDataProvider<>(Arrays.asList(provider));
		this.dgd_MandatoryPositions.setDataProvider(dataprovider, false);
	}

	private void adminRow_SelectedHandler(SelectionEvent<PositionDTO> e)
	{
		this.updateAdminActionButtons();
	}

	private void refreshReport_clickHandler()
	{
		this.startWaiting();

		this.trackReport = false;
		this.executeBbrWork(classificationsReportWork);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private CompanyClassificationArrayResultDTO executeService(BbrUI bbrUI)
	{
		CompanyClassificationArrayResultDTO result = null;

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getCompanyClassifications();
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
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

	private void doUpdateClassificationsReport(Object result, BbrWorkExecutor sender)
	{
		MandatoryPositionsManagement senderReport = (MandatoryPositionsManagement) sender;

		if (result != null)
		{
			CompanyClassificationArrayResultDTO reportResult = (CompanyClassificationArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				CompanyClassificationDTO[] classifications = reportResult.getCompanyClassificationDTOs();

				ListDataProvider<CompanyClassificationDTO> dataprovider = null;
				if (classifications != null)
				{
					dataprovider = new ListDataProvider<>(Arrays.asList(classifications));
				}
				else
				{
					dataprovider = new ListDataProvider<>(Arrays.asList(new CompanyClassificationDTO[0]));
					
					if(!senderReport.getBbrUIParent().hasAlertWindowOpen())
					senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "empty_classifications"));
				}

				this.dgd_Classifications.setDataProvider(dataprovider, false);

				// End Tracking

				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}
			}
		}

		this.stopWaiting();
	}

	private void updateAdminsReportAfterAction(Object result, BbrWorkExecutor sender)
	{
		MandatoryPositionsManagement senderReport = (MandatoryPositionsManagement) sender;

		if (result != null)
		{
			BaseResultDTO actionResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(actionResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				// End Tracking

				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.ITEM_GENERIC_ACTION, senderReport.getBbrFilterDescription());
				}

			}
		}
		this.stopWaiting();
	}

	private void initializeProfilesGridColumns()
	{
		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_classification_code"))
				.setDescriptionGenerator(p -> p.getName())
				.setWidthUndefined()
				.setId(NAME_COLUMN_ID);

		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationDTO::getComment, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_classification_description"))
				.setDescriptionGenerator(p -> p.getComment())
				.setWidth(340D)
				.setId(COMMENT_COLUMN_ID);

	}

	private void initializeAdminsGridColumns()
	{
		this.dgd_MandatoryPositions.addCustomColumn(PositionDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_mandatorypositions"), true)
				.setId(MANDATORY_POSITIONS_COLUMN_ID);
	}

	// private CheckBox getSelfAssignableCheckBox(ProfileDTO profileItem)
	// {
	// CheckBox result = null;
	//
	// if (profileItem.getAutoasignable())
	// {
	// result = new CheckBox();
	// result.setValue(true);
	// result.setReadOnly(true);
	// }
	//
	// return result;
	// }

	// private String stateDescriptionFunction(UserDTO user)
	// {
	// String result = "";
	//
	// if (user != null)
	// {
	// result = (user.getActive()) ?
	// I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active")
	// : I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive");
	// }
	//
	// return result;
	// }

	private void refreshLastUpdateInfo()
	{
		this.getUpdateInfoWork = new BbrWork<>(this, this.getBbrUIParent(), this.lbl_LastUpdate);
		this.getUpdateInfoWork.addFunction(new Function<Object, LogInfoResultDTO>()
		{
			@Override
			public LogInfoResultDTO apply(Object t)
			{
				return executeLastUpdateInfoService(MandatoryPositionsManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.getUpdateInfoWork);
	}

	private LogInfoResultDTO executeLastUpdateInfoService(BbrUI bbrUI)
	{
		LogInfoResultDTO result = null;

		try
		{
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getLastCompanyClassificationLogByType(this.mandatoryPoistionsLogType);
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

	private void updateInfoLabel(Object result, BbrWorkExecutor sender)
	{
		MandatoryPositionsManagement senderReport = (MandatoryPositionsManagement) sender;
		if (result != null)
		{
			LogInfoResultDTO callResult = (LogInfoResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(callResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				if ((callResult != null) && (callResult.getLoginfo() != null))
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

					String newInfoLabel = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date")
							+ ": "
							+ callResult.getLoginfo().getUsername()
							+ " "
							+ callResult.getLoginfo().getUserlastname()
							+ " ("
							+ dtf.format(callResult.getLoginfo().getWhen())
							+ ")";

					this.lbl_LastUpdate.setValue(newInfoLabel);
				}
			}
		}

		this.stopWaiting();
	}

	private void updateAdminActionButtons()
	{
		if (this.dgd_MandatoryPositions != null)
		{
			Boolean areAdminsSelected = (!this.dgd_MandatoryPositions.isEmpty())
					? ((this.dgd_MandatoryPositions.getSelectedItems() != null) && !this.dgd_MandatoryPositions.getSelectedItems().isEmpty()) : false;
			// Boolean areProfilesSelected =
			// (!this.dgd_Classifications.isEmpty())
			// ? ((this.dgd_Classifications.getSelectedItems() != null) &&
			// !this.dgd_Classifications.getSelectedItems().isEmpty())
			// : false;

			this.btn_RemoveClassifications.setEnabled(areAdminsSelected);
			// this.btn_AddAdmins.setEnabled(areProfilesSelected &&
			// (this.selectedProfile != null) &&
			// !this.selectedProfile.getAutoasignable());
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
