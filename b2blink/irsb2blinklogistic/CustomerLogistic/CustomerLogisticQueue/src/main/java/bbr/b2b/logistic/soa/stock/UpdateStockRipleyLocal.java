package bbr.b2b.logistic.soa.stock;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.logistic.customer.data.dto.StockDTO;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;

@Local
public interface UpdateStockRipleyLocal {

	public StockDTO StockProcessRipley(InventarioProveedor inventario, VendorW vendor, String credentials) throws Exception;
}
