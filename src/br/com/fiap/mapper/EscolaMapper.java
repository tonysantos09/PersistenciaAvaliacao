package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import br.com.fiap.entidades.Escola;

public class EscolaMapper implements RowMapper<Escola> {
	public Escola mapRow(ResultSet rs, int arg1) throws SQLException {
		Escola escola = new Escola();
		escola.setId(rs.getInt("id"));
		escola.setDescricao(rs.getString("descricao"));
		escola.setEndereco(rs.getString("endereco"));
		escola.setDataFundacao(rs.getDate("datafundacao"));

		return escola;
	}
}
