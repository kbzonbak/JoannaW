package bbr.b2b.portal.classes.utils.exagon.client;

import java.io.IOException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class RestCallUtils
{

	public enum Action
	{
		GET, POST, PUT, PATCH, DELETE
	};

	private static final String	BASE_URL	= "https://exobi.test.b2b/ExagoWebApi/";

	private CloseableHttpClient	httpclient	= null;

	public RestCallUtils()
	{
		try
		{
			setupHTTPClient();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setupHTTPClient() throws Exception
	{
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// this.httpclient = httpclient;
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, new NoopHostnameVerifier());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		this.httpclient = httpclient;
	}

	public String makeRESTCall(String path, Action action, String body) throws IOException
	{
		String url = BASE_URL + path;

		// method
		HttpRequestBase method = null;
		switch (action)
		{
			case GET:
				method = new HttpGet(url);
				break;
			case POST:
				method = new HttpPost(url);
				break;
			case PUT:
				method = new HttpPut(url);
				break;
			case PATCH:
				method = new HttpPatch(url);
				break;
			case DELETE:
				method = new HttpDelete(url);
				break;
		}
		;

		method.addHeader("Accept", "application/json");
		method.addHeader("Content-Type", "application/json");
		method.addHeader("Authorization", "Basic Og==");

		if (method instanceof HttpEntityEnclosingRequestBase)
		{
			StringEntity entity = new StringEntity(body != null ? body : "", "UTF-8");
			((HttpEntityEnclosingRequestBase) method).setEntity(entity);
		}

		try (CloseableHttpResponse response = this.httpclient.execute(method))
		{

			StatusLine status = response.getStatusLine();
			System.out.println(status);
			// if (status != null && status.getStatusCode() >= 200 && status.getStatusCode() < 3000000) {
			HttpEntity responseEntity = response.getEntity();
			// do something useful with the response body and ensure it is fully consumed
			if (responseEntity != null)
			{
				String result = EntityUtils.toString(responseEntity);
				EntityUtils.consume(responseEntity);
				return result;
			}
			else
				return null;
			// } else
			// return null;
		}
	}

	public String createSession() throws IOException
	{
		String path = "rest/sessions";
		return makeRESTCall(path, Action.POST, null);
	}

	public String setParameter(String sessionid, String paramname, String body) throws IOException
	{
		String path = "rest/parameters/" + paramname + "?sid=" + sessionid;
		return makeRESTCall(path, Action.PATCH, body);
	}

	public String setRole(String sessionid, String rolename, String body) throws IOException
	{
		String path = "rest/roles/" + rolename + "?sid=" + sessionid;
		return makeRESTCall(path, Action.PATCH, body);
	}

	public String executeReport(String sessionid, String body) throws IOException
	{
		String path = "rest/sessions/" + sessionid;
		return makeRESTCall(path, Action.PATCH, body);
	}

	public String getReports(String sessionid) throws IOException
	{
		String path = "/rest/Reports/List?sid=" + sessionid;
		return makeRESTCall(path, Action.GET, null);
	}

}
