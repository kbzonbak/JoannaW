package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class SaleStoreArrayResultDTO extends BaseResultDTO{

	private SaleStoreDTO[] salestores;

	public SaleStoreDTO[] getSalestores() {
		return salestores;
	}

	public void setSalestores(SaleStoreDTO[] salestores) {
		this.salestores = salestores;
	}

}
