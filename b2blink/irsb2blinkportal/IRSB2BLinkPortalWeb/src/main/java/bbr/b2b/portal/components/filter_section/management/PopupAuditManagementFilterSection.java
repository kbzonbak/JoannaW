package bbr.b2b.portal.components.filter_section.management;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.management.PopupAuditFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AuditPopUpTitleDTO;
import bbr.b2b.users.report.classes.AuditPopUpTitleResultDTO;
import bbr.b2b.users.report.classes.AuditPopUpYearResultDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

/**
 * Representa la sección de un filtro relacionada con los datos de los años de
 * titulos y acciones.
 */
public class PopupAuditManagementFilterSection extends BbrVerticalSection<PopupAuditFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	/**
	 * Crea una nueva instancia de la sección.
	 *
	 * 
	 * @param enableFirstSelection
	 *            Define si se mostrar la primera subsección
	 * @param enableSecondSelection
	 *            Define si se mostrar la segunda subsección
	 * @param enableThirdSelection
	 *            Define si se mostrar la tercera subsección
	 * 
	 */
	public PopupAuditManagementFilterSection(BbrUI parent, Boolean enableFirstSelection, Boolean enableSecondSelection, Boolean enableThirdSelection)
	{
		super(parent);
		this.enableFirstSubsection = enableFirstSelection;
		this.enableSecondSubsection = enableSecondSelection;
		this.enableThirdSubsection = enableThirdSelection;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long				serialVersionUID				= -8023846130331106902L;
	private static final String				STYLE_BBR_PANEL_SPACE			= "bbr-panel-space";
	private static final String				STYLE_BBR_FILTER_LABEL_HEADER	= "bbr-filter-label-header";
	private static final String				STYLE_BBR_FILTER_SECTIONS		= "bbr-filter-sections";
	private static final String				STYLE_BBR_FILTER_FIELDS			= "bbr-filter-fields";
	// Components
	private BbrComboBox<Integer>			cbx_First						= null;
	private BbrComboBox<AuditPopUpTitleDTO>	cbx_Second						= null;
	private BbrComboBox<SearchCriterion>	cbx_Third						= null;
	// Variables
	private Boolean							enableFirstSubsection			= true;
	private Boolean							enableSecondSubsection			= true;
	private Boolean							enableThirdSubsection			= true;
	private List<Integer>					allYears						= null;
	private List<AuditPopUpTitleDTO>		allTitles						= null;
	private List<SearchCriterion>			allActions						= new ArrayList<>();
	private String							sectionCaption					= null;
	private String							firstSubsectionCaption			= null;
	private String							secondSubsectionCaption			= null;
	private String							thirdSubsectionCaption			= null;

	public void setOriginLocalTitle(String originLocalTitle)
	{
		this.firstSubsectionCaption = originLocalTitle;
	}

	public void setDestinationLocalTitle(String destinationLocalTitle)
	{
		this.secondSubsectionCaption = destinationLocalTitle;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Header y Links
		BbrHInputFieldContainer pnlLocalsHeader = new BbrHInputFieldContainerBuilder()
				.withCaption(this.sectionCaption != null ? this.sectionCaption
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_publication"))
				.withLabelWidth("220px").withLabelStyle(STYLE_BBR_PANEL_SPACE).build();
		pnlLocalsHeader.addStyleName(STYLE_BBR_FILTER_LABEL_HEADER);

		// Seccion Locales Origen
		this.initializeFirstComboBox();
		BbrHInputFieldContainer pnlOriginLocalSubsection = new BbrHInputFieldContainerBuilder().withVisibility(this.enableFirstSubsection)
				.withCaption(this.firstSubsectionCaption != null ? this.firstSubsectionCaption
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_year"))
				.withLabelWidth("120px").withComponent(this.cbx_First)
				.build();
		pnlOriginLocalSubsection.addStyleName(STYLE_BBR_PANEL_SPACE);

		// Seccion Locales Destino
		this.initializeSecondComboBox();
		BbrHInputFieldContainer pnlDestinationLocalSubsection = new BbrHInputFieldContainerBuilder().withVisibility(this.enableSecondSubsection)
				.withCaption(this.secondSubsectionCaption != null ? this.secondSubsectionCaption
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_title"))
				.withLabelWidth("120px").withComponent(this.cbx_Second)
				.build();
		pnlDestinationLocalSubsection.addStyleName(STYLE_BBR_PANEL_SPACE);

		// Seccion Locales Destino
		this.initializeThirdComboBox();
		BbrHInputFieldContainer pnlDestinationSubsection = new BbrHInputFieldContainerBuilder().withVisibility(this.enableThirdSubsection)
				.withCaption(this.thirdSubsectionCaption != null ? this.thirdSubsectionCaption
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_action"))
				.withLabelWidth("120px").withComponent(this.cbx_Third)
				.build();
		pnlDestinationSubsection.addStyleName(STYLE_BBR_PANEL_SPACE);

		this.setWidth("400px");
		this.addStyleName(STYLE_BBR_FILTER_SECTIONS);
		this.addStyleName(STYLE_BBR_PANEL_SPACE);
		this.setSpacing(false);
		this.addComponents(pnlLocalsHeader, pnlOriginLocalSubsection, pnlDestinationLocalSubsection, pnlDestinationSubsection);

	}

	@Override
	public String validateData()
	{
		String result = null;

		if (this.enableFirstSubsection)
		{
			Integer selectedYear = this.cbx_First.getSelectedValue();

			if (selectedYear == null || !this.cbx_First.isEnabled())
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "year_required");
			}
		}
		// Se valida, pues si result != null se debe evitar sobreescribir
		// el error registrado

		if ((result == null) && this.enableSecondSubsection)
		{
			AuditPopUpTitleDTO selectedTitleDTO = this.cbx_Second.getSelectedValue();

			if (selectedTitleDTO == null || !this.cbx_Second.isEnabled())
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "title_required");
			}
		}

		if ((result == null) && this.enableThirdSubsection)
		{
			SearchCriterion selectedActionDTO = this.cbx_Third.getSelectedValue();

			if (selectedActionDTO == null || !this.cbx_Third.isEnabled())
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "title_required");
			}
		}

		return result;
	}

	@Override
	public PopupAuditFilterSectionInfo getSectionResult()
	{
		Integer year = (this.enableFirstSubsection) ? this.cbx_First.getSelectedValue() : null;
		AuditPopUpTitleDTO title = (this.enableSecondSubsection) ? this.cbx_Second.getSelectedValue() : null;
		String action = (this.enableSecondSubsection) ? this.cbx_Third.getSelectedValue().getDescription() : null;

		PopupAuditFilterSectionInfo result = new PopupAuditFilterSectionInfo(year, title, action);

		return result;
	}

	@Override
	public void setSectionData(Object data)
	{
		// FIXME AQUI GENERA EL CAMBIO DE FECHA PARA SU ACTUALIZACIÓN
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void initializeFirstComboBox()
	{
		if (this.cbx_First == null)
		{
			this.cbx_First = new BbrComboBox<>();
			this.cbx_First.setTextInputAllowed(false);
			this.cbx_First.setEmptySelectionAllowed(false);
			this.cbx_First.addStyleName(STYLE_BBR_FILTER_FIELDS);
			this.cbx_First.setWidth("255px");
			this.cbx_First.setHeight("30px");
			this.cbx_First.setEnabled(false);
			this.cbx_First.addValueChangeListener((ValueChangeListener<Integer> & Serializable) e -> this.cbx_Years_ValueChangeHandler(e));
		}
		if (this.allYears != null)
		{
			this.updateOriginLocalItems(this.allYears);
		}
		else
		{
			try
			{
				AuditPopUpYearResultDTO positionsResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getAuditPopUpYears();
				BbrError error = ErrorsMngr.getInstance().getError(positionsResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
				if (!error.hasError())
				{
					this.updateOriginLocalItems(Arrays.asList(positionsResult.getAuditpopupyears()));
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
		}
	}

	private void cbx_Years_ValueChangeHandler(ValueChangeEvent<Integer> e)
	{
		this.doExecuteAuditYearsService(e.getValue());
		this.doUpdateActionCombobox();
	}

	private void initializeSecondComboBox()
	{
		if (this.cbx_Second == null)
		{
			this.cbx_Second = new BbrComboBox<>();
			this.cbx_Second.setItemCaptionGenerator(AuditPopUpTitleDTO::getTitle);
			this.cbx_Second.setTextInputAllowed(false);
			this.cbx_Second.setEmptySelectionAllowed(false);
			this.cbx_Second.addStyleName(STYLE_BBR_FILTER_FIELDS);
			this.cbx_Second.setWidth("255px");
			this.cbx_Second.setHeight("30px");
			this.cbx_Second.setEnabled(false);
		}
		if (this.allTitles != null)
		{
			this.updateDestinationLocalItems(this.allTitles);
		}
		else
		{
			this.doExecuteAuditYearsService(this.cbx_First.getSelectedValue());
		}
	}

	private void doExecuteAuditYearsService(Integer yearSelected)
	{
		try
		{
			yearSelected = (yearSelected!= null) ? yearSelected : LocalDateTime.now().getYear();
			AuditPopUpTitleResultDTO positionsResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent())
					.getTitlesOfAuditPopUpByYear(yearSelected);
			BbrError error = ErrorsMngr.getInstance().getError(positionsResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				this.updateDestinationLocalItems(Arrays.asList(positionsResult.getAuditPopUpTitleDTOs()));
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
	}

	private void initializeThirdComboBox()
	{
		if (this.cbx_Third == null)
		{
			this.cbx_Third = new BbrComboBox<>();
			this.cbx_Third.setItemCaptionGenerator(SearchCriterion::getDescription);
			this.cbx_Third.setTextInputAllowed(false);
			this.cbx_Third.setEmptySelectionAllowed(false);
			this.cbx_Third.addStyleName(STYLE_BBR_FILTER_FIELDS);
			this.cbx_Third.setWidth("255px");
			this.cbx_Third.setHeight("30px");
			this.cbx_Third.setEnabled(false);
			this.doUpdateActionCombobox();
		}
	}

	private void doUpdateActionCombobox()
	{
		if (this.allActions == null || this.allActions.size() < 1)
		{
			SearchCriterion[] actionList = SearchFilterUtils.getInstance().getActionFilterCriteria(this.getBbrUIParent());
			this.allActions = Arrays.asList(actionList);
			this.updateActionItems(this.allActions);
		}
		else
		{
			this.updateActionItems(this.allActions);
		}
	}

	private void updateOriginLocalItems(List<Integer> tempLocals)
	{
		if (this.cbx_First != null)
		{
			this.cbx_First.setItems(tempLocals);
			this.cbx_First.selectFirstItem();
			this.cbx_First.setEnabled(true);
		}

	}

	private void updateDestinationLocalItems(List<AuditPopUpTitleDTO> tempLocals)
	{
		if (this.cbx_Second != null)
		{
			this.cbx_Second.setItems(tempLocals);
			this.cbx_Second.selectFirstItem();
			this.cbx_Second.setEnabled(true);
		}
	}

	private void updateActionItems(List<SearchCriterion> tempLocals)
	{
		if (this.cbx_Third != null)
		{
			this.cbx_Third.setItems(tempLocals);
			this.cbx_Third.selectFirstItem();
			this.cbx_Third.setEnabled(true);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
