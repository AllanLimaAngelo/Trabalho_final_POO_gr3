package classes;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import database.DB;
import util.Util;

public class Menu {
    Scanner scanner;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private PedidoItensDAO pedidoItensDAO;

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
        try {
			produtoDAO = new ProdutoDAO(DB.connect());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        pedidoItensDAO = new PedidoItensDAO();
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
   		//System.out.print("Informe numero do pedido: ");
   		//int idPedido = Util.stringParaInt(scanner.nextLine());
   		
   		
   		int  idCliente = pedidoDAO.selectCliente();
   		
   		System.out.print("Data de emissão : ");
   		String dtEmissao = scanner.nextLine();
   		System.out.println(dtEmissao);
   		System.out.print("Data de entrega: ");
   		String dtEntrega = scanner.nextLine();// precisa proteger
   		System.out.print("Observação: ");
   		String obs = scanner.nextLine();// precisa proteger
   		int id =0;
   		ArrayList<PedidoItens> ListaPedidoItens = new ArrayList<>(); 		
   		Pedido p = new Pedido( idCliente, Util.retornaData(dtEmissao), Util.retornaData(dtEntrega), 0d , obs, ListaPedidoItens);
			try {
				id = pedidoDAO.incluir(p);
				
				//Cadastro dos itens do pedido
	    		do {
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
		    		
		    		PedidoItens pItens =new PedidoItens(id , produto, 0, qt, desconto);
		    		ListaPedidoItens.add(pItens);
		    		PedidoItensDAO pd = new PedidoItensDAO();
		    		pd.incluir(ListaPedidoItens.get(j));
		    		System.out.println("Deseja cadastrar outro Produto? (S/N)");
		    		j++;
				    resposta1 = scanner.nextLine();
	    		}while ("S".equalsIgnoreCase(resposta1));

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			pedidoDAO.consultarPedido(id);
			System.out.println("Deseja alterar o cliente do pedido? (S/N)");
			resposta1 = scanner.nextLine();
			if("S".equalsIgnoreCase(resposta1)) {
				System.out.println("""
						1 - Alterar cliente
						2 - Excluir o cliente
						""");
				switch (Util.stringParaInt(scanner.nextLine())) {
				case 1: 
					clienteDAO.listarTodos();
					//System.out.println("Qual o código do novo cliente do pedido?");
					//int novoCliente = Util.stringParaInt(scanner.nextLine());
					clienteDAO.updateCliente(pedidoDAO.selectCliente(), id);
					pedidoDAO.consultarPedido(id);
					break;
				case 2: 
					 clienteDAO.exclusaoCliente(id);
					 pedidoDAO.consultarPedido(id);
					 System.out.println("Cliente excluido com sucesso");
					 idCliente = pedidoDAO.selectCliente();	
					 clienteDAO.updateCliente(idCliente,id);
					 pedidoDAO.consultarPedido(id);
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
    	System.out.println("Qual pedido deseja alterar?");
    	int altPedido = Util.stringParaInt(scanner.nextLine());
    	pedidoDAO.consultarPedido(altPedido);
    	System.out.println("""
    			Deseja:
    			1 - Alterar cliente
    			2 - Excluir cliente
    			3 - Alterar produto
    			2 - excluir produtos 
    			""");
    	int opcao =  Util.stringParaInt(scanner.nextLine());
    	switch (opcao) {
		case 1:
			clienteDAO.updateCliente(pedidoDAO.selectCliente(),altPedido);
			break;

		case 2: 
			 clienteDAO.exclusaoCliente(altPedido);
			 pedidoDAO.consultarPedido(altPedido);
			 System.out.println("Cliente excluido com sucesso");
			 int idCliente = pedidoDAO.selectCliente();	
			 clienteDAO.updateCliente(idCliente,altPedido);
			 pedidoDAO.consultarPedido(altPedido);
			break;
		case 3:
			System.out.println("Qual Produto deseja alterar: ");
			int idProduto = Util.stringParaInt(scanner.nextLine());
    		pedidoItensDAO.alterar(altPedido);
			pedidoItensDAO.updateProduto(idProduto);
			break;
		default:
			break;
		} 
    }

    private void excluirPedido() {
        System.out.println("Excluindo um pedido existente...");

        System.out.print("ID do pedido: ");
        int idPedido = Util.stringParaInt(scanner.nextLine());
        pedidoDAO.consultarPedido(idPedido);
        System.out.println("Deseja confirmar a exclusão? (S/N)");
        String resposta = scanner.nextLine();
        if("S".equalsIgnoreCase(resposta)) {
	        pedidoDAO.excluir(idPedido);
	        System.out.println("Pedido excluído com sucesso.");
        }
        
    }

    private void imprimirPedidoSemProdutos() {
        System.out.println("Imprimindo pedido sem produtos...");
        System.out.println("Digite o numero do pedido");
        int pesquisa = Util.stringParaInt(scanner.nextLine());
        List<Pedido> pedidos = pedidoDAO.pedidoSProduto(pesquisa);
        for (Pedido p: pedidos) {
            System.out.println("\nCód Pedido: " + p.getIdPedido()+"\nNome cliente: "+ p.getNomeCliente() + "\nCód Cliente: " + p.getIdcliente() + "\n\nData de emissão: " + p.getDtEmissao1()
			+"\n----------------------------------\n"+ "\nData de entrega: " + p.getDtEntrega1() + "\nValor Total: " + p.getValorTotal() + "\nObservação:  "
			+ p.getObservacao());
            System.out.println("__________________________________");
        }
    }

    private void imprimirPedidoComProdutos() {
        System.out.println("Imprimindo pedido com produtos...");
        System.out.println("Digite o numero do pedido");
        int pesquisa = Util.stringParaInt(scanner.nextLine());
        pedidoDAO.consultarPedido(pesquisa);
    }

    private void localizarPedidos() {
        System.out.println("Como deseja localizar os pedidos?");
        System.out.println("1 - Por código");
        System.out.println("2 - Por cliente");
        System.out.println("3 - Por data");
        int opcao = Util.stringParaInt(scanner.nextLine());

        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o código do pedido:");
                    int codigo = Util.stringParaInt(scanner.nextLine());
                    List<Pedido> pedidosPorCodigo = pedidoDAO.localizarPedidos(codigo, 0, null);
                    exibirPedidos(pedidosPorCodigo);
                    break;
                case 2:
                    System.out.println("Digite o ID do cliente:");
                    int idCliente = Util.stringParaInt(scanner.nextLine());
                    List<Pedido> pedidosPorCliente = pedidoDAO.localizarPedidos(0, idCliente, null);
                    exibirPedidos(pedidosPorCliente);
                    break;
                case 3:
                    System.out.println("Digite a data de emissão (AAAA-MM-DD):");
                    Date dataEmissao = Date.valueOf(scanner.nextLine());
                    List<Pedido> pedidosPorData = pedidoDAO.localizarPedidos(0, 0, dataEmissao);
                    exibirPedidos(pedidosPorData);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Formato de data inválido. Use o formato AAAA-MM-DD.");
        }
    }

    private void exibirPedidos(List<Pedido> pedidos) {
        if (!pedidos.isEmpty()) {
            System.out.println("Pedidos encontrados:");
            for (Pedido pedido : pedidos) {
                System.out.println("Código do Pedido: " + pedido.getIdPedido());
                System.out.println("Nome do Cliente: " + pedido.getNomeCliente());
                System.out.println("Data de Emissão: " + pedido.getDtEmissao());
                System.out.println("Data de Entrega: " + pedido.getDtEntrega());
                System.out.println("Valor Total: " + pedido.getValorTotal());
                System.out.println("Observação: " + pedido.getObservacao());
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("Nenhum pedido encontrado.");
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
    
   /* public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }*/
    
}

