package threadTest.sample.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer2<A, V> implements Computable<A, V> {
	private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
	private final Computable<A, V> c;
	
	public Memoizer2(Computable<A, V> c) {
		this.c = c;
	}
	
	//ConcurrentHashMap���̰߳�ȫ�ģ�������Memoizer1�����Ĵ�������
	//��������Ϊ����ʱ��Ȼ���ڲ���----�������߳�ͬʱ����computeʱ����һ��©��
	//���ܻᵼ�¼�����õ���ͬ��ֵ����ʹ��memoization������£��������Ч����Ϊ����������Ǳ�����ͬ���ݱ������Ρ�
	//�����ڸ�ͨ�õĻ��������˵�������������Ϊ���
	//����ֻ�ṩ���γ�ʼ���Ķ��󻺴���˵�����©���������ȫ����
	//���ĳ���߳�������һ�������ܴ�ļ��㣬�����̲߳���֪������������ڽ��У���ô�ܿ����ظ��������
	
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
