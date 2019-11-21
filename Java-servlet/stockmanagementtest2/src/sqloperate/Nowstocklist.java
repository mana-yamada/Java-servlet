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
import beans.Stock;

public class Nowstocklist {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//display 現在の備品残数を表示
	public void display(ArrayList<Stock> stockList) {
		driverConnect();
		readFile();
		search(stockList);
	}




	//add 備品名および残数の新規追加
	public void add(Stock stock) {
		driverConnect();
		readFile();

		insert(stock);
	}

	//updating残数の更新
	public void updating(Stock stock) {
		driverConnect();
		readFile();
		update(stock);
	}

	//changing 備品名の更新
	public void changing(Goods goods) {
		driverConnect();
		readFile();
		change(goods);
	}

	//nondisplay 指定した備品を非表示にする
	public void nondisplay(int goodsid) {
		driverConnect();
		readFile();
		undisplay(goodsid);
	}

	//search 備品残数を画面表示するために行う検索
	private void search(ArrayList<Stock> stockList) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("SELECT goodsname , nowstock FROM nowstocklist");
			//ひな型に値を流し込み
			//pstmt.setInt(1, occupant.getOccupantId());

			//更新系SQL文を自動組み立て送信
			rs = pstmt.executeQuery();
			//結果票の処理
			while(rs.next()) {
				String goodsName = rs.getString("goodsname");
				int nowStock = rs.getInt("nowstock");

				Stock parts = new Stock(goodsName,nowStock);
				stockList.add(parts);
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


	//insert 備品情報の新規追加
	private void insert(Stock stock) {
//		private void insert(int goodsId, String goods, int stock) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO nowstocklist(goodsname, nowstock)VALUES (?, ?)");
//			pstmt = con.prepareStatement("INSERT INTO nowstocklist( goodsid, goodsname, nowstock)VALUES (?, ?, ?)");
			//ひな型に値を流し込み
			pstmt.setString(1,stock.getGoodsName());
			pstmt.setInt(2, stock.getStock());


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



	//update 備品情報の更新
		private void update(Stock stock) {
			//DBへの接続
			try {
				//①接続・自動コミットモードの解除
				con = DriverManager.getConnection(url,userName,pass);
				con.setAutoCommit(false);
				//②SQL送信処理
				pstmt = con.prepareStatement("UPDATE nowstocklist\r\n" +
						"SET nowstock = ?\r\n" +
						"WHERE goodsid = ? ");
				//ひな型に値を流し込み
				pstmt.setInt(1, stock.getStock());
				pstmt.setInt(2, stock.getGoodsId());

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

		//change goodslistの備品名を変えたらこちらの備品名も変える!
		private void change(Goods goods) {
			//DBへの接続
			try {
				//①接続・自動コミットモードの解除
				con = DriverManager.getConnection(url,userName,pass);
				con.setAutoCommit(false);
				//②SQL送信処理
				pstmt = con.prepareStatement("UPDATE nowstocklist SET goodsname = ? WHERE goodsid = ?");
				//ひな型に値を流し込み
				pstmt.setString(1, goods.getGoodsName());
				pstmt.setInt(2, goods.getGoodsId());

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

		//undisplay 非表示とした備品名の現在のstockは表示しない
		private void undisplay(int goodsid) {
			//DBへの接続
			try {
				//①接続・自動コミットモードの解除
				con = DriverManager.getConnection(url,userName,pass);
				con.setAutoCommit(false);
				//②SQL送信処理
				pstmt = con.prepareStatement("UPDATE nowstocklist SET display = 2 WHERE goodsid = ?");
				//ひな型に値を流し込み
				pstmt.setInt(1, goodsid);

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
