package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVeVAudit extends IElement{

	public Date getWhen();
	public void setWhen(Date when);
	public String getUsername();
	public void setUsername(String username);
	public String getUsertype();
	public void setUsertype(String usertype);
	public String getUserenterprisecode();
	public void setUserenterprisecode(String userenterprisecode);
	public String getUserenterprisename();
	public void setUserenterprisename(String userenterprisename);
	public String getInterfacecontent();
	public void setInterfacecontent(String interfacecontent);	
	public String getItem();
	public void setItem(String item);
	public String getItemvalue();
	public void setItemvalue(String itemvalue);
	public String getInitialdata();
	public void setInitialdata(String initialdata);
	public String getFinaldata();
	public void setFinaldata(String finaldata);
		
}
