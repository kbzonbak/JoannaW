package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.Comparator;

import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;

public class OrderDeliveryDetailComparator implements Comparator{
	
	public OrderDeliveryDetailComparator() {
		
	}
	
	public int compare(Object obj1, Object obj2) {

		BulkDetailW odd1 = (BulkDetailW) obj1;
		BulkDetailW odd2 = (BulkDetailW) obj2;

		long orderid1 = -2;
		long orderid2 = -2;

		long itemid1 = -2;
		long itemid2 = -2;

		String refdoc1 = "";
		String refdoc2 = "";
		
		orderid1 = (odd1.getOrderid()).longValue();
		orderid2 = (odd2.getOrderid()).longValue();

		itemid1 = (odd1.getItemid()).longValue();
		itemid2 = (odd2.getItemid()).longValue();

		refdoc1 = odd1.getRefdocument();
		refdoc2 = odd2.getRefdocument();
		
		long comp = orderid1 - orderid2;
		if (comp > 0)
			//... If different lengths, we're done.
			return 1;
		else if (comp < 0)
			return -1;
		else {
			//... Si son iguales ordenamos por Bulk_id
			comp = itemid1 - itemid2;
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
