package br.com.fintech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fintech.dao.interfaces.GenericDao;
import br.com.fintech.dao.jdbc.DBManager;
import br.com.fintech.exception.UserNotFoundException;
import br.com.fintech.model.Expense;

public class ExpenseDAO implements GenericDao<Expense>{

private Connection conexao;
	
	@Override
	public void cadastrar(Expense expenses) {
		PreparedStatement ps = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " INSERT INTO"+ 
						   " EXPENSES"+ 
						   " (FK_USER_ID, EXPENSE_NAME, EXPENSE_VALUE)"+ 
						   " VALUES (?,?,?)";
			
			ps = conexao.prepareStatement(query);
			
			ps.setInt(1, expenses.getUser());
			ps.setString(2, expenses.getName());
			ps.setFloat(3, expenses.getValue());
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();
			} catch(SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public List<Expense> listar(){
		List<Expense> list = new ArrayList<Expense>();
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			
			conexao = DBManager.connector();
			
			String query = "SELECT * FROM EXPENSES ORDER BY EXPENSE_NAME";
			
			ps = conexao.prepareStatement(query);
			
			reSet = ps.executeQuery();
			
			while(reSet.next()) {
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("EXPENSE_NAME");
				float value = reSet.getFloat("EXPENSE_VALUE");
				
				Expense expenses = new Expense(user, name, value);
				
				list.add(expenses);
			}
			
		} catch(SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();	
			} catch(SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public List<Expense> getByEmail(String email) throws UserNotFoundException {
		List<Expense> list = new ArrayList<Expense>();	
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " SELECT " +
				" * "+
				" FROM "+
				" 	EXPENSES"+
				" LEFT JOIN "+
				" 	USERS ON EXPENSES.FK_USER_ID = USERS.PK_USER_ID "+
				" WHERE "+
				" 	USERS.USER_EMAIL = ?";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, email);
			
			reSet = ps.executeQuery();
			
			while (reSet.next()) {
				int id = reSet.getInt("PK_EXPENSE_ID");
				int user = reSet.getInt("FK_USER_ID");
				String name = reSet.getString("EXPENSE_NAME");
				float value = reSet.getFloat("EXPENSE_VALUE");
				LocalDateTime creationDate = reSet.getTimestamp("EXPENSE_CREATION_DATE").toLocalDateTime();

				Expense expenses = new Expense(user, name, value);
				expenses.setDt_created(creationDate);
				expenses.setId(id);
				
				list.add(expenses);
			}
			
		} catch (SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public void delete(String id) {
		PreparedStatement ps = null;
		
		try {
			
			conexao = DBManager.connector();
			
			String query = " DELETE"+ 
					" FROM"+
					" 	EXPENSES"+
					" WHERE"+
					" 	PK_EXPENSE_ID = ?";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, id);
			
			ps.execute();

		} catch(SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public Expense getById(String id){
		Expense expense = null;
		
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			
			conexao = DBManager.connector();
			
			String query = "SELECT * FROM EXPENSES WHERE PK_EXPENSE_ID = ? ORDER BY EXPENSE_CREATION_DATE DESC";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, id);
			
			reSet = ps.executeQuery();
			
			if (reSet.next()) {
				int expenId = reSet.getInt("PK_EXPENSE_ID");
				int user = reSet.getInt("FK_USER_ID");
				String name = reSet.getString("EXPENSE_NAME");
				float value = reSet.getFloat("EXPENSE_VALUE");
				LocalDateTime creationDate = reSet.getTimestamp("EXPENSE_CREATION_DATE").toLocalDateTime();
			
				expense = new Expense(user, name, value);
				
				expense.setDt_created(creationDate);
				expense.setId(expenId);

			}

		} catch(SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
		return expense;
	}
	
	public void update(int userId, String id, String name, String value) {
		PreparedStatement ps = null;
		
		try {
			
			conexao = DBManager.connector();
			
			String query = " UPDATE EXPENSES"+
					" SET"+
					"	FK_USER_ID = ?,"+
					"	EXPENSE_NAME = ?,"+
					"	EXPENSE_VALUE = ?"+
					" WHERE PK_EXPENSE_ID = ?";
			
			ps = conexao.prepareStatement(query);
			
			ps.setInt(1, userId);
			ps.setString(2, name);
			ps.setFloat(3, Float.parseFloat(value));
			ps.setString(4, id);
			
			ps.execute();

		} catch(SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Não foi possivel encerrar a conexão com o banco." + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
}
