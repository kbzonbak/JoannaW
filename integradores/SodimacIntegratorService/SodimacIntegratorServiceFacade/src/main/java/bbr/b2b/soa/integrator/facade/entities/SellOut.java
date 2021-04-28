package bbr.b2b.soa.integrator.facade.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SellOut {

	private Long id;
	private int version;
	private LocalDateTime when;
	private String path;
	private Vendor vendor;
	private SoaStateType soaStateType;
	private SellOutType sellOutType;

}