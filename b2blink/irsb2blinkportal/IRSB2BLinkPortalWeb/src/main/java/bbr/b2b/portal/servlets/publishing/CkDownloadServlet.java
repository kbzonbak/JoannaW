package bbr.b2b.portal.servlets.publishing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class CkDownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027762825010017553L;

	public CkDownloadServlet()
	{
		super();
	}

	public void destroy() 
	{
		super.destroy(); 	
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		String realpath = "";
		String filename = "";
		String disHeader = "";
		String mimetype = "";
		
		String baseDir     = PortalConstants.getInstance().getFileCkUploadPath();
		
		realpath = baseDir+ getLocalFilepathFromRequest(request);
		
		try
		{
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", disHeader + ";filename=\"" + filename + "\"");
			OutputStream out = new DataOutputStream(response.getOutputStream());
			
			// transfer the file byte-by-byte to the response object
			File fileToDownload = new File(realpath);
			FileInputStream fileInputStream = new FileInputStream(fileToDownload);
			int i;
			while ((i=fileInputStream.read())!=-1)
			{
			   out.write(i);
			}
			fileInputStream.close();
			out.close();
		}
		catch (Exception e) 
		{
			//e.printStackTrace();
		}		
	}

	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
	
	private String getLocalFilepathFromRequest(HttpServletRequest request)
	{
		String contextPath 	= request.getContextPath();
		String pathRequest 	= request.getRequestURI(); 
		String imageURL 	= B2BSystemPropertiesUtil.getProperty("ck_upload_file_url"); 
		
		String realFilePath = pathRequest.replace(contextPath+imageURL,"");
		
		return realFilePath;
	}
}
