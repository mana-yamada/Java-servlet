package operatecontroller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Goods;
import beans.Occupant;
import beans.Operating;
import beans.Record;
import beans.Staff;
import beans.Stock;
import beans.StrDateTime;
import sqloperate.Goodslist;
import sqloperate.Occupantlist;
import sqloperate.Recordlist;
import sqloperate.Stafflist;

@WebServlet("/OperateController")
public class OperateController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{

		HttpSession session = request.getSession();
		Staff loginUser = (Staff)session.getAttribute("loginUser");
		if(loginUser == null) {
			//redirect
			String path = "/stockmanagementtest/view/login/login.jsp";
			response.sendRedirect(path);
		} else {
			request.setCharacterEncoding("UTF-8");
			String parameter = request.getParameter("value");

			/*備品入出庫入力画面から確認画面*/

			if(parameter == null) {

				//日時
				String year = request.getParameter("year");
				String month   =  request.getParameter("month");
				String day   =  request.getParameter("day");
				String hour   =  request.getParameter("hour");
				String minute   =  request.getParameter("minute");
				//入力した文字列の時間をインスタンスに保存
				StrDateTime strDateTime = new StrDateTime(year,month,day,hour,minute);
				session.setAttribute("strDateTime", strDateTime);


				//担当者(職員) ID
				String strStaffId = request.getParameter("staff");
				//職員名を取得
				Staff reStaff = new Staff();
				reStaff.setStaffId(Integer.parseInt(strStaffId));
				Stafflist stfTable = new Stafflist();
				stfTable.search(reStaff);
				session.setAttribute("reStaff", reStaff);

				//備品ID
				String strGoodsId = request.getParameter("goods");
				//備品名を取得
				Goods reGoods = new Goods();
				reGoods.setGoodsId(Integer.parseInt(strGoodsId));
				Goodslist gdTable = new Goodslist();
				gdTable.search(reGoods);
				session.setAttribute("reGoods", reGoods);

				//出入庫(確認画面のみ)
				String strSheds = request.getParameter("sheds");

				/*入庫か出庫か未選択*/
				if(strSheds.equals("nonSheds")) {
					session.removeAttribute("strDateTime");
					session.removeAttribute("reStaff");
					session.removeAttribute("reGoods");
					//make errorMsg
					String errorMsg = "入庫か出庫かが選択されていません。恐れ入りますがもう一度最初から入力し直してください。";
					session.setAttribute("errorMsg", errorMsg);
					//forward
					String path = "/view/operate/operateConfirm.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}else {
					//入庫数
					String strCountIn = request.getParameter("countIn");
					//出庫数
					String strCountOut = request.getParameter("countOut");
					//使用場所 居室 or 共用部(確認画面のみ)
					String strSpace = request.getParameter("space");

					/*入庫の場合*/
					if(strSheds.equals("insheds")){
						strSheds = "入庫";
						strCountOut = "0";
						strSpace = "倉庫";
						String strOccupantId = "0";
						//確認画面で出力したいものをインスタンスに保存
						Operating operation = new Operating();
						operation.setStrSheds(strSheds);
						operation.setCountIn(Integer.parseInt(strCountIn));
						operation.setCountOut(Integer.parseInt(strCountOut));
						operation.setStrSpace(strSpace);
						session.setAttribute("operation", operation);

						//入居者名と居室番号を取得
						Occupant reOccupant = new Occupant();
						reOccupant.setOccupantId(Integer.parseInt(strOccupantId));
						Occupantlist ocTable = new Occupantlist();
						ocTable.search(reOccupant);

						session.setAttribute("reOccupant", reOccupant);

						//forward
						String path = "/view/operate/operateConfirm.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					}else {
						/*使用場所未選択の場合*/
						if(strSpace.equals("nonSpace")) {
							session.removeAttribute("strDateTime");
							session.removeAttribute("reStaff");
							session.removeAttribute("reGoods");
							//make errorMsg
							String errorMsg = "どこに出庫するのかが選択されていません。恐れ入りますがもう一度最初から入力し直してください。";
							session.setAttribute("errorMsg", errorMsg);
							//forward
							String path = "/view/operate/operateConfirm.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(path);
							dispatcher.forward(request, response);
						/*出庫の場合*/
						}else {
							//居室フロア
							String floor = request.getParameter("floor");
							if(floor.equals("nonFloor")) {
								session.removeAttribute("strDateTime");
								session.removeAttribute("reStaff");
								session.removeAttribute("reGoods");
								/*利用者の住むフロアが未選択の場合*/
								//make errorMsg
								String errorMsg = "どのフロアの誰に出庫するのかが選択されていません。恐れ入りますがもう一度最初から入力し直してください。";
								session.setAttribute("errorMsg", errorMsg);
								//forward
								String path = "/view/operate/operateConfirm.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(path);
								dispatcher.forward(request, response);
							}else {
								//フォームから受け取った入居者ID 画面上では3フロア全てのプルダウンを選んだ状態になっている
								String strOccupant1 = request.getParameter("occupant1");
								String strOccupant2 = request.getParameter("occupant2");
								String strOccupant3 = request.getParameter("occupant3");
								//実際に確認画面に送る入居者ID(共用部で使用するための出庫 or 入庫なら0)
								String strOccupantId = "";

								Stock stock = new Stock();
								stock.setGoodsId(Integer.parseInt(strGoodsId));

								Goodslist block = new Goodslist();
								block.stockJudge(stock);
									/*出庫数が残数よりも多い場合*/
									if(stock.getStock() < Integer.parseInt(strCountOut)) {
										session.removeAttribute("strDateTime");
										session.removeAttribute("reStaff");
										session.removeAttribute("reGoods");
										String errorMsg = "";
										/*nowstockが0の場合*/
										if(stock.getStock() <= 0) {
											 errorMsg = "選択した備品の在庫はありません。";
										}else if(stock.getStock() < Integer.parseInt(strCountOut)) {
											 errorMsg = "選択した出庫数は残数よりも大きく出庫できません。";
										}
										session.setAttribute("errorMsg", errorMsg);
										//forward
										String path = "/view/operate/operateConfirm.jsp";
										RequestDispatcher dispatcher = request.getRequestDispatcher(path);
										dispatcher.forward(request, response);
									}else {
										strSheds = "出庫";
										strCountIn = "0";
										/*共用部の場合*/
										if(strSpace.equals("shareSpace")){
											strSpace = "共用部";
											strOccupantId = "0";
										}
										/*利用者の居室の場合*/
										else if(strSpace.equals("possSpace")){
											strSpace = "居室";
											/*利用者の居室に出庫する場合 1F or 2F or 3F or 未選択*/
											/*1Fの場合*/
											if(floor.equals("1")) {
												strOccupantId = strOccupant1;
											}
											/*2Fの場合*/
											else if(floor.equals("2")) {
												strOccupantId = strOccupant2;
											}
											/*3Fの場合*/
											else if(floor.equals("3")) {
												strOccupantId = strOccupant3;
											}
										}
										//確認画面で出力したいものをインスタンスに保存
										Operating operation = new Operating();
										operation.setStrSheds(strSheds);
										operation.setCountIn(Integer.parseInt(strCountIn));
										operation.setCountOut(Integer.parseInt(strCountOut));
										operation.setStrSpace(strSpace);
										session.setAttribute("operation", operation);

										//入居者名と居室番号を取得
										Occupant reOccupant = new Occupant();
										reOccupant.setOccupantId(Integer.parseInt(strOccupantId));
										Occupantlist ocTable = new Occupantlist();
										ocTable.search(reOccupant);
										session.setAttribute("reOccupant", reOccupant);

										//forward
										String path = "/view/operate/operateConfirm.jsp";
										RequestDispatcher dispatcher = request.getRequestDispatcher(path);
										dispatcher.forward(request, response);
									}
							}
						}
					}
				}
			}

			/*確認画面から備品入出庫完了画面*/
			else if(parameter.equals("complete")) {

				//確認画面に移る前に保存したインスタンスを取得
				 Operating operation = (Operating)session.getAttribute("operation");
				 StrDateTime strDateTime = (StrDateTime)session.getAttribute("strDateTime");
				 Staff reStaff = (Staff)session.getAttribute("reStaff");
				 Goods reGoods = (Goods)session.getAttribute("reGoods");
				 Occupant reOccupant = (Occupant)session.getAttribute("reOccupant");

				 //入庫数 inshed
				 operation.getCountIn();
				 //出庫数 outshed
				 operation.getCountIn();
				 //staffid
				 reStaff.getStaffId();
				 //occupantid
				 reOccupant.getOccupantId();
				 //goodsid
				 reGoods.getGoodsId();

				 //date
				 String strDate = strDateTime.getYear() + "-" + strDateTime.getMonth() + "-" + strDateTime.getDay();
				 Date date = Date.valueOf(strDate);
				 //time
				 String strTime = strDateTime.getHour() + ":" + strDateTime.getMinute() + ":00";
				 Time time = Time.valueOf(strTime);

				 //SQLデータに登録したい内容だけ1つのインスタンスとしてSQLへ送る
				 Record record = new Record();
				 record.setOccupantId(reOccupant.getOccupantId());
				 record.setGoodsId(reGoods.getGoodsId());
				 record.setDate(date);
				 record.setTime(time);
				 record.setInshed(operation.getCountIn());
				 record.setOutshed(operation.getCountOut());
				 record.setStaffId( reStaff.getStaffId());
				 //stockもrecordインスタンスに含める


				 //備品出入庫記録を登録
				 Recordlist writing = new Recordlist();
				 writing.inout(record);

				 //備品出入庫記録から最新の


				 //goodslistのnowstock更新
				 Stock update = new Stock(record.getGoodsId() , record.getStock());
				 Goodslist operate = new Goodslist();
				 operate.updatingStock(update);

				 //計算テーブルのデータ出庫した数を個数テーブルに入れる
				 record.getOutshed(); //出庫数

				 session.removeAttribute("operation");
				 session.removeAttribute("strDateTime");
				 session.removeAttribute("reStaff");
				 session.removeAttribute("reGoods");
				 session.removeAttribute("reOccupant");


				//forward
				String path = "/view/operate/operateComplete.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
			/*メニュー画面から入力画面*/
			else if(parameter.equals("fromMenu")) {
				//forward
				String path = "/view/operate/operate.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}

			/*確認画面から入力画面エラーとき*/
			else if(parameter.equals("backFromComfirmError")) {

				session.removeAttribute("errorMsg");

				//forward
				String path = "/view/operate/operate.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
			/*確認画面から入力画面*/
			else if(parameter.equals("backFromComfirm")) {

				 session.removeAttribute("operation");
				 session.removeAttribute("strDateTime");
				 session.removeAttribute("reStaff");
				 session.removeAttribute("reGoods");
				 session.removeAttribute("reOccupant");

				//forward
				String path = "/view/operate/operate.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}
	}

}
