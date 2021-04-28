package bbr.b2b.soa.mercadolibre.dto.classes;

import java.io.Serializable;

import bbr.b2b.soa.mercadolibre.dto.classes.items.ItemData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ItemResponse implements Serializable {

	private ItemData data;

	private String jsonBody;

}
