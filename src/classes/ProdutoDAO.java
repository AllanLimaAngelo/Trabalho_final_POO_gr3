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
    
    
}
