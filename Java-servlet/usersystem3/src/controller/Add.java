package controller;

import java.io.IOException;
import java.io.PrintWriter;

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
	//servlet
	HttpServletRequest request;
	HttpServletResponse response;
	//forward forwardPath and form
	String forwardPath = "";
	RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//送られてきたパラメータによってフォワード先を変える
		String parameters = request.getParameter("value");

		//forward変数
		//forwardPath = "";
		//dispatcher = request.getRequestDispatcher(forwardPath);

		if(parameters == null) {
			//新規登録フォームへ
			forwardPath = "/add/add.jsp";
			dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else if (parameters.equals("ok")) {
			//HttpSessionインスタンスの取得
			//セッションスコープに保存したインスタンスを呼び出す
			HttpSession session = request.getSession();
			UserBeans person = (UserBeans)session.getAttribute("person");
			//SQLにて登録操作
			UsingMysql adduser = new UsingMysql();
			adduser.add(person);
			//登録完了画面へは上のメソッド内でaddConfirm()メソッドを呼び出すことで画面へつなげる!?
			addComplete();
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
		//String forwardPath = "";
		//RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);

		//HTMl
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		//入力されたものを確認
		if((strUserId.length() == 0 || strUserId == null) || (password.length() == 0 || password == null) || (name.length() == 0 || name == null) ||
				(mail.length() == 0 || mail  == null) ||(tel.length() == 0 || tel == null) ) {
			//エラーページを出す
			forwardPath = "/add/inputError.jsp";
			dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}else {
		    try {
		    	userId = Integer.parseInt(strUserId);

		    	//新規登録時にすでに登録されているユーザーIDを登録しようとしているか確認
				UsingMysql check = new UsingMysql();
				check.check();

		    	//passwordの暗号化
				UsingCrypt usingCrypt = new UsingCrypt();
				password = usingCrypt.saveToken(password);

				//UserBeansを用いpersonインスタンスを生成
				//personインスタンスに登録するユーザーの情報を設定
				UserBeans person = new UserBeans(userId,password,name,mail,tel);

				//セッションスコープを取得し、personインスタンスを保存
				HttpSession session = request.getSession();
				session.setAttribute("person", person); //もしもエラーだった時は破棄するように

				//forward
				forwardPath = "/add/addConfirm.jsp";
				dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);

			}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
				forwardPath = "/add/inputError.jsp";
				dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				//エラーページを出す
				forwardPath = "/add/inputError.jsp";
				dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}

	//DBを切断⇒登録確認画面へ
	public void addConfirm() throws ServletException, IOException{
		forwardPath = "/add/addConfirm.jsp";
		dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}
	//DBを切断⇒登録完了画面へ
	public void addComplete() throws ServletException, IOException {
		forwardPath = "/add/addComplete.jsp";
		dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request,response);
	}
	//入力されたユーザーIDが既に使われている場合に出すエラーページ
	public void userIdError()throws ServletException, IOException{
		forwardPath = "/add/userIdError.jsp";
		dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request,response);
	}

	//入力欄で正しく入力されていない時に出すエラーページ
	public void inputError()throws ServletException, IOException{
		forwardPath = "/add/inputError.jsp";
		dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request,response);
	}




}
