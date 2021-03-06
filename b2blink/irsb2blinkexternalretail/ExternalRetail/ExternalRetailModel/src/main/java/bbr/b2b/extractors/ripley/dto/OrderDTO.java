package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class OrderDTO {
	private String po_number;
	private String po_status;
	private String po_type;
	private String po_issued_date;
	private String po_effective_date;
	private String po_expiration_date;
	private String po_payment_method;
	private String po_obs;
	private String po_total_base_cost;
	private String po_total_net_cost;
	private String po_total_quantity;
	private String po_total_charges;
	private String po_total_discounts;
	private List<DetailsDTO> details;
	public String getPo_number() {
		return po_number;
	}
	public void setPo_number(String po_number) {
		this.po_number = po_number;
	}
	public String getPo_status() {
		return po_status;
	}
	public void setPo_status(String po_status) {
		this.po_status = po_status;
	}
	public String getPo_type() {
		return po_type;
	}
	public void setPo_type(String po_type) {
		this.po_type = po_type;
	}
	public String getPo_issued_date() {
		return po_issued_date;
	}
	public void setPo_issued_date(String po_issued_date) {
		this.po_issued_date = po_issued_date;
	}
	public String getPo_effective_date() {
		return po_effective_date;
	}
	public void setPo_effective_date(String po_effective_date) {
		this.po_effective_date = po_effective_date;
	}
	public String getPo_expiration_date() {
		return po_expiration_date;
	}
	public void setPo_expiration_date(String po_expiration_date) {
		this.po_expiration_date = po_expiration_date;
	}
	public String getPo_payment_method() {
		return po_payment_method;
	}
	public void setPo_payment_method(String po_payment_method) {
		this.po_payment_method = po_payment_method;
	}
	public String getPo_obs() {
		return po_obs;
	}
	public void setPo_obs(String po_obs) {
		this.po_obs = po_obs;
	}
	public String getPo_total_base_cost() {
		return po_total_base_cost;
	}
	public void setPo_total_base_cost(String po_total_base_cost) {
		this.po_total_base_cost = po_total_base_cost;
	}
	public String getPo_total_net_cost() {
		return po_total_net_cost;
	}
	public void setPo_total_net_cost(String po_total_net_cost) {
		this.po_total_net_cost = po_total_net_cost;
	}
	public String getPo_total_quantity() {
		return po_total_quantity;
	}
	public void setPo_total_quantity(String po_total_quantity) {
		this.po_total_quantity = po_total_quantity;
	}
	public String getPo_total_charges() {
		return po_total_charges;
	}
	public void setPo_total_charges(String po_total_charges) {
		this.po_total_charges = po_total_charges;
	}
	public String getPo_total_discounts() {
		return po_total_discounts;
	}
	public void setPo_total_discounts(String po_total_discounts) {
		this.po_total_discounts = po_total_discounts;
	}
	public List<DetailsDTO> getDetails() {
		return details;
	}
	public void setDetails(List<DetailsDTO> details) {
		this.details = details;
	}
}
