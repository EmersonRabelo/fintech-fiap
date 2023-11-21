package br.com.fintech.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public static Connection connector () {
		
		Connection conexao = null;
		
		String port = "1521";
		String sid = "orcl";
		String url = "jdbc:oracle:thin:@oracle.fiap.com.br:"+port+":"+sid;
		String user = "RM97196";
		String pwd = "130898";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); // OR  oracle.jdbc.driver.OracleDriver
			conexao = DriverManager.getConnection(url, user, pwd);

		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado: "+ e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("url, user ou password inválidos: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Um erro não esperado ocorreu: " + e.getMessage());
			e.printStackTrace();
		}
		
		return conexao;
	}
	
}
