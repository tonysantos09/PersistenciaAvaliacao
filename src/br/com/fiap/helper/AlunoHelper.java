package br.com.fiap.helper;

import javax.persistence.EntityManager;
import br.com.fiap.entidades.Aluno;

public class AlunoHelper {
	private EntityManager em;

	public AlunoHelper(EntityManager em) {
		this.em = em;
	}

	public String salvar(Aluno aluno) {
		em.getTransaction().begin();
		em.persist(aluno);
		em.getTransaction().commit();
		return "Aluno incluído com sucesso!";
	}
}