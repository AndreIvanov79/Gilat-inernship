package website.main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import website.main.services.DatabaseManager;

public class DBHandler {

	private static Connection connection = DatabaseManager.getConnection();

	public static void insertUser(String fullName, String email, String phone, String password, String nickname){
		String sql = "INSERT INTO clients (full_name, email, phone, password, nickname) VALUES (?, ?, ?, ?, ?)";
		// long generatedId;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, fullName);
			stmt.setString(2, email);
			stmt.setString(3, phone);
			stmt.setString(4, password);
			stmt.setString(5, nickname);

			stmt.executeUpdate();
		

//            ResultSet resultSet = stmt.getGeneratedKeys();
//            if (resultSet.next()) {
//                      generatedId = resultSet.getLong(1);
//                  } else {
//                      throw new RuntimeException("Creating user failed, no ID obtained.");
//                  }
//            return generatedId;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String getTokenByEmail(String email) {
		String token = null;
		String sql = "select token from clients where email=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			try (ResultSet resultSet = stmt.executeQuery()) {
				if (resultSet.next()) {
					token = resultSet.getString("token");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}

	public static String activateClient(String email) {
		String message = "No such client in DB.";
		String sql = "update clients set active = true where clients.email=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			int updatedRows = stmt.executeUpdate();
			if (updatedRows != 0) {
				message = "Client was activated.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public static boolean updateClient(String token, String fullName, String email, String phone, String password,
			String nickname) {
		boolean success = false;
		String sql = "update clients set full_name=?, email=?, phone=?, password=?, nickname=? where token=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, fullName);
			stmt.setString(2, email);
			stmt.setString(3, phone);
			stmt.setString(4, password);
			stmt.setString(5, nickname);
			stmt.setString(6, token);
			int updatedRows = stmt.executeUpdate();
			if (updatedRows != 0) {
				success = true;
				;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;

	}

	public static boolean checkLastInsertTime() {

		boolean result = false;
		String sql = "SELECT createdAt FROM clients ORDER BY createdAt DESC LIMIT 1";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				// Retrieve the createdAt timestamp of the last inserted record
				Timestamp lastInsertTime = resultSet.getTimestamp("createdAt");

				// Calculate the time difference
				Instant currentTime = Instant.now();
				Instant lastInsertInstant = lastInsertTime.toInstant();
				long secondsDifference = currentTime.getEpochSecond() - lastInsertInstant.getEpochSecond();

				// Check if the difference is more than 30 seconds
				result = secondsDifference > 10;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}

		// Return false in case of any exception or no matching record
		return result;
	}
	
	public static String getPasswordByEmail(String email) {
		String password = null;
		String sql = "select password from clients where email=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			try (ResultSet resultSet = stmt.executeQuery()) {
				if (resultSet.next()) {
					password = resultSet.getString("password");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return password;
	}
	

}
