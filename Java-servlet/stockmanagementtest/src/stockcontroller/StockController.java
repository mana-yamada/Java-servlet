package stockcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;

@WebServlet("/StockController")
public class StockController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null) {
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);
		}else {
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			if(parameter == null) {
				//forward
				String path = "/view/stock/stock.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}

		}

	}
}
