package designmodel.intermediarymodel;

import java.util.HashMap;

public abstract class AbstractMediator {
	
	//�н��߿϶���Ҫ����������ͬ�µ���ϵ��ʽ
	protected HashMap<String, AbstractColleague> colleagues = 
			new HashMap<String, AbstractColleague>();
	
	//�н��߿��Զ�̬����ĳ��ͬ�½�����ϵ
	public void addColleague(String name, AbstractColleague c) {
		this.colleagues.put(name, c);
	}
	
	//�н���Ҳ���Զ�̬������ĳ��ͬ�½�����ϵ
	public void deleteColleague(String name) {
		this.colleagues.remove(name);
	}
	//�н��߱���߱���ͬ��֮�䴦���߼����������񡢴ٽ������Ĳ���
	public abstract void execute(String name, String method);
}
