package designmodel.strategymode;

public class StrategyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String exp = "8-2";
		ICalculator cal = new Minus();
		int result = cal.calculate(exp);
		System.out.println(exp + "=" + result);
	}

}
