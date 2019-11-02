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

@WebServlet("/Menu")
public class Menu extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		UserBeans loginUser = (UserBeans)session.getAttribute("loginUser");
		if(loginUser == null) {
			//redirect
			String redirectPath = "usersystem3/index.jsp";
			response.sendRedirect(redirectPath);
		}else {
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			String forwardPath = "";

			if(parameter == null) {
				forwardPath = "/view/menu/menu.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}else if(parameter.equals("target")) {
				//削除確認時に保存したtargetインスタンスを削除
				//HttpSession session = request.getSession();
				session.removeAttribute("target");
				//forward
				forwardPath = "/view/menu/menu.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}
}
