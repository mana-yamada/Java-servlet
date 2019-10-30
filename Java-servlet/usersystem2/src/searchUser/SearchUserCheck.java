package searchUser;


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

@WebServlet("/SearchUserCheck")
public class SearchUserCheck extends HttpServlet {
	//入力されたユーザーID
	String strFormerUserId;
	//入力されたユーザーIDを整数化したもの
	int formerUserId;
	//DBに実際に入っているユーザーID
	int userId ;

	//フォームで入力したパスワード
	String formerPassword;
	//DBに実際に入っているパスワード
	String password;
	//DBから取り出し復号化したパスワード
	String openedPassword;

	//DB上で操作する名前
	String name;
	//DB上で操作するメールアドレス
	String mail;
	//DB上で操作する電話番号
	String tel;
	//エラー文を格納
	String error = "";

	//SQL処理を行うために必要な変数
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	//①searchUser.jspからリクエストパラメータを受け取る
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		//getEncodingを忘れない
		request.setCharacterEncoding("UTF-8");

		strFormerUserId = request.getParameter("userId"); //チェックする際にint型へ変換
		formerPassword = request.getParameter("password");//複合した文字列と一致しているかチェック

		//HTML出力
		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();

		if((strFormerUserId == null || strFormerUserId.length() == 0) ||
				(formerPassword == null || formerPassword.length() == 0)) {
			inputError(out);
		}else {
			//②データの確認⇒メソッド内でそれぞれの処理を呼び出し
			checkStrUserId(strFormerUserId, out);
		}



	}

	//③文字列で受け取ったuserIdをチェック&パスワードの文字列を確認
	public void checkStrUserId(String strFormerUserId, PrintWriter out) {
		try {
			//入力されたuserIdを整数化
			userId = Integer.parseInt(strFormerUserId);
			//openSecretPassword(out);
			//④指定するMySQLテーブルへ接続
			driverConnect(out);
		}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたとき
			out.println("<!DOCTYPE html>\r\n" +
					"<html lang=\"ja\">\r\n" +
					"<head>\r\n" +
					"<meta charset=\"UTF-8\">\r\n" +
					"<title></title>\r\n" +
					"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
					"</head>\r\n" +
					"<body>\r\n" +
					"<p>ユーザーIDが正しく入力されていません。</p>" + "\r\n" +
					"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
					"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
					"</form>" +
					"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
					"</body>\r\n" +
					"</html>\r\n" +
					"");

		}

	}


	//④SQLへの接続
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
			//⑤検索操作
			checkUser(URL,USERNAME,PASS,out);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//⑤ユーザー検索
	public void checkUser(String URL, String USERNAME, String PASS, PrintWriter out) {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			pstmt = null;
			rs = null;

			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ? ");
			//ひな型に値を流し込み
			pstmt.setInt(1,userId);
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
				userId = rs.getInt("userId");
				password = rs.getString("password");
				name = rs.getString("name");
				mail = rs.getString("mail");
				tel = rs.getString("tel");
				//⑥パスワードを復号化
				openSecretPassword(password, out);
			}else {
				//入力されたuserIdのデータがないときに出すHTMLを呼び出し
				userIdError(out);
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

	//⑥DB内に格納された暗号文字列を復号
	public void openSecretPassword(String password, PrintWriter out) {
		try {
			UsingCrypt usingCrypt = new UsingCrypt();
			openedPassword = usingCrypt.getToken(password);
			if(formerPassword.equals(openedPassword)) {
				//問題がなければHTMLで検索結果を出力する
				outputHTML(out);
			}else {
				//入力されたuserIdと登録したパスワードが一致しない時に出すHTMLを呼び出し
				passwordError(out);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//passwordが正しく入力されていなかったら出力するHTML
		public void inputError(PrintWriter out){
			out.println("<!DOCTYPE html>\r\n" +
					"<html lang=\"ja\">\r\n" +
					"<head>\r\n" +
					"<meta charset=\"UTF-8\">\r\n" +
					"<title></title>\r\n" +
					"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
					"</head>\r\n" +
					"<body>\r\n" +
					"<p>入力欄に間違いがあります。</p>"
					+ "お手数ですが最初から操作をやり直してください。</p>" + "\r\n" +
					"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
					"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
					"</form>" +
					"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
					"</body>\r\n" +
					"</html>\r\n" +
					"");
		}

	//入力されたuserIdのデータがない時に出力するHTML
	public void userIdError(PrintWriter out){
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>入力されたユーザーIDのデータはありません。</p>" + "\r\n" +
				"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	//passwordが正しく入力されていなかったら出力するHTML
	public void passwordError(PrintWriter out){
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>ユーザーIDとパスワードが一致しません。</p>" + "\r\n" +
				"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	//userIdが正しく入力されて場合に検索結果を出力するHTML
	public void outputHTML(PrintWriter out) {
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>ユーザーID：" + userId + "</p>" + "\r\n" +
				//下のコメントアウト3行を外すと、
				//入力したパスワードの文字列と
				//DBに暗号化して格納したパスワードを復号化した文字列と一致していることを確認できます
				//"<p>inputパスワード：" + formerPassword + "</p>" + "\r\n" +
				//"<p>outputパスワード：" + password + "</p>" + "\r\n" +
				//"<p>復号したパスワード：" + openedPassword + "</p>" + "\r\n" +
				"<p>名前：" + name + "</p>" + "\r\n" +
				"<p>メールアドレス：" + mail + "</p>" + "\r\n" +
				"<p>電話番号：" + tel + "</p>" + "\r\n" +
				"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}
}
