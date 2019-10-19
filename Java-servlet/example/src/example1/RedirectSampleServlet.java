package example1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//占いを行い、結果を出力するサーブレットクラス「Sample.java」に
//リダイレクトするサーブレットクラスです
@WebServlet("/RedirectSampleServlet")
public class RedirectSampleServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//リダイレクトをします
		response.sendRedirect("/example/Sample");
	}
}
