package br.com.fiap.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.fiap.entidades.Escola;

public class Helper {
	private EntityManager em;

	public Helper(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<Escola> listarEscola() {
		Query query = em.createQuery("select e from escola e");
		return query.getResultList();
	}
}
