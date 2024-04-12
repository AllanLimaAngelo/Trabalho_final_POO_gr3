package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import database.DB;
import util.Util;

public class PedidoDAO implements CRUD <Pedido> {
	int idCliente = 0;
	private Connection connection;
	
	

	public PedidoDAO(Connection connection) {
		this.connection = connection;
	}

	static Scanner input = new Scanner (System.in);
	
	public int incluir(Pedido pedido) throws SQLException {
		
		
		Connection conn = DB.connect();
        // Executa uma consulta SQL
        String insertSql = "INSERT INTO poo.Pedido (idcliente, dtemissao, dtentrega, valortotal, observacao) Values ( ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, pedido.getIdcliente());
        statement.setDate(2, Util.passaPDate(pedido.getDtEmissao()));
        statement.setDate(3, Util.passaPDate(pedido.getDtEmissao()));
        statement.setDouble(4, pedido.getValorTotal());
        statement.setString(5, pedido.getObservacao());
        statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		int idPedido = 0;
		if (rs.next()) {
			idPedido = rs.getInt("idpedido");	
		}
        return idPedido;
	}
	
	
	
	public int selectCliente() {
        int idCliente = 0;
       do{
        System.out.println("""
   				Deseja cadastrar o cliente por:
   				1 - Código
   				2 - CPF
   				3 - Nome
   				""");
   		int opcao = Util.stringParaInt(input.nextLine());
        switch (opcao) {
            case 1:
                System.out.print("Informe ID do cliente : ");
                int id = Util.stringParaInt(input.nextLine());
                idCliente = consultarClientePorID(id);
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
       }while(idCliente == 0);
        return idCliente;
    }
	
	private int consultarClientePorID(int id) {
	    String query = "SELECT idcliente, nome FROM poo.Cliente WHERE idcliente = ?";
	    
	    return consultarClienteInt(query, id);
	 }

    private int consultarClientePorCPF(String cpf) {
        String query = "SELECT idcliente, nome FROM poo.Cliente WHERE cpf = ?";
        return consultarCliente(query, cpf);
    }

    private int consultarClientePorNome(String nome) {
        String query = "SELECT idcliente, nome FROM poo.Cliente WHERE nome = ?";
        return consultarCliente(query, nome);
    }

    private int consultarCliente(String query, String parametro) {
        int idCliente = 0;
        
        try (Connection connection = DB.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, parametro);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    idCliente = rs.getInt("idcliente");
                    
                    System.out.println("Cód Cliente: " + idCliente + " Nome Cliente: " + rs.getString("nome"));
                    System.out.println("Deseja confirmar o cliente? (S/N)");
                    String resposta = input.nextLine();
                    if("S".equalsIgnoreCase(resposta)){
                    	
                    	idCliente = rs.getInt("idcliente");
                    }
                } else {
                    System.out.println("Cliente não encontrado.");
                    selectCliente();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratamento mais adequado seria ideal
        }
        return idCliente;
    }
    
    private int consultarClienteInt(String query, int parametro) {
        int idCliente = 0;
        
        try (Connection connection = DB.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, parametro);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    
                	
                    
                    System.out.println("Cód Cliente: " + rs.getInt("idcliente") + " Nome Cliente: " + rs.getString("nome"));
                    System.out.println("Deseja confirmar o cliente? (S/N)");
                    String resposta = input.nextLine();
                    if("S".equalsIgnoreCase(resposta)){
                    	
                    	idCliente = rs.getInt("idcliente");
                    }
                } else {
                    System.out.println("Cliente não encontrado.");
                    selectCliente();
                    
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
	    int idPe = 0;
	    String query = """
	            SELECT COALESCE(cl.idcliente, 0) as idcliente, 
					  cl.nome, 
					  pe.idpedido, 
					  pe.dtemissao, 
					  pr.descricao, 
					  pr.vlvenda, 
					  pi.qtproduto, 
					  pi.vldesconto	
	    		FROM 
	    			  poo.pedido pe
	    		LEFT JOIN 
	    			  poo.cliente cl ON pe.idcliente = cl.idcliente
	    		JOIN 
	    		      poo.pedidoitens pi ON pe.idpedido = pi.idpedido
	    		JOIN 
	    		      poo.produto pr ON pi.idproduto = pr.idproduto
	    		WHERE pe.idpedido = ?;
	            """;

	    try (Connection connection = DB.connect();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, idPedido); // Convertendo o parâmetro para inteiro
	        ResultSet resultSet = statement.executeQuery(); // Executando o PreparedStatement

	        // Imprimir o nome e o ID do cliente apenas uma vez
	        if (resultSet.next()) {
	            String idCliente = resultSet.getString("idcliente");
	            String nomeCliente = resultSet.getString("nome");
	            idPe = resultSet.getInt("idpedido");
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
	      
	        String insertSql = "UPDATE poo.pedido SET valortotal = ? WHERE idpedido = ?";
	        
	        PreparedStatement statement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
	        statement.setDouble(1, total);
	        statement.setInt(2, idPe);
	        statement.executeUpdate();
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
	/*public int capturarIdPedido() {
        String sql = "Select idpedido from poo.Pedido order by idpedido desc limit 1";
        int id =0;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
           
           id=rs.getInt("idPedido");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
	
	}*/
}
