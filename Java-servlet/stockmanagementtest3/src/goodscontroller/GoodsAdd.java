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

@WebServlet("/GoodsAdd")
public class GoodsAdd extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		//メニューページから、
		//新規登録完了画面から新しく入力を続ける場合
		//新規登録確認画面で入力からやり直すとき、  jspファイルを出す前に入力時のスコープを破棄
		String parameter = request.getParameter("value");
		if(parameter == null) {
			//forward
			String path = "/view/goods/add/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else if(parameter.equals("addgoods")) {
			//get instance Goods
			HttpSession session = request.getSession();
			Goods goods = (Goods) session.getAttribute("goods");
			goods.getGoodsName();
			goods.getGoodsPrice();

			//SQL操作!!goodslistへデータを入れる
			Goodslist addGoods = new Goodslist();
			addGoods.add(goods);
			//SQL操作  nowstocklistへデータを入れる

			//remove instance
			session.removeAttribute("goods");
			//errorMsg instance remove
			session.removeAttribute("errorMsg");

			//forward
			String path = "/view/goods/add/addComplete.jsp";
			RequestDispatcher  dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else if(parameter.equals("reinput")) {
			//remove instance need getSession!?
			request.removeAttribute("goods");
			request.removeAttribute("errorMsg");

			//forward
			String path = "/view/goods/add/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");
		//新規登録ページから
		if(parameter.equals("addconfirm")) {

			//入力フォームのパラメータ取得

			String goodsName = request.getParameter("goodsname");
			String  strGoodsPrice = request.getParameter("goodsprice");


			//入力ミスがあったら、エラーメッセージを送るために条件分岐
			if(goodsName.length() == 0 || goodsName.length() > 30 || strGoodsPrice.length() == 0) {
				//入力欄で正しく入力されていないことをセッションスコープに保存
				String errorMsg = "入力欄で正しく入力されていませんでした。恐れ入りますがもう一度入力し直してください。";
				request.setAttribute("errorMsg" , errorMsg);
				//forward
				String path = "/view/goods/add/addConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}else {
			    try {
					int goodsPrice = Integer.parseInt(strGoodsPrice);
					//入力された単価の値が0以上であることを確認
					if(goodsPrice < 0) {
						throw new NumberFormatException("正しく入力されていません.");
					}else {
						//インスタンス生成
						Goods goods = new Goods();
						goods.setGoodsName(goodsName);
						goods.setGoodsPrice(goodsPrice);


						//スコープ取得、保存
						HttpSession session = request.getSession();
						session.setAttribute("goods", goods);

						//forward
						String path = "/view/goods/add/addConfirm.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					}

			    }catch(NumberFormatException e) {
				  	//入力欄で正しく入力されていないことをセッションスコープに保存
					String errorMsg = "備品の単価が正しく入力されていませんでした。恐れ入りますがもう一度入力し直してください。";
					request.setAttribute("errorMsg" , errorMsg);
					//forward
					String path = "/view/goods/add/addConfirm.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
					e.printStackTrace();
			  }
			}

		}
	}


}

