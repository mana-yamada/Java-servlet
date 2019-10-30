package model;

public class HealthCheckLogic {

	public void calc(Health health) {
		double height = health.getHeight();
		double weight = health.getWeight();
		double bmi = weight/ ((height / 100.0) * (height / 100.0));
		health.setBmi(bmi);

		//judge
		String bodyType;
		if(bmi < 18.5) {
			bodyType = "やせ";
		}else if (bmi < 25) {
			bodyType = "標準";
		}else {
			bodyType = "肥満";
		}
		health.setBodyType(bodyType);
	}
	//セッターにbmiとbodyTypeを入れていなかったからbmiもbodyTypeも出力できなかった


}