package bbr.b2b.portal.components.filter_section.generic;

import java.time.LocalDateTime;

import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.generic.PeriodFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.PeriodData;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.interfaces.PeriodInitializable;

public class PeriodFilterSection extends BbrVerticalSection<PeriodFilterSectionInfo> 
{
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	private static final long serialVersionUID = -6008434512185945060L;

	public PeriodFilterSection(BbrUI parent) 
	{
		super(parent);
	}

// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			PROPERTIES
// =====================================================================================================================================

	private BbrDateTimeField datFld_SinceDate 		= null;
	private BbrDateTimeField datFld_UntilDate 		= null;
	
	private PeriodData periodData = null ;
	
	public PeriodData getPeriodData() 
	{
		return periodData;
	}


	public void setPeriodData(PeriodInitializable periodData) 
	{
		this.periodData = periodData.initializePeriodData();
		
		this.setComponentDateRange();
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
		// Header
		
		Label lbl_Period = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "period"));
		lbl_Period.addStyleName("bbr-panel-space");
		lbl_Period.setWidth("220px");
		
		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_Period);
		pnlHeader.setWidth("100%");

		// Selecci�n Per�odos 
		
		HorizontalLayout pnlPeriodsSubsection = this.getPeriodsSelectionPanel();
		
		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlPeriodsSubsection);
	}

	
	@Override
	public PeriodFilterSectionInfo getSectionResult() 
	{
		PeriodFilterSectionInfo result = new PeriodFilterSectionInfo(this.datFld_SinceDate.getValue(), this.datFld_UntilDate.getValue());
		
		return result;
	}

	
	@Override
	public String validateData() 
	{
		String result = null;

		LocalDateTime selectedStartDate = this.datFld_SinceDate.getValue();
		LocalDateTime selectedEndDate 	= this.datFld_UntilDate.getValue();

		if(selectedStartDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "valid_since_date");
		}
		
		else if(selectedEndDate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "valid_until_date");
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
	
	private HorizontalLayout getPeriodsSelectionPanel()
	{
		this.updatePeriodsSection();

		
		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();
		
		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));
		
		
		pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince,datFld_SinceDate);
		pnlSinceSubsection.setSpacing(true);
		
		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();
		
		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));
		
		pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, datFld_UntilDate);
		pnlUntilSubsection.setSpacing(true);
		
		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("400px");
		result.addComponents(pnlSinceSubsection,new BbrHSeparator("10px") ,pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.addStyleName("bbr-panel-space");
		
		return result;
	}
	
	
	private void updatePeriodsSection()
	{
		if(this.datFld_SinceDate == null)
		{
			this.datFld_SinceDate = new BbrDateTimeField();
			this.datFld_SinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
			this.datFld_SinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_SinceDate.setDateFormat("dd-MM-yyyy");
			this.datFld_SinceDate.setResolution(DateTimeResolution.DAY);
			this.datFld_SinceDate.setWidth("125px");
			this.datFld_SinceDate.setHeight("35px");
			this.datFld_SinceDate.setTextFieldEnabled(false);
		}
		
		if(this.datFld_UntilDate == null)
		{
			this.datFld_UntilDate = new BbrDateTimeField();
			this.datFld_UntilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
			this.datFld_UntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_UntilDate.setDateFormat("dd-MM-yyyy");
			this.datFld_UntilDate.setResolution(DateTimeResolution.DAY);
			this.datFld_UntilDate.setWidth("125px");
			this.datFld_UntilDate.setHeight("35px");
			this.datFld_UntilDate.setTextFieldEnabled(false);
		}
	}
	
	

	private void setComponentDateRange()
	{
		if(this.periodData != null)
		{
			this.datFld_SinceDate.setValue(this.periodData.getSinceDateValue());
			this.datFld_SinceDate.setRangeStart(this.periodData.getSinceDateStart());
			this.datFld_SinceDate.setRangeEnd(this.periodData.getSinceDateEnd());
			
			this.datFld_UntilDate.setValue(this.periodData.getUntilDateValue());
			this.datFld_UntilDate.setRangeStart(this.periodData.getUntilDateStart());
			this.datFld_UntilDate.setRangeEnd(this.periodData.getUntilDateEnd());
		}
		else
		{
			this.datFld_SinceDate.setEnabled(false);
			this.datFld_UntilDate.setEnabled(false);
		}
	}
// =====================================================================================================================================
// ENDING SECTION 		---->			PRIVATE METHODS
// =====================================================================================================================================


}
