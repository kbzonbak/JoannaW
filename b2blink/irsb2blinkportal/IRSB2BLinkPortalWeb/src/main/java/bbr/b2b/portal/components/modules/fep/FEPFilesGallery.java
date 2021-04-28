package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.storage.classes.FileObjectArrayResultDTO;
import bbr.b2b.fep.storage.classes.FileObjectDataDTO;
import bbr.b2b.fep.storage.classes.FileObjectInitParamDTO;
import bbr.b2b.fep.storage.classes.FileObjectResultDTO;
import bbr.b2b.fep.storage.classes.FileStorageInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.ui.gallery.BbrComponentGallery;
import bbr.b2b.portal.classes.ui.gallery.BbrComponentInListGallery;
import bbr.b2b.portal.classes.ui.gallery.BbrSelectionMode;
import bbr.b2b.portal.classes.ui.gallery.GalleryItem;
import bbr.b2b.portal.classes.ui.gallery.GalleryItemFiles;
import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrComponentGalleryInterface;
import bbr.b2b.portal.classes.ui.gallery.interfaces.BbrGalleryItem;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.InitParamsUtil;
import bbr.b2b.portal.classes.wrappers.fep.ItemValueMetadata;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class FEPFilesGallery extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long								serialVersionUID	= -8454585578751490L;

	private BbrWork<FileObjectArrayResultDTO>				reportWork			= null;

	private Upload											btn_UploadImage		= null;

	private Button											btn_Refresh			= null;

	private Button											btn_Select			= null;

	private BbrComponentGalleryInterface<FileObjectDataDTO>	gallery				= null;

	private BbrWork<FileObjectResultDTO>					uploadWork			= null;

	private DefinableAttributeDataDTO						defAttribute		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public FEPFilesGallery(BbrUI parent, DefinableAttributeDataDTO defAttribute)
	{
		super(parent);
		this.defAttribute = defAttribute;
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
		if (this.defAttribute != null)
		{
			// Main Layout
			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			this.btn_UploadImage = new Upload(null, uploaderReceiver);
			this.btn_UploadImage.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_upload_image"));
			this.btn_UploadImage.addStyleName("bbr-upload-excel-button");
			this.btn_UploadImage.addFinishedListener((FinishedListener & Serializable) e -> uploadImageFinished_handler(e));

			leftButtonsBar.addComponents(this.btn_UploadImage);

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			// Panel de la Galería
			if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_IMAGE))
			{
				gallery = new BbrComponentGallery<>();
			}

			if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FILE))
			{
				gallery = new BbrComponentInListGallery<>();
			}
			gallery.setSelectionMode(BbrSelectionMode.SINGLE);
			// gallery.setSizeFull();

			VerticalLayout pnlImages = new VerticalLayout();
			pnlImages.setSizeFull();
			pnlImages.setMargin(false);
			pnlImages.addComponent((Component) gallery);
			pnlImages.addStyleName("bbr-overflow-auto override");

			// Accept Button
			this.btn_Select = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "select"));
			this.btn_Select.setStyleName("primary");
			this.btn_Select.addStyleName("btn-generic");
			this.btn_Select.setWidth("150px");
			this.btn_Select.addClickListener((ClickListener & Serializable) e -> btn_SelectImage_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(this.btn_Select);
			buttonsPanel.addStyleName("bbr-buttons-panel");
			buttonsPanel.setSpacing(true);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(toolBar, pnlImages, buttonsPanel);
			mainLayout.setSizeFull();
			mainLayout.setSpacing(true);
			mainLayout.setExpandRatio(pnlImages, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.addStyleName("bbr-margin-windows");

			String winTitle = BbrUtils.getInstance().substitute(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_image_galley"), "", this.defAttribute.getAttributevendorname());
			// Ventana
			this.setWidth("950px");
			this.setHeight("500px");
			this.setCaption(winTitle);
			this.setContent(mainLayout);

			this.refreshReport();

			this.updateButtons(false);
		}
	}

	private void refreshReport_clickHandler(ClickEvent e)
	{
		this.refreshReport();
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			FEPFilesGallery bbrSender = (FEPFilesGallery) sender;
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject.equals(this.btn_UploadImage))
			{
				bbrSender.reportUploaded(result, sender);
			}

			bbrSender.updateButtons(false);
		}
		else
		{
			FEPFilesGallery senderReport = (FEPFilesGallery) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
			senderReport.updateButtons(false);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void uploadImageFinished_handler(FinishedEvent e)
	{
		this.uploadWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_UploadImage);
		this.uploadWork.addFunction(new Function<Object, FileObjectResultDTO>()
		{
			@Override
			public FileObjectResultDTO apply(Object t)
			{
				String filename = e.getFilename();

				return executeUploadService(FEPFilesGallery.this.getBbrUIParent(), filename);
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.uploadWork);
	}

	private void btn_SelectImage_clickHandler(ClickEvent e)
	{
		if (gallery.getSelectedItems() != null && !gallery.getSelectedItems().isEmpty())
		{
			String filename = gallery.getSelectedItems().get(0).getValue().getName();
			String url = gallery.getSelectedItems().get(0).getValue().getUri();
			ItemValueMetadata valueMetadata = new ItemValueMetadata(filename, url);
			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			bbrEvent.setResultObject(valueMetadata);
			this.dispatchBbrEvent(bbrEvent);
			this.close();
		}
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private FileObjectArrayResultDTO executeService(BbrUI bbrUIParent)
	{
		FileObjectArrayResultDTO result = null;
		if (this.getBbrUIParent() != null && this.getBbrUIParent().getUser() != null && this.getBbrUIParent().getUser().getEnterpriseCode() != null)
		{
			try
			{
				result = EJBFactory.getFEPEJBFactory().getFEPFileStorageManagerLocal(bbrUIParent).getFileObjects(this.getInitParams());
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

		return result;
	}

	private FileObjectResultDTO executeUploadService(BbrUI bbrUIParent, String filename)
	{
		FileObjectResultDTO result = null;
		try
		{
			// Start Tracking
			result = EJBFactory.getFEPEJBFactory().getFEPFileStorageManagerLocal(bbrUIParent).addOrUpdateFile(this.getUploadInitParams(filename), this.getBbrUIParent().getUser().getId());
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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		FEPFilesGallery senderReport = (FEPFilesGallery) sender;

		if (result != null)
		{
			FileObjectArrayResultDTO reportResult = (FileObjectArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				if (reportResult.getValues() != null && reportResult.getValues().length > 0)
				{
					senderReport.updateGalleryItems(reportResult.getValues());
				}
			}
		}

		senderReport.stopWaiting();

	}

	private void updateGalleryItems(FileObjectDataDTO[] fileObjects)
	{
		List<BbrGalleryItem<FileObjectDataDTO>> lstItems = new ArrayList<>();
		if (fileObjects != null && fileObjects.length > 0)
		{
			if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_IMAGE))
			{
				for (FileObjectDataDTO fileObject : fileObjects)
				{
					if (fileObject.getUri() != null)
					{
						Resource resource = new ExternalResource(fileObject.getUri());
						BbrGalleryItem<FileObjectDataDTO> item = new GalleryItem<FileObjectDataDTO>(fileObject, resource, fileObject.getDescription());
						lstItems.add(item);
					}
				}
			}
			else if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FILE))
			{
				for (FileObjectDataDTO fileObject : fileObjects)
				{
					String[] parts = fileObject.getName().split("\\.");
					String ext = parts[parts.length - 1];
					Resource resource = this.getResourceCase(ext);
					BbrGalleryItem<FileObjectDataDTO> item = new GalleryItemFiles<FileObjectDataDTO>(fileObject, resource, fileObject.getName());
					lstItems.add(item);
				}
			}

			gallery.setGalleryItems(lstItems);
		}
	}

	private Resource getResourceCase(String ext)
	{
		Resource result = null;

		if (ext.toLowerCase().trim().equals("xls") || ext.toLowerCase().trim().equals("xlsx") || ext.toLowerCase().trim().equals("csv"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_excel.png");
		}
		else if (ext.toLowerCase().trim().equals("pdf"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_pdf.png");
		}
		else if (ext.toLowerCase().trim().equals("rar"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_rar.png");
		}
		else if (ext.toLowerCase().trim().equals("doc") || ext.toLowerCase().trim().equals("docx"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_word.png");
		}
		else if (ext.toLowerCase().trim().equals("ppt") || ext.toLowerCase().trim().equals("pptx"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_ppt.png");
		}
		else if (ext.toLowerCase().trim().equals("txt"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_txt.png");
		}
		else if (ext.toLowerCase().trim().equals("zip"))
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_zip.png");
		}
		else
		{
			result = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrAppConstants.PATH_BASE_IMAGES_FILES + "icon_generic.png");
		}

		return result;
	}

	private FileStorageInitParamDTO getInitParams()
	{
		FileStorageInitParamDTO result = InitParamsUtil.getBaseInitParamInstance(FileStorageInitParamDTO.class, this.getBbrUIParent());
		if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_IMAGE))
		{
			result.setArchivetypeid(FEPConstants.IMAGE_FILE);
		}
		else if (this.defAttribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_FILE))
		{
			result.setArchivetypeid(FEPConstants.DOCUMENT_FILE);
		}
		result.setOwnercode(this.getBbrUIParent().getUser().getEnterpriseCode());

		return result;
	}

	private FileObjectInitParamDTO getUploadInitParams(String fileName)
	{
		FileObjectInitParamDTO result = InitParamsUtil.getBaseInitParamInstance(FileObjectInitParamDTO.class, this.getBbrUIParent());

		FileObjectDataDTO imgObject = new FileObjectDataDTO();
		imgObject.setName(fileName);
		imgObject.setOwnercode(this.getBbrUIParent().getUser().getEnterpriseCode());

		result.setFileObject(imgObject);

		return result;
	}

	private void refreshReport()
	{
		this.startWaiting();

		this.reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.reportWork.addFunction(new Function<Object, FileObjectArrayResultDTO>()
		{

			@Override
			public FileObjectArrayResultDTO apply(Object t)
			{
				return executeService(FEPFilesGallery.this.getBbrUIParent());
			}
		});
		this.executeBbrWork(this.reportWork);
	}

	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		FEPFilesGallery senderReport = (FEPFilesGallery) sender;

		if (result != null)
		{
			FileObjectResultDTO reportResult = (FileObjectResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			if (!error.hasError())
			{
				senderReport.refreshReport();
			}
		}

		senderReport.stopWaiting();
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_UploadImage.setEnabled(true);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
