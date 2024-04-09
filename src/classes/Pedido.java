package classes;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
	private int idPedido;
	private LocalDate dtEmissao;
	private LocalDate dtEntrega;
	private double valorTotal;
	private String observacao;
	private String idcliente;
	private List<PedidoItens> itens;

	public Pedido(int idPedido, String idcliente, LocalDate dtEmissao, LocalDate dtEntrega, double valorTotal, String observacao,
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCliente() {
		return idcliente;
	}

	public void setCliente(String idcliente) {
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

	

	

	
}
