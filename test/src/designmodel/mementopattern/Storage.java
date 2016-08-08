package designmodel.mementopattern;

public class Storage {
	
	private final Memento memento;

	public Storage(Memento memento) {
		super();
		this.memento = memento;
	}

	public Memento getMemento() {
		return memento;
	}		
}
