package designmodel.interpreterpattern;

public class Test {
	public static void main(String[] args) {
		int result = new Minus().interpret(
				new Context(
						new Plus().interpret(
								new Context(9, 2)), 8));
		System.out.println(result);
				
	}
}
