package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entidades.Escola;
import br.com.fiap.mapper.EscolaMapper;

public class JdbcEscolaDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void incluirEscola(Escola escola) throws Exception {
		try {
			String sql = "INSERT INTO escola " + "(descricao,endereco,datafundacao) VALUES (?,?,?)";
			this.jdbcTemplate.update(sql, escola.getDescricao(), escola.getEndereco(), escola.getDataFundacao());
		} catch (Exception e) {
			throw e;
		}
	}

	public Escola buscarEscola(int id) throws Exception {
		Escola escola = null;
		try {
			String query = "SELECT * FROM escola WHERE id= ? ";
			escola = this.jdbcTemplate.queryForObject(query, new Integer[] { id }, new EscolaMapper());
		} catch (Exception e) {
			throw e;
		}

		return escola;
	}

	public List<Escola> listarEscolas() throws Exception {
		List<Escola> escolas = new ArrayList<>();
		try {
			escolas = this.jdbcTemplate.query("SELECT * FROM escola", new EscolaMapper());
		} catch (Exception e) {
			throw e;
		}

		return escolas;
	}
	
	public List<Escola> listarEscolasComCursos() throws Exception {
		List<Escola> escolasComCursos = new ArrayList<>();
		try {
			escolasComCursos = this.jdbcTemplate.query("SELECT escola.* FROM escola, curso where curso.idescola = escola.id", new EscolaMapper());
		} catch (Exception e) {
			throw e;
		}

		return escolasComCursos;
	}
}
