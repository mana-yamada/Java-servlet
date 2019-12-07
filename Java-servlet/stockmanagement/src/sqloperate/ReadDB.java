package sqloperate;

public class ReadDB {
	String url = "jdbc:mysql://localhost:3306/stockmanagement";
	String userName = "root";
	String pass = "@Stockmana01";

	public ReadDB() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}



}
