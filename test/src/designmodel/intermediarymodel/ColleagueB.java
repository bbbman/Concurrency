package designmodel.intermediarymodel;

public class ColleagueB extends AbstractColleague{
	
	//ÿ������ͬʱ��ͨ�����๹�캯�����н���ȡ����ϵ
	public ColleagueB(AbstractMediator mediator) {
		super(mediator);
	}
	
	//ÿ������ͬʱ��Ȼ���Լ����ڵ��£�û��Ҫ����������
	public void self() {
		System.out.println("ͬ��B --> �����Լ����ڵ�����  ...");
	}
	
	//ÿ������ͬ��������Ҫ����罻���Ĳ�����ͨ���н��������������߼������Ź���
	public void out() {
		System.out.println("ͬ��B --> ����ͬ��A���÷��ڹ��� ...");
		//do ...
		super.mediator.execute("CollesgueA", "self");
	}
}
