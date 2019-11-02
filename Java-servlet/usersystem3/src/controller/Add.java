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
import model.UsingCrypt;
import model.UsingMysql;

@WebServlet("/Add")
public class Add extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//送られてきたパラメータによってフォワード先を変える
		request.setCharacterEncoding("UTF-8");
		String parameters = request.getParameter("value");

		//forward変数
		String forwardPath = "";
		//dispatcher = request.getRequestDispatcher(forwardPath);

		if(parameters == null) {
			//新規登録フォームへ
			forwardPath = "view/add/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else if (parameters.equals("ok")) {
			//HttpSessionインスタンスの取得
			//セッションスコープに保存したインスタンスを呼び出す
			HttpSession session = request.getSession();
			UserBeans person = (UserBeans)session.getAttribute("person");

			//SQLにて登録操作
			UsingMysql addUser = new UsingMysql();
			addUser.add(person);
			//今まで使っていたセッションスコープに保存したインスタンスを削除
			session.removeAttribute("person");

			//登録完了ページへ
			forwardPath = "/view/add/addComplete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request,response);
		} else if(parameters.equals("ng")) {
			//入力画面で打った内容を保存したpersonインスタンスを削除
			HttpSession session = request.getSession();
			session.removeAttribute("person");
			//forward/新規登録フォームへ
			forwardPath = "view/add/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
	}

	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		String strUserId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String tel = request.getParameter("tel");
		int userId;
		//forward forwardPath and form
		String forwardPath = "";

		//入力されたものを確認
		if((strUserId.length() == 0 || strUserId == null) || (password.length() == 0 || password == null) || (name.length() == 0 || name == null) ||
				(mail.length() == 0 || mail  == null) ||(tel.length() == 0 || tel == null) ) {
			//エラーページを出す
			forwardPath = "/view/add/inputError.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else {
		    try {
		    	userId = Integer.parseInt(strUserId);

		    	//passwordの暗号化
				UsingCrypt usingCrypt = new UsingCrypt();
				password = usingCrypt.saveToken(password);

				//UserBeansを用いpersonインスタンスを生成
				//personインスタンスに登録するユーザーの情報を設定
				UserBeans person = new UserBeans(userId,password,name,mail,tel);

				//セッションスコープを取得し、personインスタンスを保存
				HttpSession session = request.getSession();
				session.setAttribute("person", person); //もしもエラーだった時は破棄するように

				//新規登録時にすでに登録されているユーザーIDを登録しようとしているか確認
				UsingMysql check = new UsingMysql();
				check.addCheck(person);

				//SQL操作時にユーザーIDが既に登録されている場合
				if(check.getUserIdUsedError() != null) {
					//保存したインスタンスの削除
					session.removeAttribute("target");
					//入力されたユーザーIDが既に使われているものならばそのように知らせる
					forwardPath = "/view/add/usedIdError.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				//DBにユーザーIDのかぶりがない時
				}else {
					//forward
					forwardPath = "/view/add/addConfirm.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}
			}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
				forwardPath = "/view/add/inputError.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		    catch(Exception e) {
				e.printStackTrace();
				//エラーページを出す
				forwardPath = "/view/add/addError.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}

}