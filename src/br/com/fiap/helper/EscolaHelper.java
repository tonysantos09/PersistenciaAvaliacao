package br.com.fiap.helper;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import br.com.fiap.entidades.Escola;

public class EscolaHelper {
	private EntityManager em;

	public EscolaHelper(EntityManager em) {
		this.em = em;
	}

	public String salvar(Escola escola) {
		em.getTransaction().begin();
		em.persist(escola);
		em.getTransaction().commit();
		return "Evento incluído com sucesso!";
	}
	
	public List<Escola> listarEscolas() {
		TypedQuery<Escola> query = em.createQuery("Select e from Escola e", Escola.class);
		return query.getResultList();
	}
}