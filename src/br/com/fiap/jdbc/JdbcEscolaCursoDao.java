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

		escolas = this.jdbcTemplate.query(
					"SELECT escola.descricao, Count(*) AS numcursos "
							+ "FROM escola "
							+ "LEFT JOIN curso ON curso.idescola = escola.id "
							+ "GROUP BY escola.descricao",
					new EscolaCursoMapper());
		
		return escolas;
	}
}
