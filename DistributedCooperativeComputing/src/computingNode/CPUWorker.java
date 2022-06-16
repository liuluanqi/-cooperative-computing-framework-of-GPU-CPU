package computingNode;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import component.JobConfig;

/*初始化CPU线程执行类的实例*/
public class CPUWorker extends ProcessorOperate
{
	public CPUWorker(JobConfig config, CountDownLatch cdt,ThreadDo td) throws IOException 
	{
		super(config, cdt,1,td);//调用父类构造方法初始化线程执行类的实例
	}
}