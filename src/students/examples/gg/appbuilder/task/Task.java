package students.examples.gg.appbuilder.task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import students.examples.gg.appbuilder.options.Options;
import students.examples.gg.appbuilder.result.Result;

public interface Task {
	Result execute (Options options) throws InterruptedException, ExecutionException, IOException;

}
