package designmodel.iterativemode;

public class MyCollection<T> implements Collection<T>{
	
	public T[] string ;
	
	public MyCollection(T... str){
		string = str;
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new MyIterator<T>(this);
	}

	@Override
	public T get(int i) {
		// TODO Auto-generated method stub
		return (T) string[i];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return string.length;
	}
	
	

}
