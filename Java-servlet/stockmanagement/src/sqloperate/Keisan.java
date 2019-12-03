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
import beans.Occupant;
import beans.Table;

public class Keisan {
	String url;
	String userName;
	String pass;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//making 1カ月の備品の使用個数テーブルを作成
    public void making(Table table) {


    	driverConnect();
    	readFile();
    	make(table);
    }

	private void make(Table table) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			String year = table.getYear();
			String month = table.getMonth();

			ArrayList<Goods> goodsList = table.getGoodsList();

			String createFront = "CREATE TABLE"+ " " + "year" + year + "month" + month + " " + "(occupantid INT PRIMARY KEY, occupantname VARCHAR(20)," ;

			for(Goods goods: goodsList) {
//				int goodsId = goods.getGoodsId();
				String goodsName = goods.getGoodsName();
//				String pushGoods = "goods" +  goodsId  + " " +  "INT NOT NULL DEFAULT 0,";
				String pushGoods = goodsName + " " +  "INT NOT NULL DEFAULT 0,";
				createFront += pushGoods;
			}

			String createBack = "sum INT NOT NULL DEFAULT 0)";

			String createTable = createFront + createBack;

			pstmt = con.prepareStatement(createTable);

			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				System.out.println("テーブルが正しく作られませんでした。");
			}else {
				System.out.println("テーブルが正しく作られました。");
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

	//adding 1カ月の備品の使用個数テーブルに利用者名を挿入
	public void adding(Table table) {
		driverConnect();
		readFile();
		add(table);

	}

	private void add(Table table) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			String year = table.getYear();
			String month = table.getMonth();

			ArrayList<Occupant> occupantList = table.getOccupantList();

			String insertFront = "INSERT INTO year" + year + "month" + month + " (occupantid, occupantname) VALUES ";

			for(Occupant occupant: occupantList) {
				int occupantId = occupant.getOccupantId();
				String occupantName = occupant.getOccupantName();

				String pushOccupant = "(" + occupantId + ", '" + occupantName + "'),";
				insertFront += pushOccupant;
			}

			String insertBack = "( 0 , 'Staff' );" ;

			String insertTable = insertFront + insertBack;

			pstmt = con.prepareStatement(insertTable);

			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				System.out.println("入居者のデータが正しく挿入されました。");
			}else {
				System.out.println("入居者のデータが正しく挿入されませんでした。");
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
