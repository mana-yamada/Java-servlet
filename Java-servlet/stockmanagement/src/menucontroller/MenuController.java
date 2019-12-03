package menucontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
			path = "/view/login/login.jsp";
			response.sendRedirect(path);
		}else {
			String parameter ="";
			request.setCharacterEncoding("UTF-8");
			parameter = request.getParameter("value");
				/*don't need remove instance case*/
				if(parameter == null) {
					//remove instance

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

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*goods addConfirm */
				else if(parameter.equals("fromGoodsAddConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("goods");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*goods change undisplayConfirm*/
				else if(parameter.equals("fromGoodsEditing")) {
					//remove instance

					session.removeAttribute("editGoods");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*goods changeConfirm*/
				else if(parameter.equals("fromGoodsChangeConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("editGoods");
					session.removeAttribute("changeGoods");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

				/*occupant addConfirm*/
				else if(parameter.equals("fromOccupantAddConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("registerOccupant");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*occupant change undisplayCoonfirm*/
				else if(parameter.equals("fromOccupantEditing")) {
					//remove instance

					session.removeAttribute("editOccupant");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*occupant changeConfirm*/
				else if(parameter.equals("fromOccupantChangeConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("editOccupant");
					session.removeAttribute("changeOccupant");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*staff addConfirm*/
				else if(parameter.equals("fromStaffAddConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("registerStaff");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*staff change undisplayCoonfirm*/
				else if(parameter.equals("fromStaffEditing")) {
					//remove instance

					session.removeAttribute("editStaff");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
				/*staff changeConfirm*/
				else if(parameter.equals("fromStaffChangeConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("editStaff");
					session.removeAttribute("changeStaff");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

				/*operate operateConfirm*/
				else if(parameter.equals("fromOperateConfirm")) {
					//remove instance

					session.removeAttribute("errorMsg");
					session.removeAttribute("operation");
					session.removeAttribute("strDateTime");
					session.removeAttribute("reStaff");
					session.removeAttribute("reGoods");
					session.removeAttribute("reOccupant");

					//forward
					path = "/view/menu/menu.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

		}


	}
}
