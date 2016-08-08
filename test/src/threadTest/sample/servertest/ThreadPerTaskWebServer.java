package threadTest.sample.servertest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while(true) {
			final Socket connection = socket.accept();
			/*无限制创建线程的不足
			 * 1、线程生命周期的开销非常高
			 * 2、资源消耗，尤其是内存。如果可运行的线程数量多于处理器的数量，那么大量空闲的线程占用许多内存
			 * 3、稳定性：在可创建线程的数量上存在一个限制。随平台、jvm的启动参数、Thread构造函数中请求的栈大小、
			 *   底层操作系统对线程的限制
			 *   (在32位机器上，主要限制因素是线程栈的地址空间，每个线程都维护两个执行栈，
			 *   一个用于java代码，一个用于原生代码。通常，jvm在默认情况下会生成一个复合的栈，
			 *   大小约为0.5mb(可通过JVM标志-Xss或者通过Thread的构造函数来修改这个值)，
			 *   如果将2^32除以每个线程的栈大小，那么线程数量将被限制为几千到几万..)
			 *   如果破坏限制，可能抛出OutOfMemoryError,
			 *   更简单的方法是通过构造程序来避免超出这些限制			
			 */
			Runnable task = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//处理connection
				}				
			};
			new Thread(task).start();
		}
	}
}
