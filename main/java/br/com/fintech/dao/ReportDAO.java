package br.com.fintech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fintech.dao.interfaces.GenericDao;
import br.com.fintech.dao.jdbc.DBManager;
import br.com.fintech.model.Report;

public class ReportDAO implements GenericDao<Report> {
	
	private Connection conexao;
	
	@Override
	public void cadastrar(Report entity) {
		
		
	}

	@Override
	public List<Report> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Report> generate(int id) {
		List<Report> list = new ArrayList<Report>();
		PreparedStatement ps = null;
		ResultSet reSet = null;
		
		
		try {
			
			conexao = DBManager.connector();
			
			String query = " SELECT"+
					"     MONTHLY_INCOME.FK_user_id,"+
					"     TO_CHAR(MONTHLY_INCOME.INCOME_CREATION_DATE, 'MM-YYYY') as expense_month,"+
					"     (SELECT COALESCE(SUM(DISTINCT EXPENSES.EXPENSE_VALUE), 0)"+
					"      FROM EXPENSES"+
					"      WHERE EXPENSES.FK_USER_ID = USERS.PK_USER_ID) AS total_expenses,"+
					"      (SELECT COALESCE(SUM(DISTINCT MONTHLY_INCOME.INCOME_VALUE), 0)"+
					"      FROM MONTHLY_INCOME"+
					"      WHERE MONTHLY_INCOME.FK_USER_ID = USERS.PK_USER_ID) AS total_incomes"+
					" FROM"+
					"     USERS"+
					" INNER JOIN"+
					"         MONTHLY_INCOME ON MONTHLY_INCOME.FK_USER_ID = USERS.PK_USER_ID"+
					" WHERE"+
					"     USERS.PK_USER_ID = ?"+
					" GROUP BY"+
					"     USERS.PK_USER_ID,"+
					"    TO_CHAR(MONTHLY_INCOME.INCOME_CREATION_DATE, 'MM-YYYY'),"+
					"    MONTHLY_INCOME.FK_user_id";
			
			ps = conexao.prepareStatement(query);
			ps.setInt(1, id);
			
			reSet = ps.executeQuery();
			
			while (reSet.next()) {
				int user = reSet.getInt("FK_user_id");
				String monthAndYear = reSet.getString("expense_month");
				float amountIncome = reSet.getFloat("total_incomes");
				float amountExpenses = reSet.getFloat("total_expenses");
				
				Report report = new Report(user, monthAndYear, amountIncome, amountExpenses);
				
				list.add(report);
				
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

}
