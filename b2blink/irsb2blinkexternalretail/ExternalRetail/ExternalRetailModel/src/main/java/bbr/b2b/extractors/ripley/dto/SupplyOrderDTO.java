package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class SupplyOrderDTO {
	private String po_number;
	private String po_type;
	private String po_type_description;
	private String po_status;
	private String po_status_description;
	private String po_department_code;
	private String po_department;
	private String po_issued_date;
	private String po_effective_date;
	private String po_expiration_date;
	private String po_currency;
	private String po_payment_method;
	private String delivery_warehouse_code;
	private String delivery_warehouse_name;
	public String getPo_number() {
		return po_number;
	}
	public void setPo_number(String po_number) {
		this.po_number = po_number;
	}
	public String getPo_type() {
		return po_type;
	}
	public void setPo_type(String po_type) {
		this.po_type = po_type;
	}
	public String getPo_type_description() {
		return po_type_description;
	}
	public void setPo_type_description(String po_type_description) {
		this.po_type_description = po_type_description;
	}
	public String getPo_status() {
		return po_status;
	}
	public void setPo_status(String po_status) {
		this.po_status = po_status;
	}
	public String getPo_status_description() {
		return po_status_description;
	}
	public void setPo_status_description(String po_status_description) {
		this.po_status_description = po_status_description;
	}
	public String getPo_department_code() {
		return po_department_code;
	}
	public void setPo_department_code(String po_department_code) {
		this.po_department_code = po_department_code;
	}
	public String getPo_department() {
		return po_department;
	}
	public void setPo_department(String po_department) {
		this.po_department = po_department;
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
	public String getPo_currency() {
		return po_currency;
	}
	public void setPo_currency(String po_currency) {
		this.po_currency = po_currency;
	}
	public String getPo_payment_method() {
		return po_payment_method;
	}
	public void setPo_payment_method(String po_payment_method) {
		this.po_payment_method = po_payment_method;
	}
	public String getDelivery_warehouse_code() {
		return delivery_warehouse_code;
	}
	public void setDelivery_warehouse_code(String delivery_warehouse_code) {
		this.delivery_warehouse_code = delivery_warehouse_code;
	}
	public String getDelivery_warehouse_name() {
		return delivery_warehouse_name;
	}
	public void setDelivery_warehouse_name(String delivery_warehouse_name) {
		this.delivery_warehouse_name = delivery_warehouse_name;
	}
	
}
