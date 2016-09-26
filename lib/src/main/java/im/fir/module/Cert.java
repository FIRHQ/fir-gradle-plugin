package im.fir.module;


import org.codehaus.jackson.annotate.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Cert {
	@JsonProperty("icon")
	private Icon mIcon;
	@JsonProperty("binary")
	private Binary mBinary;

	public Icon getIcon() {
		return this.mIcon;
	}

	public void setIcon(Icon mIcon) {
		this.mIcon = mIcon;
	}

	public Binary getBinary() {
		return this.mBinary;
	}

	public void setBinary(Binary mBinary) {
		this.mBinary = mBinary;
	}
}
