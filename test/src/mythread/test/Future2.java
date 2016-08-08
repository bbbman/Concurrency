package mythread.test;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Future2<V> {
	boolean cancel(boolean mayInterruptIfRunning);
	boolean isCancelled();
	boolean isDone();
	V get() throws InterruptedException, ExecutionException, CancellationException;
	
	V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, 
	                      CancellationException, TimeoutException;
}
