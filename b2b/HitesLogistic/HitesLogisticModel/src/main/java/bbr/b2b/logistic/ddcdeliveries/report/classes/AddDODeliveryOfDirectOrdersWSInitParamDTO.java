package bbr.b2b.logistic.ddcdeliveries.report.classes;

public class AddDODeliveryOfDirectOrdersWSInitParamDTO {

	private String rutproveedor;
	private DirectOrdersWSInitParamDTO[] ordenes;

	public String getRutproveedor() {
		return rutproveedor;
	}
	public void setRutproveedor(String rutproveedor) {
		this.rutproveedor = rutproveedor;
	}
	public DirectOrdersWSInitParamDTO[] getOrdenes() {
		return ordenes;
	}
	public void setOrdenes(DirectOrdersWSInitParamDTO[] ordenes) {
		this.ordenes = ordenes;
	}
}
