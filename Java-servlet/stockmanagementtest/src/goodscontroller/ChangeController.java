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

@WebServlet("/ChangeController")
public class ChangeController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*編集画面から変更画面*/
		if(parameter.equals("change")) {

			//編集画面で保存したインスタンスをget
			HttpSession session = request.getSession();
			Goods targetGoods = (Goods) session.getAttribute("targetInstance");

			//forward
			String forwardPath = "/view/goods/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}

		/*変更確認画面から変更画面*/
		else if(parameter.equals("reChangeInput")) {
			//remove scope
			HttpSession session = request.getSession();
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			//remove changeGoods instance
			session.removeAttribute("changegoods");

			//forward
			String forwardPath = "/view/goods/edit/change.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}

		/*変更確認画面から変更完了画面*/
		else if(parameter.equals("changeAction")) {
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
			session.removeAttribute("editgoods");
			session.removeAttribute("changeGoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/changeComplete.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		/*変更画面から変更確認画面*/
		if(parameter.equals("changeConfirm")) {
			try {
				//get goods scope
				HttpSession session = request.getSession();
				Goods editGoods = (Goods)session.getAttribute("editGoods");

				/*getparameterで変更後の備品名・単価を取得*/
				String goodsName = request.getParameter("goodsName");
				if(goodsName == null || goodsName.length() == 0) {
					goodsName = "変更なし";
				}
				String strGoodsPrice = request.getParameter("goodsPrice");
				int goodsPrice = Integer.parseInt(strGoodsPrice);
				if(strGoodsPrice.length() == 0) {
					goodsPrice = editGoods.getGoodsPrice();
				}
				/*取得したを新しいインスタンスに保存*/
				Goods changeGoods = new Goods();
				changeGoods.setGoodsName(goodsName);
				changeGoods.setGoodsPrice(goodsPrice);
				session.setAttribute("changeGoods", changeGoods);

				//forward
				String forwardPath = "/view/goods/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);

			}catch(NumberFormatException e) {
				/* 変更画面から変更確認画面(エラー表示)*/

				String errorMsg = "変更後の単価が正しく入力されていません。恐れ入りますがもう一度最初から編集画面に戻って操作をやり直してください。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);
				//forward
				String forwardPath = "/view/goods/edit/changeConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
				dispatcher.forward(request, response);
				e.printStackTrace();
			}
		}
	}

}
