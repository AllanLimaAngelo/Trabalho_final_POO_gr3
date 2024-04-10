package aplicacao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import classes.Menu;
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
    
    public static void cadastroPedido() {
    	 String resposta;
    	 String resposta1;
    	
    	 
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
    		
    		 int idCliente = PedidoDAO.selectCliente(opcao);
    		
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
			PedidoDAO.consultarPedido(idPedido);
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

