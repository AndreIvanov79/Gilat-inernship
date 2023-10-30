package students.examples.gg.task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import students.examples.gg.options.Options;
import students.examples.gg.result.Result;

public interface Task {
	Result execute (Options options) throws InterruptedException, ExecutionException, IOException;

}
