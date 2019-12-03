package beans;

public class Stock {
 String goodsName;
 int stock;
 int goodsId;
public Stock() {

}

public Stock(String goodsName, int stock) {
	this.goodsName = goodsName;
	this.stock = stock;
}

public Stock(int goodsId, int stock) {
	this.goodsId = goodsId;
	this.stock = stock;

}

public int getGoodsId() {
	return goodsId;
}
public void setGoodsId(int goodsId) {
	this.goodsId = goodsId;
}
public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}

public String getGoodsName() {
	return goodsName;
}

public void setGoodsName(String goodsName) {
	this.goodsName = goodsName;
}

}
