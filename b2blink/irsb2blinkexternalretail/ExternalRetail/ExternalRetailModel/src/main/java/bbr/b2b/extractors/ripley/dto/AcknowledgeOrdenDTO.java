package bbr.b2b.extractors.ripley.dto;

public class AcknowledgeOrdenDTO {
	
	private String order_id_value;

	public AcknowledgeOrdenDTO(String order_id_value) {
		this.order_id_value = order_id_value;
	}

	public String getOrder_id_value() {
		return order_id_value;
	}

	public void setOrder_id_value(String order_id_value) {
		this.order_id_value = order_id_value;
	}
}
