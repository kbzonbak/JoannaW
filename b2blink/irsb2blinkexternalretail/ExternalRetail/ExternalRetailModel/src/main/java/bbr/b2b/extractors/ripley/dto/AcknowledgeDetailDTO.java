package bbr.b2b.extractors.ripley.dto;

public class AcknowledgeDetailDTO {
	private String order_id_value;
	private String po_number_status;
	private String po_message_status;
	public String getOrder_id_value() {
		return order_id_value;
	}
	public void setOrder_id_value(String order_id_value) {
		this.order_id_value = order_id_value;
	}
	public String getPo_number_status() {
		return po_number_status;
	}
	public void setPo_number_status(String po_number_status) {
		this.po_number_status = po_number_status;
	}
	public String getPo_message_status() {
		return po_message_status;
	}
	public void setPo_message_status(String po_message_status) {
		this.po_message_status = po_message_status;
	}
}