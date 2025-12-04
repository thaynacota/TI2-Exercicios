package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

public class AlunosDAO extends DAO {
	
	public AlunosDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	

	public boolean insert(Aluno aluno) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO Alunos (codigo, login, senha, sexo) "
				       + "VALUES ("+aluno.getCodigo()+ ", '" + aluno.getLogin() + "', '"  
				       + aluno.getSenha() + "', '" + aluno.getSexo() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}


	public Aluno get(int codigo) {
		Aluno aluno = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Alunos WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 aluno = new Aluno(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return aluno;
	}
	
	
	public List<Aluno> get() {
		return get("");
	}


	public List<Aluno> getOrderByCodigo() {
		return get("codigo");		
	}
	
	public List<Aluno> getOrderByLogin() {
		return get("login");		
	}
	
	public List<Aluno> getOrderBySexo() {
		return get("sexo");		
	}
	
	
	private List<Aluno> get(String orderBy) {	
	
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Alunos" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Aluno a = new Aluno(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            alunos.add(a);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return alunos;
	}


	public List<Aluno> getSexoMasculino() {
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Alunos WHERE sexo LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Aluno a = new Aluno(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            alunos.add(a);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return alunos;
	}
	

	public boolean update(Aluno aluno) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Alunos SET login = '" + aluno.getLogin() + "', senha = '"  
				       + aluno.getSenha() + "', sexo = '" + aluno.getSexo() + "'"
					   + " WHERE codigo = " + aluno.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	

	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM Alunos WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	

	public boolean autenticar(String login, String senha) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Alunos WHERE login LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}
