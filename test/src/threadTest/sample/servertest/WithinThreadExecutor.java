package threadTest.sample.servertest;

import java.util.concurrent.Executor;
//�ڵ����߳�����ͬ����ʽִ�����������Executor
public class WithinThreadExecutor implements Executor{

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		command.run();
	}

}
