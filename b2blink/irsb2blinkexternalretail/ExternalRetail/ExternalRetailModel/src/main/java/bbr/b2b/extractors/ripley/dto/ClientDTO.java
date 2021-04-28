package bbr.b2b.extractors.ripley.dto;
import org.codehaus.jackson.annotate.JsonProperty;

public class ClientDTO {
	private String client_name;
	@JsonProperty(value="client_fis ")
	private String client_fis;
	private String client_phone;
	private String client_email;
	private String client_address;
	private String client_region;
	private String client_county;
	private String client_city;
	private String client_delivery_place_obs;
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getClient_fis() {
		return client_fis;
	}
	public void setClient_fis(String client_fis) {
		this.client_fis = client_fis;
	}
	public String getClient_phone() {
		return client_phone;
	}
	public void setClient_phone(String client_phone) {
		this.client_phone = client_phone;
	}
	public String getClient_email() {
		return client_email;
	}
	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}
	public String getClient_address() {
		return client_address;
	}
	public void setClient_address(String client_address) {
		this.client_address = client_address;
	}
	public String getClient_region() {
		return client_region;
	}
	public void setClient_region(String client_region) {
		this.client_region = client_region;
	}
	public String getClient_county() {
		return client_county;
	}
	public void setClient_county(String client_county) {
		this.client_county = client_county;
	}
	public String getClient_city() {
		return client_city;
	}
	public void setClient_city(String client_city) {
		this.client_city = client_city;
	}
	public String getClient_delivery_place_obs() {
		return client_delivery_place_obs;
	}
	public void setClient_delivery_place_obs(String client_delivery_place_obs) {
		this.client_delivery_place_obs = client_delivery_place_obs;
	}
}
