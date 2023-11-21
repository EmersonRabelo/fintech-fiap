package br.com.fintech.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fintech.dao.UserDAO;
import br.com.fintech.exception.UserNotFoundException;
import br.com.fintech.model.User;

/**
 * Servlet implementation class registerController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder jsonBuilder = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
		    jsonBuilder.append(line);
		}

		br.close();

		String jsonData = jsonBuilder.toString();
		
		
		String email = extractValue(jsonData,"email");
	    String password = extractValue(jsonData,"pwd");
	    String occupation = extractValue(jsonData,"occupation");
	    String name = extractValue(jsonData,"nome");
	    String date_birth = extractValue(jsonData,"birth");

	    try {
	        UserDAO userDao = new UserDAO();

	        try {
	            userDao.getUserByEmail(email);
	            response.setStatus(202);

	            request.setAttribute("email", email);
	            request.getRequestDispatcher("login.jsp").forward(request, response);
	        } catch (UserNotFoundException ex) {
	            // Se caiu aqui, significa que o usuário não foi encontrado, então proceda com o cadastro
	            LocalDate birthday = LocalDate.parse(date_birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	            User user = new User(name, email, password, birthday, occupation);
	            user.setPwd(password);
	            userDao.cadastrar(user);
	            
	            user = userDao.getUserByEmail(email);
	            
	            HttpSession session = request.getSession();
	            session.setAttribute("usuarioLogado", user);

	            request.setAttribute("email", email);

	            response.setStatus(201);
	            response.sendRedirect("goal");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(401);
	    }
	}
	
	private String extractValue(String jsonData, String key) {
	    int startIndex = jsonData.indexOf("\"" + key + "\":") + key.length() + 3;
	    int endIndex = jsonData.indexOf(",", startIndex);

	    if (endIndex == -1) {
	        endIndex = jsonData.indexOf("}", startIndex);
	    }

	    StringBuilder valueBuilder = new StringBuilder();

	    boolean insideQuotes = false;
	    for (int i = startIndex; i < endIndex; i++) {
	        char currentChar = jsonData.charAt(i);

	        if (currentChar == '"') {
	            insideQuotes = !insideQuotes;
	        } else if (currentChar == ' ' && !insideQuotes) {
	            // Não adicione espaços fora de aspas
	        } else {
	            valueBuilder.append(currentChar);
	        }
	    }

	    return valueBuilder.toString();
	}

}
