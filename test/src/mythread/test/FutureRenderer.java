package mythread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FutureRenderer {
	private final ExecutorService executor = null;
	
	void renderPage(CharSequence source) {
		final List<ImageInfo> imageInfos = scanForImageInfo(source);
		Callable<List<ImageData>> task = new Callable<List<ImageData>>(){
			@Override
			public List<ImageData> call() throws Exception {
				// TODO Auto-generated method stub
				List<ImageData> result = new ArrayList<ImageData>();
				for(ImageInfo imageInfo : imageInfos) {
					result.add(imageInfo.downloadImage());
				}
				return result;
			}			
		}; 
		Future<List<ImageData>> future = executor.submit(task);
		renderText(source);
		try {
			List<ImageData> imageData = future.get();
			for(ImageData data : imageData)
				renderImage(data);
		} catch(InterruptedException e) {
			//重新设置线程的中断状态
			Thread.currentThread().interrupt();
			//由于不需要结果，因此取消任务
			future.cancel(true);			
		} catch(ExecutionException e) {
			//throw launderThrowable(e.getCause());
		}
	}
	void renderText(CharSequence source) {}
	void renderImage(ImageData data) {}
	List<ImageInfo> scanForImageInfo(CharSequence source) {return null;}
}

//class ImageData{	
//}
//class ImageInfo{
//	public ImageData downloadImage() {
//		return null;
//	}
//}

