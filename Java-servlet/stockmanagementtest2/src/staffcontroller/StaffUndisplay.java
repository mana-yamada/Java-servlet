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
import sqloperate.Stafflist;

@WebServlet("/StaffUndisplay")
public class StaffUndisplay extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*編集画面から削除確認画面*/
		if(parameter == null) {
			String strStaffId = request.getParameter("staffId");
			String StaffName = request.getParameter("staffName");
			String authority = request.getParameter("authority");

			//String型で受け取ったid priceをinteger
			int StaffId = Integer.parseInt(strStaffId);


			HttpSession session = request.getSession();
			//requestから取得した各情報からeditStaffインスタンスを生成
			Staff editStaff = new Staff(StaffId, StaffName, authority);

			//editStaffをセッションに格納
			session.setAttribute("editStaff", editStaff);

			//forward
			String path = "view/staff/edit/undisplayConfirm.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

		}
		/*削除確認画面から削除完了画面*/
		else if (parameter.equals("undisplayComplete")) {
			//get scope
			HttpSession session = request.getSession();
			Staff editStaff = (Staff)session.getAttribute("editStaff");
			int staffId = editStaff.getStaffId();

			//SQL操作
			Stafflist undisplay = new Stafflist();
			undisplay.display(staffId);

			//delete scope
			session.removeAttribute("editStaff");

			//forward
			String path = "view/staff/edit/undisplayComplete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

		}
	}
}
