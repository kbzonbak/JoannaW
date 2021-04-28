package bbr.b2b.portal.classes.utils.app;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.communication.ConnectionStateHandler;
import com.vaadin.client.communication.PushConnection;
import com.vaadin.client.communication.XhrConnectionError;

import elemental.json.JsonObject;

public class BbrConnectionStateHandler implements ConnectionStateHandler
{

	@Override
	public void setConnection(ApplicationConnection connection)
	{
		
		
	}

	@Override
	public void heartbeatException(Request request, Throwable exception)
	{
		
		
	}

	@Override
	public void heartbeatInvalidStatusCode(Request request, Response response)
	{
		
		
	}

	@Override
	public void heartbeatOk()
	{
		
		
	}

	@Override
	public void pushClosed(PushConnection pushConnection, JavaScriptObject responseObject)
	{
		
		
	}

	@Override
	public void pushClientTimeout(PushConnection pushConnection, JavaScriptObject response)
	{
		
		
	}

	@Override
	public void pushError(PushConnection pushConnection, JavaScriptObject response)
	{
		
		
	}

	@Override
	public void pushReconnectPending(PushConnection pushConnection)
	{
		
		
	}

	@Override
	public void pushOk(PushConnection pushConnection)
	{
		
		
	}

	@Override
	public void pushScriptLoadError(String resourceUrl)
	{
		
		
	}

	@Override
	public void xhrException(XhrConnectionError xhrConnectionError)
	{
		
		
	}

	@Override
	public void xhrInvalidContent(XhrConnectionError xhrConnectionError)
	{
		
		
	}

	@Override
	public void xhrInvalidStatusCode(XhrConnectionError xhrConnectionError)
	{
		
		
	}

	@Override
	public void xhrOk()
	{
		
		
	}

	@Override
	public void pushNotConnected(JsonObject payload)
	{
		
		
	}

	@Override
	public void pushInvalidContent(PushConnection pushConnection, String message)
	{
		
		
	}

	@Override
	public void configurationUpdated()
	{
		
		
	}

}
