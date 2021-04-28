package bbr.b2b.soa.mercadolibre.dto.classes;

import bbr.b2b.soa.mercadolibre.dto.interfaces.IITemStockUpdate;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ItemStockUpdateData implements IITemStockUpdate {

	private int availableQuantity;

}
