package mythread.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator implements Runnable{
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	private volatile boolean cancelled;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
