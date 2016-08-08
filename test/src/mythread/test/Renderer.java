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
	//���ExecutorCompletionService���Թ���һ��Executor
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
				public ImageData call() throws Exception {//������������޷������������׳�һ���쳣�� 					
					return imageInfo.downloadImage();
				}				
			});//�ύҪִ�е�ֵ�������񣬲����ر�ʾ������������� Future��
		renderText(source);
		
		try {
			for(int t = 0, n = info.size(); t < n; t++) {
				Future<ImageData> f = completionService.take();// ��ȡ���Ƴ���ʾ��һ������������ Future�����Ŀǰ������������������ȴ���
				/**
				 * ��֧��ʱ�����Ƶ�Future.get()�У����������ʱ�������������أ������ָ��ʱ����û�м������������׳�TimeoutException
				 * ��ʹ����ʱ����ʱ��Ҫע�⣬����Щ����ʱ��Ӧ������ֹͣ���Ӷ�����Ϊ��������һ������ʹ�õĽ�����˷Ѽ�����Դ��
				 * Ҫʵ��������ܣ������������������������޶�ʱ�䣬�����ڳ�ʱ����ִֹ�л�ȡ�����񡣴�ʱ���ٴ�ʹ��Future�����
				 * һ����ʱ��get()�׳���TimeoutException����ô����ͨ��Future��ȡ������
				 * �����д�������ǿ�ȡ���ģ���ô������ǰ��ֹ��
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
	//ָ��ʱ���ڻ�ȡ�����Ϣ
	Page renderPageWithAd() throws InterruptedException {
		CompletionService<Ad> completionService = new ExecutorCompletionService<Ad>(executor);
		long endNanos = System.nanoTime() + TIME_BUDGET;
		Future<Ad> f = completionService.submit(new FetchAdTask<Ad>());
		//�ڵȴ�����ͬ����ʾҳ��
		Page page = renderPageBody();
		Ad ad;
		try{
			//ֻ�ȴ�ָ����ʱ�䳤��
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
