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

			String createFront = "CREATE TABLE 'stockmanagement'" + year + "year" + month + "monthKosuu ( 'occupantid' INT PRIMARY KEY, 'occupantname' VARCHAR(20),";

			for(Goods goods: goodsList) {
				int goodsId = goods.getGoodsId();
				//String goodsName = goods.getGoodsName();
				String pushGoods = goodsId + " " + "INT NOT NULL DEFAULT 0,";
				createFront += pushGoods;
			}

			String createBack = " 'sum'  INT NOT NULL DEFAULT 0)";

			String createTable = createFront + createBack;

			pstmt = con.prepareStatement(createTable);

			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				System.out.println("テーブルが正しく作られました。");
			}else {
				System.out.println("テーブルが正しく作られませんでした。");
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
