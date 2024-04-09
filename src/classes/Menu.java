package classes;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;

    public Menu() {
        scanner = new Scanner(System.in);
        pedidoDAO = new PedidoDAO();
        clienteDAO = new ClienteDAO();
        produtoDAO = new ProdutoDAO();
    }

    public void exibirMenu() {
        int opcao;
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
            opcao = scanner.nextInt();

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

        System.out.print("ID do cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = clienteDAO.localizar(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        pedidoDAO.inserir(novoPedido);

        System.out.println("Pedido criado com sucesso.");
    }

    private void listarPedidos() {
        System.out.println("Listando todos os pedidos...");

        List<Pedido> pedidos = pedidoDAO.listarTodos();
        for (Pedido pedido : pedidos) {
            System.out.println("ID: " + pedido.getId() + ", Cliente: " + pedido.getCliente().getNome() + ", Data de Emissão: " + pedido.getDataEmissao());
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
    }

    private void imprimirPedidoComProdutos() {
        System.out.println("Imprimindo pedido com produtos...");
    }

    private void localizarPedidos() {
        System.out.println("Localizando pedidos...");
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}

