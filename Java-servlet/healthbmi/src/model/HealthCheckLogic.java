package model;

public class HealthCheckLogic {
	public void execute(Health health) {
		//BMI
		double weight = health.getWeight();
		double height = health.getHeight();
		double bmi = weight / ((height /100) * (height / 100));

		//bmiの結果を入れる
		health.setBmi(bmi);

		String bodyType;
		//BMI判定
		if(bmi < 18.5) {
			bodyType = "痩せ";
		}else if (bmi < 25) {
			bodyType = "普通";
		}else {
			bodyType = "肥満";
		}
		health.setBodyType(bodyType);
	}
}
