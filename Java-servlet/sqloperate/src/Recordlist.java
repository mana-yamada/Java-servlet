
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;
public class Recordlist {

	String url;
	String userName;
	String pass;

	Connection con;
	Connection readCon;
	PreparedStatement pstmt;
	ResultSet rs;

	//inshed 発注して施設に届いた備品を倉庫に入れます
	public void in() {
		driverConnect();
		readFile();
		int controlId = 1;
		int occupantId = 0;
		int goodsId = 1;
		//倉庫から備品を出し入れした年・月・日
		String strDate = "2019-11-10"; Date date = Date.valueOf(strDate);
        //倉庫から備品を出し入れした時・分・秒  //実際には秒は00秒でデータを格納するようにする
        String strTime = "09:00:00"; Time time = Time.valueOf(strTime);
		int outshed = 0;
		int inshed = 10;
		int stock = 0;
		int staffId = 1;
//		search(occupantId, goodsId, date, time, outshed, inshed, stock, staffId);
		search(controlId, occupantId, goodsId, date, time, outshed, inshed, stock, staffId);
	}

	//outshed 備品を倉庫から出します
	public void out() {
		driverConnect();
		readFile();
		int controlId = 2;
		int occupantId = 1;
		int goodsId = 1;
		//倉庫から備品を出し入れした年・月・日
		String strDate = "2019-11-10"; Date date = Date.valueOf(strDate);
        //倉庫から備品を出し入れした時・分・秒  //実際には秒は00秒でデータを格納するようにする
        String strTime = "16:00:00"; Time time = Time.valueOf(strTime);
		int outshed = 1;
		int inshed = 0;
		int stock = 0;
		int staffId = 1;
//		search(occupantId, goodsId, date, time, outshed, inshed, stock, staffId);
		search(controlId, occupantId, goodsId, date, time, outshed, inshed, stock, staffId);
	}


//	private void inoutshed(int occupantid, int goodsid ,Date date, Time time, int outshed,int inshed,int stock,String staffid ) {
	private void inoutshed(int controlId, int occupantId, int goodsId ,Date date, Time time, int outshed,int inshed,int stock, int staffId ) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
//			pstmt = con.prepareStatement(""
//					+ "INSERT INTO recordlist("
//					+ "occupantid, goodsid, date, time, outshed, inshed, stock, staffId) "
//					+ "VALUES (?, ?, ?, ?, ?, ? ,?, ?)");
			pstmt = con.prepareStatement(""
					+ "INSERT INTO recordlist("
					+ "controlid, occupantid, goodsid, date, time, outshed, inshed, stock, staffId) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?)");
			//ひな型に値を流し込み
//			pstmt.setInt(1, occupantId);
//			pstmt.setInt(2, goodsId);
//			pstmt.setDate(3, date);
//			pstmt.setTime(4, time);
//			pstmt.setInt(5, outshed);
//			pstmt.setInt(6, inshed);
//			pstmt.setInt(7, stock);
//			pstmt.setInt(8, staffId);
			pstmt.setInt(1, controlId);
			pstmt.setInt(2, occupantId);
			pstmt.setInt(3, goodsId);
			pstmt.setDate(4, date);
			pstmt.setTime(5, time);
			pstmt.setInt(6, outshed);
			pstmt.setInt(7, inshed);
			pstmt.setInt(8, stock);
			pstmt.setInt(9, staffId);

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
	private void search(int controlId, int occupantId, int goodsId , Date date, Time time, int outshed,int inshed, int stock,int staffId ) {
		try {
			con = DriverManager.getConnection(url,userName,pass);
			//con = readCon;
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型
//			pstmt = con.prepareStatement(
//					"SELECT goodsid, date, time, outshed, inshed , stock\r\n" +
//					"FROM recordlist\r\n" +
//					"WHERE goodsid = ? \r\n" +
//					"ORDER BY date DESC, time DESC LIMIT 1\r\n" +
//					"");
			pstmt = con.prepareStatement(
					"SELECT controlid, goodsid, date, time, outshed, inshed , stock\r\n" +
					"FROM recordlist\r\n" +
					"WHERE goodsid = ? \r\n" +
					"ORDER BY date DESC, time DESC LIMIT 1\r\n" +
					"");
			//ひな型に値を流し込み
			pstmt.setInt(1,goodsId);
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
				//最後に倉庫から出し入れした時点での備品の残数を引数に代入する
				stock = rs.getInt("stock");
				//倉庫に入れた備品の数 + 最後に倉庫から出し入れした時点での備品の残数 + 倉庫から出した備品の数 = 現在の倉庫にある備品の残数
				stock = stock + inshed - outshed;
			}else {
				stock = stock + inshed - outshed;
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
					inoutshed(controlId, occupantId, goodsId, date, time, outshed, inshed, stock, staffId);
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
