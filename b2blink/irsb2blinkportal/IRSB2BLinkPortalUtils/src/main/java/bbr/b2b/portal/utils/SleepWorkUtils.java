package bbr.b2b.portal.utils;

import java.util.Calendar;

import javax.resource.spi.work.Work;

public class SleepWorkUtils implements Work{

	private Integer count = 0;
	
	public SleepWorkUtils(Integer count){
		this.count = count;
	}
	
	public void release() {
				
	}

	public void run() {
		// DO NOTHING		
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MILLISECOND, count);
		while(true){
			Calendar cal2 = Calendar.getInstance();
			if (cal1.getTimeInMillis() - cal2.getTimeInMillis() <= 0){
				break;
			}			
		} 		
	}	
}
