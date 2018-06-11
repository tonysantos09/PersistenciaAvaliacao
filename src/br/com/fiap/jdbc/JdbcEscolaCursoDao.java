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
					"SELECT E.DESCRICAO AS DESCRICAO, COUNT(C.DESCRICAO) AS NUMCURSOS\r\n"
							+ "FROM ESCOLA E, CURSO C \r\n" + "WHERE E.ID = C.IDESCOLA\r\n" + "GROUP BY E.DESCRICAO",
					new EscolaCursoMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return escolas;
	}
}
