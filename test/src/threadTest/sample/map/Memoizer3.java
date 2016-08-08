package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//基于Future的Memoizing封装器
public class Memoizer3<A, V> implements Computable<A, V> {
	private final Map<A, Future<V>> cache 
	        = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;
	
	public Memoizer3(Computable<A, V> c) {
		this.c = c;
	}
	
	@Override
	public  V compute(final A arg) throws InterruptedException {
		Future<V> f = cache.get(arg);
		//由于下面if代码块仍然是非原子的"先检查再执行"操作，因此两个线程仍有可能在同一时间内调用compute
		//来计算相同的值
		//原因是：复合操作("若没有则添加")是在底层的Map对象上执行的，而这个对象无法通过加锁来确定原子性
		if (f == null) {
			Callable<V> eval = new Callable<V>() {
				@Override
				public V call() throws InterruptedException {
					return c.compute(arg);
				}				
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = ft;
			cache.put(arg, ft);
			ft.run();//在这里将调用c.compute
		}
		try {
			return f.get();//线程应该是在这里阻塞
		} catch (ExecutionException e) {
			System.out.println("");
			return null;
		}
	}

}
