package app;

import java.util.List;

import dao.AlunosDAO;
import dao.DAO;
import model.Aluno;

public class Aplicacao {
	
	public static void main(String[] args) throws Exception {
		
		AlunosDAO alunosDAO = new AlunosDAO();
		
		System.out.println("\n\n==== Inserir aluno === ");
		Aluno aluno = new Aluno(11, "pablo", "pablo", 'M');
		if(alunosDAO.insert(aluno) == true) {
			System.out.println("Inserção com sucesso -> " + aluno.toString());
		}
		
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Aluno (" + aluno.getLogin() + "): " + alunosDAO.autenticar("pablo", "pablo"));
			
		System.out.println("\n\n==== Mostrar alunos do sexo masculino === ");
		List<Aluno> alunos = alunosDAO.getSexoMasculino();
		for (Aluno a: alunos) {
			System.out.println(a.toString());
		}

		System.out.println("\n\n==== Atualizar senha (código (" + aluno.getCodigo() + ") === ");
		aluno.setSenha(DAO.toMD5("pablo"));
		alunosDAO.update(aluno);
		
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Aluno (" + aluno.getLogin() + "): " + alunosDAO.autenticar("pablo", DAO.toMD5("pablo")));		
		
		System.out.println("\n\n==== Invadir usando SQL Injection ===");
		System.out.println("Aluno (" + aluno.getLogin() + "): " + alunosDAO.autenticar("pablo", "x' OR 'x' LIKE 'x"));

		System.out.println("\n\n==== Mostrar alunos ordenados por código === ");
		alunos = alunosDAO.getOrderByCodigo();
		for (Aluno a: alunos) {
			System.out.println(a.toString());
		}
		
		System.out.println("\n\n==== Excluir aluno (código " + aluno.getCodigo() + ") === ");
		alunosDAO.delete(aluno.getCodigo());
		
		System.out.println("\n\n==== Mostrar alunos ordenados por login === ");
		alunos = alunosDAO.getOrderByLogin();
		for (Aluno a: alunos) {
			System.out.println(a.toString());
		}
	}
}
