package aplicacao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import classes.Pedido;
import classes.PedidoDAO;
import classes.PedidoItens;
import classes.PedidoItensDAO;
import database.DB;

public class Principal {
	static int i = 1;
	static Scanner input = new Scanner (System.in);
    public static void main(String[] args) {

        boolean hasConnectedToDb = connectToDatabase();
            if (hasConnectedToDb) {
                System.out.println("Conectado com sucesso!");
            }
            
            cadastroPedido();
    }

    public static boolean connectToDatabase() {

        try (var connection = DB.connect()) {
        	
            System.out.println("Connected to PostgreSQL Database!");
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return false;
        
        
        
    }
    
    public static void cadastroPedido() {
    	 String resposta;
    	 String resposta1;
    	 int idCliente = 0;
    	 
    	 //Cadastro de pedidos
    	do {
    		System.out.print("Informe numero do pedido: ");
    		int idPedido = stringParaInt(input.nextLine());
    		System.out.println("""
    				Deseja cadastrar o cliente por:
    				1 - Código
    				2 - CPF
    				3 - Nome
    				""");
    		int opcao = stringParaInt(input.nextLine());
    		switch (opcao) {
			case 1:
				System.out.print("Informe ID do cliente : ");
	    		idCliente = stringParaInt(input.nextLine());
				break;
			case 2:
				System.out.print("Informe CPF do cliente : ");
	    		String CPF = input.nextLine();
	    		
	    		String query = "SELECT idcliente FROM poo.Cliente Where cpf = " + " '"+CPF+"' ";

	            try (
	                Connection connection = DB.connect();
	                Statement statement = connection.createStatement();
	                ResultSet resultSet = statement.executeQuery(query)
	            ) {
	                while (resultSet.next()) {
	                    idCliente = resultSet.getInt("idcliente");
	                    System.out.println(resultSet.getInt("idcliente"));
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
				break;
			case 3:
				System.out.print("Informe nome do cliente : ");
	    		String nome = input.nextLine();
	    		
	    		String query1 = "SELECT idcliente FROM poo.Cliente Where nome = " + " '"+nome+"' ";

	            try (
	                Connection connection = DB.connect();
	                Statement statement = connection.createStatement();
	                ResultSet resultSet = statement.executeQuery(query1)
	            ) {
	                // Iterando sobre os resultados da consulta
	                while (resultSet.next()) {
	                    // Extraindo os dados de cada linha do resultado
	                     idCliente = resultSet.getInt("idcliente");
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
				break;
			default:
				break;
			}
    		
    		
    		
    		
    		System.out.print("Data de emissão : ");
    		String dtEmissao = input.nextLine();
    		System.out.print("Data de entrega: ");
    		String dtEntrega = input.nextLine();// precisa proteger
    		System.out.print("Observação: ");
    		String obs = input.nextLine();// precisa proteger
    		i++;
    		ArrayList<PedidoItens> ListaPedidoItens = new ArrayList<>(); 		
    		Pedido p = new Pedido(idPedido, idCliente, retornaData(dtEmissao), retornaData(dtEntrega), 0d , obs, ListaPedidoItens);
			System.out.println(p.toString());
    		PedidoDAO dao = new PedidoDAO();
			try {
				dao.incluir(p.toString());
				int j= 0;
				//Cadastro dos itens do pedido
	    		do {
	    		
	    		System.out.print("Digite o código do produto: ");
	    		int produto = stringParaInt(input.nextLine());
	    		System.out.print("Digite a quantidade do produto: ");
	    		int qt = stringParaInt(input.nextLine());
	    		System.out.print("Digite o valor de desconto: ");
	    		int desconto =stringParaInt(input.nextLine());
	    		
	    		
	    		
	    		
	    		PedidoItens pItens =new PedidoItens(idPedido, produto, 0, qt, desconto);
	    		ListaPedidoItens.add(pItens);
	    		PedidoItensDAO pd = new PedidoItensDAO();
	    		pd.incluir(ListaPedidoItens.get(j).toString());
	    		System.out.println("Deseja cadastrar outro Produto? (S/N)");
	    		j++;
			    resposta1 = input.nextLine();
	    		}while ("S".equalsIgnoreCase(resposta1));

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			System.out.println("Deseja cadastrar outro pedido? (S/N)");
		    resposta = input.nextLine();
    	}while ("S".equalsIgnoreCase(resposta));
    }
    
    public static LocalDate retornaData(String dt) {
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     LocalDate data = LocalDate.parse(dt, formatter);
		return data;
		
	}
 
    //Retira os colchetes do toString de um arraylist
    public static String ajusteArraylist(String texto) {
    	String resultado = texto.substring(1, texto.length() - 1);
    	return resultado;
    	
    }

    public static int stringParaInt(String numeroComoString) {
    	try {
    	    int numero = Integer.parseInt(numeroComoString);
    	    return numero;
    	} catch (NumberFormatException e) {
    	    System.err.println("A string não representa um número inteiro válido.");
    	}
		return i;
    }
}

