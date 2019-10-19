package example1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Pokemon")
public class Pokemon extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		try {
			//ポケモンの名前と体力を格納したLinkedHashMapを作成
			Map<String, Integer> pokemon = new LinkedHashMap<String, Integer>();
			pokemon.put("グラードン", 250);
			pokemon.put("カイオーガ", 250);
			pokemon.put("レックウザ", 280);


			//htmlを出力する準備
			response.setContentType("text/html; charset = UTF-8 ");
			PrintWriter out = response.getWriter();

			//output HTML
			out.println("<!DOCTYPE html>\r\n" +
					"<html lang=\"ja\">\r\n" +
					"<head>\r\n" +
					"<meta charset=\"utf-8\">\r\n" +
					"<title>伝説のポケモン</title>\r\n" +
					"<meta name=\"viewport\" content=\"width=device-width initial-scale=1\">\r\n" +
					"</head>\r\n" +
					"<body>");

			for(String key: pokemon.keySet()) {
				Integer value = pokemon.get(key);
				out.println("<p>" + key + ":" + value + "</p>" );
			}
			out.println("</body>\r\n" +
					"</html>");


			//forwardをしてみる⇒ここでは1つのファイルで1つのリクエストを処理していたためforwardしようとしても不具合発生
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pokemonForwardSample.jsp");
//			dispatcher.forward(request, response);

		}catch(IOException e) {

		}
	}
}
