package computingNode;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import component.JobConfig;
import component.ThreadPool;

/*根据CPU或GPU初始化线程执行类的实例*/
public class ProcessorOperate {
	

	public ThreadPool threadPool;//线程池操作类
	public ExecutorService executor;//线程池
	public CountDownLatch cdt;//计数器
	public ThreadDo[] ThreadDo;//线程执行类的实例
	JobConfig config;
	
	public ProcessorOperate(JobConfig config,CountDownLatch cdt,int processorType,ThreadDo td) throws IOException  {   
		this.config = config;
		this.cdt = cdt;
		
		if(processorType==1) {//实例化CPU线程组并初始化
			ThreadDo = (ThreadDo[])td.getThreadsList(config.getCPUDataCount());
			setCPUThreadDo(ThreadDo);
		}
		else {//实例化GPU线程组并初始化
			ThreadDo = (ThreadDo[])td.getThreadsList(config.getGPUDataCount());
			setGPUThreadDo(ThreadDo);
		}
		
    }
	public void setCPUThreadDo(ThreadDo[] td) throws IOException {
		
		for(int i = 0;i < config.getCPUDataCount();i++){//循环初始化CPU实例
			((ThreadDo)td[i]).setThreadInfo(config, config.getCpuStartIndex() + i,cdt);
		}
		
		NodeWorker nodeWorker = new NodeWorker(config);//实例化计算工人
		nodeWorker.addCPUJob(ThreadDo);//向工人添加计算任务
	}
	
	public void setGPUThreadDo(ThreadDo[] td) throws IOException {
		for(int i = 0;i < config.getGPUDataCount();i++){//循环初始化GPU实例
			((ThreadDo)td[i]).setThreadInfo(config,config.getGpuStartIndex()+ i,cdt);
		}
		NodeWorker nodeWorker = new NodeWorker(config);//实例化计算工人
		nodeWorker.addGPUJob(ThreadDo);//向工人添加计算任务
	}

}
