package dosecret;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

@WebServlet("/DoSecretSample")
public class DoSecretSample extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {

	//public static void main(String[] args) {

		//実際には暗号化と複合化は別々に行われるのが普通なので
		//暗号解読キーとInitial Vectorはファイルに保存しておき、実行時に読みこんで利用する

		//ここでは学習のためにInitial Vectorと暗号解読キーをクラス内に書いておく


		//①データの準備
		//Initial Vector (暗号化時のスタートブロック用の初期値1(128ビット固定長))
		byte[] iv = RandomStringUtils.randomAlphanumeric(16).getBytes();

		//暗号解読キー(128ビット固定長)
		byte[] key = "yamada_2015_key_".getBytes();

		//②読みこんだInitial Vectorと暗号解読キーはファイルに保存しておく
		//Initial Vectorの保存
		SecretSave.writeBytes(iv,  "iv");
		//暗号解読キーの保存
		SecretSave.writeBytes(key, "secret");

		//③データの表示⇒PrintWriterを使用
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<p>Initial Vector(スタートブロックの初期値=)" + new String(iv) + "(" + iv.length + "byte)</p>" );
		out.println("<p>暗号解読キー=" + new String(key) + "(16byte)</p>");

		//④暗号化の処理
		String source = "asdflkj3%#lkj";
		String result = "";
		try {
			//DoSecretオブジェクトを生成する⇒解読キーとInitial Vectorをファイルから読みこむ
			DoSecret c = new DoSecret(SecretSave.readBytes("secret"), SecretSave.readBytes("iv"));
			//暗号化した文字列を得る
			result = c.encrypto(source);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//暗号文字列を表示
		out.println("暗号= " + result + "</p>");

		//⑤複合化の処理
		try {
			//DoSecretオブジェクトを生成する⇒解読キーとInitial Vectorをファイルから読みこむ
			DoSecret c2 = new DoSecret(SecretSave.readBytes("secret"), SecretSave.readBytes("iv"));
			//複合化した文字列を得る
			result = c2.decrypto(result);
		}catch(Exception e) {
			e.printStackTrace();
		}
		out.println("<p>複合=" + result + "</p>");
	}

}
