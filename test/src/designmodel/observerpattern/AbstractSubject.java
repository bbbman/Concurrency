package designmodel.observerpattern;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSubject implements Subject {
	
	private List<Observer> observerList = new LinkedList<Observer>();
	
	@Override
	public void add(Observer observer) {
		// TODO Auto-generated method stub
		observerList.add(observer);
	}

	@Override
	public void del(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		Iterator<Observer> it = observerList.iterator();
		while(it.hasNext()) {
			it.next().update();
		}
	}		
}
