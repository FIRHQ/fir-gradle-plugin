package im.fir.module;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadInfo {
	private static ObjectMapper jsonMapper = new ObjectMapper();

	@JsonProperty("id")
	private String mId;
	@JsonProperty("type")
	private String mType;
	@JsonProperty("short")
	private String mShort;
	@JsonProperty("cert")
	private Cert mCert;

	public static UploadInfo createFormJson(String json) {
		try {
			return jsonMapper.readValue(json, UploadInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getId() {
		return this.mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getType() {
		return this.mType;
	}

	public void setType(String mType) {
		this.mType = mType;
	}

	public String getShort() {
		return this.mShort;
	}

	public void setShort(String mShort) {
		this.mShort = mShort;
	}

	public Cert getCert() {
		return this.mCert;
	}

	public void setCert(Cert mCert) {
		this.mCert = mCert;
	}
}
