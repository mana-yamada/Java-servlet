package goodscontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Goods;
import beans.Staff;
import sqloperate.Goodslist;

@WebServlet("/GoodsUndisplay")
public class GoodsUndisplay extends HttpServlet {

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

			/*編集画面から削除確認画面*/
			if(parameter == null) {
				String strGoodsId = request.getParameter("goodsId");
				String GoodsName = request.getParameter("goodsName");
				String strGoodsPrice = request.getParameter("goodsPrice");


				//String型で受け取ったid priceをinteger
				int GoodsId = Integer.parseInt(strGoodsId);
				int GoodsPrice = Integer.parseInt(strGoodsPrice);



				//requestから取得した各情報からeditGoodsインスタンスを生成
				Goods editGoods = new Goods(GoodsId, GoodsName, GoodsPrice);

				//editGoodsをセッションに格納
				session.setAttribute("editGoods", editGoods);

				//forward
				String path = "view/goods/edit/undisplayConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
			/*削除確認画面から削除完了画面*/
			else if (parameter.equals("undisplayComplete")) {
				//get scope

				Goods editGoods = (Goods)session.getAttribute("editGoods");
				int goodsId = editGoods.getGoodsId();

				//SQL操作 goodslist
				Goodslist undisplay = new Goodslist();
				undisplay.display(goodsId);

				//delete scope
				session.removeAttribute("editGoods");

				//forward
				String path = "view/goods/edit/undisplayComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

			}
		}
	}
}
