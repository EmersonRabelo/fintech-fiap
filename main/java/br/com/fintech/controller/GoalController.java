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

import br.com.fintech.dao.GoalDAO;
import br.com.fintech.model.Goal;
import br.com.fintech.model.User;

/**
 * Servlet implementation class GoalController
 */
@WebServlet("/goal")
public class GoalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalController() {
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
		
		if (user != null){
			if (id != null) {
				GoalDAO goalDao = new GoalDAO();
				Goal goal = goalDao.getById(id);
				
				String jsonResult = "{";
				jsonResult += "\"id\":" + goal.getId() + ",";
				jsonResult += "\"name\":\"" + goal.getName() + "\",";
				jsonResult += "\"deadLine\":\"" + goal.getDeadline() + "\",";
				jsonResult += "\"completion\":" + goal.getCompletion();
				jsonResult += "}";
				
				response.setContentType("application/json");
				response.getWriter().write(jsonResult);
			} else {
				GoalDAO goalDao = new GoalDAO();
				List<Goal> goals;
				
				String userEmail = user.getEmail();
				goals = goalDao.getByEmail(userEmail);
				
				request.setAttribute("goals", goals);
				request.getRequestDispatcher("goal.jsp").forward(request, response);
	
			}
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
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
		    String name = extractValue(jsonData, "name");
		    String completion = extractValue(jsonData, "completion");
		    String deadLine = extractValue(jsonData, "deadLine");
		    
		    completion = String.valueOf(Float.parseFloat(completion) / 100);

		    GoalDAO goalDao = new GoalDAO();
		    Goal goal = new Goal(user.getId(), name, deadLine, Float.parseFloat(completion));
		    
		    System.out.println(user.getId());
		    
		    goalDao.cadastrar(goal);
		    
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
		    String name = extractValue(jsonData, "name");
		    String deadLine = extractValue(jsonData, "deadLine");
		    String completion = extractValue(jsonData, "completion");
		    
		    completion = String.valueOf(Float.parseFloat(completion) / 100);  
   
		    GoalDAO goalDao = new GoalDAO();

		    goalDao.update(user.getId(), id, name, deadLine, completion);
		    
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
			GoalDAO goalDao = new GoalDAO();
			goalDao.delete(id);
			
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
