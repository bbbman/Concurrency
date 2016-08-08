package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//基于Future的Memoizing封装器
public class Memoizer<A, V> implements Computable<A, V> {
	//当缓存的是Future而不是值时，会导致“缓存污染”问题:
	//如果某个计算被取消或者失败，那么在计算这个结果时将指明计算过程被取消或者失败
	//为了避免这种情况，如果Memoizer发现计算取消，那么将把Future从缓存中移除。
	//检测到RuntimeException,那么也会移除Future
	
	private final ConcurrentMap<A, Future<V>> cache 
	        = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;
	
	public Memoizer(Computable<A, V> c) {
		this.c = c;
	}
	
	@Override
	public  V compute(final A arg) throws InterruptedException {
		while (true) {
			Future<V> f = cache.get(arg);			
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					public V call() throws InterruptedException {
						return c.compute(arg);
					}				
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(arg, ft);//使用ConcurrentMap中的原子方法putIfAbsent
				if (f == null) {
					f = ft;
					ft.run();//在这里将调用c.compute
				}
				cache.put(arg, ft);				
			}
			try {
				return f.get();
			} catch (CancellationException e) {
				cache.remove(arg, f);
			} catch (ExecutionException e) {
				return null;
			}
		}
	}

}
