package mythread.test;

import java.util.ArrayList;
import java.util.List;
//���У��ȴ����ı���������ͼƬ����ͼƬ���ع��̵Ĵ󲿷�ʱ�䶼���ڵȴ�I/O����ִ����ɣ����ڼ�CPU���������κι���
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
