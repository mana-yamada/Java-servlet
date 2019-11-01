package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import bean.UserBeans;

public class UsingMysql {

	String url;
	String userName;
	String pass;

	Connection con;
	Connection readCon;
	PreparedStatement pstmt;
	ResultSet rs;

	//ユーザー登録前のデータチェックで入力されたユーザーIDのデータが既に登録されていた場合にcontrollerへ送る変数
	private String userIdUsedError;
	public String getUserIdUsedError() {
		return userIdUsedError;
	}
	public void setUserIdUsedError(String userIdUsedError) {
		this.userIdUsedError = userIdUsedError;
	}

	//ユーザー削除前/検索時にデータチェックで入力されたユーザーIDのデータが登録されていない場合にcontrollerへ送る変数
	private String userIdNullError;

	public String getUserIdNullError() {
			return userIdNullError;
		}
	public void setUserIdNullError(String userIdNullError) {
		this.userIdNullError = userIdNullError;
	}

	//登録前のユーザーIDチェック
	public void addCheck(UserBeans user) {
		driverConnect();
		readFile();
		check(user);
	}
	//ユーザー登録
	public void add(UserBeans user) {
		driverConnect();
		readFile();
		addUser(user);
	}

	//ユーザー検索
	public void search(UserBeans user) {
		driverConnect();
		readFile();
		searchUser(user);
	}

	//削除前のユーザーIDチェック
	public void removeCheck(UserBeans user) {
		driverConnect();
		readFile();
		check(user);
	}
	//ユーザー削除
	public void remove(UserBeans user){
		driverConnect();
		readFile();
		removeUser(user);
	}

	//SQLのドライバー調整⇒1回接続
	private void driverConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");

		}

	}
	//DB接続先のファイル読み込み
	private void readFile() {
		//1つのprivateメソッドにする
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\usersystem3\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			 url = p.getProperty("url");
			 userName = p.getProperty("userName");
			 pass = p.getProperty("pass");
			fr.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();

		}catch(IOException e) {
			e.printStackTrace();

		}
	}

	//登録前・削除前のデータ確認操作
	//ユーザー検索操作
	private void check(UserBeans user)  {
		try {
			try {
				//①接続・自動コミットモードの解除
				con = DriverManager.getConnection(url,userName,pass);
				con.setAutoCommit(false);
				//②SQL送信処理
				pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ?");
				pstmt.setInt(1, user.getUserId());
				//検索系SQL文を自動組み立て送信
				rs = pstmt.executeQuery();
				//新規登録前のチェックで入力されたユーザーのデータが既にあった場合
				if(rs.next()) {
					userIdUsedError = "入力されたユーザーIDは既に使われています。";
					System.out.println("入力されたユーザーIDは既に使われています。");
				}else {
					userIdNullError = "入力されたユーザーIDのデータは登録されていません。";
				}
				//後片付け
				rs.close();
				pstmt.close();
				//送信済みの処理要求の確定（コミット）
				con.commit();
			}catch(SQLException e){
				e.printStackTrace();
				//ロールバック
				try {
					con.rollback();
				}catch(SQLException e2) {
					e2.printStackTrace();
				}
			}finally {
				//DB接続を切断
				if(con != null) {
					try {
						con.close();
					}catch(SQLException e3) {
						e3.printStackTrace();
					}
				}
			}
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	//ユーザー登録
	private void addUser(UserBeans user) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");
			//ひな型に値を流し込み
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4,user.getMail());
			pstmt.setString(5, user.getTel());
			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				System.out.println("データが正しく登録されました。");
			}else {
				System.out.println("データが正しく登録されませんでした。");
			}
			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e){
			e.printStackTrace();
			//ロールバック
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			//DB接続を切断
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	//ユーザー検索画面でのユーザー検索
	private void searchUser(UserBeans user) {
		try {
			con = DriverManager.getConnection(url,userName,pass);
			//con = readCon;
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1,user.getUserId());
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
				//SQL上のデータをuserインスタンスへ格納
				user.setUserId(rs.getInt("userId"));
				user.setName(rs.getString("name"));
				user.setMail(rs.getString("mail"));
				user.setTel(rs.getString("tel"));
				try {
					//パスワードを復号化
					UsingCrypt unLock = new UsingCrypt();
					String unlockedPassword = unLock.getToken(rs.getString("password")) ;
						//復号化したSQL上のパスワードをuserインスタンスへ格納
						user.setPassword(unlockedPassword);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				userIdNullError = "入力されたユーザーIDのデータは登録されていません。";
			}
			//後片付け
			rs.close();
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e){
			e.printStackTrace();
			//ロールバック
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			//DB接続を切断
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	//ユーザー削除
	private void removeUser(UserBeans user){
		try {
			con = DriverManager.getConnection(url,userName,pass);
			//con = readCon;
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("DELETE FROM users WHERE userId = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1, user.getUserId());
			//更新系sqlの自動組み立て送信
			int r = pstmt.executeUpdate();
			if(r != 0) {
				System.out.println("データが正しく削除されました");
			}else {
				System.out.println("データが正しく削除されませんでした");
			}
			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e){
			e.printStackTrace();
			//ロールバック
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			//DB接続を切断
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

}
