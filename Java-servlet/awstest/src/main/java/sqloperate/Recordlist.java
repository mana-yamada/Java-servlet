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

import beans.Record;

public class Recordlist {

	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//inshed 発注して施設に届いた備品を倉庫に入れます
	public void inout(Record record) {
		driverConnect();
		readFile();
		int stock = 0;
		search(record, stock);
	}

	//outshed 備品を倉庫から出します
	public void out(Record record) {
		driverConnect();
		readFile();
		int stock = 0;
		search(record, stock);
	}

	private void inoutshed(Record record, int stock) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement(""
					+ "INSERT INTO recordlist("
					+ "occupantid, goodsid, date, time, outshed, inshed, stock, staffId) "
					+ "VALUES (?, ?, ?, ?, ?, ? ,?, ?)");
			//ひな型に値を流し込み
			pstmt.setInt(1, record.getOccupantId());
			pstmt.setInt(2, record.getGoodsId());
			pstmt.setDate(3, record.getDate());
			pstmt.setTime(4, record.getTime());
			pstmt.setInt(5, record.getOutshed());
			pstmt.setInt(6, record.getInshed());
			pstmt.setInt(7, stock);
			pstmt.setInt(8, record.getStaffId());


			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				record.setStock(stock);
				System.out.println("備品入出庫データが正しく登録されました。");
			}else {
				System.out.println("備品入出庫データが正しく登録されませんでした。");
			}
			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException ee){
			ee.printStackTrace();
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

	//入力されたgoodsidの備品で最後に倉庫から出し入れした記録を抽出する
//	private void search(int occupantid, int goodsid , Date date, Time time, int outshed,int inshed, int stock,String staffid ) {
	private void search(Record record, int stock ) {
		try {
			con = DriverManager.getConnection(url,userName,pass);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型

			pstmt = con.prepareStatement(
//					"SELECT goodsid, date, time, outshed, inshed , stock\r\n" +
//					"FROM recordlist\r\n" +
//					"WHERE goodsid = ? \r\n" +
//					"ORDER BY date DESC, time DESC LIMIT 1\r\n" +
					"SELECT goodsid, date, time, outshed, inshed , stock FROM recordlist WHERE goodsid = ? ORDER BY controlid DESC LIMIT 1"
					);
			//ひな型に値を流し込み
			pstmt.setInt(1,record.getGoodsId());
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
				//最後に倉庫から出し入れした時点での備品の残数を引数に代入する
				stock = rs.getInt("stock");
				//倉庫に入れた備品の数 + 最後に倉庫から出し入れした時点での備品の残数 + 倉庫から出した備品の数 = 現在の倉庫にある備品の残数
				stock = stock + record.getInshed() - record.getOutshed();
			}else {
				stock = stock + record.getInshed() - record.getOutshed();
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
//					inoutshed(occupantid, goodsid, date, time, outshed, inshed, stock, staffid);
					inoutshed(record, stock);
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
