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
import java.util.ArrayList;
import java.util.Properties;

import beans.Staff;

public class Stafflist {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//search 在庫入出庫画面操作時
	public void search(Staff staff) {
		driverConnect();
		readFile();
		record(staff);
	}

	//add 職員情報の新規追加
	public void add(Staff staff) {
		driverConnect();
		readFile();
		insert(staff);

	}

	//get 職員情報の取得
	public void get(ArrayList<Staff> staffList) {
		driverConnect();
		readFile();
		getTable(staffList);
	}

	//add 職員情報の変更
		public void change(Staff staff) {
			driverConnect();
			readFile();
			update(staff);
		}



	//out 退居された入居者の情報を「退居済み」と設定
		public void display(int staffId) {
			driverConnect();
			readFile();
			leave(staffId);
		}

	//record  備品入出庫時に使う
		private void record(Staff staff) {
			try {
				con = DriverManager.getConnection(url, userName, pass);
				con.setAutoCommit(false);
				//画面に表示したい備品だけ表示(購入しなくなった備品については非表示にする)
				pstmt = con.prepareStatement("SELECT staffname FROM stafflist WHERE staffid = ? ");
				pstmt.setInt(1, staff.getStaffId());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					String staffName = rs.getString("staffname");
					staff.setStaffName(staffName);
				}
				rs.close();
				pstmt.close();
				con.commit();
			}catch(SQLException e) {
				try {
					con.rollback();
				}catch(SQLException e2) {
					e2.printStackTrace();
				}
			}finally {
				if(con != null) {
					try {
						con.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
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

	//入居者情報をすべて取得
	private void getTable(ArrayList<Staff> staffList) {
		try {
			con = DriverManager.getConnection(url,userName, pass);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement("SELECT * FROM stafflist WHERE display = 1");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int staffId = rs.getInt("staffid");
				String staffName = rs.getString("staffName");
				String authority = rs.getString("authority");

				Staff staff = new Staff(staffId, staffName, authority);
				staffList.add(staff);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//入居者情報の変更
	private void update(Staff staff) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE  stafflist\r\n" +
					"SET staffname = ? , authority = ?\r\n" +
					"WHERE staffid = ?");
			//ひな型に値を流し込み
			pstmt.setString(1, staff.getStaffName());
			pstmt.setString(2, staff.getAuthority());
			pstmt.setInt(3, staff.getStaffId());

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

	private void leave(int staffId) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE stafflist\r\n" +
					"SET display = 2 \r\n" +
					"WHERE staffId = ?");
			//ひな型に値を流し込み
			pstmt.setInt(1, staffId);

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

