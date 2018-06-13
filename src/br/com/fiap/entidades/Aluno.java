package br.com.fiap.entidades;

public class Aluno {
	private int id;
	private String nome;
	private Escola escola;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	@Override
	public String toString() {
		return this.getNome();
	}
}
