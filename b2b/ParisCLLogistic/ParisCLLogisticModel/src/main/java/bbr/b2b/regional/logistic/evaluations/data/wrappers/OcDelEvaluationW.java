package bbr.b2b.regional.logistic.evaluations.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IOcDelEvaluation;

public class OcDelEvaluationW extends ElementDTO implements IOcDelEvaluation {

	private Long delivery;
	private Long order;
	private Date datingdate;
	private Double deliveryscore;
	private Double qualitypercent;
	private Double levelservicepercent;
	private Double totalreceivedunits;
	private Double totalavailableunits;
	private Long evaluationtypeid;
	private Long vendorid;
	private Long rankingid;
	private Long departmentid;

	public Long getDelivery(){ 
		return this.delivery;
	}
	public Long getOrder(){ 
		return this.order;
	}
	public Date getDatingdate(){ 
		return this.datingdate;
	}
	public Double getDeliveryscore(){ 
		return this.deliveryscore;
	}
	public Double getQualitypercent(){ 
		return this.qualitypercent;
	}
	public Double getLevelservicepercent(){ 
		return this.levelservicepercent;
	}
	public Double getTotalreceivedunits(){ 
		return this.totalreceivedunits;
	}
	public Double getTotalavailableunits(){ 
		return this.totalavailableunits;
	}
	public Long getEvaluationtypeid(){ 
		return this.evaluationtypeid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getRankingid(){ 
		return this.rankingid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public void setDelivery(Long delivery){ 
		this.delivery = delivery;
	}
	public void setOrder(Long order){ 
		this.order = order;
	}
	public void setDatingdate(Date datingdate){ 
		this.datingdate = datingdate;
	}
	public void setDeliveryscore(Double deliveryscore){ 
		this.deliveryscore = deliveryscore;
	}
	public void setQualitypercent(Double qualitypercent){ 
		this.qualitypercent = qualitypercent;
	}
	public void setLevelservicepercent(Double levelservicepercent){ 
		this.levelservicepercent = levelservicepercent;
	}
	public void setTotalreceivedunits(Double totalreceivedunits){ 
		this.totalreceivedunits = totalreceivedunits;
	}
	public void setTotalavailableunits(Double totalavailableunits){ 
		this.totalavailableunits = totalavailableunits;
	}
	public void setEvaluationtypeid(Long evaluationtypeid){ 
		this.evaluationtypeid = evaluationtypeid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setRankingid(Long rankingid){ 
		this.rankingid = rankingid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
}
