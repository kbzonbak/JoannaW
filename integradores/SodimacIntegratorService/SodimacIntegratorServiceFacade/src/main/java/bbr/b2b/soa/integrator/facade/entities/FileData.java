package bbr.b2b.soa.integrator.facade.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FileData {

	private Long id;
	private int version;
	private String type;
	private String data;
	private LocalDateTime loadDate;
	private String vendorCode;
	private String reference;

}