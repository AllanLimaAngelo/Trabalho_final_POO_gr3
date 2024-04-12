package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import database.DB;

public class PedidoItensDAO implements CRUD <PedidoItens>{

	@Override
	public int incluir(PedidoItens a) throws SQLException {
		
		Connection conn = DB.connect();

        String insertSql = "INSERT INTO poo.PedidoItens (idpedido, idproduto, vlunitario, qtproduto, vldesconto) VALUES (?, ?, ?, ?, ?)" ;
        PreparedStatement statement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, a.getIdPedido());
        statement.setInt(2, a.getIdproduto());
        statement.setDouble(3, a.getVlUnitario());
        statement.setInt(4, a.getQtProduto());
        statement.setDouble(5, a.getVlDesconto());
        statement.executeUpdate();
		return 0;
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
