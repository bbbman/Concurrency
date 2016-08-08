package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//����Future��Memoizing��װ��
public class Memoizer<A, V> implements Computable<A, V> {
	//���������Future������ֵʱ���ᵼ�¡�������Ⱦ������:
	//���ĳ�����㱻ȡ������ʧ�ܣ���ô�ڼ���������ʱ��ָ��������̱�ȡ������ʧ��
	//Ϊ�˱���������������Memoizer���ּ���ȡ������ô����Future�ӻ������Ƴ���
	//��⵽RuntimeException,��ôҲ���Ƴ�Future
	
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
				f = cache.putIfAbsent(arg, ft);//ʹ��ConcurrentMap�е�ԭ�ӷ���putIfAbsent
				if (f == null) {
					f = ft;
					ft.run();//�����ｫ����c.compute
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
