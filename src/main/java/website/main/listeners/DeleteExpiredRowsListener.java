package website.main.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import website.main.util.DatabaseCleanUp;

public class DeleteExpiredRowsListener implements ServletContextListener {

	private DatabaseCleanUp databaseCleanUp;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		databaseCleanUp = new DatabaseCleanUp();
		databaseCleanUp.execute();

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Cleanup code if needed
	}

}
