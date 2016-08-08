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

//����Ԥ���Ż���վ
/*
 * ֧����ʱ��invokeAll������������ύ��һ��ExecutorService����ý����InvokeAll�����Ĳ���Ϊһ�����񣬲�����һ��Future
 * ����������������ͬ�Ľṹ��invokeAll�������񼯺��е�������˳�����е�Future��ӵ����صļ����У�
 * �Ӷ�ʹ�������ܽ�����Future�����ʾ��Callable����������
 * ����������ִ�����ʱ�����ߵ����̱߳��ж�ʱ�����߳���ָ��ʱ��ʱ��invokeAll�����ء�
 * ������ָ��ʱ�޺��κλ�δ��ɵ����񶼻�ȡ����
 * ��invokeAll���غ�ÿ������Ҫô��������ɣ�Ҫô��ȡ�������ͻ��˴�����Ե���get��isCancelled���ж�
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