package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBeans;
import model.UsingMysql;

@WebServlet("/Login")
public class Login extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String parameter ="";
		request.setCharacterEncoding("UTF-8");
		parameter = request.getParameter("value");
		String forwardPath ="";

		if(parameter == null) {
			//forward
			forwardPath = "/index.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else if(parameter.equals("person")) {
			//新規登録確認画面からログイン画面に戻られた時
			HttpSession session = request.getSession();
			session.removeAttribute("person");
			//forward
			forwardPath = "/index.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String strUserId = request.getParameter("userId");
		String password = request.getParameter("password");
		int userId;

		//入力されたものを確認
		if((strUserId.length() == 0 || strUserId == null) || (password.length() == 0 || password == null)) {
			//エラーページを出す
			String forwardPath = "/view/login/loginInputError.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else {
		    try {
				userId = Integer.parseInt(strUserId);

				//UserBeansを用いloginUserインスタンスを生成
				//loginUserインスタンスに登録するユーザーの情報を設定
				UserBeans loginUser = new UserBeans(); //復号化したものはpassword変数以外の変数へ代入
				loginUser.setUserId(userId);

				//セッションスコープを取得し、loginUserインスタンスを保存
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);

				//ユーザー検索の操作
				UsingMysql login = new UsingMysql();
				login.search(loginUser);

				//検索データがない時はデータがないエラーページを送る
				if(login.getUserIdNullError() != null) {
					//保存したloginUserインスタンスの削除
					session.removeAttribute("loginUser");

					//forward 新規登録をしてくださいと送る
					String forwardPath = "/view/login/loginUserNull.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}else {
					//SQLで検索したデータをloginUserインスタンスの参照に保存
					session.setAttribute("loginUser", loginUser);

					//参照を変えたloginUserインスタンスに格納された復号パスワードと入力されたパスワード文字列が位置しているか確認
					if(password.equals(loginUser.getPassword())) {
						//リダイレクト
						String redirectPath = "/usersystem3/view/menu/menu.jsp";
						response.sendRedirect(redirectPath);
					}else {
						//入力されたユーザーIDのデータがない場合やパスワードとIDが一致しない場合は全てエラー文を出す
						//インスタンスの削除
						session.removeAttribute("loginUser");
						//forward
						String forwardPath = "/view/login/loginMismatchError.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
						dispatcher.forward(request, response);
					}
				}

			}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
				String forwardPath = "/view/login/loginInputError.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}



}
