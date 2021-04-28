package bbr.b2b.logistic.costumer.walmart.dto;

import java.util.List;

public class ItemsResponse {
	
	private List<String> error;
	private List<String> ok;
	
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public List<String> getOk() {
		return ok;
	}
	public void setOk(List<String> ok) {
		this.ok = ok;
	}

}
