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
		if (!FileManager.dbPropertiesExist()) {
			Util.printMessage(
					"could not find 'db.properties' file. This must mean you're running this for the first time!");
			Util.printMessage("must create a 'db.properties' file and a new grupo2db database.");
			Scanner inputScanner = new Scanner(System.in);
			String user = "", password = "", port;
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
					ResultSet resultSet = statement
							.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + "grupo2db" + "'");
					if (resultSet.next()) {
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

	public static void createTable(String url, String user, String password) {
		String sql = """
													CREATE SCHEMA IF NOT EXISTS poo;
													CREATE TABLE IF NOT EXISTS  poo.Cliente (
													    idcliente SERIAL PRIMARY KEY,
													    nome VARCHAR(100),
													    cpf VARCHAR(14),
													    dtnascimento DATE,
													    endereco VARCHAR(255),
													    telefone VARCHAR(20)
													);


													CREATE TABLE IF NOT EXISTS  poo.Produto (
													    idproduto SERIAL PRIMARY KEY,
													    descricao VARCHAR(255),
													    vlcusto NUMERIC(10, 2),
													    vlvenda NUMERIC(10, 2),
													    categoria VARCHAR(50)
													);


													CREATE TABLE IF NOT EXISTS  poo.Pedido (
													    idpedido SERIAL PRIMARY KEY,

													    idcliente INTEGER REFERENCES poo.Cliente(idcliente),

													    dtemissao DATE,
													    dtentrega DATE,
													    valortotal NUMERIC(10, 2),
													    observacao TEXT
													);


													CREATE TABLE IF NOT EXISTS  poo.PedidoItens (
													    idpedidoitem SERIAL PRIMARY KEY,
													    idpedido INTEGER REFERENCES  poo.Pedido(idpedido),
													    idproduto INTEGER REFERENCES  poo.Produto(idproduto),
													    vlunitario NUMERIC(10, 2),
													    qtproduto INTEGER,
													    vldesconto NUMERIC(10, 2)
													);

													INSERT INTO poo.Cliente (nome, cpf, dtnascimento, endereco, telefone) VALUES
														('João Silva', '123.456.789-01', '1975-09-12', 'Rua das Flores, 123', '(11) 1234-5678'),
														('Maria Santos', '987.654.321-02', '1988-03-25', 'Avenida dos Sonhos, 456', '(22) 2345-6789'),
														('Pedro Oliveira', '246.135.798-03', '1995-11-08', 'Travessa das Alegrias, 789', '(33) 3456-7890'),
														('Ana Pereira', '789.321.654-04', '1982-07-14', 'Rua do Sol Nascente, 987', '(44) 4567-8901'),
														('Luiza Costa', '456.789.123-05', '1990-05-30', 'Avenida das Estrelas, 654', '(55) 5678-9012'),
														('Marcos Souza', '159.357.246-06', '1979-12-03', 'Praça da Paz, 321', '(66) 6789-0123'),
														('Carla Mendes', '654.321.987-07', '1987-04-18', 'Rua da Esperança, 987', '(77) 7890-1234'),
														('Rafaela Lima', '852.963.741-08', '1986-08-22', 'Alameda dos Desejos, 456', '(88) 8901-2345'),
														('Gustavo Fernandes', '369.258.147-09', '1993-02-10', 'Avenida da Liberdade, 741', '(99) 9012-3456'),
														('Fernanda Torres', '741.852.963-10', '1984-10-05', 'Rua da Felicidade, 369', '(10) 0123-4567');

													INSERT INTO poo.Produto (descricao, vlcusto, vlvenda, categoria) VALUES
														('Smartphone Samsung Galaxy', 300.00, 500.00, 'Smartphones'),
														('Notebook Dell Inspiron', 600.00, 1000.00, 'Notebooks'),
														('Fone de Ouvido Bluetooth Sony', 50.00, 80.00, 'Acessórios'),
														('TV LED 55" 4K LG', 700.00, 1200.00, 'Televisores'),
														('Console de Videogame PlayStation 5', 400.00, 700.00, 'Consoles de Videogame'),
														('Câmera DSLR Canon EOS', 500.00, 900.00, 'Câmeras Fotográficas'),
														('Tablet Samsung Galaxy Tab', 200.00, 350.00, 'Tablets'),
														('Impressora Multifuncional HP', 100.00, 200.00, 'Impressoras'),
														('Smartwatch Xiaomi Mi Band', 30.00, 60.00, 'Acessórios'),
														('Caixa de Som Bluetooth JBL', 80.00, 150.00, 'Áudio');
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