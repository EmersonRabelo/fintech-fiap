package br.com.fintech.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fintech.dao.ExpenseDAO;
import br.com.fintech.exception.UserNotFoundException;
import br.com.fintech.model.Expense;
import br.com.fintech.model.User;

/**
 * Servlet implementation class ExpenseController
 */
@WebServlet("/expense")
public class ExpenseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("usuarioLogado");
		
		String id = request.getParameter("id");
		
		ExpenseDAO expenseDAO = new ExpenseDAO();
		
		try {
			if (user != null) {
				if (id != null) {
					
					Expense expense = expenseDAO.getById(id);
					
					String jsonResult = "{";
					jsonResult += "\"id\":" + expense.getId() + ",";
					jsonResult += "\"name\":\"" + expense.getName() + "\",";
					jsonResult += "\"value\":" + expense.getValue();
					jsonResult += "}";
					
					response.setContentType("application/json");
					response.getWriter().write(jsonResult);
				} else {
					List<Expense> expenses;
					
					String userEmail = user.getEmail();
					expenses = expenseDAO.getByEmail(userEmail);
					
					request.setAttribute("expenses", expenses);
					request.getRequestDispatcher("expense.jsp").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch(UserNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("application/json");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("usuarioLogado");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder jsonBuilder = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
		    jsonBuilder.append(line);
		}

		br.close();

		String jsonData = jsonBuilder.toString();

		
		try {
		    String name = extractValue(jsonData, "title");
		    String value = extractValue(jsonData, "value");
		    
		    ExpenseDAO expenseDao = new ExpenseDAO();
		    
		    Expense expense = new Expense(user.getId(), name, Float.valueOf(value));

		    expenseDao.cadastrar(expense);

		    response.setStatus(201);
		    response.getWriter().write("sucesso!");
		} catch(Exception e) {
		    e.printStackTrace();
		    response.setStatus(400);
		    response.getWriter().write("Erro na requisição");
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("usuarioLogado");
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder jsonBuilder = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
		    jsonBuilder.append(line);
		}

		br.close();

		String jsonData = jsonBuilder.toString();

		try {
			
		    String id = extractValue(jsonData, "id");
		    String name = extractValue(jsonData, "title");
		    String value = extractValue(jsonData, "value");
		    
		    ExpenseDAO expenseDao = new ExpenseDAO();
		    
		    expenseDao.update(user.getId(), id, name, value);

		    response.setStatus(201);
		    response.getWriter().write("sucesso!");
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(400);
		    response.getWriter().write("Erro na requisição");
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		try {
			ExpenseDAO expenseDao = new ExpenseDAO();
			expenseDao.delete(id);
			
		} catch(Exception e) {
			e.printStackTrace();
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
