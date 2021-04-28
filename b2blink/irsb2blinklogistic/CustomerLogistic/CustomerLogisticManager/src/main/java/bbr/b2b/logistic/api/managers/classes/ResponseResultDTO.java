package bbr.b2b.logistic.api.managers.classes;

import java.io.InputStream;

import javax.json.JsonReader;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ResponseResultDTO extends BaseResultDTO{
	
	private JsonReader responseReader;
	private InputStream responseStream;

	public InputStream getResponseStream() {
		return responseStream;
	}

	public void setResponseStream(InputStream responseStream) {
		this.responseStream = responseStream;
	}

	public JsonReader getResponseReader() {
		return responseReader;
	}

	public void setResponseReader(JsonReader responseReader) {
		this.responseReader = responseReader;
	}	
}
