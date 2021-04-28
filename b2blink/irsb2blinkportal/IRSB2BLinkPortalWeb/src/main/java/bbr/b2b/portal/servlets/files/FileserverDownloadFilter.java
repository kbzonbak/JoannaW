package bbr.b2b.portal.servlets.files;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.PortalConstants;
import cl.bbr.core.classes.basics.UserFilesData;

public class FileserverDownloadFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException 
	{
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain fchain) throws IOException, ServletException 
	{
		System.out.println("popo");
		HttpServletRequest request 	= (HttpServletRequest) sreq;
		HttpServletResponse response 	= (HttpServletResponse) sres;		

		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");

		String url 	= 	request.getPathInfo();

		String downloadFileName = url.substring(url.lastIndexOf("/") + 1);

		HttpSession session = request.getSession(false);
		boolean allowRequest = ((session != null) && !session.isNew() && session.getAttribute(BbrUtilsConstants.USER_SESSION_BEAN_NAME) != null);
		Logger logguer = Logger.getLogger(FileserverDownloadFilter.class.getName());
		logguer.log(Level.INFO, "Ruta solicitada: " + url);

		Object objUserFileData = session.getAttribute(BbrUtilsConstants.FILES_SESSION_BEAN_NAME);

		UserFilesData 	userFileData 	= null;
		String 			realFileName	= null;

		if(objUserFileData != null)
		{
			userFileData = (UserFilesData) objUserFileData;
			realFileName = userFileData.getRealFileName(downloadFileName);
		}

		if (allowRequest)
		{
			// Ruta del archivo en carpeta cluster
			String clusterFileStr = PortalConstants.getInstance().getFileUploadPath() + realFileName;			
	
			OutputStream out = new DataOutputStream(response.getOutputStream());	
			FileInputStream fileInputStream = null;
			try
			{					
				File fileToDownload = new File(clusterFileStr); // nombre del archivo almacenado en el disco

				response.setContentLength(new Long(fileToDownload.length()).intValue());
				response.setHeader("Content-Disposition", "attachment; filename=\""+downloadFileName+"\"");

				fileInputStream = new FileInputStream(fileToDownload);			
				int i;
				while ((i = fileInputStream.read()) != -1) 
				{
					out.write(i);
				}					
			}
			catch(IOException e)
			{
				e.printStackTrace();
				logguer.log(Level.INFO, e.getStackTrace().toString());
				response.sendError(404);
			}
			finally
			{		
				fileInputStream.close();
				out.close();
			}				
		}
		else
		{
			response.sendError(404);
			logguer.log(Level.INFO, "404");
		}

	}


	public void destroy() { }
}
