package bbr.b2b.regional.logistic.evaluations.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationType;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRanking;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IOcDelEvaluation;

public class OcDelEvaluation implements IOcDelEvaluation {

	private Long id;
	private Long delivery;
	private Long order;
	private Date datingdate;
	private Double deliveryscore;
	private Double qualitypercent;
	private Double levelservicepercent;
	private Double totalreceivedunits;
	private Double totalavailableunits;
	private EvaluationType evaluationtype;
	private VendorDepartmentRanking vendordepartmentranking;

	public Long getId(){ 
		return this.id;
	}
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
	public EvaluationType getEvaluationtype(){ 
		return this.evaluationtype;
	}
	public VendorDepartmentRanking getVendordepartmentranking(){ 
		return this.vendordepartmentranking;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setEvaluationtype(EvaluationType evaluationtype){ 
		this.evaluationtype = evaluationtype;
	}
	public void setVendordepartmentranking(VendorDepartmentRanking vendordepartmentranking){ 
		this.vendordepartmentranking = vendordepartmentranking;
	}
}
