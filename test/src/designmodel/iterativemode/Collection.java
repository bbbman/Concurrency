package designmodel.iterativemode;

public interface Collection<T> {
	
	public Iterator<T> iterator();
	
	//ȡ�ü���Ԫ��
	public T get(int i);
	
	//ȡ�ü��ϴ�С
	public int size();
}
