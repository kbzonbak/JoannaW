package bbr.b2b.logistic.customer.classes;

import java.util.Date;

import javax.ejb.Local;

import bbr.b2b.logistic.customer.data.dto.PendingSOAOrderDTO;
import bbr.b2b.logistic.customer.interfaces.IOrderServer;

@Local
public interface OrderServerLocal extends IOrderServer {

	PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long id, Long vendorid, Date time, Date date);

}
