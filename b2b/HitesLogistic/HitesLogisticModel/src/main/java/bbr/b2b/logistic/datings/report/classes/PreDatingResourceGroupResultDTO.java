package bbr.b2b.logistic.datings.report.classes;

public class PreDatingResourceGroupResultDTO extends ResourceBlockingGroupResultDTO {

	private Long id;
	private String vendorcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

}
