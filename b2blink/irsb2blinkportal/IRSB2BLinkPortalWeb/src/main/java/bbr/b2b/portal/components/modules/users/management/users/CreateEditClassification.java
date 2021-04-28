package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.users.management.providers_contacts.NewPositionProviderContact;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactProviderPositionDTO;
import bbr.b2b.users.report.classes.CompanyClassificationDTO;
import bbr.b2b.users.report.classes.CompanyClassificationInitParamDTO;
import bbr.b2b.users.report.classes.LogInfoUserDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateEditClassification extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID			= 6073999518673852498L;
	public static final String					BTN_ACCEPT					= "btnAccept";
	// Components
	private BbrTextField						txt_Name					= null;
	private BbrTextField						txt_Comment					= null;
	private BbrAdvancedGrid<PositionDTO>		dgd_Positions				= null;
	private Button								btn_Accept					= null;
	// Variables
	private BbrWork<PositionResultDTO>			positionsWork				= null;
	private BbrWork<BaseResultDTO>				addWork						= null;
	private BbrWork<BaseResultDTO>				editWork					= null;
	private List<PositionDTO>					positions					= new ArrayList<PositionDTO>();
	private PositionDTO							positionGridSelected		= null;
	private CompanyClassificationInitParamDTO	initParam					= null;
	private CompanyClassificationDTO			selectedClassification		= null;
	private boolean								isEdit						= false;
	private Button								btn_AddContactPosition		= null;
	private Button								btn_RemoveContactPosition	= null;
	private List<PositionDTO>					allPositionsList			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public CreateEditClassification(BbrUI parent, CompanyClassificationDTO selectedClassification)
	{
		super(parent);
		this.selectedClassification = selectedClassification;
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
		if (selectedClassification != null)
		{
			this.isEdit = true;
		}
		// name
		this.txt_Name = new BbrTextField();
		this.txt_Name.setMaxLength(50);
		this.txt_Name.addStyleName("bbr-fields");
		BbrHInputFieldContainer fld_Name = new BbrHInputFieldContainer(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"),
				this.txt_Name);

		// description
		this.txt_Comment = new BbrTextField();
		this.txt_Comment.setMaxLength(200);
		this.txt_Comment.addStyleName("bbr-fields");
		BbrHInputFieldContainer fld_Comment = new BbrHInputFieldContainer(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"),
				this.txt_Comment);

		// deciciones

		// positions button add
		this.btn_AddContactPosition = new Button("",
				EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		this.btn_AddContactPosition.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_position"));
		this.btn_AddContactPosition.addClickListener((ClickListener & Serializable) e -> this.btn_AddPosition_ClickHandler(null));
		this.btn_AddContactPosition.addStyleName("toolbar-button");

		// positions button remove
		this.btn_RemoveContactPosition = new Button("",
				EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveContactPosition.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_position"));
		this.btn_RemoveContactPosition.addClickListener((ClickListener & Serializable) e -> this.btn_RemovePosition_clickHandler(e));
		this.btn_RemoveContactPosition.addStyleName("toolbar-button");
		this.btn_RemoveContactPosition.setEnabled(false);

		// positions datagrid
		this.dgd_Positions = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Positions.setWidth("100%");
		this.dgd_Positions.setHeight("300px");
		this.dgd_Positions.setHeaderRowHeight(30D);
		this.dgd_Positions.addSelectionListener((SelectionListener<PositionDTO> & Serializable) e -> this.dgd_Position_SelectionHandler(e));
		this.initializePositionsGrid();

		// positions button remove

		VerticalLayout pnl_Buttons = new VerticalLayout();
		pnl_Buttons.addComponents(this.btn_AddContactPosition, this.btn_RemoveContactPosition);
		pnl_Buttons.setComponentAlignment(this.btn_AddContactPosition, Alignment.BOTTOM_CENTER);
		pnl_Buttons.setComponentAlignment(this.btn_RemoveContactPosition, Alignment.TOP_CENTER);
		pnl_Buttons.setMargin(false);
		pnl_Buttons.setSpacing(true);
		pnl_Buttons.setWidth("30px");
		pnl_Buttons.setHeight("100%");

		HorizontalLayout gridAndRemoveButton = new HorizontalLayout();
		gridAndRemoveButton.addComponents(this.dgd_Positions, pnl_Buttons);
		gridAndRemoveButton.setSizeFull();
		gridAndRemoveButton.setExpandRatio(this.dgd_Positions, 1F);
		gridAndRemoveButton.setComponentAlignment(pnl_Buttons, Alignment.MIDDLE_CENTER);

		VerticalLayout positionComponent = new VerticalLayout();
		positionComponent.setMargin(false);
		positionComponent.setSpacing(true);
		positionComponent.setSizeFull();
		positionComponent.addComponents(gridAndRemoveButton);
		positionComponent.setExpandRatio(gridAndRemoveButton, 1F);

		BbrHInputFieldContainer fld_Position = new BbrHInputFieldContainer(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position_s"),
				positionComponent);

		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Accept_ClickHandler(e))
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.dispatchBtnCancelEvent())
				.build();
		bbrGroupButton.setStyleName("bbr-buttons-panel");
		this.btn_Accept = bbrGroupButton.getPrimaryButton();

		// FormLayout as Panel Content
		VerticalLayout cmp_Fields = new VerticalLayout();
		cmp_Fields.addStyleName("generic-form");
		cmp_Fields.setSizeFull();
		cmp_Fields.setMargin(false);
		cmp_Fields.addComponents(fld_Name, fld_Comment, fld_Position);
		cmp_Fields.setExpandRatio(fld_Name, 0.1F);
		cmp_Fields.setExpandRatio(fld_Comment, 0.1F);
		cmp_Fields.setExpandRatio(fld_Position, 0.8F);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(cmp_Fields, bbrGroupButton);
		mainLayout.setComponentAlignment(cmp_Fields, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(cmp_Fields, 1F);
		mainLayout.setExpandRatio(bbrGroupButton, 0.1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth("500px");
		this.setHeight("450px");
		this.setResizable(false);
		String winTitle;
		if (this.isEdit)
		{
			winTitle = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_edit_classification");
			this.txt_Name.addStyleName("bbr-text-field-read-only");
			this.txt_Name.setReadOnly(true);
		}
		else
		{
			winTitle = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_new_classification");
		}
		this.setCaption(winTitle);
		this.setContent(mainLayout);
		this.addStyleName("win-details");

		this.positionsWork = new BbrWork<PositionResultDTO>(this, this.getBbrUIParent(), this);
		this.positionsWork.addFunction(new Function<Object, PositionResultDTO>()
		{
			@Override
			public PositionResultDTO apply(Object t)
			{
				return executeService(CreateEditClassification.this.getBbrUIParent());
			}

		});

		this.startWaiting();
		this.executeBbrWork(this.positionsWork);
		if (this.isEdit)
		{
			this.txt_Name.setValue(selectedClassification.getName());
			this.txt_Comment.setValue(selectedClassification.getComment());
			this.positions = new ArrayList<>(Arrays.stream(selectedClassification.getPositions()).collect(Collectors.toList()));
			this.setContactPositionsGridDataProvider(positions);
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CreateEditClassification senderReport = (CreateEditClassification) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdatePositions(result, sender);
				if (isEdit)
				{
					senderReport.txt_Comment.focus();
				}
				else
				{
					senderReport.txt_Name.focus();
				}
			}
			else if (triggerObject == senderReport.btn_Accept)
			{
				// MOSTRAR MENSAJE DE EXITO
				if (isEdit)
				{
					this.doUpdateEditedClassification(result, senderReport);
				}
				else
				{
					this.doUpdateCreatedClassification(result, senderReport);

				}

			}
		}
		else
		{
			if(!senderReport.getBbrUIParent().hasAlertWindowOpen())
			senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}

	}

	private void doUpdateCreatedClassification(Object result, CreateEditClassification sender)
	{
		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				sender.showInfoMessage(I18NManager.getI18NString(sender.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
						I18NManager.getI18NString(sender.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "confirm_new_classification",
								sender.initParam.getName())
								+ "<br>" + I18NManager.getI18NString(sender.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "obs_confirm_new_classification"),
						null,
						() -> this.dispatchBtnAcceptEvent());
			}
		}
		this.stopWaiting();
	}

	private void doUpdateEditedClassification(Object result, CreateEditClassification sender)
	{
		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				sender.showInfoMessage(I18NManager.getI18NString(sender.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
						I18NManager.getI18NString(sender.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "confirm_edited_classification",
								sender.initParam.getName()),
						null,
						() -> this.dispatchBtnAcceptEvent());
			}
		}
		this.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void editClassification()
	{
		editWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_Accept);
		editWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeEditService(CreateEditClassification.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(editWork);
	}

	private void createClassification()
	{
		addWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_Accept);
		addWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeAddService(CreateEditClassification.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(addWork);
	}

	private void btn_Accept_ClickHandler(ClickEvent e)
	{
		String errorMsg = this.validateData();
		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			this.initParam = this.getInitParam(isEdit);
			if (isEdit)
			{
				this.editClassification();
			}
			else
			{
				this.createClassification();
			}
		}
		else
		{
			CreateEditClassification.this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMsg);
		}
	}

	public String validateData()
	{
		String result = null;

		if (this.txt_Name == null || this.txt_Name.getValue().length() < 1 || this.txt_Name.getValue().length() > 50)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_name");
		}
		else if (this.txt_Comment.getValue().length() > 200)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_comment");
		}
		else if (this.positions == null || !(this.positions.size() > 0))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_classifications_positions");
		}

		return result;
	}

	private void dgd_Position_SelectionHandler(SelectionEvent<PositionDTO> e)
	{
		if (e.getFirstSelectedItem().isPresent())
		{
			PositionDTO selected = e.getFirstSelectedItem().get();
			if (selected != null)
			{
				this.positionGridSelected = selected;
				this.btn_RemoveContactPosition.setEnabled(true);
			}
		}
		else
		{
			this.btn_RemoveContactPosition.setEnabled(false);
		}
	}

	private void btn_RemovePosition_clickHandler(ClickEvent e)
	{
		if (this.positionGridSelected != null)
		{
			this.positions.remove(this.positionGridSelected);
			this.refreshContactPositionsGrid();
			this.btn_RemoveContactPosition.setEnabled(false);
		}
	}

	private void btn_AddPosition_ClickHandler(ContactProviderPositionDTO positionName)
	{
		NewPosition winAddPosition = new NewPosition(this.getBbrUIParent(), this.allPositionsList);
		winAddPosition.initializeView();
		winAddPosition.addBbrEventListener((BbrEventListener & Serializable) e -> this.add_ProviderContactPosition_EventHandler(e));
		winAddPosition.open(true);
	}

	private void add_ProviderContactPosition_EventHandler(BbrEvent e)
	{
		if (e.getEventType().equals(NewPositionProviderContact.BTN_ACCEPT) && e.getResultObject() != null)
		{
			PositionDTO result = (PositionDTO) e.getResultObject();

			PositionDTO position = this.getPositionResultMapped(result);

			boolean isExistingPositionOfContact = this.isExistingPosition(this.positions, position);
			boolean isExistingPositionOfAll = this.isExistingPosition(this.allPositionsList, position);

			if (!isExistingPositionOfContact && !isExistingPositionOfAll)
			{
				BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_add_new_cp_position", position.getName()),
						() -> this.addToContactPositionsGrid(position));
			}
			else if (!isExistingPositionOfContact && isExistingPositionOfAll)
			{
				this.addToContactPositionsGrid(position);
			}
			else if (isExistingPositionOfContact && isExistingPositionOfAll)
			{
				PositionDTO selectPosition = this.getSamePositionContained(this.positions, position);
				this.dgd_Positions.select(selectPosition);

				// this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(),
				// BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_error"),
				// I18NManager.getI18NString(this.getBbrUIParent(),
				// BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_position"));
			}

		}
	}

	private PositionDTO getSamePositionContained(List<PositionDTO> positions, PositionDTO position)
	{
		return positions.stream().filter(p -> (position != null) && (p.getName().trim().equalsIgnoreCase(position.getName().trim()))).findFirst().orElse(null);
	}

	private void addToContactPositionsGrid(PositionDTO... position)
	{
		for (PositionDTO p : position)
		{
			if (!this.positions.contains(p))
			{
				this.positions.add(p);
			}
		}
		this.setContactPositionsGridDataProvider(this.positions);
		this.dgd_Positions.getDataProvider().refreshAll();
	}

	// private void removeFromContactPositionsGrid(PositionDTO... position)
	// {
	// this.positions.removeAll(Arrays.asList(position));
	// this.refreshContactPositionsGrid();
	// }

	private void refreshContactPositionsGrid()
	{
		this.dgd_Positions.getDataProvider().refreshAll();
		this.dgd_Positions.recalculateColumnWidths();
	}

	private boolean isExistingPosition(List<PositionDTO> positions, PositionDTO position)
	{
		boolean result = false;
		result = positions.stream().anyMatch(data -> data.getName().trim().equalsIgnoreCase(position.getName().trim()));
		return result;
	}

	private PositionDTO getPositionResultMapped(PositionDTO result)
	{
		PositionDTO position = new PositionDTO();
		position.setName(result.getName());
		position.setId(result.getId() != null ? result.getId() : -1);
		position.setSelected(result.getSelected());

		return position;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void dispatchBtnAcceptEvent()
	{
		this.dispatchEventType(BTN_ACCEPT);
		this.close();
	}

	private void dispatchBtnCancelEvent()
	{
		this.close();
	}

	private void dispatchEventType(String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private BaseResultDTO executeAddService(BbrUI bbrUI)
	{
		BaseResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).doAddCompanyClassification(this.initParam);
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

	private BaseResultDTO executeEditService(BbrUI bbrUI)
	{
		BaseResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).doEditCompanyClassification(this.initParam);
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

	private CompanyClassificationInitParamDTO getInitParam(Boolean isEdit)
	{
		CompanyClassificationInitParamDTO result = new CompanyClassificationInitParamDTO();

		if (isEdit)
		{
			result.setCsekey(this.selectedClassification.getId());
		}
		result.setName(this.txt_Name.getValue());
		result.setComment(this.txt_Comment.getValue());

		PositionDTO[] positions = this.positions.toArray(new PositionDTO[this.positions.size()]);
		result.setPositions(positions);

		LogInfoUserDTO logInfoUser = getLogInfoUser(this.getBbrUIParent());

		result.setLoginfo(logInfoUser);

		return result;
	}

	private LogInfoUserDTO getLogInfoUser(BbrUI bbrUI)
	{
		LogInfoUserDTO loginfo = new LogInfoUserDTO();
		loginfo.setUsername(bbrUI.getUser().getName());
		loginfo.setUserlastname(bbrUI.getUser().getLastName());
		loginfo.setWhen(LocalDateTime.now());
		return loginfo;
	}

	private PositionResultDTO executeService(BbrUI bbrUI)
	{
		PositionResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAllPosition();
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

	private void doUpdatePositions(Object result, BbrWorkExecutor sender)
	{
		PositionResultDTO positionsResult = (PositionResultDTO) result;
		BbrError error = ErrorsMngr.getInstance().getError(positionsResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
		if (!error.hasError())
		{
			PositionDTO[] positions = positionsResult.getPositionDTOs();
			this.allPositionsList = Arrays.asList(positions);
		}
		this.stopWaiting();
	}

	private void setContactPositionsGridDataProvider(List<PositionDTO> positions)
	{
		this.positions = positions;
		ListDataProvider<PositionDTO> dataprovider = new ListDataProvider<>(this.positions);
		this.dgd_Positions.setDataProvider(dataprovider);
	}

	private void initializePositionsGrid()
	{
		this.dgd_Positions.addCustomColumn(PositionDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position_s"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId("name");
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
