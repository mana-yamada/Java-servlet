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

	//get 現在入居されている方のリストをoccupantlistテーブルから取得
		public void get(ArrayList<Occupant> occupantList) {
			driverConnect();
			readFile();
			//DB全件取得
			getTable(occupantList);
		}


	//change 入居者の入居される階の数字、居室番号、氏名の変更
		public void change(Occupant occupant) {
			driverConnect();
			readFile();
			update(occupant);
		}

	//out 退居された入居者の情報を「退居済み」と設定
		public void out(Occupant occupant) {
			driverConnect();
			readFile();

			leave(occupant);
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

	//getTable 備品情報全体を取得、表示
	private void getTable(ArrayList<Occupant> occupantList) {
		try {
			con = DriverManager.getConnection(url, userName, pass);
			con.setAutoCommit(false);
			//画面に表示したい備品だけ表示(購入しなくなった備品については非表示にする)
			pstmt = con.prepareStatement("SELECT * FROM `occupantlist` WHERE display = '1' ORDER BY `occupantlist`.`roomnumber` ASC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int occupantId = rs.getInt("occupantid");
				int floorId = rs.getInt("floorid");
				int roomNumber = rs.getInt("roomnumber");
				String occupantName = rs.getString("occupantname");
				String display = rs.getString("display");
				//make occupantinstance
				Occupant occupant = new Occupant();
				//インデックス番号を追加しましょう！
				occupant.setOccupantId(occupantId);
				occupant.setFloorId(floorId);
				occupant.setRoomNumber(roomNumber);
				occupant.setOccupantName(occupantName);
				occupant.setDisplay(display);
				//add occupantinstance in occupantList
				occupantList.add(occupant);
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

	//入居者情報の変更
	private void update(Occupant occupant) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE occupantlist\r\n" +
					"SET floorid=?, roomnumber = ?, occupantname = ?\r\n" +
					"WHERE occupantid = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1, occupant.getFloorId());
			pstmt.setInt(2, occupant.getRoomNumber());
			pstmt.setString(3, occupant.getOccupantName());
			pstmt.setInt(4, occupant.getOccupantId());

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

	private void leave(Occupant occupant) {
		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			con = DriverManager.getConnection(url,userName,pass);
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("UPDATE occupantlist\r\n" +
					"SET display = '2' \r\n" +
					"WHERE occupantid = ?");
			//ひな型に値を流し込み
			pstmt.setInt(1, occupant.getOccupantId());
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

