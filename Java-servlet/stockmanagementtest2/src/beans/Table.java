package beans;

import java.util.ArrayList;

public class Table {
	public Table() {

	}
	String year;
	String month;

	ArrayList<Goods> goodsList;

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public ArrayList<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(ArrayList<Goods> goodsList) {
		this.goodsList = goodsList;
	}



}
