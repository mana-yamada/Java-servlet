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

import beans.Occupant;

public class Occupantlist {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//add 入居者の入居される階の数字、居室番号、氏名を追加
	public void add(Occupant occupant) {
		driverConnect();
		readFile();
		insert(occupant);
	}

	//add 入居者の入居される階の数字、居室番号、氏名の変更
		public void change() {
			driverConnect();
			readFile();
			int floorId = 1;
			int roomNumber = 101;
			//変更前の氏名
			String beforeName = "徳川綱吉";
			//変更後の氏名
			String occupantName = "徳川綱吉";
			update(floorId, roomNumber, occupantName, beforeName);
		}

	//out 退居された入居者の情報を「退居済み」と設定
		public void out() {
			driverConnect();
			readFile();
			String goout = "2";
			String occupantName = "徳川綱吉";
			leave(goout, occupantName);
		}


	//insert 入庫者情報の新規追加
		private void insert(Occupant occupant) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO occupantlist(floorid, roomnumber, occupantname)\r\n" +
					"VALUES (?,?,?)");
			//ひな型に値を流し込み
			pstmt.setInt(1, occupant.getFloorId());
			pstmt.setInt(2, occupant.getRoomNumber());
			pstmt.setString(3, occupant.getOccupantName());

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
	private void update(int floorId, int roomNumber, String occupantName , String beforeName) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE occupantlist\r\n" +
					"SET floorid=?, roomnumber = ?, occupantname = ?\r\n" +
					"WHERE occupantname = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1, floorId);
			pstmt.setInt(2, roomNumber);
			pstmt.setString(3, occupantName);
			pstmt.setString(4, beforeName);

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

	//テーブル「occupantist」内のカラム「goout」：CHAR(1)型
		//'1'入居中
		//'2'退居済み
	//退居された入居者のデータを「退居済み」と設定(メソッド名：leave)
	//退居された入居者が再入居した場合は「入居中」と設定
	private void leave(String goout, String occupantName) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE occupantlist\r\n" +
					"SET goout = ?\r\n" +
					"WHERE occupantname = ?");
			//ひな型に値を流し込み
			pstmt.setString(1, goout);
			pstmt.setString(2, occupantName);

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

