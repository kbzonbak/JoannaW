package bbr.b2b.regional.logistic.msgregionalb2b;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface AsnToXmlRemote extends IAsnToXml {

}
