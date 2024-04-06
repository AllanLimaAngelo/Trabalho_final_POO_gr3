package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import filemanager.FileManager;
import util.Util;

public class DatabaseConfig {

	private static final Properties properties = new Properties();


	public static void initializeDB() {
		if(!FileManager.dbPropertiesExist()) {
			Util.printMessage("could not find 'db.properties' file. This must mean you're running this for the first time!");
			Util.printMessage("must create a 'db.properties' file and a new grupo2db database.");
			Scanner inputScanner = new Scanner(System.in);
			String user = "", password = "";
			boolean success = false;
			System.out.println("Press Enter to continue");
			while (!success) {
				user = Util.askInputLine("Enter your PostgreSQL user: ", inputScanner);
				password = Util.askInputLine("Enter your PostgreSQL password: ", inputScanner);

				String url = "jdbc:postgresql://localhost:5432/";
				String sql = "";
				try (Connection connection = DriverManager.getConnection(url, user, password);
						Statement statement = connection.createStatement()) {

					// execute a query to test if the db exists
					ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + "grupo2db" + "'");
					if(resultSet.next()) {
						Util.printMessage("found a database and will be using that!");
					} else {
						// Execute SQL command to create the database
						sql = "CREATE DATABASE grupo2db";
						statement.executeUpdate(sql);
						System.out.println("Database created successfully.");

					}
					success = true;

				} catch (SQLException e) {
					e.printStackTrace();
					Util.printMessage(" failed to create the database. Please try again.");
				}
			}

			FileManager.createDBProperties(user, password);              
		} 

		loadProperties();

	}

	private static void loadProperties() {
		// Specify the path to the properties file
		String filePath = "src/db.properties";

		try (InputStream input = new FileInputStream(filePath)) {
			// Load the properties file
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDbUrl() {

		return properties.getProperty("db.url");
	}

	public static String getDbUsername() {

		return properties.getProperty("db.username");
	}

	public static String getDbPassword() {

		return properties.getProperty("db.password");
	}
}