package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.viewmodel.AlunoCursoViewModel;

public class AlunoCursoMapper implements RowMapper<AlunoCursoViewModel> {
	@Override
	public AlunoCursoViewModel mapRow(ResultSet rs, int arg1) throws SQLException {
		AlunoCursoViewModel vm = new AlunoCursoViewModel();
		vm.setEscola(rs.getString("escola"));
		vm.setDescricao(rs.getString("descricao"));
		vm.setNomealuno(rs.getString("nomealuno"));
		vm.setNota(rs.getDouble("nota"));
		
		return vm;
	}

}
