package designmodel.intermediarymodel;

public class Mediator extends AbstractMediator{
	
	//�н�������Ҫ�Ĺ��ܣ����ر��������ͬ��֮��
	@Override
	public void execute(String name, String method) {
		// TODO Auto-generated method stub
		if("self".equals(method)) { //�������÷�����
			if("ColleagueA".equals(name)) {
				ColleagueA colleague = (ColleagueA) super.colleagues.get("ColleagueA");
				colleague.self();
			}else {
				ColleagueB colleague = (ColleagueB) super.colleagues.get("ColleagueB");
				colleague.self();
			}
		}else { //������ͬ�º���
			if("ColleagueA".equals(name)) {
				ColleagueA colleague = (ColleagueA) super.colleagues.get("ColleagueA");
				colleague.out();
			}else {
				ColleagueB colleague = (ColleagueB) super.colleagues.get("ColleagueB");
				colleague.out();
			}
		}
	}

}
