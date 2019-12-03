package occupantcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Occupant;
import beans.Staff;
import sqloperate.Occupantlist;

@WebServlet("/OccupantUndisplay")
public class OccupantUndisplay extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null ) {
			//redirect
			String path = "/view/login/login.jsp";
			response.sendRedirect(path);

		} else if(loginUser.getAuthority().equals("NO")) {
			session.removeAttribute("loginUser");
			//redirect
			String path = "/view/login/login.jsp";
			response.sendRedirect(path);
		} else {
			//パラメータ「value」の値によって条件分岐
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			/*編集画面から削除確認画面*/
			if(parameter == null){
				//編集画面のhiddenにしたフォームで送ったパラメータを受け取る
				int occupantId = Integer.parseInt(request.getParameter("occupantId"));
				int floorId = Integer.parseInt(request.getParameter("floorId"));
				int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
				String occupantName = request.getParameter("occupantName");

				//save instance
				Occupant editOccupant = new Occupant(occupantId, floorId, roomNumber, occupantName);
				session.setAttribute("editOccupant", editOccupant);

				//forward
				String path = "view/occupant/edit/undisplayConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request,response);
			}





			/*削除確認画面から削除完了画面*/
			else if(parameter.equals("undisplayComplete")) {

				Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");

				Occupantlist undisplay = new Occupantlist();
				undisplay.out(editOccupant);

				//remove editOccupant instance
				session.removeAttribute("editOccupant");

				//forward
				String path = "view/occupant/edit/undisplayComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request,response);
			}
		}
	}

}
