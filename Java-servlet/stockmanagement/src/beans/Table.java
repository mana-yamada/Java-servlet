package beans;

import java.util.ArrayList;

public class Table {
	public Table() {

	}
	String year;
	String month;

	ArrayList<Goods> goodsList;
	ArrayList<Occupant> occupantList;

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
	public ArrayList<Occupant> getOccupantList() {
		return occupantList;
	}
	public void setOccupantList(ArrayList<Occupant> occupantList) {
		this.occupantList = occupantList;
	}



}
