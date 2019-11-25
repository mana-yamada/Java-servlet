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

@WebServlet("/OccupantAdd")
public class OccupantAdd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null ) {
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);

		} else if(loginUser.getAuthority().equals("NO")) {
			session.removeAttribute("loginUser");
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);
		} else {
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			/*メニューから登録画面*/
			/*登録完了画面から登録画面*/
			if(parameter == null || parameter.length() == 0){
				//forward
				String forwardPath = "/view/occupant/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);

			}
			/*登録確認画面から登録画面
			 * value=backFromAdd*/
			else if(parameter.equals("backFromAdd")) {
				//remove registerOccupant instance

				session.removeAttribute("registerOccupant");
				//forward
				String path = "/view/occupant/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
			/*登録確認画面から登録画面(エラー文を送った場合)
			 * value= backFromAddError*/
			else if(parameter.equals("backFromAddError")) {
				//remove errorMsg instance

				session.removeAttribute("errorMsg");
				//forward
				String path = "/view/occupant/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
			/*登録確認画面から登録完了画面
			 * value=addAction*/
			else if(parameter.equals("addAction")) {

				//get instance

				Occupant registerOccupant = (Occupant)session.getAttribute("registerOccupant");

				//operate SQL
				Occupantlist register = new Occupantlist();
				register.add(registerOccupant);

				//remove instance
				session.removeAttribute("registerOccupant");

				//forward
				String path = "/view/occupant/add/addComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");
		//path
		String path = "/view/occupant/add/addConfirm.jsp";

		/*登録画面から登録確認画面*/
		if(parameter.equals("addConfirm")) {
			String occupantName = request.getParameter("occupantName");
			String strFloorId = request.getParameter("floorId");
			String strRoomNumber1 = request.getParameter("roomNumber1");
			String strRoomNumber2 = request.getParameter("roomNumber2");
			String strRoomNumber3 = request.getParameter("roomNumber3");

			if(occupantName == null || occupantName.length() == 0) {

				String errorMsg = "入力画面で正しく入力されませんでした。恐れ入りますがもう一度最初から入力し直してください";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);

				//forward
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}else {
			  try {

				int floorId = Integer.parseInt(strFloorId);

				int roomNumber1 = Integer.parseInt(strRoomNumber1);
				int roomNumber2 = Integer.parseInt(strRoomNumber2);
				int roomNumber3 = Integer.parseInt(strRoomNumber3);

				int roomNumber = 0;
				if(floorId == 1) {
					roomNumber = roomNumber1;
				}else if(floorId == 2) {
					roomNumber = roomNumber2;
				}else if(floorId == 3) {
					roomNumber = roomNumber3;
				}

				//save instance
				Occupant registerOccupant = new Occupant(floorId, roomNumber ,occupantName);
				HttpSession session = request.getSession();
				session.setAttribute("registerOccupant", registerOccupant);

				//forward
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			  }catch(NumberFormatException e) {

				  String errorMsg = "入力画面で正しく入力されませんでした。恐れ入りますがもう一度最初から入力し直してください";
				  HttpSession session = request.getSession();
				  session.setAttribute("errorMsg", errorMsg);

				  //forward
				  RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				  dispatcher.forward(request, response);
				  e.printStackTrace();
			  }
			}
		}
	}

}
