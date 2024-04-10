package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Util;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }
    
    

    public ClienteDAO() {
		super();
	}



	public Cliente localizar(int id) {
        String sql = "SELECT * FROM Cliente WHERE idcliente=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getString("nome"), rs.getString("cpf"), rs.getDate("dtNascimento"),
                        rs.getString("endereco"), rs.getString("telefone"), rs.getInt("idcliente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public void listarTodos() {
	    List<Cliente> clientes = new ArrayList<>();
	    String sql = "SELECT * FROM poo.Cliente";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Cliente cliente = new Cliente(
	                rs.getString("nome"), 
	                rs.getString("cpf"), 
	                rs.getDate("dtNascimento"),
	                rs.getString("endereco"), 
	                rs.getString("telefone"), 
	                rs.getInt("idcliente")
	            );
	            clientes.add(cliente);
	        }
	        System.out.println("===================================");
	        System.out.println("        LISTA DE CLIENTES		   ");
	        System.out.println("===================================");
	        for (Cliente cliente : clientes) {
	            System.out.println(cliente.toString());
	            System.out.println("____________________________________");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
    public void updateCliente(int idCliente,int idPedido) {

        String update = "UPDATE poo.pedido SET idcliente = ? WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            stmt.setInt(1, idCliente); 
            stmt.setInt(2, idPedido);  
            stmt.executeUpdate();
    	 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    	
}
