package mythread.test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Renderer {
	//多个ExecutorCompletionService可以共享一个Executor
	private final ExecutorService executor;
	
	Renderer(ExecutorService executor) {
		this.executor = executor;		
	}
	
	void renderPage(CharSequence source) {
		List<ImageInfo> info = scanForImageInfo(source);
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {
				@Override
				public ImageData call() throws Exception {//计算结果，如果无法计算结果，则抛出一个异常。 					
					return imageInfo.downloadImage();
				}				
			});//提交要执行的值返回任务，并返回表示挂起的任务结果的 Future。
		renderText(source);
		
		try {
			for(int t = 0, n = info.size(); t < n; t++) {
				Future<ImageData> f = completionService.take();// 获取并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
				/**
				 * 在支持时间限制的Future.get()中：当结果可用时，它将立即返回，如果在指定时限内没有计算出结果，将抛出TimeoutException
				 * 在使用限时任务时需要注意，当这些任务超时后应该立即停止，从而避免为继续计算一个不再使用的结果而浪费计算资源。
				 * 要实现这个功能，可以由任务本身来管理它的限定时间，并且在超时后终止执行或取消任务。此时可再次使用Future，如果
				 * 一个限时的get()抛出了TimeoutException，那么可以通过Future来取消任务。
				 * 如果编写的任务是可取消的，那么可以提前终止它
				 */			
				ImageData imageData = f.get();
				renderImage(imageData);
			}
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch (ExecutionException e) {
			//throw launderThrowable(e.getCause());
		}
	}		
	void renderText(CharSequence source) {}
	List<ImageInfo> scanForImageInfo(CharSequence source){
		return null;
	}
	void renderImage(ImageData data) {}
	
	//--=-------------------------------------------------
	final static long TIME_BUDGET = 20;	
	//指定时间内获取广告信息
	Page renderPageWithAd() throws InterruptedException {
		CompletionService<Ad> completionService = new ExecutorCompletionService<Ad>(executor);
		long endNanos = System.nanoTime() + TIME_BUDGET;
		Future<Ad> f = completionService.submit(new FetchAdTask<Ad>());
		//在等待广告的同事显示页面
		Page page = renderPageBody();
		Ad ad;
		try{
			//只等待指定的时间长度
			long timeLeft = endNanos - System.nanoTime();
			ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
		}catch (ExecutionException e) {
			ad = new Ad();
		}catch (TimeoutException e) {
			ad = new Ad();
			f.cancel(true);
		}
		page.setAd(ad);
		return page;
	}
	Page renderPageBody(){
		return null;
	}
	
	
}
class Ad{}
class Page{
	private Ad ad;
	public void setAd(Ad ad){
		this.ad = ad;
	}
}
class FetchAdTask<Ad> implements Callable<Ad>{
	@Override
	public Ad call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
