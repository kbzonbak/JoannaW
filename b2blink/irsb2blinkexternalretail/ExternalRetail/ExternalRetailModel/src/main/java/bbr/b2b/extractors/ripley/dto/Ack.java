package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class Ack {
	private List<OrderAckDTO> order_list;

	public List<OrderAckDTO> getOrder_list() {
		return order_list;
	}

	public void setOrder_list(List<OrderAckDTO> order_list) {
		this.order_list = order_list;
	}

	
}
