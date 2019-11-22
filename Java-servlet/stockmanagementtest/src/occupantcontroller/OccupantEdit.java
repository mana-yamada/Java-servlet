package occupantcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;

@WebServlet("/OccupantEdit")
public class OccupantEdit extends HttpServlet{

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

			/*編集画面*/
			if(parameter == null){
				//何もしない
			}
			/* 変更画面から*/
			else if(parameter.equals("backFromChange")) {
				//備品情報変更画面で「編集画面に戻る」をクリックしたときbackFromChange
				//remove editOccupant
				session.removeAttribute("editOccupant");
			}
			/* 変更確認画面(エラー文送った場合)から*/
			else if(parameter.equals("backFromConfirmByError")) {
				session.removeAttribute("editOccupant");
				session.removeAttribute("errorMsg");
			}
			/* 変更確認画面から*/
			else if(parameter.equals("backFromConfirm")) {
				//変更確認画面から編集画面に戻ろうとするとき
				session.removeAttribute("editOccupant");
				session.removeAttribute("changeOccupant");
			}
			/* 削除確認画面から*/
			else if(parameter.equals("backFromUndisplay")) {
				//備品情報削除確認画面で「備品情報編集画面へ戻る」をクリックしたとき
				session.removeAttribute("editoccupnat");
			}

			//forward
			String path = "view/occupant/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request,response);
		}
	}


}
