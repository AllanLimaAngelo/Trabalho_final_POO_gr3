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
			String user = "", password = "",port;
			boolean success = false;
			System.out.println("Press Enter to continue");
			while (!success) {
				user = Util.askInputLine("Enter your PostgreSQL user: ", inputScanner);
				password = Util.askInputLine("Enter your PostgreSQL password: ", inputScanner);
				port = Util.askInputLine("Enter your PostgreSQL port: ", inputScanner);
				String url = "jdbc:postgresql://localhost:" + port + "/";
				String sql = "";
				try (Connection connection = DriverManager.getConnection(url, user, password);
						Statement statement = connection.createStatement()) {

					// execute a query to test if the db exists
					ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + "grupo2db" + "'");
					if(resultSet.next()) {
						Util.printMessage("found a database and will be using that!");
					} else {
						// Execute SQL command to create the database
						sql = "CREATE DATABASE grupo2db;";
					
						System.out.println("Database created successfully.");
						statement.executeUpdate(sql);
						createTable(url, user, password);
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
	
	public static void createTable(String url, String user,String password) {
		String sql = 
					"""
					CREATE SCHEMA IF NOT EXISTS poo;
					CREATE TABLE IF NOT EXISTS  poo.cliente (
					    idcliente SERIAL PRIMARY KEY,
					    nome VARCHAR(100),
					    cpf VARCHAR(14),
					    dtnascimento DATE,
					    endereco VARCHAR(255),
					    telefone VARCHAR(20)
					);
					
					
					CREATE TABLE IF NOT EXISTS  poo.produto (
					    idproduto SERIAL PRIMARY KEY,
					    descricao VARCHAR(255),
					    vlcusto NUMERIC(10, 2),
					    vlvenda NUMERIC(10, 2),
					    categoria VARCHAR(50)
					);
					
					
					CREATE TABLE IF NOT EXISTS  poo.pedido (
					    idpedido SERIAL PRIMARY KEY,
					    dtemissao DATE,
					    dtentrega DATE,
					    valortotal NUMERIC(10, 2),
					    observacao TEXT
					);
					
					
					CREATE TABLE IF NOT EXISTS  poo.pedidoItens (
					    idpedidoitem SERIAL PRIMARY KEY,
					    idpedido INTEGER REFERENCES  poo.pedido(idpedido),
					    idproduto INTEGER REFERENCES  poo.produto(idproduto),
					    vlunitario NUMERIC(10, 2),
					    qtproduto INTEGER,
					    vldesconto NUMERIC(10, 2)
					);

				""";
     
     try (Connection conn = DriverManager.getConnection((url + "grupo2db"), user, password);
          Statement stmt = conn.createStatement()) {
         
         stmt.executeUpdate(sql);
         System.out.println("Tables created successfully.");
         
     } catch (SQLException e) {
         System.out.println("An error occurred while creating the table: " + e.getMessage());
     }
	}
}