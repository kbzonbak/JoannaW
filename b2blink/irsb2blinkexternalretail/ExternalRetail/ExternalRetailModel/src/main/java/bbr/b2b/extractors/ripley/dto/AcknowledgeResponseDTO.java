package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class AcknowledgeResponseDTO {
	private String status_code;
	private List<AcknowledgeDetailDTO> details;
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public List<AcknowledgeDetailDTO> getDetails() {
		return details;
	}
	public void setDetails(List<AcknowledgeDetailDTO> details) {
		this.details = details;
	}
}
