package designmodel.intermediarymodel;

public abstract class AbstractColleague {
	
	protected AbstractMediator mediator;
	
	/**
	 * ��Ȼ���н��ߣ���ôÿ������ͬʱ��ȻҪ���н�������ϵ��
	 * �����û�б�Ҫ�������ϵͳ���У�����Ĺ��캯���൱
	 * �����ϵͳ��ע��һ���н��ߣ���ȡ����ϵ
	 */
	public AbstractColleague(AbstractMediator mediator) {
		this.mediator = mediator;
	}
	
	//�ڳ���ͬ����������������н���ȡ����ϵ(��ע��)�ķ���
	public void setMediator(AbstractMediator mediator) {
		this.mediator = mediator;
	}
}
