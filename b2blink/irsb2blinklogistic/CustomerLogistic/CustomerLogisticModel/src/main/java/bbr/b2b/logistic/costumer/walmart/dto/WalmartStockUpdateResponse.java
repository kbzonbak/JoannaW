package bbr.b2b.logistic.costumer.walmart.dto;

public class WalmartStockUpdateResponse {
	
	private String processId;
	private ItemsResponse items;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public ItemsResponse getItems() {
		return items;
	}
	public void setItems(ItemsResponse items) {
		this.items = items;
	}
	
}
