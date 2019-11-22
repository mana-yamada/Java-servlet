package staffcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Staff;
import model.UsingCrypt;
import sqloperate.Stafflist;

@WebServlet("/StaffAdd")
public class StaffAdd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null ) {
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);

		} else if(loginUser.getAuthority().equals("NO")) {
			session.removeAttribute("loginUser");
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);
		} else {
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");
			/*メニューから新規登録画面
			 * 登録完了画面から新規登録画面*/
			if(parameter == null) {
				//forward
				String forwardPath = "view/staff/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
			/*新規登録確認画面から新規登録完了画面*/
			//addAction
			else if(parameter.equals("addAction")) {
				// add.jspで保存したインスタンス取得
				Staff registerStaff = (Staff)session.getAttribute("registerStaff");

				//operate sql
				Stafflist register = new Stafflist();
				register.add(registerStaff);

				//remove instance
				session.removeAttribute("registerStaff");

				//forward
				String forwardPath = "view/staff/add/addComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
			/*登録確認画面から登録画面(エラー有の場合)*/
			else if(parameter.equals("backFromAddError")) {
				//remove errorMsg
				session.removeAttribute("errorMsg");

				//forward
				String forwardPath = "view/staff/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
			/*登録確認画面から登録画面*/
			else if(parameter.equals("backFromAdd")) {
				//remove registerInstance
				session.removeAttribute("registerStaff");

				//forward
				String forwardPath = "view/staff/add/add.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*新規登録画面から登録確認画面*/
		if(parameter.equals("addConfirm")) {
			String staffName = request.getParameter("staffName");
			String password = request.getParameter("password");
			String authority = request.getParameter("authority");

			if(staffName.length() == 0 || password.length() == 0 || authority == null) {
				String errorMsg = "入力画面で正しく入力されませんでした。恐れ入りますがもう一度最初から入力し直してください。";
				//save instance
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);

				//forward
				String forwardPath = "/view/staff/add/addConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
			}else {
				try {
					//passwordの暗号化
					UsingCrypt usingCrypt = new UsingCrypt();
					password = usingCrypt.saveToken(password);

					//make instance
					Staff registerStaff = new Staff(staffName, password, authority);
					//save instance
					HttpSession session = request.getSession();
					session.setAttribute("registerStaff", registerStaff);

					//新規登録時にすでに登録されているユーザーIDを登録しようとしているか確認
					//UsingMysql check = new UsingMysql();
					//check.addCheck(person);

					//SQL操作時にユーザーIDが既に登録されている場合
//					if(check.getUserIdUsedError() != null) {
//						//保存したインスタンスの削除
//						session.removeAttribute("target");
//						//入力されたユーザーIDが既に使われているものならばそのように知らせる
//						forwardPath = "/view/add/usedIdError.jsp";
//						RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
//						dispatcher.forward(request, response);
//					//DBにユーザーIDのかぶりがない時
//					}else {
//						//forward
//						forwardPath = "/view/add/addConfirm.jsp";
//						RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
//						dispatcher.forward(request, response);
//					}

					//forward
					String forwardPath = "/view/staff/add/addConfirm.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}catch(Exception e) {
					System.out.println("正しく暗号化されていません");
				}
			}
		}
	}
}
