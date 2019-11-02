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

@WebServlet("/Remove")
public class Remove extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		UserBeans loginUser = (UserBeans)session.getAttribute("loginUser");

		if(loginUser == null) {
			String redirectPath = "/usersystem3/index.jsp";
			response.sendRedirect(redirectPath);
		}else {
			//get parameter
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			//forwardPath
			String forwardPath ="";

			if(parameter == null) {
				//forwardpath
				String redirectPath = "/usersystem3/view/remove/remove.jsp";
				response.sendRedirect(redirectPath);
			}else if (parameter.equals("ok")) {
				//get Httpsession
				//HttpSession session = request.getSession();
				//インスタンスを呼び出し
				UserBeans target = (UserBeans)session.getAttribute("target");

				//SQL操作
				UsingMysql remove = new UsingMysql();
				remove.remove(target);

				//呼び出したセッションスコープに保存したインスタンスを削除
				session.removeAttribute("target");

				//SQL操作で正しく登録されているユーザーIDのデータを削除できる場合
				//forwardpath
				forwardPath = "/view/remove/removeComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}else if(parameter.equals("ng")) {
				//get Httpsession
				//HttpSession session = request.getSession();
				//targetインスタンスの削除
				session.removeAttribute("target");
				//forwardpath
				forwardPath = "/view/remove/remove.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//get request parameter
		String strUserId  = request.getParameter("userId");
		//forward Path
			String forwardPath = "";

		if(strUserId.length() == 0 || strUserId == null) {
			forwardPath = "/view/remove/removeInputError.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request,response);
		}else {
			try {
				int userId = Integer.parseInt(strUserId);

				//make instance
				UserBeans target = new UserBeans();
				target.setUserId(userId);

				//save instance in scope
				HttpSession session = request.getSession();
				session.setAttribute("target", target);

				//check User in SQL
				UsingMysql check = new UsingMysql();
				check.removeCheck(target);
				//入力されたユーザーのデータがなければエラーページを送る
				if(check.getUserIdNullError() != null) {
					session.removeAttribute("target");
					//forward
					forwardPath = "/view/remove/removeUserNull.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}else {
					//forward
					forwardPath = "/view/remove/removeConfirm.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}
			} catch(NumberFormatException e) {
				forwardPath = "/view/remove/removeInputError.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request,response);
			}
		}
	}

}
