package bbr.b2b.portal.classes.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils
{
	public static String doPost(List<NameValuePair> properties, String url)
	{
		String result = null;
		try
		{
			HttpClient httpclient = HttpClients.createDefault();

			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(properties, "UTF-8"));

			// Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("LTO- doPost Response:"+response);
			
			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
				result = EntityUtils.toString(entity);
			}

			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
