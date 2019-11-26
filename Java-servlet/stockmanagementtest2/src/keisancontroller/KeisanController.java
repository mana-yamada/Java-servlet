package keisancontroller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Goods;
import beans.Occupant;
import beans.Table;
import sqloperate.Goodslist;
import sqloperate.Keisan;
import sqloperate.Occupantlist;

@WebServlet("/KeisanController")
public class KeisanController extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException , IOException{
		//request.setCharacterEncoding("UTF-8");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
//		String year = "2019";
//		String month = "11";
		Table seikyu = new Table();
		seikyu.setYear(year);
		seikyu.setMonth(month);


		/*全てのgoodsid, goodsnameを取得*/
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		Goodslist addGoods = new Goodslist();
		addGoods.get(goodsList);

		//Tableインスタンスを生成⇒year, month , goodsListをsetterに入れる
		Table table = new Table();
		table.setYear(year);
		table.setMonth(month);
		table.setGoodsList(goodsList);

		/*goodsListを引数に、1カ月の備品の使用個数テーブルを作成create table*/
		Keisan keisan = new Keisan();
		keisan.making(table);


		/*全てのoccupantid, occupantnameを取得*/
		ArrayList<Occupant> occupantList = new ArrayList<Occupant>();
		Occupantlist addOccupant = new Occupantlist();
		addOccupant.get(occupantList);
	}
}
