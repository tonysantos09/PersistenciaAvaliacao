package br.com.fiap.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entidades.Matricula;

public class JdbcMatriculaDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void incluirMatricula(Matricula matricula) throws Exception {
		try {
			String sql = "INSERT INTO matricula " + "(idcurso, idaluno) VALUES (?,?)";
			jdbcTemplate.update(sql, matricula.getCurso().getId(), matricula.getAluno().getId());
		} catch (Exception e) {
			throw e;
		}
	}
}
