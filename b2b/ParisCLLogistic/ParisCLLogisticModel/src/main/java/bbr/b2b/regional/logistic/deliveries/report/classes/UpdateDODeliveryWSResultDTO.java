package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class UpdateDODeliveryWSResultDTO extends BaseResultDTO {

	public UpdateDODeliveryWSResultDTO(String codigo, String descripcion) {
		setStatuscode(codigo);
		setStatusmessage(descripcion);

	}

	public UpdateDODeliveryWSResultDTO() {

	}

}
