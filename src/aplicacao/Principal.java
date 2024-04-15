package aplicacao;

import java.sql.SQLException;
import java.util.Scanner;

import classes.Menu;
import database.DB;

public class Principal {
	static int i = 1;
	static Scanner input = new Scanner (System.in);
    public static void main(String[] args) {

        boolean hasConnectedToDb = connectToDatabase();
            if (hasConnectedToDb) {
                System.out.println("Conectado com sucesso!");
            }
            
          
			
            Menu menu = new Menu();
            menu.exibirMenu();
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

