package students.examples.gg.appbuilder.task.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import students.examples.gg.appbuilder.options.Options;

public class RunTask extends AbstractTask {

	Map<String, Object> putInitialOptions(Options options) {
		Map<String, Object> initialOptions = new LinkedHashMap<>();
		initialOptions.put("command", "java");
		initialOptions.putAll(options.getMap());
		return initialOptions;
	}

}
