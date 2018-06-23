package br.com.fiap.programa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entidades.Aluno;
import br.com.fiap.entidades.Curso;
import br.com.fiap.entidades.Escola;
import br.com.fiap.entidades.Matricula;
import br.com.fiap.helper.EscolaHelper;
import br.com.fiap.jdbc.JdbcAlunoCursoDao;
import br.com.fiap.jdbc.JdbcAlunoDao;
import br.com.fiap.jdbc.JdbcCursoAlunoDao;
import br.com.fiap.jdbc.JdbcCursoDao;
import br.com.fiap.jdbc.JdbcEscolaCursoDao;
import br.com.fiap.jdbc.JdbcEscolaDao;
import br.com.fiap.jdbc.JdbcMatriculaDao;
import br.com.fiap.viewmodel.AlunoCursoViewModel;
import br.com.fiap.viewmodel.CursoAlunoViewModel;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class AppEscola {
	public static void main(String[] args) {

		int opcao = JOptionPane.YES_OPTION;

		while (opcao == JOptionPane.YES_OPTION) {
			String[] opcoes = { "Incluir Escola", "Incluir Curso", "Incluir Aluno", "Incluir Nota",
					"Matricula em Curso", "Listar Escolas e Qtd Cursos", "Listar Cursos e Qtd Alunos",
					"Listar Alunos de Curso", "Listar Cursos de Aluno" };

			String select = (String) JOptionPane.showInputDialog(null, "Selecione a opções desejada", "Menu",
					JOptionPane.INFORMATION_MESSAGE, null, opcoes, null);

			// Vai tentar fazer as operações e se der erro mostra mensagem
			try {
				switch (select) {
				case "Incluir Escola":
					incluirEscolaJPA();
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
				case "Listar Cursos e Qtd Alunos":
					listarCursosComAlunos();
					break;
				case "Listar Alunos de Curso":
					listarAlunosDeCurso();
					break;
				case "Listar Cursos de Aluno":
					listarAlunoCursos();
					break;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro, tente novamente",
						JOptionPane.ERROR_MESSAGE);
			}

			// Pergunta se quer fazer outra operação
			opcao = JOptionPane.showConfirmDialog(null, "Deseja fazer outra operação?", "Confirmação",
					JOptionPane.YES_NO_OPTION);
		}

	}

	private static void incluirEscolaJPA() throws Exception {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
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

	private static void incluirEscola() throws Exception {
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
			throw e;
		}
	}

	private static void incluirCurso() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			JdbcCursoDao dao = (JdbcCursoDao) context.getBean("jdbcCursoDao");

			Curso curso = new Curso();
			curso.setDescricao(JOptionPane.showInputDialog("Descrição do curso"));
			curso.setEscola(escola);

			dao.incluirCurso(curso);
			JOptionPane.showMessageDialog(null, "Curso incluído com sucesso");

		} catch (Exception e) {
			throw e;
		}
	}

	private static void incluirAluno() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			JdbcAlunoDao dao = (JdbcAlunoDao) context.getBean("jdbcAlunoDao");

			Aluno aluno = new Aluno();
			aluno.setNome(JOptionPane.showInputDialog("Nome do aluno"));

			dao.incluirAluno(aluno);
			JOptionPane.showMessageDialog(null, "Aluno incluído com sucesso");

		} catch (Exception e) {
			throw e;
		}
	}

	private static void matricularCurso() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");

			List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarAlunos();

			Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno", "Alunos",
					JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);

			// vai trazer apenas escolas com cursos criados
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursosMatricula(aluno.getId());

			Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Cursos",
					JOptionPane.INFORMATION_MESSAGE, null, cursos.toArray(), null);

			JdbcMatriculaDao dao = (JdbcMatriculaDao) context.getBean("jdbcMatriculaDao");

			Matricula matricula = new Matricula();
			matricula.setCurso(curso);
			matricula.setAluno(aluno);

			dao.incluirMatricula(matricula);
			JOptionPane.showMessageDialog(null, "Matricula efetuada com sucesso");

		} catch (Exception e) {
			throw e;
		}
	}

	private static void incluirNota() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursosComAlunos(escola.getId());

			Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Cursos",
					JOptionPane.INFORMATION_MESSAGE, null, cursos.toArray(), null);

			List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarCursoAlunos(curso.getId());

			Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno", "Alunos",
					JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);

			JdbcMatriculaDao dao = (JdbcMatriculaDao) context.getBean("jdbcMatriculaDao");

			Matricula matricula = new Matricula();

			String nota = JOptionPane.showInputDialog("Nota do aluno");

			double valor = Double.parseDouble(nota.replace(',', '.'));

			matricula.setNota(valor);
			matricula.setAluno(aluno);
			matricula.setCurso(curso);

			dao.incluirNota(matricula);
			JOptionPane.showMessageDialog(null, "Nota incluída com sucesso");

		} catch (Exception e) {
			throw e;
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
				System.out.println("Num. Alunos: " + vm.getNumAlunos());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private static void listarCursosComAlunos() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			List<CursoAlunoViewModel> cursos = ((JdbcCursoAlunoDao) context.getBean("jdbcCursoAlunoDao"))
					.listarCursosQtdAluno(escola.getId());
			for (CursoAlunoViewModel vm : cursos) {
				System.out.println("Curso: " + vm.getDescricao());
				System.out.println("Num. Alunos: " + vm.getNumAlunos());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private static void listarAlunosDeCurso() throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

			Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
					JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

			List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursos(escola.getId());

			Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Cursos",
					JOptionPane.INFORMATION_MESSAGE, null, cursos.toArray(), null);

			String[] optNota = { "Sim", "Não" };

			String select = (String) JOptionPane.showInputDialog(null, "Deseja visualizar nota?", "Menu",
					JOptionPane.INFORMATION_MESSAGE, null, optNota, null);

			List<AlunoCursoViewModel> alunos = ((JdbcAlunoCursoDao) context.getBean("jdbcAlunoCursoDao"))
					.listarAlunosDeCurso(curso.getId());
			System.out.println("Escola: " + escola.getDescricao());
			System.out.println("Curso: " + curso.getDescricao());

			if (select == "Não") {
				for (AlunoCursoViewModel vm : alunos) {
					System.out.println("Nome Aluno: " + vm.getNomealuno());
				}
			} else {
				for (AlunoCursoViewModel vm : alunos) {
					System.out.println("Nome Aluno: " + vm.getNomealuno() + " - Nota: " + vm.getNota());
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	private static void listarAlunoCursos() throws BeansException, Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");

			List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarAlunos();

			Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno", "Alunos",
					JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);
			
			String[] optNota = { "Sim", "Não" };

			String select = (String) JOptionPane.showInputDialog(null, "Deseja visualizar nota?", "Menu",
					JOptionPane.INFORMATION_MESSAGE, null, optNota, null);
			
			List<AlunoCursoViewModel> cursos = ((JdbcAlunoCursoDao) context.getBean("jdbcAlunoCursoDao"))
					.listarAlunoCursos(aluno.getId());
			System.out.println("Nome Aluno: " + aluno.getNome());
			if (select == "Não") {
				for (AlunoCursoViewModel vm : cursos) {
					System.out.println("Curso: " + vm.getDescricao());
				}
			} else {
				for (AlunoCursoViewModel vm : cursos) {
					System.out.println("Curso: " + vm.getDescricao() + " - Nota: " + vm.getNota());
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
}
