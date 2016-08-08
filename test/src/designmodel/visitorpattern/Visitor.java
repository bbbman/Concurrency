package designmodel.visitorpattern;

public class Visitor implements IVisitor{

	@Override
	public void visit(ConcreteElement1 el1) {
		// TODO Auto-generated method stub
		el1.doSomething();
	}

	@Override
	public void visit(ConcreteElement2 el2) {
		// TODO Auto-generated method stub
		el2.doSomething();
	}
}
