package bbr.b2b.regional.logistic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.resource.spi.work.Work;

import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;

public class BackUpUtils implements Work{

	private String destPath = "";
	private String content = "";
	private String number;
	private String type = "";
	
	/**
	 * @param content : Contenido del mensaje
	 * @param number: N°mero identificador del mensaje.
	 * @param type: Tipo de mensaje, OC(Orden); RE(Recepción); CA(Cancelación); MO(Modificación); ACK (Acknowledge)
	 */	
	public BackUpUtils(String content, String number, String type){
		try{
			this.content = content;
			this.number = number;
			this.type = type;
			this.destPath = RegionalLogisticConstants.getInstance().getBACKUP_MSG_PATH();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void release() {
				
	}

	public void run() {
		byte[] buf = new byte[1024];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				
		try{
			String destPathWithDate = destPath + sdf.format(new Date()) + "/"; 			
			
			if (!new File(destPathWithDate).exists()){
				new File(destPathWithDate).mkdirs();
			}			
			
			String xmlFileName = type + "_" + number + "_" + System.nanoTime();
			String xmlFilePath = destPathWithDate + xmlFileName + ".xml";
			String zipFilePath = destPathWithDate + xmlFileName + ".zip";
			
			File xmlFile = new File(xmlFilePath);
			
			// ALMACENO MENSAJE EN RUTA DE RESPALDO
			FileOutputStream fout = new FileOutputStream(xmlFile);
			PrintStream ps = new PrintStream(fout);
			ps.println(content);
			fout.close();
			
			// SE LEE EL ARCHIVO Y LUEGO SE COMPRIME EN UN ZIP		
			FileInputStream fin = new FileInputStream(xmlFile);		
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)));
					
			zout.putNextEntry(new ZipEntry(xmlFileName + ".xml"));
	 
	        int len= 0;
	         
	        while ((len = fin.read(buf)) > 0) {
	            zout.write(buf, 0, len);
	        }
	 
		    zout.closeEntry();
		    fin.close();
		    zout.close();
		    
		    // ELIMINA EL ARCHIVO .XML
		    xmlFile.delete();		    
		}catch (Exception e) {
			e.printStackTrace();
		}			
	}	
}
