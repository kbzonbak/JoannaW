package bbr.b2b.logistic.api.managers.classes;

import java.net.URI;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

@NotThreadSafe
public class HttpDeleteEntity extends HttpEntityEnclosingRequestBase {

	public final static String METHOD_NAME = "DELETE";

	public HttpDeleteEntity() {
		super();
	}

	public HttpDeleteEntity(final URI uri) {
		super();
		setURI(uri);
	}
	
	public HttpDeleteEntity(final String uri) {
		super();
	    setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
		return METHOD_NAME;
	}
}
