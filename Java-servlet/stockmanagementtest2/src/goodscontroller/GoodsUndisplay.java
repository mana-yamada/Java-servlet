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
import sqloperate.Goodslist;

@WebServlet("/UndisplayController")
public class GoodsUndisplay extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*編集画面から削除確認画面*/
		if(parameter.equals("undisplay")) {
			//forward
			String parameterPath = "view/goods/edit/undisplayConfirm.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(parameterPath);
			dispatcher.forward(request, response);

		}
		/*削除確認画面から削除完了画面*/
		else if (parameter.equals("undisplayComplete")) {
			//get scope
			HttpSession session = request.getSession();
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			int goodsId = editGoods.getGoodsId();

			//SQL操作
			Goodslist undisplay = new Goodslist();
			undisplay.display(goodsId);

			//delete scope
			session.removeAttribute("editGoods");

			//forward
			String parameterPath = "view/goods/edit/undisplayComplete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(parameterPath);
			dispatcher.forward(request, response);

		}
	}

}
