package beans;

public class Operating {
	String strSheds;
	int countIn;
	int countOut;
	String strSpace;

	Staff staff;
	Occupant occupant;
	Goods goods;


	public String getStrSheds() {
		return strSheds;
	}
	public void setStrSheds(String strSheds) {
		this.strSheds = strSheds;
	}

	public int getCountIn() {
		return countIn;
	}
	public void setCountIn(int countIn) {
		this.countIn = countIn;
	}
	public int getCountOut() {
		return countOut;
	}
	public void setCountOut(int countOut) {
		this.countOut = countOut;
	}
	public String getStrSpace() {
		return strSpace;
	}
	public void setStrSpace(String strSpace) {
		this.strSpace = strSpace;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Occupant getOccupant() {
		return occupant;
	}
	public void setOccupant(Occupant occupant) {
		this.occupant = occupant;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}


}
