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

@WebServlet("/Search")
public class Search extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//フォワード先
		String forwardPath ="";

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String strUserId = request.getParameter("userId");
		String password = request.getParameter("password");
		int userId;

		//入力されたものを確認
				if((strUserId.length() == 0 || strUserId == null) || (password.length() == 0 || password == null)) {
					//エラーページを出す
					forwardPath = "/view/search/searchInputError.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}else {
				    try {
				    	userId = Integer.parseInt(strUserId);

						//UserBeansを用いtargetインスタンスを生成
						//targetインスタンスに登録するユーザーの情報を設定
						UserBeans target = new UserBeans(); //復号化したものはpassword変数以外の変数へ代入
						target.setUserId(userId);

						//セッションスコープを取得し、targetインスタンスを保存
						HttpSession session = request.getSession();
						session.setAttribute("target", target);

						//ユーザー検索の操作
						UsingMysql search = new UsingMysql();
						search.search(target);

						//検索データがない時はデータがないエラーページを送る
						if(search.getUserIdNullError() != null) {
							//targetインスタンスの削除
							session.removeAttribute("target");
							//forward
							forwardPath = "/view/search/searchUserNull.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
							dispatcher.forward(request, response);
						}

						//SQLで検索したデータをtargetインスタンスの参照に保存
						session.setAttribute("target", target);
						//参照を変えたtargetインスタンスに格納された復号パスワードと入力されたパスワード文字列が位置しているか確認
						if(password.equals(target.getPassword())) {
							//forward
							forwardPath = "/view/search/searchResult.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
							dispatcher.forward(request, response);
						}else {
							//入力されたユーザーIDのデータがない場合やパスワードとIDが一致しない場合は全てエラー文を出す
							//インスタンスの削除
							session.removeAttribute("target");
							//forward
							forwardPath = "/view/search/searchMismatch.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
							dispatcher.forward(request, response);
						}
					}catch(NumberFormatException e) {
					//ユーザーIDが全く入力されていないときのエラー文かつ
					//ユーザーIDで文字入力されたときかつ
					//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
						forwardPath = "/view/search/searchInputError.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
						dispatcher.forward(request, response);
					}
				}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

			String parameter ="";
			request.setCharacterEncoding("UTF-8");
			parameter = request.getParameter("value");

			String forwardPath ="";

			if(parameter == null) {
				//forward
				forwardPath = "/view/search/search.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}else if (parameter.equals("back")) {
				//検索操作時に使ったtargetインスタンスを削除
				HttpSession session = request.getSession();
				session.removeAttribute("target");
				//forward
				forwardPath = "/view/search/search.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}

	}

}
