package website.main.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import website.main.services.DatabaseManager;

public class DatabaseCleanUp {
	private ScheduledExecutorService scheduler;

	public void execute() {
		scheduler = Executors.newSingleThreadScheduledExecutor();

		// Schedule the cleanup job to run every 30 seconds
		scheduler.scheduleAtFixedRate(() -> {
			Connection connection = DatabaseManager.getConnection();
			// Delete records older than 30 seconds
			String deleteSql = "DELETE FROM clients WHERE token IS NOT NULL AND (email IS NULL OR email = '') AND createdAt < ?";
			Timestamp thresholdTime = Timestamp.from(Instant.now().minusSeconds(120));
			System.out.println("Current time: " + thresholdTime);
			try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
				deleteStatement.setTimestamp(1, thresholdTime);
				int rowsAffected = deleteStatement.executeUpdate();
				System.out.println(rowsAffected + " rows deleted.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, 0, 120, TimeUnit.SECONDS);
	}

}
