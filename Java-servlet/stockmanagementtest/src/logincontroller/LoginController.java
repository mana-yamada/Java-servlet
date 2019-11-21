package logincontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;
import sqloperate.Stafflist;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String parameter ="";
		request.setCharacterEncoding("UTF-8");
		parameter = request.getParameter("value");
		String forwardPath ="";

		/*index.jspからログイン画面*/
		if(parameter == null) {
			//forward
			forwardPath = "/view/login/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
		/*メニュー画面(ログインエラー)からログイン画面*/
		else if(parameter.equals("backFromMenuError")) {
			HttpSession session = request.getSession();
			session.removeAttribute("errorMsg");

			//forward
			String path = "/view/login/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		/*ログイン画面からメニュー画面*/

		request.setCharacterEncoding("UTF-8");
		String strUserId = request.getParameter("userId");
		String password = request.getParameter("password");

		//入力されたものを確認
		if((strUserId.length() == 0 || strUserId == null) || (password.length() == 0 || password == null)) {
			String errorMsg = "入力欄が空欄です。恐れ入りますがもう一度最初から入力し直してください。";
			//errorMsg送った時にログイン画面へ戻るパラメータ

			HttpSession session = request.getSession();
			session.setAttribute("errorMsg", errorMsg);

			//redirect
			String path = "/stockmanagementtest/view/menu/menu.jsp";
			response.sendRedirect(path);
		}else {
		    try {
				int userId = Integer.parseInt(strUserId);

				//UserBeansを用いloginUserインスタンスを生成
				//loginUserインスタンスに登録するユーザーの情報を設定
				Staff loginUser = new Staff(); //復号化したものはpassword変数以外の変数へ代入
				loginUser.setStaffId(userId);
				loginUser.setPassword(password);

				//ユーザー検索の操作
				Stafflist login = new Stafflist();
				login.login(loginUser);

				//セッションスコープを取得し、loginUserインスタンスを保存
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);

				//検索データがない時はデータがないエラーページを送る
				if(loginUser.getErrorMsg() != null) {
					//save errorMsg
					String errorMsg = loginUser.getErrorMsg();
					session.setAttribute("errorMsg", errorMsg);

					//保存したloginUserインスタンスの削除
					session.removeAttribute("loginUser");

					//redirect
					String path = "/stockmanagementtest/view/menu/menu.jsp";
					response.sendRedirect(path);
				}else {
					//SQLで検索したデータをloginUserインスタンスの参照に保存
					session.setAttribute("loginUser", loginUser);

					//参照を変えたloginUserインスタンスに格納された復号パスワードと入力されたパスワード文字列が位置しているか確認
					if(password.equals(loginUser.getPassword())) {
						//リダイレクト
						String path = "/stockmanagementtest/view/menu/menu.jsp";
						response.sendRedirect(path);
					}else {
						//入力されたユーザーIDのデータがない場合やパスワードとIDが一致しない場合は全てエラー文を出す
						String errorMsg = "入力されたユーザーIDとパスワードが一致しません。恐れ入りますがもう一度最初から入力し直してください。";
						session.setAttribute("errorMsg", errorMsg);
						//インスタンスの削除
						session.removeAttribute("loginUser");
						//リダイレクト
						String path = "/stockmanagementtest/view/menu/menu.jsp";
						response.sendRedirect(path);
					}
				}

			}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
				String errorMsg = "入力されたユーザーIDとパスワードが一致しません。恐れ入りますがもう一度最初から入力し直してください。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);

				//リダイレクト
				String path = "/stockmanagementtest/view/menu/menu.jsp";
				response.sendRedirect(path);
			}
		}
	}
}
