package bbr.b2b.logistic.customer.data.dto;

import java.util.stream.BaseStream;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class StockDTO extends BaseResultDTO{

	private BaseResultDTO[] detalles;

	public BaseResultDTO[] getDetalles() {
		return detalles;
	}

	public void setDetalles(BaseResultDTO[] detalles) {
		this.detalles = detalles;
	}
	
	
}
