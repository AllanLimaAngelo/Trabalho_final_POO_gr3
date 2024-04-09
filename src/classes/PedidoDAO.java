package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import aplicacao.Principal;
import database.DB;

public class PedidoDAO implements CRUD {

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
                System.out.println(idCliente);
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
