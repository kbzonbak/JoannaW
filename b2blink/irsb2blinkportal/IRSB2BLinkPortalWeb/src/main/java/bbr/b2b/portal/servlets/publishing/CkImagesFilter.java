package bbr.b2b.portal.servlets.publishing;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.servlets.publishing.classes.CkImageSaver;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

/**
 *
 */
public class CkImagesFilter implements Filter
{
	private CkImageSaver ckImageSaver;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		String imageBaseDir = PortalConstants.getInstance().getFileCkUploadPath();
		String imageDomain = B2BSystemPropertiesUtil.getProperty("ck_upload_file_url");

		String[] allowFileTypeArr = null;
		String allowFileType = B2BSystemPropertiesUtil.getProperty("ck_upload_image_types");
		if (StringUtils.isNotBlank(allowFileType))
		{
			allowFileTypeArr = StringUtils.split(allowFileType, ",");
		}

		ckImageSaver = new CkImageSaver(imageBaseDir, imageDomain, allowFileTypeArr);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (request.getContentType() == null || request.getContentType().indexOf("multipart") == -1)
		{
			chain.doFilter(request, response);
		}
		else
		{
			ckImageSaver.saveAndReturnUrlToClient(request, response);
		}
	}

	public void destroy()
	{
	}
}
