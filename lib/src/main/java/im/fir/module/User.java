package im.fir.module;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String id;
	private String uuid;
	private String name;
	private String email;
	private String gravatar;
	private boolean is_confirmed;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGravatar() {
		return this.gravatar;
	}

	public void setGravatar(String gravatar) {
		this.gravatar = gravatar;
	}

	public boolean isIs_confirmed() {
		return this.is_confirmed;
	}

	public void setIs_confirmed(boolean is_confirmed) {
		this.is_confirmed = is_confirmed;
	}

	private static ObjectMapper jsonMapper = new ObjectMapper();

	public static User createFormJson(String json) {
		try {
			return jsonMapper.readValue(json, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
