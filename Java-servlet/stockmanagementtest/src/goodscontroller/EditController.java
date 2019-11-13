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

@WebServlet("/EditController")
public class EditController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");
		if(parameter == null) {
			//編集画面へforward
			String forward = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);

		}else if(parameter.equals("change")) {
			//備品情報編集画面で「変更」をクリックしたとき

			//どの備品の情報を変更するかのインスタンス取得
			//HttpSession session = request.getSession();
			//Goods editGoods = (Goods)session.getAttribute("editGoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/change.jsp");

		}else if(parameter.equals("backFromChange")) {
			//備品情報変更画面で「編集画面に戻る」をクリックしたときbackFromChange
			//remove editGoods
			HttpSession session = request.getSession();
			session.removeAttribute("editGoods");
			//編集画面へforward
			String forward = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);

		}else if(parameter.equals("changegoods")) {

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

		}else if(parameter.equals("rechangeinput")) {
			//備品情報変更確認画面で「入力画面へ戻る」をクリックしたとき

			/*remove scope */
			HttpSession session = request.getSession();
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			session.removeAttribute("changegoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/change.jsp");
		}else if(parameter.equals("backEditFromConfirm")) {
			//変更内容確認画面から編集画面に戻ろうとするとき
			HttpSession session = request.getSession();
			session.removeAttribute("editGoods");
			session.removeAttribute("changeGoods");

			//編集画面へforward
			String forward = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);
		} if(parameter.equals("undisplay")) {
			//備品情報編集画面で「削除」をクリックしたとき

			//どの備品の情報を削除するかのインスタンス取得
			/*※ここでいう「削除」は(ユーザーに見える部分だけでは見えないように
			DBには格納しておくこととする*/
//			HttpSession session = request.getSession();
//			Goods editGoods = (Goods)session.getAttribute("editGoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/undisplayConfirm.jsp");

		}else if (parameter.equals("undisplaycomplete")) {
			//備品情報削除確認画面で「削除」をクリックしたとき
			HttpSession session = request.getSession();
			Goods editGoods = (Goods)session.getAttribute("editGoods");
			int goodsId = editGoods.getGoodsId();
			//SQL操作
			Goodslist undisplay = new Goodslist();
			undisplay.display(goodsId);

			//delete scope
			session.removeAttribute("editGoods");

			//redirect
			response.sendRedirect("/stockmanagementtest/view/goods/edit/undisplayComplete.jsp");
		}

		else if(parameter.equals("backUndisplay")) {
			//備品情報削除確認画面で「備品情報編集画面へ戻る」をクリックしたとき
			HttpSession session = request.getSession();
			session.removeAttribute("editgoods");

			//編集画面へforward
			String forward = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);
		}



	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//changeconfirm
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");
		if(parameter.equals("changeconfirm")) {
			try {
				//get goods scope
				HttpSession session = request.getSession();
				Goods editGoods = (Goods)session.getAttribute("editGoods");

				/*getparameterで変更後の備品名・単価を取得*/
				String goodsName = request.getParameter("goodsname");
				if(goodsName == null) {
					goodsName = "変更なし";
				}
				String strGoodsPrice = request.getParameter("goodsprice");
				int goodsPrice = Integer.parseInt(strGoodsPrice);
				if(strGoodsPrice.length() == 0) {
					goodsPrice = editGoods.getGoodsPrice();
				}
				/*取得したを新しいインスタンスに保存*/
				Goods changeGoods = new Goods();
				changeGoods.setGoodsName(goodsName);
				changeGoods.setGoodsPrice(goodsPrice);
				session.setAttribute("changeGoods", changeGoods);

				//redirect
				response.sendRedirect("/stockmanagementtest/view/goods/edit/changeConfirm.jsp");

			}catch(NumberFormatException e) {
				/* make errorMsg*/
				/*String errorMsg = "変更後の単価が正しく入力されていません。";
				HttpSession session = request.getSession();
				session.setAttribute("errorMsg", errorMsg);
				//redirect
				response.sendRedirect("/stockmanagementtest/view/goods/edit/changeConfirm.jsp");*/
				e.printStackTrace();

			}
		}
	}
}
