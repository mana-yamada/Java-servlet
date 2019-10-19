package example1;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//フォワードを行うサーブレットクラスです
@WebServlet("/ForwardSampleServlet")
public class ForwardSampleServlet extends HttpServlet{
	//今回はgetメソッド
		public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
			//フォワードします
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/forwardSample.jsp");
			dispatcher.forward(request, response);
		}
}
