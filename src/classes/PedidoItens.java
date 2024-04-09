package classes;

public class PedidoItens {
	private int idPedido;
    private double vlUnitario;
    private int qtProduto;
    private double vlDesconto;
    private int idproduto;
	
    
    public PedidoItens(int idPedido, int idproduto, double vlUnitario, int qtProduto, double vlDesconto) {
		super();
		this.idPedido = idPedido;
		this.vlUnitario = vlUnitario;
		this.qtProduto = qtProduto;
		this.vlDesconto = vlDesconto;
		this.idproduto = idproduto;
	}


	@Override
	public String toString() {
		return "Values ( '" + idPedido + "', '" + idproduto +"', '"+ vlUnitario + "', '" + qtProduto + "', '" + vlDesconto + 
				 "')";
	}
    
    
    
    
    
}
