package br.com.fiap.programa;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entidades.Curso;
import br.com.fiap.entidades.Escola;
import br.com.fiap.jdbc.JdbcCursoDao;
import br.com.fiap.jdbc.JdbcEscolaCursoDao;
import br.com.fiap.jdbc.JdbcEscolaDao;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class AppEscola {
	public static void main(String[] args) {
		String[] opcoes = {"Incluir Escola",
				"Incluir Curso",
				"Listar Escolas e Qtd Cursos"};
		String select = (String)JOptionPane.showInputDialog(null, 
				"Selecione a opções desejada", 
				"Menu",
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				opcoes,
				null);
		
		switch(select)
		{
			case "Incluir Escola":
				incluirEscola();
				break;
			case "Incluir Curso":
				incluirCurso();
				break;
			case "Listar Escolas e Qtd Cursos":
				listarEscolasComCursos();
				break;
		}
	}

	private static void incluirEscola() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			JdbcEscolaDao dao = (JdbcEscolaDao) context.getBean("jdbcEscolaDao");
			Escola escola = new Escola();
			escola.setDescricao("Fiap Tecnologia");
			escola.setDataString("01/02/1990");
			escola.setEndereco("Av Paulista");
			dao.incluirEscola(escola);
			JOptionPane.showMessageDialog(null, "Escola incluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void incluirCurso() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();
			Escola escola = escolas.get(0);
			JdbcCursoDao dao = (JdbcCursoDao) context.getBean("jdbcCursoDao");
			Curso curso = new Curso();
			curso.setDescricao("MBA Full Stack");
			curso.setEscola(escola);
			dao.incluirCurso(curso);
			JOptionPane.showMessageDialog(null, "Escola incluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void listarEscolasComCursos() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<EscolaCursoViewModel> escolas = ((JdbcEscolaCursoDao) context.getBean("jdbcEscolaCursoDao"))
					.listarEscolasComCursos();
			for (EscolaCursoViewModel vm : escolas) {
				System.out.println("Escola: " + vm.getDescricao());
				System.out.println("Num. Cursos: " + vm.getNumCursos());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
