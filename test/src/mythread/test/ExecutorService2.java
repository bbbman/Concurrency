package mythread.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ExecutorService2 {
	void shutdown();
	List<Runnable> shutdownNow();
	boolean isShutdown();
	boolean isTerminated();
	boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
	//.....其他用于任务提交的便利方法	
}
