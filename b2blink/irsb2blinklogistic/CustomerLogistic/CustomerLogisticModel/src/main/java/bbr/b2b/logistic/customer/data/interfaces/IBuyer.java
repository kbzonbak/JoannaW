package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;
import java.util.Date;

public interface IBuyer extends IElement {
	Date getCreated();
	void setCreated(Date created);
	String getCode();
	String getName();
	String getGln();
	String getBusinessarea();
	String getRut();
	String getDv();
	String getRazonsocial();
	String getSitename();
	String getBilltopartner();
	void setCode(String code);
	void setName(String name);
	void setGln(String gln);
	void setBusinessarea(String businessarea);
	void setRut(String rut);
	void setDv(String dv);
	void setRazonsocial(String razonsocial);
	void setSitename(String sitename);
	void setBilltopartner(String billtopartner);
}
