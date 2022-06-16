package computingNode;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

import component.JobConfig;

/*线程执行类*/
public interface ThreadDo  extends Runnable,Serializable{
	public void setThreadInfo(JobConfig config,int index, CountDownLatch cdt);
	public Object[] getThreadsList(int nums);
}
