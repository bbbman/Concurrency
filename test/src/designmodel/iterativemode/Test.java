package designmodel.iterativemode;

public class Test {
	
	public static void main(String[] args) {
		Collection<String> collection = new MyCollection<String>("A", "B", "C", "D", "E");
		Iterator<String> it = collection.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
