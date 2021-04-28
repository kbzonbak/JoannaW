package bbr.esb.storage.data.classes;

import java.net.URL;

public class S3FileDataDTO {

	private String key;

	private URL url;

	private URL signedUrl;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getSignedUrl() {
		return signedUrl;
	}

	public void setSignedUrl(URL signedUrl) {
		this.signedUrl = signedUrl;
	}

}
