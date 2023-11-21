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

import br.com.fintech.dao.MonthlyIncomeDAO;
import br.com.fintech.exception.UserNotFoundException;
import br.com.fintech.model.MonthlyIncome;
import br.com.fintech.model.User;

/**
 * Servlet implementation class MonthlyIncomeController
 */
@WebServlet("/MonthlyIncome")
public class MonthlyIncomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthlyIncomeController() {
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

		try {
			if (user != null) {	
				// Get specific item
				if(id != null) {
					MonthlyIncomeDAO monthlyIncDao = new MonthlyIncomeDAO();
					MonthlyIncome monthlyInc = monthlyIncDao.getById(id);

					String jsonResult = "{";
					jsonResult += "\"id\":" + monthlyInc.getId() + ",";
					jsonResult += "\"name\":\"" + monthlyInc.getName() + "\",";
					jsonResult += "\"value\":" + monthlyInc.getValue();
					jsonResult += "}";

					response.setContentType("application/json");
					response.getWriter().write(jsonResult);
					
				} else {
					MonthlyIncomeDAO monthlyIncDao = new MonthlyIncomeDAO();
					List<MonthlyIncome> incomes;
					
					String userEmail = user.getEmail();
					incomes = monthlyIncDao.getByEmail(userEmail);
					
					request.setAttribute("incomes", incomes);
					request.getRequestDispatcher("income.jsp").forward(request, response);
				}
				
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			
		} catch(UserNotFoundException e) {
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
		    
		    MonthlyIncomeDAO monthlyIncDao = new MonthlyIncomeDAO();
		    
		    MonthlyIncome monthlyInc = new MonthlyIncome(user.getId(), name, Float.valueOf(value));

		    monthlyIncDao.cadastrar(monthlyInc);

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
		    
		    MonthlyIncomeDAO monthlyIncDao = new MonthlyIncomeDAO();

		    monthlyIncDao.update(user.getId(), id, name, value);

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
			MonthlyIncomeDAO monthlyIncDao = new MonthlyIncomeDAO();
			monthlyIncDao.delete(id);
			
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
