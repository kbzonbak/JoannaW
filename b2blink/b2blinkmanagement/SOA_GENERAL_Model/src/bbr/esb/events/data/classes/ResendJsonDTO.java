package bbr.esb.events.data.classes;

public class ResendJsonDTO {

	Long[] orders;

	String state;

	public Long[] getOrders() {
		return orders;
	}

	public void setOrders(Long[] orders) {
		this.orders = orders;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
