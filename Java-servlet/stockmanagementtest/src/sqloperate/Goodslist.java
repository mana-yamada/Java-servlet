package sqloperate;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import beans.Goods;


public class Goodslist implements Serializable {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//add 備品名と単価の追加
	public void add() {
		driverConnect();
		readFile();
//		int goodsId = 14;
		String goods = "衣料用漂白剤";
		int price = 170;
		insert(goods,price);
//		insert(goodsId,goods,price);
	}


	//get 現在使っている備品一覧をgoodslistテーブルから取得
	public void get(ArrayList<Goods> goodsList) {
		driverConnect();
		readFile();
		//DB全件取得
		getTable(goodsList);
	}


	//change 備品名や単価の変更
	public void change() {
		driverConnect();
		readFile();
		//変更前の備品名
		String formerGoods = "テープ止めオムツS";
		//変更後の備品名・単価
		String latterGoods = "オムツS";
		int latterPrice = 2400;
		//備品情報を変更する前にSQLを取得
		//備品情報の変更
		update(formerGoods,latterGoods, latterPrice);
	}

	//insert 備品情報の新規追加
	private void insert(String goods, int price) {
//	private void insert(int goodsId, String goods, int price) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO goodslist(goodsname, price) VALUES (?, ?)");
			//pstmt = con.prepareStatement("INSERT INTO goodslist( goodsid, goodsname, price)VALUES (?, ?, ?)");
			//ひな型に値を流し込み
			pstmt.setString(1,goods);
			pstmt.setInt(2, price);
//			pstmt.setInt(1,goodsId);
//			pstmt.setString(2, goods);
//			pstmt.setInt(3, price);

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

	//getTable 備品情報全体を取得、表示
//	private void getTable(Goods goods) {
	private void getTable(ArrayList<Goods> goodsList) {
		try {
			con = DriverManager.getConnection(url, userName, pass);
			con.setAutoCommit(false);
			//1：画面に表示 2：画面に非表示
			pstmt = con.prepareStatement("SELECT * FROM `goodslist` WHERE display = '1' ORDER BY `goodslist`.`goodsname` DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int goodsId = rs.getInt("goodsid");
				String goodsName = rs.getString("goodsname");
				int price = rs.getInt("price");
				String display = rs.getString("display");
				//make goodsinstance
				Goods goods = new Goods();
				goods.setGoodsId(goodsId);
				goods.setGoodsName(goodsName);
				goods.setGoodsPrice(price);
				goods.setDisplay(display);
				goodsList.add(goods);
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


	//update 備品情報の更新
	private void update(String formerGoods, String latterGoods, int latterPrice) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE goodslist\r\n" +
					"SET goodsname = ? , price = ?\r\n" +
					"WHERE goodsname = ? ");
			//ひな型に値を流し込み
			pstmt.setString(1,latterGoods);
			pstmt.setInt(2, latterPrice);
			pstmt.setString(3, formerGoods);

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
