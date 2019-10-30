package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.RegisterLogic;
import model.User;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet{

	private final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String forwardPath = null;

		//URLのリンクの後に付け加えて送ったリクエストパラメータを受信
		String afterConfirm = request.getParameter("afterConfirm");
		if(afterConfirm == null) {
			forwardPath = "/view/registerForm.jsp";
		}else if(afterConfirm.equals("done")) {
			//get instance newUser
			HttpSession session = request.getSession();
			User newUser = (User)session.getAttribute("newUser");

			//make instance from RegisterLogic
			RegisterLogic register = new RegisterLogic();
			register.adding(newUser);

			//remove instance
			session.removeAttribute("newUser");

			//where forward
			forwardPath = "/view/registerDone.jsp";

		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		String userId =request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");

		//make instance in User
		User newUser = new User(userId, password, name);

		//save instance in session scope
		//⇒make new session scope
		HttpSession session = request.getSession();
		session.setAttribute("newUser", newUser);

		//forward
		String forwardPath = "/view/registerConfirm.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request,response);
	}
}
