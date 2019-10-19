package example1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetCoin")
public class GetCoin extends HttpServlet{
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		 try {
			 //make random
			 //make instance Random
			 Random random = new Random();
			 int r =  random.nextInt(1000) + 100;
			 int r2 = random.nextInt(2000) + 100;
			 int r3 =  random.nextInt(30000) + 100;
			 int sum = r + r2 + r3;

			 //outpu html
			 response.setContentType("text/html; charset = UTF-8 ");
			 PrintWriter out = response.getWriter();

			 out.println("<!DOCTYPE html>\r\n" +
			 		"<html lang=\"ja\">\r\n" +
			 		"<head>\r\n" +
			 		"<meta charset=\"utf-8\">\r\n" +
			 		"<title>ボーナスコインをゲット！</title>\r\n" +
			 		"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
			 		"</head>\r\n" +
			 		"\r\n" +
			 		"<body>\r\n" +
			 		"<p>あなたは" + sum + "のコインを獲得しました</p>\r\n" +
			 		"</body>\r\n" +
			 		"");
			 out.println();
			 out.println();


		 }catch(IOException e) {
			 e.printStackTrace();
		 }

	 }
}
