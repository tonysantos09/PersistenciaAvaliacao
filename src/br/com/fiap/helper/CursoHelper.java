package br.com.fiap.helper;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import br.com.fiap.entidades.Curso;

public class CursoHelper {
	private EntityManager em;

	public CursoHelper(EntityManager em) {
		this.em = em;
	}

	public String salvar(Curso curso) {
		em.getTransaction().begin();
		em.persist(curso);
		em.getTransaction().commit();
		return "Curso incluído com sucesso!";
	}
}