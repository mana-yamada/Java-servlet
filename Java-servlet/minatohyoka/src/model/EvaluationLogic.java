package model;

public class EvaluationLogic{

	public void goodEval(Evaluation ev) {
		//Field
		int goodE = ev.getGoodE();
		ev.setGoodE(goodE + 1);
	}

	public void badEval(Evaluation bv) {
		//Field
		int badE = bv.getBadE();
		bv.setBadE(badE + 1);
	}
}
