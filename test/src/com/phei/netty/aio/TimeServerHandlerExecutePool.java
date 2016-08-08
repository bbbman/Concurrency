package com.phei.netty.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool {
	
	private ExecutorService executor; 
	
	public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
		executor = new ThreadPoolExecutor(Runtime.getRuntime()
				.availableProcessors(),maxPoolSize,120L,TimeUnit.SECONDS,
				new ArrayBlockingQueue<java.lang.Runnable>(queueSize));
	}
	public void execute(java.lang.Runnable task){
		executor.execute(task);
	}
}
