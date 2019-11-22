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

@WebServlet("/StaffChange")
public class StaffChange extends HttpServlet {
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
				/*編集画面から変更画面*/
				if(parameter == null) {
					String strStaffId = request.getParameter("staffId");
					String staffName = request.getParameter("staffName");
					String authority = request.getParameter("authority");


					//String型で受け取ったid priceをinteger
					int staffId = Integer.parseInt(strStaffId);

					//requestから取得した各情報からeditStaffインスタンスを生成
					Staff editStaff = new Staff(staffId, staffName, authority);

					//editStaffをセッションに格納
					session.setAttribute("editStaff", editStaff);

					//forward
					String path = "/view/staff/edit/change.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);

				}

				/*変更確認画面から変更確認(エラー文送った場合)*/
				else if(parameter.equals("reChangeInputByError")) {
					/*remove scope*/
					//remove errorMsg instance
					session.removeAttribute("errorMsg");

					//forward
					String path = "/view/staff/edit/change.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

				/*変更確認画面から変更画面*/
				else if(parameter.equals("reChangeInput")) {

					/*remove scope*/

					//remove changeStaff instance
					session.removeAttribute("changeStaff");

					//forward
					String path = "/view/staff/edit/change.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

				/*変更確認画面から変更完了画面*/
				else if(parameter.equals("changeAction")) {
					//職員情報変更確認画面で「変更」をクリックしたとき

					//変更前の職員データ1行を保存したインスタンスを取得
					Staff editStaff = (Staff)session.getAttribute("editStaff");
					/*変更後に表示しようとしている職員名、単価を保存したインスタンスを取得*/
					Staff changeStaff = (Staff)session.getAttribute("changeStaff");

					/*変更する職員のstaffid, 変更後の職員名, 変更後の職員の単価を変数に代入*/
					int staffId = editStaff.getStaffId();
					String staffName = changeStaff.getStaffName();
					String authority = changeStaff.getAuthority();

					Staff update = new Staff(staffId, staffName, authority);


					/*職員情報を更新させるメソッドを呼び出す*/
					Stafflist changingStaffData = new Stafflist();
					changingStaffData.change(update);

					/*情報変更に使用したスコープを破棄*/
					session.removeAttribute("editStaff");
					session.removeAttribute("changeStaff");

					//forward
					String path = "/view/staff/edit/changeComplete.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*変更画面から変更確認画面*/
		if(parameter.equals("changeConfirm")) {
			try {
				//get staff scope
				HttpSession session = request.getSession();

				String staffName = request.getParameter("staffName");
				if (staffName.length() == 0) {
					throw new NullPointerException("職員名未入力");
				}

				String authority = request.getParameter("authority");

				/*取得したを新しいインスタンスに保存*/
				Staff changeStaff = new Staff();
				changeStaff.setStaffName(staffName);
				changeStaff.setAuthority(authority);

				session.setAttribute("changeStaff", changeStaff);

				//forward
				String path = "/view/staff/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
			/*職員名の入力欄が空だった場合*/
			catch(NullPointerException e) {
				/* 変更画面から変更確認画面(エラー表示)*/
				String errorMsg = "変更後の職員名が入力されていません。恐れ入りますがもう一度最初から編集画面に戻って操作をやり直してください。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);
				//forward
				String path = "/view/staff/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
				e.printStackTrace();
			}

		}
	}

}
