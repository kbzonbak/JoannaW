package bbr.b2b.soa.mercadolibre.dto.classes.shipments;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ShipmentDimensions implements Serializable {

	private Integer height;

	private Integer width;

	private Integer length;

	private Integer weight;

}
