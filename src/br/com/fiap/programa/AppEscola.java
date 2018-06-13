package br.com.fiap.programa;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entidades.Aluno;
import br.com.fiap.entidades.Curso;
import br.com.fiap.entidades.Escola;
import br.com.fiap.entidades.Matricula;
import br.com.fiap.entidades.Nota;
import br.com.fiap.jdbc.JdbcAlunoDao;
import br.com.fiap.jdbc.JdbcCursoDao;
import br.com.fiap.jdbc.JdbcEscolaCursoDao;
import br.com.fiap.jdbc.JdbcEscolaDao;
import br.com.fiap.jdbc.JdbcMatriculaDao;
import br.com.fiap.jdbc.JdbcNotaDao;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class AppEscola {
	public static void main(String[] args) {
		String[] opcoes = {"Incluir Escola",
				"Incluir Curso",
				"Incluir Aluno",
				"Incluir Nota",
				"Matricula em Curso",
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
			case "Incluir Aluno":
				incluirAluno();
				break;
			case "Incluir Nota":
				incluirNota();
				break;
			case "Matricula em Curso":
				matricularCurso();
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
			escola.setDescricao(JOptionPane.showInputDialog("Descrição da escola"));
			escola.setDataString(JOptionPane.showInputDialog("Data de Fundação"));
			escola.setEndereco(JOptionPane.showInputDialog("Endereço da escola"));

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

			Escola escola = (Escola) JOptionPane.showInputDialog(null, 
					"Selecione a escola", 
					"Escolas",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					escolas.toArray(),
					null);

			JdbcCursoDao dao = (JdbcCursoDao) context.getBean("jdbcCursoDao");

			Curso curso = new Curso();
			curso.setDescricao(JOptionPane.showInputDialog("Descrição do curso"));
			curso.setEscola(escola);

			dao.incluirCurso(curso);
			JOptionPane.showMessageDialog(null, "Curso incluído com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void incluirAluno() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, 
					"Selecione a escola", 
					"Escolas",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					escolas.toArray(),
					null);
			
			//List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursos(escola.getId());

			//Curso curso = (Curso) JOptionPane.showInputDialog(null, 
			//		"Selecione o curso", 
			//		"Cursos",
			//		JOptionPane.INFORMATION_MESSAGE, 
			//		null, 
			//		cursos.toArray(),
			//		null);

			JdbcAlunoDao dao = (JdbcAlunoDao) context.getBean("jdbcAlunoDao");

			Aluno aluno = new Aluno();
			aluno.setNome(JOptionPane.showInputDialog("Nome do aluno"));
			aluno.setEscola(escola);

			dao.incluirAluno(aluno);
			JOptionPane.showMessageDialog(null, "Aluno incluído com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void matricularCurso() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, 
					"Selecione a escola", 
					"Escolas",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					escolas.toArray(),
					null);
			
			List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursos(escola.getId());

			Curso curso = (Curso) JOptionPane.showInputDialog(null, 
					"Selecione o curso", 
					"Cursos",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					cursos.toArray(),
					null);
			
			List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarAlunos(escola.getId());

			Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, 
					"Selecione o aluno", 
					"Alunos",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					alunos.toArray(),
					null);

			JdbcMatriculaDao dao = (JdbcMatriculaDao) context.getBean("jdbcMatriculaDao");

			Matricula matricula = new Matricula();
			matricula.setCurso(curso);
			matricula.setAluno(aluno);

			dao.incluirMatricula(matricula);
			JOptionPane.showMessageDialog(null, "Matricula efetuada com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void incluirNota() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, 
					"Selecione a escola", 
					"Escolas",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					escolas.toArray(),
					null);
			
			List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursos(escola.getId());

			Curso curso = (Curso) JOptionPane.showInputDialog(null, 
					"Selecione o curso", 
					"Cursos",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					cursos.toArray(),
					null);
			
			List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarCursoAlunos(curso.getId());

			Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, 
					"Selecione o aluno", 
					"Alunos",
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					alunos.toArray(),
					null);

			JdbcNotaDao dao = (JdbcNotaDao) context.getBean("jdbcNotaDao");

			Nota nota = new Nota();
			nota.setNota(Double.parseDouble(JOptionPane.showInputDialog("Nota do aluno")));
			nota.setCurso(curso);
			nota.setAluno(aluno);

			dao.incluirNota(nota);
			JOptionPane.showMessageDialog(null, "Nota incluída com sucesso");

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
