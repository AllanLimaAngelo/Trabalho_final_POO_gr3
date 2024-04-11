package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aplicacao.Principal;
import database.DB;

public class PedidoDAO implements CRUD {
	
	private Connection connection;

	

	public PedidoDAO(Connection connection) {
		this.connection = connection;
	}

	static Scanner input = new Scanner (System.in);
	
	public void incluir(Object pedido) throws SQLException {
		
		
		Connection conn = DB.connect();
		// Cria uma declaração
        Statement statement = conn.createStatement();
        // Executa uma consulta SQL
        String insertSql = "INSERT INTO poo.Pedido " + pedido.toString();
        statement.executeUpdate(insertSql);
		 
	}
	
	public static int selectCliente(int opcao) {
        int idCliente=0;

        switch (opcao) {
            case 1:
                System.out.print("Informe ID do cliente : ");
                idCliente = Principal.stringParaInt(input.nextLine());
                break;
            case 2:
                System.out.print("Informe CPF do cliente : ");
                String cpf = input.nextLine();
                idCliente = consultarClientePorCPF(cpf);
                break;
            case 3:
                System.out.print("Informe nome do cliente : ");
                String nome = input.nextLine();
                idCliente = consultarClientePorNome(nome);
                break;
            default:
                break;
        }
        return idCliente;
    }

    private static int consultarClientePorCPF(String cpf) {
        String query = "SELECT idcliente FROM poo.Cliente WHERE cpf = ?";
        return consultarCliente(query, cpf);
    }

    private static int consultarClientePorNome(String nome) {
        String query = "SELECT idcliente FROM poo.Cliente WHERE nome = ?";
        return consultarCliente(query, nome);
    }

    private static int consultarCliente(String query, String parametro) {
        int idCliente = 0;
        
        try (Connection connection = DB.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, parametro);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idCliente = resultSet.getInt("idcliente");
                    System.out.println("ID do cliente: " + idCliente);
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratamento mais adequado seria ideal
        }
        return idCliente;
    }

    // Método para converter String em Inteiro
    public static int stringParaInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0; // Ou lance uma exceção, dependendo do comportamento desejado
        }
    }
	

	@Override
	public void alterar() {
	
		
	}

	public void consultarPedido(int idPedido) {
	    double total = 0;
	    String idPe = "";
	    String query = """
	            Select cl.idcliente, cl.nome, pe.idpedido, pe.dtemissao, 
	                pr.descricao, pr.vlvenda, pi.qtproduto, pi.vldesconto
	            from     
	                poo.cliente cl
	            join     
	                poo.pedido pe on cl.idcliente = pe.idcliente
	            join 
	                poo.pedidoitens pi on pe.idpedido = pi.idpedido
	            join 
	                poo.produto pr on pi.idproduto = pr.idproduto
	            where pe.idpedido = ?;
	            """;

	    try (Connection connection = DB.connect();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, idPedido); // Convertendo o parâmetro para inteiro
	        ResultSet resultSet = statement.executeQuery(); // Executando o PreparedStatement

	        // Imprimir o nome e o ID do cliente apenas uma vez
	        if (resultSet.next()) {
	            String idCliente = resultSet.getString("idcliente");
	            String nomeCliente = resultSet.getString("nome");
	            idPe = resultSet.getString("idpedido");
	            String peDtEm = resultSet.getString("dtemissao");
	            System.out.println("-----------------------------------------------------------------------\n");
	            System.out.println("Numero do pedido: " + idPe +"\nCód cliente: " + idCliente + "\t\tNome: " + nomeCliente + "\nData emissão: " + peDtEm );
	            System.out.println("-----------------------------------------------------------------------");
	            // Iterar sobre os resultados restantes
	            do {

	                double piDescont = resultSet.getDouble("vldesconto");
	                String prDesc = resultSet.getString("descricao");
	                double prVlVenda = resultSet.getDouble("vlvenda");
	                double piQtProd = resultSet.getDouble("qtproduto");
	                System.out.println( "\nNome Produto: " + prDesc + "\nValor Produto: " + prVlVenda + "\nQuantidade: " + piQtProd +"\nValor de desconto: " + piDescont);
	                double totalProduto = (prVlVenda * piQtProd) - piDescont;
	                if(totalProduto <0) {
	                	totalProduto = 0;
	                }
	                System.out.println("Valor total do Produto " + totalProduto);
	                System.out.println("\n");
	                
	                total += totalProduto;  
	            } while (resultSet.next());
	        } else {
	            System.out.println("Pedido não encontrado.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Tratamento mais adequado seria ideal
	    }
	    
	    try (Connection conn = DB.connect()){
	        Statement statement = conn.createStatement();
	        String insertSql = "UPDATE poo.pedido SET valortotal = "+total+" WHERE idpedido = "+ idPe;
	        statement.executeUpdate(insertSql);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println("Valor Total: " + total );
	    System.out.println("-----------------------------------------------------------------------\n");
	}

	@Override
	public void excluir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimir() {
			double totalProduto= 0;
			double total = 0;
		    String idPe = "";
		    String query = """
		            Select cl.idcliente, cl.nome, pe.idpedido, pe.dtemissao, 
		                pr.descricao, pr.vlvenda, pi.qtproduto, pi.vldesconto,
		                pe.valortotal
		            from     
		                poo.cliente cl
		            join     
		                poo.pedido pe on cl.idcliente = pe.idcliente
		            join 
		                poo.pedidoitens pi on pe.idpedido = pi.idpedido
		            join 
		                poo.produto pr on pi.idproduto = pr.idproduto;
		            """;

		    try (Connection connection = DB.connect();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        ResultSet resultSet = statement.executeQuery(); // Executando o PreparedStatement

		        // Imprimir o nome e o ID do cliente apenas uma vez
		        if (resultSet.next()) {
		            
		            // Iterar sobre os resultados restantes
		            do {
		            	String idCliente = resultSet.getString("idcliente");
			            String nomeCliente = resultSet.getString("nome");
			            idPe = resultSet.getString("idpedido");
			            String peDtEm = resultSet.getString("dtemissao");
		                double piDescont = resultSet.getDouble("vldesconto");
		                String prDesc = resultSet.getString("descricao");
		                double prVlVenda = resultSet.getDouble("vlvenda");
		                double piQtProd = resultSet.getDouble("qtproduto");
		                double peValorTotal = resultSet.getDouble("valortotal");
		                
			            System.out.println("Numero do pedido: " + idPe +"\nCód cliente: " + idCliente + "\t\tNome: " + nomeCliente + "\nData emissão: " + peDtEm );
			            System.out.println("________________________________________________________________________");
		            	
			            System.out.println( "\nNome Produto: " + prDesc + "\nValor Produto: " + prVlVenda + "\nQuantidade: " + piQtProd +"\nValor de desconto: " + piDescont);
		                
		                System.out.println("Valor total do Produto " + totalProduto);
		                System.out.println("\n");
		                
		                System.out.println("Valor Total: " + peValorTotal );
		    		    System.out.println("-----------------------------------------------------------------------\n");
		                total += totalProduto;  
		            } while (resultSet.next());
		        } else {
		            System.out.println("Pedido não encontrado.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); // Tratamento mais adequado seria ideal
		    }
		    
		    
	
	}



	@Override
	public void localizar() {
		// TODO Auto-generated method stub
		
	}

	public List<Pedido> listarTodos() { 
		List<Pedido> pedidos = new ArrayList<>();
	    String sql = "SELECT * FROM poo.pedido pe JOIN poo.cliente cl ON pe.idcliente = cl.idcliente";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Pedido p = new Pedido(
	                rs.getInt("idPedido"), 
	                rs.getInt("idcliente"), 
	                rs.getDate("dtEmissao"),
	                rs.getDate("dtEntrega"), 
	                rs.getDouble("valorTotal"), 
	                rs.getString("observacao"),
	                rs.getString("nome")
	            );
	            pedidos.add(p);
	        }
	        System.out.println("===================================");
	        System.out.println("        LISTA DE PEDIDOS		   ");
	        System.out.println("===================================");
	        
	    } catch (SQLException e) {
        e.printStackTrace();
	    }
	    return pedidos;
	}

	public List<Pedido> pedidoSProduto(int idPedido) { 
		List<Pedido> pedidos = new ArrayList<>();
	    String sql = "SELECT * FROM poo.pedido pe JOIN poo.cliente cl ON pe.idcliente = cl.idcliente WHERE pe.idpedido = ?";
	    
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	    	stmt.setInt(1, idPedido);
	    	ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            Pedido p = new Pedido(
	                rs.getInt("idPedido"), 
	                rs.getInt("idcliente"), 
	                rs.getDate("dtEmissao"),
	                rs.getDate("dtEntrega"), 
	                rs.getDouble("valorTotal"), 
	                rs.getString("observacao"),
	                rs.getString("nome")
	            );
	            pedidos.add(p);
	        }
	        System.out.println("===================================");
	        System.out.println("        LISTA DE PEDIDOS		   ");
	        System.out.println("===================================");
	        
	    } catch (SQLException e) {
        e.printStackTrace();
	    }
	    return pedidos;
	}
	
	
}
