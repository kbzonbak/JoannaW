package bbr.b2b.portal.components.utils.document_viewer;

import bbr.b2b.portal.classes.constants.ModuleDocumentsTypes;
import cl.bbr.core.classes.basics.ModuleDocument;
import cl.bbr.core.classes.factory.DocumentViewerFactory;
import cl.bbr.core.components.basics.BbrUI;

public class DocumentViewer extends DocumentViewerFactory
{
	
	@Override
	public void showModuleDocument(BbrUI bbrUI, ModuleDocument document)
	{
		if(document != null)
		{
			if(document.getCode().equalsIgnoreCase(ModuleDocumentsTypes.PDF))
			{
				ViewPdfDocument winDocument = new ViewPdfDocument(bbrUI);
				winDocument.showDocument(document);
	
			}
		}
	}

}
