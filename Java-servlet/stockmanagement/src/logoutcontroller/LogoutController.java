package logoutcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;

@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String path ="";

		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null) {
			//redirect
			path = "/stockmanagement/view/login/login.jsp";
			response.sendRedirect(path);
		}else {
			String parameter ="";
			request.setCharacterEncoding("UTF-8");
			parameter = request.getParameter("value");
				/*don't need remove instance case*/
				if(parameter == null) {
					//remove instance
					/*errorMsg*/
					session.removeAttribute("errorMsg");
					/*goods*/
					session.removeAttribute("goods");
					session.removeAttribute("editGoods");
					session.removeAttribute("changeGoods");
					/*occupant*/
					session.removeAttribute("registerOccupant");
					session.removeAttribute("editOccupant");
					session.removeAttribute("changeOccupant");
					/*staff*/
					session.removeAttribute("registerStaff");
					session.removeAttribute("editStaff");
					session.removeAttribute("changeStaff");
					/*operate*/
					session.removeAttribute("operation");
					session.removeAttribute("strDateTime");
					session.removeAttribute("reStaff");
					session.removeAttribute("reOccupant");
					/*loginUser*/
					session.removeAttribute("loginUser");

					//forward
					path = "/view/logout/logout.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
		}
	}

}
