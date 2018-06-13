package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entidades.Nota;
import br.com.fiap.mapper.NotaMapper;

public class JdbcNotaDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void incluirNota(Nota nota) throws Exception {
		try {
			String sql = "INSERT INTO nota " + "(idcurso,idaluno,nota) VALUES (?,?,?)";
			jdbcTemplate.update(sql, nota.getCurso().getId(), nota.getAluno().getId(), nota.getNota());
		} catch (Exception e) {
			throw e;
		}
	}

	public Nota buscarNota(int id) throws Exception {
		Nota nota = null;
		try {
			String query = "SELECT * FROM nota WHERE id= ? ";
			nota = this.jdbcTemplate.queryForObject(query, new Integer[] { id }, new NotaMapper());
		} catch (Exception e) {
			throw e;
		}
		
		return nota;
	}

	public List<Nota> listarNota(int idaluno) throws Exception {
		List<Nota> notas = new ArrayList<>();
		try {
			notas = this.jdbcTemplate.query("SELECT * FROM nota WHERE idaluno=?", new Integer[] { idaluno },
					new NotaMapper());
		} catch (Exception e) {
			throw e;
		}
		
		return notas;
	}
}
