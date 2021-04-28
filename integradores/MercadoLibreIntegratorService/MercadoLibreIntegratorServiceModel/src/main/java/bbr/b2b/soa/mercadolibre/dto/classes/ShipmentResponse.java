package bbr.b2b.soa.mercadolibre.dto.classes;

import java.io.Serializable;

import bbr.b2b.soa.mercadolibre.dto.classes.shipments.ShipmentData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ShipmentResponse implements Serializable {

	private ShipmentData data;

	private String jsonBody;

}
