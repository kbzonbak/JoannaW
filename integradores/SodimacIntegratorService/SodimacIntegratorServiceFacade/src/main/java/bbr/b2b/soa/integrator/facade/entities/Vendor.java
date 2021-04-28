package bbr.b2b.soa.integrator.facade.entities;

import lombok.Data;

@Data
public class Vendor {

	private Long id;
	private int version;
	private String code;
	private String name;
	private String gln;
	private String credentials;
	private Boolean active;	

}