package bbr.b2b.logistic.customer.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IReception;

public class ReceptionW extends ElementDTO implements IReception {

	private Long receptionnumber;
	private Long ordernumber;
	private String ordertypename;
	private boolean complete;
	private Date receptiondate;
	private Double total;
	private String paymentcondition;
	private String observation;
	private String responsible;
	private Long ordertypeid;
	private Long soastatetypeid;
	private Long buyerid;
	private Long vendorid;
	private Long deliveryplaceid;

	public Long getReceptionnumber(){ 
		return this.receptionnumber;
	}
	public Long getOrdernumber(){ 
		return this.ordernumber;
	}
	public String getOrdertypename(){ 
		return this.ordertypename;
	}
	public boolean getComplete(){ 
		return this.complete;
	}
	public Date getReceptiondate(){ 
		return this.receptiondate;
	}
	public Double getTotal(){ 
		return this.total;
	}
	public String getPaymentcondition(){ 
		return this.paymentcondition;
	}
	public String getObservation(){ 
		return this.observation;
	}
	public String getResponsible(){ 
		return this.responsible;
	}
	public Long getOrdertypeid(){ 
		return this.ordertypeid;
	}
	public Long getSoastatetypeid(){ 
		return this.soastatetypeid;
	}
	public Long getBuyerid(){ 
		return this.buyerid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getDeliveryplaceid(){ 
		return this.deliveryplaceid;
	}
	public void setReceptionnumber(Long receptionnumber){ 
		this.receptionnumber = receptionnumber;
	}
	public void setOrdernumber(Long ordernumber){ 
		this.ordernumber = ordernumber;
	}
	public void setOrdertypename(String ordertypename){ 
		this.ordertypename = ordertypename;
	}
	public void setComplete(boolean complete){ 
		this.complete = complete;
	}
	public void setReceptiondate(Date receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setTotal(Double total){ 
		this.total = total;
	}
	public void setPaymentcondition(String paymentcondition){ 
		this.paymentcondition = paymentcondition;
	}
	public void setObservation(String observation){ 
		this.observation = observation;
	}
	public void setResponsible(String responsible){ 
		this.responsible = responsible;
	}
	public void setOrdertypeid(Long ordertypeid){ 
		this.ordertypeid = ordertypeid;
	}
	public void setSoastatetypeid(Long soastatetypeid){ 
		this.soastatetypeid = soastatetypeid;
	}
	public void setBuyerid(Long buyerid){ 
		this.buyerid = buyerid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setDeliveryplaceid(Long deliveryplaceid){ 
		this.deliveryplaceid = deliveryplaceid;
	}
}
