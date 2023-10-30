package students.examples.gg.appbuilder.options;

import java.util.LinkedHashMap;
import java.util.Map;

public class Options {
	
	private Map<String, Object> options = new LinkedHashMap<>();
	
	public Options set(String key, Object value) {
		options.put(key, value);
		return this;
	}
	
	public Object get(String key) {
		return options.get(key);
	}
	
	public Map<String, Object> getMap() {
		return options;
	}

}
