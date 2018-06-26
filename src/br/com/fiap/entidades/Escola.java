package br.com.fiap.entidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="escola")
public class Escola {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="descricao", length=45)
	private String descricao;

	@Column(name="endereco", length=45)
	private String endereco;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="dataFundacao")
	private Date dataFundacao;

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="escola")
	private Set<Curso> cursos = new HashSet<>();

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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}

	public void setDataString(String data) throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.setDataFundacao(df.parse(data));
		} catch (Exception e) {
			throw new Exception("Data Inválida");
			//O ideal é cuspir um erro
			//this.setDataFundacao(new Date());
		}
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}
}
