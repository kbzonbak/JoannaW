package bbr.b2b.regional.logistic.report.classes;

import java.util.Comparator;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class BaseResultComparator implements Comparator{
	
	public String orderer;
	public Boolean asc;

	public BaseResultComparator(String order, boolean ascendent) {
		orderer = order;
		asc = ascendent;
	}
	public int compare(Object obj1, Object obj2) {
		BaseResultDTO obj01 = (BaseResultDTO)obj1;
		BaseResultDTO obj02 = (BaseResultDTO)obj2;

		int result = 0;

		if(orderer.equalsIgnoreCase("statusmessage")){
			if(asc){
				result = (obj01.getStatusmessage().compareTo(obj02.getStatusmessage()));
			}else{
				result = (obj02.getStatusmessage().compareTo(obj01.getStatusmessage()));
			}
		}
		
		if(orderer.equalsIgnoreCase("statuscode")){

			if(asc){
				result = obj01.getStatuscode().compareTo(obj02.getStatuscode());
			}else{
				result = obj02.getStatuscode().compareTo(obj01.getStatuscode());
			}
			
		}		
		return result;
	}	
}
