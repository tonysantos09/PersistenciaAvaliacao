package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.entidades.Nota;

public class NotaMapper implements RowMapper<Nota> {
	
	@Override
	public Nota mapRow(ResultSet rs, int arg1) throws SQLException {
		Nota nota = new Nota();
		nota.setId(rs.getInt("id"));
		nota.setNota(rs.getDouble("nota"));
		
		return nota;
	}
}
