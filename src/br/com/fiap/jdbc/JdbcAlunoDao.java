package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entidades.Aluno;
import br.com.fiap.mapper.AlunoMapper;

public class JdbcAlunoDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void incluirAluno(Aluno aluno) throws Exception {
		try {
			String sql = "INSERT INTO aluno " + "(nome) VALUES (?)";
			jdbcTemplate.update(sql, aluno.getNome());
		} catch (Exception e) {
			throw e;
		}
	}

	public Aluno buscarAluno(int id) throws Exception {
		Aluno aluno = null;
		try {
			String query = "SELECT * FROM aluno WHERE id= ? ";
			aluno = this.jdbcTemplate.queryForObject(query, new Integer[] { id }, new AlunoMapper());
		} catch (Exception e) {
			throw e;
		}

		return aluno;
	}

	public List<Aluno> listarAlunos() throws Exception {
		List<Aluno> alunos = new ArrayList<>();
		try {
			alunos = this.jdbcTemplate.query("SELECT * FROM aluno order by nome",
					new AlunoMapper());
		} catch (Exception e) {
			throw e;
		}

		return alunos;
	}

	public List<Aluno> listarCursoAlunos(int idcurso) throws Exception {
		List<Aluno> alunos = new ArrayList<>();
		try {
			alunos = this.jdbcTemplate.query(
					"SELECT DISTINCT aluno.* "
							+ "FROM matricula LEFT JOIN aluno ON matricula.idaluno = aluno.id "
							+ "WHERE matricula.idcurso = ?",
					new Integer[] { idcurso }, new AlunoMapper());
		} catch (Exception e) {
			throw e;
		}

		return alunos;
	}

}
