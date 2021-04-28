package bbr.b2b.soa.mercadolibre.entities;

import lombok.Data;

@Data
public class Company {

	private Long id;

	private int version;

	private String rut;

	private String dv;

	private String contactinfo;

	private String address;

	private String phone;

	private Long userId;

	private String name;

}