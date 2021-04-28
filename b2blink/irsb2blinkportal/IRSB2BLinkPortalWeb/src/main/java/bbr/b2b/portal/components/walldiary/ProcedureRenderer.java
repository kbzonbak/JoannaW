package bbr.b2b.portal.components.walldiary;

import java.time.format.DateTimeFormatter;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.users.report.classes.PublishingReportDataDTO;

public class ProcedureRenderer extends CustomComponent 
{
	private static final long serialVersionUID = -3532410796186595625L;

	public ProcedureRenderer(PublishingReportDataDTO procedure) 
	{
		this.buildLayout(procedure);
	}

	private void buildLayout(PublishingReportDataDTO procedure) 
	{
		if (procedure == null)
		{
			Label lbl_Date = new Label("11-02-2017");
			Label lbl_Title = new Label("TEST Procedimientos");

			Label lbl_Content = new Label("Esto es una prueba de lo que puede ser el contenido de un procedimiento.<br /> Puede haber texto en html.");
			lbl_Content.setContentMode(ContentMode.HTML);
			VerticalLayout mainLayout = new VerticalLayout(lbl_Date, lbl_Title, lbl_Content);

			lbl_Date.addStyleName("walldiary-procedure-date");
			lbl_Title.addStyleName("walldiary-procedure-title");
			lbl_Content.addStyleName("walldiary-procedure-content");
			mainLayout.addStyleName("walldiary-procedure");

			mainLayout.setSpacing(true);
			this.setCompositionRoot(mainLayout);
		}
		else
		{
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			Label lbl_Date = new Label(procedure.getEnddate().format(dateFormatter));
			Label lbl_Title = new Label(procedure.getTitle());

			HorizontalLayout cmp_Title = new HorizontalLayout();
			cmp_Title.setMargin(false);
			cmp_Title.setSizeFull();
			cmp_Title.addComponents(lbl_Date,lbl_Title);
			cmp_Title.setComponentAlignment(lbl_Date, Alignment.MIDDLE_LEFT);
			cmp_Title.setComponentAlignment(lbl_Title, Alignment.MIDDLE_LEFT);
			cmp_Title.setExpandRatio(lbl_Title, 1F);
			
			Label lbl_Content = new Label(procedure.getHtmlcontent());
			lbl_Content.setContentMode(ContentMode.HTML);
			
			VerticalLayout mainLayout = new VerticalLayout(cmp_Title, lbl_Content);

			lbl_Date.addStyleName("walldiary-procedure-date");
			lbl_Title.addStyleName("walldiary-procedure-title");
			lbl_Content.addStyleName("walldiary-procedure-content");
			mainLayout.addStyleName("walldiary-procedure");

			mainLayout.setSpacing(true);
			this.setCompositionRoot(mainLayout);
		}
	}
}
