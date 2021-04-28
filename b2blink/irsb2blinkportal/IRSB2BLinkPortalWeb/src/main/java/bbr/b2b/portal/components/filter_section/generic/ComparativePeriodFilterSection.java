package bbr.b2b.portal.components.filter_section.generic;

import java.time.LocalDateTime;

import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.generic.ComparativePeriodFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.PeriodData;
import cl.bbr.core.classes.container.BbrHorizontalSection;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.interfaces.PeriodInitializable;

public class ComparativePeriodFilterSection extends BbrHorizontalSection<ComparativePeriodFilterSectionInfo> 
{
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7638202865769093035L;
	public ComparativePeriodFilterSection(BbrUI parent) 
	{
		super(parent);
	}

// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			PROPERTIES
// =====================================================================================================================================

	private BbrDateTimeField datFld_BaseSinceDate 		= null;
	private BbrDateTimeField datFld_BaseUntilDate 		= null;

	private BbrDateTimeField datFld_ToCompareSinceDate 		= null;
	private BbrDateTimeField datFld_ToCompareUntilDate 		= null;
	
	private PeriodData periodBaseData = null ;
	public PeriodData getPeriodBaseData() 
	{
		return periodBaseData;
	}
	public void setPeriodBaseData(PeriodInitializable periodBaseData) 
	{
		this.periodBaseData = periodBaseData.initializePeriodData();
		
		this.setComponentBaseDateRange();
	}

	private PeriodData periodToCompareData = null ;
	public PeriodData getPeriodToCompareData() 
	{
		return periodToCompareData;
	}
	public void setPeriodToCompareData(PeriodInitializable periodToCompareData) 
	{
		this.periodToCompareData = periodToCompareData.initializePeriodData();
		
		this.setComponentToCompareDateRange();
	}
// =====================================================================================================================================
// ENDING SECTION 		---->			PROPERTIES
// =====================================================================================================================================
	

// =====================================================================================================================================
// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
// =====================================================================================================================================

	@Override
	public void initializeView() 
	{
		// Selecci�n Base
		Label lbl_BasePeriod = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "base_period"));
		lbl_BasePeriod.addStyleName("bbr-panel-space");
		lbl_BasePeriod.addStyleName("center-text");
		lbl_BasePeriod.setWidth("100%");
		
		HorizontalLayout pnlBaseHeader = new HorizontalLayout();
		pnlBaseHeader.addStyleName("bbr-filter-label-header");
		pnlBaseHeader.addComponents(lbl_BasePeriod);
		pnlBaseHeader.setWidth("185px");
		
		VerticalLayout pnlPeriodsBaseSubsection = this.getPeriodsBaseSelectionPanel();
		
		VerticalLayout pnlBaseSection = new VerticalLayout(pnlBaseHeader, pnlPeriodsBaseSubsection);
		pnlBaseSection.setWidth("190px");
		pnlBaseSection.setMargin(false);
		// Selecci�n A Comparar
		Label lbl_ToComparePeriod = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "to_compare_period"));
		lbl_ToComparePeriod.addStyleName("bbr-panel-space");
		lbl_ToComparePeriod.addStyleName("center-text");
		lbl_ToComparePeriod.setWidth("100%");
		
		HorizontalLayout pnlToCompareHeader = new HorizontalLayout();
		pnlToCompareHeader.addStyleName("bbr-filter-label-header");
		pnlToCompareHeader.addComponents(lbl_ToComparePeriod);
		pnlToCompareHeader.setWidth("185px");
		
		VerticalLayout pnlPeriodsToCompareSubsection = this.getPeriodsToCompareSelectionPanel();
		
		VerticalLayout pnlToCompareSection = new VerticalLayout(pnlToCompareHeader, pnlPeriodsToCompareSubsection);
		pnlToCompareSection.setWidth("190px");
		pnlToCompareSection.setMargin(false);
		
		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(true);
		this.addComponents(pnlBaseSection,pnlToCompareSection);
		this.setExpandRatio(pnlToCompareSection, 1F);
	}

	
	@Override
	public ComparativePeriodFilterSectionInfo getSectionResult() 
	{
		ComparativePeriodFilterSectionInfo result = new ComparativePeriodFilterSectionInfo(this.datFld_BaseSinceDate.getValue(), this.datFld_BaseUntilDate.getValue(), this.datFld_ToCompareSinceDate.getValue(), this.datFld_ToCompareUntilDate.getValue());
		
		return result;
	}

	
	@Override
	public String validateData() 
	{
		String result = null;

		LocalDateTime selectedBaseStartDate 		= this.datFld_BaseSinceDate.getValue();
		LocalDateTime selectedBaseEndDate 			= this.datFld_BaseUntilDate.getValue();
		LocalDateTime selectedToCompareStartDate 	= this.datFld_ToCompareSinceDate.getValue();
		LocalDateTime selectedToCompareEndDate 		= this.datFld_ToCompareUntilDate.getValue();

		if(selectedBaseStartDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_base_since_date");
		}
		else if(selectedBaseEndDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_base_until_date");
		}
		else if(selectedToCompareStartDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_to_compare_since_date");
		}
		else if(selectedToCompareEndDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_to_compare_until_date");
		}
		else if(selectedBaseStartDate.isAfter(selectedBaseEndDate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_base_range_date");
		}
		else if(selectedToCompareStartDate.isAfter(selectedToCompareEndDate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "valid_to_compare_range_date");
		}

		return result;
	}
	
	
	@Override
	public void setSectionData(Object data) 
	{
		super.setSectionData(data);
	}
	
// =====================================================================================================================================
// ENDING SECTION 		---->			OVERRIDDEN METHODS
// =====================================================================================================================================



// =====================================================================================================================================
// BEGINNING SECTION 	---->			EVENT HANDLERS
// =====================================================================================================================================

// =====================================================================================================================================
// ENDING SECTION 		---->			EVENT HANDLERS
// =====================================================================================================================================

	
	
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			PRIVATE METHODS
// =====================================================================================================================================
	
	private VerticalLayout getPeriodsBaseSelectionPanel()
	{
		this.updatePeriodsSection();

		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();
		
		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));
		lblSince.setWidth("38px");
		
		pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince,datFld_BaseSinceDate);
		pnlSinceSubsection.setSpacing(true);
		pnlSinceSubsection.setMargin(false);
		
		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();
		
		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));
		lblUntil.setWidth("38px");
		
		pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, datFld_BaseUntilDate);
		pnlUntilSubsection.setSpacing(true);
		pnlUntilSubsection.setMargin(false);
	
		VerticalLayout result = new VerticalLayout();
		result.setWidth("180px");
		result.addComponents(pnlSinceSubsection,pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.setMargin(false);
		result.addStyleName("bbr-panel-space");
		
		return result;
	}

	private VerticalLayout getPeriodsToCompareSelectionPanel()
	{
		this.updatePeriodsSection();
		
		
		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();
		
		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));
		lblSince.setWidth("38px");
		
		pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince,datFld_ToCompareSinceDate);
		pnlSinceSubsection.setSpacing(true);
		pnlSinceSubsection.setMargin(false);
		
		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();
		
		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));
		lblUntil.setWidth("38px");
		pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, datFld_ToCompareUntilDate);
		pnlUntilSubsection.setSpacing(true);
		pnlUntilSubsection.setMargin(false);
		
		VerticalLayout result = new VerticalLayout();
		result.setWidth("180px");
		result.addComponents(pnlSinceSubsection,pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.setMargin(false);
		result.addStyleName("bbr-panel-space");
		
		return result;
	}
	
	
	private void updatePeriodsSection()
	{
		if(this.datFld_BaseSinceDate == null)
		{
			this.datFld_BaseSinceDate = new BbrDateTimeField();
			this.datFld_BaseSinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
			this.datFld_BaseSinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_BaseSinceDate.setDateFormat("dd-MM-yyyy");
			this.datFld_BaseSinceDate.setResolution(DateTimeResolution.DAY);
			this.datFld_BaseSinceDate.setWidth("125px");
			this.datFld_BaseSinceDate.setHeight("35px");
			this.datFld_BaseSinceDate.setTextFieldEnabled(false);
		}
		
		if(this.datFld_BaseUntilDate == null)
		{
			this.datFld_BaseUntilDate = new BbrDateTimeField();
			this.datFld_BaseUntilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
			this.datFld_BaseUntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_BaseUntilDate.setDateFormat("dd-MM-yyyy");
			this.datFld_BaseUntilDate.setResolution(DateTimeResolution.DAY);
			this.datFld_BaseUntilDate.setWidth("125px");
			this.datFld_BaseUntilDate.setHeight("35px");
			this.datFld_BaseUntilDate.setTextFieldEnabled(false);
		}

		if(this.datFld_ToCompareSinceDate == null)
		{
			this.datFld_ToCompareSinceDate = new BbrDateTimeField();
			this.datFld_ToCompareSinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
			this.datFld_ToCompareSinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_ToCompareSinceDate.setDateFormat("dd-MM-yyyy");
			this.datFld_ToCompareSinceDate.setResolution(DateTimeResolution.DAY);
			this.datFld_ToCompareSinceDate.setWidth("125px");
			this.datFld_ToCompareSinceDate.setHeight("35px");
			this.datFld_ToCompareSinceDate.setTextFieldEnabled(false);
		}
		
		if(this.datFld_ToCompareUntilDate == null)
		{
			this.datFld_ToCompareUntilDate = new BbrDateTimeField();
			this.datFld_ToCompareUntilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
			this.datFld_ToCompareUntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_ToCompareUntilDate.setDateFormat("dd-MM-yyyy");
			this.datFld_ToCompareUntilDate.setResolution(DateTimeResolution.DAY);
			this.datFld_ToCompareUntilDate.setWidth("125px");
			this.datFld_ToCompareUntilDate.setHeight("35px");
			this.datFld_ToCompareUntilDate.setTextFieldEnabled(false);
		}
	}

	private void setComponentBaseDateRange()
	{
		if(this.periodBaseData != null)
		{
			this.datFld_BaseSinceDate.setValue(this.periodBaseData.getSinceDateValue());
			this.datFld_BaseSinceDate.setRangeStart(this.periodBaseData.getSinceDateStart());
			this.datFld_BaseSinceDate.setRangeEnd(this.periodBaseData.getSinceDateEnd());
			
			this.datFld_BaseUntilDate.setValue(this.periodBaseData.getUntilDateValue());
			this.datFld_BaseUntilDate.setRangeStart(this.periodBaseData.getUntilDateStart());
			this.datFld_BaseUntilDate.setRangeEnd(this.periodBaseData.getUntilDateEnd());
		}
		else
		{
			this.datFld_BaseSinceDate.setEnabled(false);
			this.datFld_BaseUntilDate.setEnabled(false);
		}
	}

	private void setComponentToCompareDateRange()
	{
		if(this.periodToCompareData != null)
		{
			this.datFld_ToCompareSinceDate.setValue(this.periodToCompareData.getSinceDateValue());
			this.datFld_ToCompareSinceDate.setRangeStart(this.periodToCompareData.getSinceDateStart());
			this.datFld_ToCompareSinceDate.setRangeEnd(this.periodToCompareData.getSinceDateEnd());
			
			this.datFld_ToCompareUntilDate.setValue(this.periodToCompareData.getUntilDateValue());
			this.datFld_ToCompareUntilDate.setRangeStart(this.periodToCompareData.getUntilDateStart());
			this.datFld_ToCompareUntilDate.setRangeEnd(this.periodToCompareData.getUntilDateEnd());
		}
		else
		{
			this.datFld_ToCompareSinceDate.setEnabled(false);
			this.datFld_ToCompareUntilDate.setEnabled(false);
		}
	}
// =====================================================================================================================================
// ENDING SECTION 		---->			PRIVATE METHODS
// =====================================================================================================================================
	

	
}
