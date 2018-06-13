package br.com.fiap.entidades;

public class Matricula {
		private Curso curso;
		private Aluno aluno;
		
		public Curso getCurso() {
			return curso;
		}
		public void setCurso(Curso curso) {
			this.curso = curso;
		}
		public Aluno getAluno() {
			return aluno;
		}
		public void setAluno(Aluno aluno) {
			this.aluno = aluno;
		}
}
