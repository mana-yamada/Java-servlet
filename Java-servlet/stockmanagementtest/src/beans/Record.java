package beans;

import java.sql.Date;
import java.sql.Time;

public class Record {

 int occupantId;
 int goodsId;
 Date date;
 Time time;
 int outshed;
 int inshed;
 int staffId;

 public Record() {

 }

public int getOccupantId() {
	return occupantId;
}
public void setOccupantId(int occupantId) {
	this.occupantId = occupantId;
}
public int getGoodsId() {
	return goodsId;
}
public void setGoodsId(int goodsId) {
	this.goodsId = goodsId;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Time getTime() {
	return time;
}
public void setTime(Time time) {
	this.time = time;
}
public int getOutshed() {
	return outshed;
}
public void setOutshed(int outshed) {
	this.outshed = outshed;
}
public int getInshed() {
	return inshed;
}
public void setInshed(int inshed) {
	this.inshed = inshed;
}
public int getStaffId() {
	return staffId;
}
public void setStaffId(int staffId) {
	this.staffId = staffId;
}


}
