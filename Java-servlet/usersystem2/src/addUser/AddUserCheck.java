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

@WebServlet("/AddUserCheck")
public class AddUserCheck extends HttpServlet {
	String strUserId;
	int userId ;
	String password;
	String name;
	String mail;
	String tel;
	String userIdState = "";
	String userIdError = "";
	String errorMsg = "";
	String msg = "";

//throws ServletException, IOException
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		//getEncodingを忘れない
		request.setCharacterEncoding("UTF-8");

		strUserId = request.getParameter("userId"); //チェックする際にint型へ変換
	    password  = request.getParameter("password");
		name 	 = request.getParameter("name");
		mail      = request.getParameter("mail");
		tel 		 = request.getParameter("tel");

		//passwordチェック
		if(password.length() == 0 || password == null ) {
			errorMsg += "<p>・パスワードが入力されていません。</p>";
		}else if(password.length() < 8 && password.length() > 20) {
			errorMsg += "<p>" +  "・パスワードが8文字以上20文字以内で正しく入力されていません。" + "</p>";
		}else {
			msg += "<p>パスワード：*************</p>";
		}
		//nameチェック
		if( name.length() == 0 || name == null) {
			errorMsg  += "<p>・名前が入力されていません。 </p>";
		}else if(name.length() > 50) {
			errorMsg  += "<p>" + "・名前が長すぎです。(50文字以内)" + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>名前：" + name + "</p>";
		}
		//mailチェック
		if( mail.length() == 0 || mail  == null) {
			errorMsg += "<p>・メールアドレスが入力されていません。</p>";
		}else if(mail.length() > 50) {
			errorMsg += "<p>" + "・メールアドレスが長すぎです。(50文字以内)"  + "</p>";
		}else {
			msg += "<p>メールアドレス：" + mail + "</p>";
		}
		//telチェック
		if( tel.length() == 0 || tel == null) {
			errorMsg += "<p>・電話番号が入力されていません。</p>";
			//System.out.println(errorMsg);
		}else if(tel.length() > 15) {
			errorMsg += "<p>" + "・電話番号が長すぎです。(15文字以内)" + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>電話番号：" + tel + "<p>";
			//System.out.println(msg);
		}

		//HTML出力
		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();

		//データの確認⇒メソッド内でそれぞれの処理を呼び出し
		checkStrUserId(strUserId, out);

	}

	//文字列で受け取ったuserIdをチェック
	public void checkStrUserId(String strUserId, PrintWriter out) {
		try {
			//入力されたuserIdを整数化
			userId = Integer.parseInt(strUserId);
			//DB接続&SQL検索
			driverConnect();
			checkUserIdSQL(out);
		}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたとき
			userIdError += "<p>ユーザーIDが正しく入力されていません。</p>";
			stateErrorUserId(userIdError, out);
		}
	}

	//SQLのドライバー調整
	public void driverConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
	}

	//ユーザーIDがチェック時のプロパティファイルから接続先のデータを読み込み
	public void checkUserIdSQL( PrintWriter out ) {
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\usersystem2\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();
			//connection(URL, USERNAME, PASS);
			//DBへの接続
			Connection con = null;
			try {
				con = DriverManager.getConnection(URL, USERNAME, PASS);
				//自動コミットモードの解除
				con.setAutoCommit(false);
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//SQL操作 //新規登録前のuserIdを検索
				checkUserId(con, pstmt, rs, out);
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
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//ユーザーIDのチェック
	public void checkUserId(Connection con , PreparedStatement pstmt ,ResultSet rs ,PrintWriter out) throws SQLException{
		//SQL送信処理
		//ひな型
		pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ?");
		//ひな型に値を流し込み
		pstmt.setInt(1, userId);
		//検索系SQL文を自動組み立て、送信
		rs = pstmt.executeQuery();
		//結果票の処理 //既にユーザーが登録されていたらエラー文を出す
		if(rs.next()) {
			userIdError += "<p> ・入力したユーザーはユーザー登録されています</p>";
			//System.out.println(errorMsg);
			stateErrorUserId(userIdError, out);
		}else {
			userIdState += "<p>ユーザーID：" + userId + "</p>";
			//System.out.println(msg);
			stateUserId(out);
		}
		//後片付け
		rs.close();
		pstmt.close();
		//送信済みの処理要求の確定（コミット）
		con.commit();
	}

	//userIdの状態によって最後に出力するものを判定
	public void stateErrorUserId(String userIdError , PrintWriter out){
		//System.out.println(userIdError);
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				userIdError + "\r\n" +
				"<form action = \"/usersystem2/addUser/addUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"新規登録をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	public void stateUserId(PrintWriter out) {
		if(errorMsg.length() != 0) {
			out.println("<!DOCTYPE html>\r\n" +
					"<html lang=\"ja\">\r\n" +
					"<head>\r\n" +
					"<meta charset=\"UTF-8\">\r\n" +
					"<title></title>\r\n" +
					"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
					"</head>\r\n" +
					"<body>\r\n" +
					userIdState + errorMsg + "\r\n" +
					"<form action = \"/usersystem2/addUser/addUser.jsp\" method = \"get\">" +
					"<input type = \"submit\" value =\"新規登録をやり直す\">"+"\r\n" +
					"</form>" +
					"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
					"</body>\r\n" +
					"</html>\r\n" +
					"");
		}else {
			//データを送るSQLを呼び出す
			driverConnect();
			insertUserDataSQL(out);
		}
	}

	//SQLに入力したデータを送る
	public void insertUserDataSQL( PrintWriter out ) {
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-servlet\\Java-servlet\\usersystem2\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();

			//DBへの接続
			Connection con = null;
			try {
				con = DriverManager.getConnection(URL, USERNAME, PASS);
				//自動コミットモードの解除
				con.setAutoCommit(false);
				PreparedStatement pstmt = null;
				//SQL操作 //新規登録前のuserIdを検索
				insertUserData(con, pstmt, out);
				out.println("<!DOCTYPE html>\r\n" +
						"<html lang=\"ja\">\r\n" +
						"<head>\r\n" +
						"<meta charset=\"UTF-8\">\r\n" +
						"<title></title>\r\n" +
						"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
						"</head>\r\n" +
						"<body>\r\n" +
						"<h3>登録が完了しました</h3>"+ "\r\n" +
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
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//SQLに入力したデータを送るSQL本文
	public void insertUserData(Connection con , PreparedStatement pstmt  ,PrintWriter out) throws SQLException{
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
	}
}
