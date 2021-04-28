package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.function.Function;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class DownloadProductsByTemplate extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID	= -2928184568999564047L;

	private BbrComboBox<ClientDTO>					cbx_Clients			= null;
	private BbrComboBox<ArticleTypeDataDTO>		cbx_Templates		= null;
	CPItemDTO[]												currentItems		= null;
	Button													btn_Accept			= null;

	private BbrWork<ArticleTypeArrayResultDTO>	templatesWork		= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadWork		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public DownloadProductsByTemplate(BbrUI parent, CPItemDTO[] currentItems)
	{
		super(parent);
		this.currentItems = currentItems;
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
		// Sección Cliente
		Label lbl_Client = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"));
		lbl_Client.setWidth("90px");

		this.updateClientsComboBox();

		HorizontalLayout pnlClient = new HorizontalLayout(lbl_Client, this.cbx_Clients);
		pnlClient.setExpandRatio(this.cbx_Clients, 1F);
		pnlClient.setSizeFull();

		// Sección Plantilla
		Label lbl_Template = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template"));
		lbl_Template.setWidth("90px");

		this.cbx_Templates = this.getTemplatesComboBox();

		HorizontalLayout pnlTemplate = new HorizontalLayout(lbl_Template, this.cbx_Templates);
		pnlTemplate.setExpandRatio(this.cbx_Templates, 1F);
		pnlTemplate.setSizeFull();

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlClient, pnlTemplate);

		// Accept Button
		this.btn_Accept = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download"));
		this.btn_Accept.setStyleName("primary");
		this.btn_Accept.addStyleName("btn-generic");
		this.btn_Accept.setWidth("100%");
		this.btn_Accept.addClickListener((ClickListener & Serializable) e -> btnSelect_clickHandler(e));
		this.btn_Accept.setClickShortcut(KeyCode.ENTER);

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Accept, btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");
		buttonsPanel.setWidth("300px");
		buttonsPanel.setSpacing(true);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(optionsPanel, buttonsPanel);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(buttonsPanel, 1F);
		mainLayout.setMargin(false);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		this.setWidth(500, Unit.PIXELS);
		this.setHeight(200, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_standarized_products"));
		this.setContent(mainLayout);

		templatesWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		templatesWork.addFunction(new Function<Object, ArticleTypeArrayResultDTO>()
		{
			@Override
			public ArticleTypeArrayResultDTO apply(Object t)
			{
				return executeGetTemplatesService(DownloadProductsByTemplate.this.getBbrUIParent());
			}
		});
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		DownloadProductsByTemplate bbrSender = (DownloadProductsByTemplate) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateTemplates(result, sender);
			}
			else if (triggerObject == this.btn_Accept)
			{
				bbrSender.doDownloadFile(result, sender);
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

	private void clientsFilter_changeHandler(ValueChangeEvent<ClientDTO> e)
	{
		if((this.cbx_Clients != null) && (this.cbx_Clients.getSelectedValue() != null))
		{
			this.startWaiting();
			this.executeBbrWork(this.templatesWork);
		}
	}


	private void btnSelect_clickHandler(ClickEvent event)
	{
		String errorMsg = this.validateData();

		if (errorMsg == null)
		{
			downloadWork = new BbrWork<>(this, this.getBbrUIParent(), btn_Accept);
			downloadWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
			{
				@Override
				public FileDownloadInfoResultDTO apply(Object t)
				{
					return executeDownloadService(DownloadProductsByTemplate.this.getBbrUIParent(), DownloadFormats.XLS, btn_DownloadTriggerButton, null);
				}
			});

			this.startWaiting();
			this.executeBbrWork(downloadWork);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private ArticleTypeArrayResultDTO executeGetTemplatesService(BbrUI bbrUI)
	{
		ArticleTypeArrayResultDTO result = null;

		Integer requestedPage = 1;
		Integer maxRow = 100;

		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(true);
		initParam.setDescription(null);
		initParam.setClientname(this.cbx_Clients.getSelectedValue().getInternalname());
		initParam.setType(FEPConstants.ARTICLETYPE_CLASS_NAME_CP);

		OrderCriteriaDTO ordercriteria[] = new OrderCriteriaDTO[1];
		OrderCriteriaDTO order = new OrderCriteriaDTO();
		order.setPropertyname("description");
		order.setAscending(true);
		ordercriteria[0] = order;

		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getArticleTypeData(initParam,
			requestedPage,
			maxRow,
			false,
			ordercriteria,
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


	private void doUpdateTemplates(Object result, BbrWorkExecutor sender)
	{
		DownloadProductsByTemplate senderReport = (DownloadProductsByTemplate) sender;

		if (result != null)
		{
			ArticleTypeArrayResultDTO serviceResult = (ArticleTypeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(serviceResult, this.getBbrUIParent(), false);

			if (!error.hasError())
			{
				senderReport.updateComboBoxData(serviceResult);
			}
			else
			{
				ArticleTypeDataDTO emptyResult = new ArticleTypeDataDTO();
				emptyResult.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

				senderReport.cbx_Templates.setItems(emptyResult);
				senderReport.cbx_Templates.setSelectedItem(emptyResult);
				senderReport.cbx_Templates.setEnabled(false);
			}
		}

		senderReport.stopWaiting();
	}
	
	
	private void doDownloadFile(Object result, BbrWorkExecutor sender)
	{
		DownloadProductsByTemplate senderReport = (DownloadProductsByTemplate) sender;

		if (result != null)
		{
			FileDownloadInfoResultDTO serviceResult = (FileDownloadInfoResultDTO) result;
			BbrError error = ErrorsMngr.getInstance().getError(serviceResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				senderReport.downloadLinkFile(serviceResult);
				senderReport.close();
			}
		}

		senderReport.stopWaiting();
	}


	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;

		DefinableAttributeInitParamDTO initParam = new DefinableAttributeInitParamDTO();
		initParam.setArticletypeid(this.cbx_Templates.getValue().getId());
		initParam.setLocale(bbrUIParent.getUser().getLocale());
		initParam.setQuerytype(-1);
		initParam.setClientname(this.cbx_Clients.getValue().getInternalname());

		if (selectedFormat != null)
		{
			try
			{
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloaStandardProductSheet(initParam, this.currentItems, true, true, bbrUIParent.getUser().getId(), bbrUIParent.getUser().getLocale());
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


	private String validateData()
	{
		String result = null;

		if (this.cbx_Templates.getValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "templates_requires");
		}

		return result;
	}


	private BbrComboBox<ArticleTypeDataDTO> getTemplatesComboBox()
	{
		BbrComboBox<ArticleTypeDataDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			result = new BbrComboBox<ArticleTypeDataDTO>();
			result.setItemCaptionGenerator(ArticleTypeDataDTO::getDescription);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.setWidth("100%");
		}

		return result;
	}


	private void updateComboBoxData(ArticleTypeArrayResultDTO templatesData)
	{
		if ((templatesData != null) && (templatesData.getValues() != null) && (templatesData.getValues().length > 0))
		{
			this.cbx_Templates.setEnabled(true);
			this.cbx_Templates.setItems(templatesData.getValues());
			this.cbx_Templates.selectFirstItem();
			this.cbx_Templates.addStyleName("bbr-filter-fields");
		}
		else
		{
			ArticleTypeDataDTO emptyResult = new ArticleTypeDataDTO();
			emptyResult.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

			this.cbx_Templates.setItems(emptyResult);
			this.cbx_Templates.setSelectedItem(emptyResult);
			this.cbx_Templates.setEnabled(false);
		}
	}


	private BbrComboBox<ClientDTO> updateClientsComboBox()
	{
		BbrComboBox<ClientDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				ClientInitParamDTO initparam = new ClientInitParamDTO();
				initparam.setIsRetail(true);
				
				ClientArrayResultDTO clientsResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getAllClients(initparam);

				this.cbx_Clients = new BbrComboBox<ClientDTO>();
				this.cbx_Clients.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "select_client"));
				this.cbx_Clients.setItemCaptionGenerator(ClientDTO::getName);
				this.cbx_Clients.setTextInputAllowed(false);
				this.cbx_Clients.setEmptySelectionAllowed(false);
				this.cbx_Clients.setWidth("100%");
				this.cbx_Clients.addValueChangeListener((Serializable & ValueChangeListener<ClientDTO>) e -> this.clientsFilter_changeHandler(e));

				if ((clientsResult != null) && (clientsResult.getValues() != null) && (clientsResult.getValues().length > 0))
				{
					this.cbx_Clients.setItems(clientsResult.getValues());
					this.cbx_Clients.addStyleName("bbr-filter-fields");
				//	this.cbx_Clients.selectFirstItem();
				}
				else
				{
					ClientDTO emptyResult = new ClientDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_clients_available"));

					this.cbx_Clients.setItems(emptyResult);
					this.cbx_Clients.setSelectedItem(emptyResult);
					this.cbx_Clients.setEnabled(false);
				}
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
