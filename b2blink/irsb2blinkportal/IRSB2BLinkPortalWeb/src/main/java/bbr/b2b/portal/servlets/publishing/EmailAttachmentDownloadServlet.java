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

import org.apache.commons.lang.StringUtils;
import org.jboss.security.Base64Utils;

import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class EmailAttachmentDownloadServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 972039816594240844L;

	public EmailAttachmentDownloadServlet()
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

		String baseDir = PortalConstants.getInstance().getFileUploadPath();

		realpath = StringUtils.removeEnd(baseDir, "/") + getLocalFilepathFromRequest(request);

		try
		{
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", disHeader + ";filename=\"" + filename + "\"");
			OutputStream out = new DataOutputStream(response.getOutputStream());

			// transfer the file byte-by-byte to the response object
			File fileToDownload = new File(realpath);
			FileInputStream fileInputStream = new FileInputStream(fileToDownload);
			int i;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			out.close();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	private String getLocalFilepathFromRequest(HttpServletRequest request)
	{
		String contextPath = request.getContextPath();
		String pathRequest = request.getRequestURI();
		String fileURL = B2BSystemPropertiesUtil.getProperty("email_file_url");
		String realFilePath = pathRequest.replace(contextPath + "/" + fileURL, "");
		String realFileName = "";

		// VERIFICAR SI SE TRATA DE UN ARCHIVO DE CONTACTOS
		// SI ES ASI SE DEBE DECODIFICAR EL NOMBRE
		String contactPath = "CONTACT";
		String[] contactSplit = realFilePath.split("/");

		if (contactSplit[1].equals(contactPath))
		{
			byte[] realFileByte = Base64Utils.fromb64(contactSplit[2]);
			realFileName = new String(realFileByte);
			realFilePath = "/CONTACT/" + realFileName;
		}

		return realFilePath;
	}
}
