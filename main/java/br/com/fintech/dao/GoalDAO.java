package br.com.fintech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fintech.dao.interfaces.GenericDao;
import br.com.fintech.dao.jdbc.DBManager;
import br.com.fintech.model.Goal;

public class GoalDAO implements GenericDao<Goal> {
	private Connection conexao;

	@Override
	public void cadastrar(Goal goal) {

		PreparedStatement ps = null;

		try {
			conexao = DBManager.connector();

			String query = " INSERT INTO"+ 
			" GOALS" + 
			" (FK_user_id, goal_name, goal_deadline, goal_completion)"+ 
			" VALUES (?, ?, ?, ?)";

			ps = conexao.prepareStatement(query);

			ps.setInt(1, goal.getUser());
			ps.setString(2, goal.getName());
			ps.setString(3, goal.getDeadline());
			ps.setFloat(4, goal.getCompletion());

			ps.execute();

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
	}

	@Override
	public List<Goal> listar() {
		List<Goal> list = new ArrayList<Goal>();
		PreparedStatement ps = null;
		ResultSet reSet = null;

		try {
			conexao = DBManager.connector();

			String query = "SELECT * FROM GOALS ORDER BY goal_name ASC";
			ps = conexao.prepareStatement(query);

			reSet = ps.executeQuery();

			while (reSet.next()) {
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("goal_name");
				String deadLine = reSet.getString("goal_deadline");
				Float completion = reSet.getFloat("goal_completion");

				Goal goal = new Goal(user, name, deadLine, completion);

				list.add(goal);
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
	
	public List<Goal> getByEmail(String email){
		List<Goal> list = new ArrayList<Goal>();
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " SELECT"+
					"	*"+
					" FROM"+
					"	GOALS"+
					" LEFT JOIN"+
					"	USERS ON GOALS.FK_USER_ID = USERS.PK_USER_ID"+
					" WHERE"+ 
					"	USERS.USER_EMAIL = ?";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, email);
			
			reSet = ps.executeQuery();
			
			while (reSet.next()) {
				int id = reSet.getInt("PK_goal_id");
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("goal_name");
				String deadLine = reSet.getString("goal_deadline");
				float completion = reSet.getFloat("goal_completion");
				
				Goal goal = new Goal(user, name, deadLine, completion);
				goal.setId(id);
				
				list.add(goal);
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
					" 	GOALS"+
					" WHERE"+
					" 	PK_GOAL_ID = ?";
			
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
	
	public Goal getById(String id) {
		Goal goal = null;
		
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = "SELECT * FROM GOALS WHERE PK_GOAL_ID = ? ORDER BY GOAL_CREATION_DATE DESC";
			
			ps = conexao.prepareStatement(query);
			ps.setString(1, id);
			
			reSet = ps.executeQuery();
			
			if (reSet.next()) {
				int goalId = reSet.getInt("PK_goal_id");
				int user = reSet.getInt("FK_user_id");
				String name = reSet.getString("goal_name");
				String deadLine = reSet.getString("goal_deadline");
				float completion = reSet.getFloat("goal_completion");
				
				goal = new Goal(user, name, deadLine, completion);
				goal.setId(goalId);
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
		return goal;
	}
	
	public void update(int userId, String id, String name, String deadLine, String completion) {
		PreparedStatement ps = null;
		
		try {
			conexao = DBManager.connector();
			
			String query = " UPDATE GOALS"+
			" SET"+
			"	FK_USER_ID = ?,"+
			"	GOAL_NAME = ?,"+ 
			"	GOAL_DEADLINE = ?,"+
			"	GOAL_COMPLETION = ?"+
			" WHERE PK_GOAL_ID = ?";
				
			ps = conexao.prepareStatement(query);
			
			ps.setInt(1, userId);
			ps.setString(2, name);
			ps.setString(3, deadLine);
			ps.setFloat(4, Float.parseFloat(completion));
			ps.setInt(5, Integer.parseInt(id));
			
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
