package classes;

public class PedidoItens {
	private int idPedido;
    private double vlUnitario;
    private int qtProduto;
    private double vlDesconto;
    private int idproduto;
	
    
    public PedidoItens( int idPedido, int idproduto, double vlUnitario, int qtProduto, double vlDesconto) {
		super();
		this.idPedido = idPedido;
		this.vlUnitario = vlUnitario;
		this.qtProduto = qtProduto;
		this.vlDesconto = vlDesconto;
		this.idproduto = idproduto;
	}


	public int getIdPedido() {
		return idPedido;
	}


	public double getVlUnitario() {
		return vlUnitario;
	}


	public int getQtProduto() {
		return qtProduto;
	}


	public double getVlDesconto() {
		return vlDesconto;
	}


	public int getIdproduto() {
		return idproduto;
	}


	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}


	public void setVlUnitario(double vlUnitario) {
		this.vlUnitario = vlUnitario;
	}


	public void setQtProduto(int qtProduto) {
		this.qtProduto = qtProduto;
	}


	public void setVlDesconto(double vlDesconto) {
		this.vlDesconto = vlDesconto;
	}


	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}


	@Override
	public String toString() {
		return "Values ( '" + idPedido + "', '" + idproduto +"', '"+ vlUnitario + "', '" + qtProduto + "', '" + vlDesconto + 
				 "')";
	}
    
    
    
    
    
}
