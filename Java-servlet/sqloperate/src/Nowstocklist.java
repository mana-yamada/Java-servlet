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

public class Nowstocklist {
	String url;
	String userName;
	String pass;

	Connection con;
	Connection readCon;
	PreparedStatement pstmt;
	ResultSet rs;

	//add 備品名および残数の新規追加
	public void add() {
		driverConnect();
		readFile();
		int goodsId = 14;
		String goods = "衣料用漂白剤";
		int stock = 10;
		insert(goodsId,goods,stock);
	}

	//change 備品名や残数の更新
		public void change() {
			driverConnect();
			readFile();
			String goods = "衣料用漂白剤";
			int latterStock = 15;
			update(goods, latterStock);
		}

	//insert 備品情報の新規追加
	private void insert(int goodsId, String goods, int stock) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			//pstmt = con.prepareStatement("INSERT INTO nowstocklist(goodsname, nowstock)VALUES (?, ?, ?)");
			pstmt = con.prepareStatement("INSERT INTO nowstocklist( goodsid, goodsname, nowstock)VALUES (?, ?, ?)");
			//ひな型に値を流し込み
//			pstmt.setString(1,goods);
//			pstmt.setInt(2, price);
			pstmt.setInt(1,goodsId);
			pstmt.setString(2, goods);
			pstmt.setInt(3, stock);

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
	private void update(String goods, int latterStock) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE nowstocklist\r\n" +
					"SET nowstock = ?\r\n" +
					"WHERE goodsname = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1, latterStock);
			pstmt.setString(2, goods);

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

	//入力されたgoodsidの備品で最後に倉庫から出し入れした記録を抽出する
//	private void search(String goods, int price) {
//		try {
//			con = DriverManager.getConnection(url,userName,pass);
//			//con = readCon;
//			//自動コミットモードの解除
//			con.setAutoCommit(false);
//			//SQL送信処理
//			//ひな型
//			pstmt = con.prepareStatement(
//					"SELECT * \r\n" +
//					"FROM goodslist\r\n" +
//					"WHERE goods = ? ");
//			//ひな型に値を流し込み
//			pstmt.setString(1,goods);
//			//検索系SQL文を自動組み立て、送信
//			rs = pstmt.executeQuery();
//			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
//			if(rs.next()) {
//
//			}else {
//
//			}
//			//後片付け
//			rs.close();
//			pstmt.close();
//			//送信済みの処理要求の確定（コミット）
//			con.commit();
//		}catch(SQLException e){
//			e.printStackTrace();
//			//ロールバック
//			try {
//				con.rollback();
//			}catch(SQLException e2) {
//				e2.printStackTrace();
//			}
//		}finally {
//			//DB接続を切断
//			if(con != null) {
//				try {
//					con.close();
//
//				}catch(SQLException e3) {
//					e3.printStackTrace();
//				}
//			}
//		}
//	}



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
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\sqloperate\\MySQLdocs.properties");
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
