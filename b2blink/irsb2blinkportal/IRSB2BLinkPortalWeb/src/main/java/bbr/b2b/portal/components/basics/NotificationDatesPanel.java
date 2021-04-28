package bbr.b2b.portal.components.basics;

import java.time.LocalDateTime;

import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.management.NotificationDatesPanelInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

public class NotificationDatesPanel
{
	private NotificationDatesPanelInfo	notificationDatesPanelInfo	= null;
	private String						dateFormat					= "dd-MM-yyyy HH:mm";
	private String						dfDateWidth					= "170px";
	private String						labelWidth					= "100px";
	private String						panelStyle					= "bbr-panel-space";
	private String						panelWidth					= "100%";
	private String						panelHeight					= "100px";

	public void setDateFormat(String dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	public void setDfDateWidth(String dfDateWidth)
	{
		this.dfDateWidth = dfDateWidth;
	}

	public void setLabelWidth(String labelWidth)
	{
		this.labelWidth = labelWidth;
	}

	public void setPanelStyle(String panelStyle)
	{
		this.panelStyle = panelStyle;
	}

	public void setPanelWidth(String panelWidth)
	{
		this.panelWidth = panelWidth;
	}

	public void setPanelHeight(String panelHeight)
	{
		this.panelHeight = panelHeight;
	}

	public NotificationDatesPanel(BbrUI bbrUI, LocalDateTime startDate, LocalDateTime EndDate)
	{
		this.notificationDatesPanelInfo = this.initializeDatesPanel(bbrUI, startDate, EndDate);
	}

	public NotificationDatesPanelInfo initializeDatesPanel(BbrUI bbrUI, LocalDateTime startDate, LocalDateTime EndDate)
	{
		Label lblStart = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "init_date"));
		lblStart.setWidth(this.labelWidth);
		Label lblEnd = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "end_date"));
		lblEnd.setWidth(this.labelWidth);

		Panel pnlDates = new Panel(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "validity_period"));
		pnlDates.setWidth(this.panelWidth);
		pnlDates.setHeight(this.panelHeight);
		pnlDates.addStyleName(this.panelStyle);

		DateTimeField dfSinceDate = new DateTimeField();
		dfSinceDate.setParseErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
		dfSinceDate.setValue(startDate);

		DateTimeField dfUntilDate = new DateTimeField();
		dfUntilDate.setParseErrorMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
		dfUntilDate.setValue(EndDate);

		dfSinceDate.setLocale(bbrUI.getUser().getLocale());
		dfSinceDate.setDateFormat(this.dateFormat);
		dfSinceDate.setResolution(DateTimeResolution.MINUTE);
		dfSinceDate.setWidth(this.dfDateWidth);

		dfUntilDate.setLocale(bbrUI.getUser().getLocale());
		dfUntilDate.setDateFormat(this.dateFormat);
		dfUntilDate.setResolution(DateTimeResolution.MINUTE);
		dfUntilDate.setWidth(this.dfDateWidth);

		HorizontalLayout datesLayout = new HorizontalLayout();
		datesLayout.setMargin(true);
		datesLayout.setWidth(this.panelWidth);
		datesLayout.addComponents(lblStart, dfSinceDate, lblEnd, dfUntilDate);
		datesLayout.setExpandRatio(dfSinceDate, 1F);
		datesLayout.setExpandRatio(dfUntilDate, 1F);
		datesLayout.setComponentAlignment(lblStart, Alignment.MIDDLE_LEFT);
		datesLayout.setComponentAlignment(lblEnd, Alignment.MIDDLE_LEFT);

		pnlDates.setContent(datesLayout);
		pnlDates.setHeight(this.panelHeight);

		NotificationDatesPanelInfo notificationDatesPanelInfo = new NotificationDatesPanelInfo();
		notificationDatesPanelInfo.setDfSinceDate(dfSinceDate);
		notificationDatesPanelInfo.setDfUntilDate(dfUntilDate);
		notificationDatesPanelInfo.setPnlDates(pnlDates);

		return notificationDatesPanelInfo;
	}

	public NotificationDatesPanelInfo getNotificationDatesPanelInfo()
	{
		return notificationDatesPanelInfo;
	}
}
