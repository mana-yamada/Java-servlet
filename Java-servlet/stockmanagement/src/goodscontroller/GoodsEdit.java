package goodscontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;



@WebServlet("/GoodsEdit")
public class GoodsEdit extends HttpServlet {

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
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			 /*どんなパラメータを受け取ったのかで編集画面に移るまでの処理を条件分岐*/

			/* 管理画面から
			 * 変更完了画面から
			 * 削除完了画面から
			 * 変更確認画面で出したエラー画面から */
			if(parameter == null) {
				//何もしない
			}
			/* 変更画面から*/
			else if(parameter.equals("backFromChange")) {
				//備品情報変更画面で「編集画面に戻る」をクリックしたときbackFromChange
				//remove editGoods

				session.removeAttribute("editGoods");
			}
			/* 変更確認画面(エラー文送った場合)から*/
			else if(parameter.equals("backFromConfirmByError")) {

				session.removeAttribute("editGoods");
				session.removeAttribute("errorMsg");
			}
			/* 変更確認画面から*/
			else if(parameter.equals("backFromConfirm")) {
				//変更確認画面から編集画面に戻ろうとするとき

				session.removeAttribute("editGoods");
				session.removeAttribute("changeGoods");
			}
			/* 削除確認画面から*/
			else if(parameter.equals("backFromUndisplay")) {
				//備品情報削除確認画面で「備品情報編集画面へ戻る」をクリックしたとき

				session.removeAttribute("editgoods");
			}

			//編集画面へforward
			String path = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

		}
	}

}



