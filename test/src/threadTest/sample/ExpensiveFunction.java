package threadTest.sample;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger> {

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		//在经过长时间的计算后
		return new BigInteger(arg);
	}

}
