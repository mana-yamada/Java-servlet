package beans;

public class Goods {
	public Goods(){

	}
	public Goods(int goodsId, String goodsName, int goodsPrice, String display) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.display = display;
	}
	public Goods(int goodsId, String goodsName, int goodsPrice, String display, int listNumber) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.display = display;
		this.listNumber = listNumber;
	}

	//備品ID //備品名 //備品の単価 //表示or非表示
	int goodsId;
	String goodsName;
	int goodsPrice;
	String display;
	int listNumber;
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}

	public int getListNumber() {
		return listNumber;
	}

	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
}
