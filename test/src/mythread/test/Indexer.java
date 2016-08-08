package mythread.test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Indexer implements Runnable {
	private final BlockingQueue<File> queue;
	
	public Indexer(BlockingQueue<File> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				indexFile(queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
	
	private void indexFile(File file){
		
	}

}
