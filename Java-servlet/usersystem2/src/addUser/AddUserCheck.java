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

import org.apache.commons.lang3.RandomStringUtils;

import dosecret.DoSecret;
import dosecret.SecretSave;

@WebServlet("/AddUserCheck")
public class AddUserCheck extends HttpServlet  {
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


		//passwordチェック
		if(password.length() == 0 || password == null ) {
			errorMsg += "<p>・パスワードが入力されていません。</p>";
		}else {
			msg += "<p>パスワード：*************</p>";
		}
		//nameチェック
		if( name.length() == 0 || name == null) {
			errorMsg  += "<p>・名前が入力されていません。 </p>";
		}else {
			msg += "<p>名前：" + name + "</p>";
		}
		//mailチェック
		if( mail.length() == 0 || mail  == null) {
			errorMsg += "<p>・メールアドレスが入力されていません。</p>";
		}else {
			msg += "<p>メールアドレス：" + mail + "</p>";
		}
		//telチェック
		if( tel.length() == 0 || tel == null) {
			errorMsg += "<p>・電話番号が入力されていません。</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>電話番号：" + tel + "<p>";
			//System.out.println(msg);
		}


		//HTML出力
		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();

		//パスワードを暗号化
		makeSecretPassword(out);
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
							userIdError += "<p> ・入力したユーザーはユーザー登録されています</p>";
							//System.out.println(errorMsg);
							stateErrorUserId(out);
							//breakを忘れない！！
						}else {
							userIdState += "<p>ユーザーID：" + userId + "</p>";
							//System.out.println(msg);
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
				//ユーザーIDがint型の範囲を超えたとき
				userIdError += "<p>ユーザーIDが正しく入力されていません。</p>";
				stateErrorUserId(out);
			}
	}

	//IDが既に別のユーザーで登録されていた時
	public void stateErrorUserId(PrintWriter out){
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
						msg +
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
