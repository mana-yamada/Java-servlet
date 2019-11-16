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

import beans.Goods;


public class Goodslist  {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//add 備品名と単価の追加
	public void add(Goods goods) {
		driverConnect();
		readFile();
		insert(goods);
	}


	//get 現在使っている備品一覧をgoodslistテーブルから取得
	public void get(ArrayList<Goods> goodsList) {
		driverConnect();
		readFile();
		//DB全件取得
		getTable(goodsList);
	}


	//change 備品名や単価の変更
	public void change(int goodsId, String goodsName, int goodsPrice) {
		driverConnect();
		readFile();
		//備品情報の変更
		update(goodsId, goodsName, goodsPrice);
	}

	//display 登録されていた備品名と単価データの非表示 or 再表示の設定
	public void display(int goodsId) {
		driverConnect();
		readFile();
		changeDisplay(goodsId);
	}

	//insert 備品情報の新規追加
	private void insert(Goods goods) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO goodslist(goodsname, price) VALUES (?, ?)");
			//ひな型に値を流し込み
			pstmt.setString(1,goods.getGoodsName());
			pstmt.setInt(2, goods.getGoodsPrice());
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
	private void getTable(ArrayList<Goods> goodsList) {
		try {
			con = DriverManager.getConnection(url, userName, pass);
			con.setAutoCommit(false);
			//画面に表示したい備品だけ表示(購入しなくなった備品については非表示にする)
			pstmt = con.prepareStatement("SELECT * FROM `goodslist` WHERE display = '1' ORDER BY `goodslist`.`goodsname` DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int goodsId = rs.getInt("goodsid");
				String goodsName = rs.getString("goodsname");
				int price = rs.getInt("price");
				String display = rs.getString("display");
				//make goodsinstance
				Goods goods = new Goods();
				//インデックス番号を追加しましょう！

				goods.setGoodsId(goodsId);
				goods.setGoodsName(goodsName);
				goods.setGoodsPrice(price);
				goods.setDisplay(display);
				//add goodsinstance in goodsList
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
	private void update(int goodsId, String goodsName, int goodsPrice) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE goodslist SET goodsname = ? , price = ? WHERE goodsid = ? ");

			//ひな型に値を流し込み
			pstmt.setString(1, goodsName);
			pstmt.setInt(2, goodsPrice);
			pstmt.setInt(3,goodsId);

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

	//changeDisplay 登録されていた備品名と単価データの非表示 or 再表示の設定
	private void changeDisplay(int goodsId){
		try {
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("UPDATE goodslist SET display = '2' WHERE goodsid = ?");
			pstmt.setInt(1, goodsId );

			int r = pstmt.executeUpdate();
			if(r != 0) {
				System.out.println("データが正しく登録されました");
			}else {
				System.out.println("データが正しく登録されませんでした。");
			}
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
