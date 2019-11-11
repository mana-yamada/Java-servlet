package goodscontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditController")
public class EditController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
		String parameter = request.getParameter("value");
		if(parameter == null) {
			//編集画面へforward
			String forward = "/view/goods/edit/edit.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


	}
}
