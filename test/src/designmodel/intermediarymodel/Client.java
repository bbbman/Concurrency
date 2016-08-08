package designmodel.intermediarymodel;

public class Client {
	
	public static void main(String[] args) {
		//����һ���н���
		AbstractMediator mediator = new Mediator();
		
		//��������ͬ��
		ColleagueA a = new ColleagueA(mediator);
		ColleagueB b = new ColleagueB(mediator);
		
		//�н��߷ֱ���ÿ��ͬ�½�����ϵ
		mediator.addColleague("ColleagueA", a);
		mediator.addColleague("ColleagueB", b);
		
		//ͬ�¿�ʼ����
		a.self();
		a.out();
		System.out.println("=========������죬������ɣ�\n");
		
		b.self();
		b.out();
		System.out.println("=========������죬������ɣ�\n");		
	}
}
