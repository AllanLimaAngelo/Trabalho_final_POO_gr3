package aplicacao;

import java.sql.SQLException;

import database.DB;

public class Principal {

    public static void main(String[] args) {

        boolean hasConnectedToDb = connectToDatabase();
            if (hasConnectedToDb) {
                System.out.println("Conectado com sucesso!");
            }
    }

    public static boolean connectToDatabase() {

        try (var connection = DB.connect()) {
        	
            System.out.println("Connected to PostgreSQL Database!");
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return false;
    }
}

