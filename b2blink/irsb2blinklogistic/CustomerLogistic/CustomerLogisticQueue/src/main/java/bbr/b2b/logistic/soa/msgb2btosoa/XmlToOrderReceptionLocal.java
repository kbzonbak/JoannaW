package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion;

@Local
public interface XmlToOrderReceptionLocal {

	public void processMessage(Recepcion recepcion) throws Exception;

}
