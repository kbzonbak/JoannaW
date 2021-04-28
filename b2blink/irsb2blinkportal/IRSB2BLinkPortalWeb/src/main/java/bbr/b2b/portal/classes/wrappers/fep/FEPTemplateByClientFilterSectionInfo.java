package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;

public class FEPTemplateByClientFilterSectionInfo
{
	private ArticleTypeDataDTO				selectedTemplate		= null;
	private ClientDTO						selectedClient          = null;


public FEPTemplateByClientFilterSectionInfo(ClientDTO selectedClient, ArticleTypeDataDTO selectedTemplate)
	{
		super();
		this.selectedClient		= selectedClient;
		this.selectedTemplate 	= selectedTemplate;
	}

	public ArticleTypeDataDTO getTemplate()
	{
		return selectedTemplate;
	}


	public void setTemplate(ArticleTypeDataDTO selectedTemplate)
	{
		this.selectedTemplate = selectedTemplate;
	}

	public ClientDTO getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}
	
}
