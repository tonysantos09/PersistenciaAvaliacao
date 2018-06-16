package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import br.com.fiap.entidades.Curso;

public class CursoMapper implements RowMapper<Curso> {

	@Override
	public Curso mapRow(ResultSet rs, int arg1) throws SQLException {
		Curso curso = new Curso();
		curso.setId(rs.getInt("id"));
		curso.setDescricao(rs.getString("descricao"));

		return curso;
	}
}
