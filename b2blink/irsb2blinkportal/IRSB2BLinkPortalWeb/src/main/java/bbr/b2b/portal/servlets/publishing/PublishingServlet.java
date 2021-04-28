package bbr.b2b.portal.servlets.publishing;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;

public class PublishingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1717211445353577684L;
	private File dir = null;
	private String uploadAttachDir = null;
	private String uploadImageDir = null;

	public PublishingServlet(){
		super();
	}

	public void destroy() {
		super.destroy(); 	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Subida de archivos
		
		// La subida tambien es por tipo
		// 0: Adjuntos (Carpeta temporal)
		// 1: Imagenes (Timestamp)
		String filename = "";
		String fname="";
		String ext="";
		
		Part part = null;
		FilePart filePart = null;
		MultipartParser mp = new MultipartParser(request, 10 * 1024 * 1024); // 10MB
			
		try {
			while ((part = mp.readNextPart()) != null) 
			{
				if (!part.isFile()) 
				{
					continue;
				}						
				filePart = (FilePart) part;
				filename =  new String(filePart.getFileName().getBytes("ISO-8859-1"), "UTF-8");
				
				int mid = filename.lastIndexOf(".");
				fname = filename.substring(0, mid);
				ext = filename.substring(mid + 1, filename.length()); 			
				
				filename = fname + new Date().getTime() + "." + ext;
				
				// Creando directorio
				String path = uploadAttachDir;
				dir = new File(path);
				dir.mkdirs();
				
				// Creado archivo
				File file = new File(dir, filename);
				
				if (!dir.isDirectory()) {
					throw new ServletException("Supplied uploadDir " + path + " is invalid");
				}
				
				long cantBytes = filePart.writeTo(file);
				response.setContentLength((int) cantBytes);
				System.out.println("Se escribió el archivo: " + file.getAbsolutePath() + " de tamaño: " + cantBytes + " bytes");
			
			}

		} catch (IOException e) {
			System.out.println("Error escribiendo el archivo ".concat(filename));
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("Error desconocido transfiriendo el archivo ".concat(filename));
			e.printStackTrace();
		}
		
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		uploadAttachDir = BbrPublishingConstants.PUBLISHING_FILES_PATH;
		uploadImageDir = BbrPublishingConstants.PUBLISHING_FILES_PATH;
		
		if (uploadAttachDir == null) {
			throw new ServletException("Please supply uploadDir parameter");
		}	
		if (uploadImageDir == null) {
			throw new ServletException("Please supply uploadDir parameter");
		}	
	}
}
