package addUser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UsingCrypt.UsingCrypt;

//import UsingCrypt.UsingCrypt;

@WebServlet("/AddUserCheck")
public class AddUserCheck extends HttpServlet  {
	String strUserId;
	int userId ;
	String password;
	String name;
	String mail;
	String tel;

	String userIdError = "";
	String errorMsg = "";
	String msg = "";


	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//throws ServletException, IOException
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		//getEncodingを忘れない
		request.setCharacterEncoding("UTF-8");

		strUserId = request.getParameter("userId"); //チェックする際にint型へ変換
	    password  = request.getParameter("password");
		name 	 = request.getParameter("name");
		mail      = request.getParameter("mail");
		tel 		 = request.getParameter("tel");

		//HTML出力
		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();

		//入力されたものを確認 いずれか1つでも正しく入力されていないものがあったらエラー文をだす
		if((password.length() == 0 || password == null) || (name.length() == 0 || name == null) ||
				(mail.length() == 0 || mail  == null) ||(tel.length() == 0 || tel == null) ) {
			stateErrorInput(out);
		}else {
			//パスワードを暗号化⇒メソッド内でドライバ接続、SQL送信
			makeSecretPassword(out);
		}

	}

	//passwordの暗号化
	public void makeSecretPassword(PrintWriter out) {
		try {
			UsingCrypt usingCrypt = new UsingCrypt();
			password = usingCrypt.saveToken(password);
			//SQLの接続
			driverConnect(out);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//SQLのドライバー調整&ユーザー検索
	public void driverConnect(PrintWriter out) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\usersystem2\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();
			//DBへの接続
			//ユーザーID検索
			checkUserId(URL,USERNAME,PASS, out);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}



		//ユーザーID検索
		public void checkUserId(String URL, String USERNAME, String PASS, PrintWriter out) {
			try {
				//入力されたuserIdを整数化
				userId = Integer.parseInt(strUserId);
				try {
					con = DriverManager.getConnection(URL,USERNAME,PASS);
					//自動コミットモードの解除
					con.setAutoCommit(false);
					pstmt = null;
					rs = null;

					//SQL送信処理
					//ひな型
					pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ?");
					//ひな型に値を流し込み
					pstmt.setInt(1, userId);
					//検索系SQL文を自動組み立て、送信
					rs = pstmt.executeQuery();
					//結果票の処理 //既にユーザーが登録されていたらエラー文を出す

					if(rs.next()) {
						usedUserId(out);
					}

					//後片付け
					rs.close();
					pstmt.close();
					//送信済みの処理要求の確定（コミット）
					con.commit();

					//データを入れるメソッドへ
					insertUserData(URL, USERNAME, PASS, out);
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
				stateErrorInput(out);
			}
	}

	//IDが既に別のユーザーで登録されていた時
	public void usedUserId(PrintWriter out){
		//System.out.println(userIdError);
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>入力したユーザーIDは既に入力されています。</p>" + "\r\n" +
				"<form action = \"/usersystem2/addUser/addUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"新規登録をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}
	//ユーザーIDなどの入力欄での入力が正しく入力されていなかった時
	public void stateErrorInput(PrintWriter out){
		//System.out.println(userIdError);
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>正しく入力されていない箇所がありました。</p>"
				+ "<p>お手数ですがもう一度最初からやり直してください。</p>" + "\r\n" +
				"<form action = \"/usersystem2/addUser/addUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"新規登録をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	//SQLに入力したデータを送る
	public void insertUserData(String URL, String USERNAME, String PASS, PrintWriter out) {

			//DBへの接続
			con = null;
			try {
				con = DriverManager.getConnection(URL,USERNAME,PASS);
				//自動コミットモードの解除
				con.setAutoCommit(false);
				pstmt = null;

				//SQL送信処理
				//ひな型
				pstmt = con.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");
				//ひな型に値を流し込み
				pstmt.setInt(1, userId);
				pstmt.setString(2, password);
				pstmt.setString(3, name);
				pstmt.setString(4,mail);
				pstmt.setString(5, tel);

				//更新系SQL文を自動組み立て、送信
				int r = pstmt.executeUpdate();
				//結果票の処理
				if(r != 0) {
					out.println("データを登録しました");
				}else {
					System.out.println("データが正しく登録されませんでした。");
				}
				//後片付け
				pstmt.close();
				//送信済みの処理要求の確定（コミット）
				con.commit();

				out.println("<!DOCTYPE html>\r\n" +
						"<html lang=\"ja\">\r\n" +
						"<head>\r\n" +
						"<meta charset=\"UTF-8\">\r\n" +
						"<title></title>\r\n" +
						"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
						"</head>\r\n" +
						"<body>\r\n" +
						"<h3>登録が完了しました</h3>"+ "\r\n" +
						"<p>・ユーザーID：" + userId + "</p>"+
						"<p>・パスワード：" + "**********</p>"+
						"<p>・名前：" + name +  "</p>"+
						"<p>・メールアドレス：" + mail +  "</p>"+
						"<p>・電話番号：" + tel + "</p>"+
						"<form action = \"/usersystem2/addUser/addUser.jsp\" method = \"get\">" +
						"<input type = \"submit\" value =\"新規登録を続ける\">"+"\r\n" +
						"</form>" +
						"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
						"</body>\r\n" +
						"</html>\r\n" +
						"");
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
