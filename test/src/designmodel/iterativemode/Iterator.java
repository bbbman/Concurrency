package designmodel.iterativemode;

public interface Iterator<T> {
	
	//前移
	public T previous();
	
	//后移
	public T next();
	
	public boolean hasNext();
	
	//取得第一个元素
	public T first();
}
