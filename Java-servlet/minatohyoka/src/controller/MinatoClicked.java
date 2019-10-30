package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Evaluation;
import model.EvaluationLogic;

@WebServlet("/MinatoClicked")
public class MinatoClicked extends HttpServlet {
	public synchronized void  doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		//get Parameter
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("value");

		//get Scope
		ServletContext application = this.getServletContext();
		Evaluation evaluation = (Evaluation) application.getAttribute("evaluation");
		if(parameter == null) {
			//make instance
			evaluation = new Evaluation();
		}

		//サイトの評価処理 //ここでRunnableインターフェースを用いたメソッドを呼び出す
		EvaluationLogic evaluationLogic = new EvaluationLogic();
		if(parameter != null && parameter.equals("good")) {
			evaluationLogic.goodEval(evaluation);
		}else if(parameter != null && parameter.equals("bad")) {
			evaluationLogic.badEval(evaluation);
		}

		//アプリケーションスコープにサイト評価を保存
		application.setAttribute("evaluation", evaluation);

		//forward
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/webEvaluation.jsp") ;
		dispatcher.forward(request, response);
	}

}
