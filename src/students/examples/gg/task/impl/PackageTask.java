package students.examples.gg.task.impl;


import java.util.LinkedHashMap;
import java.util.Map;
import students.examples.gg.options.Options;

public class PackageTask extends AbstractTask {

	Map<String, Object> putInitialOptions(Options options) {
		Map<String, Object> initialOptions = new LinkedHashMap<>();
		initialOptions.put("command", "jar");
		initialOptions.putAll(options.getMap());
		return initialOptions;
	}

}
