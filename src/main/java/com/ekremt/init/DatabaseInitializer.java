package com.ekremt.init;



import com.ekremt.utility.DatabaseConfig;

import java.sql.*;

public class DatabaseInitializer {
	private static final String CREATE_DATABASE = "CREATE DATABASE " + DatabaseConfig.getDatabaseName();
	
	public static void initialize() {
		createDatabaseIfNotExists();
	}
	
	private static void createDatabaseIfNotExists() {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres",  // postgres veritabanına bağlan
				DatabaseConfig.getUsername(),
				DatabaseConfig.getPassword())) {
			
			boolean dbExists = false;
			try (ResultSet rs = conn.getMetaData().getCatalogs()) {
				while (rs.next()) {
					String dbName = rs.getString(1);
					if (DatabaseConfig.getDatabaseName().equalsIgnoreCase(dbName)) {
						dbExists = true;
						break;
					}
				}
			}
			
			if (!dbExists) {
				try (Statement statement = conn.createStatement()) {
					// Önce var olan bağlantıları kapat
					String terminateConnections =
							"SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE " +
									"datname = '" + DatabaseConfig.getDatabaseName() + "' AND pid <> pg_backend_pid()";
					statement.execute(terminateConnections);
					
					// Veritabanını oluştur
					statement.executeUpdate(CREATE_DATABASE);
					System.out.println("Veritabanı oluşturuldu: " + DatabaseConfig.getDatabaseName());
				}
			} else {
				System.out.println("Veritabanı zaten mevcut: " + DatabaseConfig.getDatabaseName());
			}
			
		} catch (SQLException e) {
			System.err.println("Veritabanı oluşturulurken hata: " + e.getMessage());
		}
	}
}