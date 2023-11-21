package br.com.fintech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.fintech.dao.interfaces.GenericDao;
import br.com.fintech.dao.jdbc.DBManager;
import br.com.fintech.exception.UserNotFoundException;
import br.com.fintech.model.User;
import br.com.fintech.utils.PasswordStorage;
import br.com.fintech.utils.PasswordStorage.CannotPerformOperationException;
import br.com.fintech.utils.PasswordStorage.InvalidHashException;

public class UserDAO implements GenericDao<User> {
	private Connection conexao;

	@Override
	public void cadastrar(User user) {
		PreparedStatement ps = null;

		try {
			conexao = DBManager.connector();

			String query = " INSERT INTO" + " USERS"
					+ "(user_name, user_email, user_password, user_birth_date, user_occupation)" + " VALUES"
					+ "(?, ?, ?, ?, ?)";

			ps = conexao.prepareStatement(query);

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPwd());
			ps.setTimestamp(4, Timestamp.valueOf(user.getDtBirth().atStartOfDay()));
			ps.setString(5, user.getOccupation());

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
	public List<User> listar() {
		List<User> list = new ArrayList<User>();
		PreparedStatement ps = null;
		ResultSet reSet = null;

		try {
			conexao = DBManager.connector();

			String query = "SELECT * FROM USERS ORDER BY USER_NAME ASC";
			ps = conexao.prepareStatement(query);

			reSet = ps.executeQuery();

			while (reSet.next()) {
				int id = reSet.getInt("pk_user_id");
				String name = reSet.getString("user_name");
				String email = reSet.getString("user_email");
				String pwd = reSet.getString("user_password");
				LocalDate dtBirth = reSet.getTimestamp("user_birth_date").toLocalDateTime().toLocalDate();
				String occupation = reSet.getString("user_occupation");

				User user = new User(name, email, pwd, dtBirth, occupation);
				user.setId(id);

				list.add(user);
			}

		} catch (SQLTimeoutException e) {
			System.out.println("Tempo excedido: Execução da busca" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Query inválida!" + e.getMessage());
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

	public User getUserByEmail(String email) throws UserNotFoundException {
		User user = null;
		PreparedStatement ps = null;
		ResultSet reSet = null;

		try {
			
			conexao = DBManager.connector();
			
			System.out.println(conexao);

			String query = "SELECT * FROM USERS WHERE USER_EMAIL = ?";
			ps = conexao.prepareStatement(query);
			ps.setString(1, email);

			reSet = ps.executeQuery();

			if (reSet.next()) {
				int id = reSet.getInt("pk_user_id");
				String name = reSet.getString("user_name");
				String pwd = reSet.getString("user_password");
				LocalDate dtBirth = reSet.getTimestamp("user_birth_date").toLocalDateTime().toLocalDate();
				String occupation = reSet.getString("user_occupation");

				user = new User(name, email, pwd, dtBirth, occupation);
				user.setId(id);
				
			} else {
				throw new UserNotFoundException("Nenhum usuário encontrado para o email: " + email);
			}

		} catch (SQLException e) {
			System.out.println("Query inválida!" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("Erro ao fechar PreparedStatement: " + e.getMessage());
				e.printStackTrace();
			}
			try {
				if (conexao != null) {
					conexao.close();
				}
			} catch (SQLException e) {
				System.out.println("Erro ao fechar conexão: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return user;
	}

	private boolean isUserExist(String email) throws UserNotFoundException {
		User user = getUserByEmail(email);

		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPwdValid(String pwd, String email) throws UserNotFoundException {
		boolean isValid = false;

		try {
			
			if (isUserExist(email)) {
				isValid = PasswordStorage.verifyPassword(pwd, getUserByEmail(email).getPwd());
			}

		} catch (CannotPerformOperationException e) {
			System.out.println("Não foi possivel verificar a senha: " + e.getMessage());
			e.printStackTrace();
		} catch (InvalidHashException e) {
			System.out.println("Hash inválida: " + e.getMessage());
			e.printStackTrace();
		}
		
		return isValid;
	}

}
