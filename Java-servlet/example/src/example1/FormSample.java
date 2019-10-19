package example1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FormSample")
public class FormSample extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//リクエストパラメータの文字コードの設定
		request.setCharacterEncoding("UTF-8");

		//リクエストパラメータの取得
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		//入力された文字や選択されたボタンの確認
		String errorMsg = "";
		//もしnameが空であるか入力値の文字数が0の文字列⇒エラー文出力
		if(name == null || name.length() == 0) {
			errorMsg += "<p>名前が入力されていません。</p>";
		}
		//もしgenderが空であるか入力値の文字数が0の文字列⇒エラー文出力
		if(gender == null || gender.length() == 0) {
			errorMsg += "<p>性別が選択されていません</p>";
		}else { //genderが選択されている場合はvalueで受け取った文字列を変換
			if(gender.equals("g1")) {
				gender = "男";
			}else if(gender.equals("g2")) {
				gender = "女";
			}
		}

		//表示するメッセージを決定
		String msg = "";
		if(errorMsg.length() != 0) {
			msg = errorMsg;
		}else {
			msg = "<p>" + name + "さん(" + gender + ")を登録しました";
		}

		//HTMLで入力された登録内容を表示
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<title>登録完了画面もどき</title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>");
		out.println("<body>");
		out.println("</body>");
		//メッセージを出力
		out.println(msg);
		out.println("</html>");
	}
}
