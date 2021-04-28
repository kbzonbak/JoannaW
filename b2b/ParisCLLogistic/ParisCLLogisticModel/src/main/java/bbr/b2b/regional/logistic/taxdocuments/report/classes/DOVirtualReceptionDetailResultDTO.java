package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.ClientDTO;

public class DOVirtualReceptionDetailResultDTO extends BaseResultDTO{

	private ClientDTO client;
	private DOVirtualReceptionDetailDataDTO[] details;
		
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
	public DOVirtualReceptionDetailDataDTO[] getDetails() {
		return details;
	}
	public void setDetails(DOVirtualReceptionDetailDataDTO[] details) {
		this.details = details;
	}
			
}
