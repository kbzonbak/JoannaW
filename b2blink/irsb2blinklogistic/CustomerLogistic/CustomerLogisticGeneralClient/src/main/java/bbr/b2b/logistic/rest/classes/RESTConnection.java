package bbr.b2b.logistic.rest.classes;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
//import org.jboss.resteasy.util.HttpResponseCodes;

import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.logistic.rest.dto.GenericResponseResultDTO;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;


@SuppressWarnings("unchecked")
public class RESTConnection {

	private static RESTConnection _myInstance = null;
	private CloseableHttpClient client = null;
	
	private HttpGet httpGet = null;
	private HttpPost httpPost = null;
	private HttpPut httpPut = null;
	private HttpPatch httpPatch = null;
	private HttpDeleteEntity httpDelete = null;
	private RequestConfig config;
	
	private RESTConnection(){
		
		try{
			boolean isProxy =  Boolean.valueOf(System.getProperty("PROXY"));
			if(isProxy){
				String ProxyHost = System.getProperty("PROXY_HOST");
				Integer ProxyPort = Integer.valueOf(System.getProperty("PROXY_PORT"));
				HttpHost proxy = new HttpHost(ProxyHost, ProxyPort);
			    DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
				
				client = HttpClients.custom()
		                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE))
		                .setRoutePlanner(routePlanner)
		                .build();	
				
				config =RequestConfig.custom().setProxy(proxy).build();
			} else {
				client = HttpClients.custom()
		                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE))
		                .build();		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RESTConnection getInstance(){
		if (_myInstance == null)
			_myInstance = new RESTConnection();
		return _myInstance;
	}
	
	private HttpGet getHttpGetEndpoint(URI uri, String headerToken, String accessToken){
		httpGet = new HttpGet(uri);		
		
//		if (!accessToken.isEmpty())
//			httpGet.addHeader(headerToken, accessToken);   
		
		return httpGet;
	}
	
//	private HttpPost getHttpPostEndpoint(URI uri, String accessToken, boolean isBasic){
//		httpPost = new HttpPost(uri);
//		
//		if (!accessToken.isEmpty())
//			httpPost.addHeader("Authorization", isBasic ? "Basic " : "Bearer " + accessToken);   
//		
//		return httpPost;
//	}
	
	public HttpPost getHttpPostEndpoint(String url, String accessToken, boolean isBasic) throws URISyntaxException{
		URI uri = new URIBuilder(url).build();
		httpPost = new HttpPost(uri);
		httpPost.setConfig(config);
		if (!accessToken.isEmpty())
			httpPost.addHeader("Authorization", (isBasic ? "Basic " : "Bearer " ) + accessToken);   
		
		return httpPost;
	}

	public HttpPut getHttpPutEndpoint(URI uri, String accessToken){
		httpPut = new HttpPut(uri);
		
		if (!accessToken.isEmpty())
			httpPut.addHeader("Authorization", "Bearer " + accessToken);   
		
		return httpPut;
	}
	
	public HttpPatch getHttpPatchEndpoint(String url, String accessToken, boolean isBasic) throws URISyntaxException{
		URI uri = new URIBuilder(url).build();
		httpPatch = new HttpPatch(uri);
		
		if (!accessToken.isEmpty())
			httpPost.addHeader("Authorization", (isBasic ? "Basic " : "Bearer " ) + accessToken);   
		
		return httpPatch;
	}

	public HttpDeleteEntity getHttpDeleteEndpoint(URI uri, String accessToken){
		httpDelete = new HttpDeleteEntity(uri);
		
		if (!accessToken.isEmpty())
			httpDelete.addHeader("Authorization", "Bearer " + accessToken);   
		
		return httpDelete;
	}
	
	public HttpGet getHttpGetEndpoint(String url) throws URISyntaxException{
		return getHttpGetEndpoint(url, null, null, null);
	}
	
	public HttpGet getHttpGetEndpoint(String url, Map<String, Object> params, String headerToken, String accessToken) throws URISyntaxException{
		URI uri = null;
		
		if (params != null){
			List<NameValuePair> data = getDataFromParams(params);		
			uri = new URIBuilder(url).addParameters(data).build();		
		}
		else{
			uri = new URIBuilder(url).build();	
		}		
		
		httpGet = getHttpGetEndpoint(uri, headerToken, accessToken);
		return httpGet;
	}
	
	public HttpPost getHttpPostEndpoint(String url, Object params, String headerToken, String accessToken, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		HttpEntity entity = null;		
		
		httpPost = new HttpPost(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);	
			
			httpPost.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpPost.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}	
			
		if (!accessToken.isEmpty()){
			httpPost.addHeader(headerToken, accessToken);			
		}		   
		
		return httpPost;
	}
	
	
	public HttpPost getHttpPostEndpoint(String url, Object params,RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		HttpEntity entity = null;		
		
		httpPost = new HttpPost(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);	
			
			httpPost.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpPost.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}	
		
		return httpPost;
	}
	
	public HttpPatch getHttpPatchEndpoint(String url, Object params, String accessToken, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		HttpEntity entity = null;		
		
		httpPatch = new HttpPatch(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);	
			
			httpPatch.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpPatch.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpPatch.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}	
			
		if (!accessToken.isEmpty()){
			httpPatch.addHeader("Authorization", "Bearer " + accessToken);			
		}		   
		
		return httpPatch;
	}

	public HttpPut getHttpPutEndpoint(String url, Object params, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		return getHttpPutEndpoint(url, params, "", contenType);
	}
	
	public HttpPut getHttpPutEndpoint(String url, Object params, String accessToken, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		HttpEntity entity = null;		
		
		httpPut = new HttpPut(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);	
			
			httpPut.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpPut.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpPut.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}	
			
		if (!accessToken.isEmpty()){
			httpPut.addHeader("Authorization", "Bearer " + accessToken);			
		}		   
		
		return httpPut;
	}
	
	public HttpPut getHttpPutEndpoint(String url, Map<String, Object> params, String accessToken, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		
		HttpEntity entity = null;
		
		httpPut = new HttpPut(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);
			
			httpPut.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpPut.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpPut.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}					
			
		if (!accessToken.isEmpty()){
			httpPut.addHeader("Authorization", "Bearer " + accessToken);			
		}		   
		
		return httpPut;	
	}

	public HttpDeleteEntity getHttpDeleteEndpoint(String url, Map<String, Object> params, String accessToken, RESTContentType contenType) throws URISyntaxException, UnsupportedEncodingException{
		HttpEntity entity = null;
		
		httpDelete = new HttpDeleteEntity(url);
		
		switch (contenType) {
		case FORM:
			entity = getFormEntityFromParams(params);	
			
			httpDelete.setEntity(entity);		
			break;
		case JSON:
			entity = getJsonEntityFromParams(params);				
			httpDelete.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			httpDelete.setEntity(entity);		
			break;
		case NONE:
			
			break;
		}	
			
		if (!accessToken.isEmpty()){
			httpDelete.addHeader("Authorization", "Bearer " + accessToken);			
		}		   
		
		return httpDelete;			
	}
	
	
	public GenericResponseResultDTO executeRequest(HttpUriRequest request) {
		GenericResponseResultDTO result = new GenericResponseResultDTO();	
		CloseableHttpResponse response = null;
		String responseStr = "";	
		try {
			response = client.execute(request);
			
			switch (response.getStatusLine().getStatusCode()) {
			
			case HttpStatus.SC_OK:
				responseStr = convert(response.getEntity().getContent(), Charset.forName("UTF-8"));		
				result.setJsonResponse(responseStr);				
				break;

			case HttpStatus.SC_CREATED:				
				
				break;
			case HttpStatus.SC_UNAUTHORIZED:
				result = LogisticStatusCodeUtils.getInstance().setStatusCode(result, "401");
				break;

			case HttpStatus.SC_FORBIDDEN:
				responseStr = convert(response.getEntity().getContent(), Charset.forName("UTF-8"));
				result = LogisticStatusCodeUtils.getInstance().setStatusCode(result, "403");
				result.setJsonResponse(responseStr);
				break;
			case HttpStatus.SC_NOT_FOUND:
				result = LogisticStatusCodeUtils.getInstance().setStatusCode(result, "404");
				break;
			case HttpStatus.SC_CONFLICT:
				result = LogisticStatusCodeUtils.getInstance().setStatusCode(result, "409");
				break;	
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				responseStr = convert(response.getEntity().getContent(), Charset.forName("UTF-8"));
				result = LogisticStatusCodeUtils.getInstance().setStatusCode(result, "500");
				result.setJsonResponse(responseStr);
				break;	
			
			default:
				break;
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(result, "500");
		} finally {
			try{
				if (response != null)
					response.close();
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}			
		return result;		
	}	
	
	//********************************************************************************************************/
	private HttpEntity getJsonEntityFromParams(Object params){
		
		HttpEntity entity = null;
		
		if (params instanceof Set<?>){
			
			Set<Map<String, Object>> convParams = (Set<Map<String, Object>>)params;
			List<Properties> propList = new ArrayList<Properties>();			
			
			for (Map<String, Object> map : convParams){
							
				Properties properties = new Properties();
				
				for (String key : map.keySet()){
					properties.put(key, map.get(key));
				}
				
				propList.add(properties);				
			}
			
			entity = new StringEntity(JsonUtils.createJsonArray(propList.toArray(new Properties[propList.size()])).toString(), Charset.forName("UTF-8"));			
		}
		else if (params instanceof Map<?, ?>){
			
			Map<String, Object> convParams = (Map<String, Object>)params; 
			
			Properties properties = new Properties();
			
			for (String key : convParams.keySet()){
				properties.put(key, convParams.get(key));
			}
		
			entity = new StringEntity(JsonUtils.createJsonObject(properties).toString(), Charset.forName("UTF-8"));			
		} 
		else if (params instanceof String){
			entity = new StringEntity((String)params, Charset.forName("UTF-8"));			
		}	
		
		return entity;
	}
	
	private HttpEntity getFormEntityFromParams(Object params) throws UnsupportedEncodingException{
		
		Map<String, Object> convParams = (Map<String, Object>)params;
		
		List<NameValuePair> data = new ArrayList<NameValuePair>();		
		
		for (String key : convParams.keySet()){
			data.add(new BasicNameValuePair(key, (String)convParams.get(key)));
		}		
	
		HttpEntity entity = new UrlEncodedFormEntity(data);		
		
		return entity;
	}
	
	private List<NameValuePair> getDataFromParams(Map<String, Object> params){
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		
		for (String key : params.keySet()){
			data.add(new BasicNameValuePair(key, (String)params.get(key)));
		}		
	
		return data;
	}	
	
	private String convert(InputStream inputStream, Charset charset) throws IOException {
		 
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
	 
		return stringBuilder.toString();
	}
	
}