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
import br.com.fintech.model.MonthlyIncome;

public class MonthlyIncomeDAO implements GenericDao<MonthlyIncome>{

private Connection conexao;
	
	@Override
	public void cadastrar(MonthlyIncome monthlyIncome) {
		
		PreparedStatement ps = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " INSERT INTO"+ 
			" Monthly_Income" + 
			" (FK_user_id, income_name, income_value)"+
			" VALUES (?, ?, ?)";
			
			ps = conexao.prepareStatement(query);
			
			ps.setInt(1, monthlyIncome.getUser());
			ps.setString(2, monthlyIncome.getName());
			ps.setFloat(3, monthlyIncome.getValue());
			
			ps.execute();
			
			
		} catch (SQLException e) {
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
	public List<MonthlyIncome> listar() {
		List<MonthlyIncome> list = new ArrayList<MonthlyIncome>();	
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " SELECT * FROM Monthly_Income";
			
			ps = conexao.prepareStatement(query);
			reSet = ps.executeQuery();
			
			
			while (reSet.next()) {
				int id = reSet.getInt("PK_INCOME_ID");
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("income_name");
				float value = reSet.getFloat("income_value");
				LocalDateTime creationDate = reSet.getTimestamp("income_creation_date").toLocalDateTime();
			
				MonthlyIncome monthlyIncome = new MonthlyIncome(user, name, value);
				
				monthlyIncome.setId(id);
				monthlyIncome.setCreationDate(creationDate);		
				
				list.add(monthlyIncome);
			
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

	public List<MonthlyIncome> getByEmail(String email) throws UserNotFoundException {
		List<MonthlyIncome> list = new ArrayList<MonthlyIncome>();	
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " SELECT " +
				" * "+
				" FROM "+
				" 	MONTHLY_INCOME "+
				" LEFT JOIN "+
				" 	USERS on MONTHLY_INCOME.FK_USER_ID = USERS.PK_USER_ID "+
				" WHERE "+
				" 	USERS.USER_EMAIL = ?";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, email);
			
			reSet = ps.executeQuery();
			
			while (reSet.next()) {
				int id = reSet.getInt("PK_INCOME_ID");
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("income_name");
				float value = reSet.getFloat("income_value");
				LocalDateTime creationDate = reSet.getTimestamp("income_creation_date").toLocalDateTime();
			
				MonthlyIncome monthlyIncome = new MonthlyIncome(user, name, value);
				
				monthlyIncome.setId(id);
				monthlyIncome.setCreationDate(creationDate);
				
				list.add(monthlyIncome);
			
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
					" 	Monthly_Income"+
					" WHERE"+
					" 	PK_INCOME_ID = ?";
			
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
	
	public MonthlyIncome getById(String id){
		MonthlyIncome monthlyInc = null;
		
		PreparedStatement ps = null;
		ResultSet reSet = null;
		

		try {
			
			conexao = DBManager.connector();
			
			String query = "SELECT * FROM MONTHLY_INCOME WHERE PK_INCOME_ID = ? ORDER BY INCOME_CREATION_DATE DESC";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, id);
			
			reSet = ps.executeQuery();
			
			if (reSet.next()) {
				int incId = reSet.getInt("PK_INCOME_ID");
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("income_name");
				float value = reSet.getFloat("income_value");
				LocalDateTime creationDate = reSet.getTimestamp("income_creation_date").toLocalDateTime();
			
				monthlyInc = new MonthlyIncome(user, name, value);
				
				monthlyInc.setId(incId);
				monthlyInc.setCreationDate(creationDate);

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
		return monthlyInc;
	}
	
	public void update(int userId, String id, String name, String value) {
		PreparedStatement ps = null;
		
		try {
			
			conexao = DBManager.connector();
			
			String query = " UPDATE Monthly_Income"+
					" SET"+
					"	FK_user_id = ?,"+
					"	income_name = ?,"+
					"	income_value = ?"+
					" WHERE PK_income_id = ?";
			
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
