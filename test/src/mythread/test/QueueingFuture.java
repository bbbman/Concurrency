package mythread.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueingFuture<V> extends FutureTask<V>{
	private BlockingQueue<QueueingFuture<V>> completionQueue = new LinkedBlockingDeque<QueueingFuture<V>>();	
	public QueueingFuture(Callable<V> c) {
		super(c);
	}
	public QueueingFuture(Runnable t, V r) {
		super(t,r);
	}
	
	protected void done() {
		completionQueue.add(this);
	}
}
