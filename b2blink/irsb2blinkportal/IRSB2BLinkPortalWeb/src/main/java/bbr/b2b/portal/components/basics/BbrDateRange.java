package bbr.b2b.portal.components.basics;

import java.time.LocalDateTime;

import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;

public class BbrDateRange extends HorizontalLayout
{
	private static final long	serialVersionUID	= 9201608908733574432L;
	private DateTimeField		dtf_SinceDate		= null;
	private DateTimeField		dtf_UntilDate		= null;

	public BbrDateRange(BbrDateRangeBuilder bbrDateRangeBuilder)
	{
		this.initializeBbrDateRange(bbrDateRangeBuilder);
	}

	public void initializeBbrDateRange(BbrDateRangeBuilder bbrDateRangeBuilder)
	{
		Label lbl_Start = new Label(I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "since_date"));
		lbl_Start.setWidth(bbrDateRangeBuilder.labelWidth);
		Label lbl_End = new Label(I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "until_date"));
		lbl_End.setWidth(bbrDateRangeBuilder.labelWidth);

		dtf_SinceDate = new DateTimeField();
		dtf_SinceDate.setParseErrorMessage(I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "valid_base_since_date"));
		dtf_SinceDate.setValue(bbrDateRangeBuilder.startDate);
		dtf_SinceDate.setLocale(bbrDateRangeBuilder.bbrUI.getUser().getLocale());
		dtf_SinceDate.setDateFormat(bbrDateRangeBuilder.dateFormat);
		dtf_SinceDate.setResolution(bbrDateRangeBuilder.dateTimeResolution);
		dtf_SinceDate.setWidth(bbrDateRangeBuilder.dtf_Width);
		dtf_SinceDate.setDateOutOfRangeMessage(bbrDateRangeBuilder.dateOutOfRangeMessage != null
				? bbrDateRangeBuilder.dateOutOfRangeMessage
				: I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "date_out_of_range"));
		dtf_SinceDate.setTextFieldEnabled(bbrDateRangeBuilder.isTextFieldEnabled);

		dtf_UntilDate = new DateTimeField();
		dtf_UntilDate.setParseErrorMessage(I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "valid_base_until_date"));
		dtf_UntilDate.setValue(bbrDateRangeBuilder.endDate);
		dtf_UntilDate.setLocale(bbrDateRangeBuilder.bbrUI.getUser().getLocale());
		dtf_UntilDate.setDateFormat(bbrDateRangeBuilder.dateFormat);
		dtf_UntilDate.setResolution(bbrDateRangeBuilder.dateTimeResolution);
		dtf_UntilDate.setWidth(bbrDateRangeBuilder.dtf_Width);
		dtf_UntilDate.setDateOutOfRangeMessage(bbrDateRangeBuilder.dateOutOfRangeMessage != null
				? bbrDateRangeBuilder.dateOutOfRangeMessage
				: I18NManager.getI18NString(bbrDateRangeBuilder.bbrUI, BbrUtilsResources.RES_MODULES_GENERIC, "date_out_of_range"));
		dtf_UntilDate.setTextFieldEnabled(bbrDateRangeBuilder.isTextFieldEnabled);

		this.addComponents(lbl_Start, dtf_SinceDate, new BbrHSeparator(bbrDateRangeBuilder.separationBetween), lbl_End, dtf_UntilDate);
		this.setExpandRatio(dtf_SinceDate, 1F);
		this.setExpandRatio(dtf_UntilDate, 1F);
		this.setComponentAlignment(lbl_Start, Alignment.MIDDLE_LEFT);
		this.setComponentAlignment(lbl_End, Alignment.MIDDLE_LEFT);
		this.setMargin(true);
	}

	public void updateStartDate(LocalDateTime startDate)
	{
		dtf_SinceDate.setValue(startDate);
	}

	public void updateEndDate(LocalDateTime endDate)
	{
		dtf_UntilDate.setValue(endDate);
	}

	public void updateRangeStartEndDate(LocalDateTime startDate, LocalDateTime endDate)
	{
		if (startDate != null)
		{
			dtf_SinceDate.setRangeStart(startDate.withHour(0));
			dtf_UntilDate.setRangeStart(startDate.withHour(0));
		}
		if (endDate != null)
		{
			dtf_UntilDate.setRangeEnd(endDate.withHour(23));
			dtf_SinceDate.setRangeEnd(endDate.withHour(23));
		}
	}

	public LocalDateTime getSinceDate()
	{
		return dtf_SinceDate.getValue();
	}

	public LocalDateTime getUntilDate()
	{
		return dtf_UntilDate.getValue();
	}

	public static class BbrDateRangeBuilder
	{
		private String				separationBetween		= "20px";
		private DateTimeResolution	dateTimeResolution		= DateTimeResolution.DAY;
		private boolean				isTextFieldEnabled		= false;
		private String				dateOutOfRangeMessage	= null;
		private String				dateFormat				= "dd-MM-yyyy";
		private String				labelWidth				= "100px";
		private String				dtf_Width				= "170px";
		private BbrUI				bbrUI					= null;
		private LocalDateTime		startDate				= null;
		private LocalDateTime		endDate					= null;

		public BbrDateRangeBuilder(BbrUI bbrUI)
		{
			this.bbrUI = bbrUI;
		}

		public BbrDateRangeBuilder withSeparationBetween(String separationBetween)
		{
			this.separationBetween = separationBetween;
			return this;
		}

		public BbrDateRangeBuilder withDateTimeResolution(DateTimeResolution dateTimeResolution)
		{
			this.dateTimeResolution = dateTimeResolution;
			return this;
		}

		public BbrDateRangeBuilder withTextFieldEnabled(boolean isTextFieldEnabled)
		{
			this.isTextFieldEnabled = isTextFieldEnabled;
			return this;
		}

		public BbrDateRangeBuilder withDateOutOfRangeMessage(String dateOutOfRangeMessage)
		{
			this.dateOutOfRangeMessage = dateOutOfRangeMessage;
			return this;
		}

		public BbrDateRangeBuilder withEndDate(LocalDateTime endDate)
		{
			this.endDate = endDate;
			return this;
		}

		public BbrDateRangeBuilder withStartDate(LocalDateTime startDate)
		{
			this.startDate = startDate;
			return this;
		}

		public BbrDateRangeBuilder withDtfWidth(String dtf_Width)
		{
			this.dtf_Width = dtf_Width;
			return this;
		}

		public BbrDateRangeBuilder withLabelWidth(String labelWidth)
		{
			this.labelWidth = labelWidth;
			return this;
		}

		public BbrDateRangeBuilder withDateFormat(String dateFormat)
		{
			this.dateFormat = dateFormat;
			return this;
		}

		public BbrDateRange build()
		{
			return new BbrDateRange(this);
		}
	}
}
