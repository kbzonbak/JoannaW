package bbr.b2b.portal.components.basics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.portal.classes.basics.PageRangesEvent;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.utilities.I18NManager;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class SelectPageRanges extends BbrWindow
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -3571257906222533377L;
	private int							minPageValue		= 1;
	private int							maxPageValue		= 0;
	private String						caption				= "";
	private BbrUI						parentUI			= null;
	private RadioButtonGroup<String>	radio_btn_options	= null;
	private boolean						alertSelected		= true;
	private Button						btn_Download;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public SelectPageRanges(BbrUI parent, String caption)
	{
		super(parent);
		this.parentUI = parent;
		this.caption = caption;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		this.btn_Download = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept", parentUI.getLocale()));
		this.btn_Download.addClickListener(e -> this.btn_Download_clickHandler());
		this.btn_Download.addStyleName("btn-generic");
		this.btn_Download.setEnabled(false);

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel", parentUI.getLocale()));
		btn_Cancel.addClickListener(e -> this.close());
		btn_Cancel.addStyleName("btn-generic");

		// Selección individual
		radio_btn_options = new RadioButtonGroup<>();
		radio_btn_options.setItems(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "alerts_selected", parentUI.getLocale()),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "all_alerts", parentUI.getLocale()));
		radio_btn_options.addValueChangeListener((ValueChangeListener<String> & Serializable) e -> radio_btn_options_valueChangeHandler(e));
		if (!alertSelected)
		{
			radio_btn_options.setItemEnabledProvider(item -> !I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "alerts_selected", parentUI.getLocale()).equals(item));
		}else{
			radio_btn_options.setSelectedItem(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "alerts_selected",parentUI.getLocale()));
		}

		HorizontalLayout cmp_Radios_txt = new HorizontalLayout(radio_btn_options);
		//cmp_Radios_txt.setComponentAlignment(Alignment.MIDDLE_CENTER);
		cmp_Radios_txt.setSpacing(true);
		cmp_Radios_txt.setMargin(false);
		cmp_Radios_txt.setWidth("80%");

		HorizontalLayout cmp_Buttons = new HorizontalLayout(btn_Download, btn_Cancel);
		VerticalLayout mainLayout = new VerticalLayout(cmp_Radios_txt, cmp_Buttons);
		mainLayout.setComponentAlignment(cmp_Buttons, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(cmp_Radios_txt, 1F);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);
		mainLayout.addStyleName("bbr-margin-windows-zero-top");
		mainLayout.addStyleName("bbr-module-top");

		this.setWidth(370, Unit.PIXELS);
		this.setHeight(210, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(this.caption);
		this.setContent(mainLayout);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void radio_btn_options_valueChangeHandler(ValueChangeEvent<String> e)
	{
		this.btn_Download.setEnabled(true);
	}

	private void btn_Download_clickHandler()
	{
		if (this.radio_btn_options.getSelectedItem().isPresent())
		{
			if (this.radio_btn_options.getSelectedItem().get() == I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "alerts_selected", parentUI.getLocale()))
			{
				BbrEvent pageSelectedEvent = new BbrEvent(PageRangesEvent.SELECTED_ITEMS_OPTION);
				PageRangesEvent pageRangesEvent = new PageRangesEvent(PageRangesEvent.SELECTED_ITEMS_OPTION);
				pageRangesEvent.setPageRanges(null);
				pageRangesEvent.setOption(PageRangesEvent.SELECTED_ITEMS_OPTION);
				pageSelectedEvent.setResultObject(pageRangesEvent);
				this.dispatchBbrEvent(pageSelectedEvent);
			}
			else if (this.radio_btn_options.getSelectedItem().get() == I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "all_alerts", parentUI.getLocale()))
			{
				PageRangeDTO pageRange[] = new PageRangeDTO[1];
				pageRange[0] = new PageRangeDTO();
				pageRange[0].setSincePage(1);
				pageRange[0].setUntilPage(this.maxPageValue);
				BbrEvent pageSelectedEvent = new BbrEvent(PageRangesEvent.ALL_PAGES_OPTION);
				PageRangesEvent pageRangesEvent = new PageRangesEvent(PageRangesEvent.ALL_PAGES_OPTION);
				pageRangesEvent.setPageRanges(pageRange);
				pageRangesEvent.setOption(PageRangesEvent.ALL_PAGES_OPTION);
				pageSelectedEvent.setResultObject(pageRangesEvent);
				this.dispatchBbrEvent(pageSelectedEvent);
			}
		}
		else
		{
			showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "error", parentUI.getLocale()),
					I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "is_not_selected_option_to_page_download", parentUI.getLocale()));
		}
		this.close();
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	public boolean isalertSelected()
	{
		return alertSelected;
	}

	public void setalertSelected(boolean alertSelected)
	{
		this.alertSelected = alertSelected;
	}

	public int getMaxPageValue()
	{
		return maxPageValue;
	}

	public void setMaxPageValue(int maxPageValue)
	{
		this.maxPageValue = maxPageValue;
	}

	public int getMinPageValue()
	{
		return minPageValue;
	}

	public void setMinPageValue(int minPageValue)
	{
		this.minPageValue = minPageValue;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
