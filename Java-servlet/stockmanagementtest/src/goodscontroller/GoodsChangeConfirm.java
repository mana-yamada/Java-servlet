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

@WebServlet("/GoodsChangeConfirm")
public class GoodsChangeConfirm extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*変更確認画面から変更完了画面*/
		if(parameter.equals("changeAction")) {
			//備品情報変更確認画面で「変更」をクリックしたとき
			HttpSession session = request.getSession();
			//変更前の備品データ1行を保存したインスタンスを取得
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			/*変更後に表示しようとしている備品名、単価を保存したインスタンスを取得*/
			Goods changeGoods = (Goods)session.getAttribute("changeGoods");

			/*変更する備品のgoodsid, 変更後の備品名, 変更後の備品の単価を変数に代入*/
			int goodsId = editGoods.getGoodsId();
			String goodsName = changeGoods.getGoodsName();
			int goodsPrice = changeGoods.getGoodsPrice();

			/*備品情報を更新させるメソッドを呼び出す*/
			Goodslist changingGoodsData = new Goodslist();
			changingGoodsData.change(goodsId, goodsName, goodsPrice);

			/*情報変更に使用したスコープを破棄*/
			session.removeAttribute("editGoods");
			session.removeAttribute("changeGoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/changeComplete.jsp");
		}

		/*変更確認画面から変更画面*/
		else if(parameter.equals("reChangeInput")) {
			//remove scope
			HttpSession session = request.getSession();
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			//remove changeGoods instance
			session.removeAttribute("changeGoods");

			//forward
			String forwardPath = "/view/goods/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

	}
}
