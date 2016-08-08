package threadTest.sample;

import java.util.concurrent.CountDownLatch;

//�ڼ�ʱ������ʹ��CountDownLatch��������ֹͣ�߳�
public class TestHarness {
	public long timeTasks(int nThreads,final Runnable task) throws InterruptedException{
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate   = new CountDownLatch(nThreads);
		
		for(int i=0; i < nThreads;i++){
			Thread t = new Thread(){
				public void run(){
					try{
						startGate.await();//ʹ��ǰ�߳�������������������֮ǰһֱ�ȴ��������̱߳��жϡ�
						try{
							task.run();
						}finally{
							endGate.countDown();//
						}
					}catch(InterruptedException ignored){ }
				}
			};
			t.start();
		}
		long start = System.nanoTime();
		startGate.await();
		long end = System.nanoTime();
		return end - start;
	}	
}
