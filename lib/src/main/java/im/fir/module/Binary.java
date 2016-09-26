package im.fir.module;


import org.codehaus.jackson.annotate.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Binary {
	@JsonProperty("key")
	private String mKey;
	@JsonProperty("token")
	private String mToken;
	@JsonProperty("upload_url")
	private String mUpLoadUrl;

	public String getKey() {
		return this.mKey;
	}

	public void setKey(String mKey) {
		this.mKey = mKey;
	}

	public String getToken() {
		return this.mToken;
	}

	public void setToken(String mToken) {
		this.mToken = mToken;
	}

	public String getUpLoadUrl() {
		return this.mUpLoadUrl;
	}

	public void setUpLoadUrl(String mUpLoadUrl) {
		this.mUpLoadUrl = mUpLoadUrl;
	}
}
