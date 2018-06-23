package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.EscolaCursoMapper;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class JdbcEscolaCursoDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<EscolaCursoViewModel> listarEscolasComCursos() {
		List<EscolaCursoViewModel> escolas = new ArrayList<>();
		try {
			escolas = this.jdbcTemplate.query(
					"SELECT escola.descricao, Count(curso.id) AS numcursos, "
							+ "Count(DISTINCT matricula.idaluno) AS numalunos "
							+ "FROM (matricula INNER JOIN curso ON matricula.idcurso = curso.id) "
							+ "INNER JOIN escola ON curso.idescola = escola.id "
							+ "GROUP BY escola.descricao",
					new EscolaCursoMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return escolas;
	}
}
