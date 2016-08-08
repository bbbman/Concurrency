package mythread.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//旅行预订门户网站
/*
 * 支持限时的invokeAll，将多个任务提交到一个ExecutorService并获得结果。InvokeAll方法的参数为一组任务，并返回一组Future
 * 这两个集合有着相同的结构。invokeAll按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中，
 * 从而使调用者能将各个Future与其表示的Callable关联起来。
 * 当所有任务都执行完毕时，或者调用线程被中断时，或者超过指定时限时，invokeAll将返回。
 * 当超过指定时限后，任何还未完成的任务都会取消。
 * 当invokeAll返回后，每个任务要么正常地完成，要么被取消，而客户端代码可以调用get或isCancelled来判断
 */
public class QueueTask implements Callable<TravelQuote>{	
	private final TravelCompany company;
	private final TravelInfo travelInfo;
	
	private  ExecutorService exec;
	CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(exec);
	
	public QueueTask(TravelCompany company, TravelInfo travelInfo) {
		this.company = company;
		this.travelInfo = travelInfo;
	}
	public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo,
			Set<TravelCompany> companies,
			Comparator<TravelQuote> ranking, long time, TimeUnit unit) throws InterruptedException {
		List<QueueTask> tasks = new ArrayList<QueueTask>();
		for(TravelCompany company : companies)
			tasks.add(new QueueTask(company, travelInfo));
		
		List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
		
		List<TravelQuote> quotes = new ArrayList<TravelQuote>(tasks.size());
		Iterator<QueueTask> taskIter = tasks.iterator();
		for(Future<TravelQuote> f : futures) {
			QueueTask task = taskIter.next();
			try {
				quotes.add(f.get());				
			}catch(ExecutionException e) {
				quotes.add(tasks.getFailureQuote(e.getCause()));
			}catch (CancellationException e) {
				quotes.add(task.getTimeoutQuote(e));
			}
		}
		Collections.sort(quotes, ranking);		
		return quotes;
	}
	@Override
	public TravelQuote call() throws Exception {
		// TODO Auto-generated method stub
		return company.solicitQuote(travelInfo);
	}
}

class TravelQuote{}
class TravelCompany{
	TravelQuote solicitQuote(TravelInfo travelInfo){ return null;}
}
class TravelInfo{}