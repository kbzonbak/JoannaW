package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.Comparator;

public class AsnDetailComparator implements Comparator{
	
	public AsnDetailComparator() {
				
	}

	public int compare(Object obj1, Object obj2) {

		AsnDetailW bd1 = (AsnDetailW) obj1;
		AsnDetailW bd2 = (AsnDetailW) obj2;

		long orderid1 = -2;
		long orderid2 = -2;

		long bulkid1 = -2;
		long bulkid2 = -2;
		
		String refdoc1 = "";
		String refdoc2 = "";

		orderid1 = (bd1.getOrderid()).longValue();
		orderid2 = (bd2.getOrderid()).longValue();

		bulkid1 = (bd1.getBulkid()).longValue();
		bulkid2 = (bd2.getBulkid()).longValue();
		
		refdoc1 = bd1.getRefdocument();
		refdoc2 = bd2.getRefdocument();

		long comp = orderid1 - orderid2;
		if (comp > 0)
			//... If different lengths, we're done.
			return 1;
		else if (comp < 0)
			return -1;
		else {
			//... Si son iguales ordenamos por Bulk_id
			comp = bulkid1 - bulkid2;
			if (comp > 0)
				return 1;
			else if (comp < 0)
				return -1;
			else{
				//... Si son iguales ordenamos por refdocument
				comp = refdoc1.compareTo(refdoc2);
				if (comp > 0)
					return 1;
				else if (comp < 0)
					return -1;
				else
					return 0;			
			}
				
		}
	}
}
