package classes;

public class Produto {
	private int idProduto;
    private String descricao;
    private double vlCusto;
    private double vlVenda;
    private String categoria;
	
    public Produto(int idProduto, String descricao, double vlCusto, double vlVenda, String categoria) {
		super();
		this.idProduto = idProduto;
		this.descricao = descricao;
		this.vlCusto = vlCusto;
		this.vlVenda = vlVenda;
		this.categoria = categoria;
		
		
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getVlCusto() {
		return vlCusto;
	}

	public void setVlCusto(double vlCusto) {
		this.vlCusto = vlCusto;
	}

	public double getVlVenda() {
		return vlVenda;
	}

	public void setVlVenda(double vlVenda) {
		this.vlVenda = vlVenda;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
    
}
