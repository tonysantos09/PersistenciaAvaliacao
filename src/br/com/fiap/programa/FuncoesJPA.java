package br.com.fiap.programa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entidades.Aluno;
import br.com.fiap.entidades.Curso;
import br.com.fiap.entidades.Escola;
import br.com.fiap.entidades.Matricula;
import br.com.fiap.helper.AlunoHelper;
import br.com.fiap.helper.CursoHelper;
import br.com.fiap.helper.EscolaHelper;
import br.com.fiap.jdbc.JdbcAlunoDao;
import br.com.fiap.jdbc.JdbcCursoDao;
import br.com.fiap.jdbc.JdbcEscolaDao;
import br.com.fiap.jdbc.JdbcMatriculaDao;

public class FuncoesJPA {

	 static void incluirEscola() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
			EntityManagerFactory emf = (EntityManagerFactory) context.getBean("myEmf");
			EntityManager em = emf.createEntityManager();
			EscolaHelper helper = new EscolaHelper(em);

			Escola escola = new Escola();
			escola.setDescricao(JOptionPane.showInputDialog("Descrição da escola"));
			escola.setDataString(JOptionPane.showInputDialog("Data de Fundação"));
			escola.setEndereco(JOptionPane.showInputDialog("Endereço da escola"));

			helper.salvar(escola);
			JOptionPane.showMessageDialog(null, "Escola incluída com sucesso");
		} catch (Exception e) {
			throw e;
		}
	}

	 static void incluirCurso() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
			EntityManagerFactory emf = (EntityManagerFactory) context.getBean("myEmf");
			EntityManager em = emf.createEntityManager();
			CursoHelper helperCurso = new CursoHelper(em);
			EscolaHelper helperEscola = new EscolaHelper(em);
			
			List<Escola> escolas = helperEscola.listarEscolas();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			Curso curso = new Curso();
			curso.setDescricao(JOptionPane.showInputDialog("Descrição do curso"));
			curso.setEscola(escola);

			helperCurso.salvar(curso);
			JOptionPane.showMessageDialog(null, "Curso incluído com sucesso");

		} catch (Exception e) {
			throw e;
		}
	}

	 static void incluirAluno() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
			EntityManagerFactory emf = (EntityManagerFactory) context.getBean("myEmf");
			EntityManager em = emf.createEntityManager();
			AlunoHelper helper = new AlunoHelper(em);

			Aluno aluno = new Aluno();
			aluno.setNome(JOptionPane.showInputDialog("Nome do aluno"));

			helper.salvar(aluno);
			JOptionPane.showMessageDialog(null, "Aluno incluído com sucesso");
		} catch (Exception e) {
			throw e;
		}
	}

}
