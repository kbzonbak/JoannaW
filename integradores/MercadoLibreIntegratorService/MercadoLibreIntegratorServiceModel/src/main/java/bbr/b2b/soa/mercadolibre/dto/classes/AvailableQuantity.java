package bbr.b2b.soa.mercadolibre.dto.classes;

import bbr.b2b.soa.mercadolibre.dto.interfaces.IAuthorizationData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class AvailableQuantity implements IAuthorizationData {

	private int available_quantity;

}
