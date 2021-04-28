package bbr.b2b.portal.components.utils.document_viewer;

import java.io.File;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import cl.bbr.core.classes.basics.ModuleDocument;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrpdfviewer.BbrPdfViewer;

public class ViewPdfDocument extends BbrWindow implements IDocumentViewer
{
	public ViewPdfDocument(BbrUI parent)
	{
		super(parent);
	}

	private static final long	serialVersionUID	= -1571874621097085086L;
	private ModuleDocument		document			= null;

	@Override
	public void showDocument(ModuleDocument document)
	{
		this.document = document;
		initializeView();
	}

	@Override
	public void initializeView()
	{
		String filePath = PortalConstants.getInstance().getReportsDocumentsPathByType(this.document.getCode());

		if (filePath != null && filePath.length() > 0 && this.document.getPath() != null && this.document.getPath().length() > 0)
		{
			File file = new File(filePath + this.document.getPath());
			if (!file.exists())
			{
				this.close();
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "file_not_found"));
			}
			else
			{
				BbrPdfViewer pdf = new BbrPdfViewer(file);
				pdf.setSizeFull();
				pdf.setNextPageIcon(VaadinIcons.CHEVRON_CIRCLE_RIGHT);
				pdf.setPreviousPageIcon(VaadinIcons.CHEVRON_CIRCLE_LEFT);
				pdf.setAngleButtonVisible(false);

				VerticalLayout mainLayout = new VerticalLayout();
				mainLayout.addComponents(pdf);
				mainLayout.setMargin(true);
				mainLayout.setSizeFull();
				mainLayout.addStyleName("bbr-margin-windows");

				this.setContent(mainLayout);
				this.setWidth("800px");
				this.setHeight("90%");
				this.setResizable(true);
				this.setCaption(document.getName());
				this.open(false);
			}

		}

	}
}
