package threadTest.sample.servertest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while(true) {
			final Socket connection = socket.accept();
			/*�����ƴ����̵߳Ĳ���
			 * 1���߳��������ڵĿ����ǳ���
			 * 2����Դ���ģ��������ڴ档��������е��߳��������ڴ���������������ô�������е��߳�ռ������ڴ�
			 * 3���ȶ��ԣ��ڿɴ����̵߳������ϴ���һ�����ơ���ƽ̨��jvm������������Thread���캯���������ջ��С��
			 *   �ײ����ϵͳ���̵߳�����
			 *   (��32λ�����ϣ���Ҫ�����������߳�ջ�ĵ�ַ�ռ䣬ÿ���̶߳�ά������ִ��ջ��
			 *   һ������java���룬һ������ԭ�����롣ͨ����jvm��Ĭ������»�����һ�����ϵ�ջ��
			 *   ��СԼΪ0.5mb(��ͨ��JVM��־-Xss����ͨ��Thread�Ĺ��캯�����޸����ֵ)��
			 *   �����2^32����ÿ���̵߳�ջ��С����ô�߳�������������Ϊ��ǧ������..)
			 *   ����ƻ����ƣ������׳�OutOfMemoryError,
			 *   ���򵥵ķ�����ͨ��������������ⳬ����Щ����			
			 */
			Runnable task = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//����connection
				}				
			};
			new Thread(task).start();
		}
	}
}
