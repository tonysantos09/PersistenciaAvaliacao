package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entidades.Curso;
import br.com.fiap.mapper.CursoMapper;

public class JdbcCursoDao {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void incluirCurso(Curso curso) throws Exception {
		String sql = "INSERT INTO curso " + "(idescola,descricao) VALUES (?,?)";
		jdbcTemplate.update(sql, curso.getEscola().getId(), curso.getDescricao());
	}

	public Curso buscarCurso(int id) throws Exception {
		Curso curso = null;

		String query = "SELECT * FROM curso WHERE id= ? ";
		curso = this.jdbcTemplate.queryForObject(query, new Integer[] { id }, new CursoMapper());
		
		return curso;
	}

	public List<Curso> listarCursos(int idescola) throws Exception {
		List<Curso> cursos = new ArrayList<>();
		
		cursos = this.jdbcTemplate.query("SELECT * FROM curso WHERE idescola=?", new Integer[] { idescola },
					new CursoMapper());
		
		return cursos;
	}

	public List<Curso> listarCursosComAlunos(int idescola) {
		List<Curso> cursos = new ArrayList<>();
		
		cursos = this.jdbcTemplate.query("SELECT DISTINCT curso.* FROM matricula LEFT JOIN curso ON "
					+ "matricula.idcurso = curso.id WHERE matricula.idcurso = curso.id "
					+ "AND curso.idescola=?", 
					new Integer[] { idescola },
					new CursoMapper());
		
		return cursos;
	}
	
	public List<Curso> listarCursosPorEscola(int idescola) {
		List<Curso> cursos = new ArrayList<>();

		cursos = this.jdbcTemplate.query("SELECT curso.* FROM curso "
					+ "WHERE curso.idescola = ?", 
					new Integer[] { idescola },
					new CursoMapper());
		
		return cursos;
	}
}
