package classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.DB;

public class PedidoItensDAO implements CRUD{

	@Override
	public void incluir(Object a) throws SQLException {
		Connection conn = DB.connect();
		// Cria uma declaração
        Statement statement = conn.createStatement();
        // Executa uma consulta SQL
        String insertSql = "INSERT INTO poo.PedidoItens (idpedido, idproduto, vlunitario, qtproduto, vldesconto) " + a.toString();
        statement.executeUpdate(insertSql);
		
	}
	

	@Override
	public void alterar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void localizar() {
		// TODO Auto-generated method stub
		
	}

}
