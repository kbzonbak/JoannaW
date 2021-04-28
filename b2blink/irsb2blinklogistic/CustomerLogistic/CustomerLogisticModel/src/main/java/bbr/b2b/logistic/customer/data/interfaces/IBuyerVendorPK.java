package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IBuyerVendorPK extends IPrimaryKey {
	Long getBuyerid();
	Long getVendorid();
	String getCode();
	void setBuyerid(Long paramLong);
	void setVendorid(Long paramLong);
	void setCode(String paramString);
}
