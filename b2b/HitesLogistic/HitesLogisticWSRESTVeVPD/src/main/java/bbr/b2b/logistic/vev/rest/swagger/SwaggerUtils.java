package bbr.b2b.logistic.vev.rest.swagger;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class SwaggerUtils {

	private static final String HTTP = "http";
	private static final String X_REQUEST_URI = "x-request-uri";
	private static final String X_FORWARDED_PORT = "x-forwarded-port";
	private static final String X_FORWARDED_PROTO = "x-forwarded-proto";
	private static final String LOCALHOST = "localhost";
	private static final int DEFAULT_HTTP_PORT = 80;
	private static final int DEFAULT_HTTPS_PORT = 443;

	public static URL getOriginalRequestURL(HttpServletRequest request) {

		try {
			String path = getOriginalPath(request);
			String scheme = getOriginalRequestScheme(request);
			String host = getOriginalRequestHost(request);
			int port = getOriginalRequestPort(request, scheme);
			URL u = new URL(scheme, host, port, path);
			return u;
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static int getOriginalRequestPort(HttpServletRequest request, String scheme) {
		int original = request.getServerPort();
		if (original != -1 && original > 0 && original != DEFAULT_HTTP_PORT && original != DEFAULT_HTTPS_PORT)
			return original;

		int portFromHeader = request.getIntHeader(X_FORWARDED_PORT);
		if (portFromHeader != -1 && portFromHeader > 0 && portFromHeader != DEFAULT_HTTP_PORT && portFromHeader != DEFAULT_HTTPS_PORT)
			return portFromHeader;

		try {
			int portFromUrl = new URL(request.getRequestURL().toString()).getPort();
			if (portFromUrl != -1 && portFromUrl > 0 && portFromUrl != DEFAULT_HTTP_PORT && portFromUrl != DEFAULT_HTTPS_PORT)
				return portFromUrl;

			return getDefaultPort(scheme);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return getDefaultPort(scheme);
		}
	}
	
	private static int getDefaultPort(String scheme) {
		if (scheme == null || scheme.isEmpty() || scheme.equalsIgnoreCase(HTTP))
			return DEFAULT_HTTP_PORT;
		return DEFAULT_HTTPS_PORT;
	}

	private static String getOriginalRequestHost(HttpServletRequest request) {
		String original = request.getServerName();
		if (original != null && !original.isEmpty())
			return original;
		try {
			return new URL(request.getRequestURL().toString()).getHost();
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return LOCALHOST;
		}
	}

	private static String getOriginalRequestScheme(HttpServletRequest request) {
		String original = request.getHeader(X_FORWARDED_PROTO);
		if (original != null && !original.isEmpty())
			return original;
		try {
			return new URL(request.getRequestURL().toString()).getProtocol();
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return HTTP; // default
		}
	}

	private static String getOriginalPath(HttpServletRequest request) {
		String original = request.getHeader(X_REQUEST_URI);
		if (original != null && !original.isEmpty())
			return original;
		return request.getRequestURI();
	}
}
