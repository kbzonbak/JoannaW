package bbr.b2b.soa.mercadolibre.dto.classes.orders;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class BillingInfo implements Serializable {

	private String doc_type;

	private String doc_number;

}
