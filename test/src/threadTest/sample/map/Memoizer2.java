package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer2<A, V> implements Computable<A, V> {
	private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
	private final Computable<A, V> c;
	
	public Memoizer2(Computable<A, V> c) {
		this.c = c;
	}
	
	//ConcurrentHashMap是线程安全的，避免了Memoizer1带来的串行问题
	//但它在作为缓存时仍然存在不足----当两个线程同时调用compute时存在一个漏洞
	//可能会导致计算机得到相同的值。在使用memoization的情况下，会带来低效，因为缓存的作用是避免相同数据被计算多次。
	//但对于更通用的缓存机制来说，这种情况将更为糟糕
	//对于只提供单次初始化的对象缓存来说，这个漏洞会带来安全风险
	//如果某个线程启动了一个开销很大的计算，其他线程并不知道这个计算正在进行，那么很可能重复这个计算
	
	@Override
	public  V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
