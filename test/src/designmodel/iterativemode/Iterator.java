package designmodel.iterativemode;

public interface Iterator<T> {
	
	//ǰ��
	public T previous();
	
	//����
	public T next();
	
	public boolean hasNext();
	
	//ȡ�õ�һ��Ԫ��
	public T first();
}
