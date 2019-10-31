package bean;

public class UserBeans {
	//Field

	private int userId;
	private String password;
	private String name;
	private String mail;
	private String tel;

	//Constructor
	public UserBeans() {
	}
	public UserBeans(int userId, String password, String name, String mail, String tel ) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.mail = mail;
		this.tel = tel;
	}

	//getter & setter
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}



}
