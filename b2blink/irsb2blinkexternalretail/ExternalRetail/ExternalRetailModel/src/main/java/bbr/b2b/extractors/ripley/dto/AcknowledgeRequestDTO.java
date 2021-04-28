package bbr.b2b.extractors.ripley.dto;

import java.util.List;

import bbr.b2b.extractors.ripley.dto.AcknowledgeOrdenDTO;

public class AcknowledgeRequestDTO {

	private List<AcknowledgeOrdenDTO> order_list;

	public List<AcknowledgeOrdenDTO> getOrder_list() {
		return order_list;
	}

	public void setOrder_list(List<AcknowledgeOrdenDTO> order_list) {
		this.order_list = order_list;
	} 
}

