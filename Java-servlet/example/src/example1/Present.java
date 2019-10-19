package example1;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Present")
public class Present extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{

		try {
			//randomに7種から1種のプレゼントを出す
			String[] presents = {"チョコボール","ハイソフト","エンゼルパイ","小倉ソフト","じまんやき","いちごパフェ","カントリーマアム"};

			Random random = new Random();
			int randoms = random.nextInt(6) + 1;

			String present = presents[randoms];

			//HTML output
			response.setContentType("text/html ; charset = UTF-8");
			PrintWriter out = response.getWriter();

			out.println("<!DOCTYPE html>"
					+ "<head>"
					+ "<title>7種から1種をプレゼント</title>"
					+ "</head>");

			out.println("<body>"
					+ "<p>" + present + "をプレゼント</p>"
					+"</body>"
					+ "</html>");


		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}
