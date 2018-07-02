package br.com.fiap.helper;

import javax.persistence.EntityManager;
import br.com.fiap.entidades.Curso;

public class CursoHelper {
	private EntityManager em;

	public CursoHelper(EntityManager em) {
		this.em = em;
	}

	public void salvar(Curso curso) {
		em.getTransaction().begin();
		em.persist(curso);
		em.getTransaction().commit();
	}
}