package example1;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//占いを行い、結果を出力するサーブレットクラスです
@WebServlet("/Sample")
public  class Sample extends HttpServlet {

	private static long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException , IOException{

		//content
		String[] luckArray = {"超すっきり" ,"スッキリ", "もやっと"};
		int index = (int) (Math.random() * 3);
		String luck = luckArray[index];

		//getdate
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		String today = sdf.format(date);

		//output HTML
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\r\n" +
				"<html lang=\"ja\">\r\n" +
				"<head>\r\n" +
				"<meta charset=\"utf-8\">\r\n" +
				"<title>すっきり占い</title>\r\n" +
				"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
				"</head>");
		out.println("<body>");
		out.println("<p>" + today + "の運勢は「" + luck + "です！」" );
		out.println("</body>");
		out.println("</html>");
	}
}
