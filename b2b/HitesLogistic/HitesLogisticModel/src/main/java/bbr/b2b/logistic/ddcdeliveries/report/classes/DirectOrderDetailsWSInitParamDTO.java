package bbr.b2b.logistic.ddcdeliveries.report.classes;

import java.io.Serializable;

public class DirectOrderDetailsWSInitParamDTO implements Serializable {

	private String sku;

	private Long cantidad;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
}
