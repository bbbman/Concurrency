package mythread.test;

import java.util.ArrayList;
import java.util.List;
//串行，先处理文本，再下载图片，在图片下载过程的大部分时间都是在等待I/O操作执行完成，这期间CPU几乎不做任何工作
public class SingleThreadRenderer {
	void renderPage(CharSequence source) {
		renderText(source);
		List<ImageData> imageData = new ArrayList<ImageData>();
		for(ImageInfo imageInfo : scanForImageInfo(source))
			imageData.add(imageInfo.downloadImage());
		for(ImageData data : imageData)
			renderImage(data);
	}
	void renderText(CharSequence source) {}
	List<ImageInfo> scanForImageInfo(CharSequence source){
		return null;
	}
	void renderImage(ImageData data) {}
	
}
class ImageData{	
}
class ImageInfo{
	public ImageData downloadImage() {
		return null;
	}
}
