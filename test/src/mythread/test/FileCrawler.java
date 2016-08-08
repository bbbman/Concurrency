package mythread.test;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

public class FileCrawler implements Runnable {
	
	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter;
	private final File root;
	public FileCrawler(BlockingQueue<File> fileQueue,FileFilter fileFilter,File root){
		this.fileQueue = fileQueue;
		this.fileFilter = fileFilter;
		this.root = root;
	}
	//...	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			crawl(root);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void crawl(File root) throws InterruptedException{
		File[] entries = root.listFiles(fileFilter);
		if(entries !=null){
			for(File entry : entries)
				if(entry.isDirectory())
					crawl(entry); 
				else if(!alreadyIndexed(entry))
					fileQueue.put(entry);
		}
	}
	
	private boolean alreadyIndexed(File entry){
		return true;
	}

}
