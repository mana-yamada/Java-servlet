package beans;

public class Staff {
/*staffid	staffname	password	authority	goout*/
	public Staff() {

	}

	public Staff(String staffName, String password, String authority) {
		this.staffName = staffName;
		this.password = password;
		this.authority = authority;
	}

	int staffId;
	String staffName;
	String password;
	String authority;
	String display;
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}

}
