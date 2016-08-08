package mythread.test;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Desk {
	private final static Integer BOUND =1000;
	private final static Integer N_CONSUMERS =1000;
	public static void startIndexing(File[] roots){
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
		FileFilter filter = new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return true;
			}			
		};
		for(File root:roots)
			new Thread(new FileCrawler(queue,filter,root)).start();
		for(int i=0;i<N_CONSUMERS;i++)
			new Thread(new Indexer(queue)).start();
	}
}
