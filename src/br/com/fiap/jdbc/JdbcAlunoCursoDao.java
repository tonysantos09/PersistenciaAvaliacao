package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.AlunoCursoMapper;
import br.com.fiap.viewmodel.AlunoCursoViewModel;

public class JdbcAlunoCursoDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<AlunoCursoViewModel> listarAlunosDeCurso(int idcurso) {
		List<AlunoCursoViewModel> alunos = new ArrayList<>();
		
		String query = "SELECT escola.descricao AS escola, curso.descricao, aluno.nome AS nomealuno, matricula.nota "
				+ "FROM aluno INNER JOIN ((matricula INNER JOIN curso ON matricula.idcurso = curso.id) "
				+ "INNER JOIN escola ON curso.idescola = escola.id) ON aluno.id = matricula.idaluno "
				+ "WHERE curso.id = ? GROUP BY aluno.nome";
		
		alunos = this.jdbcTemplate.query(query, new Integer[] { idcurso }, new AlunoCursoMapper());

		return alunos;
	}
	
	public List<AlunoCursoViewModel> listarAlunoCursos(int idaluno) {
		List<AlunoCursoViewModel> alunos = new ArrayList<>();
		
		String query = "SELECT escola.descricao AS escola, aluno.nome AS nomealuno, curso.descricao, matricula.nota "
				+ "FROM ((matricula INNER JOIN aluno ON matricula.idaluno = aluno.id) "
				+ "INNER JOIN curso ON matricula.idcurso = curso.id) INNER JOIN escola ON curso.idescola = escola.id "
				+ "WHERE aluno.id = ? ORDER BY curso.descricao";
		alunos = this.jdbcTemplate.query(query, new Integer[] { idaluno }, new AlunoCursoMapper());

		return alunos;
	}
}
