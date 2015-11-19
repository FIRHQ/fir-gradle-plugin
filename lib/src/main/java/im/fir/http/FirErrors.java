package im.fir.http;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirErrors {
	public List<String> errors = new ArrayList();

	public static FirErrors createFromJson(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, FirErrors.class);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!this.errors.isEmpty()) {
			for (String error : this.errors) {
				sb.append(error).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
