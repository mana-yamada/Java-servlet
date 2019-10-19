package addUserTest;

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

public class SQLTest {


	//データ受信
	public void inputData() {
		String strUserId = "2";
		String password = "1asdg2sddf3sddf4dddf";
		String name = "Nokko Yamada";
		String mail = "nokkoyamada@test.com";
		String tel = "10112022303";

		String userIdState = "";
		String userIdError = "";
		String errorMsg = "";
		String msg = "";

		//passwordチェック
		if(password.length() == 0 || password == null ) {
			errorMsg += "<p>・パスワードが入力されていません。</p>";
			//System.out.println(errorMsg);
		}else if(password.length() < 8 || password.length() > 20) {
			errorMsg += "<p>" +  "・パスワードが8文字以上20文字以内で正しく入力されていません。" + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>パスワード：*************</p>";
			//System.out.println(msg);
		}
		//nameチェック
		if( name.length() == 0 || name == null) {
			errorMsg  += "<p>・名前が入力されていません。 </p>";
			//System.out.println(errorMsg);
		}else if(name.length() > 50) {
			errorMsg  += "<p>" + "・名前が長すぎです。(50文字以内)" + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>名前：" + name + "</p>";
			//System.out.println(msg);
		}
		//mailチェック
		if( mail.length() == 0 || mail  == null) {
			errorMsg = "<p>メールアドレスが入力されていません。</p>";
			//System.out.println(errorMsg);
		}else if(mail.length() > 50) {
			errorMsg += "<p>" + "・メールアドレスが長すぎです。(50文字以内)"  + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>メールアドレス：" + mail + "</p>";
			//System.out.println(msg);
		}
		//telチェック
		if( tel.length() == 0 || tel == null) {
			errorMsg = "<p>・電話番号が入力されていません。</p>";
			//System.out.println(errorMsg);
		}else if(tel.length() > 15) {
			errorMsg += "<p>" + "・電話番号が長すぎです。(15文字以内)" + "</p>";
			//System.out.println(errorMsg);
		}else {
			msg += "<p>電話番号：" + tel + "<p>";
			//System.out.println(msg);
		}

		checkStrUserId(strUserId, userIdState, userIdError, errorMsg, msg);


	}

	//文字列で受け取ったuserIdをチェック
	public void checkStrUserId(String strUserId , String userIdState, String userIdError ,String errorMsg, String msg) {
		try {
			//入力されたuserIdを整数化
			int userId = Integer.parseInt(strUserId);
			//DB接続&SQL検索
			driverConnect();
			usingDB(userIdState, userIdError, userId, errorMsg, msg);
		}catch(NumberFormatException e) {
			//ユーザーIDが全く入力されていないときのエラー文かつ
			//ユーザーIDで文字入力されたときかつ
			//ユーザーIDがint型の範囲を超えたとき
			userIdError += "<p>ユーザーIDが正しく入力されていません。</p>";
			stateErrorUserId(userIdError);
			//System.out.println(errorMsg);
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

	//プロパティファイルから接続先のデータを読み込み
	public void usingDB(String userIdState, String userIdError, int userId, String errorMsg, String msg) {
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
				checkUserId(con, pstmt, rs, userIdState, userIdError, userId, errorMsg, msg);
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
	public void checkUserId(Connection con , PreparedStatement pstmt ,ResultSet rs , String userIdState, String userIdError, int userId, String errorMsg, String msg) throws SQLException{
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
			stateErrorUserId(userIdError);
		}else {
			userIdState += "<p>ユーザーID：" + userId + "</p>";
			//System.out.println(msg);
			stateUserId(userIdState, errorMsg, msg);

		}
		//後片付け
		rs.close();
		pstmt.close();
		//送信済みの処理要求の確定（コミット）
		con.commit();
	}


	//userIdの状態
	public void stateErrorUserId(String userIdError){
		System.out.println(userIdError);
	}
	public void stateUserId(String userIdState, String errorMsg, String msg) {
		System.out.println(userIdState);
		if(errorMsg.length() != 0) {
			System.out.println(errorMsg);
		}else {
			System.out.println(msg);
		}
	}

//	//userId以外のデータの状態
//	public void errorStateData(String userIdError ,String errorMsg, String msg) {
//
//	}
//	public void notErrorStateData(String userIdState, String errorMsg, String msg ) {
//		if(errorMsg.length() != 0) {
//			System.out.println(errorMsg);
//		}else {
//			System.out.println(msg);
//		}
//	}




}
