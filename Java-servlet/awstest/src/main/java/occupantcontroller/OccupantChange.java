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

@WebServlet("/OccupantChange")
public class OccupantChange  extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
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

		//パラメータ「value」の値によって条件分岐
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*編集画面から変更画面*/
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
			String path = "view/occupant/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request,response);
		}

		/*変更確認画面から変更確認(エラー文送った場合)*/
		else if(parameter.equals("reChangeInputByError")) {
			//remove errorMsg instance
			session.removeAttribute("errorMsg");

			//forward
			String path = "/view/occupant/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}

		/*変更確認画面から変更画面*/
		else if(parameter.equals("reChangeInput")) {

			//remove changeOccupant instance
			session.removeAttribute("changeOccupant");

			//forward
			String path = "/view/occupant/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}

		/*変更確認画面から変更完了画面*/
		else if(parameter.equals("changeAction")) {

			Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");
			Occupant changeOccupant = (Occupant)session.getAttribute("changeOccupant");

			String name = changeOccupant.getOccupantName();
			int floorId = changeOccupant.getFloorId();
			int roomNumber = changeOccupant.getRoomNumber();
			int occupantId = editOccupant.getOccupantId();

			Occupant insert   = new Occupant(occupantId, floorId, roomNumber, name);
			Occupantlist change = new Occupantlist();
			change.change(insert);

			session.removeAttribute("editOccupant");
			session.removeAttribute("changeOccupant");

			//forward
			String path = "/view/occupant/edit/changeComplete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//パラメータ「value」の値によって条件分岐

		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*変更画面から変更確認画面*/
		if(parameter.equals("changeConfirm")) {
			try {
				//get editOccupant scope
				HttpSession session = request.getSession();
				//Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");


				String occupantName = request.getParameter("occupantName");
				if (occupantName.length() == 0) {
					throw new NullPointerException("氏名未入力");
				}

				String strOccupantFloor = request.getParameter("occupantFloor");
				int occupantFloor = Integer.parseInt(strOccupantFloor);


				String strRoomNumber = request.getParameter("roomNumber");
				int roomNumber = Integer.parseInt(strRoomNumber);
				if(roomNumber < 0) {
					throw new NumberFormatException("居室番号がマイナスです");
				}

				/*取得したを新しいインスタンスに保存*/
				Occupant changeOccupant = new Occupant();
				changeOccupant.setOccupantName(occupantName);
				changeOccupant.setFloorId(occupantFloor);
				changeOccupant.setRoomNumber(roomNumber);
				session.setAttribute("changeOccupant", changeOccupant);

				//forward
				String path = "/view/occupant/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
			/*備品名の入力欄が空だった場合*/
			catch(NullPointerException e) {
				/* 変更画面から変更確認画面(エラー表示)*/
				String errorMsg = "変更後の氏名が入力されていません。恐れ入りますがもう一度最初から編集画面に戻って操作をやり直してください。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);
				//forward
				String path = "/view/occupant/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
				e.printStackTrace();
			}
			/*整数を入力させる入力欄で0~9以外の文字列が入っていた場合*/
			catch(NumberFormatException e) {
				/* 変更画面から変更確認画面(エラー表示)*/
				String errorMsg = "変更後の入居フロアや居室番号が正しく入力されていません。恐れ入りますがもう一度最初から編集画面に戻って操作をやり直してください。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);
				//forward
				String path = "/view/occupant/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
				e.printStackTrace();
			}



		}

	}



}
