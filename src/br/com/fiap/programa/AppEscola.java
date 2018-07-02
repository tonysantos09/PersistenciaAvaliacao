package br.com.fiap.programa;
import javax.swing.JOptionPane;

import br.com.fiap.programa.FuncoesJDBC;
import br.com.fiap.programa.FuncoesJPA;

public class AppEscola {
	
	public static void main(String[] args) {

		int opcao = JOptionPane.YES_OPTION;

		while (opcao == JOptionPane.YES_OPTION) {
			String[] opcoes = { "Incluir Escola", "Incluir Curso", "Incluir Aluno", "Incluir Nota",
					"Matricula em Curso", "Listar Escolas e Qtd Cursos", "Listar Cursos e Qtd Alunos",
					"Listar Alunos de Curso", "Listar Cursos de Aluno" };

			String select = (String) JOptionPane.showInputDialog(null, "Selecione a op��es desejada", "Menu",
					JOptionPane.INFORMATION_MESSAGE, null, opcoes, null);

			if (select == null) {
				//se n�o escolher nada sai do programa
				opcao = JOptionPane.NO_OPTION;
				continue;
			}
				
			// Vai tentar fazer as opera��es e se der erro mostra mensagem
			try {
				switch (select) {
					case "Incluir Escola":
						FuncoesJPA.incluirEscola();
						break;
					case "Incluir Curso":
						FuncoesJPA.incluirCurso();
						break;
					case "Incluir Aluno":
						FuncoesJPA.incluirAluno();
						break;
					case "Incluir Nota":
						FuncoesJDBC.incluirNota();
						break;
					case "Matricula em Curso":
						FuncoesJDBC.matricularCurso();
						break;
					case "Listar Escolas e Qtd Cursos":
						FuncoesJDBC.listarEscolasComCursos();
						break;
					case "Listar Cursos e Qtd Alunos":
						FuncoesJDBC.listarCursosComAlunos();
						break;
					case "Listar Alunos de Curso":
						FuncoesJDBC.listarAlunosDeCurso();
						break;
					case "Listar Cursos de Aluno":
						FuncoesJDBC.listarAlunoCursos();
						break;
				}
				
				// Pergunta se quer fazer outra opera��o
				opcao = JOptionPane.showConfirmDialog(null, "Deseja fazer outra opera��o?", "Confirma��o",
						JOptionPane.YES_NO_OPTION);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao realizar a opera��o. Tente novamente.");
			}

			
		}

	}

}
