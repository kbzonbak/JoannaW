package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.entities.Responsable;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.soa.entities.SOAStateType;
import bbr.b2b.logistic.buyorders.entities.OrderType;

import java.time.LocalDateTime;

import bbr.b2b.logistic.buyorders.data.interfaces.IOrder;

public class Order implements IOrder {

	private Long id;
	private Long number;
	private LocalDateTime emitted;
	private LocalDateTime creationdate;
	private Responsable responsable;
	private Retailer retailer;
	private Vendor vendor;
	private Section section;
	private OrderType ordertype;
	private Client client;
	private LocalDateTime currentsoastatetypedate;
	private SOAStateType currentsoastatetype;

	public Long getId() {
		return id;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDateTime getEmitted() {
		return emitted;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public Section getSection() {
		return section;
	}

	public OrderType getOrdertype() {
		return ordertype;
	}

	public Client getClient() {
		return client;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public void setOrdertype(OrderType ordertype) {
		this.ordertype = ordertype;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDateTime getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}

	public void setCurrentsoastatetypedate(LocalDateTime currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}

	public SOAStateType getCurrentsoastatetype() {
		return currentsoastatetype;
	}

	public void setCurrentsoastatetype(SOAStateType currentsoastatetype) {
		this.currentsoastatetype = currentsoastatetype;
	}
}
