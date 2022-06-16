package component;
import java.util.concurrent.CountDownLatch;
/*线程池接口*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	ExecutorService executor;
	int nThreads;
	public static int DEFAULT_THREADS = 8;
	/*初始化线程池线程数*/
	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		
	}
	/*返回线程池对象*/
	public void getExecutor(){
		executor = Executors.newFixedThreadPool(nThreads);
	}
	
	/**
	 * @param thread
	 */
	public void addJob(Runnable thread) {
		if(executor==null) {
			executor = Executors.newFixedThreadPool(DEFAULT_THREADS);
		}
		executor.execute(thread);
	}
	/**
	 * 返回一个计数器
	 * @param nums 计数器大小
	 * @return CountDownLatch
	 */
	public CountDownLatch getAThreadCounter(int counterSize) {
		return new CountDownLatch(counterSize);
	}
}
