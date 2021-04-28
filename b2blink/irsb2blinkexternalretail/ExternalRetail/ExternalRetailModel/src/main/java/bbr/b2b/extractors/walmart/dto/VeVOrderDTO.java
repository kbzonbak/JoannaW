package bbr.b2b.extractors.walmart.dto;

public class VeVOrderDTO {

	private String purchaseDate;
	private String purchaseOrder;
	private String shippingGroupID;
	private String deliverTo;
	private String warehouseCode;
	private String warehouse;
	private String clientRUT;
	private String clientName;
	private String clientPhone;
	private String clientAddress;
	
	private String city;
	private String itemNumber;
	private String department;
	
	private String productNumber;
	private String barcode;
	private String productDescription;
	private String innerPack;
	private Double quantity;
	private Double cost;
	private Double unitCost;
	private String dropShipDate;
	

	public String getDropShipDate() {
		return dropShipDate;
	}
	public void setDropShipDate(String dropShipDate) {
		this.dropShipDate = dropShipDate;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	private String status;
	
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getShippingGroupID() {
		return shippingGroupID;
	}
	public void setShippingGroupID(String shippingGroupID) {
		this.shippingGroupID = shippingGroupID;
	}
	public String getDeliverTo() {
		return deliverTo;
	}
	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}
	public String getClientRUT() {
		return clientRUT;
	}
	public void setClientRUT(String clientRUT) {
		this.clientRUT = clientRUT;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getInnerPack() {
		return innerPack;
	}
	public void setInnerPack(String innerPack) {
		this.innerPack = innerPack;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
