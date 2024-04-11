package classes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JList;

import database.DB;
import util.Util;

public class Menu {
    private Scanner scanner;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;

    public Menu() {
        scanner = new Scanner(System.in);
        try {
			pedidoDAO = new PedidoDAO(DB.connect());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			clienteDAO = new ClienteDAO(DB.connect());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        produtoDAO = new ProdutoDAO(null);
    }

    public void exibirMenu() {
        int opcao = 0;
        do {
            System.out.println("===================================");
            System.out.println("           MENU DE PEDIDOS         ");
            System.out.println("===================================");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Alterar Pedido");
            System.out.println("4. Excluir Pedido");
            System.out.println("5. Imprimir Pedido (sem produtos)");
            System.out.println("6. Imprimir Pedido (com produtos)");
            System.out.println("7. Localizar Pedidos");
            System.out.println("0. Sair");
            System.out.println("-----------------------------------");
            System.out.print("Escolha uma opção: ");
            opcao = Util.stringParaInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    alterarPedido();
                    break;
                case 4:
                    excluirPedido();
                    break;
                case 5:
                    imprimirPedidoSemProdutos();
                    break;
                case 6:
                    imprimirPedidoComProdutos();
                    break;
                case 7:
                    localizarPedidos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void criarPedido() {
        System.out.println("Criando novo pedido...");
        System.out.println("-----------------------------------");
        String resposta;
   	 String resposta1;
   	
   	 
   	 //Cadastro de pedidos
   	do {
   		int j= 0;
   		System.out.print("Informe numero do pedido: ");
   		int idPedido = Util.stringParaInt(scanner.nextLine());
   		System.out.println("""
   				Deseja cadastrar o cliente por:
   				1 - Código
   				2 - CPF
   				3 - Nome
   				""");
   		int opcao = Util.stringParaInt(scanner.nextLine());
   		
   		 int idCliente = PedidoDAO.selectCliente(opcao);
   		
   		System.out.print("Data de emissão : ");
   		String dtEmissao = scanner.nextLine();
   		System.out.print("Data de entrega: ");
   		String dtEntrega = scanner.nextLine();// precisa proteger
   		System.out.print("Observação: ");
   		String obs = scanner.nextLine();// precisa proteger
   	
   		ArrayList<PedidoItens> ListaPedidoItens = new ArrayList<>(); 		
   		Pedido p = new Pedido(idPedido, idCliente, Util.retornaData(dtEmissao), Util.retornaData(dtEntrega), 0d , obs, ListaPedidoItens);
		System.out.println(p.toString());
			try {
				pedidoDAO.incluir(p.toString());
				
				//Cadastro dos itens do pedido
	    		do {
	    		
	    		System.out.print("Digite o código do produto: ");
	    		int produto = Util.stringParaInt(scanner.nextLine());
	    		System.out.print("Digite a quantidade do produto: ");
	    		int qt = Util.stringParaInt(scanner.nextLine());
	    		System.out.print("Digite o valor de desconto: ");
	    		int desconto =Util.stringParaInt(scanner.nextLine());	
	    		PedidoItens pItens =new PedidoItens(idPedido, produto, 0, qt, desconto);
	    		ListaPedidoItens.add(pItens);
	    		PedidoItensDAO pd = new PedidoItensDAO();
	    		pd.incluir(ListaPedidoItens.get(j).toString());
	    		System.out.println("Deseja cadastrar outro Produto? (S/N)");
	    		j++;
			    resposta1 = scanner.nextLine();
	    		}while ("S".equalsIgnoreCase(resposta1));

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			pedidoDAO.consultarPedido(idPedido);
			System.out.println("Deseja alterar o cliente do pedido? (S/N)");
			resposta1 = scanner.nextLine();
			if("S".equalsIgnoreCase(resposta1)) {
				System.out.println("""
						1 - Alterar cliente
						2 - Excluir o cliente
						""");
				opcao = Util.stringParaInt(scanner.nextLine());
				switch (opcao) {
				case 1: 
					clienteDAO.listarTodos();
					System.out.println("Qual o código do novo cliente do pedido?");
					int novoCliente = Util.stringParaInt(scanner.nextLine());
					clienteDAO.updateCliente(novoCliente, idPedido);
					pedidoDAO.consultarPedido(idPedido);
					break;

				default:
					break;
				}

			}
			
			System.out.println("Deseja cadastrar outro pedido? (S/N)");
		    resposta = scanner.nextLine();
   	}while ("S".equalsIgnoreCase(resposta));
   }
    

    private void listarPedidos() {
        System.out.println("Listando todos os pedidos...");

        List<Pedido> pedidos = pedidoDAO.listarTodos();
        for (Pedido p: pedidos) {
            System.out.println("\nCód Pedido: " + p.getIdPedido()+"\nNome cliente: "+ p.getNomeCliente() + "\nCód Cliente: " + p.getIdcliente() + "\n\nData de emissão: " + p.getDtEmissao1()
			+ "\nData de entrega: " + p.getDtEntrega1() + "\nValor Total: " + p.getValorTotal() + "\nObservação:  "
			+ p.getObservacao());
            System.out.println("____________________________________");
        }
    }

    private void alterarPedido() {
        System.out.println("Alterando um pedido existente...");

        System.out.print("ID do pedido: ");
        int idPedido = scanner.nextInt();
        Pedido pedido = pedidoDAO.localizar(idPedido);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
    }

    private void excluirPedido() {
        System.out.println("Excluindo um pedido existente...");

        System.out.print("ID do pedido: ");
        int idPedido = scanner.nextInt();
        Pedido pedido = pedidoDAO.localizar(idPedido);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        pedidoDAO.excluir(idPedido);

        System.out.println("Pedido excluído com sucesso.");
    }

    private void imprimirPedidoSemProdutos() {
        System.out.println("Imprimindo pedido sem produtos...");
        System.out.println("Digite o numero do pedido");
        int pesquisa = Util.stringParaInt(scanner.nextLine());
        List<Pedido> pedidos = pedidoDAO.pedidoSProduto(pesquisa);
        for (Pedido p: pedidos) {
            System.out.println("\nCód Pedido: " + p.getIdPedido()+"\nNome cliente: "+ p.getNomeCliente() + "\nCód Cliente: " + p.getIdcliente() + "\n\nData de emissão: " + p.getDtEmissao1()
			+ "\nData de entrega: " + p.getDtEntrega1() + "\nValor Total: " + p.getValorTotal() + "\nObservação:  "
			+ p.getObservacao());
            System.out.println("____________________________________");
        }
    }

    private void imprimirPedidoComProdutos() {
        System.out.println("Imprimindo pedido com produtos...");
        System.out.println("Digite o numero do pedido");
        int pesquisa = Util.stringParaInt(scanner.nextLine());
        pedidoDAO.consultarPedido(pesquisa);
    }

    private void localizarPedidos() {
        System.out.println("Localizando pedidos...");
        
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}

