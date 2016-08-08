package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//����Future��Memoizing��װ��
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
		//��������if�������Ȼ�Ƿ�ԭ�ӵ�"�ȼ����ִ��"��������������߳����п�����ͬһʱ���ڵ���compute
		//��������ͬ��ֵ
		//ԭ���ǣ����ϲ���("��û�������")���ڵײ��Map������ִ�еģ�����������޷�ͨ��������ȷ��ԭ����
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
			ft.run();//�����ｫ����c.compute
		}
		try {
			return f.get();//�߳�Ӧ��������������
		} catch (ExecutionException e) {
			System.out.println("");
			return null;
		}
	}

}
