package threadTest.sample.map;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		//������ʱ������
		return new BigInteger(arg);
	}
}
