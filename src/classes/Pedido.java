package classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Pedido {
	private int idPedido;
	private Date dtEmissao1;
	private Date dtEntrega1;
	private LocalDate dtEmissao;
	private LocalDate dtEntrega;
	private double valorTotal;
	private String observacao;
	private int idcliente;
	private List<PedidoItens> itens;
	private String nomeCliente;

	public Pedido(int idPedido, int idcliente, LocalDate dtEmissao, LocalDate dtEntrega, double valorTotal, String observacao,
			List<PedidoItens> itens) {
		super();
		this.idPedido = idPedido;
		this.idcliente = idcliente;
		this.dtEmissao = dtEmissao;
		this.dtEntrega = dtEntrega;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.itens = itens;
	}
	
	//usado para localizar pedidos
	public Pedido(int idPedido, int idcliente, Date dtEmissao, Date dtEntrega, 
			double valorTotal, String observacao, String nomeCliente) {
		super();
		this.idPedido = idPedido;
		this.idcliente = idcliente;
		this.dtEmissao1 = dtEmissao;
		this.dtEntrega1 = dtEntrega;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.nomeCliente = nomeCliente;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDate getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(LocalDate dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public LocalDate getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(LocalDate dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDtEmissao1() {
		return dtEmissao1;
	}


	public Date getDtEntrega1() {
		return dtEntrega1;
	}


	public int getIdcliente() {
		return idcliente;
	}


	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getCliente() {
		return idcliente;
	}

	public void setCliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public List<PedidoItens> getItens() {
		return itens;
	}

	public void setItens(List<PedidoItens> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Values ( '" + idPedido +"', '"+ idcliente+"', '" + dtEmissao + "', '" + dtEntrega + "', '" + valorTotal + "', '"
				+ observacao +"')";
	}


	public String getNomeCliente() {
		return nomeCliente;
	}

	

	

	
}
