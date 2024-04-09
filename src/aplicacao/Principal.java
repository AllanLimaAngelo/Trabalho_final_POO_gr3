package aplicacao;

import java.sql.SQLException;
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
    	 
    	 //Cadastro de pedidos
    	do {
    		System.out.print("Informe numero do pedido: ");
    		int idPedido = input.nextInt();
    		System.out.print("Informe ID do cliente : ");
    		String idCliente = input.next();
    		System.out.print("Data de emissão : ");
    		String dtEmissao = input.next();
    		System.out.print("Data de entrega: ");
    		String dtEntrega = input.next();// precisa proteger
    		System.out.print("Observação: ");
    		String obs = input.next();// precisa proteger
    		i++;
    		ArrayList<PedidoItens> ListaPedidoItens = new ArrayList<>(); 		
    		Pedido p = new Pedido(idPedido, idCliente, retornaData(dtEmissao), retornaData(dtEntrega), 0d , obs, ListaPedidoItens);
			System.out.println(p.toString());
    		PedidoDAO dao = new PedidoDAO();
			try {
				dao.incluir(p.toString());
				
				//Cadastro dos itens do pedido
	    		do {
	    		
	    		System.out.print("Digite o código do produto: ");
	    		int produto = input.nextInt();
	    		System.out.print("Digite a quantidade do produto: ");
	    		int qt = input.nextInt();
	    		System.out.print("Digite o valor de desconto: ");
	    		int desconto = input.nextInt();
	    		
	    		
	    		
	    		
	    		PedidoItens pItens =new PedidoItens(idPedido, produto, 0, qt, desconto);
	    		ListaPedidoItens.add(pItens);
	    		PedidoItensDAO pd = new PedidoItensDAO();
	    		pd.incluir(ajusteArraylist(ListaPedidoItens.toString()));
	    		System.out.println("Deseja cadastrar outro Produto? (S/N)");
			    resposta1 = input.next();
	    		}while ("S".equalsIgnoreCase(resposta1));

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			System.out.println("Deseja cadastrar outro pedido? (S/N)");
		    resposta = input.next();
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

