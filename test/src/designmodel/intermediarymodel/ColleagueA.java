package designmodel.intermediarymodel;

public class ColleagueA extends AbstractColleague{
	
	//ÿ������ͬʱ��ͨ�����๹�캯�����н���ȡ����ϵ
	public ColleagueA(AbstractMediator mediator) {
		super(mediator);
	}
	
	//ÿ������ͬʱ��Ȼ���Լ����ڵ��£�û��Ҫ����������
	public void self() {
		System.out.println("ͬ��A --> �����Լ����ڵ�����  ...");
	}
	
	//ÿ������ͬ��������Ҫ����罻���Ĳ�����ͨ���н��������������߼������Ź���
	public void out() {
		System.out.println("ͬ��A --> ����ͬ��B���÷��ڹ��� ...");
		//do ...
		super.mediator.execute("CollesgueB", "self");
	}
}
