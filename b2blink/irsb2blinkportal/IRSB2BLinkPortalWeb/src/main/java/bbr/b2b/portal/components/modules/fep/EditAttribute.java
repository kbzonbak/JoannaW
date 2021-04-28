package bbr.b2b.portal.components.modules.fep;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeConstraintDataDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesErrorsResultDTO;
import bbr.b2b.fep.common.data.classes.ConstraintParameterDTO;
import bbr.b2b.fep.common.data.classes.ConstraintParameterDataDTO;
import bbr.b2b.fep.common.data.classes.ConstraintTypesResultDTO;
import bbr.b2b.fep.common.data.classes.LanguageArrayResultDTO;
import bbr.b2b.fep.common.data.classes.LanguageDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FEPImageFactory;
import bbr.b2b.portal.classes.utils.fep.FEPOthersFilesFactory;
import bbr.b2b.portal.classes.wrappers.fep.AttributesInfo;
import bbr.b2b.portal.classes.wrappers.fep.CodeListParamsInfo;
import bbr.b2b.portal.classes.wrappers.fep.ConstrainParamsInfo;
import bbr.b2b.portal.classes.wrappers.fep.HelpFileValues;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.mdm.sections.EditAttributeDTOSettings;
import bbr.b2b.portal.components.renderers.grid_details.AllowedValuesRenderer;
import bbr.b2b.portal.components.renderers.grid_details.MDMImageHelpFilesRender;
import bbr.b2b.portal.components.utils.fep.AttributeValueID;
import bbr.b2b.portal.components.utils.fep.LanguagesAttributesValue;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.utils.FEPUtils;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class EditAttribute extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long										serialVersionUID				= 4946776453603112345L;

	private static final String									NAME								= "name";
	private static final String									PARAMS							= "params";
	private static final String									FILE_NAME						= "filename";
	private static final String									SIZE								= "size";
	private static final String									WIDTH								= "width";
	private static final String									HEIGHT							= "height";

	private AttributesInfo											initParam						= null;

	private BbrTabContent											tabCont_Restrictions			= null;
	private BbrTabContent											tabCont_AllowedValues		= null;
	private BbrTabContent											tabCont_HelpFile				= null;
	private BbrTabSheet												tabNav_Attributes				= null;

	private Button														btn_AddAllowedValues			= null;
	private Upload														btn_AddFiles					= null;
	private Button														btn_AddRestrictions			= null;
	private Button														btn_DeleteAllowedValues		= null;
	private Button														btn_DeleteConstrains			= null;
	private Button														btn_DeleteFiles				= null;
	private Button														btn_DownloadAllowedValues	= null;
	private Button														btn_Save							= null;
	private Upload														btn_UploadExcel				= null;

	private BbrAdvancedGrid<AttributeConstraintDataDTO>	datGrid_Constrains			= null;
	private BbrAdvancedGrid<LanguagesAttributesValue>		datGrid_AllowedValues		= null;
	private BbrAdvancedGrid<HelpFileValues>					datGrid_Files					= null;

	private LanguageDTO[]											languageArrayResultDTO		= null;
	private ArrayList<AttributeConstraintDataDTO>			constraintList					= new ArrayList<AttributeConstraintDataDTO>();
	private ArrayList<LanguagesAttributesValue>				valuesAllowedList				= new ArrayList<LanguagesAttributesValue>();
	private ArrayList<HelpFileValues>							helpFileList					= new ArrayList<HelpFileValues>();
	private BbrWork<AttributeResultDTO>							updateWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork					= null;
	private BbrWork<AttributeValuesErrorsResultDTO>			uploadWork						= null;
	private BbrWork<AttributeResultDTO>							saveWork							= null;
	private int															index;
	private int															visualorder						= 1;
	private String														artDIR							= null;
	private String														filename							= null;
	private BufferedImage											bimage							= null;
	private EditAttributeDTOSettings								sec_AttributeDTOSettings	= null;
	private UserDataTypeDTO											dataTypeSelected				= null;
	private AttributeDTO												selectedAttribute				= null;
	private AttributeDTO[]											selectedAttributes			= null;
	private UserDataTypeArrayResultDTO							dataTypesResult				= null;
	private LanguageArrayResultDTO								allLanguages					= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public EditAttribute(BbrUI parent, AttributesInfo initParam, AttributeDTO[] selectedAttributes, LanguageArrayResultDTO allLanguages)
	{
		super(parent);
		this.initParam = initParam;
		this.selectedAttributes = selectedAttributes;
		this.allLanguages = allLanguages;

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
		if (this.selectedAttributes != null && this.selectedAttributes.length > 0)
		{
			this.selectedAttribute = this.selectedAttributes[0];

			dataTypesResult = this.getDataTypesresult();

			Optional<UserDataTypeDTO> selectedDataType = Arrays.stream(dataTypesResult.getValues()).filter(v -> v.getId() == this.selectedAttribute.getDatatypeid()).findAny();
			if (selectedDataType.isPresent())
			{
				this.dataTypeSelected = selectedDataType.get();
			}

			this.tabCont_Restrictions = this.getRestrictionTab();
			this.tabCont_Restrictions.setSizeFull();

			this.tabCont_AllowedValues = this.getAllowedValuesTab();
			this.tabCont_AllowedValues.setSizeFull();

			this.tabCont_HelpFile = this.getHelpFileTab();
			this.tabCont_HelpFile.setSizeFull();

			this.tabNav_Attributes = new BbrTabSheet();
			this.tabNav_Attributes.setSizeFull();

			this.tabNav_Attributes.addBbrTab(this.tabCont_Restrictions, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "tb_restrictions"), false);
			this.tabNav_Attributes.addBbrTab(this.tabCont_AllowedValues,
			I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "tb_allowed_values"),
			false);
			this.tabNav_Attributes.addBbrTab(this.tabCont_HelpFile, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "tb_help_file"), false);

			this.updateTabs(true);

			// Sección de Configuración AttributeDTO
			this.sec_AttributeDTOSettings = new EditAttributeDTOSettings(this.getBbrUIParent(), this.dataTypesResult, this.dataTypeSelected, this.selectedAttribute);
			this.sec_AttributeDTOSettings.initializeView();
			this.sec_AttributeDTOSettings.addBbrSectionListener(BbrFilterSectionConstants.FS_DATA_TYPE, (BbrSectionEventListener & Serializable) e -> this.dataTypeChange_Listener(e));
			this.sec_AttributeDTOSettings.setSectionData(this.selectedAttribute);

			// Accept Button
			this.btn_Save = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "save"));
			this.btn_Save.setStyleName("primary");
			this.btn_Save.addStyleName("btn-generic");
			this.btn_Save.setWidth("150px");
			this.btn_Save.addClickListener((ClickListener & Serializable) e -> btn_SaveAttribute_clickHandler(e));

			// Cancel Button
			Button btn_Cancel = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "cancel"));
			btn_Cancel.setStyleName("primary");
			btn_Cancel.addStyleName("btn-generic");
			btn_Cancel.setWidth("150px");
			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btn_Cancel_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(this.btn_Save, new BbrHSeparator("40px"), btn_Cancel);
			buttonsPanel.addStyleName("bbr-buttons-panel");
			buttonsPanel.setWidth("600px");
			buttonsPanel.setSpacing(true);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(this.sec_AttributeDTOSettings, this.tabNav_Attributes, buttonsPanel);
			mainLayout.setSizeFull();
			mainLayout.setSpacing(false);
			mainLayout.setExpandRatio(this.tabNav_Attributes, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.addStyleName("bbr-margin-windows-zero-top");

			// Ventana
			this.setWidth(1000, Unit.PIXELS);
			this.setHeight(90, Unit.PERCENTAGE);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_edit_attribute"));
			this.setContent(mainLayout);

			this.startWaiting();

			this.updateWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			this.updateWork.addFunction(new Function<Object, AttributeResultDTO>()
			{

				@Override
				public AttributeResultDTO apply(Object t)
				{
					return executeUpdateService(EditAttribute.this.getBbrUIParent());
				}
			});
			this.executeBbrWork(this.updateWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			EditAttribute bbrSender = (EditAttribute) sender;
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_Save)
			{
				bbrSender.updateSavedAttribute(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				bbrSender.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject.equals(this.btn_UploadExcel))
			{
				bbrSender.reportUploaded(result, sender);
			}
		}
		else
		{
			EditAttribute senderReport = (EditAttribute) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}


	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		this.downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_DownloadTriggerButton);
		this.downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(EditAttribute.this.getBbrUIParent(), selectedFormat, EditAttribute.this.btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.downloadsWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_AddRestriction_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			AddConstrains restrictionWin = new AddConstrains(this.getBbrUIParent(), this.getConstraintTypes(), this.constraintList);
			restrictionWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> addConstraint_handler(bbrEvent));
			restrictionWin.initializeView();
			restrictionWin.open(true);
		}
	}


	private void addConstraint_handler(BbrEvent bbrEvent)
	{
		if (bbrEvent.getResultObject() != null)
		{
			ConstrainParamsInfo constraintDefinition = (ConstrainParamsInfo) bbrEvent.getResultObject();

			ConstraintParameterDataDTO[] constraintParameterData = null;
			if (constraintDefinition.getConstrain().getParams() != null)
			{
				constraintParameterData = new ConstraintParameterDataDTO[constraintDefinition.getConstrain().getParams().length];
				ConstraintParameterDTO[] constraintParameterDTO = constraintDefinition.getConstrain().getParams();
				for (int i = 0; i < constraintDefinition.getConstrain().getParams().length; i++)
				{
					constraintParameterData[i] = new ConstraintParameterDataDTO();
					constraintParameterData[i].setConsparamid(constraintParameterDTO[i].getId());
					constraintParameterData[i].setConstypeid(constraintParameterDTO[i].getConstrainttypeid());
					constraintParameterData[i].setRelatedbasictypeid(constraintParameterDTO[i].getBasictypeid());
				}

				if (!constraintDefinition.getFirstValue().isEmpty() && constraintParameterData.length >= 1)
				{
					constraintParameterData[0].setValue(constraintDefinition.getFirstValue().trim());
				}
				if (!constraintDefinition.getSecondValue().isEmpty() && constraintParameterData.length == 2)
				{
					constraintParameterData[1].setValue(constraintDefinition.getSecondValue().trim());
				}
			}
			AttributeConstraintDataDTO constraint = new AttributeConstraintDataDTO();
			constraint.setConstinternalname(constraintDefinition.getConstrain().getInternalname());
			constraint.setConstname(constraintDefinition.getConstrain().getName());
			constraint.setConstypeid(constraintDefinition.getConstrain().getId());
			constraint.setRelatedbasictypeid(constraintDefinition.getConstrain().getRelatedbasictypeid());

			constraint.setParamsdata(constraintParameterData);
			this.constraintList.add(constraint);
			ListDataProvider<AttributeConstraintDataDTO> dataprovider = new ListDataProvider<>(this.constraintList);
			this.datGrid_Constrains.setDataProvider(dataprovider);

		}
	}


	private void btn_DeleteRestrictions_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			if (this.datGrid_Constrains.getSelectedItems() != null && this.datGrid_Constrains.getSelectedItems().size() > 0)
			{
				this.datGrid_Constrains.getSelectedItems().forEach(f -> this.constraintList.remove(f));
				ListDataProvider<AttributeConstraintDataDTO> dataprovider = new ListDataProvider<>(this.constraintList);
				this.datGrid_Constrains.setDataProvider(dataprovider);
				this.datGrid_Constrains.getDataProvider().refreshAll();
			}
		}
	}


	private void btn_DeleteAllowedValues_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			if (this.datGrid_AllowedValues.getSelectedItems() != null && this.datGrid_AllowedValues.getSelectedItems().size() > 0)
			{
				this.datGrid_AllowedValues.getSelectedItems().forEach(f -> this.valuesAllowedList.remove(f));
				ListDataProvider<LanguagesAttributesValue> dataprovider = new ListDataProvider<>(this.valuesAllowedList);
				this.datGrid_AllowedValues.setDataProvider(dataprovider);
				this.datGrid_AllowedValues.getDataProvider().refreshAll();
			}
		}
	}


	private void btn_DeleteFiles_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			if (this.datGrid_Files.getSelectedItems() != null && this.datGrid_Files.getSelectedItems().size() > 0)
			{
				this.datGrid_Files.getSelectedItems().forEach(f -> this.helpFileList.remove(f));
				ListDataProvider<HelpFileValues> dataprovider = new ListDataProvider<>(this.helpFileList);
				this.datGrid_Files.setDataProvider(dataprovider);
				this.datGrid_Files.getDataProvider().refreshAll();
				this.filename = null;
			}
		}
	}


	private void btn_AddAllowedValues_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			if (FEPConstants.ATTRIBUTE_NAME_TYPE_CODEDLIST.equals(this.dataTypeSelected.getInternalname()) ||
			FEPConstants.ATTRIBUTE_NAME_TYPE_MUCODEDLIST.equals(this.dataTypeSelected.getInternalname()))
			{
				AddAllowedValuesToCodeList allowedValuesCodeListWin = new AddAllowedValuesToCodeList(this.getBbrUIParent(), this.valuesAllowedList);
				allowedValuesCodeListWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> addAllowedValuesCodeList_handler(bbrEvent));
				allowedValuesCodeListWin.initializeView();
				allowedValuesCodeListWin.open(true);
			}
			else
			{
				AddAllowedValuesToList allowedValuesListWin = new AddAllowedValuesToList(this.getBbrUIParent(), this.valuesAllowedList);
				allowedValuesListWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> addAllowedValues_handler(bbrEvent));
				allowedValuesListWin.initializeView();
				allowedValuesListWin.open(true);
			}
		}
	}


	private void btn_DownloadAllowedValues_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadAllowedValues)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}


	private void uploadExcelFinished_handler(FinishedEvent e)
	{
		this.uploadWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_UploadExcel);
		this.uploadWork.addFunction(new Function<Object, AttributeValuesErrorsResultDTO>()
		{
			@Override
			public AttributeValuesErrorsResultDTO apply(Object t)
			{
				String filename = e.getFilename();

				return executeUploadService(EditAttribute.this.getBbrUIParent(), filename);
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.uploadWork);
	}


	private void addAllowedValues_handler(BbrEvent bbrEvent)
	{
		if (bbrEvent.getResultObject() != null)
		{
			String valueOfAllowedValues = (String) bbrEvent.getResultObject();
			LanguagesAttributesValue languagesAttributesValue = new LanguagesAttributesValue();

			HashMap<AttributeValueID, Map<Long, AttributeValueDTO>> mapLanguages = new HashMap<AttributeValueID, Map<Long, AttributeValueDTO>>();
			AttributeValueID attributeValueID = new AttributeValueID();
			AttributeValueDTO attributeValueDTO = new AttributeValueDTO();

			OptionalInt max = this.valuesAllowedList.stream().flatMap(l -> l.getMapLanguages().values().stream()).mapToInt(a -> a.getVisualorder()).max();
			this.visualorder = max.isPresent() ? max.getAsInt() + 1 : this.visualorder;

			LanguageDTO languageES = Arrays.stream(this.languageArrayResultDTO).filter(l -> l.getName().equals("es")).findFirst().get();
			attributeValueDTO.setLanguagecode(languageES.getName());
			attributeValueDTO.setLanguageid(languageES.getId());
			attributeValueDTO.setValue(valueOfAllowedValues);
			attributeValueDTO.setVisualorder(this.visualorder++);
			HashMap<Long, AttributeValueDTO> values = new HashMap<Long, AttributeValueDTO>();
			values.put(languageES.getId(), attributeValueDTO);
			mapLanguages.put(attributeValueID, values);

			languagesAttributesValue.setId(attributeValueID);
			languagesAttributesValue.setMapLanguages(values);

			this.valuesAllowedList.add(languagesAttributesValue);

			ListDataProvider<LanguagesAttributesValue> dataprovider = new ListDataProvider<>(this.valuesAllowedList);

			this.datGrid_AllowedValues.setDataProvider(dataprovider);

		}
	}


	private void addAllowedValuesCodeList_handler(BbrEvent bbrEvent)
	{
		if (bbrEvent.getResultObject() != null)
		{

			LanguagesAttributesValue languagesAttributesValue = new LanguagesAttributesValue();
			CodeListParamsInfo codeListParamsInfo = (CodeListParamsInfo) bbrEvent.getResultObject();
			String valueResult = FEPUtils.getCodedValue(codeListParamsInfo.getDescription(), codeListParamsInfo.getCode());

			HashMap<AttributeValueID, Map<Long, AttributeValueDTO>> mapLanguages = new HashMap<AttributeValueID, Map<Long, AttributeValueDTO>>();
			AttributeValueID attributeValueID = new AttributeValueID();
			AttributeValueDTO attributeValueDTO = new AttributeValueDTO();

			OptionalInt max = this.valuesAllowedList.stream().flatMap(l -> l.getMapLanguages().values().stream()).mapToInt(a -> a.getVisualorder()).max();
			this.visualorder = max.isPresent() ? max.getAsInt() + 1 : this.visualorder;

			LanguageDTO languageES = Arrays.stream(this.languageArrayResultDTO).filter(l -> l.getName().equals("es")).findFirst().get();
			attributeValueDTO.setLanguagecode(languageES.getName());
			attributeValueDTO.setLanguageid(languageES.getId());
			attributeValueDTO.setValue(valueResult);
			attributeValueDTO.setVisualorder(this.visualorder++);
			HashMap<Long, AttributeValueDTO> values = new HashMap<Long, AttributeValueDTO>();
			values.put(languageES.getId(), attributeValueDTO);
			mapLanguages.put(attributeValueID, values);

			languagesAttributesValue.setId(attributeValueID);
			languagesAttributesValue.setMapLanguages(values);

			this.valuesAllowedList.add(languagesAttributesValue);

			ListDataProvider<LanguagesAttributesValue> dataprovider = new ListDataProvider<>(this.valuesAllowedList);

			this.datGrid_AllowedValues.setDataProvider(dataprovider);

		}
	}


	private void btn_Cancel_clickHandler(ClickEvent event)
	{
		this.close();
	}


	private void selectionAllowedValuesGrid_handler(SelectionEvent<LanguagesAttributesValue> e)
	{
		this.updateButtons(true);
	}


	private void selectionConstraintGrid_handler(SelectionEvent<AttributeConstraintDataDTO> e)
	{
		this.updateButtons(true);
	}


	private void selectionHelpFilesGrid_handler(SelectionEvent<HelpFileValues> e)
	{
		this.updateButtons(true);
	}


	private void btn_SaveAttribute_clickHandler(ClickEvent e)
	{
		String errorMsg = this.validateData();

		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			AttributeDTO attributedto = this.sec_AttributeDTOSettings.getSectionResult();
			attributedto.setTempfilename(this.filename);

			AttributeConstraintDataDTO[] constraints = this.constraintList.toArray(new AttributeConstraintDataDTO[this.constraintList.size()]);
			List<AttributeValueDTO> attributeValueDTOList = this.valuesAllowedList.stream().flatMap(l -> l.getMapLanguages().values().stream()).collect(Collectors.toList());
			AttributeValueDTO[] values = attributeValueDTOList.toArray(new AttributeValueDTO[attributeValueDTOList.size()]);
			this.startWaiting();
			this.saveWork = new BbrWork<>(EditAttribute.this, EditAttribute.this.getBbrUIParent(), this.btn_Save);
			this.saveWork.addFunction(new Function<Object, AttributeResultDTO>()
			{
				@Override
				public AttributeResultDTO apply(Object t)
				{
					return executeSaveAttributesService(EditAttribute.this.getBbrUIParent(), constraints, values, attributedto);
				}
			});

			this.executeBbrWork(this.saveWork);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			EditAttribute.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	private void dataTypeChange_Listener(BbrSectionEvent e)
	{
		this.dataTypeSelected = (UserDataTypeDTO) e.getResultObject();
		this.updateTabs(true);
		this.constraintList.clear();
		this.valuesAllowedList.clear();
		this.datGrid_Constrains.getDataProvider().refreshAll();
		this.datGrid_AllowedValues.getDataProvider().refreshAll();
	}


	private void uploadFinished_handler(FinishedEvent event)
	{
		HelpFileValues result = null;
		this.filename = event.getFilename();
		this.artDIR = BbrAppConstants.getUploadPathOfUser(this.getUser().getId());
		File newfile = (this.filename != null && this.artDIR != null) ? new File(this.artDIR, this.filename) : null;
		FEPImageFactory imageFactory = new FEPImageFactory();
		FEPOthersFilesFactory otherFilesFactory = new FEPOthersFilesFactory(this.getBbrUIParent());

		if (newfile != null && newfile.canRead())
		{
			try
			{
				this.bimage = ImageIO.read(newfile);
				if (this.bimage != null) // Si es una imagen
				{
					result = imageFactory.getImageLayout(newfile, this.bimage);
				}
				else // Si no es imagen
				{
					result = otherFilesFactory.getOtherLayout(newfile);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (result != null)
		{
			this.helpFileList.add(result);
			ListDataProvider<HelpFileValues> dataprovider = new ListDataProvider<>(this.helpFileList);
			this.datGrid_Files.setDataProvider(dataprovider);
			this.datGrid_Files.getDataProvider().refreshAll();
			this.updateButtons(false);
		}
	}


	private void updateTabs(boolean init)
	{
		if (init)
		{
			ConstraintTypesResultDTO constrains = this.getConstraintTypes();
			// ArrayList<ConstraintTypeDTO> constrainList= new
			// ArrayList<>(Arrays.stream(constrains.getValues()).collect(Collectors.toList()));
			// this.constraintList.add(constrainList);
			// ListDataProvider<AttributeConstraintDataDTO> dataprovider = new
			// ListDataProvider<>(this.constraintList);
			// this.datGrid_Constrains.setDataProvider(dataprovider);
			this.tabCont_AllowedValues.setVisible(dataTypeSelected != null ? FEPUtils.attributeIsValueList(this.dataTypeSelected.getInternalname()) : false);
			this.tabCont_Restrictions.setVisible((constrains != null) && (constrains.getValues() != null) && (constrains.getValues().length > 0));
		}
		else
		{
			this.tabCont_AllowedValues.setVisible(init);
			this.tabCont_Restrictions.setVisible(init);
		}
		if (this.tabNav_Attributes.getTab(this.tabCont_Restrictions) != null)
		{
			this.tabNav_Attributes.getTab(this.tabCont_Restrictions).setVisible(this.tabCont_Restrictions.isVisible());
			this.tabNav_Attributes.setSelectedTab(this.tabNav_Attributes.getTab(this.tabCont_Restrictions));
		}
		if (this.tabNav_Attributes.getTab(this.tabCont_AllowedValues) != null)
		{
			this.tabNav_Attributes.getTab(this.tabCont_AllowedValues).setVisible(this.tabCont_AllowedValues.isVisible());
			this.tabNav_Attributes.setSelectedTab(this.tabNav_Attributes.getTab(this.tabCont_AllowedValues));
		}
		if (this.tabNav_Attributes.getTab(this.tabCont_HelpFile) != null)
		{
			this.tabNav_Attributes.getTab(this.tabCont_HelpFile).setVisible(this.tabCont_HelpFile.isVisible());
		}

	}


	private ConstraintTypesResultDTO getConstraintTypes()
	{
		ConstraintTypesResultDTO constrainsResult = null;
		try
		{
			constrainsResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getConstraintTypesByDataType(this.dataTypeSelected.getId(),
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

		return constrainsResult;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private BbrTabContent getRestrictionTab()
	{
		this.btn_AddRestrictions = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"));
		this.btn_AddRestrictions.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_add_restrictions"));
		this.btn_AddRestrictions.addClickListener((ClickListener & Serializable) e -> this.btn_AddRestriction_clickHandler(e));
		this.btn_AddRestrictions.addStyleName("toolbar-button");

		this.btn_DeleteConstrains = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
		this.btn_DeleteConstrains.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "delete_restriction"));
		this.btn_DeleteConstrains.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteRestrictions_clickHandler(e));
		this.btn_DeleteConstrains.addStyleName("toolbar-button");

		HorizontalLayout pnlButtonsBar = new HorizontalLayout();
		pnlButtonsBar.addComponents(this.btn_AddRestrictions, this.btn_DeleteConstrains);
		pnlButtonsBar.setSpacing(true);
		pnlButtonsBar.setMargin(false);
		pnlButtonsBar.setHeight("30px");
		pnlButtonsBar.addStyleName("toolbar-layout");

		pnlButtonsBar.setComponentAlignment(this.btn_AddRestrictions, Alignment.MIDDLE_LEFT);

		this.datGrid_Constrains = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_Constrains.setSortable(false);

		this.initializeConstraintDataGridColumns();

		this.datGrid_Constrains.addStyleName("report-grid");
		this.datGrid_Constrains.setSizeFull();
		this.datGrid_Constrains.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_Constrains.addSelectionListener((SelectionListener<AttributeConstraintDataDTO> & Serializable) e -> selectionConstraintGrid_handler(e));

		VerticalLayout tabLayout = new VerticalLayout(pnlButtonsBar, this.datGrid_Constrains);
		tabLayout.setSpacing(false);
		tabLayout.setMargin(false);
		tabLayout.setSizeFull();
		tabLayout.setExpandRatio(this.datGrid_Constrains, 1F);
		tabLayout.setComponentAlignment(pnlButtonsBar, Alignment.TOP_LEFT);

		BbrTabContent resultingTab = new BbrTabContent();
		resultingTab.addComponents(tabLayout);
		resultingTab.setExpandRatio(tabLayout, 1F);
		resultingTab.setSizeFull();

		return resultingTab;
	}


	private void initializeConstraintDataGridColumns()
	{
		this.datGrid_Constrains.addCustomColumn(AttributeConstraintDataDTO::getConstname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_restriction_name"), true)
		.setId(NAME);
		this.datGrid_Constrains.addCustomColumn(p -> p.getParamsdata() != null ? Arrays.stream(p.getParamsdata()).map(n -> n.getValue()).collect(Collectors.joining(" - ")) : "",
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_restriction_parameter"),
		true).setId(PARAMS);
	}


	private BbrTabContent getAllowedValuesTab()
	{
		this.btn_AddAllowedValues = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"));
		this.btn_AddAllowedValues.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_add_allowed_values"));
		this.btn_AddAllowedValues.addClickListener((ClickListener & Serializable) e -> this.btn_AddAllowedValues_clickHandler(e));
		this.btn_AddAllowedValues.addStyleName("toolbar-button");

		this.btn_DeleteAllowedValues = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
		this.btn_DeleteAllowedValues.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_delete_allowed_values"));
		this.btn_DeleteAllowedValues.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteAllowedValues_clickHandler(e));
		this.btn_DeleteAllowedValues.addStyleName("toolbar-button");

		HorizontalLayout pnlLeftButtonsBar = new HorizontalLayout();
		pnlLeftButtonsBar.addComponents(this.btn_AddAllowedValues, this.btn_DeleteAllowedValues);
		pnlLeftButtonsBar.setSpacing(true);
		pnlLeftButtonsBar.setMargin(false);
		pnlLeftButtonsBar.setHeight("30px");
		pnlLeftButtonsBar.addStyleName("toolbar-layout");

		this.btn_DownloadAllowedValues = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
		this.btn_DownloadAllowedValues.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_download_allowed_values"));
		this.btn_DownloadAllowedValues.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadAllowedValues_downloadHandler(e));
		this.btn_DownloadAllowedValues.addStyleName("toolbar-button");

		BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

		this.btn_UploadExcel = new Upload(null, uploaderReceiver);
		this.btn_UploadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_upload_allowed_values"));
		this.btn_UploadExcel.addStyleName("bbr-upload-excel-button");
		this.btn_UploadExcel.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));

		HorizontalLayout pnlRightButtonsBar = new HorizontalLayout();
		pnlRightButtonsBar.addComponents(this.btn_DownloadAllowedValues, this.btn_UploadExcel);
		pnlRightButtonsBar.setSpacing(true);
		pnlRightButtonsBar.setMargin(false);
		pnlRightButtonsBar.setHeight("30px");
		pnlRightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout pnlButtonsBar = new HorizontalLayout();
		pnlButtonsBar.setWidth("100%");
		pnlButtonsBar.addComponents(pnlLeftButtonsBar, pnlRightButtonsBar);
		pnlButtonsBar.addStyleName("filter-toolbar");
		pnlButtonsBar.setExpandRatio(pnlLeftButtonsBar, 1F);
		pnlButtonsBar.setExpandRatio(pnlRightButtonsBar, 1F);
		pnlButtonsBar.setComponentAlignment(pnlLeftButtonsBar, Alignment.MIDDLE_LEFT);
		pnlButtonsBar.setComponentAlignment(pnlRightButtonsBar, Alignment.MIDDLE_RIGHT);
		pnlButtonsBar.setSpacing(true);
		pnlButtonsBar.setMargin(false);
		pnlButtonsBar.setHeight("30px");

		this.datGrid_AllowedValues = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_AllowedValues.setSortable(false);

		this.initializeAllowedValuesDataGridColumns();

		this.datGrid_AllowedValues.addStyleName("report-grid");
		this.datGrid_AllowedValues.setSizeFull();
		this.datGrid_AllowedValues.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_AllowedValues.addSelectionListener((SelectionListener<LanguagesAttributesValue> & Serializable) e -> selectionAllowedValuesGrid_handler(e));

		VerticalLayout tabLayout = new VerticalLayout(pnlButtonsBar, this.datGrid_AllowedValues);
		tabLayout.setSpacing(false);
		tabLayout.setMargin(false);
		tabLayout.setSizeFull();
		tabLayout.setExpandRatio(this.datGrid_AllowedValues, 1F);
		tabLayout.setComponentAlignment(pnlButtonsBar, Alignment.TOP_LEFT);

		BbrTabContent resultingTab = new BbrTabContent();
		resultingTab.addComponents(tabLayout);
		resultingTab.setExpandRatio(tabLayout, 1F);
		resultingTab.setSizeFull();

		return resultingTab;
	}


	private void initializeAllowedValuesDataGridColumns()
	{
		this.languageArrayResultDTO = this.allLanguages.getValues();

		for (LanguageDTO language : this.languageArrayResultDTO)
		{
			this.datGrid_AllowedValues.addColumn(languagesAttributesValue -> {
				AllowedValuesRenderer renderd = new AllowedValuesRenderer(languagesAttributesValue, language.getId());
				return renderd;
			}, new ComponentRenderer()).setCaption(language.getDescription());
		}

	}


	private BbrTabContent getHelpFileTab()
	{
		BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));
		this.btn_AddFiles = new Upload(null, uploaderReceiver);
		this.btn_AddFiles.setButtonCaption("");
		this.btn_AddFiles.addStyleName("bbr-upload-alternative-button");
		this.btn_AddFiles.addFinishedListener((FinishedListener & Serializable) e -> uploadFinished_handler(e));

		this.btn_DeleteFiles = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
		this.btn_DeleteFiles.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_delete_files"));
		this.btn_DeleteFiles.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteFiles_clickHandler(e));
		this.btn_DeleteFiles.addStyleName("toolbar-button");

		HorizontalLayout pnlButtonsBar = new HorizontalLayout();
		pnlButtonsBar.addComponents(this.btn_AddFiles, this.btn_DeleteFiles);
		pnlButtonsBar.setSpacing(true);
		pnlButtonsBar.setMargin(false);
		pnlButtonsBar.setHeight("30px");
		pnlButtonsBar.addStyleName("toolbar-layout");

		pnlButtonsBar.setComponentAlignment(this.btn_AddFiles, Alignment.MIDDLE_LEFT);

		this.datGrid_Files = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_Files.setSortable(false);

		this.initializeHelpFilesDataGridColumns();

		this.datGrid_Files.addStyleName("report-grid");
		this.datGrid_Files.setSizeFull();
		this.datGrid_Files.setSelectionMode(SelectionMode.MULTI);

		this.datGrid_Files.addSelectionListener((SelectionListener<HelpFileValues> & Serializable) e -> selectionHelpFilesGrid_handler(e));

		VerticalLayout tabLayout = new VerticalLayout(pnlButtonsBar, this.datGrid_Files);
		tabLayout.setSpacing(false);
		tabLayout.setMargin(false);
		tabLayout.setSizeFull();
		tabLayout.setExpandRatio(this.datGrid_Files, 1F);
		tabLayout.setComponentAlignment(pnlButtonsBar, Alignment.TOP_LEFT);

		BbrTabContent resultingTab = new BbrTabContent();
		resultingTab.addComponents(tabLayout);
		resultingTab.setExpandRatio(tabLayout, 1F);
		resultingTab.setSizeFull();

		this.updateButtons(false);
		return resultingTab;
	}


	private void initializeHelpFilesDataGridColumns()
	{
		this.datGrid_Files.addColumn(helpFileValues -> {
			MDMImageHelpFilesRender imageComponent = new MDMImageHelpFilesRender(helpFileValues);
			return imageComponent;
		}, new ComponentRenderer())
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "resource"))
		.setWidth(290D)
		.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());
		this.datGrid_Files.addCustomColumn(HelpFileValues::getFileName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_file_name"), true)
		.setId(FILE_NAME)
		.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());
		this.datGrid_Files.addCustomColumn(HelpFileValues::getSize, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_file_size"), true)
		.setId(SIZE)
		.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());
		this.datGrid_Files.addCustomColumn(h -> h.getWidth() != 0 ? h.getWidth() : "-", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_file_width"), true)
		.setId(WIDTH)
		.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());
		this.datGrid_Files.addCustomColumn(h -> h.getHeight() != 0 ? h.getHeight() : "-", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_file_height"), true)
		.setId(HEIGHT)
		.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());
		this.datGrid_Files.setBodyRowHeight(100);
	}


	private UserDataTypeArrayResultDTO getDataTypesresult()
	{
		UserDataTypeArrayResultDTO result = null;
		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal().getUserDataTypes(this.getBbrUIParent().getLocale().getLanguage(), true);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut();
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		return result;
	}


	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadAllowedValues)
				{
					ArrayList<AttributeValueDTO> attributesValuesList = new ArrayList<AttributeValueDTO>();
					this.valuesAllowedList.forEach(l -> l.getMapLanguages().values().forEach(v -> attributesValuesList.add(v)));
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadAttributeValues(null,
					attributesValuesList.toArray(new AttributeValueDTO[attributesValuesList.size()]),
					this.getBbrUIParent().getUser().getId(),
					selectedFormat.getValue(),
					this.getBbrUIParent().getLocale());
				}
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


	private AttributeResultDTO executeUpdateService(BbrUI bbrUIParent)
	{
		AttributeResultDTO fileResult = null;
		if (this.selectedAttributes != null)
		{
			try
			{
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).getAttribute(this.selectedAttribute.getId(),
				bbrUIParent.getLocale().getLanguage());
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


	private AttributeValuesErrorsResultDTO executeUploadService(BbrUI bbrUIParent, String filename)
	{
		AttributeValuesErrorsResultDTO result = null;
		try
		{
			// Start Tracking
			ArrayList<AttributeValueDTO> attributesValuesList = new ArrayList<AttributeValueDTO>();
			this.valuesAllowedList.forEach(l -> l.getMapLanguages().values().forEach(v -> attributesValuesList.add(v)));
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).uploadAttributesValues(filename,
			attributesValuesList.toArray(new AttributeValueDTO[attributesValuesList.size()]),
			this.getBbrUIParent().getUser().getId(),
			this.getBbrUIParent().getLocale());
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


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		EditAttribute senderReport = (EditAttribute) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}


	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		EditAttribute senderReport = (EditAttribute) sender;

		if (result != null)
		{
			AttributeValuesErrorsResultDTO reportResult = (AttributeValuesErrorsResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			if (!error.hasError())
			{
				this.valuesAllowedList.clear();
				AttributeValueDTO[] attributeValueArray = reportResult.getValues();
				List<AttributeValueDTO> attributeValueList = new ArrayList<AttributeValueDTO>(Arrays.asList(attributeValueArray));
				List<Integer> visualOrderList = attributeValueList.stream().map(m -> m.getVisualorder()).distinct().collect(Collectors.toList());

				for (this.index = 0; this.index < visualOrderList.size(); this.index++)
				{
					AttributeValueID attributeValueID = new AttributeValueID();
					HashMap<Long, AttributeValueDTO> mapLanguages = new HashMap<Long, AttributeValueDTO>();
					LanguagesAttributesValue languagesAttributesValue = new LanguagesAttributesValue();
					if (this.valuesAllowedList.stream().filter(a -> a.getId().getId().equals(visualOrderList.get(this.index))).findAny().isPresent())
					{
						for (LanguagesAttributesValue langAttrib : this.valuesAllowedList)
						{
							if (langAttrib.getId().equals(visualOrderList.get(this.index)))
							{
								attributeValueList.stream().filter(a -> a.getVisualorder() == visualOrderList.get(this.index)).forEach(a -> mapLanguages.put((long) a.getLanguageid(), a));
								langAttrib.setMapLanguages(mapLanguages);
							}
						}
					}
					else
					{
						attributeValueID.setId((long) visualOrderList.get(this.index));
						languagesAttributesValue.setId(attributeValueID);
						attributeValueList.stream().filter(a -> a.getVisualorder() == visualOrderList.get(this.index)).forEach(a -> mapLanguages.put((long) a.getLanguageid(), a));
						languagesAttributesValue.setMapLanguages(mapLanguages);
					}
					this.valuesAllowedList.add(languagesAttributesValue);

				}

				ListDataProvider<LanguagesAttributesValue> dataprovider = new ListDataProvider<>(this.valuesAllowedList);
				this.datGrid_AllowedValues.setDataProvider(dataprovider);
				this.datGrid_AllowedValues.getDataProvider().refreshAll();
				this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_COMMERCIAL, "file_processed"));
			}
			else
			{
				BbrError errorMSG = ErrorsMngr.getInstance().getError(reportResult, null, false);

				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMSG.getErrorMessage());
			}

		}
		senderReport.stopWaiting();
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_DeleteConstrains.setEnabled(this.datGrid_Constrains.getSelectedItems() != null && !this.datGrid_Constrains.getSelectedItems().isEmpty());
		this.btn_DeleteFiles.setEnabled(this.datGrid_Files.getSelectedItems() != null && !this.datGrid_Files.getSelectedItems().isEmpty());
		this.btn_AddFiles.setEnabled(this.helpFileList.size() < 1);

		this.btn_DeleteAllowedValues.setEnabled(this.datGrid_AllowedValues.getSelectedItems() != null && !this.datGrid_AllowedValues.getSelectedItems().isEmpty());
		this.btn_DownloadAllowedValues.setEnabled(this.initParam != null);
		this.btn_UploadExcel.setEnabled(this.initParam != null);
	}


	private String validateData()
	{
		String errorResult = null;

		if (this.sec_AttributeDTOSettings.validateData() != null)
		{
			errorResult = this.sec_AttributeDTOSettings.validateData();
		}

		return errorResult;
	}


	private AttributeResultDTO executeSaveAttributesService(BbrUI bbrUIParent, AttributeConstraintDataDTO[] constraints, AttributeValueDTO[] values, AttributeDTO attributedto)
	{
		AttributeResultDTO result = null;
		if (attributedto != null)
		{
			try
			{
				// Start Tracking
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal().addorEditAttribute(attributedto,
				constraints,
				values,
				this.getBbrUIParent().getLocale().getLanguage(),
				this.getUser().getId());
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


	private void updateSavedAttribute(Object result, BbrWorkExecutor sender)
	{
		EditAttribute senderReport = (EditAttribute) sender;
		BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);

		if (result != null)
		{
			AttributeResultDTO reportResult = (AttributeResultDTO) result;

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


	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		EditAttribute senderReport = (EditAttribute) sender;

		if (result != null)
		{
			AttributeResultDTO reportResult = (AttributeResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			if (!error.hasError())
			{
				if (reportResult.getConstdata() != null)
				{

					senderReport.constraintList = reportResult.getConstdata() != null ? new ArrayList<AttributeConstraintDataDTO>(Arrays.asList(reportResult.getConstdata())) : null;
					ListDataProvider<AttributeConstraintDataDTO> dataproviderConstraintData = new ListDataProvider<>(senderReport.constraintList);
					senderReport.datGrid_Constrains.setDataProvider(dataproviderConstraintData);
					senderReport.datGrid_Constrains.getDataProvider().refreshAll();
				}

				if (reportResult.getValues() != null)
				{
					senderReport.setAllAllowedValues(Arrays.asList(reportResult.getValues()));
					ListDataProvider<LanguagesAttributesValue> dataproviderAllowedValues = new ListDataProvider<>(senderReport.valuesAllowedList);
					senderReport.datGrid_AllowedValues.setDataProvider(dataproviderAllowedValues);
					senderReport.datGrid_AllowedValues.getDataProvider().refreshAll();
				}

				if (reportResult.getAttribute().getFilename() != null && reportResult.getAttribute().getFilename().length() > 0)
				{
					senderReport.chargeHelpFileToGrid(reportResult.getAttribute().getFilename());
					ListDataProvider<HelpFileValues> dataproviderHelpFiles = new ListDataProvider<>(senderReport.helpFileList);
					senderReport.datGrid_Files.setDataProvider(dataproviderHelpFiles);
					senderReport.datGrid_Files.getDataProvider().refreshAll();
				}

				this.updateButtons(false);
			}
		}

		senderReport.stopWaiting();
	}


	private void chargeHelpFileToGrid(String fileName)
	{
		HelpFileValues result = null;
		this.artDIR = FEPUtils.getInstance().getAttributesFilesPath() + "/";
		File newfile = (fileName != null && this.artDIR != null) ? new File(this.artDIR, FEPUtils.getFilenameByUri(fileName)) : null;
		FEPImageFactory imageFactory = new FEPImageFactory();
		FEPOthersFilesFactory otherFilesFactory = new FEPOthersFilesFactory(this.getBbrUIParent());

		try
		{
			URL url = new URL(fileName);

			FileUtils.copyURLToFile(url, newfile);

			if (newfile != null && newfile.canRead())
			{
				this.bimage = ImageIO.read(newfile);
				if (this.bimage != null) // Si es una imagen
				{
					result = imageFactory.getImageLayout(newfile, this.bimage);
				}
				else // Si no es imagen
				{
					result = otherFilesFactory.getOtherLayout(newfile);
				}
			}

			this.helpFileList.add(result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}


	private void setAllAllowedValues(List<AttributeValueDTO> values)
	{
		if (values != null && values.size() > 0)
		{
			List<Integer> visualOrderList = values.stream().map(m -> m.getVisualorder()).distinct().collect(Collectors.toList());

			for (this.index = 0; this.index < visualOrderList.size(); this.index++)
			{
				AttributeValueID attributeValueID = new AttributeValueID();
				HashMap<Long, AttributeValueDTO> mapLanguages = new HashMap<Long, AttributeValueDTO>();
				LanguagesAttributesValue languagesAttributesValue = new LanguagesAttributesValue();
				// if (this.valuesAllowedList.stream().filter(a ->
				// a.getId().getId().equals(visualOrderList.get(this.index))).findAny().isPresent())
				// {
				// for (LanguagesAttributesValue langAttrib :
				// this.valuesAllowedList)
				// {
				// if
				// (langAttrib.getId().equals(visualOrderList.get(this.index)))
				// {
				// values.stream().filter(a -> a.getVisualorder() ==
				// visualOrderList.get(this.index)).forEach(a ->
				// mapLanguages.put((long) a.getLanguageid(), a));
				// langAttrib.setMapLanguages(mapLanguages);
				// }
				// }
				// }
				// else
				// {
				attributeValueID.setId((long) visualOrderList.get(this.index));
				languagesAttributesValue.setId(attributeValueID);
				values.stream().filter(a -> a.getVisualorder() == visualOrderList.get(this.index)).forEach(a -> mapLanguages.put((long) a.getLanguageid(), a));
				languagesAttributesValue.setMapLanguages(mapLanguages);
				// }
				this.valuesAllowedList.add(languagesAttributesValue);
			}
		}
	}
	//
	//
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
