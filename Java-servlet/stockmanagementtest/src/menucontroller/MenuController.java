package menucontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;

@WebServlet("/MenuController")
public class MenuController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{

		String path ="";

		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null) {
			path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);
		}else {
			String parameter ="";
			request.setCharacterEncoding("UTF-8");
			parameter = request.getParameter("value");
				if(parameter == null) {
					path = "/stockmanagementtest/view/search/search.jsp";
					response.sendRedirect(path);
				}
		}


	}
}
