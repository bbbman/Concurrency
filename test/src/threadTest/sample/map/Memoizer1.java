package threadTest.sample.map;

import java.util.HashMap;
import java.util.Map;

public class Memoizer1<A, V> implements Computable<A, V> {
	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> c;
	
	public Memoizer1(Computable<A, V> c) {
		this.c = c;
	}
	
	//如果另一个线程正在计算结果，那么其他调用compute的线程可能被阻塞很长时间，相当于串行了
	@Override
	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
