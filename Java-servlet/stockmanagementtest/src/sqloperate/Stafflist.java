package sqloperate;
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

import beans.Staff;

public class Stafflist {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//add 職員情報の新規追加
	public void add(Staff staff) {
		driverConnect();
		readFile();
		insert(staff);

	}

	//add 職員情報の変更
		public void change() {
			driverConnect();
			readFile();
			String beforeName = "藤原鎌足";
			String afterName = "中臣鎌足";
			String afterAuthority = "YES";
			update(beforeName, afterName, afterAuthority);
		}

	//out 退居された入居者の情報を「退居済み」と設定
		public void out() {
			driverConnect();
			readFile();
			String goout = "2";
			String staffName = "足利義政";
			leave(goout, staffName);
		}


	//insert 職員の新規追加

		private void insert(Staff staff) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO stafflist(staffname, password, authority)\r\n" +
					"VALUES (?,?,?)");

			//ひな型に値を流し込み
			pstmt.setString(1, staff.getStaffName());
			pstmt.setString(2, staff.getPassword());
			pstmt.setString(3, staff.getAuthority());


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

	//入居者情報の変更
	private void update(String beforeName, String afterName,  String afterAuthority) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE  stafflist\r\n" +
					"SET staffname = ? , authority = ?\r\n" +
					"WHERE staffname = ?");
			//ひな型に値を流し込み
			pstmt.setString(1, afterName);
			pstmt.setString(2, afterAuthority);
			pstmt.setString(3, beforeName);

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
	//テーブル「stafflist」内のカラム「goout」：CHAR(1)型
	//'1'在職中
	//'2'退社済み
	//退社した職員のデータを「退社済み」と設定(メソッド名：leave)
	//退社した社員が会社に戻った場合には「在職中」と再設定
	private void leave(String goout, String staffName) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE stafflist\r\n" +
					"SET goout = ? \r\n" +
					"WHERE staffname = ?");
			//ひな型に値を流し込み
			pstmt.setString(1, goout);
			pstmt.setString(2, staffName);

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
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\stockmanagementtest\\MySQLdocs.properties");
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



}

