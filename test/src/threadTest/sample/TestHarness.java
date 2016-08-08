package threadTest.sample;

import java.util.concurrent.CountDownLatch;

//在计时测试中使用CountDownLatch来启动和停止线程
public class TestHarness {
	public long timeTasks(int nThreads,final Runnable task) throws InterruptedException{
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate   = new CountDownLatch(nThreads);
		
		for(int i=0; i < nThreads;i++){
			Thread t = new Thread(){
				public void run(){
					try{
						startGate.await();//使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。
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
