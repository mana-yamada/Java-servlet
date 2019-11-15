package beans;

public class Occupant {
	public Occupant() {

	}

	public Occupant(int floorId, int roomNumber, String occupantName) {
		this.floorId = floorId;
		this.roomNumber = roomNumber;
		this.occupantName = occupantName;
	}

	int occupantId;
	int floorId;
	int roomNumber;
	String occupantName;
	String display;

	public int getOccupantId() {
		return occupantId;
	}
	public void setOccupantId(int occupantId) {
		this.occupantId = occupantId;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getOccupantName() {
		return occupantName;
	}
	public void setOccupantName(String occupantName) {
		this.occupantName = occupantName;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}

}
