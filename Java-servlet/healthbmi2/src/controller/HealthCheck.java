package controller;

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
public class HealthCheck extends HttpServlet {
	private final long serialVersionUID = 1L;

	//立ち上げ時に入力ページを出す
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/healthCheck.jsp");
		dispatcher.forward(request, response);
	}

	//入力したものを受け取る！！そして、インスタンスに保存する！！
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//get Parameter
		String height = request.getParameter("height");
		String weight = request.getParameter("weight");

		//make instantce in Health
		Health health = new Health();
		health.setHeight(Double.parseDouble(height));
		health.setWeight(Double.parseDouble(weight));

		//make instance in HealthCheckLogic
		HealthCheckLogic healthCheckLogic = new HealthCheckLogic();
		//call calc method
		healthCheckLogic.calc(health);

		//インスタンスを保存
		request.setAttribute("health", health);

		//forward 結果表示
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/healthCheckResult.jsp");
		dispatcher.forward(request, response);
	}
}
