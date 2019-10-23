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

import org.apache.commons.lang3.RandomStringUtils;

import dosecret.DoSecret;
import dosecret.SecretSave;

@WebServlet("/SearchUserCheck")
public class SearchUserCheck extends HttpServlet {
	String strUserId;
	int userId ;
	String password;
	String formerPassword;
	String name;
	String mail;
	String tel;
	String userIdError = "";

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

//throws ServletException, IOException
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		//getEncodingを忘れない
		request.setCharacterEncoding("UTF-8");

		strUserId = request.getParameter("userId"); //チェックする際にint型へ変換
		password = request.getParameter(password);//複合した文字列と一致しているかチェック

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
			makeSecretPassword(out);
		}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたとき
			userIdError += "<p>ユーザーIDが正しく入力されていません。</p>";
			stateErrorUserId(userIdError, out);
		}

	}

	//パスワードを暗号化するメソッド
		public void  makeSecretPassword(PrintWriter out) {
			//データの準備
			//Initial Vector
			byte[] iv = RandomStringUtils.randomAlphanumeric(16).getBytes();
			//暗号解読キー
			byte[] key = "yamada_2019_key_".getBytes();
			//読みこんだinitial Vectorと暗号解読キーの保存
			SecretSave.writeBytes(iv, "iv");
			SecretSave.writeBytes(key, "secret");

			//暗号化の処理
			String source = password;
			String result = "";
			try {
				//DoSecretオブジェクトの生成
				 DoSecret c = new DoSecret(SecretSave.readBytes("secret"), SecretSave.readBytes("iv"));
				//暗号化した文字列を得る
					result = c.encrypto(source);
				//暗号化したパスワードをpassword変数へ代入する
					password = result;
				//MySQLへ接続
				driverConnect(out);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	//SQLのドライバー調整
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
			checkUserId(URL,USERNAME,PASS,out);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//ユーザーIDの検索
	public void checkUserId(String URL, String USERNAME, String PASS, PrintWriter out) {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASS);
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
			//結果票の処理 //ユーザーが登録されていなかったらエラー文を出す
			if(rs.next()) {
				userId = rs.getInt("userId");
				formerPassword = rs.getString("password");
				name = rs.getString("name");
				mail = rs.getString("mail");
				tel = rs.getString("tel");
				if(formerPassword.length() == password.length()) {
					outputHTML(out);
				}else {
				//パスワードが一致しないエラー文を出す
					stateErrorPassword(out);
				}
			}else {
			userIdError += "<p> ・入力したユーザーはユーザー登録されていません</p>";
			stateErrorUserId(userIdError, out);
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

	//userIdがないか正しく入力されていなかったら出力するHTML
	public void stateErrorUserId(String userIdError , PrintWriter out){
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				userIdError + "\r\n" +
				"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	//入力し、暗号化したパスワードがデータに入っている暗号文と一致しない場合に出すエラー文
	public void stateErrorPassword(PrintWriter out) {
		out.println("<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title></title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<p>入力したパスワードが正しく入力されていません。</p>"+ "\r\n" +
				"<form action = \"/usersystem2/searchUser/searchUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"ユーザー検索をやり直す\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>"+
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}

	//HTML出力する前にデータに格納されたパスワードを複合化する
//	public void passwordUnEncryption(String password, PrintWriter out) {
//		//実際には暗号化と複合化は別々に行われるのが普通なので
//				//暗号解読キーとInitial Vectorはファイルに保存しておき、実行時に読みこんで利用する
//
//				//ここでは学習のためにInitial Vectorと暗号解読キーをクラス内に書いておく
//
//
//				//①データの準備
//				//Initial Vector (暗号化時のスタートブロック用の初期値1(128ビット固定長))
//				byte[] iv = RandomStringUtils.randomAlphanumeric(16).getBytes();
//
//				//暗号解読キー(128ビット固定長)
//				byte[] key = "yamada_2019_key_".getBytes();
//
//				//②読みこんだInitial Vectorと暗号解読キーはファイルに保存しておく
//				//Initial Vectorの保存
//				SecretSave.writeBytes(iv,  "iv");
//				//暗号解読キーの保存
//				SecretSave.writeBytes(key, "secret");
//
//				//String source = password;
//				String result = password;
//				//⑤複合化の処理
//				try {
//					//DoSecretオブジェクトを生成する⇒解読キーとInitial Vectorをファイルから読みこむ
//					DoSecret c2 = new DoSecret(SecretSave.readBytes("secret"), SecretSave.readBytes("iv"));
//					//複合化した文字列を得る
//					result = c2.decrypto(result);
//
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				//out.println("<p>複合=" + result + "</p>");
//
//		//HTMLにて検索結果を出力
//		outputHTML(userId, result, name, mail, tel, out);
//	}

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
				"<p>パスワード：" + formerPassword + "</p>" + "\r\n" +
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
