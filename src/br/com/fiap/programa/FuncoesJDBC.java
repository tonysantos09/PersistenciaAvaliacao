package br.com.fiap.programa;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import br.com.fiap.entidades.Aluno;
import br.com.fiap.entidades.Curso;
import br.com.fiap.entidades.Escola;
import br.com.fiap.entidades.Matricula;
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

public class FuncoesJDBC {

	 static void incluirEscola() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		JdbcEscolaDao dao = (JdbcEscolaDao) context.getBean("jdbcEscolaDao");

		Escola escola = new Escola();
		escola.setDescricao(JOptionPane.showInputDialog("Descrição da escola"));
		escola.setDataString(JOptionPane.showInputDialog("Data de Fundação"));
		escola.setEndereco(JOptionPane.showInputDialog("Endereço da escola"));

		dao.incluirEscola(escola);
		JOptionPane.showMessageDialog(null, "Escola incluída com sucesso");
	}

	 static void incluirCurso() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolas();

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

		JdbcCursoDao dao = (JdbcCursoDao) context.getBean("jdbcCursoDao");

		Curso curso = new Curso();
		curso.setDescricao(JOptionPane.showInputDialog("Descrição do curso"));
		curso.setEscola(escola);

		dao.incluirCurso(curso);
		JOptionPane.showMessageDialog(null, "Curso incluído com sucesso");
	}

	 static void incluirAluno() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		JdbcAlunoDao dao = (JdbcAlunoDao) context.getBean("jdbcAlunoDao");

		Aluno aluno = new Aluno();
		aluno.setNome(JOptionPane.showInputDialog("Nome do aluno"));

		dao.incluirAluno(aluno);
		JOptionPane.showMessageDialog(null, "Aluno incluído com sucesso");
	}	

	 static void matricularCurso() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");

		List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarAlunos();

		Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno", "Alunos",
				JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);

		// vai trazer apenas escolas com cursos criados
		List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

		List<Curso> cursos = ((JdbcCursoDao) context.getBean("jdbcCursoDao")).listarCursosPorEscola(escola.getId());

		Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione o curso", "Cursos",
				JOptionPane.INFORMATION_MESSAGE, null, cursos.toArray(), null);

		JdbcMatriculaDao dao = (JdbcMatriculaDao) context.getBean("jdbcMatriculaDao");

		Matricula matricula = new Matricula();
		matricula.setCurso(curso);
		matricula.setAluno(aluno);

		try {
			dao.incluirMatricula(matricula);
			JOptionPane.showMessageDialog(null, "Matricula efetuada com sucesso");
		} catch (DataAccessException e) {
			if (e.getCause() instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException)
				JOptionPane.showMessageDialog(null, "Erro! Verifique se o aluno já está matriculado no curso.");
			else
				throw e;
		}
	}

	 static void incluirNota() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
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
	}

	static void listarEscolasComCursos() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		List<EscolaCursoViewModel> escolas = ((JdbcEscolaCursoDao) context.getBean("jdbcEscolaCursoDao"))
				.listarEscolasComCursos();
		
		Object[] cols = {
		    "Escola","Num. Cursos"
		};
		
		Object[][] rows = new Object[escolas.size()][];

		for (EscolaCursoViewModel vm : escolas) {
			Object[] row = new Object[2];
			row[0] = vm.getDescricao();
			row[1] = vm.getNumCursos();
			
			rows[escolas.indexOf(vm)] = row;		
		}
		
		JTable table = new JTable(rows, cols);
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}

	static void listarCursosComAlunos() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		List<Escola> escolas = ((JdbcEscolaDao) context.getBean("jdbcEscolaDao")).listarEscolasComCursos();

		Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione a escola", "Escolas",
				JOptionPane.INFORMATION_MESSAGE, null, escolas.toArray(), null);

		List<CursoAlunoViewModel> cursos = ((JdbcCursoAlunoDao) context.getBean("jdbcCursoAlunoDao"))
				.listarCursosQtdAluno(escola.getId());
		
		Object[] cols = {
		    "Curso","Num. Alunos"
		};

		Object[][] rows = new Object[cursos.size()][];

		for (CursoAlunoViewModel vm : cursos) {
			Object[] row = new Object[2];
			row[0] = vm.getDescricao();
			row[1] = vm.getNumAlunos();
			
			rows[cursos.indexOf(vm)] = row;	
		}		

		JTable table = new JTable(rows, cols);
		JOptionPane.showMessageDialog(null, new JScrollPane(table));			
	}

	 static void listarAlunosDeCurso() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
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
		
		Object[] cols = {
			    "Escola","Curso", "Aluno", "Nota"
			};
		
		Object[][] rows = new Object[alunos.size()][];

		for (AlunoCursoViewModel vm : alunos) {
			Object[] row = new Object[4];
			row[0] = vm.getEscola();
			row[1] = vm.getDescricao();
			row[2] = vm.getNomealuno();
			
			if (select == "Sim")
				row[3] = vm.getNota();
			else
				row[3] = "";
			
			rows[alunos.indexOf(vm)] = row;	
		}		
	
		JTable table = new JTable(rows, cols);
		JOptionPane.showMessageDialog(null, new JScrollPane(table));			
		
	}

	 static void listarAlunoCursos() throws BeansException, Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");

		List<Aluno> alunos = ((JdbcAlunoDao) context.getBean("jdbcAlunoDao")).listarAlunos();

		Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione o aluno", "Alunos",
				JOptionPane.INFORMATION_MESSAGE, null, alunos.toArray(), null);
		
		String[] optNota = { "Sim", "Não" };

		String select = (String) JOptionPane.showInputDialog(null, "Deseja visualizar nota?", "Menu",
				JOptionPane.INFORMATION_MESSAGE, null, optNota, null);
		
		List<AlunoCursoViewModel> cursos = ((JdbcAlunoCursoDao) context.getBean("jdbcAlunoCursoDao"))
				.listarAlunoCursos(aluno.getId());
		
		Object[] cols = {
			    "Aluno","Curso", "Nota"
			};
		
		Object[][] rows = new Object[cursos.size()][];

		for (AlunoCursoViewModel vm : cursos) {
			Object[] row = new Object[3];
			row[0] = vm.getNomealuno();
			row[1] = vm.getDescricao();
			
			if (select == "Sim")
				row[2] = vm.getNota();
			else
				row[2] = "";
			
			rows[cursos.indexOf(vm)] = row;	
		}		
	
		JTable table = new JTable(rows, cols);
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}
}
