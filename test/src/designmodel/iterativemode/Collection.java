package designmodel.iterativemode;

public interface Collection<T> {
	
	public Iterator<T> iterator();
	
	//取得集合元素
	public T get(int i);
	
	//取得集合大小
	public int size();
}
