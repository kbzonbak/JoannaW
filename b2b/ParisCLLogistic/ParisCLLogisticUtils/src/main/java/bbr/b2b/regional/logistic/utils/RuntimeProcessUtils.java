package bbr.b2b.regional.logistic.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.resource.spi.work.Work;

public class RuntimeProcessUtils implements Work{

	private InputStream inputStream = null;
	private String type = "";
	
	/**
	 * @param inputStream: Stream
	 * @param type: Tipo de salida
	 */	
	public RuntimeProcessUtils(InputStream inputStream, String type){
		try{
			this.inputStream = inputStream;
			this.type = type;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void release() {
				
	}

	public void run() {
		try{
			InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
		}catch (Exception e) {
			e.printStackTrace();
		}			
	}	
}
