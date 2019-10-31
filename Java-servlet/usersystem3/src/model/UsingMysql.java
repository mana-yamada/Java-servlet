package model;

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

import javax.servlet.ServletException;

import bean.UserBeans;
import controller.Add;

public class UsingMysql {

	Connection con;
	Connection readCon;
	PreparedStatement pstmt;
	ResultSet rs;

	//登録前のユーザーIDチェック
	public void check() {
		driverConnect();
		checkUserId();
	}
	//ユーザー登録
	public void add(UserBeans user) {
		driverConnect();
		addUser(user);
	}
	//ユーザー検索
	public void search() {
		driverConnect();
		searchUser();
	}
	//ユーザー削除
	public void remove() {
		driverConnect();
		removeUser();
	}
	//SQLのドライバー調整⇒1回接続
	private void driverConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\usersystem3\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();
			readCon = DriverManager.getConnection(URL,USERNAME,PASS);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//新規登録時のユーザーIDチェック詳細
	private void checkUserId()  {
		try {
			//Addクラス型の変数
			Add operateAdd = new Add();
			try {
				//①接続・自動コミットモードの解除
				//con = readCon; 接続済み！？
				con.setAutoCommit(false);
				//②SQL送信処理
				pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ?");
				//pstmt.setInt(1, userId);
				//検索系SQL文を自動組み立て送信
				rs = pstmt.executeQuery();
				//結果票の処理 //既にユーザーが登録されていたらエラー文を出す
				if(rs.next()) {
					try {
						//Addクラスに戻り、エラーページを呼び出すメソッドを作り出力する
						operateAdd.userIdError();
					}catch(ServletException e4) {
						e4.printStackTrace();
					}catch(IOException e5) {
						e5.printStackTrace();
					}
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

					}catch(SQLException e3) {
						e3.printStackTrace();
					}
				}
			}
		}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたときにはエラー文を出す
			//Addクラスに戻り、エラーページを呼び出すメソッドを作り出力する
		}
	}

	//ユーザーの新規登録データをテーブルに挿入①接続②SQL送信処理③切断
	//ユーザー登録の実装詳細
	private void addUser(UserBeans user) {
		//Addクラス型の変数
		Add addAdd = new Add();

		//DBへの接続
		try {
			//①接続・自動コミットモードの解除
			//con = readCon;
			con.setAutoCommit(false);
			//②SQL送信処理
			pstmt = con.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");
			//ひな型に値を流し込み
//			pstmt.setInt(1, userId);
//			pstmt.setString(2, password);
//			pstmt.setString(3, name);
//			pstmt.setString(4,mail);
//			pstmt.setString(5, tel);
			//更新系SQL文を自動組み立て送信
			int r = pstmt.executeUpdate();
			//結果票の処理
			if(r != 0) {
				try {
					//Addクラスに戻り、新規登録完了ページへ移動させるメソッドを呼び出す
					addAdd.addComplete();
				}catch(ServletException e4) {
					e4.printStackTrace();
				}catch(IOException e5) {
					e5.printStackTrace();
				}
			}else {
				System.out.println("データが正しく登録されませんでした。");
				//Addクラスに戻り、ユーザー登録時のエラー文を出せたら出したい
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

	//ユーザー検索画面でのユーザー検索
	private void searchUser() {
		try {
			con = readCon;
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ? ");
			//ひな型に値を流し込み
			//pstmt.setInt(1,userId);
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
//					userId = rs.getInt("userId");
//					password = rs.getString("password");
//					name = rs.getString("name");
//					mail = rs.getString("mail");
//					tel = rs.getString("tel");
				//searchクラスに戻り、復号するメソッドをつくり呼び出す
			}else {
				//searchクラスに戻り、エラーページを呼び出すメソッドを呼び出す
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
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	//ユーザーIDの削除
	private void removeUser(){
		try {
			con = readCon;
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("DELETE FROM users WHERE userId = ? ");
			//ひな型に値を流し込み
			//pstmt.setInt(1, userId);
			//更新系sqlの自動組み立て送信
			int r = pstmt.executeUpdate();
			if(r != 0) {
				//removeクラスに戻り、削除完了ページにフォワードするメソッドをつくり呼び出す
			}else {
				//removeクラスに戻り、エラーページにフォワードするメソッドをつくり呼び出す
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

}
