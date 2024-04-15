package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public String localizar(int id) {
    	String produto = "";
        String sql = "SELECT * FROM poo.Produto WHERE idproduto=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	produto = "\n-----------------------------\n"+"Nome: " +rs.getString("descricao") + "\n\nCusto unitário: "+ rs.getDouble("vlcusto")+"\n\nValor de venda: " + rs.getDouble("vlvenda") +
                      "\n\nCategoria: " +  rs.getString("categoria")+"\n-----------------------------\n";
            	return produto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto(rs.getString("descricao"), rs.getDouble("vlcusto"),
                        rs.getDouble("vlvenda"), rs.getString("categoria"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    public void updateProduto(int idProduto,int idPedido, int novoProduto) {

        String update = "UPDATE poo.pedidoitens SET idProduto = ? WHERE idpedido = ? and idProduto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            stmt.setInt(1, idProduto); 
            stmt.setInt(2, idPedido);
            stmt.setInt(3, novoProduto);
            stmt.executeUpdate();
    	 } catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
 public void excluirProduto(int idProduto, int idPedido) {
    	
    	
    	String delete = "delete from poo.pedidoitens where idproduto = ? and idpedido = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(delete)) {
            
        	stmt.setInt(1, idProduto);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
            
            
            
            System.out.println("Pedido e seus itens excluídos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	

    	
    }
    public void excluirTodosProdutos(int idPedido) {
    	
    	
    	String delete = "delete from poo.pedidoitens where idpedido = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(delete)) {
            
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
            
            
            
            System.out.println("Itens excluídos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        public void quadroProduto() {
     	   
       	 
     	    String sql = "SELECT * FROM poo.produto";
     	    System.out.println("==========================================");
     	    System.out.println("|           LISTA DE PRODUTOS		 | ");
     	    System.out.println("==========================================");
     	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     	        ResultSet rs = stmt.executeQuery();
     	        while (rs.next()) {
     	        	System.out.printf("|%2d | %-34s |\n", rs.getInt("idproduto"), rs.getString("descricao"));
     	                
     	            
     	            
     	        }
     	        System.out.println("==========================================");
     	    } catch (SQLException e) {
     	        e.printStackTrace();
     	    }
        		
    	
    
    }
}
