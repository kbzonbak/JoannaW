package bbr.b2b.regional.logistic.notifications.data.classes;

import java.io.Serializable;

public class VendorNoticationDTO implements Serializable{

	private Long id;
	private String rut;
	private String name;
	private String tradename;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

}
