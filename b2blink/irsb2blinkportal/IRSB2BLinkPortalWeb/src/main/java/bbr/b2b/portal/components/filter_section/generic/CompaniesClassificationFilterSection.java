package bbr.b2b.portal.components.filter_section.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.addons.ComboBoxMultiselect;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.management.CompanyClassificationFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyClassificationArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyClassificationDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

/**
 * Representa la secci�n de un filtro relacionada con los datos de los
 * productos.
 */
public class CompaniesClassificationFilterSection extends BbrVerticalSection<CompanyClassificationFilterSectionInfo>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************
	// Constants
	private static final long serialVersionUID = 4515852344551366544L;

	/**
	 * Crea una nueva instancia de la secci�n de productos.
	 *
	 * @param enableFirstSubsection
	 *            Define si se mostrar� la subsecci�n de Categor�as
	 * @param enableSecondSubsection
	 *            Define si se mostrar� la subsecci�n de Marcas
	 * @param enableActiveProductsSelection
	 *            Define si se mostrar� la subsecci�n de Productos Activos
	 */
	public CompaniesClassificationFilterSection(BbrUI parent, Boolean enableFirstSubsection, Boolean enableSecondSubsection)
	{
		super(parent);
		this.enableFirstSubsection = enableFirstSubsection;
		this.enableSecondSubsection = enableSecondSubsection;
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Components
	private ComboBoxMultiselect<CompanyClassificationDTO>	cbx_Classifications		= null;
	private BbrTextField									txt_Code				= null;
	private BbrTextField									txt_SocialReason		= null;
	private BbrComboBox<SearchCriterion>					cbx_FilterCriteria		= null;
	private HorizontalLayout								pnlSecondSubsection		= null;
	private String											filterSectionTitle		= "";
	private Boolean											enableFirstSubsection	= true;
	private String											firstSubsectionTitle	= "";
	private Boolean											enableSecondSubsection	= true;
	private SearchCriterion									selectedCriteria		= null;

	public void setFilterSectionTitle(String filterSectionTitle)
	{
		this.filterSectionTitle = filterSectionTitle;
	}

	public void setFirstSubsectionTitle(String firstSubsectionTitle)
	{
		this.firstSubsectionTitle = firstSubsectionTitle;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		// Header
		Label lbl_Title = new Label(!StringUtils.isEmpty(this.filterSectionTitle) ? this.filterSectionTitle
				: I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "filter"));

		lbl_Title.addStyleName("bbr-panel-space");
		lbl_Title.setWidth("200px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_Title);
		pnlHeader.setWidth("100%");

		// Sección Primera
		HorizontalLayout pnlFirstSubsection = this.getFirstSubsectionPanel();
		// Sección Segunda
		this.pnlSecondSubsection = this.getSecondSubsectionPanel(null);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlFirstSubsection, this.pnlSecondSubsection);

	}

	@Override
	public CompanyClassificationFilterSectionInfo getSectionResult()
	{

		Integer filtertype = this.cbx_FilterCriteria.getValue().getId();

		CompanyClassificationDTO[] clasificationId = this.cbx_Classifications != null ? this.cbx_Classifications.getSelectedItems().toArray(new CompanyClassificationDTO[this.cbx_Classifications.getSelectedItems().size()])
				: new CompanyClassificationDTO[0];

		String value = this.txt_Code != null ? this.txt_Code.getValue() : this.txt_SocialReason != null ? this.txt_SocialReason.getValue() : "";

		SearchCriterion selectedCriteria = this.selectedCriteria;

		CompanyClassificationFilterSectionInfo result = new CompanyClassificationFilterSectionInfo(filtertype, clasificationId, value, selectedCriteria);

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		if (this.enableFirstSubsection)
		{
			Integer filterType = this.cbx_FilterCriteria.getValue().getId();

			if (filterType == null || !this.cbx_FilterCriteria.isEnabled())
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "product_requires");
			}
		}

		// Se valida, pues si result != null se debe evitar sobreescribir
		// el error registrado

		if (this.enableSecondSubsection)
		{
			if ((result == null) && this.cbx_Classifications != null)
			{
				Long[] selectedClassifications = this.cbx_Classifications.getSelectedItems().stream().map(p -> p.getId()).toArray(Long[]::new);

				if (selectedClassifications == null || !this.cbx_Classifications.isEnabled() || (this.pnlSecondSubsection.getComponentIndex(this.cbx_Classifications) != -1) && selectedClassifications.length < 1)
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_criteria");
				}
			}

			if ((result == null) && this.txt_Code != null)
			{
				String code = this.txt_Code.getValue();

				if (code == null || !this.txt_Code.isEnabled() || (this.pnlSecondSubsection.getComponentIndex(this.txt_Code) != -1) && code.isEmpty())
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_criteria");
				}
			}

			if ((result == null) && this.txt_SocialReason != null)
			{
				String code = this.txt_SocialReason.getValue();

				if (code == null || !this.txt_SocialReason.isEnabled() || (this.pnlSecondSubsection.getComponentIndex(this.txt_SocialReason) != -1) && code.isEmpty())
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_criteria");
				}
			}
		}
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private HorizontalLayout getFirstSubsectionPanel()
	{
		HorizontalLayout pnlCategorySubsection = null;

		if (this.enableFirstSubsection)
		{
			Label lbl_FirstSubsection = this.getSubsectionPanelLabel(!StringUtils.isEmpty(this.firstSubsectionTitle) ? this.firstSubsectionTitle
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "criteria"));

			this.initializeFirstSubsectionComponent();
			pnlCategorySubsection = new HorizontalLayout();
			pnlCategorySubsection.setWidth("100%");
			pnlCategorySubsection.addComponents(lbl_FirstSubsection, this.cbx_FilterCriteria);
			pnlCategorySubsection.setExpandRatio(this.cbx_FilterCriteria, 1F);
			pnlCategorySubsection.addStyleName("bbr-panel-space");
		}
		else
		{
			pnlCategorySubsection = new HorizontalLayout();
			pnlCategorySubsection.setVisible(false);
		}

		return pnlCategorySubsection;
	}

	private void initializeFirstSubsectionComponent()
	{
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getClassificationManagementFilterCriteria();
		this.cbx_FilterCriteria = new BbrComboBox<SearchCriterion>(searcCriterions);
		this.cbx_FilterCriteria.setItemCaptionGenerator(SearchCriterion::getDescription);
		this.cbx_FilterCriteria.selectFirstItem();
		this.cbx_FilterCriteria.setTextInputAllowed(false);
		this.cbx_FilterCriteria.setEmptySelectionAllowed(false);
		this.cbx_FilterCriteria.addStyleName("bbr-filter-fields");
		this.cbx_FilterCriteria.setWidth("255px");
		this.cbx_FilterCriteria.setHeight("30px");
		this.cbx_FilterCriteria.addValueChangeListener((ValueChangeListener<SearchCriterion> & Serializable) e -> this.getSecondSubsectionPanel(e));
	}

	private HorizontalLayout getSecondSubsectionPanel(ValueChangeEvent<SearchCriterion> searchCriteria)
	{
		this.selectedCriteria = this.cbx_FilterCriteria.getSelectedValue();
		SearchCriterion criterion = this.selectedCriteria;
		if (this.enableSecondSubsection)
		{
			Component selectedComponent = null;
			String inputWidth = "255px";
			String inputHeight = "30px";
			Label lbl_SecondSubsection = new Label();
			switch (criterion.getId())
			{
				case -1: // all
					lbl_SecondSubsection = this.getSubsectionPanelLabel("");
					selectedComponent = new BbrHSeparator("100%");
					selectedComponent.addStyleName("bbr-filter-fields");
					selectedComponent.setVisible(true);
					break;
				case 0: // classification
					lbl_SecondSubsection = this.getSubsectionPanelLabel(criterion.getDescription());
					this.cbx_Classifications = new ComboBoxMultiselect<CompanyClassificationDTO>();
					this.cbx_Classifications.setItemCaptionGenerator(CompanyClassificationDTO::getName);
					this.cbx_Classifications.setSelectAllButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_all"));
					this.cbx_Classifications.setClearButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "deselect_all"));
					this.cbx_Classifications.setTextInputAllowed(true);
					this.cbx_Classifications.showSelectAllButton(true);
					this.cbx_Classifications.showClearButton(true);
					this.cbx_Classifications.addStyleName("bbr-filter-fields");
					this.cbx_Classifications.setWidth(inputWidth);
					this.cbx_Classifications.setHeight(inputHeight);
					this.setClassificationsCombBoxItems();
					selectedComponent = this.cbx_Classifications;
					break;
				case 1: // code
					lbl_SecondSubsection = this.getSubsectionPanelLabel(criterion.getDescription());
					this.txt_Code = new BbrTextField();
					this.txt_Code.addStyleName("bbr-filter-fields");
					this.txt_Code.setWidth(inputWidth);
					this.txt_Code.setHeight(inputHeight);
					selectedComponent = this.txt_Code;
					break;
				case 2: // social_reason
					lbl_SecondSubsection = this.getSubsectionPanelLabel(criterion.getDescription());
					this.txt_SocialReason = new BbrTextField();
					this.txt_SocialReason.addStyleName("bbr-filter-fields");
					this.txt_SocialReason.setWidth(inputWidth);
					this.txt_SocialReason.setHeight(inputHeight);
					selectedComponent = this.txt_SocialReason;
					break;
			}

			if (this.pnlSecondSubsection != null)
			{
				this.pnlSecondSubsection.removeAllComponents();
			}
			else
			{
				this.pnlSecondSubsection = new HorizontalLayout();
			}

			this.pnlSecondSubsection.addComponents(lbl_SecondSubsection, selectedComponent);
			this.pnlSecondSubsection.setExpandRatio(selectedComponent, 1F);
			this.pnlSecondSubsection.addStyleName("bbr-panel-space");
			this.updateComponentsRemoved();
		}
		else
		{
			this.pnlSecondSubsection = new HorizontalLayout();
			this.pnlSecondSubsection.setVisible(false);
		}
		return this.pnlSecondSubsection;
	}

	private void updateComponentsRemoved()
	{
		if (this.pnlSecondSubsection.getComponentIndex(this.cbx_Classifications) == -1)
		{
			this.cbx_Classifications = null;
		}
		if (this.pnlSecondSubsection.getComponentIndex(this.txt_SocialReason) == -1)
		{
			this.txt_SocialReason = null;
		}
		if (this.pnlSecondSubsection.getComponentIndex(this.txt_Code) == -1)
		{
			this.txt_Code = null;
		}
	}

	private void setClassificationsCombBoxItems()
	{
		try
		{
			CompanyClassificationArrayResultDTO classificactionResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).getCompanyClassifications();
			classificactionResult.getCompanyClassificationDTOs();
			BbrError error = ErrorsMngr.getInstance().getError(classificactionResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				List<CompanyClassificationDTO> items = new ArrayList<CompanyClassificationDTO>();

				CompanyClassificationDTO[] results = classificactionResult.getCompanyClassificationDTOs();
				items.addAll(Arrays.asList(results));

				this.cbx_Classifications.setItems(items);
				this.cbx_Classifications.setEnabled(true);

			}
			else
			{
				CompanyClassificationDTO emptyResult = this.getEmptyItem();
				this.cbx_Classifications.setItems(emptyResult);
				this.cbx_Classifications.select(emptyResult);
				this.cbx_Classifications.setEnabled(false);
			}

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

	private CompanyClassificationDTO getEmptyItem()
	{
		CompanyClassificationDTO emptyResult = new CompanyClassificationDTO();
		emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_classification_assigned"));
		return emptyResult;
	}

	private Label getSubsectionPanelLabel(String subsectionTitle)
	{
		Label lbl_FirstSubsection = new Label(!StringUtils.isBlank(subsectionTitle) ? subsectionTitle
				: "");
		lbl_FirstSubsection.setWidth("120px");
		return lbl_FirstSubsection;

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
