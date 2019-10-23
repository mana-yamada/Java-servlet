package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Health;
import model.HealthCheckLogic;

@WebServlet("/HealthCheck")
public class HealthCheck extends HttpServlet  {
	//get forward
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
	RequestDispatcher dispatcher = request.getRequestDispatcher("/view/healthCheck.jsp");
	dispatcher.forward(request, response);
	}

	//post forward
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
		String weight = request.getParameter("height");
		String height = request.getParameter("weight");

		//入力値をプロパティに設定
		Health health = new Health();
		health.setHeight(Double.parseDouble(height));
		health.setWeight(Double.parseDouble(weight));

		//健康診断を実行し結果を設定
		HealthCheckLogic healthCheckLogic = new HealthCheckLogic();
		healthCheckLogic.execute(health);

		//リクエストスコープに保存
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/healthCheckResult.jsp");
		dispatcher.forward(request, response);
	}
}
