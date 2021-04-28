package bbr.b2b.logistic.customer.data.dto;

public class PLKeyDTO {
	
	private String ocnumber;
	private String sku;
	private String locationcode;
	private String numfact;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PLKeyDTO){
			PLKeyDTO plkeydto = (PLKeyDTO) obj ;
			return (this.ocnumber.equals(plkeydto.getOcnumber()) && this.sku.equals(plkeydto.getSku()) && this.locationcode.equals(plkeydto.getLocationcode()) && this.numfact.equals(plkeydto.getNumfact()));
		}
		return false;
	}
	@Override
	public int hashCode() {
		return this.ocnumber.hashCode() + this.sku.hashCode() + this.locationcode.hashCode() + this.numfact.hashCode();
	}
	
	public String getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(String ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getNumfact() {
		return numfact;
	}
	public void setNumfact(String numfact) {
		this.numfact = numfact;
	}
	
}
