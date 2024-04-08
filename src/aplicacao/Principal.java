package aplicacao;

import java.sql.SQLException;
import java.util.Scanner;

import classes.ProdutoDAO;
import classes.Produto;
import database.DB;

public class Principal {
	static Scanner input = new Scanner (System.in);
    public static void main(String[] args) {

        boolean hasConnectedToDb = connectToDatabase();
            if (hasConnectedToDb) {
                System.out.println("Conectado com sucesso!");
            }
            
            cadastroProduto();
    }

    public static boolean connectToDatabase() {

        try (var connection = DB.connect()) {
        	
            System.out.println("Connected to PostgreSQL Database!");
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return false;
        
        
        
    }
    
    public static void cadastroProduto() {
    	 String resposta;
    	do {
    		System.out.println("Nome do Produto: ");
    		String desc = input.nextLine();
    		System.out.println("Categoria : ");
    		String categoria = input.nextLine();
    		System.out.println("Valor de custo: ");
    		String vlCusto = input.nextLine();// precisa proteger
    		System.out.println("Valor de venda: ");
    		String vlVenda = input.nextLine();// precisa proteger
    		
		
			
    		
    		Produto p = new Produto( desc, Double.parseDouble(vlCusto),Double.parseDouble(vlVenda) , categoria );
    		ProdutoDAO dao = new ProdutoDAO();
			try {
				dao.incluir(p);
				System.out.println("Cadastro realizado com sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			System.out.print("Deseja cadastrar outro cliente? (S/N)");
		    resposta = input.nextLine();
    	}while ("S".equalsIgnoreCase(resposta));
    }
    
    
}

