package computingNode;

import component.JobConfig;
import component.ThreadPool;

/*
 *向线程池添加任务*/
public class NodeWorker {

	ThreadPool threadPool;//线程池操作类
	JobConfig config;
	//初始化数据信息
	public NodeWorker(JobConfig config) {   
		this.config = config;
    }
	
	public void addCPUJob(ThreadDo[] cpuThreadDo) {
		// TODO Auto-generated method stub
		threadPool=new ThreadPool(16);//实例化线程池
		threadPool.getExecutor();//获取线程池对象
		for(int i = 0;i < config.getCPUDataCount();i++){//循环添加任务
			threadPool.addJob(cpuThreadDo[i]);
		}
	}
	
	public void addGPUJob(ThreadDo[] cpuThreadDo) {
		// TODO Auto-generated method stub
		threadPool=new ThreadPool(16);//实例化线程池
		threadPool.getExecutor();//获取线程池对象
		for(int i = 0;i < config.getGPUDataCount();i++){//循环添加任务
			threadPool.addJob(cpuThreadDo[i]);
		}
	}
}
