package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import database.DB;
import util.Util;

public class PedidoItensDAO implements CRUD <PedidoItens>{
	Scanner scanner = new Scanner (System.in);
	ProdutoDAO produtoDAO = new ProdutoDAO(null);
	private Connection conn ;
	PedidoItens pItens1;
	int j = 0;

	ArrayList<PedidoItens> Lista = new ArrayList<>(); 
	
	public PedidoItensDAO() {
		super();
		try {
			this.conn = DB.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			produtoDAO = new ProdutoDAO(DB.connect());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public int incluir(PedidoItens a) throws SQLException {
		
		

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
	

	public PedidoItens alterar( int idPedido) {
		String resposta1 = "";			
			String lp;
			int produto = 0; 
		do {
			lp = "";
    		System.out.print("Digite o código do produto: ");
    		produto =Util.stringParaInt(scanner.nextLine());
    		lp = localizarProduto(produto);
		}while(lp == "");
			System.out.print("Digite a quantidade do produto: ");
			int qt = Util.stringParaInt(scanner.nextLine());
			System.out.print("Digite o valor de desconto: ");
			int desconto =Util.stringParaInt(scanner.nextLine());
			pItens1 =new PedidoItens(idPedido , produto, 0, qt, desconto);
			return pItens1;		
	}
	
	 public void updateProduto(int idProduto) {
		 	
	        String update = "UPDATE poo.pedidoitens SET idproduto = ?, qtproduto = ? , vlDesconto = ? WHERE idpedido = ? and idProduto = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(update)) {
	            stmt.setInt(1, pItens1.getIdproduto()); 
	            stmt.setInt(2, pItens1.getQtProduto());
	            stmt.setDouble(3, pItens1.getVlDesconto());
	            stmt.setInt(4, pItens1.getIdPedido()); 
	            stmt.setInt(5, idProduto);
	            stmt.executeUpdate();
	    	 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 
	  public String localizarProduto(int idp) {
	    	String produto = produtoDAO.localizar(idp);
	    	System.out.println(produto);
	    	System.out.println("Confirma o produto? (S/N)");
	    	String resposta = scanner.nextLine();
	    	if("S".equalsIgnoreCase(resposta)){
	    		return produto;
	    	}else{
	    		return produto = "";
	    	}
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


	@Override
	public void alterar(PedidoItens a) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void excluir(int i) {
		// TODO Auto-generated method stub
		
	}
	
	

}
