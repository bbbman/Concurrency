package threadTest.sample.servertest;

import java.util.concurrent.Executor;
//Ϊÿһ����������һ�����̵߳�Executor
public class ThreadPerTaskExecutor implements Executor{

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		new Thread(command).start();
	}
	
}
