package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Pizza;
import model.User;

import java.io.IOException;
import java.util.List;

import dao.PizzaDao;

/**
 * Servlet implementation class PizzaController
 */
public class PizzaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PizzaDao pdao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PizzaController() {
        super();
        pdao=new PizzaDao();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // For displaying individual pizza details
        if (request.getParameter("id") != null) {
            long id = Long.parseLong(request.getParameter("id"));
            Pizza pizza = pdao.findById(id);
            if (pizza != null) {
                request.setAttribute("Pizza", pizza);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Detail.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("message", "Pizza not found");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/PizzaList.jsp");
                rd.forward(request, response);
            }
        } else {
            
        	    // List all pizzas
        	    List<Pizza> results = pdao.findAll();
        	    if (results != null && !results.isEmpty()) {
        	        request.setAttribute("listPizza", results);
        	    } else {
        	        request.setAttribute("message", "No pizzas available at the moment.");
        	    }
        	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/PizzaList.jsp");
        	    rd.forward(request, response);
        	}

    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
