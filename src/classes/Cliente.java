package classes;

import java.util.Date;

public class Cliente extends Pessoa {
	private int idCliente;
	
		public Cliente(String nome, String cpf, Date dtNascimento, String endereco, String telefone, int idCliente) {
		super(nome, cpf, dtNascimento, endereco, telefone);
		this.idCliente = idCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+ "Values ('" + getNome() + "', '" + getCpf() + "', '" + getDtNascimento() + "', '"
				+ getEndereco() + "', '" + getTelefone() + "')";
	}
	
	
}
