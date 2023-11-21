package br.com.fintech.controller;

import java.io.IOException;
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
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		
		try {
			UserDAO userDao = new UserDAO();
			boolean isValid = userDao.isPwdValid(password, email);
			
			if (isValid) {
				
				User user = userDao.getUserByEmail(email);
				
				HttpSession session = request.getSession();
				session.setAttribute("usuarioLogado", user);
				
				request.setAttribute("email", email);

				response.setStatus(200);
				response.sendRedirect("report");
			} else {
				response.setStatus(401);
				response.setHeader("Cause", "Invalid: Email or password.");
			}
			
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}

}
