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
import sqloperate.Nowstocklist;
import sqloperate.Occupantlist;
import sqloperate.Recordlist;
import sqloperate.Stafflist;

@WebServlet("/OperateController")
public class OperateController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{

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

			//担当者(職員) ID
			String strStaffId = request.getParameter("staff");

			//備品ID
			String strGoodsId = request.getParameter("goods");

			//出入庫(確認画面のみ)
			String strSheds = request.getParameter("sheds");

			//入庫数
			String strCountIn = request.getParameter("countIn");
			//出庫数
			String strCountOut = request.getParameter("countOut");

			//使用場所 居室 or 共用部(確認画面のみ)
			String strSpace = request.getParameter("space");

			//居室フロア
			String floor = request.getParameter("floor");

			//フォームから受け取った入居者ID 画面上では3フロア全てのプルダウンを選んだ状態になっている
			String strOccupant1 = request.getParameter("occupant1");
			String strOccupant2 = request.getParameter("occupant2");
			String strOccupant3 = request.getParameter("occupant3");
			//実際に確認画面に送る入居者ID(共用部で使用するための出庫 or 入庫なら0)
			String strOccupantId = "";

			//画面に持っていくもの
			/* 画面のみ
			 * strSheds
			 * strSpace
			 *
			 * SQLに登録するのに必要な物
			 * strGoodsId ⇒ SQLで備品名を取得しないと
			 * strCountIn
			 * strCountOut
			 * strOccupantId
			 *
			 * GoodsでもOccupantでもないもの⇒beansにする
			 * */
			if(strSheds.equals("insheds")){
				strSheds = "入庫";
				strCountOut = "0";
				strSpace = "倉庫";
				strOccupantId = "0";
			}else if(strSheds.equals("outsheds")){
				strSheds = "出庫";
				strCountIn = "0";
				if(strSpace.equals("shareSpace")){
					strSpace = "共用部";
					strOccupantId = "0";
				}else if(strSpace.equals("possSpace")){
					strSpace = "居室";
					if(floor.equals("1")) {
						strOccupantId = strOccupant1;
					}else if(floor.equals("2")) {
						strOccupantId = strOccupant2;
					}else if(floor.equals("3")) {
						strOccupantId = strOccupant3;
					}
				}
			}


			/* 出庫数 入庫数 入庫したのか出庫したのか
			 * どこに出庫したのか(入庫時は「倉庫」に入れたとする)
			 * */
			//確認画面で出力したいものをインスタンスに保存
			Operating operation = new Operating();
			operation.setStrSheds(strSheds);
			operation.setCountIn(Integer.parseInt(strCountIn));
			operation.setCountOut(Integer.parseInt(strCountOut));
			operation.setStrSpace(strSpace);

			HttpSession session = request.getSession();
			session.setAttribute("operation", operation);

			//入力した文字列の時間をインスタンスに保存
			StrDateTime strDateTime = new StrDateTime(year,month,day,hour,minute);
			session.setAttribute("strDateTime", strDateTime);

			//職員名を取得
			Staff reStaff = new Staff();
			reStaff.setStaffId(Integer.parseInt(strStaffId));
			Stafflist stfTable = new Stafflist();
			stfTable.search(reStaff);

			session.setAttribute("reStaff", reStaff);

			//備品名を取得
			Goods reGoods = new Goods();
			reGoods.setGoodsId(Integer.parseInt(strGoodsId));
			Goodslist gdTable = new Goodslist();
			gdTable.search(reGoods);

			session.setAttribute("reGoods", reGoods);

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

		/*確認画面から備品入出庫完了画面*/
		else if(parameter.equals("complete")) {

			HttpSession session = request.getSession();

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


			 //nowstocklistも操作する
			 Stock update = new Stock(record.getGoodsId() , record.getStock());

			 Nowstocklist operate = new Nowstocklist();
			 operate.updating(update);


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
		/*入力内容確認画面から入力画面*/
		else if(parameter.equals("backFromComfirm")) {
			 HttpSession session = request.getSession();
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
