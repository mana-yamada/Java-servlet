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
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet{
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Forward to
		String forwardPath = null;

		String action = request.getParameter("action");
		if(action == null) {
			forwardPath = "/view/registerForm.jsp";
		}
		else if (action.equals("done")) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			RegisterLogic register = new RegisterLogic();
			register.makeUser(user);
			//delete session
			session.removeAttribute("user");

			forwardPath = "/view/registerDone.jsp";


		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{
		//parameter get
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");

		//make instance in User
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		user.setName(name);


		//getsessionscope
		HttpSession session = request.getSession();
		//save instance for session scope
		session.setAttribute("user", user);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/registerConfirm.jsp");
		dispatcher.forward(request, response);
	}
}
