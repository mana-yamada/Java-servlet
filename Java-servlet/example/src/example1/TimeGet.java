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

@WebServlet("/TimeGet")
public class TimeGet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
		//getTime
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH時mm分");
		String strDate = sdf.format(date);

		//output HTML
		response.setContentType("text/html; charset = UTF-8" );
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>\r\n" +
				"		<head>\r\n" +
				"		<title>現在時刻</title>\r\n" +
				"		</head>");
		out.println("	<body>\r\n" +
				"		<p>只今の時刻：" + strDate + "</p>\r\n" +
				"		</body>" +
				"		</html>");






	}
}
