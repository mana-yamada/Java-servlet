package removeUser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/RemoveUserCheck")
public class RemoveUserCheck extends HttpServlet {
	String strUserId;
	int userId ;
	String userIdError = "";

//throws ServletException, IOException
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		//getEncodingを忘れない
		request.setCharacterEncoding("UTF-8");

		strUserId = request.getParameter("userId"); //チェックする際にint型へ変換

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

	//ユーザーIDのデータを削除時のプロパティファイルから接続先のデータを読み込み
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
				//SQL操作 //新規登録前のuserIdを検索
				removeUserId(con, pstmt , out);
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

	//ユーザーIDの削除
	public void removeUserId(Connection con , PreparedStatement pstmt ,PrintWriter out) throws SQLException{
		//SQL送信処理
		//ひな型
		pstmt = con.prepareStatement("DELETE FROM users WHERE userId = ? ");
		//ひな型に値を流し込み
		pstmt.setInt(1, userId);

		//更新系sqlの自動組み立て送信
		int r = pstmt.executeUpdate();
		if(r != 0) {
			outputHTML(out);
		}else {
			userIdError += "<p> ・入力したユーザーはユーザー登録されていません</p>";
			stateErrorUserId(userIdError, out);
		}

		//後片付け
		pstmt.close();
		//送信済みの処理要求の確定（コミット）
		con.commit();
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
				"<form action = \"/usersystem2/removeUser/removeUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"削除画面へ戻る\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>" +

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
				"<p>削除が完了しました</p>"+
				"<form action = \"/usersystem2/removeUser/removeUser.jsp\" method = \"get\">" +
				"<input type = \"submit\" value =\"削除画面へ戻る\">"+"\r\n" +
				"</form>" +
				"<a href =\"/usersystem2/index/index.jsp\"><input type = \"submit\" value = \"TOPへ戻る\"></a>" +
				"</body>\r\n" +
				"</html>\r\n" +
				"");
	}
}
