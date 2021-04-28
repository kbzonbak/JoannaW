package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.util.Comparator;

public class VeVVendorNotificationComparator implements Comparator {
	
	public String orderer;
	public Boolean asc;

	public VeVVendorNotificationComparator(String order, boolean ascendent) {
		orderer = order;
		asc = ascendent;
	}
	public int compare(Object obj1, Object obj2) {
		VeVVendorNotificationDTO obj01 = (VeVVendorNotificationDTO)obj1;
		VeVVendorNotificationDTO obj02 = (VeVVendorNotificationDTO)obj2;

		int result = 0;

		if (orderer.equalsIgnoreCase("vendortradename")) {
			if(asc) {
				result = (obj01.getVendortradename().compareTo(obj02.getVendortradename()));
			} else {
				result = (obj02.getVendortradename().compareTo(obj01.getVendortradename()));
			}
		}
		
		if (orderer.equalsIgnoreCase("ordernumber")) {
			if(asc) {
				result = obj01.getOrdernumber().compareTo(obj02.getOrdernumber());
			} else {
				result = obj02.getOrdernumber().compareTo(obj01.getOrdernumber());
			}			
		}
		
		if (orderer.equalsIgnoreCase("requestnumber")) {
			if(asc) {
				result = obj01.getRequestnumber().compareTo(obj02.getRequestnumber());
			} else {
				result = obj02.getRequestnumber().compareTo(obj01.getRequestnumber());
			}			
		}
		
		if (orderer.equalsIgnoreCase("originaldeliverydate")) {
			if(asc) {
				result = obj01.getOriginaldeliverydate().compareTo(obj02.getOriginaldeliverydate());
			} else {
				result = obj02.getOriginaldeliverydate().compareTo(obj01.getOriginaldeliverydate());
			}			
		}
		
		if (orderer.equalsIgnoreCase("vevtype")) {
			if(asc) {
				result = obj01.getVevtype().compareTo(obj02.getVevtype());
			} else {
				result = obj02.getVevtype().compareTo(obj01.getVevtype());
			}			
		}
		
		return result;
	}	
}
