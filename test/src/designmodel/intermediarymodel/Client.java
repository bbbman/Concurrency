package designmodel.intermediarymodel;

public class Client {
	
	public static void main(String[] args) {
		//创建一个中介者
		AbstractMediator mediator = new Mediator();
		
		//创建两个同事
		ColleagueA a = new ColleagueA(mediator);
		ColleagueB b = new ColleagueB(mediator);
		
		//中介者分别与每个同事建立联系
		mediator.addColleague("ColleagueA", a);
		mediator.addColleague("ColleagueB", b);
		
		//同事开始工作
		a.self();
		a.out();
		System.out.println("=========合作愉快，任务完成！\n");
		
		b.self();
		b.out();
		System.out.println("=========合作愉快，任务完成！\n");		
	}
}
