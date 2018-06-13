package br.com.fiap.entidades;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private int id;
	private String descricao;
	private Escola escola;
	private List<Aluno> alunos = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}
