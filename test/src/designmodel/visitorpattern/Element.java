package designmodel.visitorpattern;

public abstract class Element {
	
	public abstract void accept(IVisitor visitor);
	public abstract void doSomething();
}
