package threadTest.sample;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger> {

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		//�ھ�����ʱ��ļ����
		return new BigInteger(arg);
	}

}
