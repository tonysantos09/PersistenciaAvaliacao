package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.CursoAlunoMapper;
import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class JdbcCursoAlunoDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<CursoAlunoViewModel> listarCursosQtdAluno(int idescola) {
		List<CursoAlunoViewModel> cursos = new ArrayList<>();
		
		String query = "SELECT curso.descricao, COUNT(curso.id) AS numalunos "
				+ "FROM curso INNER JOIN matricula ON curso.id = matricula.idcurso "
				+ "WHERE curso.idescola = ? GROUP BY curso.id";
		cursos = this.jdbcTemplate.query(query, new Integer[] { idescola }, new CursoAlunoMapper());

		return cursos;
	}
	
	public List<CursoAlunoViewModel> listarAlunosDeCurso(int idcurso) {
		List<CursoAlunoViewModel> alunos = new ArrayList<>();

		String query = "SELECT escola.descricao AS escola, curso.descricao, aluno.nome as nomealuno "
				+ "FROM aluno INNER JOIN ((matricula INNER JOIN curso ON matricula.idcurso = curso.id) "
				+ "INNER JOIN escola ON curso.idescola = escola.id) ON aluno.id = matricula.idaluno "
				+ "WHERE curso.id = ? GROUP BY aluno.nome";
		alunos = this.jdbcTemplate.query(query, new Integer[] { idcurso }, new CursoAlunoMapper());
	
		return alunos;
	}
}
